package com.unisoft.collection.distribution.loan;



import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.unisoft.collection.distribution.loan.loanApi.LoanApiPayload;
import com.unisoft.loanApi.model.*;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountInfo;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountService;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherInfo;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformation.AccountInformationEntity;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationRepository.AccountInformationRepository;
import com.unisoft.retail.loan.dataEntry.CustomerUpdate.accountInformationService.AccountInformationService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.user.UserRepository;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.unisoft.utillity.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class LoanDistributionService {

    private final DateUtils dateUtils;

    private final RetailLoanUcbApiService retailLoanUcbApiService;

    private final LoanAccountService loanAccountService;

    private final LoanAccountOtherService loanAccountOtherService;

    private final LoanAccountBasicService loanAccountBasicService;

    private final CustomerBasicInfoService customerBasicInfoService;

    private final LoanAccountDistributionService loanAccountDistributionService;

    private final AccountInformationService accountInformationService;

    @Autowired
    private final AccountInformationRepository accountInformationRepository;

    @Autowired
    private PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> saveManuallyDistributedAccounts(MultipartFile multipartFile) {
        Map<String, String> errors = new LinkedHashMap<>();
        Map<String, LoanAccountDistributionInfo> distributionInfos = new HashMap<>();
        Map<String, LoanAccountInfo> accountInfos = new HashMap<>();
        Map<String, LoanAccountOtherInfo> accountOtherInfos = new HashMap<>();

        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

            String accountNumber = null;
            Iterator<Row> rows = xssfSheet.iterator();
            rows.next();
            long time = new Date().getTime();
            while (rows.hasNext()) {
                try {
                    Row row = rows.next();
                    accountNumber = Objects.toString(row.getCell(0), "");

                    if (!isValidAccountForDistribution(accountNumber)) {
                        errors.put(accountNumber, "Invalid Account Number");
                        continue;
                    }else if (checkAccountStatus(accountNumber)){
                        errors.put(accountNumber, "This Account is not found!");
                        continue;

                    }else if (distributionInfos.get(accountNumber) != null) {
                        errors.put(accountNumber, "Duplicate Entry");
                        continue;
                    }

                    String dealerPin = Objects.toString(row.getCell(1), "").trim();
                    if (dealerPin.contains("@")) dealerPin = dealerPin.split("\\@")[0];

                    if (!StringUtils.hasText(dealerPin)) {
                        errors.put(accountNumber, "No dealer found");
                        continue;
                    }

                    if(!userRepository.findUserByUsername(dealerPin+"@ificbankbd.com").getRoles().get(0).getName().equalsIgnoreCase("dealer")){
                        errors.put(accountNumber, "Not a dealer");
                        continue;
                    }


                    if(loanAccountDistributionService.findLoanAccountDistributionInfoByAccountNo(accountNumber,"1") != null){
                        errors.put(accountNumber, "Already Distributed!");
                        continue;
                    }

                    String dealerName = Objects.toString(row.getCell(2), "").trim();

                    String branchMnemonic = Objects.toString(row.getCell(3), "").trim();
                    String productCode = Objects.toString(row.getCell(4), "").trim();
                    String dealReference = Objects.toString(row.getCell(5), "").trim();


                    LoanAccountDistributionInfo accountDistributionInfo = new LoanAccountDistributionInfo();
                    accountDistributionInfo.setDealerPin(dealerPin+"@ificbankbd.com");
                    accountDistributionInfo.setDealerName(dealerName);
                    accountDistributionInfo.setBranchMnemonic(branchMnemonic);
                    accountDistributionInfo.setProductCode(productCode);
                    accountDistributionInfo.setDealReference(dealReference);
                    accountDistributionInfo.setAccountNo(accountNumber);

                    distributionInfos.put(accountNumber, accountDistributionInfo);

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    errors.put(accountNumber, "Something went wrong");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        updateLoanAccountDistribution(distributionInfos);
//        manageBulkLoanAccountDistributionAsync(distributionInfos, accountInfos, accountOtherInfos);
        return errors;
    }



    public Map<String, String>saveMannualDealerWiseDistribution(LoanApiPayload loanApiPayload){

        String dealername = "";
        String dealerPin = "";
        Map<String, String> errors = new LinkedHashMap<>();
        Map<String, LoanAccountDistributionInfo> distributionInfos = new HashMap<>();

        PeopleAllocationLogicInfo peopleAllocationLogicInfo = peopleAllocationLogicRepository.findById(new Long(loanApiPayload.getId())).get();

        if (peopleAllocationLogicInfo != null) {
            dealerPin = peopleAllocationLogicInfo.getDealer().getPin();
            dealername = peopleAllocationLogicInfo.getDealer().getUser().getFirstName();
        }

        for(String accountNumber : loanApiPayload.getList()) {

            accountNumber = accountNumber.trim();

            String [] acc = accountNumber.split(":");

            if (!isValidAccountForDistribution(acc[0])) {
                errors.put(accountNumber, "Invalid Account Number");
                continue;
            } else if (checkAccountStatus(acc[0])) {
                errors.put(acc[0], "This Account is not found!");
                continue;

            } else if (distributionInfos.get(acc[0]) != null) {
                errors.put(acc[0], "Duplicate Entry!");
                continue;
            }

            if (!StringUtils.hasText(dealerPin)) {
                errors.put(acc[0], "No dealer found!");
                continue;
            }

            AccountInformationEntity accountInformationEntity = accountInformationService.getAllAccountInformation(acc[0],acc[1],acc[2],acc[3]);

            if (accountInformationEntity.getIsEscalated().equalsIgnoreCase("N")){
                errors.put(acc[0], "This Account is not Escalated!");
                continue;
            }

            String branchMnemonic = accountInformationEntity.getBranchMnemonic();
            String productCode = accountInformationEntity.getProductCode();
            String dealReference = accountInformationEntity.getDealReference();

            LoanAccountDistributionInfo accountDistributionInfo = new LoanAccountDistributionInfo();
            accountDistributionInfo.setDealerPin(dealerPin);
            accountDistributionInfo.setDealerName(dealername);
            accountDistributionInfo.setBranchMnemonic(branchMnemonic);
            accountDistributionInfo.setProductCode(productCode);
            accountDistributionInfo.setDealReference(dealReference);
            accountDistributionInfo.setAccountNo(acc[0]);

            distributionInfos.put(acc[0],accountDistributionInfo);

        }
        updateLoanAccountDistribution(distributionInfos);

        List<AccountInformationEntity> accountInformationEntities = new ArrayList<>();
        distributionInfos.forEach((accountNumber,distribution)-> {
            UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            AccountInformationEntity accountInformationEntity = accountInformationService.getAllAccountInformation(accountNumber, distribution.getBranchMnemonic(), distribution.getProductCode(), distribution.getDealReference());
            LoanAccountDistributionInfo loanAccountDistributionInfo=  loanAccountDistributionService.findLoanAccountDistributionInfoByAccountNo(accountNumber,"1");
            if (loanAccountDistributionInfo == null && accountInformationEntity != null) {
                accountInformationEntity.setIsDistributed("Y");
                accountInformationEntity.setIsEscalated("N");
                accountInformationEntity.setModifiedBy(principal.getUsername());
                accountInformationEntity.setModifiedDate(new Date());

                accountInformationEntities.add(accountInformationEntity);
                //accountInformationRepository.save(accountInformationEntity);
            }
            if(accountInformationEntities.size()>500)
                accountInformationRepository.saveAll(accountInformationEntities);
        });
        accountInformationRepository.saveAll(accountInformationEntities);

        return errors;
    }



    public void updateLoanAccountDistribution(Map<String, LoanAccountDistributionInfo> distributionInfos) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        //List<LoanAccountDistributionInfo> list = loanAccountDistributionService.findLoanAccountDistributionInfoByLatest("1");
//        for (var entry : distributionInfos.entrySet()) {
//            LoanAccountDistributionInfo loanAccountDistributionInfo=  loanAccountDistributionService.findLoanAccountDistributionInfoByAccountNo(entry.getKey(),"1");
//            if (loanAccountDistributionInfo !=null) {
//                loanAccountDistributionInfo.setLatest("0");
//                loanAccountDistributionInfo.setStartDate(loanAccountDistributionInfo.getCreatedDate());
//                loanAccountDistributionInfo.setEndDate(new Date());
//            }
//            loanAccountDistributionService.save(loanAccountDistributionInfo);
//        }

        distributionInfos.forEach((accountNumber,distribution)->{
            System.out.println(accountNumber);
            AccountInformationEntity accountInformationEntity = accountInformationService.getAllAccountInformation(accountNumber,distribution.getBranchMnemonic(),distribution.getProductCode(),distribution.getDealReference());
            LoanAccountDistributionInfo loanAccountDistributionInfo=  loanAccountDistributionService.findLoanAccountDistributionInfoByAccountNo(accountNumber,"1");
            if (loanAccountDistributionInfo == null && accountInformationEntity != null){
                CustomerBasicInfoEntity customerBasicInfoEntity = updateCustomerBasiscInfo(accountInformationEntity);

                LoanAccountBasicInfo loanAccountBasicInfo = updateLoanAccountBasicInfo(accountInformationEntity, customerBasicInfoEntity);
                distribution.setLoanAccountBasicInfo(loanAccountBasicInfo);
                distribution.setLatest("1");
                distribution.setWriteOffAccount("0");
                distribution.setSamAccount("0");
                distribution.setCreatedDate(new Date());
                distribution.setCreatedBy(username);
                distribution.setStatusDate(new Date());
                distribution.setOutStanding(accountInformationEntity.getTotalOutstanding());
                distribution.setDpdBucket(accountInformationEntity.getDpd());
                distribution.setEmiAmount(Double.parseDouble(accountInformationEntity.getEmiAmount()));
                distribution.setOpeningOverDue(Double.parseDouble(accountInformationEntity.getOverdue()));
                distribution.setStartDate(new Date());
                loanAccountDistributionService.saveDistribution(distribution);

                accountInformationEntity.setIsDistributed("Y");

                accountInformationRepository.save(accountInformationEntity);
            }
        });
    }

    public LoanAccountBasicInfo updateLoanAccountBasicInfo(AccountInformationEntity accountInformationEntity, CustomerBasicInfoEntity customerBasicInfoEntity) {
        LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNo(accountInformationEntity.getLoanAccountNew());
        LoanAccountBasicInfo basicInfo;
        if (loanAccountBasicInfo.getId() != null){
            loanAccountBasicInfo.setCustomer(customerBasicInfoEntity);
            loanAccountBasicInfo.setAccountName(accountInformationEntity.getCustomerName());
            //loanAccountBasicInfo.setAccountNo(accountInformationEntity.getLoanAccountNew().replaceAll("\\s", ""));

            if(!accountInformationEntity.getBranchMnemonic().isEmpty() && !accountInformationEntity.getProductCode().isEmpty() && !accountInformationEntity.getDealReference().isEmpty()) {
                loanAccountBasicInfo.setAccountNo(accountInformationEntity.getLoanACNo()+""+accountInformationEntity.getBranchMnemonic()+""+accountInformationEntity.getProductCode()+""+accountInformationEntity.getDealReference());
            }else {
                loanAccountBasicInfo.setAccountNo(accountInformationEntity.getLoanAccountNew().replaceAll("\\s", ""));
            }

            basicInfo =  loanAccountBasicService.save(loanAccountBasicInfo);
        }else {
            LoanAccountBasicInfo loanAccountBasicInfo1 = new LoanAccountBasicInfo();
            loanAccountBasicInfo1.setCustomer(customerBasicInfoEntity);
            loanAccountBasicInfo1.setAccountName(accountInformationEntity.getCustomerName());
            loanAccountBasicInfo1.setAccountNo(accountInformationEntity.getLoanAccountNew().replaceAll("\\s", ""));
            basicInfo =  loanAccountBasicService.save(loanAccountBasicInfo1);
        }

        return basicInfo;
    }

    public CustomerBasicInfoEntity updateCustomerBasiscInfo(AccountInformationEntity accountInformationEntity) {
        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoService.findByAccountNo(accountInformationEntity.getLoanAccountNew());
        CustomerBasicInfoEntity basicInfoEntity;
        if (customerBasicInfoEntity != null){
            customerBasicInfoEntity.setCustomerName(accountInformationEntity.getCustomerName());
            customerBasicInfoEntity.setEmail(accountInformationEntity.getEmail());
            customerBasicInfoEntity.setCustomerFatherName(accountInformationEntity.getFatherName());
            customerBasicInfoEntity.setBranchCode(accountInformationEntity.getBranchCode());
            customerBasicInfoEntity.setBranchName(accountInformationEntity.getBranchName());
            customerBasicInfoEntity.setSex(accountInformationEntity.getGender());
            customerBasicInfoEntity.setMobileNumber(accountInformationEntity.getMobile());
            customerBasicInfoEntity.setMotherName(accountInformationEntity.getMotherName());
            customerBasicInfoEntity.setNid(accountInformationEntity.getNid());
            customerBasicInfoEntity.setOccupation(accountInformationEntity.getProfession());
            customerBasicInfoEntity.setTin(accountInformationEntity.getTin());
            // customerBasicInfoEntity.setAccountNo(accountInformationEntity.getLoanAccountNew().replaceAll("\\s",""));

            if(!accountInformationEntity.getBranchMnemonic().isEmpty() && !accountInformationEntity.getProductCode().isEmpty() && !accountInformationEntity.getDealReference().isEmpty()) {
                customerBasicInfoEntity.setAccountNo(accountInformationEntity.getLoanACNo()+""+accountInformationEntity.getBranchMnemonic()+""+accountInformationEntity.getProductCode()+""+accountInformationEntity.getDealReference());
            }else {
                customerBasicInfoEntity.setAccountNo(accountInformationEntity.getLoanAccountNew().replaceAll("\\s", ""));
            }

            basicInfoEntity =  customerBasicInfoService.save(customerBasicInfoEntity);
        }else {
            CustomerBasicInfoEntity customerBasicInfoEntity1 = new CustomerBasicInfoEntity();
            customerBasicInfoEntity1.setCustomerName(accountInformationEntity.getCustomerName());
            customerBasicInfoEntity1.setEmail(accountInformationEntity.getEmail());
            customerBasicInfoEntity1.setCustomerFatherName(accountInformationEntity.getFatherName());
            customerBasicInfoEntity1.setBranchCode(accountInformationEntity.getBranchCode());
            customerBasicInfoEntity1.setBranchName(accountInformationEntity.getBranchName());
            customerBasicInfoEntity1.setSex(accountInformationEntity.getGender());
            customerBasicInfoEntity1.setMobileNumber(accountInformationEntity.getMobile());
            customerBasicInfoEntity1.setMotherName(accountInformationEntity.getMotherName());
            customerBasicInfoEntity1.setNid(accountInformationEntity.getNid());
            customerBasicInfoEntity1.setOccupation(accountInformationEntity.getProfession());
            customerBasicInfoEntity1.setTin(accountInformationEntity.getTin());

            customerBasicInfoEntity1.setAccountNo(accountInformationEntity.getLoanAccountNew().replaceAll("\\s", ""));
            basicInfoEntity = customerBasicInfoService.save(customerBasicInfoEntity1);
        }



        return basicInfoEntity;
    }

    private boolean isValidAccountForDistribution(String accountNumber) {
        boolean isAccountNumberValid = StringUtils.hasText(accountNumber) && accountNumber.length() == 13;
        return isAccountNumberValid;
    }

    private boolean checkAccountStatus(String accountNumber) {
        AccountInformationEntity accountInformationEntity = accountInformationService.findAccountInformationByLoanAccountNo(accountNumber);
        if(accountInformationEntity != null){
            return false;
        }
        return true;
    }

    private CustomerBasicInfoEntity findOrSaveCustomerInfo(String accountNumber) {
        CustomerBasicInfoEntity customerInfo = new CustomerBasicInfoEntity(accountNumber);
        customerInfo.setTypeAccount("Loan");
        return customerBasicInfoService.findOrSave(customerInfo);
    }

//    public void manageBulkLoanAccountDistributionAsync(Map<String, LoanAccountDistributionInfo> distributionInfos,
//                                                       Map<String, LoanAccountInfo> accountInfos,
//                                                       Map<String, LoanAccountOtherInfo> accountOtherInfos) {
//        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = user.getUsername();
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                ExecutorService executorService = Executors.newFixedThreadPool(100);
//
////                List<LoanAccountDistributionInfo> distributionInfos = loanAccountDistributionService.findCurrentMonthAccountDistList();
//
//                distributionInfos.forEach((accountNumber, distributionInfo) -> {
//
////                    String accountNo = distributionInfo.getLoanAccountBasicInfo().getAccountNo();
//
//                    LoanAccountInfo loanAccountInfo = accountInfos.get(accountNumber);
//                    LoanAccountOtherInfo accountOtherInfo = accountOtherInfos.get(accountNumber);
//
//                    executorService.execute(new Runnable() {
//
//                        @Override
//                        public void run() {
//
//                            if (!loanAccountDistributionService.isValidDistribution(distributionInfo)) return;
//
//                            String accountNumber = distributionInfo.getLoanAccountBasicInfo().getAccountNo();
//
//                            AccountInformationEntity accountInformationEntity = accountInformationService.findAccountInformationByLoanAccountNo(accountNumber);
//
//                            /* get account info and details info from server for specific account */
//
//                                LoanAccountBasicInfo loanAccountBasicInfo = distributionInfo.getLoanAccountBasicInfo();
//
//                                CustomerBasicInfoEntity customer = loanAccountBasicInfo.getCustomer();
//                                customer.setBranchName(accountInformationEntity.getBranchName());
//                                CustomerInfo customerInfo = updateCustomerInfo(customer,accountInformationEntity, username);
//
//                                loanAccountBasicInfo.setLocation(customerInfo.getLocation());
//                                loanAccountBasicInfo = updateLoanAccountBasicInfo(loanAccountBasicInfo, accInfoFromReportServer, accDetailsFormReportServer, username);
//
//                                updateLoanAccountDistributionInfo(distributionInfo, loanAccountBasicInfo, accDetailsFormReportServer);
//                                updateLoanAccountInfo(loanAccountInfo, loanAccountBasicInfo, accDetailsFormReportServer, branchInfoFormReportServer, username);
//                                updateLoanAccountOtherInfo(accountOtherInfo, loanAccountBasicInfo, accInfoFromReportServer, username);
//                            }
//
//                    });
//
//                });
//                executorService.shutdown();
//            }
//        });
//        thread.start();
//    }







    public CustomerInfo updateCustomerInfo(CustomerBasicInfoEntity customer, LoanAccInfo loanAccInfo, LoanAccDetails loanAccDetails, String username) {
        CustomerInfo customerInfo = new CustomerInfo();
        if (customerBasicInfoService.isExistingCustomer(customer)) {
            loanAccDetails = populateLoanAccDetails(loanAccDetails, customer.getAccountNo());
            customerInfo = populateCustomerInfoWithApiData(customer, loanAccInfo, loanAccDetails);
            customerBasicInfoService.saveOrUpdate(customer, username);
        }
        return customerInfo;
    }

    public CustomerInfo populateCustomerInfoWithApiData(@NonNull CustomerBasicInfoEntity customer, @NonNull LoanAccInfo loanAccInfo, @NonNull LoanAccDetails loanAccDetails) {
        CustomerInfo customerInfoFromReportServer = new CustomerInfo();

        if (StringUtils.hasText(loanAccInfo.getCustomerId())) {

            customerInfoFromReportServer = retailLoanUcbApiService.getCustomerInfo(loanAccInfo.getCustomerId());

        }
        return customerInfoFromReportServer;
    }

    public LoanAccountBasicInfo updateLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo, LoanAccInfo accInfoFromReportServer, LoanAccDetails loanAccDetails, String username) {

        CustomerBasicInfoEntity customer = Optional.ofNullable(loanAccountBasicInfo.getCustomer()).orElse(new CustomerBasicInfoEntity());
        populateLoanAccInfo(accInfoFromReportServer, customer.getAccountNo());
        populateLoanAccDetails(loanAccDetails, customer.getAccountNo());

        List<LoanAccSchedule> loanAccSchedule = retailLoanUcbApiService.getAccScheduleDetails(loanAccountBasicInfo.getAccountNo());
        boolean fid = !loanAccSchedule.isEmpty() && loanAccSchedule.get(0).getTotalOverdue() > 0;

        String location = "-";
        String expiryDateString = loanAccDetails.getExpiryDate();
        Date expiryDate = StringUtils.hasText(expiryDateString) ? dateUtils.getFormattedDate(expiryDateString, "dd-MM-yyyy") : null;
        String accountName = accInfoFromReportServer.getAccountName();
        String linkAccountNumber = accInfoFromReportServer.getAltAccNo();
        double linkAccountBalance = loanAccDetails.getSattlementAcBal();
        String disbursementDateString = accInfoFromReportServer.getDisbursmentDt();
        Date disbursementDate = StringUtils.hasText(disbursementDateString) ?
                dateUtils.getFormattedDate(disbursementDateString, "dd-MMM-yyyy") :
                dateUtils.getFormattedDate("1983-06-26", "yyyy-MM-dd");
        double disburseAmount = getDisbursementAmountByAccount(loanAccountBasicInfo, disbursementDate);
//        double disburseAmount = accInfoFromReportServer.getDisburseLoanAmount();
//        Date disbursementDate = dateManager.getFormattedDate("dd-MMM-yyyy", accInfoFromReportServer.getDisbursmentDt());

        loanAccountBasicInfo.setAccountName(accountName);
        loanAccountBasicInfo.setLinkAccountNumber(linkAccountNumber);
        loanAccountBasicInfo.setLinkAccountBalance(linkAccountBalance);
        loanAccountBasicInfo.setDisbursementAmount(disburseAmount);
        loanAccountBasicInfo.setDisbursementDate(disbursementDate);
        loanAccountBasicInfo.setExpiryDate(expiryDate);
        loanAccountBasicInfo.setFid(fid);

        loanAccountBasicService.saveOrUpdate(loanAccountBasicInfo, username);
        return loanAccountBasicInfo;
    }
    //
    public LoanAccountDistributionInfo updateLoanAccountDistributionInfo(LoanAccountDistributionInfo loanAccountDistributionInfo,
                                                                         LoanAccountBasicInfo loanAccountBasicInfo, LoanAccDetails loanAccDetails) {

        populateLoanAccDetails(loanAccDetails, loanAccountBasicInfo.getAccountNo());
        Date statusDate = dateUtils.getMonthStartDate();
        String schemeCode = loanAccDetails.getProductCode();
        double outStanding = loanAccDetails.getOutStandingLocalCurrency();
        Double openingOverDue = loanAccDetails.getOverdue();

        String emiDueDate = dateUtils.getFormattedDateString(loanAccDetails.getScheduleNextDate(), "dd-MM-yyyy");
        double emiAmount = loanAccDetails.getEmiAmount();

        long dpd = loanAccDetails.getOverdueDays();
        String dpdBucket = Double.toString(loanAccDetails.getDpdBucket()).split("\\.")[0];

        double lastPayment = loanAccDetails.getAmountPaidLastMnth();
        Date lastPaymentDate = loanAccDetails.getLastPaymentDate();
        double totalPayment = loanAccDetails.getTotalAmountPaid();

        loanAccountDistributionInfo.setOutStanding(Objects.toString(outStanding, "0"));
        loanAccountDistributionInfo.setOpeningOverDue(openingOverDue);
        loanAccountDistributionInfo.setCurrentOverdue(openingOverDue);
        loanAccountDistributionInfo.setSchemeCode(schemeCode);
        loanAccountDistributionInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
        loanAccountDistributionInfo.setEmiAmount(emiAmount);
        loanAccountDistributionInfo.setDpdBucket(dpdBucket);
        loanAccountDistributionInfo.setCurrentDpdBucket(dpdBucket);
        loanAccountDistributionInfo.setEmiDueDate(emiDueDate);
        loanAccountDistributionInfo.setLastPaidAmount(lastPayment);
        loanAccountDistributionInfo.setLastPaymnetDate(lastPaymentDate);
        loanAccountDistributionInfo.setTotalPayment(totalPayment);
        loanAccountDistributionInfo.setDpd((double) dpd);
        loanAccountDistributionInfo.setStatusDate(statusDate);

        loanAccountDistributionService.update(loanAccountDistributionInfo);
        return loanAccountDistributionInfo;
    }
    //
    public LoanAccountInfo updateLoanAccountInfo(LoanAccountInfo loanAccountInfo, LoanAccountBasicInfo loanAccountBasicInfo, LoanAccDetails loanAccDetails, BranchInfo branchInfo, String username) {

        String schemeCode = loanAccDetails.getProductCode();
        double outstandingPrincipal = loanAccDetails.getPrincipalOutstanding();
        double installmentAmount = loanAccDetails.getEmiAmount();
        double ate = loanAccDetails.getDpdBucket();
        String assetClassification = loanAccDetails.getStatus1();
        double totalOverdue = loanAccDetails.getOverdue();
        double totalOutstanding = loanAccDetails.getOutStandingLocalCurrency();
//        double cumulativePayment;
//        String nonPerformingLoan;
        String emiDueDate = dateUtils.getFormattedDateString(loanAccDetails.getScheduleNextDate(), "dd-MM-yyyy");
//        String grossDistributionCriteriaNew;
        String branchName = branchInfo.getBranchName();

        loanAccountInfo.setAte(ate);
        loanAccountInfo.setAssetClassification(assetClassification);
        loanAccountInfo.setInstallmentAmount(installmentAmount);
        loanAccountInfo.setSchemeCode(schemeCode);
        loanAccountInfo.setTotalOverdue(totalOverdue);
        loanAccountInfo.setOutstandingPrincipal(outstandingPrincipal);
        loanAccountInfo.setTotalOutstanding(totalOutstanding);
        loanAccountInfo.setEmiDueDate(emiDueDate);
        loanAccountInfo.setBranchName(branchName);
        loanAccountInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
        loanAccountService.saveOrUpdate(loanAccountInfo, username);

        return loanAccountInfo;
    }
    //
    public LoanAccountOtherInfo updateLoanAccountOtherInfo(LoanAccountOtherInfo loanAccountOtherInfo, LoanAccountBasicInfo loanAccountBasicInfo, LoanAccInfo loanAccInfo, String username) {
        // TODO: if required data provided in future, have to change it;
        String status = loanAccInfo.getAccountStatus();

        loanAccountOtherInfo.setNotificationStatus(status);
        loanAccountOtherInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
        loanAccountOtherService.saveOrUpdate(loanAccountOtherInfo, username);

        return loanAccountOtherInfo;
    }

    public LoanAccDetails populateLoanAccDetails(LoanAccDetails loanAccDetails, String accountNo) {
        return (loanAccDetails == null || loanAccDetails.getAccountNumber() == null) ?
                retailLoanUcbApiService.getLoanAccountDetails(accountNo) : loanAccDetails;
    }
    //
    public LoanAccInfo populateLoanAccInfo(LoanAccInfo loanAccInfo, String accountNo) {
        return (loanAccInfo == null || loanAccInfo.getAccountNumber() == null) ?
                retailLoanUcbApiService.getLoanAccountInfo(accountNo) : loanAccInfo;
    }

    public Set<String> getDistributedAccountNumbers() {
        return loanAccountDistributionService.getCurrentMonthDistributedAccountNumbers();
    }

    /**
     * Disbursement Amount = Sum of Principal Debit in Account Statements
     * To reduce computer process, previous disbursement amount is added to new
     * changes in Account Statement. Therefore, account statements after previous calculation
     * are collected form server.
     * <p>
     * Implemented by
     * At 15 April 2021
     *
     * @param accountBasicInfo
     * @param disbursementDate
     * @return Current Disbursement amount
     */
    public double getDisbursementAmountByAccount(LoanAccountBasicInfo accountBasicInfo, Date disbursementDate) {
        try {

            Date startDate = null;
            if (accountBasicInfo.getDisbursementDate() != null) {
                // For old accounts
                if (accountBasicInfo.getModifiedDate() != null) {
                    startDate = dateUtils.getNextOrPreviousDate(accountBasicInfo.getModifiedDate(), 1);
                } else {
                    startDate = dateUtils.getNextOrPreviousDate(accountBasicInfo.getCreatedDate(), 1);
                }
            } else {
                // For new accounts
                startDate = disbursementDate;
            }
            double disbursementAmount = retailLoanUcbApiService.getLoanAccStatement(accountBasicInfo.getAccountNo(), startDate, new Date())
                    .stream().mapToDouble(LoanAccStatement::getPrincipalDebit).sum();

            return accountBasicInfo.getDisbursementAmount() + disbursementAmount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0D;
    }

}