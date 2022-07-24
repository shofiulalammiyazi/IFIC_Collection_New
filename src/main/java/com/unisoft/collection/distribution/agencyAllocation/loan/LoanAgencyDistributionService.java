package com.unisoft.collection.distribution.agencyAllocation.loan;
/*
Created by   Islam at 7/22/2019
*/

import com.unisoft.beans.Validation;
import com.unisoft.collection.distribution.loan.LoanDistributionService;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountInfo;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountService;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherInfo;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.loanApi.model.*;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import com.unisoft.utillity.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LoanAgencyDistributionService {

    @Autowired
    private LoanAgencyDistributionDao loanAgencyDistributionDao;

    @Autowired
    private LoanAgencyDistributionRepository loanAgencyDistributionRepository;
    @Autowired
    private CustomerBasicInfoService customerBasicInfoService;
    @Autowired
    private LoanAccountBasicService loanAccountBasicService;
    @Autowired
    private LoanAccountService loanAccountService;
    @Autowired
    private LoanAccountOtherService loanAccountOtherService;
    @Autowired
    private RetailLoanUcbApiService retailLoanUcbApiService;
    @Autowired
    private LoanDistributionService loanDistributionService;
    @Autowired
    private DateUtils dateUtils;
    

    public List<LoanAgencyDistributionInfo> getAll() {
        return loanAgencyDistributionDao.getList();
    }

    public boolean saveNew(LoanAgencyDistributionInfo agency) {
        return loanAgencyDistributionDao.saveNew(agency);
    }

    public boolean updateAgency(LoanAgencyDistributionInfo agency) {
        return loanAgencyDistributionDao.updateObj(agency);
    }

    public LoanAgencyDistributionInfo getById(Long Id) {
        return loanAgencyDistributionDao.getById(Id);
    }

    public List<LoanAgencyDistributionInfo> getActiveList() {
        return loanAgencyDistributionDao.getActiveOnly();
    }

    public List<LoanAgencyDistributionInfo> findByLoanAccountBasicInfo(LoanAccountBasicInfo byAccountNo) {
        return loanAgencyDistributionDao.findByLoanAccountBasicInfo(byAccountNo);
    }

    public LoanAgencyDistributionInfo findByCreatedDateIsBetweenAndLoanAccountBasicInfoAndLatest(LoanAccountBasicInfo loanAccountBasicInfo) {
        return loanAgencyDistributionRepository.findFirstByCreatedDateGreaterThanEqualAndCreatedDateLessThanEqualAndLoanAccountBasicInfoAndLatestOrderByCreatedDateDesc(getStartDate(), getEndDate(), loanAccountBasicInfo, "1");
    }


    public Date getStartDate() {
        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        return Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getEndDate() {
        LocalDate today = LocalDate.now();
        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(endDateOfMonthString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map<String, Object> storeData(MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        List<String> errorMessages = this.validate(file);
        if (errorMessages.size() == 0){
            List<LoanAgencyDistributionInfo> allLoanAgencyDistributionInfos = new ArrayList<>();
            List<LoanAccountInfo> allLoanAccountInfo = new ArrayList<>();
            List<LoanAccountOtherInfo> allLoanAccountOtherInfo = new ArrayList<>();
            List<String> accountNos = new ArrayList<>();


            try{
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                Iterator<Row> rows = xssfSheet.iterator();
                Row row = rows.next();
                Iterator<Cell> cells = row.cellIterator();
                List<String> cellName = new ArrayList<>();
                while(cells.hasNext()){
                    cellName.add(Objects.toString(cells.next(),"").toUpperCase());
                }

                while (rows.hasNext()){
                    row = rows.next();
                    LoanAgencyDistributionInfo loanAgencyDistributionInfo = new LoanAgencyDistributionInfo();
                    loanAgencyDistributionInfo.setStatusDate(new Date());

                    Cell cellAccountNo = row.getCell(cellName.indexOf("ACCOUNT NO"));
                    String accountNo = Objects.toString(cellAccountNo);
                    accountNos.add(accountNo);

                    Cell cellAccountName = row.getCell(cellName.indexOf("A/C NAME"));
                    String accountName = Objects.toString(cellAccountName, "");

                    Cell cellCustomerID = row.getCell(cellName.indexOf("CUSTOMER ID"));
                    String customerId = Objects.toString(cellCustomerID, "");

                    Cell cellAgencyID = row.getCell(cellName.indexOf("AGENCY ID"));
                    String agencyId = Objects.toString(cellAgencyID,"");
                    loanAgencyDistributionInfo.setAgencyPin(agencyId);

                    Cell cellAgencyName = row.getCell(cellName.indexOf("AGENCY NAME"));
                    String agencyName = Objects.toString(cellAgencyName,"");
                    loanAgencyDistributionInfo.setAgencyName(agencyName);

                    /* Process other data
                     * Update customer informations */
                    CustomerBasicInfoEntity customerInfo = new CustomerBasicInfoEntity(accountNo);
                    customerInfo.setCustomerId(customerId);
                    customerInfo = customerBasicInfoService.findOrSave(customerInfo);
                    if (customerInfo == null || customerInfo.getId() == null) continue;

                    /*Update Loan Account Basic Information*/
                    Date expiryDate = new Date();

                    LoanAccountBasicInfo loanAccountBasicInfo = new LoanAccountBasicInfo("-", expiryDate, customerInfo);
                    loanAccountBasicInfo.setAccountName(accountName);
                    loanAccountBasicInfo.setDisbursementDate(null);
                    loanAccountBasicInfo = loanAccountBasicService.findOrSave(loanAccountBasicInfo);

                    /*Set loan account distribution information */
                    Double outStanding = 0d;
                    Double dpd = 0d;
                    String dpdBucket = "X";

                    Long loanAccountBasicInfoId = loanAccountBasicInfo.getId();
                    this.updateLoanAccountDistributionLatestStatus(loanAccountBasicInfoId);

                    /* Loan Account Information */
                    LoanAccountInfo loanAccountInfo = new LoanAccountInfo();
                    loanAccountInfo.setAte(0d);
                    loanAccountInfo.setAssetClassification("-");
                    loanAccountInfo.setInstallmentAmount(0d);
                    loanAccountInfo.setSchemeCode("-");
                    loanAccountInfo.setTotalOverdue(0d);
                    loanAccountInfo.setTotalOutstanding(outStanding);
                    loanAccountInfo.setDpd(dpd);
                    loanAccountInfo.setEmiDueDate("-");
                    loanAccountInfo.setDpbBucket(dpdBucket);
                    loanAccountInfo.setBranchName("-");
                    loanAccountInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                    loanAccountService.saveOrUpdate(loanAccountInfo);

                    LoanAccountOtherInfo loanAccountOtherInfo = new LoanAccountOtherInfo();
                    loanAccountOtherInfo.setNotificationStatus("-");
                    loanAccountOtherInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                    loanAccountOtherService.saveOrUpdate(loanAccountOtherInfo);

                    /*Remember data in memory to updateLoanAccountDistributionLatestStatus all data*/
                    loanAgencyDistributionInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);

                    allLoanAgencyDistributionInfos.add(loanAgencyDistributionInfo);
                    allLoanAccountInfo.add(loanAccountInfo);
                    allLoanAccountOtherInfo.add(loanAccountOtherInfo);
                }

                List<LoanAgencyDistributionInfo> prevDistributionList = loanAgencyDistributionRepository.findByAccountNo(accountNos);
                prevDistributionList.forEach(s -> s.setLatest("0"));
                loanAgencyDistributionRepository.saveAll(prevDistributionList);

                //save new distribution
                loanAgencyDistributionRepository.saveAll(allLoanAgencyDistributionInfos);

                this.manageBulkLoanAccountDistributionAsync(allLoanAgencyDistributionInfos, allLoanAccountInfo, allLoanAccountOtherInfo);
            }catch (IOException e){
                e.printStackTrace();
            }

            response.put("outcome", "success");

        }else {
            response.put("outcome", "failure");
            response.put("errors", errorMessages);
        }
        return response;
    }

    private void manageBulkLoanAccountDistributionAsync(List<LoanAgencyDistributionInfo> allLoanAgencyDistributionInfos, List<LoanAccountInfo> allLoanAccountInfo, List<LoanAccountOtherInfo> allLoanAccountOtherInfo) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ExecutorService executorService = Executors.newFixedThreadPool(100);
                int index = 0;

                for (LoanAgencyDistributionInfo distribution : allLoanAgencyDistributionInfos){
                    LoanAccountInfo loanAccountInfo = allLoanAccountInfo.get(index);
                    LoanAccountOtherInfo loanAccountOtherInfo = allLoanAccountOtherInfo.get(index);
                    index += 1;

                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (!isValidDistribution(distribution)) return;

//                            LoanAccountBasicInfo loanAccountBasicInfo = distribution.getLoanAccountBasicInfo();
                            String accountNumber = distribution.getLoanAccountBasicInfo().getAccountNo();

                            /* Fetch account information from client database server */
                            LoanAccInfo clientLoanAccountInfo = retailLoanUcbApiService.getLoanAccountInfo(accountNumber);

                            if (clientLoanAccountInfo.getAccountStatus().equals("Liquidated")){
                                LoanAccDetails accDetailsFormReportServer = retailLoanUcbApiService.getLoanAccountDetails(accountNumber);
                                BranchInfo branchInfoFormReportServer = retailLoanUcbApiService.getBranchInfo(clientLoanAccountInfo.getBranchCode());

                                LoanAccountBasicInfo loanAccountBasicInfo = distribution.getLoanAccountBasicInfo();

                                CustomerBasicInfoEntity customer = loanAccountBasicInfo.getCustomer();
                                customer.setBranchName(branchInfoFormReportServer.getBranchName());
                                CustomerInfo customerInfo = updateCustomerInfo(customer, clientLoanAccountInfo, accDetailsFormReportServer, username);

                                loanAccountBasicInfo.setLocation(customerInfo.getLocation());
                                loanAccountBasicInfo = updateLoanAccountBasicInfo(loanAccountBasicInfo, clientLoanAccountInfo, accDetailsFormReportServer, username);

                                updateLoanAccountDistributionInfo(distribution, loanAccountBasicInfo, accDetailsFormReportServer);
                                updateLoanAccountInfo(loanAccountInfo, loanAccountBasicInfo, accDetailsFormReportServer, branchInfoFormReportServer, username);
                                updateLoanAccountOtherInfo(loanAccountOtherInfo, loanAccountBasicInfo, clientLoanAccountInfo, username);
                            }
                            
                        }
                    });
                }
                executorService.shutdown();
            }
        });
        thread.start();
    }

    public LoanAccountOtherInfo updateLoanAccountOtherInfo(LoanAccountOtherInfo loanAccountOtherInfo, LoanAccountBasicInfo loanAccountBasicInfo, LoanAccInfo loanAccInfo, String username) {
        // TODO: if required data provided in future, have to change it;
        String status = loanAccInfo.getAccountStatus();

        loanAccountOtherInfo.setNotificationStatus(status);
        loanAccountOtherInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
        loanAccountOtherService.saveOrUpdate(loanAccountOtherInfo, username);

        return loanAccountOtherInfo;
    }

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

    public LoanAgencyDistributionInfo updateLoanAccountDistributionInfo(LoanAgencyDistributionInfo loanAgencyDistributionInfo,
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

        loanAgencyDistributionInfo.setOutstanding(Objects.toString(outStanding, "0"));
        loanAgencyDistributionInfo.setOpeningOverdue(openingOverDue);
        loanAgencyDistributionInfo.setCurrentOverdue(openingOverDue);
        loanAgencyDistributionInfo.setSchemeCode(schemeCode);
        loanAgencyDistributionInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
        loanAgencyDistributionInfo.setEmiAmount(emiAmount);
        loanAgencyDistributionInfo.setDpdBucket(dpdBucket);
        loanAgencyDistributionInfo.setCurrentDpdBucket(dpdBucket);
        loanAgencyDistributionInfo.setEmiDueDate(emiDueDate);
        loanAgencyDistributionInfo.setLastPaidAmount(lastPayment);
        loanAgencyDistributionInfo.setLastPaymnetDate(lastPaymentDate);
        loanAgencyDistributionInfo.setTotalPayment(totalPayment);
        loanAgencyDistributionInfo.setDpd((double) dpd);
        loanAgencyDistributionInfo.setStatusDate(statusDate);

        update(loanAgencyDistributionInfo);
        return loanAgencyDistributionInfo;
    }

    public LoanAgencyDistributionInfo update(LoanAgencyDistributionInfo entity) {
//        setDefaultDistributionValues(entity);
        return isValidDistribution(entity) ? loanAgencyDistributionRepository.save(entity) : null;
    }



    private boolean isValidDistribution(LoanAgencyDistributionInfo distribution) {
        return distribution != null && loanAccountBasicService.isValidAccount(distribution.getLoanAccountBasicInfo())
                && customerBasicInfoService.isValidCustomer(distribution.getLoanAccountBasicInfo().getCustomer());
    }

    private void updateLoanAccountDistributionLatestStatus(Long loanAccountBasicInfoId) {
        loanAgencyDistributionRepository.updateLoanAccountDistributionLatestStatus(loanAccountBasicInfoId);
    }

    private List<String> validate(MultipartFile file) {
        ArrayList<String> errors = new ArrayList<>();
        List<String> validCells = Arrays.asList(
                new String[]{"ACCOUNT NO", "AGENCY ID", "AGENCY NAME"});

        if (Validation.isStringEmpty(file.getOriginalFilename())){
            errors.add("Attachment File required");
        }
        else {
            try{
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

                Iterator<Row> rows = xssfSheet.iterator();
                Row row = rows.next();

                Iterator<Cell> cells = row.cellIterator();
                List<String> addedCellNames = new ArrayList<String>();

                while (cells.hasNext()){
                    addedCellNames.add(Objects.toString(cells.next(), "").toUpperCase());
                }

                //Check Columns Validity
                for (String cellName:validCells){
                    if (!addedCellNames.contains(cellName)){
                        errors.add(cellName + " is required");
                    }
                }

            } catch (IOException e){
                e.printStackTrace();
            }
        }

        return errors;
    }


    public CustomerInfo updateCustomerInfo(CustomerBasicInfoEntity customer, LoanAccInfo loanAccInfo, LoanAccDetails loanAccDetails, String username) {
        CustomerInfo customerInfo = new CustomerInfo();
        if (customerBasicInfoService.isExistingCustomer(customer)) {
            loanAccDetails = populateLoanAccDetails(loanAccDetails, customer.getAccountNo());
            customerInfo = populateCustomerInfoWithApiData(customer, loanAccInfo, loanAccDetails);
            customerBasicInfoService.saveOrUpdate(customer, username);
        }
        return customerInfo;
    }

    public LoanAccDetails populateLoanAccDetails(LoanAccDetails loanAccDetails, String accountNo) {
        return (loanAccDetails == null || loanAccDetails.getAccountNumber() == null) ?
                retailLoanUcbApiService.getLoanAccountDetails(accountNo) : loanAccDetails;
    }

    public CustomerInfo populateCustomerInfoWithApiData(@NonNull CustomerBasicInfoEntity customer, @NonNull LoanAccInfo loanAccInfo, @NonNull LoanAccDetails loanAccDetails) {
        CustomerInfo customerInfoFromReportServer = new CustomerInfo();

        if (StringUtils.hasText(loanAccInfo.getCustomerId())) {

            customerInfoFromReportServer = retailLoanUcbApiService.getCustomerInfo(loanAccInfo.getCustomerId());
            String customerId = loanAccInfo.getCustomerId();
            String customerName = loanAccInfo.getAccountName();
            String customerFatherName = customerInfoFromReportServer.getFatherName();
            String customerMotherName = customerInfoFromReportServer.getMotherName();
            String mobileNumber = customerInfoFromReportServer.getMobileNumber();
            String typeAccount = customerInfoFromReportServer.getAccountType();
            String clStatus = loanAccDetails.getStatus1();
            String occupation = customerInfoFromReportServer.getEmployer();
            String branchCode = loanAccInfo.getBranchCode();
            String sourcingChannel = loanAccInfo.getRmCode();
//            Todo: Must find source of this value for 'Branch wise CL Report'
            String region;

            customer.setCustomerId(customerId);
            customer.setCustomerName(customerName);
            customer.setCustomerFatherName(customerFatherName);
            customer.setCustomerMotherName(customerMotherName);
            customer.setMobileNumber(mobileNumber);
            customer.setTypeAccount(typeAccount);
            customer.setClStatus(clStatus);
            customer.setOccupation(occupation);
            customer.setBranchCode(branchCode);
            customer.setSourcingChannel(sourcingChannel);
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
                dateUtils.getFormattedDate("1983-06-26", "yyyy-MM-dd"); // "1983-06-26" is UCBL's establishment date
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

    public LoanAccInfo populateLoanAccInfo(LoanAccInfo loanAccInfo, String accountNo) {
        return (loanAccInfo == null || loanAccInfo.getAccountNumber() == null) ?
                retailLoanUcbApiService.getLoanAccountInfo(accountNo) : loanAccInfo;
    }


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
