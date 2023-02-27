package com.unisoft.collection.scheduler;

import com.unisoft.collection.dashboard.DashboardService;
import com.unisoft.collection.dashboard.DealerPerformanceDataService;
import com.unisoft.collection.dashboard.ProductWiseSummary;
import com.unisoft.collection.distribution.loan.LoanAccountDistributionRepository;
import com.unisoft.collection.distribution.loan.loanAccount.LoanAccountService;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicService;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.unisoft.collection.distribution.loan.loanAccountOther.LoanAccountOtherService;
import com.unisoft.customerbasicinfo.CustomerBasicInfoService;
import com.unisoft.customerloanprofile.loanpayment.LoanPayment;
import com.unisoft.customerloanprofile.loanpayment.LoanPaymentRepository;
import com.unisoft.dataStore.loanListing.LoanListingService;
import com.unisoft.loanApi.model.LoanAccDetails;
import com.unisoft.loanApi.model.LoanAccStatement;
import com.unisoft.loanApi.service.RetailLoanUcbApiService;
import com.unisoft.retail.loan.dashboard.kpi.LoanKpiTargetVsAchievementSevrice;
import com.unisoft.retail.loan.dataEntry.distribution.auto.LoanAutoDistributionService;
import com.unisoft.retail.loan.dataEntry.manualAccountWriteOff.ManualAccountWriteOffService;
import com.unisoft.retail.loan.dataEntry.ptp.LoanPtp;
import com.unisoft.retail.loan.dataEntry.ptp.LoanPtpDao;
import com.unisoft.retail.loan.dataEntry.ptp.LoanPtpRepository;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.persistence.Tuple;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableAsync
@Slf4j
@Component
@RequiredArgsConstructor
public class RetailLoanScheduledCornJob {

    private final RetailLoanUcbApiService retailLoanUcbApiService;
    private final LoanPaymentRepository loanPaymentRepository;
    private final LoanPtpDao loanPtpDao;
    private final LoanPtpRepository loanPtpRepository;
    private final LoanAccountDistributionRepository distributionRepository;
    private final LoanAccountDistributionService distributionService;
    private final LoanAutoDistributionService autoDistributionService;
    private final CustomerBasicInfoService customerBasicInfoService;
    private final LoanAccountBasicService loanAccountBasicService;
    private final LoanAccountService loanAccountService;
    private final LoanAccountOtherService loanAccountOtherService;
    private final DashboardService dashboardService;
    private final DealerPerformanceDataService dealerPerformanceDataService;
    private final RetailLoanUcbApiService apiService;
    private final LoanKpiTargetVsAchievementSevrice kpiTargetVsAchievementSevrice;
    private final DateUtils dateUtils;
    private final ManualAccountWriteOffService manualAccountWriteOffService;
    private final LoanListingService loanListingService;

    /**
     * Updates payment information for the distributed loan accounts.
     * Payment information are pulled from client API.
     * Payment info considered from last paid date or the month start date
     * whichever nearer to the current date.
     * <p>
     * At 7 April 2021
     */
    @Async
    @Scheduled(cron = "0 0 1 * * *")
    public void updateLoanPaymentStatus() {
        log.info("PTP STATUS LOAN SCHEDULER INVOKED: " + new Date().toString());
        StopWatch watch = new StopWatch();
        watch.start();

        pullLoanPaymentsFromApi();
        updatePtpStatusByPayment();

        watch.stop();
        log.error("Elapsed Time" + watch.getTotalTimeSeconds());
    }

    /**
     * Calculate and store dealer performance data. Performance is calculated from the begining of month to yesterday.
     *
     * at 19 April 2021
     */
    @Async
    @Scheduled(cron = "0 30 1 * * *")
    public void updateLoanDealerPerformanceData() {
        kpiTargetVsAchievementSevrice.updateKpiPerformanceStatusForAllDealerTillYesterday();
    }

    private void pullLoanPaymentsFromApi() {
        List<LoanPayment> paymentDetails = new LinkedList<>();
        List<Tuple> latestPaymentInfo = loanPaymentRepository.getLatestPaymentDetailsOfCurrentlyDistributedAccounts();

        Date monthStartDate = dateUtils.getMonthStartDate();
        Date yesterday = dateUtils.getNextOrPreviousDate(new Date(), -1);
        yesterday = dateUtils.getEndingPointOfDay(yesterday);

        for (Tuple latestPayment : latestPaymentInfo) {
            String accountNo = latestPayment.get("ACCOUNT_NO", String.class);
            Date lastPaymentDate = latestPayment.get("PAYMENT_DATE", Date.class);
            if (lastPaymentDate != null)
                lastPaymentDate = dateUtils.getNextOrPreviousDate(lastPaymentDate, 1);
            else
                lastPaymentDate = monthStartDate;
            List<LoanPayment> paymentList = getLoanPaymentModelsFromApi(accountNo, lastPaymentDate, yesterday);
            paymentDetails.addAll(paymentList);
        }

        loanPaymentRepository.saveAll(paymentDetails);
    }

    private List<LoanPayment> getLoanPaymentModelsFromApi(String accountNo, Date startDate, Date endDate) {
        List<LoanAccStatement> accountStatements = retailLoanUcbApiService.getLoanAccStatement(accountNo, startDate, endDate);
        List<LoanPayment> paymentList = new LinkedList<>();
        for (LoanAccStatement statement : accountStatements) {
            Date paymentDate = statement.getValueDate();
            double payment = statement.getLastPayment();
            if (payment < 1) continue;
            LoanPayment paymentDetails = new LoanPayment(paymentDate, accountNo, payment);
            paymentList.add(paymentDetails);
        }
        return paymentList;
    }

    private void updatePtpStatusByPayment() {

        Date yesterday = dateUtils.getNextOrPreviousDate(new Date(), -1);
        List<LoanPtp> loanPtpList = loanPtpDao.getLoanPtpByCustomer(yesterday);
        Map<Long, List<LoanPtp>> customerToLoanPtpMap = new HashMap<>();

        for (LoanPtp loanPtp : loanPtpList) {
            Long id = loanPtp.getCustomerBasicInfo().getId();
            boolean b = customerToLoanPtpMap.containsKey(id);
            if (b) {
                List<LoanPtp> loanPtps = customerToLoanPtpMap.get(id);
                loanPtps.add(loanPtp);
                customerToLoanPtpMap.put(id, loanPtps);
            } else {
                List<LoanPtp> loanPtps = new ArrayList<>();
                loanPtps.add(loanPtp);
                customerToLoanPtpMap.put(id, loanPtps);
            }
        }

        for (Map.Entry<Long, List<LoanPtp>> entry : customerToLoanPtpMap.entrySet()) {
            List<LoanPtp> loanPtps = entry.getValue();
            loanPtps.sort((o1, o2) -> {
                if (o1.getCreatedDate() == null || o2.getCreatedDate() == null)
                    return 0;
                return o1.getCreatedDate().compareTo(o2.getCreatedDate());
            });

//            String firstCreatedDateString = simpleDateFormat.format(loanPtps.get(0).getCreatedDate());
//            Date firstCreatedDate = null;
//            try {
//                firstCreatedDate = simpleDateFormat.parse(firstCreatedDateString);
//            } catch (ParseException e) {
//                System.out.println(e.getMessage());
//            }

            Date firstCreatedDate = loanPtps.get(0).getCreatedDate();
            double byPaymentDateBetweenAndAccountNo = loanPaymentRepository.findByPaymentDateBetweenAndAccountNo(firstCreatedDate, yesterday, loanPtps.get(0).getCustomerBasicInfo().getAccountNo());

            for (LoanPtp l : loanPtps) {
                double loanAmount = 0;
                if (l.getLoan_amount() == null) {
                    l.setLoan_ptp_status("broken");
                    loanPtpRepository.save(l);
                } else {
                    loanAmount = Double.parseDouble(l.getLoan_amount());
                    if (byPaymentDateBetweenAndAccountNo >= loanAmount) {
                        l.setLoan_ptp_status("cured");
                        byPaymentDateBetweenAndAccountNo = byPaymentDateBetweenAndAccountNo - loanAmount;
                    } else l.setLoan_ptp_status("broken");
                    loanPtpRepository.save(l);
                }


            }
        }
    }

    /**
     * @deprecated
     */
    private void updateMonthlyDealerPerformanceData() {
        // Performance is always till previous date as payment information is available till previous date
        Date previousDate = dateUtils.getNextOrPreviousDate(new Date(), -1);
        Date startDate = dateUtils.getMonthStartDate(previousDate);
        Date endDate = dateUtils.getEndingPointOfDay(previousDate);
        List<Tuple> employeeInfos = distributionRepository.findDistributedDealerDataWithinDateRange(startDate, endDate);

        ExecutorService threadPool = Executors.newFixedThreadPool(25);
        // For each dealer calculate performance data
        for (Tuple employeeInfo : employeeInfos) {
            threadPool.execute(() -> {
                String employeeId = Objects.toString(employeeInfo.get("ID"), "-1");
                String designation = Objects.toString(employeeInfo.get("DESIGNATION"), "NULL");
                String unit = Objects.toString(employeeInfo.get("UNIT"), "NULL");
                String dealerPin = Objects.toString(employeeInfo.get("PIN"), "NULL");
                List<LoanAccountDistributionInfo> distributionInfos = distributionRepository.findByCreatedDateIsBetweenAndDealerPin(startDate, endDate, dealerPin);

                Map<String, Object> summaryForDealer = dashboardService.getProductWiseSummaryForDealer(distributionInfos, employeeId, designation, unit);

                String dealerName = (String) summaryForDealer.getOrDefault("dealerName", "");
                List<ProductWiseSummary> productWiseSummaryList =
                        (List<ProductWiseSummary>) summaryForDealer.getOrDefault("productWiseSummaryList", new LinkedList<>());

                dealerPerformanceDataService.updateLoanDealerPerformanceData(productWiseSummaryList, dealerPin, dealerName);
            });

        }
        threadPool.shutdown();

    }

    /**
     * Update delinquent account list from client server at month opening.
     * Delinquent accounts are stored primarily in the distribution table without dealer pin.
     * <p>

     */
    @Async
    @Scheduled(cron = "0 30 2 1 * *")
    public void getDelenquentAccountListFromClientApi() {

        log.info("UNALLOCATED ACCOUNT LIST SCHEDULER INVOKED AT : " + new Date().toString());
        autoDistributionService.getCurrentMonthDelinquentAccountsFromClientApi();
        autoDistributionService.temporarilyDistributeDelinquentAccounts();
        manualAccountWriteOffService.getCurrentMonthManualAccountWriteOffFromApi();
    }

    /**
     * download loan listing from api.
     * <p>
     */

    @Async
    @Scheduled(cron = "1 1 3 1 * *")
    public void downloadWriteOffAccountFromApi(){
        System.out.println("start downloading write of account from api");
        manualAccountWriteOffService.getCurrentMonthManualAccountWriteOffFromApi();
    }

    /**
     * Update delinquent account list from client server at month opening.
     * Delinquent accounts are stored primarily in the distribution table without dealer pin.
     * <p>
     * at 19 April 2021
     */
    @Async
    @Scheduled(cron = "0 0 3 * * *")
    public void updateDpdBucketOfDistributedAccounts() {

        List<LoanAccDetails> allLoanAccounts = apiService.getLoanAccountDetails();

        Set<String> distributedAccountNumbers = distributionRepository.getCurrentMonthDistributedAccountNumbers();

        allLoanAccounts.parallelStream()
                .forEach(accountDetails -> {
                    if (distributedAccountNumbers.contains(accountDetails.getAccountNumber()))
                        distributionRepository.updateCurrentBucketRelatedStatus(
                                accountDetails.getAccountNumber(),
                                Double.toString(accountDetails.getDpdBucket()),
                                accountDetails.getOverdue()
                        );
                });
    }

    @Async
    @Scheduled(cron = "1 8 2 1 * *")
    public  void downloadLoanListing(){
        log.info("Download Loan Listing "+new Date().toString());
        loanListingService.storeDataFromApi();
    }

//    private void updatePreviousMonthDealerPerformanceDate() {
//        // Find previous month distributed dealer data (employee id, pin, designation and unit)
//        Date startDate = dateManager.getMonthStartDate(-1);
//        Date endDate = dateManager.getMonthEndDate(-1);
//        List<Tuple> dealers = distributionRepository.findDistributedDealerDataWithinDateRange(startDate, endDate);
//
//        ExecutorService threadPool = Executors.newFixedThreadPool(25);
//        // For each dealer calculate performance data
//        for (Tuple dealer : dealers) {
//            threadPool.execute(() -> {
//                String employeeId = Objects.toString(dealer.get("ID"), "-1");
//                String designation = Objects.toString(dealer.get("DESIGNATION"), "NULL");
//                String unit = Objects.toString(dealer.get("UNIT"), "NULL");
//                String dealerPin = Objects.toString(dealer.get("PIN"), "NULL");
//                List<LoanAccountDistributionInfo> previousMonthDistributionByDealer = distributionRepository.findByCreatedDateIsBetweenAndDealerPin(startDate, endDate, dealerPin);
//
//                Map<String, Object> summaryForDealer = dashboardService.getProductWiseSummaryForDealer(previousMonthDistributionByDealer, employeeId, designation, unit);
//
//                String dealerName = (String) summaryForDealer.getOrDefault("dealerName", "");
//                List<ProductWiseSummary> productWiseSummaryList =
//                        (List<ProductWiseSummary>) summaryForDealer.getOrDefault("productWiseSummaryList", new LinkedList<>());
//
//                dealerPerformanceDataService.updateLoanDealerPerformanceData(productWiseSummaryList, dealerPin, dealerName);
//            });
//
//        }
//        threadPool.shutdown();
//
//    }

//    @Async
//    @Scheduled(cron = "0 0 3 * * ?")
//    public void upDateLoanPtpStatusAndDelenquentAccount() {
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -1);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String format = simpleDateFormat.format(new Date(cal.getTimeInMillis()));
//        Date startDate = null;
//        try {
//            startDate = simpleDateFormat.parse(format);
//        } catch (ParseException e) {
//            System.out.println(e.getMessage());
//        }
////
////        List<LoanPayment> loanPayments = loanPaymentRepository.findByThoseAccountWhoPaid();
////
////        for (LoanPayment l : loanPayments) {
////            Double loanPayment = loanPaymentDao.getLoanPayment(l.getAccountNo());
////            if (loanPayment > l.getAmount()) {
////                double payment = loanPayment - l.getAmount();
////                LoanPaymentDetails loanPaymentDetails = new LoanPaymentDetails(startDate, l.getAccountNo(), payment);
////                l.getLoanPaymentDetails().add(loanPaymentDetails);
////            }
////            l.setAmount(loanPayment);
////            loanPaymentRepository.save(l);
////
////        }
//
//        List<LoanPtp> loanPtpList = loanPtpDao.getLoanPtpByCustomer(startDate);
//        Map<Long, List<LoanPtp>> customerToLoanPtpMap = new HashMap<>();
//
//        for (LoanPtp loanPtp : loanPtpList) {
//            Long id = loanPtp.getCustomerBasicInfo().getId();
//            boolean b = customerToLoanPtpMap.containsKey(id);
//            if (b) {
//                List<LoanPtp> loanPtps = customerToLoanPtpMap.get(id);
//                loanPtps.add(loanPtp);
//                customerToLoanPtpMap.put(id, loanPtps);
//            } else {
//                List<LoanPtp> loanPtps = new ArrayList<>();
//                loanPtps.add(loanPtp);
//                customerToLoanPtpMap.put(id, loanPtps);
//            }
//        }
//
//        for (Map.Entry<Long, List<LoanPtp>> entry : customerToLoanPtpMap.entrySet()) {
//            List<LoanPtp> loanPtps = entry.getValue();
//            loanPtps.sort((o1, o2) -> {
//                if (o1.getCreatedDate() == null || o2.getCreatedDate() == null)
//                    return 0;
//                return o1.getCreatedDate().compareTo(o2.getCreatedDate());
//            });
//            String firstCreatedDateString = simpleDateFormat.format(loanPtps.get(0).getCreatedDate());
//            Date firstCreatedDate = null;
//            try {
//                firstCreatedDate = simpleDateFormat.parse(firstCreatedDateString);
//            } catch (ParseException e) {
//                System.out.println(e.getMessage());
//            }
//
//            double byPaymentDateBetweenAndAccountNo = loanPaymentRepository.findByPaymentDateBetweenAndAccountNo(firstCreatedDate, startDate, loanPtps.get(0).getCustomerBasicInfo().getAccountNo());
//
//            for (LoanPtp l : loanPtps) {
//                double loanAmount = 0;
//                if (l.getLoan_amount() == null) {
//                    l.setLoan_ptp_status("broken");
//                    loanPtpRepository.save(l);
//                } else {
//                    loanAmount = Double.parseDouble(l.getLoan_amount());
//                    if (byPaymentDateBetweenAndAccountNo >= loanAmount) {
//                        l.setLoan_ptp_status("cured");
//                        byPaymentDateBetweenAndAccountNo = byPaymentDateBetweenAndAccountNo - loanAmount;
//                    } else l.setLoan_ptp_status("broken");
//                    loanPtpRepository.save(l);
//                }
//
//
//            }
//        }
//    }

//    @Async
//    @Scheduled(cron = "0 0 4 * * ?")
//    public void getDelenquentAccountLoan() {
//        log.info("DELENQUENT LOAN ACCOUNT SCHEDULER INVOKED: " + new Date().toString());
//        return;
////        List<DelenquentAccount> delinquentAccountList = loanPaymentDao.getDelenquentAccount();
////
////        for(DelenquentAccount d: delinquentAccountList){
////            LoanAccountBasicInfo loanAccountBasicInfo = loanAccountBasicRepository.findByAccountNo(d.getAccountNo());
////            if(loanAccountBasicInfo == null){
////                CustomerBasicInfoEntity customerBasicInfoEntity = new CustomerBasicInfoEntity("", d.getAccountNo(), "","", "", "", "Loan");
////                customerBasicInfoEntityRepository.save(customerBasicInfoEntity);
////
////                LoanAccountBasicInfo loanAccountBasicInfo1 = new LoanAccountBasicInfo(d.getAccountNo(), "", null, 0,"", "", customerBasicInfoEntity);
////                loanAccountBasicRepository.save(loanAccountBasicInfo1);
////
////                UnallocatedLoanList firstByLoanAccountBasicInfo = unallocatedLoanListRepository.findFirstByLoanAccountBasicInfo(loanAccountBasicInfo1);
////                if(firstByLoanAccountBasicInfo == null){
////                    UnallocatedLoanList unallocatedLoanList = new UnallocatedLoanList();
////                    unallocatedLoanList.setLoanAccountBasicInfo(loanAccountBasicInfo1);
////                    unallocatedLoanList.setDpd((float)d.getDpd());
////                    unallocatedLoanList.setOutStanding(d.getOutstanding()+"");
////                    unallocatedLoanList.setDealerPin("0");
////                    unallocatedLoanList.setDealerName("0");
////                    unallocatedLoanList.setSupervisorPin("0");
////                    unallocatedLoanList.setSupervisorName("0");
////                    unallocatedLoanList.setSamAccount("0");
////                    unallocatedLoanList.setWriteOffAccount("0");
////                    unallocatedLoanList.setCreatedDate(new Date());
////                    unallocatedLoanList.setProductGroup(d.getSchemeCode());
////                    unallocatedLoanList.setOpeningOverDue(d.getOverDue());
////                    unallocatedLoanListRepository.save(unallocatedLoanList);
////                }
////                LoanAccountOtherInfo loanAccountOtherInfo = new LoanAccountOtherInfo("", "", "","", loanAccountBasicInfo1);
////                loanAccountOtherRepository.save(loanAccountOtherInfo);
////                LoanAccountInfo loanAccountInfo = new LoanAccountInfo(d.getSchemeCode(), 0, 0, d.getAte(), "", d.getOverDue(), 0, d.getDpd(), "", "", "", "", "", loanAccountBasicInfo1);
////                loanAccountRepository.save(loanAccountInfo);
////            }else{
////                UnallocatedLoanList firstByLoanAccountBasicInfo = unallocatedLoanListRepository.findFirstByLoanAccountBasicInfo(loanAccountBasicInfo);
////                if(firstByLoanAccountBasicInfo == null){
////                    UnallocatedLoanList unallocatedLoanList = new UnallocatedLoanList();
////                    unallocatedLoanList.setLoanAccountBasicInfo(loanAccountBasicInfo);
////                    unallocatedLoanList.setDpd((float)d.getDpd());
////                    unallocatedLoanList.setOutStanding(d.getOutstanding()+"");
////                    unallocatedLoanList.setDealerPin("0");
////                    unallocatedLoanList.setDealerName("0");
////                    unallocatedLoanList.setSupervisorPin("0");
////                    unallocatedLoanList.setSupervisorName("0");
////                    unallocatedLoanList.setSamAccount("0");
////                    unallocatedLoanList.setWriteOffAccount("0");
////                    unallocatedLoanList.setCreatedDate(new Date());
////                    unallocatedLoanList.setProductGroup(d.getSchemeCode());
////                    unallocatedLoanList.setOpeningOverDue(d.getOverDue());
////                    unallocatedLoanListRepository.save(unallocatedLoanList);
////                }
////            }
////        }
//
//    }

//    @Async
//    @Scheduled(cron = "0 0 1 1 1/1 ?")
//    public void getDelenquentAccountFromLoanAccountDistributionInfo() {
////        loanPaymentDao.deletePreviousMonthUnallocatedData();
//        List<LoanAccountDistributionInfo> loanAccountDistributionInfos = loanAccountDistributionRepository.findByCreatedDateIsBetweenAndSamAccountAndWriteOffAccountAndLatestOrderByCreatedDateDesc(getStartDate(), getEndDate(), "0", "0", "1");
//        for (LoanAccountDistributionInfo l : loanAccountDistributionInfos) {
//            UnallocatedLoanList firstByLoanAccountBasicInfo = unallocatedLoanListRepository.findFirstByLoanAccountBasicInfo(l.getLoanAccountBasicInfo());
//            if (firstByLoanAccountBasicInfo == null) {
//                UnallocatedLoanList unallocatedLoanList = new UnallocatedLoanList();
//                unallocatedLoanList.setLoanAccountBasicInfo(l.getLoanAccountBasicInfo());
//                unallocatedLoanList.setDpdBucket(l.getDpdBucket());
//                unallocatedLoanList.setDpd(l.getDpd().floatValue());
//                unallocatedLoanList.setOpeningOverDue(l.getOpeningOverDue());
//                unallocatedLoanList.setOutStanding(l.getOutStanding());
//                unallocatedLoanList.setDealerPin("0");
//                unallocatedLoanList.setDealerName("0");
//                unallocatedLoanList.setSupervisorPin("0");
//                unallocatedLoanList.setSupervisorName("0");
//                unallocatedLoanList.setSamAccount("0");
//                unallocatedLoanList.setWriteOffAccount("0");
//                unallocatedLoanList.setCreatedDate(new Date());
//                unallocatedLoanList.setProductGroup(l.getProductGroup());
//                unallocatedLoanList.setSchemaCode(l.getSchemeCode());
//                unallocatedLoanListRepository.save(unallocatedLoanList);
//            }
//
//
//        }
//    }

}
