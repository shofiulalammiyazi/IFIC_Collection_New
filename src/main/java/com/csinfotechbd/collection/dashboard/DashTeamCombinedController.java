package com.csinfotechbd.collection.dashboard;
/*
  Created by Md. Monirul Islam on 1/2/2020
*/

import com.csinfotechbd.collection.KPI.Loan.AccountTargetByManager.DealerAccountTargetByManager;
import com.csinfotechbd.collection.KPI.Loan.AccountTargetByManager.DealerAccountTargetByManagerDao;
import com.csinfotechbd.collection.KPI.Loan.AmountTargetByManager.DealerAmountTargetByManager;
import com.csinfotechbd.collection.KPI.Loan.AmountTargetByManager.DealerAmountTargetByManagerDao;
import com.csinfotechbd.collection.KPI.Loan.TargetByAccount.LoanKPITargetByAccountEntity;
import com.csinfotechbd.collection.KPI.Loan.TargetByAccount.LoanKPITargetByAccountService;
import com.csinfotechbd.collection.KPI.Loan.TargetByAmount.LoanKPITargetByAmountEntity;
import com.csinfotechbd.collection.KPI.Loan.TargetByAmount.LoanKPITargetByAmountService;
import com.csinfotechbd.collection.KPI.Loan.TargetWeightByAccount.LoanKPITargetWeightByAccountEntity;
import com.csinfotechbd.collection.KPI.Loan.TargetWeightByAccount.LoanKPITargetWeightByAccountService;
import com.csinfotechbd.collection.KPI.Loan.TargetWeightByAmount.LoanKPITargetWeightByAmountEntity;
import com.csinfotechbd.collection.KPI.Loan.TargetWeightByAmount.LoanKPITargetWeightByAmountService;
import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.csinfotechbd.collection.scheduler.ScheduledCornJobCard;
import com.csinfotechbd.retail.card.dataEntry.distribution.agency.CardAgencyDistributionInfo;
import com.csinfotechbd.collection.distribution.agencyAllocation.loan.LoanAgencyDistributionInfo;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.csinfotechbd.collection.settings.NPLQueueAccRule.NPLQueueAccRuleEntity;
import com.csinfotechbd.collection.settings.NPLQueueAccRule.NPLQueueAccService;
import com.csinfotechbd.retail.loan.setup.nplReleaseLoan.NplReleaseLoan;
import com.csinfotechbd.retail.loan.setup.nplReleaseLoan.NplReleaseLoanService;
import com.csinfotechbd.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanEntity;
import com.csinfotechbd.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanService;
import com.csinfotechbd.collection.settings.PARqueueAccRuleLoan.PARqueueAccRuleLoanEntity;
import com.csinfotechbd.collection.settings.PARqueueAccRuleLoan.PARqueueAccRuleLoanService;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeRepository;
import com.csinfotechbd.collection.settings.esau.card.ESAUCardEntity;
import com.csinfotechbd.collection.settings.esau.card.ESAUCardService;
import com.csinfotechbd.collection.settings.esau.loan.ESAULoanEntity;
import com.csinfotechbd.collection.settings.esau.loan.ESAULoanService;
import com.csinfotechbd.collection.settings.nplAccountRule.NPLAccountRuleEntity;
import com.csinfotechbd.collection.settings.nplAccountRule.NPLAccountRuleService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeRepository;
import com.csinfotechbd.customerloanprofile.diarynote.DairyNotesLoanRepository;

import com.csinfotechbd.customerloanprofile.loanpayment.LoanPayment;
import com.csinfotechbd.retail.loan.dataEntry.ptp.LoanPtp;
import com.csinfotechbd.retail.card.dataEntry.dairynotes.DairyNotesRepository;
import com.csinfotechbd.retail.loan.setup.parReleaseLoan.ParReleaseLoan;
import com.csinfotechbd.retail.loan.setup.parReleaseLoan.ParReleaseLoanService;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.DateUtils;
import com.csinfotechbd.utillity.HttpSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/dashboard/team/")
public class DashTeamCombinedController {

    private final DashboardService dashboardService;
    private final DPDBucketService dpdBucketService;
    private final ProductTypeRepository productTypeRepository;
    private final PARaccountRuleLoanService paRaccountRuleLoanService;
    private final PARqueueAccRuleLoanService paRqueueAccRuleLoanService;
    private final NPLAccountRuleService nplAccountRuleService;
    private final NPLQueueAccService nplQueueAccService;
    private final AgeCodeService ageCodeService;
    private final com.csinfotechbd.collection.settings.nplAccountRuleCard.NPLAccountRuleCardService NPLAccountRuleCardService;
    private final ParReleaseLoanService paRreleaseAmountLoanService;
    private final NplReleaseLoanService npLreleaseAmountLoanService;
    private final LoanKPITargetByAmountService loanKPITargetByAmountService;
    private final LoanKPITargetByAccountService loanKPITargetByAccountService;
    private final LoanKPITargetWeightByAmountService loanKPITargetWeightByAmountService;
    private final DairyNotesLoanRepository dairyNotesLoanRepository;
    private final EmployeeRepository employeeRepository;
    private final DealerPerformanceDataDao dealerPerformanceDataDao;
    private final ESAUCardService esauCardService;
    private final DairyNotesRepository dairyNotesRepository;
    private final ESAULoanService esauLoanService;
    private final DealerAmountTargetByManagerDao dealerAmountTargetByManagerDao;
    private final LoanKPITargetWeightByAccountService loanKPITargetWeightByAccountService;
    private final DealerAccountTargetByManagerDao dealerAccountTargetByManagerDao;
    private final HttpSessionUtils httpSessionUtils;
    private final DateUtils dateUtils;

    @GetMapping(value = "esau/loan")
    @ResponseBody
    public EsauRatingDataModel getEsauLoan(@RequestParam(value = "userPin") String userPin,
                                           @RequestParam(value = "unit") String unit,
                                           @RequestParam(value = "designation") String designation,
                                           @RequestParam(value = "userId") String userId) {
        DealerPerformanceDataEntity dealerPerformanceData = dealerPerformanceDataDao.getCurrentMonthPerformanceByUserPin(userPin, unit.toUpperCase());

        List<ESAULoanEntity> esauCardList = esauLoanService.getAll();
        EsauRatingDataModel esauRatingDataModel = new EsauRatingDataModel();

        if (dealerPerformanceData != null) {
            for (ESAULoanEntity esauCard : esauCardList) {
                if (esauCard.getFinalAvgLowerLimit() <= dealerPerformanceData.getPerformanceAvg() && dealerPerformanceData.getPerformanceAvg() <= esauCard.getFinalAvgUpperLimit()) {
                    esauRatingDataModel.setPerformanceAvg(dealerPerformanceData.getPerformanceAvg());
                    esauRatingDataModel.setRatingName(esauCard.getRatingName());
                    //break;
                }
            }
        }
        return esauRatingDataModel;
    }

    @GetMapping(value = "esau/card")
    @ResponseBody
    public EsauRatingDataModel getEsauCard(@RequestParam(value = "userPin") String userPin,
                                           @RequestParam(value = "unit") String unit,
                                           @RequestParam(value = "designation") String designation,
                                           @RequestParam(value = "userId") String userId) {
        DealerPerformanceDataEntity dealerPerformanceData = dealerPerformanceDataDao.getCurrentMonthPerformanceByUserPin(userPin, unit.toUpperCase());

        List<ESAUCardEntity> esauCardList = esauCardService.getAll();
        EsauRatingDataModel esauRatingDataModel = new EsauRatingDataModel();

        if (dealerPerformanceData != null) {
            for (ESAUCardEntity esauCard : esauCardList) {
                if (esauCard.getFinalAvgLowerLimit() <= dealerPerformanceData.getPerformanceAvg() && dealerPerformanceData.getPerformanceAvg() <= esauCard.getFinalAvgUpperLimit()) {
                    esauRatingDataModel.setPerformanceAvg(dealerPerformanceData.getPerformanceAvg());
                    esauRatingDataModel.setRatingName(esauCard.getRatingName());
                    //break;
                }
            }
        }
        return esauRatingDataModel;
    }

    @GetMapping("daily_acivity")
    @ResponseBody
    public List<DailyActivityModel> getDailyActivity(@RequestParam(value = "userPin") String userPin,
                                                     @RequestParam(value = "unit") String unit,
                                                     @RequestParam(value = "designation") String designation,
                                                     @RequestParam(value = "userId") String userId,
                                                     @RequestParam(value = "selectedDate") String selectedDateString,
                                                     @RequestParam(value = "startTime") String startTime,
                                                     @RequestParam(value = "endTime") String endTime) {

        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);

        List<DailyActivityModel> activityModelList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date selectedDate = new Date();
        //System.err.println("STRING : "+selectedDateString+" "+startTime+endTime+"SUB  "+startTime.substring(startTime.lastIndexOf(":")+1));
        System.err.println(startTime.substring(0, startTime.lastIndexOf(":")));
        try {
            selectedDate = formatter.parse(selectedDateString);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        for (PeopleAllocationLogicInfo logicInfo : allocationList) {
            DailyActivityModel activityModel = new DailyActivityModel();

            activityModel.setDealerName(logicInfo.getDealer().getUser().getFirstName() + " " + logicInfo.getDealer().getUser().getLastName());
            activityModel.setDealerPin(logicInfo.getDealer().getPin());

           /* activityModel.setVisitLedger(dashboardService.getTotalVisitLedgerNoteByDealer(userId, unit, startTime, endTime, selectedDate));
            activityModel.setDailyNotes(dashboardService.getTotalDailyNoteByDealer(userId, unit, startTime, endTime, selectedDate));
            activityModel.setDairyNote(dashboardService.getTotalDairyNoteByDealer(userId, unit, startTime, endTime, selectedDate));
            activityModel.setHotNote(dashboardService.getTotalHotNoteNoteByDealer(userId, unit, startTime, endTime, selectedDate));
            activityModel.setPtp(dashboardService.getTotalPtpNoteByDealer(userId, unit, startTime, endTime, selectedDate));*/

            activityModel.setVisitLedger(dashboardService.getTotalVisitLedgerNoteByDealer(activityModel.getDealerPin(), unit, startTime, endTime, selectedDate));
            activityModel.setDailyNotes(dashboardService.getTotalDailyNoteByDealer(activityModel.getDealerPin(), unit, startTime, endTime, selectedDate));
            activityModel.setDairyNote(dashboardService.getTotalDairyNoteByDealer(activityModel.getDealerPin(), unit, startTime, endTime, selectedDate));
            activityModel.setHotNote(dashboardService.getTotalHotNoteNoteByDealer(activityModel.getDealerPin(), unit, startTime, endTime, selectedDate));
            activityModel.setPtp(dashboardService.getTotalPtpNoteByDealer(activityModel.getDealerPin(), unit, startTime, endTime, selectedDate));

            activityModelList.add(activityModel);
        }
        return activityModelList;

    }

    @GetMapping(value = "resource_details/loan")
    @ResponseBody
    public List<ResourceDetailModel> getResourceDetailLoan(@RequestParam(value = "userPin") String userPin,
                                                           @RequestParam(value = "unit") String unit,
                                                           @RequestParam(value = "designation") String designation,
                                                           @RequestParam(value = "userId") String userId) {
        List<ResourceDetailModel> resourceDetailList = new ArrayList<>();

        ResourceDetailModel office = new ResourceDetailModel();
        office.setStatusName("Office");
        office.setNoOfEmp(dashboardService.getTotalLoggedInUser(unit));

        ResourceDetailModel visit = new ResourceDetailModel();
        visit.setStatusName("Visit");
        Date today = new Date();
        visit.setNoOfEmp(dashboardService.getVisitLedgerNo(today, unit));


        resourceDetailList.add(office);
        resourceDetailList.add(visit);
        resourceDetailList.addAll(dashboardService.getNoOfLateEmp(unit));

        return resourceDetailList;
    }

    @GetMapping(value = "rfd_detail/card")
    @ResponseBody
    public Rfd getRfdMenuCard(HttpSession session) {

        List<Long> customerIdList = httpSessionUtils.getCardCustomerIds(session);

        Rfd rfd = new Rfd();

        Date startDate = dateUtils.getMonthStartDate();

        List<RfdMenuModel> menuList = dairyNotesRepository.findRfdMenuGroup(customerIdList, startDate);
        List<RfdMenuModel> subMenu1List = dairyNotesRepository.findRfdSuBMenu1Group(customerIdList, startDate);
        List<RfdMenuModel> subMenu2List = dairyNotesRepository.findRfdSuBMenu2Group(customerIdList, startDate);
        List<RfdMenuModel> subMenu3List = dairyNotesRepository.findRfdSuBMenu3Group(customerIdList, startDate);

        rfd.setRfdMenuList(menuList);
        rfd.setSubMenu1List(subMenu1List);
        rfd.setSubMenu2List(subMenu2List);
        rfd.setSubMenu3List(subMenu3List);

        return rfd;
    }


    @GetMapping(value = "rfd_detail")
    @ResponseBody
    public Rfd getRfdStatus(@RequestParam(value = "unit") String unit, HttpSession session) {
        return dashboardService.getRfdSummaryByCustomer(unit, session);
    }


    @GetMapping(value = "agency_detail/loan")
    @ResponseBody
    public List<AgencyWiseModel> getAgencyDetailLoan(@RequestParam(value = "userPin") String userPin,
                                                     @RequestParam(value = "unit") String unit,
                                                     @RequestParam(value = "designation") String designation,
                                                     @RequestParam(value = "userId") String userId) {
        List<AgencyWiseModel> agencyDetailList = new ArrayList<>();

        List<String> agencyNameList = dashboardService.getAgencyNameLoan();

        List<LoanAgencyDistributionInfo> agencyDistList = dashboardService.getAllAgencyDistLoan();
        List<ProductTypeEntity> ptList = productTypeRepository.findByProductGroupEntityCardAccount(unit);
        List<DPDBucketEntity> bucketList = dpdBucketService.getActiveList();

        long totalAc = 0;
        double totalOutstanding = 0;
        long noOfPtp = 0;
        long noOfFollowUp = 0;

        for (ProductTypeEntity typeEntity : ptList) {
            for (DPDBucketEntity dpd : bucketList) {
                for (String agencyName : agencyNameList) {
                    for (LoanAgencyDistributionInfo info : agencyDistList) {
                        LoanAccountDistributionInfo distInfo = dashboardService.getDistInfoByLoanBasicInfo(info.getLoanAccountBasicInfo());
                        if (info.getDpdBucket().toUpperCase().equals(dpd.getBucketName().toUpperCase()) && distInfo.getSchemeCode().toUpperCase().equals(typeEntity.getCode().toUpperCase()) && info.getAgencyName().equals(agencyName)) {
                            totalAc++;
                            totalOutstanding = totalOutstanding + Double.parseDouble(info.getOutstanding());

                            noOfPtp = dashboardService.getLoanPtpByCustomer(distInfo.getLoanAccountBasicInfo().getCustomer().getId()).size();

                            noOfFollowUp = dashboardService.getTotalFollowUpNumber(distInfo.getLoanAccountBasicInfo().getCustomer().getId());

                            agencyDistList.remove(info);
                        }
                    }
                    AgencyWiseModel agencyWiseModel = new AgencyWiseModel();

                    agencyWiseModel.setProduct(typeEntity.getName());
                    agencyWiseModel.setDpd(dpd.getBucketName());
                    agencyWiseModel.setAgencyName(agencyName);
                    agencyWiseModel.setTotalAc(totalAc);
                    agencyWiseModel.setTotalOutstanding(totalOutstanding);
                    agencyWiseModel.setNoOfFollowUp(noOfFollowUp);
                    agencyWiseModel.setNoOfPtp(noOfPtp);

                    agencyDetailList.add(agencyWiseModel);

                    totalAc = 0;
                    totalOutstanding = 0;
                    noOfPtp = 0;
                    noOfFollowUp = 0;
                }

            }
        }

        return agencyDetailList;
    }

    @GetMapping(value = "agency_detail/card")
    @ResponseBody
    public List<AgencyWiseModel> getAgencyDetailCard(@RequestParam(value = "userPin") String userPin,
                                                     @RequestParam(value = "unit") String unit,
                                                     @RequestParam(value = "designation") String designation,
                                                     @RequestParam(value = "userId") String userId) {
        List<AgencyWiseModel> agencyDetailList = new ArrayList<>();

        List<String> agencyNameList = dashboardService.getAgencyNameCard();

        List<CardAgencyDistributionInfo> agencyDistList = dashboardService.getAllAgencyDistCard();
        List<ProductTypeEntity> ptList = productTypeRepository.findByProductGroupEntityCardAccount(unit);
        List<AgeCodeEntity> ageCodeList = ageCodeService.getActiveList();

        long totalAc = 0;
        double totalOutstanding = 0;
        long noOfPtp = 0;
        long noOfFollowUp = 0;

        for (ProductTypeEntity typeEntity : ptList) {
            for (AgeCodeEntity ageCode : ageCodeList) {
                for (String agencyName : agencyNameList) {
                    for (CardAgencyDistributionInfo info : agencyDistList) {
                        //CardAgencyDistributionInfo distInfo=dashboardService.getDistInfoByLoanBasicInfo(info.getLoanAccountBasicInfo());
                        if (info.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && info.getProductGroup().toUpperCase().equals(typeEntity.getCode().toUpperCase()) && info.getAgencyName().equals(agencyName)) {
                            totalAc++;
                            totalOutstanding = totalOutstanding + Double.parseDouble(info.getOutstanding());


                            noOfPtp = dashboardService.getCardPtpByCustomer(info.getCardAccountBasicInfo().getCustomer().getId()).size();
                            noOfFollowUp = dashboardService.getTotalFollowUpNumber(info.getCardAccountBasicInfo().getCustomer().getId());

                            agencyDistList.remove(info);
                        }
                    }
                    AgencyWiseModel agencyWiseModel = new AgencyWiseModel();

                    agencyWiseModel.setAgencyName(agencyName);
                    agencyWiseModel.setTotalAc(totalAc);
                    agencyWiseModel.setTotalOutstanding(totalOutstanding);
                    agencyWiseModel.setNoOfFollowUp(noOfFollowUp);
                    agencyWiseModel.setNoOfPtp(noOfPtp);

                    agencyDetailList.add(agencyWiseModel);

                    totalAc = 0;
                    totalOutstanding = 0;
                    noOfPtp = 0;
                    noOfFollowUp = 0;
                }

            }
        }
        return agencyDetailList;
    }

    @RequestMapping(value = "/summary_detail/loan")
    @ResponseBody
    public List<ProductWiseSummary2> getSummaryDetailLoan(@RequestParam(value = "userPin") String userPin,
                                                          @RequestParam(value = "unit") String unit,
                                                          @RequestParam(value = "designation") String designation,
                                                          @RequestParam(value = "userId") String userId,
                                                          HttpSession session) {
        List<ProductWiseSummary2> productWiseSummaryList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);

        List<LoanAccountDistributionInfo> accountDistributionList = (List<LoanAccountDistributionInfo>) session.getAttribute("loanDistributionList");

        if (accountDistributionList.size() < 1)
            return productWiseSummaryList;

        List<LoanKPITargetByAmountEntity> kpiTargetList = new ArrayList<>();
        if (accountDistributionList.size() > 0)
            kpiTargetList = loanKPITargetByAmountService.getAllDataForDist(accountDistributionList.get(0).getCreatedDate());
        else
            kpiTargetList = loanKPITargetByAmountService.getAllDataForDist(new Date());

        double amountPerPT = 0;
        double amountOfEachDpd = 0;
        double amountOfFlow = 0;
        double amountOfSave = 0;
        double amountOfBack = 0;
        double weightBackPerc = 0;
        double weightFlowPerc = 0;
        double weightSavePerc = 0;

        List<LoanKPITargetWeightByAmountEntity> weightByAmountList = new ArrayList<>();

        if (accountDistributionList.size() > 0)
            weightByAmountList = loanKPITargetWeightByAmountService.getAllData(accountDistributionList.get(0).getCreatedDate());
        else
            weightByAmountList = loanKPITargetWeightByAmountService.getAllData(new Date());


        List<DPDBucketEntity> bucketEntities = dpdBucketService.getActiveList();
        // bucketEntities.sort();
        List<AmountWiseSummaryModel> summaryModelList = new ArrayList<>();
        List<ProductTypeEntity> ptList = productTypeRepository.findByProductGroupEntityCardAccount(unit);
        ParReleaseLoan parRelAmountLoan = paRreleaseAmountLoanService.getPAR();
        NplReleaseLoan npLreleaseAmountLoan = npLreleaseAmountLoanService.getNPL();

        double amountPerPG = 0;
        double amountOfParRel = 0;
        double amountOfParQ = 0;
        double amountOfNplRel = 0;
        double amountOfNplQ = 0;
        double amountOftouchedAcc = 0;
        double amountOfUnTouched = 0;
        double amountOfPending = 0;
        double amountOfBrokenPtp = 0;

        double amountOfTotalPar = 0;
        double amountOfTotalNPL = 0;

        //double totalDistAmount=0;

        List<PARaccountRuleLoanEntity> paRaccountRuleLoanList = paRaccountRuleLoanService.getActiveList();
        List<PARqueueAccRuleLoanEntity> paRqueueAccRuleList = paRqueueAccRuleLoanService.getActiveList();
        List<NPLAccountRuleEntity> nplAccountRuleList = nplAccountRuleService.getActiveList();
        List<NPLQueueAccRuleEntity> nplQueueAccRuleList = nplQueueAccService.getActiveList();

        for (LoanKPITargetByAmountEntity kpiTargetByAmount : kpiTargetList) {

            for (ProductTypeEntity productType : kpiTargetByAmount.getProductType()) {
                if (!designation.toUpperCase().equals("DEALER")) {
                    for (PeopleAllocationLogicInfo logic : allocationList) {
                        amountPerPT = amountPerPT + dashboardService.getAmountPerPt(logic.getDealer().getPin(), productType.getCode());
                        //noOfAcPerPT=noOfAcPerPT+dashboardService.getNumberOfAcPerPt(logic.getDealer().getPin(),productType.getCode());
                    }
                } else {
                    amountPerPT = dashboardService.getAmountPerPt(userPin, productType.getCode());
                    //noOfAcPerPT=noOfAcPerPT+dashboardService.getNumberOfAcPerPt(userPin,productType.getCode());
                }

                for (DPDBucketEntity dpdBucket : kpiTargetByAmount.getDpdBucket()) {

                    double saveAmount = 0;
                    double MO_DPD;
                    double dayDiff;
                    double backAmount = 0;
                    double overDueAmount = 0;
                    double flowAmount = 0;
                    double regularAmnt = 0;
                    double overDueAmnt = 0;
                    //double totalDaysInMonth=getTotalDaysIMonth();
                    //double monthEndDpd;
                    //double emiPerDay;
                    Duration day;

                    //System.err.println("DIST LIST "+accountDistributionList.size());
                    for (LoanAccountDistributionInfo distributionInfo : accountDistributionList) {

                        for (LoanKPITargetWeightByAmountEntity weight : weightByAmountList) {
                            if (weight.getProductType().contains(productType) && weight.getDpdBucket().contains(dpdBucket)) {
                                weightBackPerc = weight.getBackWeight();
                                weightSavePerc = weight.getSaveWeight();
                                weightFlowPerc = weight.getSaveWeight();
                            }
                        }
                        if (distributionInfo.getDpdBucket().toUpperCase().equals(dpdBucket.getBucketName().toUpperCase()) && distributionInfo.getSchemeCode().toUpperCase().equals(productType.getCode().toUpperCase())) {
                            //noOfAcEachDpd++;
                            overDueAmnt = overDueAmnt + distributionInfo.getOpeningOverDue();
                            overDueAmount = overDueAmount + distributionInfo.getOpeningOverDue();
                            if (distributionInfo.getOpeningOverDue() == 0 && distributionInfo.getDpd() == 0)
                                regularAmnt = regularAmnt + Double.parseDouble(distributionInfo.getOutStanding());

                            amountOfEachDpd = amountOfEachDpd + Double.parseDouble(distributionInfo.getOutStanding());

                            Date monthEndDate = dateUtils.getMonthEndDate();
                            Date monthOpenDate = distributionInfo.getCreatedDate();

                            LocalDate localDate1 = monthOpenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            LocalDate localDate2 = monthEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                            day = Duration.between(localDate1.atStartOfDay(), localDate2.atStartOfDay());

                            //long ld=day.toDays();
                            dayDiff = (double) day.toDays();
                            //System.err.println("DIFF "+diff.toDays());
                            MO_DPD = distributionInfo.getDpd();
                            if (distributionInfo.getDpdBucket().toUpperCase().equals("X")) {
                                saveAmount = ((MO_DPD + (dayDiff + 1 - 29.50)) * distributionInfo.getEmiAmount()) / 30;
                                distributionInfo.setBackAmount(0);
                            } else {
                                double highestDpdRange = Double.parseDouble(distributionInfo.getDpdBucket()) + 29.50;
                                saveAmount = ((MO_DPD + (dayDiff + 1 - highestDpdRange)) * distributionInfo.getEmiAmount()) / 30;

                                double highestDpdRangeBack = Double.parseDouble(distributionInfo.getDpdBucket()) - .50;
                                backAmount = ((MO_DPD + dayDiff + 1 - highestDpdRangeBack) * distributionInfo.getEmiAmount()) / 30;


                            }

                            amountOfSave = amountOfSave + saveAmount;
                            amountOfBack = amountOfBack + backAmount;

                            //AmountWiseSummaryModel

                            try {
                                double dpdAmnt = Double.parseDouble(distributionInfo.getDpdBucket());

                                for (PARaccountRuleLoanEntity entity : paRaccountRuleLoanList) {
                                    if (dpdAmnt >= entity.getMinDpd() && dpdAmnt <= entity.getMaxDpd()) {
                                        amountOfTotalPar = amountOfTotalPar + Double.parseDouble(distributionInfo.getOutStanding());
                                    }
                                }

                                if (parRelAmountLoan.getDpdBucketNames().contains(distributionInfo.getDpdBucket()))
                                    amountOfParRel = amountOfParRel + Double.parseDouble(distributionInfo.getOutStanding());

                                for (PARqueueAccRuleLoanEntity parQ : paRqueueAccRuleList) {
                                    if (parQ.getMinDpd() <= dpdAmnt && parQ.getProductTypeList().contains(distributionInfo)) {
                                        amountOfParQ = amountOfParQ + Double.parseDouble(distributionInfo.getOutStanding());
                                    }
                                }

                                if (npLreleaseAmountLoan.getDpdBucketNames().contains(distributionInfo.getDpdBucket()))
                                    amountOfNplRel = amountOfNplRel + Double.parseDouble(distributionInfo.getOutStanding());

                            } catch (Exception e) {
                                //System.out.println(e.getMessage());
                            }

                            //NPL
                            for (NPLAccountRuleEntity nplAccountRuleEntity : nplAccountRuleList) {
                                if (nplAccountRuleEntity.getDpdBucketList().contains(dpdBucket) && nplAccountRuleEntity.getProductTypeList().contains(productType))
                                    amountOfTotalNPL = amountOfTotalNPL + Double.parseDouble(distributionInfo.getOutStanding());
                            }


                            for (NPLQueueAccRuleEntity nplQueue : nplQueueAccRuleList) {
                                if (nplQueue.getDpdBucketList().contains(dpdBucket) && nplQueue.getProductTypeList().contains(productType))
                                    amountOfNplQ = amountOfNplQ + Double.parseDouble(distributionInfo.getOutStanding());
                            }

                            int count = 0;
                            try {

                                List<LoanPtp> loanPtps = dashboardService.getLoanPtpByCustomer(distributionInfo.getLoanAccountBasicInfo().getCustomer().getId());
                                if (!loanPtps.isEmpty()) {
                                    for (LoanPtp ptp : loanPtps) {
                                        if (ptp.getCreatedBy().equals(userId))
                                            count++;
                                        if (ptp.getLoan_ptp_status().equals("broken"))
                                            amountOfBrokenPtp = amountOfBrokenPtp + Double.parseDouble(distributionInfo.getOutStanding());
                                    }
                                }

                            } catch (Exception e) {
                                //System.out.println(e.getMessage());
                            }
                            try {
                                if (dashboardService.getTotalDailyNoteNumber(distributionInfo.getLoanAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
                                    count++;
                            } catch (Exception e) {

                            }

                            try {
                                if (dashboardService.getTotalFollowUpNumber(distributionInfo.getLoanAccountBasicInfo().getCustomer().getId()) > 0)
                                    count++;
                            } catch (Exception e) {

                            }

                            try {
                                if (dashboardService.getTotalDiaryNoteNumber(distributionInfo.getLoanAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
                                    count++;
                            } catch (Exception e) {

                            }
                            try {
                                if (dashboardService.getTotalFollowUpNumber(distributionInfo.getLoanAccountBasicInfo().getCustomer().getId()) > 0)
                                    count++;
                            } catch (Exception e) {

                            }

                            if (count > 0) {
                                amountOftouchedAcc = amountOftouchedAcc + Double.parseDouble(distributionInfo.getOutStanding());
                            }
                        }
                    }

                    if (amountOfEachDpd > 0) {
                        ProductWiseSummary2 summary = new ProductWiseSummary2();

                        summary.setDpdBucket(dpdBucket.getBucketName());
                        summary.setProduct(productType.getName());
                        summary.setAmntEachDpd(amountOfEachDpd);
                        //Target for amount
                        summary.setTarAmntOfFlowAc((kpiTargetByAmount.getFlowTarget() / 100) * amountOfFlow);
                        summary.setTarAmntOfFlowAcPerc(kpiTargetByAmount.getFlowTarget());
                        summary.setTarAmntOfBackAc((kpiTargetByAmount.getBackTarget() / 100) * backAmount);
                        summary.setTarAmntOfBackAcPerc(kpiTargetByAmount.getBackTarget());
                        summary.setTarAmntOfSaveAc((kpiTargetByAmount.getSaveTarget() / 100) * saveAmount);
                        summary.setTarAmntOfSaveAcPerc(kpiTargetByAmount.getSaveTarget());
                        summary.setTarAmntOverDue((kpiTargetByAmount.getOverDueTaret() / 100) * overDueAmount);
                        summary.setTarAmntOverDuePerc(kpiTargetByAmount.getOverDueTaret());


                        //Achievement amount not found yet


                        //Shortfall
                        summary.setShortFallBack(summary.getTarAmntOfBackAc() - summary.getAmntOfBackAc());
                        summary.setShortFallFlow(summary.getTarAmntOfFlowAc() - summary.getAmntOfFlowAc());
                        summary.setShortFallSave(summary.getTarAmntOfSaveAc() - summary.getAmntOfSaveAc());

                        //Performance
                        summary.setSavePerformance((summary.getAmntOfSaveAc() / summary.getTarAmntOfSaveAc()) * 100);
                        summary.setBackPerformance((summary.getAmntOfBackAc() / summary.getTarAmntOfBackAc()) * 100);
                        if (summary.getAmntOfFlowAc() > 0)
                            summary.setFlowPerformance((summary.getAmntOfFlowAc() / summary.getTarAmntOfFlowAc()) * 100);
                        summary.setOverDuePerformance((summary.getAmntOverDue() / summary.getTarAmntOverDue()) * 100);

                        //weight percentage
                        summary.setWeightBackPerc((weightBackPerc / 100) * amountOfEachDpd);
                        summary.setWeightSavePerc((weightSavePerc / 100) * amountOfEachDpd);

                        //weight Performance calculation need to change after getting achievement
                        summary.setWeightBackPerformmance((summary.getWeightBackPerc() / weightBackPerc) * 100);
                        summary.setWeightSavePerformmance((summary.getWeightSavePerc() / weightSavePerc) * 100);

                        summary.setAmntOverDue(overDueAmnt);
                        summary.setTarAmntOverDue((kpiTargetByAmount.getOverDueTaret() / 100) * amountOfEachDpd);
                        summary.setTarAmntOverDuePerc(kpiTargetByAmount.getOverDueTaret());
                        summary.setOverDuePerformance((summary.getAmntOverDue() / summary.getTarAmntOverDue()) * 100);
                        summary.setShortFallOverDue(summary.getTarAmntOverDue() - overDueAmnt);

                        summary.setAmntRegular(regularAmnt);
                        summary.setTarAmntRegular((kpiTargetByAmount.getRegularTarget() / 100) * amountOfEachDpd);
                        summary.setAmntRegularPerc(kpiTargetByAmount.getRegularTarget());
                        summary.setRegularPerFormance((regularAmnt / kpiTargetByAmount.getRegularTarget()));
                        summary.setShortFallRegular(summary.getTarAmntOverDue() - regularAmnt);

                        //summary.setWeightSavePerformmance(((weightSavePerc*summary.getSavePerformance)/saveAmount));
                        //summary.setWeightBackPerformmance((weightBackPerc*amountOfBack)/backAmount);
                        if (amountOfFlow > 0)
                            summary.setWeightFlowPerformmance((weightFlowPerc * amountOfFlow) / flowAmount);
                        //Flow Performance calculation need to change after getting achievement


                        //AmountWiseSummaryModel

                        amountOfUnTouched = amountOfEachDpd - amountOftouchedAcc;
                        amountOfPending = amountOfUnTouched + amountOfBrokenPtp;

                        if (amountOfPending > amountOfEachDpd)
                            amountOfPending = amountOfEachDpd;


                        AmountWiseSummaryModel summaryModel = new AmountWiseSummaryModel();

                        summaryModel.setPG(productType.getName());
                        summaryModel.setTotalNumber(amountPerPG);
                        //summaryModel.setPerProductPerc((amountPerPG/totalDistAmount)*100);
                        summaryModel.setProductAndDpd(dpdBucket.getBucketName());
                        summaryModel.setNumberPerDpd(amountOfEachDpd);
                        summaryModel.setTotalPerc((amountOfEachDpd / amountPerPG) * (float) 100);
                        summaryModel.setTouchedNumber(amountOftouchedAcc);
                        summaryModel.setTouchedPerc((amountOftouchedAcc / amountOfEachDpd) * 100);
                        summaryModel.setUnTouchedNumber(amountOfUnTouched);
                        summaryModel.setUnTouchedPerc((amountOfUnTouched / amountOfEachDpd) * 100);
                        summaryModel.setTotalPending(amountOfPending);
                        summaryModel.setPendingPerc((amountOfPending / amountOfEachDpd) * 100);
                        summaryModel.setTotalParRel(amountOfParRel);
                        summaryModel.setParRelPerc((amountOfParRel / amountOfEachDpd) * 100);
                        summaryModel.setTotalParRelRem(amountOfTotalPar - amountOfParRel);
                        summaryModel.setParRelRemPerc((summaryModel.getTotalParRelRem() / amountOfEachDpd) * 100);
                        summaryModel.setTotalParQ(amountOfParQ);
                        summaryModel.setParQPerc((amountOfParQ / amountOfEachDpd) * 100);
                        summaryModel.setTotalNplRel(amountOfNplRel);
                        summaryModel.setNplRelPerc((amountOfNplRel / amountOfEachDpd) * 100);
                        summaryModel.setTotalNplRem(amountOfTotalNPL - amountOfNplRel);
                        summaryModel.setNplRemPerc((summaryModel.getTotalNplRem() / amountOfEachDpd) * 100);
                        summaryModel.setTotalNplQ(amountOfNplQ);
                        summaryModel.setNplQPerc((amountOfNplQ / amountOfEachDpd) * 100);

                        summary.setAmountWiseSummaryModel(summaryModel);

                        productWiseSummaryList.add(summary);

                    }
                    amountOfEachDpd = 0;
                    amountOfFlow = 0;
                    amountOfSave = 0;
                    amountOfBack = 0;
                    weightBackPerc = 0;
                    weightFlowPerc = 0;
                    weightSavePerc = 0;
                    regularAmnt = 0;


                    //AmountWiseSummaryModel
                    amountOfEachDpd = 0;
                    amountPerPG = 0;
                    amountOftouchedAcc = 0;
                    amountOfParRel = 0;
                    amountOfParQ = 0;
                    amountOfNplRel = 0;
                    amountOfNplQ = 0;
                    amountOfUnTouched = 0;
                    amountOfPending = 0;
                    amountOfBrokenPtp = 0;
                    amountOfTotalPar = 0;
                    amountOfTotalNPL = 0;

                }


            }
        }
        return productWiseSummaryList;
    }


    @RequestMapping(value = "summary_detail_account/loan")
    @ResponseBody
    public List<ProductWiseSummary2> getSummaryDetailLoanAccount(@RequestParam(value = "userPin") String userPin,
                                                                 @RequestParam(value = "unit") String unit,
                                                                 @RequestParam(value = "designation") String designation,
                                                                 @RequestParam(value = "userId") String userId,
                                                                 HttpSession session) {
        List<ProductWiseSummary2> productWiseSummaryList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);

        List<LoanAccountDistributionInfo> accountDistributionList = (List<LoanAccountDistributionInfo>) session.getAttribute("loanDistributionList");

        if (accountDistributionList.size() < 1)
            return productWiseSummaryList;

        List<LoanKPITargetByAccountEntity> kpiTargetList = loanKPITargetByAccountService.getAllData();


        double noOfSaveAc = 0;
        double noOfBackAc = 0;
        double noOfAcPerPT = 0;
        double noOfAcEachDpd = 0;
        double noOfFlowAc = 0;
        double weightBackPerc = 0;
        double weightFlowPerc = 0;
        double weightSavePerc = 0;
        double regularAmnt = 0;
        double overDueAmnt = 0;

        List<LoanKPITargetWeightByAmountEntity> weightByAmountList = new ArrayList<>();

        if (accountDistributionList.size() > 0)
            weightByAmountList = loanKPITargetWeightByAmountService.getAllData(accountDistributionList.get(0).getCreatedDate());
        else
            weightByAmountList = loanKPITargetWeightByAmountService.getAllData(new Date());

        //AccountWiseSummaryModel
        List<DPDBucketEntity> bucketEntities = dpdBucketService.getActiveList();
        List<AccountWiseSummaryModel> summaryModelList = new ArrayList<>();
        List<ProductTypeEntity> ptList = productTypeRepository.findByProductGroupEntityCardAccount(unit);
        List<PARaccountRuleLoanEntity> paRaccountRuleLoanList = paRaccountRuleLoanService.getActiveList();
        List<PARqueueAccRuleLoanEntity> paRqueueAccRuleList = paRqueueAccRuleLoanService.getActiveList();
        List<NPLAccountRuleEntity> nplAccountRuleList = nplAccountRuleService.getActiveList();
        List<NPLQueueAccRuleEntity> nplQueueAccRuleList = nplQueueAccService.getActiveList();
        ParReleaseLoan parRelAmountLoan = paRreleaseAmountLoanService.getPAR();
        NplReleaseLoan npLreleaseAmountLoan = npLreleaseAmountLoanService.getNPL();

        Long numberPerPG = 0l;
        Long numOfParRel = 0l;
        Long numOfParQ = 0l;
        Long numOfNplRel = 0l;
        Long numOfNplQ = 0l;
        Long numberOfEachDpd = 0l;
        Long numberOftouchedAcc = 0l;
        Long numberOfUnTouched = 0l;
        Long numberOfPending = 0l;
        Long numOfBrokenPtp = 0l;


        Long numOfTotalPar = 0l;
        Long numOfTotalNpl = 0l;

        for (LoanKPITargetByAccountEntity kpiTargetByAcc : kpiTargetList) {

            for (ProductTypeEntity productType : kpiTargetByAcc.getProductType()) {
                if (!designation.toUpperCase().equals("DEALER")) {
                    for (PeopleAllocationLogicInfo logic : allocationList) {
                        //amountPerPT=amountPerPT+dashboardService.getAmountPerPt(logic.getDealer().getPin(),productType.getCode());
                        noOfAcPerPT = noOfAcPerPT + dashboardService.getNumberOfAcPerPt(logic.getDealer().getPin(), productType.getCode());
                    }
                } else {
                    //amountPerPT=dashboardService.getAmountPerPt(userPin,productType.getCode());
                    noOfAcPerPT = noOfAcPerPT + dashboardService.getNumberOfAcPerPt(userPin, productType.getCode());
                }

                for (DPDBucketEntity dpdBucket : kpiTargetByAcc.getDpdBucket()) {

                    double saveAmount = 0;
                    double MO_DPD;
                    double dayDiff;
                    double backAmount = 0;
                    double totalDaysInMonth = dateUtils.getTotalDaysInMonth();
                    double monthEndDpd;
                    double emiPerDay;
                    Duration day;

                    //System.err.println("DIST LIST "+accountDistributionList.size());
                    for (LoanAccountDistributionInfo distributionInfo : accountDistributionList) {
                        for (LoanKPITargetWeightByAmountEntity weight : weightByAmountList) {
                            if (weight.getProductType().contains(productType) && weight.getDpdBucket().contains(dpdBucket)) {
                                weightBackPerc = weight.getBackWeight();
                                weightSavePerc = weight.getSaveWeight();
                                weightFlowPerc = weight.getSaveWeight();
                            }
                        }

                        if (distributionInfo.getDpdBucket().toUpperCase().equals(dpdBucket.getBucketName().toUpperCase()) && distributionInfo.getSchemeCode().toUpperCase().equals(productType.getCode().toUpperCase())) {
                            noOfAcEachDpd++;
                            overDueAmnt++;
                            if (distributionInfo.getOpeningOverDue() == 0 && distributionInfo.getDpd() == 0)
                                regularAmnt++;

                            Date monthEndDate = dateUtils.getMonthEndDate();
                            Date monthOpenDate = distributionInfo.getCreatedDate();

                            LocalDate localDate1 = monthOpenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            LocalDate localDate2 = monthEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                            day = Duration.between(localDate1.atStartOfDay(), localDate2.atStartOfDay());

                            //long ld=day.toDays();
                            dayDiff = (double) day.toDays();
                            //System.err.println("DIFF "+diff.toDays());
                            MO_DPD = distributionInfo.getDpd();
                            if (distributionInfo.getDpdBucket().toUpperCase().equals("X")) {
                                saveAmount = ((MO_DPD + (dayDiff + 1 - 29.50)) * distributionInfo.getEmiAmount()) / 30;
                                distributionInfo.setBackAmount(0);
                                if (saveAmount > 0) {
                                    noOfSaveAc++;
                                }
                            } else {
                                double highestDpdRange = Double.parseDouble(distributionInfo.getDpdBucket()) + 29.50;
                                saveAmount = ((MO_DPD + (dayDiff + 1 - highestDpdRange)) * distributionInfo.getEmiAmount()) / 30;

                                if (saveAmount > 0) {
                                    noOfSaveAc++;
                                }
                                double highestDpdRangeBack = Double.parseDouble(distributionInfo.getDpdBucket()) - .50;
                                backAmount = ((MO_DPD + dayDiff + 1 - highestDpdRangeBack) * distributionInfo.getEmiAmount()) / 30;

                                if (backAmount > 0) {
                                    noOfBackAc++;
                                }
                            }

                            //AccountWiseSummaryModel

                            numberOfEachDpd++;

                            try {
                                double dpdAmnt = Double.parseDouble(distributionInfo.getDpdBucket());

                                for (PARaccountRuleLoanEntity entity : paRaccountRuleLoanList) {
                                    if (dpdAmnt >= entity.getMinDpd() && dpdAmnt <= entity.getMaxDpd()) {
                                        // numOfPerRel++;
                                        numOfTotalPar++;
                                    }
                                }

                                if (parRelAmountLoan.getDpdBucketNames().contains(distributionInfo.getDpdBucket()))
                                    numOfParRel++;

                                if (npLreleaseAmountLoan.getDpdBucketNames().contains(distributionInfo.getDpdBucket()))
                                    numOfNplRel++;

                                for (PARqueueAccRuleLoanEntity parQ : paRqueueAccRuleList) {
                                    if (parQ.getMinDpd() <= dpdAmnt && parQ.getProductTypeList().contains(productType)) {
                                        numOfParQ++;
                                    }
                                }

                            } catch (Exception e) {
                                //System.out.println(e.getMessage());
                            }

                            //NPL
                            for (NPLAccountRuleEntity nplAccountRuleEntity : nplAccountRuleList) {
                                if (nplAccountRuleEntity.getDpdBucketList().contains(dpdBucket) && nplAccountRuleEntity.getProductTypeList().contains(productType))
                                    numOfNplRel++;
                            }

                            for (NPLQueueAccRuleEntity nplQueue : nplQueueAccRuleList) {
                                if (nplQueue.getDpdBucketList().contains(dpdBucket) && nplQueue.getProductTypeList().contains(productType))
                                    numOfNplQ++;
                            }

                            int count = 0;
                            try {
                                List<LoanPtp> loanPtps = dashboardService.getLoanPtpByCustomer(distributionInfo.getLoanAccountBasicInfo().getCustomer().getId());
                                if (!loanPtps.isEmpty()) {
                                    for (LoanPtp ptp : loanPtps) {
                                        if (ptp.getCreatedBy().equals(userId))
                                            count++;
                                        if (ptp.getLoan_ptp_status().equals("broken"))
                                            numOfBrokenPtp++;
                                    }
                                }

                            } catch (Exception e) {
                                //System.out.println(e.getMessage());
                            }
                            try {
                                if (dashboardService.getTotalDailyNoteNumber(distributionInfo.getLoanAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
                                    count++;
                            } catch (Exception e) {

                            }

                            try {
                                if (dashboardService.getTotalFollowUpNumber(distributionInfo.getLoanAccountBasicInfo().getCustomer().getId()) > 0)
                                    count++;
                            } catch (Exception e) {

                            }

                            try {
                                if (dashboardService.getTotalDiaryNoteNumber(distributionInfo.getLoanAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
                                    count++;
                            } catch (Exception e) {

                            }
                            try {
                                if (dashboardService.getTotalFollowUpNumber(distributionInfo.getLoanAccountBasicInfo().getCustomer().getId()) > 0)
                                    count++;
                            } catch (Exception e) {

                            }

                            if (count > 0) {
                                numberOftouchedAcc++;
                            }
                        }
                    }
                    if (noOfAcEachDpd > 0) {
                        ProductWiseSummary2 summary = new ProductWiseSummary2();

                        summary.setDpdBucket(dpdBucket.getBucketName());
                        summary.setProduct(productType.getName());
                        summary.setNoOfAcEachDpd(noOfAcEachDpd);
                        //Target for amount
                        summary.setTarOfSaveAc((kpiTargetByAcc.getSaveTarget() / 100) * noOfSaveAc);
                        summary.setTarOfSaveAcPerc(kpiTargetByAcc.getSaveTarget());
                        summary.setTarOfBackAc((kpiTargetByAcc.getBackTarget() / 100) * noOfBackAc);
                        summary.setTarOfBackAcPerc(kpiTargetByAcc.getBackTarget());
                        summary.setTarOfFlowAc((kpiTargetByAcc.getFlowTarget() / 100) * noOfFlowAc);
                        summary.setTarOfFlowAcPerc(kpiTargetByAcc.getFlowTarget());

                        //Achievement amount not found yet


                        //Shortfall
                        summary.setShortFallBack(summary.getTarOfBackAc() - summary.getAmntOfBackAc());
                        summary.setShortFallFlow(summary.getTarOfFlowAc() - summary.getAmntOfFlowAc());
                        summary.setShortFallSave(summary.getTarAmntOfSaveAc() - summary.getAmntOfSaveAc());


                        //Performance

                        summary.setSavePerformance((summary.getNoOfSaveAc() / summary.getTarOfSaveAc()) * 100);
                        summary.setBackPerformance((summary.getNoOfBackAc() / summary.getTarOfBackAc()) * 100);
                        summary.setFlowPerformance((summary.getNoOfFlowAc() / summary.getTarOfFlowAc()) * 100);


                        //weight
                        summary.setWeightSavePerc((weightSavePerc / 100) * noOfAcEachDpd);
                        summary.setWeightBackPerc((weightBackPerc / 100) * noOfAcEachDpd);
                        summary.setWeightFlowPerc((weightFlowPerc / 100) * noOfAcEachDpd);
                        //Flow is kept for achievement

                        summary.setWeightSavePerformmance((summary.getWeightSavePerc() / weightSavePerc) * 100);
                        summary.setWeightBackPerformmance((summary.getWeightBackPerc() / weightBackPerc) * 100);

                        summary.setRegularAcNo(regularAmnt);
                        summary.setTarNoOfAcRegular((kpiTargetByAcc.getRegularTarget() / 100) * noOfAcEachDpd);
                        summary.setAmntRegularPerc(kpiTargetByAcc.getRegularTarget());
                        summary.setRegularPerFormance((regularAmnt / kpiTargetByAcc.getRegularTarget()));
                        summary.setShortFallRegular(summary.getTarNoOfAcRegular() - regularAmnt);
//                        if(noOfFlowAc>0)
//                            summary.setWeightFlowPerformmance((summary.getWeightFlowPerc()/)/flowAmount);

                        //AccountWiseSummaryModel
                        numberOfUnTouched = numberOfEachDpd - numberOftouchedAcc;
                        numberOfPending = numberOfUnTouched + numOfBrokenPtp;

                        if (numberOfPending > numberOfEachDpd)
                            numberOfPending = numberOfEachDpd;

                        AccountWiseSummaryModel summaryModel = new AccountWiseSummaryModel();

                        summaryModel.setPG(productType.getName());
                        summaryModel.setTotalNumber(numberPerPG);
//                    summaryModel.setPerProductPerc(((double) numberPerPG/ numOfTotalDistAcc)*100);
                        summaryModel.setProductAndDpd(dpdBucket.getBucketName());
                        summaryModel.setNumberPerDpd(numberOfEachDpd);
                        summaryModel.setTotalPerc(((float) numberOfEachDpd / (float) numberPerPG) * (float) 100);
                        summaryModel.setTouchedNumber(numberOftouchedAcc);
                        summaryModel.setTouchedPerc(((float) numberOftouchedAcc / (float) numberOfEachDpd) * 100);
                        summaryModel.setUnTouchedNumber(numberOfUnTouched);
                        summaryModel.setUnTouchedPerc(((float) numberOfUnTouched / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalPending(numberOfPending);
                        summaryModel.setPendingPerc(((float) numberOfPending / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalParRel(numOfParRel);
                        summaryModel.setParRelPerc(((float) numOfParRel / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalParRelRem(numOfTotalPar - numOfParRel);
                        summaryModel.setParRelRemPerc(((float) summaryModel.getTotalParRelRem() / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalParQ(numOfParQ);
                        summaryModel.setParQPerc(((float) numOfParQ / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalNplRel(numOfNplRel);
                        summaryModel.setNplRelPerc(((float) numOfNplRel / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalNplRem(numOfTotalNpl - numOfNplRel);
                        summaryModel.setNplRemPerc(((float) summaryModel.getTotalNplRem() / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalNplQ(numOfNplQ);
                        summaryModel.setNplQPerc(((float) numOfNplQ / (float) numberOfEachDpd) * 100);

                        summary.setAccountWiseSummaryModel(summaryModel);
                        productWiseSummaryList.add(summary);

                    }

                    noOfAcEachDpd = 0;


                    numberOfEachDpd = 0l;

                    //AccountWiseSummaryModel
                    numberPerPG = 0l;
                    numberOftouchedAcc = 0l;
                    numOfParRel = 0l;
                    numOfParQ = 0l;
                    numOfNplRel = 0l;
                    numOfNplQ = 0l;
                    numberOfUnTouched = 0l;
                    numberOfPending = 0l;
                    numOfBrokenPtp = 0l;
                    numOfTotalPar = 0l;
                    numOfTotalNpl = 0l;

                }


            }
        }
        return productWiseSummaryList;
    }

    @GetMapping(value = "/card/secured_card")
    @ResponseBody
    List<CardAccountDistributionInfo> getSecuredCard(@RequestParam(value = "userPin") String userPin,
                                                     @RequestParam(value = "unit") String unit,
                                                     @RequestParam(value = "designation") String designation,
                                                     @RequestParam(value = "userId") String userId) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(userPrincipal.getEmpId());

        List<CardAccountDistributionInfo> distributionList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();

        if (!employeeInfoEntity.getDesignation().getName().toUpperCase().equals("DEALER")) {
            allocationList = dashboardService.getAllDealerList(employeeInfoEntity.getId().toString(), employeeInfoEntity.getDesignation().getName(), "Card");

            for (PeopleAllocationLogicInfo logic : allocationList) {
                List<CardAccountDistributionInfo> temp = dashboardService.getSecuredCard(logic.getDealer().getPin());
                distributionList.addAll(temp);
            }

        } else {
            distributionList = dashboardService.getSecuredCard(employeeInfoEntity.getPin());
        }

        for (CardAccountDistributionInfo dist : distributionList) {
            dist.getCardAccountBasicInfo().getCustomer();
            dist.getCardAccountBasicInfo().getCardNo();
        }

        return distributionList;
    }


    @GetMapping(value = "targetByManager/amountWise/loan")
    @ResponseBody
    public List<ProductWiseSummary> getProductWiseKpiVsAchvLoan(@RequestParam(value = "userPin") String userPin,
                                                                @RequestParam(value = "unit") String unit,
                                                                @RequestParam(value = "designation") String designation,
                                                                @RequestParam(value = "userId") String userId,
                                                                HttpSession session) {
        List<ProductWiseSummary> productWiseSummaryList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);

        List<LoanAccountDistributionInfo> accountDistributionList = (List<LoanAccountDistributionInfo>) session.getAttribute("loanDistributionList");

        if (accountDistributionList.size() < 1)
            return productWiseSummaryList;

//        if(!designation.toUpperCase().equals("DEALER"))
//        {
//            allocationList=dashboardService.getAllDealerList(userId,designation,unit);
//
//            for(PeopleAllocationLogicInfo logic : allocationList)
//            {
//                List<LoanAccountDistributionInfo> temp=dashboardService.getAccountListByUser(logic.getDealer().getPin());
//                accountDistributionList.addAll(temp);
//            }
//
//        }else {
//            accountDistributionList=dashboardService.getAccountListByUser(userPin);
//        }


        List<DealerAmountTargetByManager> kpiTargetList = new ArrayList<>();

        try {
            kpiTargetList = dealerAmountTargetByManagerDao.getList(accountDistributionList.get(0).getCreatedDate());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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


        List<LoanKPITargetWeightByAmountEntity> weightByAmountList = loanKPITargetWeightByAmountService.getAllData(accountDistributionList.get(0).getCreatedDate());

        for (DealerAmountTargetByManager kpiTargetByAmount : kpiTargetList) {

            for (ProductTypeEntity productType : kpiTargetByAmount.getProductTypeEntityList()) {
                if (!designation.toUpperCase().equals("DEALER")) {
                    allocationList = dashboardService.getAllDealerList(userId, designation, unit);
                    for (PeopleAllocationLogicInfo logic : allocationList) {
                        amountPerPT = dashboardService.getAmountPerPt(logic.getDealer().getPin(), productType.getCode());
                        //noOfAcPerPT=noOfAcPerPT+dashboardService.getNumberOfAcPerPt(logic.getDealer().getPin(),productType.getCode());
                    }
                } else {
                    amountPerPT = dashboardService.getAmountPerPt(userPin, productType.getCode());
                    //noOfAcPerPT=noOfAcPerPT+dashboardService.getNumberOfAcPerPt(userPin,productType.getCode());
                }

                for (DPDBucketEntity dpdBucket : kpiTargetByAmount.getDpdBucketEntityList()) {

                    double saveAmount = 0;
                    double MO_DPD;
                    double dayDiff;
                    double backAmount = 0;
                    double overDueAmount = 0;
                    double flowAmount = 0;
                    //double noOfAcEachDpd=0;
                    //double totalDaysInMonth=getTotalDaysIMonth();
                    //double monthEndDpd;
                    //double emiPerDay;
                    double overDueAmnt = 0;
                    double regularAmnt = 0;
                    Double totalOutstanding = 0d;

                    Duration day;

                    //System.err.println("DIST LIST "+accountDistributionList.size());
                    // List<LoanAccountDistributionInfo> checkedItem=new ArrayList<>();
                    for (LoanAccountDistributionInfo distributionInfo : accountDistributionList) {

                        for (LoanKPITargetWeightByAmountEntity weight : weightByAmountList) {
                            if (weight.getProductType().contains(productType) && weight.getDpdBucket().contains(dpdBucket)) {
                                weightBackPerc = weight.getBackWeight();
                                weightSavePerc = weight.getSaveWeight();
                                weightFlowPerc = weight.getFlowWeight();
                                weightregularPerc = weight.getRegularWeight();
                                weightOverDuePerc = weight.getOverDueWeight();
                            }
                        }
                        if (distributionInfo.getDpdBucket().toUpperCase().equals(dpdBucket.getBucketName().toUpperCase()) && distributionInfo.getSchemeCode().toUpperCase().equals(productType.getCode().toUpperCase())) {
                            // checkedItem.add(distributionInfo);
                            totalOutstanding = totalOutstanding + Double.parseDouble(distributionInfo.getOutStanding());
                            //regularAmnt=regularAmnt+distributionInfo.get
                            overDueAmount = overDueAmount + distributionInfo.getOpeningOverDue();

                            amountOfEachDpd = amountOfEachDpd + Double.parseDouble(distributionInfo.getOutStanding());

                            Date monthEndDate = dateUtils.getMonthEndDate();
                            Date monthOpenDate = distributionInfo.getCreatedDate();

                            LocalDate localDate1 = monthOpenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            LocalDate localDate2 = monthEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                            day = Duration.between(localDate1.atStartOfDay(), localDate2.atStartOfDay());

                            //long ld=day.toDays();
                            dayDiff = (double) day.toDays();
                            //System.err.println("DIFF "+diff.toDays());
                            MO_DPD = distributionInfo.getDpd();

                            List<LoanPayment> loanPayment = dashboardService.getLoanPaymentByAcc(distributionInfo.getLoanAccountBasicInfo().getAccountNo(), distributionInfo.getCreatedDate());
                            double totalPaidAmnt = 0;
                            if (loanPayment != null) {

                                for (LoanPayment paymentDetails : loanPayment) {
                                    totalPaidAmnt = totalPaidAmnt + paymentDetails.getPayment();
                                }
                            }

                            if (distributionInfo.getDpdBucket().toUpperCase().equals("X")) {
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

                            //                           if (distributionInfo.getOpeningOverDue() == 0 && distributionInfo.getDpd() == 0)
//                                regularAmnt = regularAmnt + Double.parseDouble(distributionInfo.getOutStanding());

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

                    //accountDistributionList.removeAll(checkedItem);

                    if (amountOfEachDpd > 0) {
                        ProductWiseSummary summary = new ProductWiseSummary();

                        summary.setDpdBucket(dpdBucket.getBucketName());
                        summary.setProduct(productType.getName());
                        summary.setAmntEachDpd(amountOfEachDpd);

                        //back
                        summary.setAmntOfBackAc(backAmount);
                        summary.setTarAmntOfBackAc((kpiTargetByAmount.getBackAmountTarget() / 100) * amountOfEachDpd);
                        summary.setTarAmntOfBackAcPerc(kpiTargetByAmount.getBackAmountTarget());
                        summary.setShortFallBack(summary.getTarAmntOfBackAc() - summary.getAmntOfBackAc());
                        if (backAmount > 0) {
                            summary.setBackPerformance((summary.getAmntOfBackAc() / summary.getTarAmntOfBackAc()) * 100);
                            summary.setAmntOfBackAcPerc((summary.getAmntOfBackAc() / amountOfEachDpd) * 100);
                        }
                        summary.setWeightBackPerc(weightBackPerc);
                        summary.setWeightBackPerformmance(weightBackPerc * summary.getBackPerformance());


                        //save
                        summary.setAmntOfSaveAc(amountOfSave);
                        summary.setTarAmntOfSaveAcPerc(kpiTargetByAmount.getSaveAmountTarget());
                        summary.setTarAmntOfSaveAc((kpiTargetByAmount.getSaveAmountTarget() / 100) * amountOfEachDpd);
                        summary.setShortFallSave(summary.getTarAmntOfSaveAc() - summary.getAmntOfSaveAc());
                        if (amountOfSave > 0) {
                            summary.setSavePerformance((summary.getAmntOfSaveAc() / summary.getTarAmntOfSaveAc()) * 100);
                            summary.setAmntOfSaveAcPerc((summary.getAmntOfSaveAc() / amountOfEachDpd) * 100);
                        }
                        summary.setWeightSavePerc(weightSavePerc);
                        summary.setWeightSavePerformmance(weightSavePerc * summary.getSavePerformance());

//                        summary.setShortFallSave(summary.getTarAmntOfSaveAc()-summary.getAmntOfSaveAc());
//                        summary.setSavePerformance((summary.getAmntOfSaveAc()/summary.getTarAmntOfSaveAc())*100);
//                        summary.setWeightSavePerc(weightSavePerc);
//                        summary.setWeightSavePerformmance(weightSavePerc*summary.getSavePerformance());
//                        summary.setTarAmntOfSaveAc((kpiTargetByAmount.getSaveTarget()/100)*saveAmount);
//                        summary.setTarAmntOfSaveAcPerc(kpiTargetByAmount.getSaveTarget());

                        //overdue
                        summary.setAmntOverDue(overDueAmnt);
                        summary.setTarAmntOverDue((kpiTargetByAmount.getCashCollectionTarget() / 100) * amountOfEachDpd);
                        summary.setTarAmntOverDuePerc(kpiTargetByAmount.getCashCollectionTarget());
                        summary.setShortFallOverDue(summary.getTarAmntOverDue() - overDueAmnt);
                        if (overDueAmnt > 0) {
                            summary.setOverDuePerformance((summary.getAmntOverDue() / summary.getTarAmntOverDue()) * 100);
                            summary.setAmntOverDuePerc((summary.getAmntOverDue() / amountOfEachDpd) * 100);
                        }
                        summary.setWeightOverdDuePerc(weightOverDuePerc);
                        summary.setWeightOverDuePerformmance(weightOverDuePerc * summary.getOverDuePerformance());

                        //regular


                        summary.setAmntRegular(regularAmnt);
                        summary.setTarAmntRegular((kpiTargetByAmount.getRegularAmountTarget() / 100) * amountOfEachDpd);
                        summary.setTarAmntRegularPerc(kpiTargetByAmount.getRegularAmountTarget());
                        summary.setAmntRegularPerc(kpiTargetByAmount.getRegularAmountTarget());
                        if (regularAmnt > 0) {
                            summary.setRegularPerFormance((regularAmnt / kpiTargetByAmount.getRegularAmountTarget()) * 100);
                            summary.setAmntRegularPerc((summary.getAmntRegular() / amountOfEachDpd) * 100);

                        }
                        summary.setShortFallRegular(summary.getTarAmntOverDue() - regularAmnt);
                        summary.setWeightRegularPerc(weightregularPerc);
                        summary.setWeightRegularPerformmance(weightregularPerc * summary.getRegularPerFormance());

                        //flow
                        summary.setTarAmntOfFlowAc((kpiTargetByAmount.getFlowAmountTarget() / 100) * amountOfEachDpd);
                        summary.setTarAmntOfFlowAcPerc(kpiTargetByAmount.getFlowAmountTarget());
                        summary.setAmntOfFlowAc(amountOfEachDpd - summary.getAmntOfSaveAc());
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
        //System.err.println("RES "+productWiseSummaryList.size());
        return productWiseSummaryList;
    }

    @GetMapping(value = "targetByManager/accountWise/loan")
    @ResponseBody
    public List<ProductWiseSummary> getProductWiseKpiVsAchvLoanByAc(@RequestParam(value = "userPin") String userPin,
                                                                    @RequestParam(value = "unit") String unit,
                                                                    @RequestParam(value = "designation") String designation,
                                                                    @RequestParam(value = "userId") String userId,
                                                                    HttpSession session) {
        List<ProductWiseSummary> productWiseSummaryList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);

        List<LoanAccountDistributionInfo> accountDistributionList = (List<LoanAccountDistributionInfo>) session.getAttribute("loanDistributionList");

        if (accountDistributionList.size() < 1)
            return productWiseSummaryList;

        List<DealerAccountTargetByManager> kpiTargetList = dealerAccountTargetByManagerDao.getActiveList(accountDistributionList.get(0).getCreatedDate());


        double noOfSaveAc = 0;
        double noOfBackAc = 0;
        double noOfAcPerPT = 0;
        double noOfAcEachDpd = 0;
        double noOfFlowAc = 0;
        double weightBackPerc = 0;
        double weightFlowPerc = 0;
        double weightSavePerc = 0;
        double overDueAmnt = 0;
        double regularAmnt = 0;
        double weightregularPerc = 0;
        double weightOverDuePerc = 0;


        List<LoanKPITargetWeightByAccountEntity> weightByAccountList = loanKPITargetWeightByAccountService.getAllData();

        for (DealerAccountTargetByManager kpiTargetByAcc : kpiTargetList) {

            for (ProductTypeEntity productType : kpiTargetByAcc.getProductTypeEntityList()) {
                if (!designation.toUpperCase().equals("DEALER")) {
                    for (PeopleAllocationLogicInfo logic : allocationList) {
                        //amountPerPT=amountPerPT+dashboardService.getAmountPerPt(logic.getDealer().getPin(),productType.getCode());
                        noOfAcPerPT = noOfAcPerPT + dashboardService.getNumberOfAcPerPt(logic.getDealer().getPin(), productType.getCode());
                    }
                } else {
                    //amountPerPT=dashboardService.getAmountPerPt(userPin,productType.getCode());
                    noOfAcPerPT = noOfAcPerPT + dashboardService.getNumberOfAcPerPt(userPin, productType.getCode());
                }

                for (DPDBucketEntity dpdBucket : kpiTargetByAcc.getDpdBucketEntityList()) {

                    double saveAmount = 0;
                    double MO_DPD;
                    double dayDiff;
                    double backAmount = 0;
                    double totalDaysInMonth = dateUtils.getTotalDaysInMonth();
                    double monthEndDpd;
                    double emiPerDay;
                    Duration day;

                    //List<LoanAccountDistributionInfo> checkedItem=new ArrayList<>();
                    //System.err.println("DIST LIST "+accountDistributionList.size());
                    for (LoanAccountDistributionInfo distributionInfo : accountDistributionList) {
                        for (LoanKPITargetWeightByAccountEntity weight : weightByAccountList) {
                            if (weight.getProductType().contains(productType) && weight.getDpdBucket().contains(dpdBucket)) {
                                weightBackPerc = weight.getBackWeight();
                                weightSavePerc = weight.getSaveWeight();
                                weightFlowPerc = weight.getSaveWeight();
                                weightregularPerc = weight.getRegularWeight();
                                weightOverDuePerc = weight.getOverDueWeight();
                            }
                        }

                        if (distributionInfo.getDpdBucket().toUpperCase().equals(dpdBucket.getBucketName().toUpperCase()) && distributionInfo.getSchemeCode().toUpperCase().equals(productType.getCode().toUpperCase())) {
                            noOfAcEachDpd++;
                            overDueAmnt++;
                            if (distributionInfo.getOpeningOverDue() == 0 && distributionInfo.getDpd() == 0)
                                regularAmnt++;

                            Date monthEndDate = dateUtils.getMonthEndDate();
                            Date monthOpenDate = distributionInfo.getCreatedDate();

                            LocalDate localDate1 = monthOpenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            LocalDate localDate2 = monthEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                            day = Duration.between(localDate1.atStartOfDay(), localDate2.atStartOfDay());

                            //long ld=day.toDays();
                            dayDiff = (double) day.toDays();
                            //System.err.println("DIFF "+diff.toDays());
                            MO_DPD = distributionInfo.getDpd();

                            if (distributionInfo.getDpdBucket().toUpperCase().equals("X")) {
//                                if(accountDpd.getCurrentDpd()>=30)
//                                {
//
//                                }

                                saveAmount = ((MO_DPD + (dayDiff + 1 - 29.50)) * distributionInfo.getEmiAmount()) / 30;
                                distributionInfo.setBackAmount(0);
                                if (saveAmount > 0) {
                                    noOfSaveAc++;
                                }
                            } else {
                                double highestDpdRange = Double.parseDouble(distributionInfo.getDpdBucket()) + 29.50;
                                saveAmount = ((MO_DPD + (dayDiff + 1 - highestDpdRange)) * distributionInfo.getEmiAmount()) / 30;

                                if (saveAmount > 0) {
                                    noOfSaveAc++;
                                }
                                double highestDpdRangeBack = Double.parseDouble(distributionInfo.getDpdBucket()) - .50;
                                backAmount = ((MO_DPD + dayDiff + 1 - highestDpdRangeBack) * distributionInfo.getEmiAmount()) / 30;

                                if (backAmount > 0) {
                                    noOfBackAc++;
                                }
                            }

                        }

                    }
                    //accountDistributionList.removeAll(checkedItem);
                    if (noOfAcEachDpd > 0) {
                        ProductWiseSummary summary = new ProductWiseSummary();

//                        summary.setTarAmntOfFlowAc();
                        summary.setDpdBucket(dpdBucket.getBucketName());
                        summary.setProduct(productType.getName());
                        summary.setNoOfAcEachDpd(noOfAcEachDpd);
                        //Target for amount

                        //back
                        summary.setAmntOfBackAc(noOfBackAc);
                        summary.setTarAmntOfBackAc((kpiTargetByAcc.getBackAccountTarget() / 100) * noOfAcEachDpd);
                        summary.setTarAmntOfBackAcPerc(kpiTargetByAcc.getBackAccountTarget());
                        summary.setShortFallBack(summary.getTarAmntOfBackAc() - summary.getAmntOfBackAc());
                        if (noOfBackAc > 0) {
                            summary.setBackPerformance((summary.getAmntOfBackAc() / summary.getTarAmntOfBackAc()) * 100);
                            summary.setNoOfBackAcPerc((summary.getAmntOfBackAc() / noOfAcEachDpd) * 100);
                        }
                        summary.setWeightBackPerc(weightBackPerc);
                        summary.setWeightBackPerformmance(weightBackPerc * summary.getBackPerformance());


                        //save
                        summary.setAmntOfSaveAc(noOfSaveAc);
                        summary.setTarAmntOfSaveAcPerc(kpiTargetByAcc.getSaveAccountTarget());
                        summary.setTarAmntOfSaveAc((kpiTargetByAcc.getSaveAccountTarget() / 100) * noOfAcEachDpd);
                        summary.setShortFallSave(summary.getTarAmntOfSaveAc() - summary.getAmntOfSaveAc());
                        if (noOfSaveAc > 0) {
                            summary.setSavePerformance((summary.getAmntOfSaveAc() / summary.getTarAmntOfSaveAc()) * 100);
                            summary.setAmntOfSaveAcPerc((summary.getAmntOfSaveAc() / noOfAcEachDpd) * 100);
                        }
                        summary.setWeightSavePerc(weightSavePerc);
                        summary.setWeightSavePerformmance(weightSavePerc * summary.getSavePerformance());

//                        summary.setShortFallSave(summary.getTarAmntOfSaveAc()-summary.getAmntOfSaveAc());
//                        summary.setSavePerformance((summary.getAmntOfSaveAc()/summary.getTarAmntOfSaveAc())*100);
//                        summary.setWeightSavePerc(weightSavePerc);
//                        summary.setWeightSavePerformmance(weightSavePerc*summary.getSavePerformance());
//                        summary.setTarAmntOfSaveAc((kpiTargetByAmount.getSaveTarget()/100)*saveAmount);
//                        summary.setTarAmntOfSaveAcPerc(kpiTargetByAmount.getSaveTarget());

                        //overdue
                        summary.setAmntOverDue(overDueAmnt);
                        summary.setTarAmntOverDue((kpiTargetByAcc.getCashCollectionTarget() / 100) * noOfAcEachDpd);
                        summary.setTarAmntOverDuePerc(kpiTargetByAcc.getCashCollectionTarget());
                        summary.setShortFallOverDue(summary.getTarAmntOverDue() - overDueAmnt);
                        if (overDueAmnt > 0) {
                            summary.setOverDuePerformance((summary.getAmntOverDue() / summary.getTarAmntOverDue()) * 100);
                            summary.setAmntOverDuePerc((summary.getAmntOverDue() / noOfAcEachDpd) * 100);
                        }
                        summary.setWeightOverdDuePerc(weightOverDuePerc);
                        summary.setWeightOverDuePerformmance(weightOverDuePerc * summary.getOverDuePerformance());

                        //regular
                        summary.setAmntRegular(regularAmnt);
                        summary.setTarAmntRegular((kpiTargetByAcc.getRegularAccountTarget() / 100) * noOfAcEachDpd);
                        summary.setTarAmntRegularPerc(kpiTargetByAcc.getRegularAccountTarget());
                        summary.setAmntRegularPerc(kpiTargetByAcc.getRegularAccountTarget());
                        if (regularAmnt > 0) {
                            summary.setRegularPerFormance((regularAmnt / kpiTargetByAcc.getRegularAccountTarget()) * 100);
                            summary.setAmntRegularPerc((summary.getAmntRegular() / noOfAcEachDpd) * 100);

                        }
                        summary.setShortFallRegular(summary.getTarAmntOverDue() - regularAmnt);
                        summary.setWeightRegularPerc(weightregularPerc);
                        summary.setWeightRegularPerformmance(weightregularPerc * summary.getRegularPerFormance());

                        //flow
                        summary.setTarAmntOfFlowAc((kpiTargetByAcc.getFlowAccountTarget() / 100) * noOfAcEachDpd);
                        summary.setTarAmntOfFlowAcPerc(kpiTargetByAcc.getFlowAccountTarget());
                        summary.setAmntOfFlowAc(noOfAcEachDpd - summary.getAmntOfSaveAc());
                        summary.setShortFallFlow(summary.getTarAmntOfFlowAc() - summary.getAmntOfFlowAc());
                        if (summary.getAmntOfFlowAc() > 0) {
                            summary.setFlowPerformance((summary.getAmntOfFlowAc() / summary.getTarAmntOfFlowAc()) * 100);
                            summary.setAmntOfFlowAcPerc((summary.getAmntOfFlowAc() / noOfAcEachDpd) * 100);
                        }
                        summary.setWeightFlowPerc(weightFlowPerc);
                        summary.setWeightFlowPerformmance(weightFlowPerc * summary.getFlowPerformance());

                        productWiseSummaryList.add(summary);

                    }

                    noOfAcEachDpd = 0;
                    noOfAcEachDpd = 0;
                    noOfFlowAc = 0;
                    noOfSaveAc = 0;
                    noOfBackAc = 0;
                    weightBackPerc = 0;
                    weightFlowPerc = 0;
                    weightSavePerc = 0;
                    weightregularPerc = 0;
                    weightOverDuePerc = 0;
                    overDueAmnt = 0;
                    regularAmnt = 0;

                }


            }
        }
        return productWiseSummaryList;
    }


}
