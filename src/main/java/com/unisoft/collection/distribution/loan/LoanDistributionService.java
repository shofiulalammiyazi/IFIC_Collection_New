package com.unisoft.collection.distribution.loan;



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
import com.unisoft.user.UserPrincipal;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
                    }
                    //to do: need to add condition for closed accounts
//                    else if (checkAccountStatus(accountNumber)){
//                        errors.put(accountNumber, "This Account is Closed");
//                        continue;
//
//                    }
                    else if (distributionInfos.get(accountNumber) != null) {
                        errors.put(accountNumber, "Duplicate Entry");
                        continue;
                    }

                    String dealerPin = Objects.toString(row.getCell(1), "").trim();

                    if (!StringUtils.hasText(dealerPin)) {
                        errors.put(accountNumber, "No dealer found");
                        continue;
                    }

                    if (dealerPin.contains(".")) dealerPin = dealerPin.split("\\.")[0];

                    String dealerName = Objects.toString(row.getCell(2), "").trim();

                    CustomerBasicInfoEntity customerInfo = findOrSaveCustomerInfo(accountNumber);
                    if (customerInfo == null || customerInfo.getId() == null) continue;

                    LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.findOrSave(new LoanAccountBasicInfo(customerInfo));

                    LoanAccountDistributionInfo loanAccountDistributionInfo = new LoanAccountDistributionInfo();
                    loanAccountDistributionInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                    loanAccountDistributionInfo.setDealerPin(dealerPin);
                    loanAccountDistributionInfo.setDealerName(dealerName);

                    loanAccountDistributionInfo.setStatusDate(dateUtils.getMonthStartDate());
                    loanAccountDistributionService.save(loanAccountDistributionInfo);
                    distributionInfos.put(accountNumber, loanAccountDistributionInfo);

                    LoanAccountInfo loanAccountInfo = new LoanAccountInfo();
                    loanAccountInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                    loanAccountService.findOrSave(loanAccountInfo);
                    accountInfos.put(accountNumber, loanAccountInfo);

                    LoanAccountOtherInfo loanAccountOtherInfo = new LoanAccountOtherInfo();
                    loanAccountOtherInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
                    accountOtherInfos.put(accountNumber, loanAccountOtherInfo);

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    errors.put(accountNumber, "Something went wrong");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        manageBulkLoanAccountDistributionAsync(distributionInfos, accountInfos, accountOtherInfos);
        return errors;
    }

    private boolean isValidAccountForDistribution(String accountNumber) {
        boolean isAccountNumberValid = StringUtils.hasText(accountNumber) && accountNumber.length() == 16;
        if (isAccountNumberValid) {
            LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicService.getByAccountNo(accountNumber);
            if (loanAccountBasicInfo.getId() == null) {
                LoanAccInfo loanAccInfo = retailLoanUcbApiService.getLoanAccountInfo(accountNumber);
                return loanAccInfo.getCustomerId() != null;
            }
        }
        return isAccountNumberValid;
    }

    private boolean checkAccountStatus(String accountNumber) {
        LoanAccInfo loanAccInfo = retailLoanUcbApiService.getLoanAccountInfo(accountNumber);
        if(loanAccInfo.getCustomerId() == null){
            return false;
        }
        if (loanAccInfo.getAccountStatus().equals("Liquidated")){
            return true;
        }
        return false;
    }

    private CustomerBasicInfoEntity findOrSaveCustomerInfo(String accountNumber) {
        CustomerBasicInfoEntity customerInfo = new CustomerBasicInfoEntity(accountNumber);
        customerInfo.setTypeAccount("Loan");
        return customerBasicInfoService.findOrSave(customerInfo);
    }

    public void manageBulkLoanAccountDistributionAsync(Map<String, LoanAccountDistributionInfo> distributionInfos,
                                                       Map<String, LoanAccountInfo> accountInfos,
                                                       Map<String, LoanAccountOtherInfo> accountOtherInfos) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                ExecutorService executorService = Executors.newFixedThreadPool(100);

//                List<LoanAccountDistributionInfo> distributionInfos = loanAccountDistributionService.findCurrentMonthAccountDistList();

                distributionInfos.forEach((accountNumber, distributionInfo) -> {

//                    String accountNo = distributionInfo.getLoanAccountBasicInfo().getAccountNo();

                    LoanAccountInfo loanAccountInfo = accountInfos.get(accountNumber);
                    LoanAccountOtherInfo accountOtherInfo = accountOtherInfos.get(accountNumber);

                    executorService.execute(new Runnable() {

                        @Override
                        public void run() {

                            if (!loanAccountDistributionService.isValidDistribution(distributionInfo)) return;

                            String accountNumber = distributionInfo.getLoanAccountBasicInfo().getAccountNo();

                            /* get account info and details info from server for specific account */
                            LoanAccInfo accInfoFromReportServer = retailLoanUcbApiService.getLoanAccountInfo(accountNumber);
                            if (!accInfoFromReportServer.getAccountStatus().equals("Liquidated")){
                                LoanAccDetails accDetailsFormReportServer = retailLoanUcbApiService.getLoanAccountDetails(accountNumber);
                                BranchInfo branchInfoFormReportServer = retailLoanUcbApiService.getBranchInfo(accInfoFromReportServer.getBranchCode());

                                LoanAccountBasicInfo loanAccountBasicInfo = distributionInfo.getLoanAccountBasicInfo();

                                CustomerBasicInfoEntity customer = loanAccountBasicInfo.getCustomer();
                                customer.setBranchName(branchInfoFormReportServer.getBranchName());
                                CustomerInfo customerInfo = updateCustomerInfo(customer, accInfoFromReportServer, accDetailsFormReportServer, username);

                                loanAccountBasicInfo.setLocation(customerInfo.getLocation());
                                loanAccountBasicInfo = updateLoanAccountBasicInfo(loanAccountBasicInfo, accInfoFromReportServer, accDetailsFormReportServer, username);

                                updateLoanAccountDistributionInfo(distributionInfo, loanAccountBasicInfo, accDetailsFormReportServer);
                                updateLoanAccountInfo(loanAccountInfo, loanAccountBasicInfo, accDetailsFormReportServer, branchInfoFormReportServer, username);
                                updateLoanAccountOtherInfo(accountOtherInfo, loanAccountBasicInfo, accInfoFromReportServer, username);
                            }

                        }
                    });

                });
                executorService.shutdown();
            }
        });
        thread.start();
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
