package com.unisoft.collection.dashboard;

import com.unisoft.collection.KPI.Card.TargetByManager.DealerTargetCardManager;
import com.unisoft.collection.KPI.Loan.TargetByAmount.LoanKPITargetByAmountEntity;
import com.unisoft.collection.KPI.Loan.TargetByAmount.LoanKPITargetByAmountService;
import com.unisoft.collection.KPI.Loan.TargetWeightByAmount.LoanKPITargetWeightByAmountEntity;
import com.unisoft.collection.KPI.Loan.TargetWeightByAmount.LoanKPITargetWeightByAmountService;
import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.retail.card.dashboard.RetailCardDashBoardRepository;
import com.unisoft.retail.card.dataEntry.cardPayment.CardPaymentSummaryModel;
import com.unisoft.retail.card.dataEntry.distribution.agency.CardAgencyDistributionInfo;
import com.unisoft.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.unisoft.collection.settings.dpdBucket.DPDBucketEntity;
import com.unisoft.collection.settings.dpdBucket.DpdBucketRepository;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;

import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.customerloanprofile.loanpayment.LoanPayment;
import com.unisoft.customerloanprofile.loanpayment.LoanPaymentRepository;
import com.unisoft.retail.loan.dataEntry.ptp.LoanPtp;
import com.unisoft.retail.loan.dataEntry.ptp.PtpStatusSummary;
import com.unisoft.retail.card.dataEntry.ptp.CardPtp;
import com.unisoft.utillity.DashboardHelper;
import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardDao dashboardDao;
    private final LoanPaymentRepository loanPaymentRepository;
    private final DashboardHelper dashboardHelper;
    private final DpdBucketRepository dpdBucketRepository;
    private final LoanKPITargetByAmountService loanKPITargetByAmountService;
    private final LoanKPITargetWeightByAmountService loanKPITargetWeightByAmountService;
    private final DateUtils dateUtils;
    private final RetailCardDashBoardRepository retailCardDashBoardRepository;

    private static final int MAX_NUMBER_OF_CHUNK = 10;
    private static final int MAX_NUMBER_OF_ITEM_IN_ONE_QUERY = 10000;

    public List<CardAccountDistributionInfo> getAccountDistInfoAllUserCurrentmonth() {
        return dashboardDao.getAccountDistInfoAllUserCurrentmonth();
    }

    public List<PeopleAllocationLogicInfo> getProductsForAccWiseSummary(String designation, String userPin, String unit, String userId) {
        return dashboardDao.AccountWiseSummaryGetProducts(designation, userPin, unit, userId);
    }

    public List<LoanAccountDistributionInfo> getAccountListByUser(String userPin) {
        return dashboardDao.getAccountListByUser(userPin);
    }

    public Long getNumberOfAcPerPt(String userPin, String productCode) {
        return dashboardDao.getNumberOfAcPerPt(userPin, productCode);
    }

    public double getAmountPerPt(String userPin, String productCode) {
        return dashboardDao.getTotalAmountPerPt(userPin, productCode);
    }

    public double getTotalAmountPerPtCard(String userPin, String productCode) {
        return dashboardDao.getTotalAmountPerPtCard(userPin, productCode);
    }

    public List<CardAccountDistributionInfo> getListBYUserPinCard(String userPin) {
        return dashboardDao.getListBYUserPinCard(userPin);
    }

    public Long getNumberOfAcPerPtCard(String userPin, String productCode) {
        return dashboardDao.getNumberOfAcPerPtCard(userPin, productCode);
    }

    public double getTotalMinDueAmntPerPtCard(String userPin, String productCode) {
        return dashboardDao.getTotalMinDueAmntPerPtCard(userPin, productCode);
    }

    public List<PeopleAllocationLogicInfo> getAllDealerList(String userId, String designation, String unit) {
        return dashboardDao.getAllDealerList(userId, designation, unit);
    }

    public List<CardPtp> getCardPtpByCustomer(Long custId) {
        return dashboardDao.getCardPtpByCustomer(custId);
    }

    public Long getTotalDailyNoteNumber(Long cusId, String userId, String unit) {
        return dashboardDao.getTotalDailyNoteNumber(cusId, userId, unit);
    }

    public Long getTotalFollowUpNumber(Long cusId) {
        return dashboardDao.getTotalFollowUpNumber(cusId);
    }

    public Long getTotalDiaryNoteNumber(Long cusId, String userid, String unit) {
        return dashboardDao.getTotalDiaryNoteNumber(cusId, userid, unit);
    }

    public DealerTargetCardManager getBySuperVisor(EmployeeInfoEntity superVisor) {
        return dashboardDao.getBySuperVisor(superVisor);
    }

    public List<LoanPtp> getLoanPtpByCustomer(Long cusId) {
        return dashboardDao.getLoanPtpBYUser(cusId);
    }

    public List<PtpStatusSummary> getLoanPtpStatusSummary(List<Long> accNoList) {
        return dashboardDao.getLoanPtpStatusSummary(accNoList);
    }


    public List<CardPaymentSummaryModel> getCategorizedPaymentSummary(List<String> dealerPins, Date startDate, Date endDate) {

        Map<String, CardPaymentSummaryModel> categoryWiseMap = new LinkedHashMap<>();

        // Ensure all the user defined categories available in list
        categoryWiseMap.put("Full amount paid", new CardPaymentSummaryModel("Full amount paid"));
        categoryWiseMap.put("Partial paid but forward flow to upper bucket", new CardPaymentSummaryModel("Partial paid but forward flow to upper bucket"));
        categoryWiseMap.put("No payment during the month", new CardPaymentSummaryModel("No payment during the month"));

        // Get category wise payment summary
        List<Tuple> data = retailCardDashBoardRepository.getCategorizedPaymentSummary(dealerPins, startDate, endDate);
        for (Tuple paymentInfo : data) {
            CardPaymentSummaryModel paymentStatusModel = new CardPaymentSummaryModel();
            paymentStatusModel.setFieldValuesFromTuple(paymentInfo);
            categoryWiseMap.put(paymentStatusModel.getCategory(), paymentStatusModel);
        }

        return new LinkedList<>(categoryWiseMap.values());
    }







    // Retrieve current month Rfd status summary for all
    public Rfd getRfdSummaryByCustomer(String unit, HttpSession session) {
        Map ptpSummaryParameter = dashboardHelper.getJpqlQueryParameter(unit, session);

        Rfd rfd = new Rfd();

        List<Long> customerIds = (List<Long>) ptpSummaryParameter.get("customerIds");
        int numberOfChunk = (int) ptpSummaryParameter.get("numberOfChunk");

        if (numberOfChunk > MAX_NUMBER_OF_CHUNK) {
            rfd = getRfdSummaryWithMaximumNumberOfChunk(customerIds, unit, numberOfChunk);
        } else {
            String jpqlQueryCardMenu = createRfdJpqlQuery(unit, numberOfChunk, "lp.card_menu", "lp.loan_menu");
            String jpqlQuerySubMenu1 = createRfdJpqlQuery(unit, numberOfChunk, "lp.card_submenu_one", "lp.loan_submenu_one");
            String jpqlQuerySubMenu2 = createRfdJpqlQuery(unit, numberOfChunk, "lp.card_submenu_two", "lp.loan_submenu_two");
            String jpqlQuerySubMenu3 = createRfdJpqlQuery(unit, numberOfChunk, "lp.card_submenu_three", "lp.loan_submenu_three");

            List<RfdMenuModel> menuList = dashboardDao.getRfdSummary(customerIds, jpqlQueryCardMenu, numberOfChunk);
            List<RfdMenuModel> subMenu1List = dashboardDao.getRfdSummary(customerIds, jpqlQuerySubMenu1, numberOfChunk);
            List<RfdMenuModel> subMenu2List = dashboardDao.getRfdSummary(customerIds, jpqlQuerySubMenu2, numberOfChunk);
            List<RfdMenuModel> subMenu3List = dashboardDao.getRfdSummary(customerIds, jpqlQuerySubMenu3, numberOfChunk);

            rfd.setRfdMenuList(menuList);
            rfd.setSubMenu1List(subMenu1List);
            rfd.setSubMenu2List(subMenu2List);
            rfd.setSubMenu3List(subMenu3List);
        }

        return rfd;
    }


    private Rfd getRfdSummaryWithMaximumNumberOfChunk(List<Long> customerIds, String unit, int numberOfChunk) {
        Rfd rfd = new Rfd();
        int totalNumberOfQueryExecute = getTotalNumberOfQueryExecute(numberOfChunk);

        int startIndex = 0, endIndex = 0;
        int remainingNumberOfChunk = numberOfChunk;

        for (int i = 1; i <= totalNumberOfQueryExecute; i++) {

            int numberOfChunkWillBeExecute = remainingNumberOfChunk > MAX_NUMBER_OF_CHUNK ? MAX_NUMBER_OF_CHUNK : remainingNumberOfChunk;

            String jpqlQueryCardMenu = createRfdJpqlQuery(unit, numberOfChunkWillBeExecute, "lp.card_menu", "lp.loan_menu");
            String jpqlQuerySubMenu1 = createRfdJpqlQuery(unit, numberOfChunkWillBeExecute, "lp.card_submenu_one", "lp.loan_submenu_one");
            String jpqlQuerySubMenu2 = createRfdJpqlQuery(unit, numberOfChunkWillBeExecute, "lp.card_submenu_two", "lp.loan_submenu_two");
            String jpqlQuerySubMenu3 = createRfdJpqlQuery(unit, numberOfChunkWillBeExecute, "lp.card_submenu_three", "lp.loan_submenu_three");

            endIndex = Math.min(i * MAX_NUMBER_OF_ITEM_IN_ONE_QUERY, customerIds.size());
            final List<Long> ids = customerIds.subList(startIndex, endIndex);

            startIndex = endIndex;
            remainingNumberOfChunk -= MAX_NUMBER_OF_CHUNK;

            List<RfdMenuModel> menuList = dashboardDao.getRfdSummary(ids, jpqlQueryCardMenu, numberOfChunkWillBeExecute);
            List<RfdMenuModel> subMenu1List = dashboardDao.getRfdSummary(ids, jpqlQuerySubMenu1, numberOfChunkWillBeExecute);
            List<RfdMenuModel> subMenu2List = dashboardDao.getRfdSummary(ids, jpqlQuerySubMenu2, numberOfChunkWillBeExecute);
            List<RfdMenuModel> subMenu3List = dashboardDao.getRfdSummary(ids, jpqlQuerySubMenu3, numberOfChunkWillBeExecute);

            rfd.getRfdMenuList().addAll(menuList);
            rfd.getSubMenu1List().addAll(subMenu1List);
            rfd.getSubMenu2List().addAll(subMenu2List);
            rfd.getSubMenu3List().addAll(subMenu3List);
        }

        return rfd;
    }

    private int getTotalNumberOfQueryExecute(int numberOfChunk) {
        int totalNumberOfQueryExecute = numberOfChunk / MAX_NUMBER_OF_CHUNK;
        if (numberOfChunk % MAX_NUMBER_OF_CHUNK > 0) totalNumberOfQueryExecute++;
        return totalNumberOfQueryExecute;
    }

    private String createRfdJpqlQuery(String unit, int queryCount, String queryStringForCard, String queryStringForLoan) {
        String inClauseUpdateQuery = getInClauseUpdateQuery(queryCount);
        String queryString = unit.equalsIgnoreCase("Loan") ? queryStringForLoan : queryStringForCard;

        String card = "select new com.unisoft.collection.dashboard.RfdMenuModel(" +
                "lower(" + queryString + ") , count(distinct lp.customerBasicInfo.id)" +
                ") from DairyNotes lp " +
                "where TO_DATE(lp.card_date, 'dd-mm-yyyy')>= :startDate and (" + inClauseUpdateQuery + ") group by lower(" + queryString + ")";

        String loan = "select new com.unisoft.collection.dashboard.RfdMenuModel(" +
                "lower(" + queryString + ") , count(distinct lp.customerBasicInfo.id)" +
                ") from DairyNotesLoan lp " +
                "where TO_DATE(lp.loan_date, 'dd-mm-yyyy')>= :startDate and (" + inClauseUpdateQuery + ") group by lower(" + queryString + ")";

        return unit.equalsIgnoreCase("Loan") ? loan : card;
    }

    private String getInClauseUpdateQuery(int queryCount) {
        String inClause = "";

        for (int i = 1; i <= queryCount; i++) {
            inClause = inClause + "lp.customerBasicInfo.id IN(:custIdList" + i + ")";
            if (i < queryCount) inClause = inClause + " or ";
        }
        return inClause;
    }

    public List<String> getAgencyNameLoan() {
        return dashboardDao.getAgencyNameLoan();
    }

    public List<String> getAgencyNameCard() {
        return dashboardDao.getAgencyNameCard();
    }

    public List<LoanAgencyDistributionInfo> getAgencyWiseDistList(String agencyName) {
        return dashboardDao.getAgencyWiseDistList(agencyName);
    }

    public List<LoanAgencyDistributionInfo> getAllAgencyDistLoan() {
        return dashboardDao.getAllAgencyDistLoan();
    }

    public List<CardAgencyDistributionInfo> getAllAgencyDistCard() {
        return dashboardDao.getAllAgencyDistCard();
    }

    public int getTotalLoggedInUser(String unit) {
        return dashboardDao.getTotalLoggedInUser(unit);
    }

    public int getVisitLedgerNo(Date visitDate, String unit) {
        return dashboardDao.getVisitLedgerNo(visitDate, unit);
    }

    public List<ResourceDetailModel> getNoOfLateEmp(String unit) {
        return dashboardDao.getNoOfLateEmp(unit);
    }

    public long getTotalVisitLedgerNoteByDealer(String userId, String unit,
                                                String startTime,
                                                String endTIme,
                                                Date selectedDate) {
        return dashboardDao.getTotalVisitLedgerNoteByDealer(userId, unit,
                startTime,
                endTIme,
                selectedDate);
    }

    public long getTotalDailyNoteByDealer(String userId, String unit,
                                          String startTime,
                                          String endTIme,
                                          Date selectedDate) {
        return dashboardDao.getTotalDailyNoteByDealer(userId, unit,
                startTime,
                endTIme,
                selectedDate);
    }

    public long getTotalDairyNoteByDealer(String userId, String unit,
                                          String startTime,
                                          String endTIme,
                                          Date selectedDate) {
        return dashboardDao.getTotalDairyNoteByDealer(userId, unit, startTime, endTIme, selectedDate);
    }

    public long getTotalHotNoteNoteByDealer(String userId, String unit,
                                            String startTime,
                                            String endTIme,
                                            Date selectedDate) {
        return dashboardDao.getTotalHotNoteNoteByDealer(userId, unit, startTime, endTIme, selectedDate);
    }

    public long getTotalPtpNoteByDealer(String userId, String unit,
                                        String startTime,
                                        String endTIme,
                                        Date selectedDate) {
        return dashboardDao.getTotalPtpNoteByDealer(userId, unit, startTime, endTIme, selectedDate);
    }

    public LoanAccountDistributionInfo getDistInfoByLoanBasicInfo(LoanAccountBasicInfo basicInfo) {
        return dashboardDao.getDistInfoByLoanBasicInfo(basicInfo);
    }

    public List<LoanPayment> getLoanPaymentByAcc(String accNo, Date startDate) {
        return loanPaymentRepository.findByAccountNoAndPaymentDateIsAfter(accNo, startDate);
    }

    public List<CardPaymentModel> getCardPaymentByAcc(String cardNo, Date date) {
        return dashboardDao.getCardPaymentByAcc(cardNo, date);
    }

    public List<CardAccountDistributionInfo> getSecuredCard(String userPin) {
        return dashboardDao.getSecuredCard(userPin);
    }

    public long getNoOfAccPerAgeCode(String userPin, String ageCode) {
        return dashboardDao.getNoOfAccPerAgeCode(userPin, ageCode);
    }

    public CardPaymentModel getCardPaymentByAccLastPayment(String cardNo, Date date) {
        return dashboardDao.getCardPaymentByAccLastPayment(cardNo, date);
    }

    public int getVisitLedgerByAcc(String accNo, String unit, String dealerPin) {
        return dashboardDao.getVisitLedgerByAcc(accNo, unit, dealerPin);
    }

    public LoanAccountDpd getCurrentDpd(String accNo) {
        return dashboardDao.getCurrentDpd(accNo);
    }

    public List<CardDetailPaymentModel> getCardDetailedPaymentInfo(String cardNo, Date date, int MO_ageCode) {
        return dashboardDao.getCardDetailedPaymentInfo(cardNo, date, MO_ageCode);
    }

    public long getNoOfHotNotesByCustomerId(Long customrId, String unit, String userId) {
        return dashboardDao.getNoOfHotNotesByCustomerId(customrId, unit, userId);
    }

    public CardDetailPaymentModel getCardDetailedPaymentInfoForUnPaidThisMonth(String cardNo, int MO_ageCode) {
        return dashboardDao.getCardDetailedPaymentInfoForUnPaidThisMonth(cardNo, MO_ageCode);
    }

    public List<CardPaymentModel> getCardPaymentAmntOnlyByCardNo(String cardNo, Date date) {
        return dashboardDao.getCardPaymentAmntOnlyByCardNo(cardNo, date);
    }

    public String getCardCusMblByCusId(String cusId) {
        return dashboardDao.getCardCusMblByCusId(cusId);
    }


    public List<CardAccountDistributionInfo> getAllCardsForKipCal() {
        return dashboardDao.getAllCardsForKipCal();
    }

    public List<CardAccountDistributionInfo> getAccountDistInfoAllUserCurrentmonthForDealer(String pin) {
        return dashboardDao.getAccountDistInfoAllUserCurrentmonthForDealer(pin);
    }


    public Map<String, Object> getProductWiseSummaryForDealer(List<LoanAccountDistributionInfo> accountDistributionList,
                                                              String userId,
                                                              String designation,
                                                              String unit) {

        Map<String, Object> productWiseSummary = new HashMap<>();

        String dealerName = "";
        List<ProductWiseSummary> productWiseSummaryList = new LinkedList<>();

        List<PeopleAllocationLogicInfo> allocationList = getAllDealerList(userId, designation, unit);

        if (accountDistributionList.isEmpty())
            return productWiseSummary;

        List<LoanKPITargetByAmountEntity> kpiTargetList = new ArrayList<>();

        try {
            kpiTargetList = loanKPITargetByAmountService.getAllDataForDist(accountDistributionList.get(0).getCreatedDate());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        List<LoanKPITargetByAmountEntity> temp = new ArrayList<>();

        for (PeopleAllocationLogicInfo logicInfo : allocationList) {
            for (ProductTypeEntity productTypeEntity : logicInfo.getProductTypeEntityLoan()) {

                for (LoanKPITargetByAmountEntity target : kpiTargetList) {
                    if (target.getProductType().contains(productTypeEntity)) {
                        if (!temp.contains(target))
                            temp.add(target);
                    }
                }

            }
        }

        kpiTargetList = temp;

        List<LoanKPITargetByAmountEntity> temp1 = new ArrayList<>();

        for (PeopleAllocationLogicInfo logicInfo : allocationList) {
            for (DPDBucketEntity bucket : logicInfo.getDpdBucketEntity()) {
                for (LoanKPITargetByAmountEntity target : kpiTargetList) {
                    if (target.getDpdBucket().contains(bucket)) {
                        if (!temp1.contains(target))
                            temp1.add(target);
                    }
                }

            }
        }

        kpiTargetList = temp1;
        double amountPerPT = 0;
        double amountOfEachDpd = 0;
        double amountOfFlow = 0;
        double amountOfSave = 0;
        double amountOfBack = 0;
        double weightBackPerc = 0;
        double weightFlowPerc = 0;
        double weightSavePerc = 0;
        double weightregularPerc = 0;
        double weightOverDuePerc = 0;

        double flowAchieventAmount = 0;
        double saveAchieventAmount = 0;
        double backAchieventAmount = 0;
        double regularAchieventAmount = 0;


        List<LoanKPITargetWeightByAmountEntity> weightByAmountList = loanKPITargetWeightByAmountService.getAllData(accountDistributionList.get(0).getCreatedDate());

        for (PeopleAllocationLogicInfo logicInfo : allocationList) {

            for (LoanKPITargetByAmountEntity kpiTargetByAmount : kpiTargetList) {

                for (ProductTypeEntity productType : kpiTargetByAmount.getProductType()) {

//                    if (!designation.toUpperCase().equals("DEALER")) {
//                        allocationList = dashboardService.getAllDealerList(userId, designation, unit);
//                        for (PeopleAllocationLogicInfo logic : allocationList) {
//                            amountPerPT = dashboardService.getAmountPerPt(logic.getDealer().getPin(), productType.getCode());
//                        }
//                    } else {
//                        amountPerPT = dashboardService.getAmountPerPt(userPin, productType.getCode());
//                    }
                    for (DPDBucketEntity dpdBucket : kpiTargetByAmount.getDpdBucket()) {

                        double saveAmount = 0;
                        double MO_DPD;
                        double dayDiff;
                        double backAmount = 0;
                        double overDueAmount = 0;
                        double flowAmount = 0;
                        double overDueAmnt = 0;
                        double regularAmnt = 0;
                        Double totalOutstanding = 0d;

                        Duration day;
                        flowAchieventAmount = 0;
                        saveAchieventAmount = 0;
                        backAchieventAmount = 0;
                        regularAchieventAmount = 0;
                        double totalPaidAmnt = 0;
                        for (LoanAccountDistributionInfo distributionInfo : accountDistributionList) {
                            dealerName = distributionInfo.getDealerName();

                            for (LoanKPITargetWeightByAmountEntity weight : weightByAmountList) {
                                if (weight.getProductType().contains(productType) && weight.getDpdBucket().contains(dpdBucket)) {
                                    weightBackPerc = weight.getBackWeight();
                                    weightSavePerc = weight.getSaveWeight();
                                    weightFlowPerc = weight.getFlowWeight();
                                    weightregularPerc = weight.getRegularWeight();
                                    weightOverDuePerc = weight.getOverDueWeight();
                                }
                            }
                            String distributedBucket = Optional.ofNullable(distributionInfo.getDpdBucket()).orElse("");
                            String currentBucket = Optional.ofNullable(dpdBucket.getBucketName()).orElse("");
                            String distributedProductCode = Optional.ofNullable(distributionInfo.getSchemeCode()).orElse("");
                            String currentProductCode = Optional.ofNullable(productType.getCode()).orElse("");

                            if (distributedBucket.equalsIgnoreCase(currentBucket) && distributedProductCode.equalsIgnoreCase(currentProductCode)
                                    && logicInfo.getProductTypeEntityLoan().contains(productType) && logicInfo.getDpdBucketEntity().contains(dpdBucket)) {


                                totalOutstanding = totalOutstanding + Double.parseDouble(distributionInfo.getOutStanding());
                                overDueAmount = overDueAmount + distributionInfo.getOpeningOverDue();
                                amountOfEachDpd = amountOfEachDpd + Double.parseDouble(distributionInfo.getOutStanding());

                                /*if(distributionInfo.getLiveDpd() <=0 ){
                                    regularAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                }*/

                                DPDBucketEntity currentDpdBucket = dpdBucketRepository.findFirstByMinDpdLessThanEqualAndMaxDpdGreaterThanEqual(distributionInfo.getLiveDpd(), distributionInfo.getLiveDpd());

                                // regular achievement amount
                                if (distributionInfo.getPresentOverDue() <= 0) {
//                                    System.out.println("DPD BUCKET NAME: " + dpdBucket.getBucketName());
//                                    System.out.println("PRODUCT SCHEME CODE: " + distributionInfo.getSchemeCode());
//                                    System.out.println("Regular Account NO: " + distributionInfo.getLoanAccountBasicInfo().getAccountNo() + "  "
//                                            + distributionInfo.getOutStanding()
//                                            + " present overdue " + distributionInfo.getPresentOverDue()
//                                            + " Live dpd " + distributionInfo.getLiveDpd()
//
//                                    );
                                    regularAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());

                                    backAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                }


                                if (currentDpdBucket != null) {
                                    if (!(currentDpdBucket.getBucketName().toUpperCase().equals("INTERIM")
                                            || currentDpdBucket.getBucketName().toUpperCase().equals("0"))
                                            && !(distributionInfo.getDpdBucket().toUpperCase().equals("INTERIM")
                                            || distributionInfo.getDpdBucket().toUpperCase().equals("0"))) {
                                        if (Double.parseDouble(currentDpdBucket.getBucketName()) > Double.parseDouble(distributionInfo.getDpdBucket())) {
                                            flowAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        } else if (Double.parseDouble(currentDpdBucket.getBucketName()) == Double.parseDouble(distributionInfo.getDpdBucket())) {
                                            /*System.out.println("DPD BUCKET NAME: " + dpdBucket.getBucketName());
                                            System.out.println("PRODUCT SCHEME CODE: " + distributionInfo.getSchemaCode());
                                            System.out.println("Save Account NO: " + distributionInfo.getLoanAccountBasicInfo().getAccountNo() +"  "
                                                    + distributionInfo.getOutStanding()
                                                    + "  " + currentDpdBucket.getBucketName()
                                                    + " Live dpd " + distributionInfo.getLiveDpd()
                                            );*/
                                            saveAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        } else if (Double.parseDouble(currentDpdBucket.getBucketName()) < Double.parseDouble(distributionInfo.getDpdBucket())) {
//                                            System.out.println("DPD BUCKET NAME: " + dpdBucket.getBucketName());
//                                            System.out.println("PRODUCT SCHEME CODE: " + distributionInfo.getSchemeCode());
//                                            System.out.println("Back Account NO: " + distributionInfo.getLoanAccountBasicInfo().getAccountNo() + "  "
//                                                    + distributionInfo.getOutStanding()
//                                                    + "  " + currentDpdBucket.getBucketName()
//                                                    + " Live dpd " + distributionInfo.getLiveDpd()
//                                            );
                                            backAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        }


                                    }
                                    String monthOpeningDpdBucket = distributionInfo.getDpdBucket();
                                    /*if(currentDpdBucket.getBucketName().toUpperCase().equals("0") || currentDpdBucket.getBucketName().toUpperCase().equals("INTERIM")){

                                        if(!monthOpeningDpdBucket.toUpperCase().equals("0") || !monthOpeningDpdBucket.toUpperCase().equals("INTERIM") ){
                                            if(Double.parseDouble(monthOpeningDpdBucket) >= 30){
                                                backAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                            }
                                        }
                                    }

                                    else if(Double.parseDouble(currentDpdBucket.getBucketName()) >= 30){

                                        if(monthOpeningDpdBucket.equals("0")){
                                            flowAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        }
                                    }*/

                                    //for x bucket calculation
                                    if (distributionInfo.getDpdBucket().toUpperCase().equals("0") || distributionInfo.getDpdBucket().toUpperCase().equals("INTERIM")) {
                                        if (Double.parseDouble(currentDpdBucket.getBucketName()) >= 30) {
                                            flowAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        } else if (currentDpdBucket.getBucketName().toUpperCase().equals("0")) {
                                            saveAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        } else if (distributionInfo.getPresentOverDue() > 0) {
//                                            System.out.println("DPD BUCKET NAME: " + dpdBucket.getBucketName());
//                                            System.out.println("PRODUCT SCHEME CODE: " + distributionInfo.getSchemeCode());
//                                            System.out.println("Back Account NO: " + distributionInfo.getLoanAccountBasicInfo().getAccountNo() + "  "
//                                                    + distributionInfo.getOutStanding()
//                                                    + "  " + currentDpdBucket.getBucketName()
//                                                    + " Live dpd " + distributionInfo.getLiveDpd()
//                                            );
                                            backAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        } else if (distributionInfo.getPresentOverDue() <= 0) {
                                            backAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                            regularAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        }
                                    }

                                    if (currentDpdBucket.getBucketName().toUpperCase().equals("0") || currentDpdBucket.getBucketName().toUpperCase().equals("INTERIM")) {
                                        if (Double.parseDouble(distributionInfo.getDpdBucket()) >= 30) {
//                                            System.out.println("DPD BUCKET NAME: " + dpdBucket.getBucketName());
//                                            System.out.println("PRODUCT SCHEME CODE: " + distributionInfo.getSchemeCode());
//                                            System.out.println("Back Account NO for 2: " + distributionInfo.getLoanAccountBasicInfo().getAccountNo() + "  "
//                                                    + distributionInfo.getOutStanding()
//                                                    + "  " + currentDpdBucket.getBucketName()
//                                                    + " Live dpd " + distributionInfo.getLiveDpd()
//                                            );
                                            backAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        }
                                    }
                                }

                                Date monthOpenDate = distributionInfo.getCreatedDate();
                                Date monthEndDate = dateUtils.getMonthEndDate(monthOpenDate);

                                LocalDate localDate1 = monthOpenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                LocalDate localDate2 = monthEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                                day = Duration.between(localDate1.atStartOfDay(), localDate2.atStartOfDay());

                                dayDiff = (double) day.toDays();
                                MO_DPD = distributionInfo.getDpd();

                                List<LoanPayment> loanPayments = getLoanPaymentByAcc(distributionInfo.getLoanAccountBasicInfo().getAccountNo(), distributionInfo.getStatusDate());
                                if (loanPayments != null) {

                                    for (LoanPayment paymentDetails : loanPayments) {
                                        totalPaidAmnt = totalPaidAmnt + paymentDetails.getPayment();
                                    }
                                }

                                if (distributionInfo.getDpdBucket().toUpperCase().equals("0")) {
                                    saveAmount = ((MO_DPD + (dayDiff + 1 - 29.999)) * distributionInfo.getEmiAmount()) / 30;

                                    if (saveAmount <= totalPaidAmnt) {
                                        saveAmount = totalPaidAmnt;
                                    } else
                                        saveAmount = 0;

                                    distributionInfo.setBackAmount(0);
                                } else {
                                    double highestDpdRange = Double.parseDouble(distributionInfo.getDpdBucket()) + 29.999;
                                    saveAmount = ((MO_DPD + (dayDiff + 1 - highestDpdRange)) * distributionInfo.getEmiAmount()) / 30;

                                    if (saveAmount <= totalPaidAmnt) {
                                        saveAmount = totalPaidAmnt;
                                    } else
                                        saveAmount = 0;
                                    double highestDpdRangeBack = Double.parseDouble(distributionInfo.getDpdBucket()) - .999;
                                    backAmount = ((MO_DPD + dayDiff + 1 - highestDpdRangeBack) * distributionInfo.getEmiAmount()) / 30;

                                    if (backAmount <= totalPaidAmnt) {
                                        backAmount = totalPaidAmnt;
                                    } else
                                        backAmount = 0;
                                }

                                overDueAmnt = overDueAmnt + totalPaidAmnt;
                                amountOfSave = amountOfSave + saveAmount;
                                amountOfBack = amountOfBack + backAmount;

                                Double presentOverDue = distributionInfo.getOpeningOverDue() + distributionInfo.getEmiAmount();
                                Double tempRegAch = 0d;
                                if (totalPaidAmnt >= presentOverDue) {
                                    tempRegAch = Double.parseDouble(distributionInfo.getOutStanding());
                                }

                                if (!distributionInfo.getMonitoringStatus().toUpperCase().equals("SINGLE")) {
                                    tempRegAch = tempRegAch / 2;
                                }
                                regularAmnt = regularAmnt + tempRegAch;
                            }
                        }
//                        System.out.println("Flow Achievement Amount: " + BigDecimal.valueOf(flowAchieventAmount));
                        if (amountOfEachDpd > 0) {
                            ProductWiseSummary summary = new ProductWiseSummary();

                            summary.setDpdBucket(dpdBucket.getBucketName());
                            summary.setProduct(productType.getName() + "-" + productType.getCode());
                            summary.setAmntEachDpd(amountOfEachDpd);

                            //back
                            summary.setAmntOfBackAc(backAchieventAmount);
                            summary.setTarAmntOfBackAc((kpiTargetByAmount.getBackTarget() / 100) * amountOfEachDpd);
                            summary.setTarAmntOfBackAcPerc(kpiTargetByAmount.getBackTarget());
                            summary.setShortFallBack(summary.getTarAmntOfBackAc() - summary.getAmntOfBackAc());
                            if (backAmount > 0) {
                                if (summary.getTarAmntOfBackAc() > 0)
                                    summary.setBackPerformance((summary.getAmntOfBackAc() / summary.getTarAmntOfBackAc()) * 100);
                                summary.setAmntOfBackAcPerc((summary.getAmntOfBackAc() / amountOfEachDpd) * 100);
                            }
                            summary.setWeightBackPerc(weightBackPerc);
                            if (weightBackPerc > 0)
                                summary.setWeightBackPerformmance(weightBackPerc * summary.getBackPerformance());


                            //save
                            summary.setAmntOfSaveAc(saveAchieventAmount);
                            summary.setTarAmntOfSaveAcPerc(kpiTargetByAmount.getSaveTarget());
                            summary.setTarAmntOfSaveAc((kpiTargetByAmount.getSaveTarget() / 100) * amountOfEachDpd);
                            summary.setShortFallSave(summary.getTarAmntOfSaveAc() - summary.getAmntOfSaveAc());
                            if (amountOfSave > 0) {
                                if (summary.getTarAmntOfSaveAc() > 0)
                                    summary.setSavePerformance((amountOfSave / summary.getTarAmntOfSaveAc()) * 100);
                                summary.setAmntOfSaveAcPerc((summary.getAmntOfSaveAc() / amountOfEachDpd) * 100);
                            }
                            summary.setWeightSavePerc(weightSavePerc);
                            if (weightSavePerc > 0)
                                summary.setWeightSavePerformmance(weightSavePerc * summary.getSavePerformance());

                            //overdue
                            summary.setAmntOverDue(totalPaidAmnt);
                            summary.setTarAmntOverDue((kpiTargetByAmount.getOverDueTaret() / 100) * amountOfEachDpd);
                            summary.setTarAmntOverDuePerc(kpiTargetByAmount.getOverDueTaret());
                            summary.setShortFallOverDue(summary.getTarAmntOverDue() - overDueAmnt);
                            if (overDueAmnt > 0) {
                                if (summary.getTarAmntOverDue() > 0)
                                    summary.setOverDuePerformance((summary.getAmntOverDue() / summary.getTarAmntOverDue()) * 100);
                                summary.setAmntOverDuePerc((summary.getAmntOverDue() / amountOfEachDpd) * 100);
                            }
                            summary.setWeightOverdDuePerc(weightOverDuePerc);
                            if (weightOverDuePerc > 0)
                                summary.setWeightOverDuePerformmance(weightOverDuePerc * summary.getOverDuePerformance());

                            //regular
                            summary.setAmntRegular(regularAchieventAmount);
                            summary.setTarAmntRegular((kpiTargetByAmount.getRegularTarget() / 100) * amountOfEachDpd);
                            summary.setTarAmntRegularPerc(kpiTargetByAmount.getRegularTarget());
                            summary.setAmntRegularPerc(kpiTargetByAmount.getRegularTarget());
                            if (regularAmnt > 0) {
                                if (kpiTargetByAmount.getRegularTarget() > 0)
                                    summary.setRegularPerFormance((regularAmnt / kpiTargetByAmount.getRegularTarget()) * 100);
                                summary.setAmntRegularPerc((summary.getAmntRegular() / amountOfEachDpd) * 100);

                            }
                            summary.setShortFallRegular(summary.getTarAmntOverDue() - regularAmnt);
                            summary.setWeightRegularPerc(weightregularPerc);
                            if (weightregularPerc > 0)
                                summary.setWeightRegularPerformmance(weightregularPerc * summary.getRegularPerFormance());

                            //flow
                            summary.setTarAmntOfFlowAc((kpiTargetByAmount.getFlowTarget() / 100) * amountOfEachDpd);
                            summary.setTarAmntOfFlowAcPerc(kpiTargetByAmount.getFlowTarget());


                            summary.setAmntOfFlowAc(flowAchieventAmount);
                            summary.setShortFallFlow(summary.getTarAmntOfFlowAc() - summary.getAmntOfFlowAc());
                            if (summary.getAmntOfFlowAc() > 0) {
                                double flowAchPerc = 0;
                                if (summary.getTarAmntOfFlowAc() >= summary.getAmntOfFlowAc()) {
                                    flowAchPerc = ((summary.getTarAmntOfFlowAc() - summary.getAmntOfFlowAc()) / summary.getTarAmntOfFlowAc()) + 1;
                                } else {
                                    if (summary.getAmntOfFlowAc() > summary.getTarAmntOfFlowAc() && summary.getAmntOfFlowAc() < (totalOutstanding - summary.getTarAmntOfFlowAc())) {
                                        flowAchPerc = (1 - (1 - (summary.getTarAmntOfFlowAc() / summary.getAmntOfFlowAc())));
                                    } else {
                                        flowAchPerc = (totalOutstanding / summary.getAmntOfFlowAc()) - 1;
                                    }
                                }
                                summary.setFlowPerformance(flowAchPerc);
                                summary.setAmntOfFlowAcPerc((summary.getAmntOfFlowAc() / amountOfEachDpd) * 100);
                            }
                            summary.setWeightFlowPerc(weightFlowPerc);
                            if (weightFlowPerc > 0)
                                summary.setWeightFlowPerformmance(weightFlowPerc * summary.getFlowPerformance());

                            productWiseSummaryList.add(summary);

                        }
                        amountOfEachDpd = 0;
                        amountOfFlow = 0;
                        amountOfSave = 0;
                        amountOfBack = 0;
                        weightBackPerc = 0;
                        weightFlowPerc = 0;
                        weightSavePerc = 0;
                        weightregularPerc = 0;
                        weightOverDuePerc = 0;
                        overDueAmnt = 0;
                        regularAmnt = 0;


                    }
                    amountPerPT = 0;

                }
            }
        }

        List<ProductWiseSummary> productWiseSummaryListFinal = new ArrayList<>();
        for (LoanKPITargetByAmountEntity target : kpiTargetList) {
            for (ProductTypeEntity productType : target.getProductType()) {
                String product = productType.getName() + "-" + productType.getCode();
                for (ProductWiseSummary summary : productWiseSummaryList) {
                    if (summary.getProduct().equals(product)) {
                        if (!productWiseSummaryListFinal.contains(summary)) {
                            productWiseSummaryListFinal.add(summary);
                        }
                    }


                }
            }

        }

        productWiseSummary.put("dealerName", dealerName);
        productWiseSummary.put("productWiseSummaryList", productWiseSummaryListFinal);

        return productWiseSummary;
    }
}
