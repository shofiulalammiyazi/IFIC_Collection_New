package com.unisoft.collection.distribution.loan;

import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountInfo;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountService;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherInfo;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.loanApi.model.CustomerInfo;
import com.unisoft.loanApi.model.LoanAccDetails;
import com.unisoft.loanApi.model.LoanAccInfo;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



@RequiredArgsConstructor
@Service
public class LoanService {

    private final DateUtils dateUtils;

    private final RetailLoanUcbApiService retailLoanUcbApiService;

    private final LoanAccountService loanAccountService;

    private final LoanAccountOtherService loanAccountOtherService;

    private final LoanAccountBasicService loanAccountBasicService;

    private final CustomerBasicInfoService customerBasicInfoService;

    private final LoanAccountDistributionService loanAccountDistributionService;

    private final CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    /* Save information of loan */
    public void saveLoan(List<String> accounts) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                /* Get All Customer IDs by Accounts */
                List<String> customerIds = customerBasicInfoEntityRepository.findCustomerIdsByAccounts(accounts);
                ExecutorService executorService = Executors.newFixedThreadPool(100);

                for (String accountNumber : accounts) {

                    /* get account info and details info from server for specific account */
                    LoanAccInfo accInfoFromReportServer = retailLoanUcbApiService.getLoanAccountInfo(accountNumber);
                    LoanAccDetails accDetailsFormReportServer = retailLoanUcbApiService.getLoanAccountDetails(accountNumber);

                    executorService.execute(new Runnable() {

                        @Override
                        public void run() {
                            String customerId = accInfoFromReportServer.getCustomerId();
                            CustomerBasicInfoEntity customer = null;
                            if (!customerIds.contains(customerId)) {
                                /* if customer id doesn't exists in vendors server create new customer */
                                String productDesc = accDetailsFormReportServer.getProductDesc();
                                customer = updateCustomerInfo(accountNumber, customerId, productDesc);
                            } else {
                                /* if customer id exists in vendors server just update loan account info*/
                                customer = customerBasicInfoEntityRepository.findByCustomerId(customerId);
                            }

                            /* after updating customer information
                             * Update all other data related with loan accounts */
                            LoanAccountBasicInfo loanAccountBasicInfo = updateLoanAccountBasicInfo(accInfoFromReportServer, customer);
                            updateLoanAccountDistributionInfo(accDetailsFormReportServer, loanAccountBasicInfo);
                            updateLoanAccountInfo(loanAccountBasicInfo);
                            updateLoanAccountOtherInfo(loanAccountBasicInfo);
                        }
                    });
                }
                executorService.shutdown();
            }
        });
        thread.start();
    }

    /* Method to save a new customer */
    public CustomerBasicInfoEntity updateCustomerInfo(String accountNumber, String customerId, String productDesc) {
        CustomerInfo customerInfoFromReportServer = retailLoanUcbApiService.getCustomerInfo(customerId);

        String accountName = customerInfoFromReportServer.getAcDesc();
        String mobileNumber = customerInfoFromReportServer.getMobileNumber();
        String accountType = productDesc;
        String fatherName = customerInfoFromReportServer.getFatherName();
        String motherName = customerInfoFromReportServer.getMotherName();

        CustomerBasicInfoEntity customerInfo = new CustomerBasicInfoEntity(
                customerId, accountNumber, accountName,
                fatherName, motherName, mobileNumber, accountType
        );

        customerBasicInfoService.saveOrUpdate(customerInfo);
        return customerInfo;
    }

    /* if data can be found, have to update/modify this method.
     * Parameter list can be changed
     * */
    public LoanAccountBasicInfo updateLoanAccountBasicInfo(LoanAccInfo accInfoFromReportServer, CustomerBasicInfoEntity customer) {

        // TODO: if required data provided in future, have to change it ;
        String location = "-";
        Date expiryDate = new Date();
        String accountName = customer.getCustomerName();
        String linkAccountNumber = accInfoFromReportServer.getAltAccNo();
        Double disburseAmount = accInfoFromReportServer.getDisburseLoanAmount();
        Date disbursementDate = dateUtils.getFormattedDate("dd-MMM-yyyy", accInfoFromReportServer.getDisbursmentDt());

        LoanAccountBasicInfo loanAccountBasicInfo = new LoanAccountBasicInfo(location, expiryDate, customer);
        loanAccountBasicInfo.setAccountName(accountName);
        loanAccountBasicInfo.setLinkAccountNumber(linkAccountNumber);
        loanAccountBasicInfo.setDisbursementAmount(disburseAmount);
        loanAccountBasicInfo.setDisbursementDate(disbursementDate);

        loanAccountBasicService.saveOrUpdate(loanAccountBasicInfo);
        return loanAccountBasicInfo;
    }

    /* if data can be found, have to update/modify this method.
     * Parameter list can be changed
     * */
    public LoanAccountDistributionInfo updateLoanAccountDistributionInfo(LoanAccDetails accDetailsFromReportServer, LoanAccountBasicInfo loanAccountBasicInfo) {
        // TODO: if required data provided in future, have to change it ;
        String schemeCode = "-";
        Double totalOverdue = 0d;
        Double outStanding = 0d;
        Date disbursementDate = new Date();
        Double dpd = 0d;
        String dpdBucket = "-";
        String productGroup = "-";
        String dealerPin = "-";

        Double emiAmount = accDetailsFromReportServer.getEmiAmount();
        String emiDate = accDetailsFromReportServer.getUserDefineEmi();
        Double lastPayment = accDetailsFromReportServer.getAmountPaidLastMnth();
        Date lastPaymentDate = dateUtils.getFormattedDate(accDetailsFromReportServer.getLastPaymentDate(), "dd-MM-yyyy");
        Double totalPayment = accDetailsFromReportServer.getTotalAmountPaid();

        LoanAccountDistributionInfo loanAccountDistributionInfo = new LoanAccountDistributionInfo(schemeCode,
                totalOverdue, outStanding, disbursementDate, dpd, dpdBucket, productGroup, dealerPin);

        loanAccountDistributionInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
        loanAccountDistributionInfo.setEmiAmount(emiAmount);
        loanAccountDistributionInfo.setEmiDueDate(emiDate);
        loanAccountDistributionInfo.setLastPaidAmount(lastPayment);
        loanAccountDistributionInfo.setLastPaymnetDate(lastPaymentDate);
        loanAccountDistributionInfo.setTotalPayment(totalPayment);

        loanAccountDistributionService.save(loanAccountDistributionInfo);
        return loanAccountDistributionInfo;
    }

    /* if data can be found, have to update/modify this method.
     * Parameter list can be changed
     * */
    public LoanAccountInfo updateLoanAccountInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        // TODO: if required data provided in future, have to change it ;
        Double ate = 0d;
        String assetClass = "-";
        Double installmentAmount = 0d;
        String schemeCode = "-";
        Double totalOverdue = 0d;

        Double outStanding = 0d;
        Double dpd = 0d;
        String emiDueDate = "-";
        String dpdBucket = "-";
        String branchName = "-";

        LoanAccountInfo loanAccountInfo = new LoanAccountInfo();

        loanAccountInfo.setAte(ate);
        loanAccountInfo.setAssetClassification(assetClass);
        loanAccountInfo.setInstallmentAmount(installmentAmount);
        loanAccountInfo.setSchemeCode(schemeCode);
        loanAccountInfo.setTotalOverdue(totalOverdue);

        loanAccountInfo.setTotalOutstanding(outStanding);
        loanAccountInfo.setDpd(dpd);
        loanAccountInfo.setEmiDueDate(emiDueDate);
        loanAccountInfo.setDpbBucket(dpdBucket);
        loanAccountInfo.setBranchName(branchName);
        loanAccountInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
        loanAccountService.saveOrUpdate(loanAccountInfo);

        return loanAccountInfo;
    }

    /* if data can be found, have to update/modify this method.
     * Parameter list can be changed
     * */
    public LoanAccountOtherInfo updateLoanAccountOtherInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        // TODO: if required data provided in future, have to change it ;
        String status = "-";

        LoanAccountOtherInfo loanAccountOtherInfo = new LoanAccountOtherInfo();
        loanAccountOtherInfo.setNotificationStatus(status);
        loanAccountOtherInfo.setLoanAccountBasicInfo(loanAccountBasicInfo);
        loanAccountOtherService.saveOrUpdate(loanAccountOtherInfo);

        return loanAccountOtherInfo;
    }
}
