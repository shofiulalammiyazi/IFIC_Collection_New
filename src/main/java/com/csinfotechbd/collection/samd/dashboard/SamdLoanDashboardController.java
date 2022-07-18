package com.csinfotechbd.collection.samd.dashboard;

import com.csinfotechbd.collection.KPI.Loan.TargetByAccount.LoanKPITargetByAccountEntity;
import com.csinfotechbd.collection.KPI.Loan.TargetByAccount.LoanKPITargetByAccountService;
import com.csinfotechbd.collection.KPI.Loan.TargetByAmount.LoanKPITargetByAmountEntity;
import com.csinfotechbd.collection.KPI.Loan.TargetByAmount.LoanKPITargetByAmountService;
import com.csinfotechbd.collection.KPI.Loan.TargetWeightByAccount.LoanKPITargetWeightByAccountEntity;
import com.csinfotechbd.collection.KPI.Loan.TargetWeightByAccount.LoanKPITargetWeightByAccountService;
import com.csinfotechbd.collection.KPI.Loan.TargetWeightByAmount.LoanKPITargetWeightByAmountEntity;
import com.csinfotechbd.collection.KPI.Loan.TargetWeightByAmount.LoanKPITargetWeightByAmountService;
import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.csinfotechbd.collection.dashboard.*;
import com.csinfotechbd.collection.samd.dashboard.performance.SamdDashboardPerformance;
import com.csinfotechbd.collection.samd.dashboard.performance.SamdDashboardPerformanceRepository;
import com.csinfotechbd.collection.samd.dashboard.shortfallDetails.SamdDashboardShortFallDetails;
import com.csinfotechbd.collection.samd.dashboard.shortfallDetails.SamdDashboardShortFallDetailsRepository;
import com.csinfotechbd.collection.samd.dataEntry.loanAccountDistribution.SamLoanAccountDistribution;
import com.csinfotechbd.collection.samd.dataEntry.loanAccountDistribution.SamLoanAccountDistributionService;
import com.csinfotechbd.collection.samd.dataEntry.loanAccountDistribution.SamLoanAccountDistributionSummary;
import com.csinfotechbd.collection.settings.NPLQueueAccRule.NPLQueueAccRuleEntity;
import com.csinfotechbd.collection.settings.NPLQueueAccRule.NPLQueueAccService;
import com.csinfotechbd.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanEntity;
import com.csinfotechbd.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanService;
import com.csinfotechbd.collection.settings.PARqueueAccRuleLoan.PARqueueAccRuleLoanEntity;
import com.csinfotechbd.collection.settings.PARqueueAccRuleLoan.PARqueueAccRuleLoanService;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.collection.settings.dpdBucket.DpdBucketRepository;
import com.csinfotechbd.collection.settings.nplAccountRule.NPLAccountRuleEntity;
import com.csinfotechbd.collection.settings.nplAccountRule.NPLAccountRuleService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeRepository;
import com.csinfotechbd.customerloanprofile.followup.FollowUpEntity;
import com.csinfotechbd.customerloanprofile.followup.FollowUpService;
import com.csinfotechbd.customerloanprofile.followup.FollowUpSummaryModel;
import com.csinfotechbd.customerloanprofile.loanpayment.LoanPayment;
import com.csinfotechbd.retail.loan.dashboard.RetailLoanDashboardService;
import com.csinfotechbd.retail.loan.dataEntry.ptp.LoanPtp;
import com.csinfotechbd.retail.loan.dataEntry.ptp.LoanPtpService;
import com.csinfotechbd.retail.loan.dataEntry.ptp.PtpStatusSummary;
import com.csinfotechbd.retail.loan.setup.nplReleaseLoan.NplReleaseLoan;
import com.csinfotechbd.retail.loan.setup.nplReleaseLoan.NplReleaseLoanService;
import com.csinfotechbd.retail.loan.setup.parReleaseLoan.ParReleaseLoan;
import com.csinfotechbd.retail.loan.setup.parReleaseLoan.ParReleaseLoanService;
import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import com.csinfotechbd.utillity.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/*
 * Implemented By :~ Hasibul Islam
 *                   Software Engineer
 * 26-Apr-2021 04:11 PM
 * */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/collection/samd/loan/dashboard")
public class SamdLoanDashboardController {

    private final DateUtils dateUtils;
    private final SamLoanAccountDistributionService samLoanAccountDistributionService;
    private final DashboardService dashboardService;

    private final ProductTypeRepository productTypeRepository;

    private final DpdBucketRepository dpdBucketRepository;
    private final DPDBucketService dpdBucketService;

    private final ParReleaseLoanService paRreleaseAmountLoanService;
    private final PARaccountRuleLoanService paRaccountRuleLoanService;
    private final PARqueueAccRuleLoanService paRqueueAccRuleLoanService;

    private final NplReleaseLoanService npLreleaseAmountLoanService;
    private final NPLAccountRuleService nplAccountRuleService;
    private final NPLQueueAccService nplQueueAccService;

    private final LoanKPITargetWeightByAmountService loanKPITargetWeightByAmountService;
    private final LoanKPITargetByAmountService loanKPITargetByAmountService;
    private final LoanKPITargetByAccountService loanKPITargetByAccountService;
    private final LoanKPITargetWeightByAccountService loanKPITargetWeightByAccountService;
    private final ThreadPerformance threadPerformance;

    private final FollowUpService followUpService;
    private final LoanPtpService loanPtpService;
    private final RetailLoanDashboardService retailLoanDashboardService;
    private final SamdDashboardShortFallDetailsRepository samdDashboardShortFallDetailsRepository;
    private final SamdDashboardPerformanceRepository samdDashboardPerformanceRepository;

    @ResponseBody
    @GetMapping(value = "/account-details")
    public List<SamLoanAccountDistributionSummary> getAccountDetails(@RequestParam("dealerPin") String dealerPin) {
        List<SamLoanAccountDistributionSummary> loanDistSummary = samLoanAccountDistributionService.getLoanAccountDistributionSummary(dealerPin);
        return loanDistSummary;
    }

    @GetMapping(value = "/distribution/amountWise")
    @ResponseBody
    public List<AmountWiseSummaryModel> distributionAmountWise(@RequestParam(value = "userPin") String userPin,
                                                               @RequestParam(value = "unit") String unit,
                                                               @RequestParam(value = "designation") String designation,
                                                               @RequestParam(value = "userId") String userId,
                                                               HttpSession session) {

        List<SamLoanAccountDistribution> distributionInfos = (List<SamLoanAccountDistribution>) session.getAttribute("loanDistributionList");
        // System.err.println("BEFRORE : "+distributionInfos.size());
        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);

        List<DPDBucketEntity> bucketEntities = dpdBucketService.getActiveList();
        // bucketEntities.sort();
        List<AmountWiseSummaryModel> summaryModelList = new ArrayList<>();

        if (distributionInfos.isEmpty())
            return summaryModelList;

        List<ProductTypeEntity> ptList = productTypeRepository.findByProductGroupEntityCardAccount(unit);
        ParReleaseLoan parRelAmountLoan = paRreleaseAmountLoanService.getPAR();
        NplReleaseLoan npLreleaseAmountLoan = npLreleaseAmountLoanService.getNPL();

        double amountPerPG = 0;
        double amountOfEachDpd = 0;
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
        try {

            for (ProductTypeEntity typeEntity : ptList) {

                for (DPDBucketEntity dpd : bucketEntities) {
                    if (!designation.toUpperCase().equals("DEALER")) {
                        allocationList = dashboardService.getAllDealerList(userId, designation, unit);
                        for (PeopleAllocationLogicInfo logic : allocationList) {
                            amountPerPG = amountPerPG + dashboardService.getAmountPerPt(logic.getDealer().getPin(), typeEntity.getCode());
                        }
                    } else {
                        amountPerPG = dashboardService.getAmountPerPt(userPin, typeEntity.getCode());
                    }

                    //totalDistAmount=amountPerPG+totalDistAmount;
//                List<LoanAccountDistributionInfo> checkedItem=new ArrayList<>();

                    for (SamLoanAccountDistribution dist : distributionInfos) {
                        String dpdBucket = StringUtils.hasText(dist.getDpdBucket()) ? dist.getDpdBucket().toUpperCase() : "";
                        String dpdBucketName = StringUtils.hasText(dpd.getBucketName()) ? dpd.getBucketName().toUpperCase() : "";
                        String schemeCode = StringUtils.hasText(dist.getSchemeCode()) ? dist.getSchemeCode().toUpperCase() : "";
                        String productTypeCode = StringUtils.hasText(typeEntity.getCode()) ? typeEntity.getCode().toUpperCase() : "";

                        if (dpdBucket.equals(dpdBucketName) && schemeCode.equals(productTypeCode)) {
//                        checkedItem.add(dist);
                            amountOfEachDpd = amountOfEachDpd + Double.parseDouble(dist.getOutStanding());
                            //totalDistAmount=totalDistAmount+amountOfEachDpd;
                            try {
                                double dpdAmnt = Double.parseDouble(dist.getDpdBucket());

                                for (PARaccountRuleLoanEntity entity : paRaccountRuleLoanList) {
                                    if (dpdAmnt >= entity.getMinDpd() && dpdAmnt <= entity.getMaxDpd()) {
                                        amountOfTotalPar = amountOfTotalPar + Double.parseDouble(dist.getOutStanding());
                                    }
                                }

                                if (parRelAmountLoan.getDpdBucketNames().contains(dist.getDpdBucket()))
                                    amountOfParRel = amountOfParRel + Double.parseDouble(dist.getOutStanding());

                                for (PARqueueAccRuleLoanEntity parQ : paRqueueAccRuleList) {
                                    if (parQ.getMinDpd() <= dpdAmnt && parQ.getProductTypeList().contains(typeEntity)) {
                                        amountOfParQ = amountOfParQ + Double.parseDouble(dist.getOutStanding());
                                    }
                                }

                                if (npLreleaseAmountLoan.getDpdBucketNames().contains(dist.getDpdBucket()))
                                    amountOfNplRel = amountOfNplRel + Double.parseDouble(dist.getOutStanding());

                            } catch (Exception e) {
                                //System.out.println(e.getMessage());
                            }

                            //NPL
                            for (NPLAccountRuleEntity nplAccountRuleEntity : nplAccountRuleList) {
                                if (nplAccountRuleEntity.getDpdBucketList().contains(dpd) && nplAccountRuleEntity.getProductTypeList().contains(typeEntity))
                                    amountOfTotalNPL = amountOfTotalNPL + Double.parseDouble(dist.getOutStanding());
                            }


                            for (NPLQueueAccRuleEntity nplQueue : nplQueueAccRuleList) {
                                if (nplQueue.getDpdBucketList().contains(dpd) && nplQueue.getProductTypeList().contains(typeEntity))
                                    amountOfNplQ = amountOfNplQ + Double.parseDouble(dist.getOutStanding());
                            }

                            int count = 0;
                            try {
                                List<LoanPtp> loanPtps = dashboardService.getLoanPtpByCustomer(dist.getLoanAccountBasicInfo().getCustomer().getId());
                                if (!loanPtps.isEmpty()) {
                                    count++;
                                    for (LoanPtp ptp : loanPtps) {
                                        if (ptp.getLoan_ptp_status().equals("broken"))
                                            amountOfBrokenPtp = amountOfBrokenPtp + Double.parseDouble(dist.getOutStanding());
                                    }
                                }

                            } catch (Exception e) {
                                //System.out.println(e.getMessage());
                            }
                            try {
                                if (dashboardService.getTotalDailyNoteNumber(dist.getLoanAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
                                    count++;
                            } catch (Exception ignored) {

                            }

                            try {
                                if (dashboardService.getTotalFollowUpNumber(dist.getLoanAccountBasicInfo().getCustomer().getId()) > 0)
                                    count++;
                            } catch (Exception e) {

                            }

                            try {
                                if (dashboardService.getTotalDiaryNoteNumber(dist.getLoanAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
                                    count++;
                            } catch (Exception e) {

                            }
                            try {
                                if (dashboardService.getTotalFollowUpNumber(dist.getLoanAccountBasicInfo().getCustomer().getId()) > 0)
                                    count++;
                            } catch (Exception e) {

                            }
                            try {
                                if (dashboardService.getVisitLedgerByAcc(dist.getLoanAccountBasicInfo().getAccountNo(), unit, userId) > 0)
                                    count++;
                            } catch (Exception e) {

                            }

                            if (count > 0) {
                                amountOftouchedAcc = amountOftouchedAcc + Double.parseDouble(dist.getOutStanding());
                            }
                        }
                    }
//                distributionInfos.removeAll(checkedItem);

                    if (amountOfEachDpd > 0) {
                        amountOfUnTouched = amountOfEachDpd - amountOftouchedAcc;
                        amountOfPending = amountOfUnTouched + amountOfBrokenPtp;

                        if (amountOfPending > amountOfEachDpd)
                            amountOfPending = amountOfEachDpd;

                        AmountWiseSummaryModel summaryModel = new AmountWiseSummaryModel();

                        summaryModel.setPG(typeEntity.getName() + "-" + typeEntity.getCode());
                        summaryModel.setTotalNumber(amountPerPG);
                        //summaryModel.setPerProductPerc((amountPerPG/totalDistAmount)*100);
                        summaryModel.setProductAndDpd(dpd.getBucketName());
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

                        summaryModelList.add(summaryModel);


                    }
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
                //System.err.println("Dist :"+summaryModelList.size());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.err.println("accountWise "+distributionInfos.size());
        return summaryModelList;
    }

    @GetMapping(value = "/distribution/accountWise")
    @ResponseBody
    public List<AccountWiseSummaryModel> distributionAccountWise(@RequestParam(value = "userPin") String userPin,
                                                                 @RequestParam(value = "unit") String unit,
                                                                 @RequestParam(value = "designation") String designation,
                                                                 @RequestParam(value = "userId") String userId,
                                                                 HttpSession session) {
        List<SamLoanAccountDistribution> distributionInfos = (List<SamLoanAccountDistribution>) session.getAttribute("loanDistributionList");
        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);

        List<DPDBucketEntity> bucketEntities = dpdBucketService.getActiveList();
        // bucketEntities.sort();
        List<AccountWiseSummaryModel> summaryModelList = new ArrayList<>();

        if (distributionInfos.isEmpty())
            return summaryModelList;

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
        //double numOfTotalDistAcc=Double.parseDouble(Integer.toString(distributionInfos.size()));
        try {

            for (ProductTypeEntity typeEntity : ptList) {

                for (DPDBucketEntity dpd : bucketEntities) {
                    //numberPerPG=dashboardService.getNumberOfAcPerPt(userPin,typeEntity.getCode());
                    //numOfTotalDistAcc=numOfTotalDistAcc+numberPerPG;

                    if (!designation.toUpperCase().equals("DEALER")) {
                        allocationList = dashboardService.getAllDealerList(userId, designation, unit);
                        for (PeopleAllocationLogicInfo logic : allocationList) {
                            numberPerPG = numberPerPG + dashboardService.getNumberOfAcPerPt(logic.getDealer().getPin(), typeEntity.getCode());
                        }
                    } else {
                        numberPerPG = dashboardService.getNumberOfAcPerPt(userPin, typeEntity.getCode());
                    }

                    //List<LoanAccountDistributionInfo> checkedItem=new ArrayList<>();
                    for (SamLoanAccountDistribution dist : distributionInfos) {
                        String dpdBucket = StringUtils.hasText(dist.getDpdBucket()) ? dist.getDpdBucket().toUpperCase() : "";
                        String dpdBucketName = StringUtils.hasText(dpd.getBucketName()) ? dpd.getBucketName().toUpperCase() : "";
                        String schemeCode = StringUtils.hasText(dist.getSchemeCode()) ? dist.getSchemeCode().toUpperCase() : "";
                        String productTypeCode = StringUtils.hasText(typeEntity.getCode()) ? typeEntity.getCode().toUpperCase() : "";


                        if (dpdBucket.equals(dpdBucketName) && schemeCode.equals(productTypeCode)) {
                            // checkedItem.add(dist);
                            numberOfEachDpd++;

                            try {
                                double dpdAmnt = Double.parseDouble(dist.getDpdBucket());

                                for (PARaccountRuleLoanEntity entity : paRaccountRuleLoanList) {
                                    if (dpdAmnt >= entity.getMinDpd() && dpdAmnt <= entity.getMaxDpd()) {
                                        // numOfPerRel++;
                                        numOfTotalPar++;
                                    }
                                }

                                if (parRelAmountLoan.getDpdBucketNames().contains(dist.getDpdBucket()))
                                    numOfParRel++;

                                if (npLreleaseAmountLoan.getDpdBucketNames().contains(dist.getDpdBucket()))
                                    numOfNplRel++;

                                for (PARqueueAccRuleLoanEntity parQ : paRqueueAccRuleList) {
                                    if (parQ.getMinDpd() <= dpdAmnt && parQ.getProductTypeList().contains(typeEntity)) {
                                        numOfParQ++;
                                    }
                                }

                            } catch (Exception e) {
                                //System.out.println(e.getMessage());
                            }

                            //NPL
                            for (NPLAccountRuleEntity nplAccountRuleEntity : nplAccountRuleList) {
                                if (nplAccountRuleEntity.getDpdBucketList().contains(dpd) && nplAccountRuleEntity.getProductTypeList().contains(typeEntity))
                                    numOfNplRel++;
                            }

                            for (NPLQueueAccRuleEntity nplQueue : nplQueueAccRuleList) {
                                if (nplQueue.getDpdBucketList().contains(dpd) && nplQueue.getProductTypeList().contains(typeEntity))
                                    numOfNplQ++;
                            }

                            int count = 0;
                            try {
                                List<LoanPtp> loanPtps = dashboardService.getLoanPtpByCustomer(dist.getLoanAccountBasicInfo().getCustomer().getId());
                                if (!loanPtps.isEmpty()) {
                                    count++;
                                    for (LoanPtp ptp : loanPtps) {
                                        if (ptp.getLoan_ptp_status().equals("broken"))
                                            numOfBrokenPtp++;
                                    }
                                }
                            } catch (Exception e) {
                                //System.out.println(e.getMessage());
                            }
                            try {
                                if (dashboardService.getTotalDailyNoteNumber(dist.getLoanAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
                                    count++;
                            } catch (Exception e) {

                            }

                            try {
                                if (dashboardService.getTotalFollowUpNumber(dist.getLoanAccountBasicInfo().getCustomer().getId()) > 0)
                                    count++;
                            } catch (Exception e) {

                            }

                            try {
                                if (dashboardService.getTotalDiaryNoteNumber(dist.getLoanAccountBasicInfo().getCustomer().getId(), userId, unit) > 0)
                                    count++;
                            } catch (Exception e) {

                            }
                            try {
                                if (dashboardService.getTotalFollowUpNumber(dist.getLoanAccountBasicInfo().getCustomer().getId()) > 0)
                                    count++;
                            } catch (Exception e) {

                            }

                            if (count > 0) {
                                numberOftouchedAcc++;
                            }
                        }
                    }

                    //distributionInfos.removeAll(checkedItem);

                    if (numberOfEachDpd > 0l) {
                        numberOfUnTouched = numberOfEachDpd - numberOftouchedAcc;
                        numberOfPending = numberOfUnTouched + numOfBrokenPtp;

                        if (numberOfPending > numberOfEachDpd)
                            numberOfPending = numberOfEachDpd;

                        AccountWiseSummaryModel summaryModel = new AccountWiseSummaryModel();

                        summaryModel.setPG(typeEntity.getName() + "-" + typeEntity.getCode());
                        summaryModel.setTotalNumber(numberPerPG);
//                    summaryModel.setPerProductPerc(((double) numberPerPG/ numOfTotalDistAcc)*100);
                        summaryModel.setProductAndDpd(dpd.getBucketName());
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

                        summaryModelList.add(summaryModel);


                    }
                    numberOfEachDpd = 0l;
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
                //numOfTotalDistAcc=0;
                //System.err.println("Dist :"+summaryModelList.size());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.err.println("accountWise "+distributionInfos.size());
        return summaryModelList;
    }

    //KPI target vs achievement LOAN
    //amount wise
    @GetMapping(value = "/kpi-vs-achievement/amount-wise")
    @ResponseBody
    public List<ProductWiseSummary> getKpiVsAchievementAmountWise(@RequestParam(value = "userPin") String userPin,
                                                                  @RequestParam(value = "unit") String unit,
                                                                  @RequestParam(value = "designation") String designation,
                                                                  @RequestParam(value = "userId") String userId,
                                                                  HttpSession session) {

        String dealerName = "";
        List<ProductWiseSummary> productWiseSummaryList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);

        List<SamLoanAccountDistribution> accountDistributionList = (List<SamLoanAccountDistribution>) session.getAttribute("loanDistributionList");

        if (accountDistributionList.isEmpty())
            return productWiseSummaryList;

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
                        for (SamLoanAccountDistribution distributionInfo : accountDistributionList) {
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
                            if (distributionInfo.getDpdBucket() != null && distributionInfo.getDpdBucket().toUpperCase().equals(dpdBucket.getBucketName().toUpperCase()) && distributionInfo.getSchemeCode().toUpperCase().equals(productType.getCode().toUpperCase())
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
                                    //System.out.println("DPD BUCKET NAME: " + dpdBucket.getBucketName());
                                    //System.out.println("PRODUCT SCHEME CODE: " + distributionInfo.getSchemeCode());
                                    /*System.out.println("Regular Account NO: " + distributionInfo.getLoanAccountBasicInfo().getAccountNo() + "  "
                                            + distributionInfo.getOutStanding()
                                            + " present overdue " + distributionInfo.getPresentOverDue()
                                            + " Live dpd " + distributionInfo.getLiveDpd()

                                    );*/
                                    regularAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());

                                    backAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                }


                                if (currentDpdBucket != null) {
                                    if (!(currentDpdBucket.getBucketName().toUpperCase().equals("INTERIM")
                                            || currentDpdBucket.getBucketName().toUpperCase().equals("X"))
                                            && !(distributionInfo.getDpdBucket().toUpperCase().equals("INTERIM")
                                            || distributionInfo.getDpdBucket().toUpperCase().equals("X"))) {
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
                                            /*System.out.println("DPD BUCKET NAME: " + dpdBucket.getBucketName());
                                            System.out.println("PRODUCT SCHEME CODE: " + distributionInfo.getSchemeCode());
                                            System.out.println("Back Account NO: " + distributionInfo.getLoanAccountBasicInfo().getAccountNo() + "  "
                                                    + distributionInfo.getOutStanding()
                                                    + "  " + currentDpdBucket.getBucketName()
                                                    + " Live dpd " + distributionInfo.getLiveDpd()
                                            );*/
                                            backAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        }


                                    }
                                    String monthOpeningDpdBucket = distributionInfo.getDpdBucket();
                                    /*if(currentDpdBucket.getBucketName().toUpperCase().equals("X") || currentDpdBucket.getBucketName().toUpperCase().equals("INTERIM")){

                                        if(!monthOpeningDpdBucket.toUpperCase().equals("X") || !monthOpeningDpdBucket.toUpperCase().equals("INTERIM") ){
                                            if(Double.parseDouble(monthOpeningDpdBucket) >= 30){
                                                backAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                            }
                                        }
                                    }

                                    else if(Double.parseDouble(currentDpdBucket.getBucketName()) >= 30){

                                        if(monthOpeningDpdBucket.equals("X")){
                                            flowAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        }
                                    }*/

                                    //for x bucket calculation
                                    if (distributionInfo.getDpdBucket().toUpperCase().equals("X") || distributionInfo.getDpdBucket().toUpperCase().equals("INTERIM")) {
                                        if (Double.parseDouble(currentDpdBucket.getBucketName()) >= 30) {
                                            flowAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        } else if (currentDpdBucket.getBucketName().toUpperCase().equals("X")) {
                                            saveAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        } else if (distributionInfo.getPresentOverDue() > 0) {
                                            /*System.out.println("DPD BUCKET NAME: " + dpdBucket.getBucketName());
                                            System.out.println("PRODUCT SCHEME CODE: " + distributionInfo.getSchemeCode());
                                            System.out.println("Back Account NO: " + distributionInfo.getLoanAccountBasicInfo().getAccountNo() + "  "
                                                    + distributionInfo.getOutStanding()
                                                    + "  " + currentDpdBucket.getBucketName()
                                                    + " Live dpd " + distributionInfo.getLiveDpd()
                                            );*/
                                            backAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        } else if (distributionInfo.getPresentOverDue() <= 0) {
                                            backAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                            regularAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        }
                                    }

                                    if (currentDpdBucket.getBucketName().toUpperCase().equals("X") || currentDpdBucket.getBucketName().toUpperCase().equals("INTERIM")) {
                                        if (Double.parseDouble(distributionInfo.getDpdBucket()) >= 30) {
                                            /*System.out.println("DPD BUCKET NAME: " + dpdBucket.getBucketName());
                                            System.out.println("PRODUCT SCHEME CODE: " + distributionInfo.getSchemeCode());
                                            System.out.println("Back Account NO for 2: " + distributionInfo.getLoanAccountBasicInfo().getAccountNo() + "  "
                                                    + distributionInfo.getOutStanding()
                                                    + "  " + currentDpdBucket.getBucketName()
                                                    + " Live dpd " + distributionInfo.getLiveDpd()
                                            );*/
                                            backAchieventAmount += Double.parseDouble(distributionInfo.getOutStanding());
                                        }
                                    }
                                }

                                Date monthEndDate = dateUtils.getMonthEndDate();
                                Date monthOpenDate = distributionInfo.getCreatedDate();

                                LocalDate localDate1 = monthOpenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                LocalDate localDate2 = monthEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                                day = Duration.between(localDate1.atStartOfDay(), localDate2.atStartOfDay());

                                dayDiff = (double) day.toDays();
                                MO_DPD = distributionInfo.getDpd();

                                List<LoanPayment> loanPayments = dashboardService.getLoanPaymentByAcc(distributionInfo.getLoanAccountBasicInfo().getAccountNo(), distributionInfo.getStatusDate());
                                if (loanPayments != null) {

                                    for (LoanPayment paymentDetails : loanPayments) {
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
                        //System.out.println("Flow Achievement Amount: " + BigDecimal.valueOf(flowAchieventAmount));
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
        productWiseSummaryList = productWiseSummaryListFinal;
        threadPerformance.loanPerformanceCal(productWiseSummaryList, userPin, dealerName);
        return productWiseSummaryList;

    }

    //KPI target vs achievement LOAN
    @GetMapping(value = "/kpi-vs-achievement/account-wise")
    @ResponseBody
    public List<ProductWiseSummary> getKpiVsAchievementAccountWise(@RequestParam(value = "userPin") String userPin,
                                                                   @RequestParam(value = "unit") String unit,
                                                                   @RequestParam(value = "designation") String designation,
                                                                   @RequestParam(value = "userId") String userId,
                                                                   HttpSession session) {
        List<ProductWiseSummary> productWiseSummaryList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);

        List<SamLoanAccountDistribution> accountDistributionList = (List<SamLoanAccountDistribution>) session.getAttribute("loanDistributionList");

        if (accountDistributionList.isEmpty())
            return productWiseSummaryList;

        //List<LoanKPITargetByAccountEntity> kpiTargetList = loanKPITargetByAccountService.getAllData();
        List<LoanKPITargetByAccountEntity> kpiTargetList = new ArrayList<>();

        try {
            kpiTargetList = loanKPITargetByAccountService.getAllDataForDist(accountDistributionList.get(0).getCreatedDate());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        List<LoanKPITargetByAccountEntity> temp = new ArrayList<>();

        for (PeopleAllocationLogicInfo logicInfo : allocationList) {
            for (ProductTypeEntity productTypeEntity : logicInfo.getProductTypeEntityLoan()) {
                for (DPDBucketEntity bucket : logicInfo.getDpdBucketEntity()) {
                    for (LoanKPITargetByAccountEntity target : kpiTargetList) {
                        if (target.getDpdBucket().contains(bucket) && target.getProductType().contains(productTypeEntity)) {
                            if (!temp.contains(target))
                                temp.add(target);
                        }
                    }
                }
            }
        }

        kpiTargetList = temp;

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

        List<LoanKPITargetWeightByAccountEntity> weightByAccountList = loanKPITargetWeightByAccountService.getAllDataDist(accountDistributionList.get(0).getCreatedDate());

        for (PeopleAllocationLogicInfo logicInfo : allocationList) {
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

                        //List<LoanAccountDistributionInfo> checkedItem=new ArrayList<>();
                        //System.err.println("DIST LIST "+accountDistributionList.size());
                        for (SamLoanAccountDistribution distributionInfo : accountDistributionList) {
                            for (LoanKPITargetWeightByAccountEntity weight : weightByAccountList) {
                                if (weight.getProductType() != null) {
                                    if (weight.getProductType().contains(productType) && weight.getDpdBucket().contains(dpdBucket)) {
                                        weightBackPerc = weight.getBackWeight();
                                        weightSavePerc = weight.getSaveWeight();
                                        weightFlowPerc = weight.getSaveWeight();
                                        weightregularPerc = weight.getRegularWeight();
                                        weightOverDuePerc = weight.getOverDueWeight();
                                    }
                                }

                            }

                            if (distributionInfo.getDpdBucket() != null && distributionInfo.getDpdBucket().toUpperCase().equals(dpdBucket.getBucketName().toUpperCase()) && distributionInfo.getSchemeCode().toUpperCase().equals(productType.getCode().toUpperCase())
                                    && logicInfo.getDpdBucketEntity().contains(dpdBucket) && logicInfo.getProductTypeEntityLoan().contains(productType)) {

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

                                List<LoanPayment> loanPayments = dashboardService.getLoanPaymentByAcc(distributionInfo.getLoanAccountBasicInfo().getAccountNo(), distributionInfo.getCreatedDate());
                                double totalPaidAmnt = 0;
                                if (loanPayments != null) {

                                    for (LoanPayment paymentDetails : loanPayments) {
                                        totalPaidAmnt = totalPaidAmnt + paymentDetails.getPayment();
                                    }
                                }

                                if (distributionInfo.getDpdBucket().toUpperCase().equals("X")) {
//                                if(accountDpd.getCurrentDpd()>=30)
//                                {
//
//                                }

                                    saveAmount = ((MO_DPD + (dayDiff + 1 - 29.99)) * distributionInfo.getEmiAmount()) / 30;
                                    distributionInfo.setBackAmount(0);
//                                    if (saveAmount > 0) {
//                                        noOfSaveAc++;
//                                    }
                                    if (saveAmount <= totalPaidAmnt)
                                        noOfSaveAc++;
                                } else {
                                    double highestDpdRange = Double.parseDouble(distributionInfo.getDpdBucket()) + 29.99;
                                    saveAmount = ((MO_DPD + (dayDiff + 1 - highestDpdRange)) * distributionInfo.getEmiAmount()) / 30;

                                    if (saveAmount <= totalPaidAmnt) {
                                        noOfSaveAc++;
                                    }
                                    double highestDpdRangeBack = Double.parseDouble(distributionInfo.getDpdBucket()) - .99;
                                    backAmount = ((MO_DPD + dayDiff + 1 - highestDpdRangeBack) * distributionInfo.getEmiAmount()) / 30;

                                    if (backAmount <= totalPaidAmnt) {
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
                            summary.setProduct(productType.getName() + "-" + productType.getCode());
                            summary.setNoOfAcEachDpd(noOfAcEachDpd);
                            //Target for amount

                            //back
                            summary.setAmntOfBackAc(noOfBackAc);
                            summary.setTarAmntOfBackAc((kpiTargetByAcc.getBackTarget() / 100) * noOfAcEachDpd);
                            summary.setTarAmntOfBackAcPerc(kpiTargetByAcc.getBackTarget());
                            summary.setShortFallBack(summary.getTarAmntOfBackAc() - summary.getAmntOfBackAc());
                            if (noOfBackAc > 0) {
                                if (summary.getTarAmntOfBackAc() > 0)
                                    summary.setBackPerformance((summary.getAmntOfBackAc() / summary.getTarAmntOfBackAc()) * 100);
                                summary.setNoOfBackAcPerc((summary.getAmntOfBackAc() / noOfAcEachDpd) * 100);
                            }
                            summary.setWeightBackPerc(weightBackPerc);
                            summary.setWeightBackPerformmance(weightBackPerc * summary.getBackPerformance());


                            //save
                            summary.setAmntOfSaveAc(noOfSaveAc);
                            summary.setTarAmntOfSaveAcPerc(kpiTargetByAcc.getSaveTarget());
                            summary.setTarAmntOfSaveAc((kpiTargetByAcc.getSaveTarget() / 100) * noOfAcEachDpd);
                            summary.setShortFallSave(summary.getTarAmntOfSaveAc() - summary.getAmntOfSaveAc());
                            if (noOfSaveAc > 0) {
                                if (summary.getTarAmntOfSaveAc() > 0)
                                    summary.setSavePerformance((summary.getAmntOfSaveAc() / summary.getTarAmntOfSaveAc()) * 100);
                                summary.setAmntOfSaveAcPerc((summary.getAmntOfSaveAc() / noOfAcEachDpd) * 100);
                            }
                            summary.setWeightSavePerc(weightSavePerc);
                            summary.setWeightSavePerformmance(weightSavePerc * summary.getSavePerformance());

                            //overdue
                            summary.setAmntOverDue(overDueAmnt);
                            summary.setTarAmntOverDue((kpiTargetByAcc.getOverDueTarget() / 100) * noOfAcEachDpd);
                            summary.setTarAmntOverDuePerc(kpiTargetByAcc.getOverDueTarget());
                            summary.setShortFallOverDue(summary.getTarAmntOverDue() - overDueAmnt);
                            if (overDueAmnt > 0) {
                                if (summary.getTarAmntOverDue() > 0)
                                    summary.setOverDuePerformance((summary.getAmntOverDue() / summary.getTarAmntOverDue()) * 100);
                                summary.setAmntOverDuePerc((summary.getAmntOverDue() / noOfAcEachDpd) * 100);
                            }
                            summary.setWeightOverdDuePerc(weightOverDuePerc);
                            summary.setWeightOverDuePerformmance(weightOverDuePerc * summary.getOverDuePerformance());

                            //regular
                            summary.setAmntRegular(regularAmnt);
                            summary.setTarAmntRegular((kpiTargetByAcc.getRegularTarget() / 100) * noOfAcEachDpd);
                            summary.setTarAmntRegularPerc(kpiTargetByAcc.getRegularTarget());
                            summary.setAmntRegularPerc(kpiTargetByAcc.getRegularTarget());
                            if (regularAmnt > 0) {
                                if (kpiTargetByAcc.getRegularTarget() > 0)
                                    summary.setRegularPerFormance((regularAmnt / kpiTargetByAcc.getRegularTarget()) * 100);
                                summary.setAmntRegularPerc((summary.getAmntRegular() / noOfAcEachDpd) * 100);

                            }
                            summary.setShortFallRegular(summary.getTarAmntOverDue() - regularAmnt);
                            summary.setWeightRegularPerc(weightregularPerc);
                            summary.setWeightRegularPerformmance(weightregularPerc * summary.getRegularPerFormance());

                            //flow
                            summary.setTarAmntOfFlowAc((kpiTargetByAcc.getFlowTarget() / 100) * noOfAcEachDpd);
                            summary.setTarAmntOfFlowAcPerc(kpiTargetByAcc.getFlowTarget());
                            summary.setAmntOfFlowAc(noOfAcEachDpd - summary.getAmntOfSaveAc());
                            summary.setShortFallFlow(summary.getTarAmntOfFlowAc() - summary.getAmntOfFlowAc());
                            if (summary.getAmntOfFlowAc() > 0) {
                                double flowAchPerc = 0;
                                if (summary.getTarAmntOfFlowAc() >= summary.getAmntOfFlowAc()) {
                                    flowAchPerc = ((summary.getTarAmntOfFlowAc() - summary.getAmntOfFlowAc()) / summary.getTarAmntOfFlowAc()) + 1;
                                } else {
                                    if (summary.getAmntOfFlowAc() > summary.getTarAmntOfFlowAc() && summary.getAmntOfFlowAc() < (noOfAcEachDpd - summary.getTarAmntOfFlowAc())) {
                                        flowAchPerc = (1 - (1 - (summary.getTarAmntOfFlowAc() / summary.getAmntOfFlowAc())));
                                    } else {
                                        flowAchPerc = (noOfAcEachDpd / summary.getAmntOfFlowAc()) - 1;
                                    }
                                }
                                summary.setFlowPerformance(flowAchPerc);
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

        }
        List<ProductWiseSummary> productWiseSummaryListFinal = new ArrayList<>();
        for (LoanKPITargetByAccountEntity target : kpiTargetList) {
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
        productWiseSummaryList = productWiseSummaryListFinal;

        return productWiseSummaryList;

    }

    @GetMapping(value = "/follow-up")
    @ResponseBody
    public List<FollowUpSummaryModel> getMonthlyFollowUpSummary(String designation, String userPin) {
        return followUpService.getSamdCurrentMonthFollowUpSummmary(userPin, designation);
    }

    @GetMapping(value = "date-wise-follow-up")
    public Map getDateWiseFollowUpSummary(String userId, HttpSession session) {
        Map map = new HashMap();
        List<FollowUpEntity> followUpList = new ArrayList<>();
        List<SamLoanAccountDistribution> distributionInfos =
                (List<SamLoanAccountDistribution>) session.getAttribute("loanDistributionList");

        if (distributionInfos == null) {
            map.put("loanFollowup", followUpList);
            return map;
        }

        for (SamLoanAccountDistribution distributionInfo : distributionInfos) {
            Long customerId = distributionInfo.getLoanAccountBasicInfo().getCustomer().getId();
            List<FollowUpEntity> followups = retailLoanDashboardService.getLoanFollowUpByCusBasicInfo(customerId, userId);
            for (FollowUpEntity follow : followups) {
                follow.setOutstanding(distributionInfo.getOutStanding());
                follow.setAccNo(distributionInfo.getLoanAccountBasicInfo().getAccountNo());
            }
            followUpList.addAll(followups);
        }
        map.put("loanFollowup", followUpList);
        return map;
    }

    @RequestMapping(value = "/payment-status")
    @ResponseBody
    public List<PaymentStatusModel> getPaymentDetails(@RequestParam(value = "userPin") String userPin,
                                                      @RequestParam(value = "unit") String unit,
                                                      @RequestParam(value = "designation") String designation,
                                                      @RequestParam(value = "userId") String userId,
                                                      HttpSession session) {

        List<SamLoanAccountDistribution> distributionInfos =
                (List<SamLoanAccountDistribution>) session.getAttribute("loanDistributionList");
        return getSamdPaymentSummaryForDistributedAccounts(distributionInfos);
    }

    public List<PaymentStatusModel> getSamdPaymentSummaryForDistributedAccounts(List<SamLoanAccountDistribution> distributionInfos) {

        Date today = dateUtils.getStartingPointOfDay(new Date());

        List<LoanPayment> totalPaidDetails = new ArrayList<>();
        List<LoanPayment> savePaidDetails = new ArrayList<>();
        List<LoanPayment> notSavePaidDetails = new ArrayList<>();
        List<LoanPayment> oneEmiPaidDetails = new ArrayList<>();
        List<LoanPayment> unpaidDetails = new ArrayList<>();

        List<PaymentStatusModel> statusModels = new ArrayList<>();

        if (distributionInfos.isEmpty())
            return statusModels;

        PaymentStatusModel totalPaid = new PaymentStatusModel();
        totalPaid.setNarration("Total Paid");

        PaymentStatusModel save = new PaymentStatusModel();
        save.setNarration("SAVE");

        PaymentStatusModel paidOneEmi = new PaymentStatusModel();
        paidOneEmi.setNarration("Paid >= One EMI");

        PaymentStatusModel paidNotSave = new PaymentStatusModel();
        paidNotSave.setNarration("Paid But Not SAVE");

        PaymentStatusModel unpaid = new PaymentStatusModel();
        unpaid.setNarration("UNPAID");

        double totalPaidAmnt = 0;

        for (SamLoanAccountDistribution dist : distributionInfos) {
            boolean isEarlierThanYesterday = false;
            List<LoanPayment> loanPayments = dashboardService.getLoanPaymentByAcc(dist.getLoanAccountBasicInfo().getAccountNo(), dateUtils.getMonthStartDate());

            if (loanPayments != null && !loanPayments.isEmpty()) {

                totalPaidAmnt = loanPayments.stream().mapToDouble(LoanPayment::getPayment).sum();

                LoanPayment lastPayment = loanPayments.get(loanPayments.size() - 1);

                for (LoanPayment paymentDetails : loanPayments) {

                    // Total payment summary
                    totalPaid.setPaidAmnt(totalPaid.getPaidAmnt() + paymentDetails.getPayment());
                    totalPaid.setTotalAc(totalPaid.getTotalAc() + 1);
                    totalPaidDetails.add(
                            new LoanPayment(
                                    paymentDetails.getPaymentDate(),
                                    paymentDetails.getAccountNo(),
                                    paymentDetails.getPayment(),
                                    lastPayment.getPayment(),
                                    lastPayment.getLastPaymentDate(),
                                    dist.getDealerName(),
                                    dist.getOutStanding(),
                                    dist.getLoanAccountBasicInfo().getAccountName(),
                                    dist.getOpeningOverDue()
                            ));


                    //save acc summary
                    if (totalPaidAmnt >= dist.getSaveAmount()) {
                        save.setTotalAc(save.getTotalAc() + 1);
                        save.setPaidAmnt(save.getPaidAmnt() + paymentDetails.getPayment());
                        savePaidDetails.add(
                                new LoanPayment(
                                        paymentDetails.getPaymentDate(),
                                        paymentDetails.getAccountNo(),
                                        paymentDetails.getPayment(),
                                        lastPayment.getPayment(),
                                        lastPayment.getLastPaymentDate(),
                                        dist.getDealerName(),
                                        dist.getOutStanding(),
                                        dist.getLoanAccountBasicInfo().getAccountName(),
                                        dist.getOpeningOverDue()
                                ));
                    } else {
                        paidNotSave.setTotalAc(paidNotSave.getTotalAc() + 1);
                        paidNotSave.setPaidAmnt(paidNotSave.getPaidAmnt() + paymentDetails.getPayment());
                        notSavePaidDetails.add(
                                new LoanPayment(
                                        paymentDetails.getPaymentDate(),
                                        paymentDetails.getAccountNo(),
                                        paymentDetails.getPayment(),
                                        lastPayment.getPayment(),
                                        lastPayment.getLastPaymentDate(),
                                        dist.getDealerName(),
                                        dist.getOutStanding(),
                                        dist.getLoanAccountBasicInfo().getAccountName(),
                                        dist.getOpeningOverDue()
                                ));
                    }
                    //emi >=
                    if (totalPaidAmnt >= dist.getEmiAmount()) {
                        paidOneEmi.setTotalAc(paidOneEmi.getTotalAc() + 1);
                        paidOneEmi.setPaidAmnt(paidOneEmi.getPaidAmnt() + paymentDetails.getPayment());
                        oneEmiPaidDetails.add(
                                new LoanPayment(
                                        paymentDetails.getPaymentDate(),
                                        paymentDetails.getAccountNo(),
                                        paymentDetails.getPayment(),
                                        lastPayment.getPayment(),
                                        lastPayment.getLastPaymentDate(),
                                        dist.getDealerName(),
                                        dist.getOutStanding(),
                                        dist.getLoanAccountBasicInfo().getAccountName(),
                                        dist.getOpeningOverDue()
                                ));
                    }


                    if (isEarlierThanYesterday) continue;

                    int compare = paymentDetails.getPaymentDate().compareTo(today);
                    if (compare >= 0) {
                        double paymentAmount = paymentDetails.getPayment();
                        updatePaymentStatusModel(totalPaid, paymentAmount);
                        //save acc
                        if (totalPaidAmnt >= dist.getSaveAmount()) {
                            updatePaymentStatusModel(save, paymentAmount);
                            savePaidDetails.add(new LoanPayment(
                                    paymentDetails.getPaymentDate(),
                                    paymentDetails.getAccountNo(),
                                    paymentDetails.getPayment(),
                                    lastPayment.getPayment(),
                                    lastPayment.getLastPaymentDate(),
                                    dist.getDealerName(),
                                    dist.getOutStanding(),
                                    dist.getLoanAccountBasicInfo().getAccountName(),
                                    dist.getOpeningOverDue()
                            ));
                        } else {
                            updatePaymentStatusModel(paidNotSave, paymentAmount);
                            notSavePaidDetails.add(new LoanPayment(
                                    paymentDetails.getPaymentDate(),
                                    paymentDetails.getAccountNo(),
                                    paymentDetails.getPayment(),
                                    lastPayment.getPayment(),
                                    lastPayment.getLastPaymentDate(),
                                    dist.getDealerName(),
                                    dist.getOutStanding(),
                                    dist.getLoanAccountBasicInfo().getAccountName(),
                                    dist.getOpeningOverDue()
                            ));
                        }
                        //one emi
                        if (totalPaidAmnt >= dist.getEmiAmount()) {
                            updatePaymentStatusModel(paidOneEmi, paymentAmount);
                            oneEmiPaidDetails.add(new LoanPayment(paymentDetails.getPaymentDate(),
                                    paymentDetails.getAccountNo(),
                                    paymentDetails.getPayment(),
                                    lastPayment.getPayment(),
                                    lastPayment.getLastPaymentDate(),
                                    dist.getDealerName(),
                                    dist.getOutStanding(),
                                    dist.getLoanAccountBasicInfo().getAccountName(),
                                    dist.getOpeningOverDue()
                            ));
                        }
                        isEarlierThanYesterday = true;
                    }
                }

            } else {
                unpaid.setTotalAc(unpaid.getTotalAc() + 1);

                String outstanding = dist.getOutStanding() == null ? "0" : dist.getOutStanding();
                Double openingOverdue = dist.getOpeningOverDue() == null ? 0 : dist.getOpeningOverDue();

                unpaidDetails.add(new LoanPayment(
                        null, dist.getLoanAccountBasicInfo().getAccountNo(),
                        0, 0, null, dist.getDealerName(),
                        outstanding, null, openingOverdue));
            }
        }

        //Set Payment details lists
        totalPaid.setLoanAccountPaymentDetails(totalPaidDetails);
        save.setLoanAccountPaymentDetails(savePaidDetails);
        paidOneEmi.setLoanAccountPaymentDetails(oneEmiPaidDetails);
        paidNotSave.setLoanAccountPaymentDetails(notSavePaidDetails);
        unpaid.setLoanAccountPaymentDetails(unpaidDetails);

        // Wrap the Status models in a list
        statusModels.add(totalPaid);
        statusModels.add(save);
        statusModels.add(paidOneEmi);
        statusModels.add(paidNotSave);
        statusModels.add(unpaid);

        return statusModels;
    }

    public PaymentStatusModel updatePaymentStatusModel(PaymentStatusModel model, double amount) {
        model.setTotalAcToday(model.getTotalAcToday() + 1);
        model.setPaidAmntToday(model.getPaidAmntToday() + amount);
        return model;
    }

    @GetMapping(value = "ptp-status-summary")
    public List<PtpStatusSummary> getPtpStatusWiseSummary(String designation, String userPin) {
        return loanPtpService.getSamdCurrentMonthFollowUpSummmary(designation, userPin);
    }

    @GetMapping(value = "shortfall-details")
    public SamdDashboardShortFallDetails getShortfallDetails(String userPin) {
        Tuple data = samdDashboardShortFallDetailsRepository.getShortFallDetails(userPin);
        return new SamdDashboardShortFallDetails(data);
    }

    @GetMapping(value = "performance")
    public SamdDashboardPerformance getPerformanceData(String userPin) {

        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();

        Tuple data = samdDashboardPerformanceRepository.getPerformanceData(userPin, startDate, endDate);
        return new SamdDashboardPerformance(data);
    }
}
