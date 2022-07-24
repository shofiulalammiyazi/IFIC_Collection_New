package com.unisoft.collection.dashboard;
/*
  Created by Md.   Islam on 1/27/2020
*/

import com.unisoft.collection.KPI.Card.TargetByAmount.CardKPITargetByAmountService;
import com.unisoft.collection.KPI.Card.TargetByManagerAmount.DealerTargetCardManagerDao;
import com.unisoft.collection.settings.NPLreleaseAmount.NPLReleaseAmountService;
import com.unisoft.collection.settings.PARreleaseAmount.PARReleaseAmountService;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.collection.settings.nplAccountRuleCard.NPLAccountRuleCardService;
import com.unisoft.collection.settings.PARAccountRuleCard.PARAccountRuleCardService;
import com.unisoft.collection.settings.ageCode.AgeCodeRepository;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeRepository;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.collection.settings.productType.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping(value = "/kpi")
public class ThreadCardCalInitiator {

    @Autowired
    private ThreadKpiCalCard threadKpiCalCard;
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ThreadCardMOCal threadCardMOCal;
    @Autowired
    private PARAccountRuleCardService parAccountRuleCardService;
    @Autowired
    private PARReleaseAmountService paRreleaseAmountService;
    @Autowired
    private NPLReleaseAmountService npLreleaseAmountService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private AgeCodeService ageCodeService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AmountWiseSummaryModelDao amountWiseSummaryModelDao;
    @Autowired
    private AccountWiseSummaryModelDao  accountWiseSummaryModelDao;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private AgeCodeRepository ageCodeRepository;
    @Autowired
    private NPLAccountRuleCardService NPLAccountRuleCardService;
    @Autowired
    private CardKPITargetByAmountService cardKPITargetByAmountService;
    @Autowired
    private DealerTargetCardManagerDao dealerTargetCardManagerDao;
    @Autowired
    private CardBackendAccDetailService cardBackendAccDetailService;
    public static int interval=10;

    @GetMapping("/cal/mo_data")
    @ResponseBody
    public String calculateMoData() {
        Map<String, Integer> map = new HashMap<>();
        List<CardAccountDistributionInfo> distributionInfos=dashboardService.getAccountDistInfoAllUserCurrentmonth();
        int threadCount = 0;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        if(distributionInfos.size()>0)
        {
            for(CardAccountDistributionInfo distribution : distributionInfos)
            {
                try {
                    interval=interval+10;
                    if(interval >= 60)
                        interval=10;
                    Thread.sleep(interval);
                    threadCount++;

                    if(threadCount == 100)
                    {
                        Thread.sleep(10000);
                        threadCount=0;
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                String dealerPin = distribution.getDealerPin();
                EmployeeInfoEntity emp= employeeRepository.findByPin(dealerPin);
                threadCardMOCal.calThread(distribution,emp);
            }

        }else {
            return "No Account found for calculation";
        }

        return "Month opening Calculation Started...";
    }

//    @GetMapping(value = "/cal")
//    @ResponseBody
//    @Scheduled(cron = "0 0 1 * * ?")
//    public String calculateKpi() {
//        threadKpiCalCard.calculateKpiAch();
//
//        return "KPI Calculation Started...";
//    }
//
//
//    @GetMapping(value = "kpi_status_summary_by_acc")
//    @ResponseBody
//    @Scheduled(cron = "0 0 3 * * ?")
//    public void distributionStatByAcc() {
//
//        List<EmployeeInfoEntity> dealerList=employeeRepository.findByCardDistribution(getStartDate(), getEndDate());
//        List<AccountWiseSummaryModel> summaryModelList = new ArrayList<>();
//
////        if (distributionList.size() < 1)
////            return ;
//
//        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();
//
//        PARAccountRuleCardEntity  parLogic = parLogicSetupService.getPar();
//        PARReleaseAmountEntity paRreleaseAmount = paRreleaseAmountService.getPAR();
//
//        NPLAccountRuleCardEntity npLlogicSetUp = NPLAccountRuleCardService.getNPL();
//        NPLReleaseAmountEntity npLreleaseAmount = npLreleaseAmountService.getNPL();
//
//        String userPin="";
//        String unit="";
//        String designation="";
//        String userId="";
//
////        List<EmployeeInfoEntity> cardDealer=new ArrayList<>();
////        for(EmployeeInfoEntity dealer : dealerList)
////        {
////            try{
////                if(dealer.getUnit().toUpperCase().contains("CARD"))
////                    cardDealer.add(dealer);
////            }catch (Exception e)
////            {
////
////            }
////        }
////
////        dealerList=cardDealer;
//
//        for(EmployeeInfoEntity dealer : dealerList) {
//            userPin=dealer.getPin();
//            unit=dealer.getUnit();
//            designation=dealer.getDesignation().getName();
//            userId=dealer.getId().toString();
//
//            List<ProductTypeEntity> ptList =productTypeRepository.findByDelaerPin(dealer.getPin(), getStartDate(), getEndDate());
//
//            for (ProductTypeEntity typeEntity : ptList) {
//
//                List<AgeCodeEntity> ageCodeList =ageCodeRepository.findByDealerPin(dealer.getPin(), getStartDate(), getEndDate());
//
//                for (AgeCodeEntity ageCode : ageCodeList) {
//
//                    Long numberPerPG = 0l;
//                    Long numOfPerRel = 0l;
//                    Long numOfParQ = 0l;
//                    Long numOfNplRel = 0l;
//                    Long numOfNplQ = 0l;
//                    Long numberOfEachDpd = 0l;
//                    Long numberOftouchedAcc = 0l;
//                    Long numberOfUnTouched = 0l;
//                    Long numberOfPending = 0l;
//                    Long numOfBrokenPtp = 0l;
//                    Long numOfTotalPar = 0l;
//                    Long numOfTotalNPL = 0l;
//
//                    //numOfTotalDistAcc=numOfTotalDistAcc+numberPerPG;
//                    if (!designation.toUpperCase().equals("DEALER")) {
//                        for (PeopleAllocationLogicInfo logic : allocationList) {
//                            allocationList = dashboardService.getAllDealerList(userId, designation, unit);
//                            numberPerPG = numberPerPG + dashboardService.getNumberOfAcPerPtCard(logic.getDealer().getPin(), typeEntity.getCode());
//                        }
//                    } else {
//                        numberPerPG = dashboardService.getNumberOfAcPerPtCard(userPin, typeEntity.getCode());
//                    }
//
//                    //List<CardAccountDistributionInfo> checkedItem=new ArrayList<>();
//
//                    List<CardAccountDistributionInfo> distributionList = dashboardService.getAccountDistInfoAllUserCurrentmonthForDealer(dealer.getPin());
//
//                    for (CardAccountDistributionInfo dist : distributionList) {
//                        if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(typeEntity.getCode().toUpperCase())) {
//                            //checkedItem.add(dist);
//                            numberOfEachDpd++;
//
//                            try {
//                                //double dpdAmnt=Double.parseDouble(dist.getAgeCode());
//
////                            for(PARaccountRuleLoanEntity entity:paRaccountRuleLoanList)
////                            {
////                                if(dpdAmnt >= entity.getMinDpd() && dpdAmnt<=entity.getMaxDpd())
////                                {
////                                    numOfPerRel++;
////                                }
////                            }
//                                if (parLogic.getAgeCodeList().contains(ageCode))
//                                    numOfTotalPar++;
//                                if (paRreleaseAmount.getAgeCodeList().contains(ageCode))
//                                    numOfPerRel++;
//
////                            for(PARqueueAccRuleLoanEntity parQ:  paRqueueAccRuleList)
////                            {
////                                if(parQ.getMinDpd() <= dpdAmnt && parQ.getProductTypeList().contains(typeEntity))
////                                {
////                                    numOfParQ++;
////                                }
////                            }
//
//                            } catch (Exception e) {
//                             System.out.println(e.getMessage());
//                            }
//
//                            //NPL
////                        for(NPLAccountRuleEntity nplAccountRuleEntity: nplAccountRuleList)
////                        {
////                            if(nplAccountRuleEntity.getDpdBucketList().contains(dpd) && nplAccountRuleEntity.getProductTypeList().contains(typeEntity))
////                                numOfNplRel++;
////                        }
//
//                            try {
//                                if (npLlogicSetUp.getAgeCodeList().contains(ageCode))
//                                    numOfTotalNPL++;
//                                if (npLreleaseAmount.getAgeCodeList().contains(ageCode))
//                                    numOfNplRel++;
//                            } catch (Exception e) {
//
//                            }
//
////                        for(NPLQueueAccRuleEntity nplQueue: nplQueueAccRuleList)
////                        {
////                            if(nplQueue.getDpdBucketList().contains(dpd) && nplQueue.getProductTypeList().contains(typeEntity))
////                                numOfNplQ++;
////                        }
//                        }
//                        int count = 0;
//
//
//                        try {
//                            List<CardPtp> cardPtpList = dashboardService.getCardPtpByCustomer(dist.getCardAccountBasicInfo().getCustomer().getId());
//                            dist.getCardAccountBasicInfo().getCustomer().setCardPtpList(cardPtpList);
//
//                            if (dist.getCardAccountBasicInfo().getCustomer().getCardPtpList().size() > 0) {
//                                count++;
//                                for (CardPtp cardPtp : dist.getCardAccountBasicInfo().getCustomer().getCardPtpList()) {
////                                    if (cardPtp.getCreatedBy().equals(userId)) {
//
////                                    }
//                                    if (cardPtp.getCard_ptp_status().equals("broken"))
//                                        numOfBrokenPtp++;
//                                }
////                            for(CardPtp ptp: dist.getCardAccountBasicInfo().getCustomer().getCardPtpList())
////                            {
////                                if(ptp.getCard_ptp_status().equals("broken"))
////                                {
////                                    numOfBrokenPtp++;
////                                    break;
////                                }
////                            }
//                            }
//
//                        } catch (Exception e) {
//                         System.out.println(e.getMessage());
//                        }
//                        try {
//                            if (dashboardService.getTotalDailyNoteNumber(dist.getCardAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
//                                count++;
////                        if(dist.getCardAccountBasicInfo().getCustomer().getDailyNoteList().size()>0)
////                            count++;
//                        } catch (Exception e) {
//
//                        }
//
//                        try {
//                            if (dashboardService.getTotalFollowUpNumber(dist.getCardAccountBasicInfo().getCustomer().getId()) > 0)
//                                count++;
////                        if( dist.getCardAccountBasicInfo().getCustomer().getFollowUpList().size()>0 )
////                            count++;
//                        } catch (Exception e) {
//
//                        }
//
//                        try {
//                            if (dashboardService.getTotalDiaryNoteNumber(dist.getCardAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
//                                count++;
////                        if(dist.getCardAccountBasicInfo().getCustomer().getDairyNotesList().size()>0 )
////                            count++;
//                        } catch (Exception e) {
//
//                        }
//
//                        try {
//                            if (dashboardService.getVisitLedgerByAcc(dist.getCardAccountBasicInfo().getCardNo(), unit, userId) > 0)
//                                count++;
//
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//
//                        try {
//                            if (dashboardService.getNoOfHotNotesByCustomerId(dist.getCardAccountBasicInfo().getCustomer().getId(), unit, userId) > 0)
//                                count++;
//                        } catch (Exception e) {
//
//                        }
//
//                        if (count > 0) {
//                            numberOftouchedAcc++;
//                        }
//
//                    }
//
//                    //distributionList.removeAll(checkedItem);
//
//                    if (numberOfEachDpd > 0l) {
//                        numberOfUnTouched = numberOfEachDpd - numberOftouchedAcc;
//                        numberOfPending = numberOfUnTouched + numOfBrokenPtp;
//
//                        if (numberOfPending > numberOfEachDpd)
//                            numberOfPending = numberOfEachDpd;
//
//                        AccountWiseSummaryModel summaryModel = accountWiseSummaryModelDao.getByDealerPin(userPin,typeEntity.getName() + "-" + typeEntity.getCode(),ageCode.getName());
//
//                        if(summaryModel == null)
//                            summaryModel=new AccountWiseSummaryModel();
//
//                        summaryModel.setUpdateDate(new Date());
//                        summaryModel.setDealerPin(userPin);
//                        summaryModel.setCusId(userId);
//
//                        summaryModel.setPG(typeEntity.getName() + "-" + typeEntity.getCode());
//                        summaryModel.setTotalNumber(numberPerPG);
////                    summaryModel.setPerProductPerc(((double) numberPerPG/ numOfTotalDistAcc)*100);
//                        summaryModel.setProductAndDpd(ageCode.getName());
//                        summaryModel.setNumberPerDpd(numberOfEachDpd);
//                        if (numberPerPG > 0)
//                            summaryModel.setTotalPerc(((float) numberOfEachDpd / (float) numberPerPG) * (float) 100);
//                        summaryModel.setTouchedNumber(numberOftouchedAcc);
//                        if (numberOfEachDpd > 0)
//                            summaryModel.setTouchedPerc(((float) numberOftouchedAcc / (float) numberOfEachDpd) * 100);
//                        summaryModel.setUnTouchedNumber(numberOfUnTouched);
//                        if (numberOfEachDpd > 0)
//                            summaryModel.setUnTouchedPerc(((float) numberOfUnTouched / (float) numberOfEachDpd) * 100);
//                        summaryModel.setTotalPending(numberOfPending);
//                        if (numberOfEachDpd > 0)
//                            summaryModel.setPendingPerc(((float) numberOfPending / (float) numberOfEachDpd) * 100);
//                        summaryModel.setTotalParRel(numOfPerRel);
//                        if (numberOfEachDpd > 0)
//                            summaryModel.setParRelPerc(((float) numOfPerRel / (float) numberOfEachDpd) * 100);
//                        summaryModel.setTotalParRelRem(numOfTotalPar - numOfPerRel);
//                        if (numberOfEachDpd > 0)
//                            summaryModel.setParRelRemPerc(((float) summaryModel.getTotalParRelRem() / (float) numberOfEachDpd) * 100);
//                        summaryModel.setTotalParQ(numOfParQ);
//                        if (numberOfEachDpd > 0)
//                            summaryModel.setParQPerc(((float) numOfParQ / (float) numberOfEachDpd) * 100);
//                        summaryModel.setTotalNplRel(numOfNplRel);
//                        if (numberOfEachDpd > 0)
//                            summaryModel.setNplRelPerc(((float) numOfNplRel / (float) numberOfEachDpd) * 100);
//                        summaryModel.setTotalNplRem(numOfTotalNPL - numOfNplRel);
//                        if (numberOfEachDpd > 0)
//                            summaryModel.setNplRemPerc(((float) summaryModel.getTotalNplRem() / (float) numberOfEachDpd) * 100);
//                        summaryModel.setTotalNplQ(numOfNplQ);
//                        if (numberOfEachDpd > 0)
//                            summaryModel.setNplQPerc(((float) numOfNplQ / (float) numberOfEachDpd) * 100);
//
//                        accountWiseSummaryModelDao.saveOrUpdateData(summaryModel);
//
//
//                    }
//
//                }
//                //numOfTotalDistAcc=0;
//                //System.err.println("Dist :"+summaryModelList.size());
//            }
//
//        }
//
//
//    }
//
//    @GetMapping(value = "kpi_status_summary")
//    @ResponseBody
//    @Scheduled(cron = "0 0 4 * * ?")
//    public void distributionStat() {
//        List<EmployeeInfoEntity> dealerList=employeeRepository.findByCardDistribution(getStartDate(), getEndDate());
//        //System.err.println("SUPERVISOR DASH :"+distributionList.size());
//
////        List<EmployeeInfoEntity> dealerList=employeeService.getDealerList();
//
//
//        List<AmountWiseSummaryModel> summaryModelList = new ArrayList<>();
//
////        if (distributionList.size() < 1)
////            return ;
//
//        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();
////        List<ProductTypeEntity> ptList =productTypeService.getAllActive();
////        List<AgeCodeEntity> ageCodeList =ageCodeService.getActiveList();
////
////        for (PeopleAllocationLogicInfo logicInfo : allocationList) {
////            for (ProductTypeEntity typeEntity : logicInfo.getProductTypeEntity()) {
////                if (!ptList.contains(typeEntity))
////                    ptList.add(typeEntity);
////            }
////            for (AgeCodeEntity ageCode : logicInfo.getAgeCodeEntity()) {
////                if (!ageCodeList.contains(ageCode))
////                    ageCodeList.add(ageCode);
////            }
////        }
//
//
//        PARAccountRuleCardEntity  parLogic = parLogicSetupService.getPar();
//        PARReleaseAmountEntity paRreleaseAmount = paRreleaseAmountService.getPAR();
//
//        NPLAccountRuleCardEntity npLlogicSetUp = NPLAccountRuleCardService.getNPL();
//        NPLReleaseAmountEntity npLreleaseAmount = npLreleaseAmountService.getNPL();
//
////        List<ProductTypeEntity> tempProducttypeList = new ArrayList<>();
//
////        for (ProductTypeEntity typeEntity : ptList) {
////
////            for (AgeCodeEntity ageCode : ageCodeList) {
////                for (CardAccountDistributionInfo dist : distributionList) {
////                    if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(typeEntity.getCode().toUpperCase())) {
////                        {
////                            if(!tempProducttypeList.contains(typeEntity))
////                                tempProducttypeList.add(typeEntity);
////                        }
////                    }
////                }
////
////            }
////        }
//
////        ptList=tempProducttypeList;
//
//        String userPin="";
//        String unit="";
//        String designation="";
//        String userId="";
//
////        List<EmployeeInfoEntity> cardDealer=new ArrayList<>();
////        for(EmployeeInfoEntity dealer : dealerList)
////        {
////            try{
////                if(dealer.getUnit().toUpperCase().contains("CARD"))
////                    cardDealer.add(dealer);
////            }catch (Exception e)
////            {
////
////            }
////        }
////
////        dealerList=cardDealer;
//        for(EmployeeInfoEntity dealer : dealerList) {
//            userPin=dealer.getPin();
//            unit=dealer.getUnit();
//            designation=dealer.getDesignation().getName();
//            userId=dealer.getId().toString();
//
//            List<ProductTypeEntity> ptList =productTypeRepository.findByDelaerPin(dealer.getPin(), getStartDate(), getEndDate());
//
//            for (ProductTypeEntity typeEntity : ptList) {
//
//                List<AgeCodeEntity> ageCodeList =ageCodeRepository.findByDealerPin(dealer.getPin(), getStartDate(), getEndDate());
//
//                for (AgeCodeEntity ageCode : ageCodeList) {
//                    double amountPerPG = 0;
//                    double amountOfPerRel = 0;
//                    double amountOfParQ = 0;
//                    double amountOfNplRel = 0;
//                    double amountOfNplQ = 0;
//                    double amountOfEachAgeCode = 0;
//                    double amountOftouchedAcc = 0;
//                    double amountberOfUnTouched = 0;
//                    double amountOfPending = 0;
//                    double amountOfBrokenPtp = 0;
//                    double amountOfTotalPar = 0;
//                    double amountOfTotalNPL = 0;
//                    //amountPerPG=dashboardService.getTotalAmountPerPtCard(userPin,typeEntity.getCode());
//                    //numOfTotalDistAcc=numOfTotalDistAcc+numberPerPG;
//                    if (!designation.toUpperCase().equals("DEALER")) {
//                        allocationList = dashboardService.getAllDealerList(userId, designation, unit);
//                        for (PeopleAllocationLogicInfo logic : allocationList) {
//                            amountPerPG = amountPerPG + dashboardService.getTotalAmountPerPtCard(logic.getDealer().getPin(), typeEntity.getCode());
//                        }
//                    } else {
//                        amountPerPG = dashboardService.getTotalAmountPerPtCard(userPin, typeEntity.getCode());
//                    }
//
//                    //List<CardAccountDistributionInfo> checkedItem=new ArrayList<>();
//                    List<CardAccountDistributionInfo> distributionList = dashboardService.getAccountDistInfoAllUserCurrentmonthForDealer(dealer.getPin());
//
//                    for (CardAccountDistributionInfo dist : distributionList) {
//                        if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(typeEntity.getCode().toUpperCase())) {
//
//                            amountOfEachAgeCode = amountOfEachAgeCode + Double.parseDouble(dist.getOutstandingAmount());
//                            //checkedItem.add(dist);
//                            try {
//                                //double dpdAmnt=Double.parseDouble(dist.getAgeCode());
//
////                            for(PARaccountRuleLoanEntity entity:paRaccountRuleLoanList)
////                            {
////                                if(dpdAmnt >= entity.getMinDpd() && dpdAmnt<=entity.getMaxDpd())
////                                {
////                                    numOfPerRel++;
////                                }
////                            }
//                                if (parLogic.getAgeCodeList().contains(ageCode))
//                                    amountOfTotalPar = amountOfTotalPar + Double.parseDouble(dist.getOutstandingAmount());
//                                if (paRreleaseAmount.getAgeCodeList().contains(ageCode))
//                                    amountOfPerRel = amountOfPerRel + Double.parseDouble(dist.getOutstandingAmount());
//
////                            for(PARqueueAccRuleLoanEntity parQ:  paRqueueAccRuleList)
////                            {
////                                if(parQ.getMinDpd() <= dpdAmnt && parQ.getProductTypeList().contains(typeEntity))
////                                {
////                                    numOfParQ++;
////                                }
////                            }
//
//                            } catch (Exception e) {
//                                System.out.println(e.getMessage());
//                            }
//
//                            //NPL
////                        for(NPLAccountRuleEntity nplAccountRuleEntity: nplAccountRuleList)
////                        {
////                            if(nplAccountRuleEntity.getDpdBucketList().contains(dpd) && nplAccountRuleEntity.getProductTypeList().contains(typeEntity))
////                                numOfNplRel++;
////                        }
//
//                            try {
//                                if (npLlogicSetUp.getAgeCodeList().contains(ageCode))
//                                    amountOfTotalNPL = amountOfTotalNPL + Double.parseDouble(dist.getOutstandingAmount());
//                                if (npLreleaseAmount.getAgeCodeList().contains(ageCode))
//                                    amountOfNplRel = amountOfNplRel + Double.parseDouble(dist.getOutstandingAmount());
//                            } catch (Exception e) {
//
//                            }
//
////                        for(NPLQueueAccRuleEntity nplQueue: nplQueueAccRuleList)
////                        {
////                            if(nplQueue.getDpdBucketList().contains(dpd) && nplQueue.getProductTypeList().contains(typeEntity))
////                                numOfNplQ++;
////                        }
//                        }
//                        int count = 0;
//
//                        try {
//                            List<CardPtp> cardPtpList = dashboardService.getCardPtpByCustomer(dist.getCardAccountBasicInfo().getCustomer().getId());
//                            dist.getCardAccountBasicInfo().getCustomer().setCardPtpList(cardPtpList);
//
//                            if (dist.getCardAccountBasicInfo().getCustomer().getCardPtpList().size() > 0) {
//                                count++;
//                                for (CardPtp cardPtp : dist.getCardAccountBasicInfo().getCustomer().getCardPtpList()) {
////                                    if (cardPtp.getCreatedBy().equals(userId)) {
//
////                                    }
//                                    if (cardPtp.getCard_ptp_status().equals("broken"))
//                                        amountOfBrokenPtp = amountOfBrokenPtp + Double.parseDouble(dist.getOutstandingAmount());
//                                }
//
//                            }
//
//                        } catch (Exception e) {
//                            System.out.println(e.getMessage());
//                        }
//                        try {
//                            if (dashboardService.getTotalDailyNoteNumber(dist.getCardAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
//                                count++;
////                        if(dist.getCardAccountBasicInfo().getCustomer().getDailyNoteList().size()>0)
////                            count++;
//                        } catch (Exception e) {
//
//                        }
//
//                        try {
//                            if (dashboardService.getTotalFollowUpNumber(dist.getCardAccountBasicInfo().getCustomer().getId()) > 0)
//                                count++;
////                        if( dist.getCardAccountBasicInfo().getCustomer().getFollowUpList().size()>0 )
////                            count++;
//                        } catch (Exception e) {
//
//                        }
//
//                        try {
//                            if (dashboardService.getTotalDiaryNoteNumber(dist.getCardAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
//                                count++;
////                        if(dist.getCardAccountBasicInfo().getCustomer().getDairyNotesList().size()>0 )
////                            count++;
//                        } catch (Exception e) {
//
//                        }
//
//                        try {
//                            if (dashboardService.getVisitLedgerByAcc(dist.getCardAccountBasicInfo().getCardNo(), unit, userId) > 0)
//                                count++;
//
//                        } catch (Exception e) {
//                         System.out.println(e.getMessage());
//                        }
//
//                        try {
//                            if (dashboardService.getNoOfHotNotesByCustomerId(dist.getCardAccountBasicInfo().getCustomer().getId(), unit, userId) > 0)
//                                count++;
//                        } catch (Exception e) {
//
//                        }
//                        if (count > 0) {
//                            amountOftouchedAcc = amountOftouchedAcc + Double.parseDouble(dist.getOutstandingAmount());
//                        }
//
//                    }
//
//                    if (amountOfEachAgeCode > 0l) {
//                        amountberOfUnTouched = amountOfEachAgeCode - amountOftouchedAcc;
//                        amountOfPending = amountberOfUnTouched + amountOfBrokenPtp;
//
//                        if (amountOfPending > amountOfEachAgeCode)
//                            amountOfPending = amountOfEachAgeCode;
//
//                        AmountWiseSummaryModel summaryModel = amountWiseSummaryModelDao.getDataByDealerpin(userPin,typeEntity.getName() + "-" + typeEntity.getCode(),ageCode.getName());
//
//                        if(summaryModel == null)
//                            summaryModel=new AmountWiseSummaryModel();
//
//                        summaryModel.setUpdateDate(new Date());
//                        summaryModel.setDealerPin(userPin);
//                        summaryModel.setCusId(userId);
//
//                        summaryModel.setPG(typeEntity.getName() + "-" + typeEntity.getCode());
//                        summaryModel.setTotalNumber(amountPerPG);
////                    summaryModel.setPerProductPerc(((double) numberPerPG/ numOfTotalDistAcc)*100);
//                        summaryModel.setProductAndDpd(ageCode.getName());
//
//                        summaryModel.setNumberPerDpd(amountOfEachAgeCode);
//                        if (amountPerPG > 0)
//                            summaryModel.setTotalPerc((amountOfEachAgeCode / amountPerPG) * (float) 100);
//                        summaryModel.setTouchedNumber(amountOftouchedAcc);
//                        if (amountOfEachAgeCode > 0)
//                            summaryModel.setTouchedPerc((amountOftouchedAcc / amountOfEachAgeCode) * 100);
//                        summaryModel.setUnTouchedNumber(amountberOfUnTouched);
//                        if (amountOfEachAgeCode > 0)
//                            summaryModel.setUnTouchedPerc((amountberOfUnTouched / amountOfEachAgeCode) * 100);
//                        summaryModel.setTotalPending(amountOfPending);
//                        if (amountOfEachAgeCode > 0)
//                            summaryModel.setPendingPerc((amountOfPending / amountOfEachAgeCode) * 100);
//                        summaryModel.setTotalParRel(amountOfPerRel);
//                        if (amountOfEachAgeCode > 0)
//                            summaryModel.setParRelPerc((amountOfPerRel / amountOfEachAgeCode) * 100);
//                        summaryModel.setTotalParRelRem(amountOfTotalPar - amountOfPerRel);
//                        if (amountOfEachAgeCode > 0)
//                            summaryModel.setParRelRemPerc((summaryModel.getTotalParRelRem() / amountOfEachAgeCode) * 100);
//                        summaryModel.setTotalParQ(amountOfParQ);
//                        if (amountOfEachAgeCode > 0)
//                            summaryModel.setParQPerc((amountOfParQ / amountOfEachAgeCode) * 100);
//                        summaryModel.setTotalNplRel(amountOfNplRel);
//                        if (amountOfEachAgeCode > 0)
//                            summaryModel.setNplRelPerc((amountOfNplRel / amountOfEachAgeCode) * 100);
//                        summaryModel.setTotalNplRem(amountOfTotalNPL - amountOfNplRel);
//                        if (amountOfEachAgeCode > 0)
//                            summaryModel.setNplRemPerc((summaryModel.getTotalNplRem() / amountOfEachAgeCode) * 100);
//                        summaryModel.setTotalNplQ(amountOfNplQ);
//                        if (amountOfEachAgeCode > 0)
//                            summaryModel.setNplQPerc((amountOfNplQ / amountOfEachAgeCode) * 100);
//
//                        amountWiseSummaryModelDao.saveOrUpdateData(summaryModel);
//                    }
//                    amountOfEachAgeCode = 0;
//                    amountPerPG = 0;
//                    amountOftouchedAcc = 0;
//                    amountOfPerRel = 0;
//                    amountOfParQ = 0;
//                    //numOfNplRel=0;
//                    amountOfNplRel = 0;
//                    amountOfNplQ = 0;
//                    amountberOfUnTouched = 0;
//                    amountOfPending = 0;
//                    amountOfBrokenPtp = 0;
//                }
//                //numOfTotalDistAcc=0;
//                //System.err.println("Dist :"+summaryModelList.size());
//            }
//
//        }
////        System.err.println("SUPER DASH : " + summaryModelList.size());
////        return summaryModelList;
//    }

    Date getStartDate() {
        Calendar c1 = Calendar.getInstance();

        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.set(Calendar.HOUR_OF_DAY,0);
        c1.set(Calendar.MINUTE,0);
        c1.set(Calendar.SECOND,0);
        c1.set(Calendar.MILLISECOND,0);
        Date startDate=c1.getTime();

        return startDate;
    }

    Date getEndDate() {
        Calendar cal = Calendar.getInstance();
        int totalDays = cal.getActualMaximum(Calendar.DATE);

        Calendar c2=Calendar.getInstance();
        c2.set(Calendar.DAY_OF_MONTH,totalDays);
        c2.set(Calendar.HOUR_OF_DAY,23);
        c2.set(Calendar.MINUTE,59);
        c2.set(Calendar.SECOND,59);
        c2.set(Calendar.MILLISECOND,0);
        Date endDate=c2.getTime();

        return endDate;
    }

}
