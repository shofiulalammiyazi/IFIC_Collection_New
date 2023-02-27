package com.unisoft.collection.scheduler;

import com.unisoft.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.unisoft.collection.dashboard.*;
import com.unisoft.collection.settings.NPLreleaseAmount.NPLReleaseAmountEntity;
import com.unisoft.collection.settings.NPLreleaseAmount.NPLReleaseAmountService;
import com.unisoft.collection.settings.PARAccountRuleCard.PARAccountRuleCardEntity;
import com.unisoft.collection.settings.PARAccountRuleCard.PARAccountRuleCardService;
import com.unisoft.collection.settings.PARreleaseAmount.PARReleaseAmountEntity;
import com.unisoft.collection.settings.PARreleaseAmount.PARReleaseAmountService;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeRepository;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeRepository;
import com.unisoft.collection.settings.nplAccountRuleCard.NPLAccountRuleCardEntity;
import com.unisoft.collection.settings.nplAccountRuleCard.NPLAccountRuleCardService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.retail.card.dataEntry.ptp.CardPtp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.unisoft.collection.settingshelper.SettingsHelper.getEndDate;
import static com.unisoft.collection.settingshelper.SettingsHelper.getStartDate;

@Component
@Slf4j
@EnableAsync
public class ScheduledCornJobCardDistribution {
    private ThreadKpiCalCard threadKpiCalCard;
    private EmployeeRepository employeeRepository;
    private PARAccountRuleCardService parAccountRuleCardService;
    private PARReleaseAmountService paRreleaseAmountService;
    private NPLAccountRuleCardService NPLAccountRuleCardService;
    private NPLReleaseAmountService npLreleaseAmountService;
    private ProductTypeRepository productTypeRepository;
    private AgeCodeRepository ageCodeRepository;
    private DashboardService dashboardService;
    private AmountWiseSummaryModelDao amountWiseSummaryModelDao;
    private AccountWiseSummaryModelDao accountWiseSummaryModelDao;

    public ScheduledCornJobCardDistribution(ThreadKpiCalCard threadKpiCalCard, EmployeeRepository employeeRepository, PARAccountRuleCardService parAccountRuleCardService, PARReleaseAmountService paRreleaseAmountService, NPLAccountRuleCardService NPLAccountRuleCardService, NPLReleaseAmountService npLreleaseAmountService, ProductTypeRepository productTypeRepository, AgeCodeRepository ageCodeRepository, DashboardService dashboardService, AmountWiseSummaryModelDao amountWiseSummaryModelDao, AccountWiseSummaryModelDao accountWiseSummaryModelDao) {
        this.threadKpiCalCard = threadKpiCalCard;
        this.employeeRepository = employeeRepository;
        this.parAccountRuleCardService = parAccountRuleCardService;
        this.paRreleaseAmountService = paRreleaseAmountService;
        this.NPLAccountRuleCardService = NPLAccountRuleCardService;
        this.npLreleaseAmountService = npLreleaseAmountService;
        this.productTypeRepository = productTypeRepository;
        this.ageCodeRepository = ageCodeRepository;
        this.dashboardService = dashboardService;
        this.amountWiseSummaryModelDao = amountWiseSummaryModelDao;
        this.accountWiseSummaryModelDao = accountWiseSummaryModelDao;
    }

    //Insert data to  CARD_KPI_ACH_ENTITY, CARD_KPI_ACH_MANAGER_ENTITY
    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void calculateKpi() {
        log.info("CALCULATE SCHEDULER INVOKED: " + new Date().toString());
        threadKpiCalCard.calculateKpiAch();
    }

    //Insert data to ACCOUNT_WISE_SUMMARY_MODEL
    @Async
    @Scheduled(cron = "0 0 4 * * ?")
    public void distributionStatByAcc() {
        log.info("DISTRIBUTION STATUS BY ACCOUNT SCHEDULER INVOKED: " + new Date().toString());
        Date startDate = getStartDate();
        Date endDate = getEndDate();

        List<EmployeeInfoEntity> dealerList = employeeRepository.findByCardDistribution(startDate, endDate);
        List<AccountWiseSummaryModel> summaryModelList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();

        PARAccountRuleCardEntity parLogic = parAccountRuleCardService.getPar();
        PARReleaseAmountEntity paRreleaseAmount = paRreleaseAmountService.getPAR();

        NPLAccountRuleCardEntity npLlogicSetUp = NPLAccountRuleCardService.getNPL();
        NPLReleaseAmountEntity npLreleaseAmount = npLreleaseAmountService.getNPL();

        String userPin = "";
        String unit = "";
        String designation = "";
        String userId = "";

        for (EmployeeInfoEntity dealer : dealerList) {
            userPin = dealer.getPin();
            unit = dealer.getUnit();
            designation = dealer.getDesignation().getName();
            userId = dealer.getId().toString();

            List<ProductTypeEntity> ptList = productTypeRepository.findByDelaerPin(dealer.getPin(), startDate, endDate);

            for (ProductTypeEntity typeEntity : ptList) {

                List<AgeCodeEntity> ageCodeList = ageCodeRepository.findByDealerPin(dealer.getPin(), startDate, endDate);

                for (AgeCodeEntity ageCode : ageCodeList) {

                    Long numberPerPG = 0l;
                    Long numOfPerRel = 0l;
                    Long numOfParQ = 0l;
                    Long numOfNplRel = 0l;
                    Long numOfNplQ = 0l;
                    Long numberOfEachDpd = 0l;
                    Long numberOftouchedAcc = 0l;
                    Long numberOfUnTouched = 0l;
                    Long numberOfPending = 0l;
                    Long numOfBrokenPtp = 0l;
                    Long numOfTotalPar = 0l;
                    Long numOfTotalNPL = 0l;

                    if (!designation.toUpperCase().equals("DEALER")) {
                        for (PeopleAllocationLogicInfo logic : allocationList) {
                            allocationList = dashboardService.getAllDealerList(userId, designation, unit);
                            numberPerPG = numberPerPG + dashboardService.getNumberOfAcPerPtCard(logic.getDealer().getPin(), typeEntity.getCode());
                        }
                    } else {
                        numberPerPG = dashboardService.getNumberOfAcPerPtCard(userPin, typeEntity.getCode());
                    }

                    List<CardAccountDistributionInfo> distributionList = dashboardService.getAccountDistInfoAllUserCurrentmonthForDealer(dealer.getPin());
                    for (CardAccountDistributionInfo dist : distributionList) {
                        if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(typeEntity.getCode().toUpperCase())) {
                            numberOfEachDpd++;
                            try {
                                if (parLogic.getAgeCodeList().contains(ageCode))
                                    numOfTotalPar++;
                                if (paRreleaseAmount.getAgeCodeList().contains(ageCode))
                                    numOfPerRel++;

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }

                            try {
                                if (npLlogicSetUp.getAgeCodeList().contains(ageCode))
                                    numOfTotalNPL++;
                                if (npLreleaseAmount.getAgeCodeList().contains(ageCode))
                                    numOfNplRel++;
                            } catch (Exception e) {

                            }
                        }
                        int count = 0;


                        try {
                            List<CardPtp> cardPtpList = dashboardService.getCardPtpByCustomer(dist.getCardAccountBasicInfo().getCustomer().getId());
                            if (!cardPtpList.isEmpty()) {
                                count++;
                                for (CardPtp cardPtp : cardPtpList) {
                                    if (cardPtp.getCard_ptp_status().equals("broken"))
                                        numOfBrokenPtp++;
                                }
                            }

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        try {
                            if (dashboardService.getTotalDailyNoteNumber(dist.getCardAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
                                count++;
                        } catch (Exception e) {

                        }

                        try {
                            if (dashboardService.getTotalFollowUpNumber(dist.getCardAccountBasicInfo().getCustomer().getId()) > 0)
                                count++;
                        } catch (Exception e) {

                        }

                        try {
                            if (dashboardService.getTotalDiaryNoteNumber(dist.getCardAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
                                count++;
                        } catch (Exception e) {

                        }

                        try {
                            if (dashboardService.getVisitLedgerByAcc(dist.getCardAccountBasicInfo().getCardNo(), unit, userId) > 0)
                                count++;

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        try {
                            if (dashboardService.getNoOfHotNotesByCustomerId(dist.getCardAccountBasicInfo().getCustomer().getId(), unit, userId) > 0)
                                count++;
                        } catch (Exception e) {

                        }

                        if (count > 0) {
                            numberOftouchedAcc++;
                        }

                    }

                    if (numberOfEachDpd > 0l) {
                        numberOfUnTouched = numberOfEachDpd - numberOftouchedAcc;
                        numberOfPending = numberOfUnTouched + numOfBrokenPtp;

                        if (numberOfPending > numberOfEachDpd)
                            numberOfPending = numberOfEachDpd;

                        AccountWiseSummaryModel summaryModel = accountWiseSummaryModelDao.getByDealerPin(userPin, typeEntity.getName() + "-" + typeEntity.getCode(), ageCode.getName());

                        if (summaryModel == null)
                            summaryModel = new AccountWiseSummaryModel();

                        summaryModel.setUpdateDate(new Date());
                        summaryModel.setDealerPin(userPin);
                        summaryModel.setCusId(userId);

                        summaryModel.setPG(typeEntity.getName() + "-" + typeEntity.getCode());
                        summaryModel.setTotalNumber(numberPerPG);
                        summaryModel.setProductAndDpd(ageCode.getName());
                        summaryModel.setNumberPerDpd(numberOfEachDpd);
                        if (numberPerPG > 0)
                            summaryModel.setTotalPerc(((float) numberOfEachDpd / (float) numberPerPG) * (float) 100);
                        summaryModel.setTouchedNumber(numberOftouchedAcc);
                        if (numberOfEachDpd > 0)
                            summaryModel.setTouchedPerc(((float) numberOftouchedAcc / (float) numberOfEachDpd) * 100);
                        summaryModel.setUnTouchedNumber(numberOfUnTouched);
                        if (numberOfEachDpd > 0)
                            summaryModel.setUnTouchedPerc(((float) numberOfUnTouched / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalPending(numberOfPending);
                        if (numberOfEachDpd > 0)
                            summaryModel.setPendingPerc(((float) numberOfPending / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalParRel(numOfPerRel);
                        if (numberOfEachDpd > 0)
                            summaryModel.setParRelPerc(((float) numOfPerRel / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalParRelRem(numOfTotalPar - numOfPerRel);
                        if (numberOfEachDpd > 0)
                            summaryModel.setParRelRemPerc(((float) summaryModel.getTotalParRelRem() / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalParQ(numOfParQ);
                        if (numberOfEachDpd > 0)
                            summaryModel.setParQPerc(((float) numOfParQ / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalNplRel(numOfNplRel);
                        if (numberOfEachDpd > 0)
                            summaryModel.setNplRelPerc(((float) numOfNplRel / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalNplRem(numOfTotalNPL - numOfNplRel);
                        if (numberOfEachDpd > 0)
                            summaryModel.setNplRemPerc(((float) summaryModel.getTotalNplRem() / (float) numberOfEachDpd) * 100);
                        summaryModel.setTotalNplQ(numOfNplQ);
                        if (numberOfEachDpd > 0)
                            summaryModel.setNplQPerc(((float) numOfNplQ / (float) numberOfEachDpd) * 100);

                        accountWiseSummaryModelDao.saveOrUpdateData(summaryModel);
                    }

                }
            }

        }


    }

    //Insert data to AMOUNT_WISE_SUMMARY_MODEL
    @Async
    @Scheduled(cron = "0 0 4 * * ?")
    public void distributionStat() {
        log.info("DISTRIBUTION STATUS BY AMOUNT SCHEDULER INVOKED: " + new Date().toString());
        List<EmployeeInfoEntity> dealerList = employeeRepository.findByCardDistribution(getStartDate(), getEndDate());

        List<AmountWiseSummaryModel> summaryModelList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();

        PARAccountRuleCardEntity parLogic = parAccountRuleCardService.getPar();
        PARReleaseAmountEntity paRreleaseAmount = paRreleaseAmountService.getPAR();

        NPLAccountRuleCardEntity npLlogicSetUp = NPLAccountRuleCardService.getNPL();
        NPLReleaseAmountEntity npLreleaseAmount = npLreleaseAmountService.getNPL();

        String userPin = "";
        String unit = "";
        String designation = "";
        String userId = "";

        for (EmployeeInfoEntity dealer : dealerList) {
            userPin = dealer.getPin();
            unit = dealer.getUnit();
            designation = dealer.getDesignation().getName();
            userId = dealer.getId().toString();

            List<ProductTypeEntity> ptList = productTypeRepository.findByDelaerPin(dealer.getPin(), getStartDate(), getEndDate());

            for (ProductTypeEntity typeEntity : ptList) {

                List<AgeCodeEntity> ageCodeList = ageCodeRepository.findByDealerPin(dealer.getPin(), getStartDate(), getEndDate());

                for (AgeCodeEntity ageCode : ageCodeList) {
                    double amountPerPG = 0;
                    double amountOfPerRel = 0;
                    double amountOfParQ = 0;
                    double amountOfNplRel = 0;
                    double amountOfNplQ = 0;
                    double amountOfEachAgeCode = 0;
                    double amountOftouchedAcc = 0;
                    double amountberOfUnTouched = 0;
                    double amountOfPending = 0;
                    double amountOfBrokenPtp = 0;
                    double amountOfTotalPar = 0;
                    double amountOfTotalNPL = 0;

                    if (!designation.toUpperCase().equals("DEALER")) {
                        allocationList = dashboardService.getAllDealerList(userId, designation, unit);
                        for (PeopleAllocationLogicInfo logic : allocationList) {
                            amountPerPG = amountPerPG + dashboardService.getTotalAmountPerPtCard(logic.getDealer().getPin(), typeEntity.getCode());
                        }
                    } else {
                        amountPerPG = dashboardService.getTotalAmountPerPtCard(userPin, typeEntity.getCode());
                    }

                    List<CardAccountDistributionInfo> distributionList = dashboardService.getAccountDistInfoAllUserCurrentmonthForDealer(dealer.getPin());

                    for (CardAccountDistributionInfo dist : distributionList) {
                        if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(typeEntity.getCode().toUpperCase())) {

                            amountOfEachAgeCode = amountOfEachAgeCode + Double.parseDouble(dist.getOutstandingAmount());
                            try {
                                if (parLogic.getAgeCodeList().contains(ageCode))
                                    amountOfTotalPar = amountOfTotalPar + Double.parseDouble(dist.getOutstandingAmount());
                                if (paRreleaseAmount.getAgeCodeList().contains(ageCode))
                                    amountOfPerRel = amountOfPerRel + Double.parseDouble(dist.getOutstandingAmount());


                            } catch (Exception e) {
                                //System.out.println(e.getMessage());
                            }

                            try {
                                if (npLlogicSetUp.getAgeCodeList().contains(ageCode))
                                    amountOfTotalNPL = amountOfTotalNPL + Double.parseDouble(dist.getOutstandingAmount());
                                if (npLreleaseAmount.getAgeCodeList().contains(ageCode))
                                    amountOfNplRel = amountOfNplRel + Double.parseDouble(dist.getOutstandingAmount());
                            } catch (Exception e) {

                            }

                        }
                        int count = 0;

                        try {
                            List<CardPtp> cardPtpList = dashboardService.getCardPtpByCustomer(dist.getCardAccountBasicInfo().getCustomer().getId());
                            if (!cardPtpList.isEmpty()) {
                                count++;
                                for (CardPtp cardPtp : cardPtpList) {
                                    if (cardPtp.getCard_ptp_status().equals("broken"))
                                        amountOfBrokenPtp = amountOfBrokenPtp + Double.parseDouble(dist.getOutstandingAmount());
                                }

                            }

                        } catch (Exception e) {
                            //System.out.println(e.getMessage());
                        }
                        try {
                            if (dashboardService.getTotalDailyNoteNumber(dist.getCardAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
                                count++;
                        } catch (Exception e) {

                        }

                        try {
                            if (dashboardService.getTotalFollowUpNumber(dist.getCardAccountBasicInfo().getCustomer().getId()) > 0)
                                count++;
                        } catch (Exception e) {

                        }

                        try {
                            if (dashboardService.getTotalDiaryNoteNumber(dist.getCardAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
                                count++;
                        } catch (Exception e) {

                        }

                        try {
                            if (dashboardService.getVisitLedgerByAcc(dist.getCardAccountBasicInfo().getCardNo(), unit, userId) > 0)
                                count++;

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        try {
                            if (dashboardService.getNoOfHotNotesByCustomerId(dist.getCardAccountBasicInfo().getCustomer().getId(), unit, userId) > 0)
                                count++;
                        } catch (Exception e) {

                        }
                        if (count > 0) {
                            amountOftouchedAcc = amountOftouchedAcc + Double.parseDouble(dist.getOutstandingAmount());
                        }

                    }

                    if (amountOfEachAgeCode > 0l) {
                        amountberOfUnTouched = amountOfEachAgeCode - amountOftouchedAcc;
                        amountOfPending = amountberOfUnTouched + amountOfBrokenPtp;

                        if (amountOfPending > amountOfEachAgeCode)
                            amountOfPending = amountOfEachAgeCode;

                        AmountWiseSummaryModel summaryModel = amountWiseSummaryModelDao.getDataByDealerpin(userPin, typeEntity.getName() + "-" + typeEntity.getCode(), ageCode.getName());

                        if (summaryModel == null)
                            summaryModel = new AmountWiseSummaryModel();

                        summaryModel.setUpdateDate(new Date());
//                        summaryModel.setDealerPin(userPin);
                        summaryModel.setCusId(userId);

                        summaryModel.setPG(typeEntity.getName() + "-" + typeEntity.getCode());
                        summaryModel.setTotalNumber(amountPerPG);
                        summaryModel.setProductAndDpd(ageCode.getName());

                        summaryModel.setNumberPerDpd(amountOfEachAgeCode);
                        if (amountPerPG > 0)
                            summaryModel.setTotalPerc((amountOfEachAgeCode / amountPerPG) * (float) 100);
                        summaryModel.setTouchedNumber(amountOftouchedAcc);
                        if (amountOfEachAgeCode > 0)
                            summaryModel.setTouchedPerc((amountOftouchedAcc / amountOfEachAgeCode) * 100);
                        summaryModel.setUnTouchedNumber(amountberOfUnTouched);
                        if (amountOfEachAgeCode > 0)
                            summaryModel.setUnTouchedPerc((amountberOfUnTouched / amountOfEachAgeCode) * 100);
                        summaryModel.setTotalPending(amountOfPending);
                        if (amountOfEachAgeCode > 0)
                            summaryModel.setPendingPerc((amountOfPending / amountOfEachAgeCode) * 100);
                        summaryModel.setTotalParRel(amountOfPerRel);
                        if (amountOfEachAgeCode > 0)
                            summaryModel.setParRelPerc((amountOfPerRel / amountOfEachAgeCode) * 100);
                        summaryModel.setTotalParRelRem(amountOfTotalPar - amountOfPerRel);
                        if (amountOfEachAgeCode > 0)
                            summaryModel.setParRelRemPerc((summaryModel.getTotalParRelRem() / amountOfEachAgeCode) * 100);
                        summaryModel.setTotalParQ(amountOfParQ);
                        if (amountOfEachAgeCode > 0)
                            summaryModel.setParQPerc((amountOfParQ / amountOfEachAgeCode) * 100);
                        summaryModel.setTotalNplRel(amountOfNplRel);
                        if (amountOfEachAgeCode > 0)
                            summaryModel.setNplRelPerc((amountOfNplRel / amountOfEachAgeCode) * 100);
                        summaryModel.setTotalNplRem(amountOfTotalNPL - amountOfNplRel);
                        if (amountOfEachAgeCode > 0)
                            summaryModel.setNplRemPerc((summaryModel.getTotalNplRem() / amountOfEachAgeCode) * 100);
                        summaryModel.setTotalNplQ(amountOfNplQ);
                        if (amountOfEachAgeCode > 0)
                            summaryModel.setNplQPerc((amountOfNplQ / amountOfEachAgeCode) * 100);

                        amountWiseSummaryModelDao.saveOrUpdateData(summaryModel);
                    }
                    amountOfEachAgeCode = 0;
                    amountPerPG = 0;
                    amountOftouchedAcc = 0;
                    amountOfPerRel = 0;
                    amountOfParQ = 0;
                    //numOfNplRel=0;
                    amountOfNplRel = 0;
                    amountOfNplQ = 0;
                    amountberOfUnTouched = 0;
                    amountOfPending = 0;
                    amountOfBrokenPtp = 0;
                }
            }

        }
    }
}
