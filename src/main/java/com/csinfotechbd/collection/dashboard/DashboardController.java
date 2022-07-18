package com.csinfotechbd.collection.dashboard;


import com.csinfotechbd.collection.KPI.Card.TargetByAccount.CardKPITargetByAccountEntity;
import com.csinfotechbd.collection.KPI.Card.TargetByAccount.CardKPITargetByAccountService;
import com.csinfotechbd.collection.KPI.Card.TargetByAmount.CardKPITargetByAmountEntity;
import com.csinfotechbd.collection.KPI.Card.TargetByManager.DealerTargetCardManager;
import com.csinfotechbd.collection.KPI.Card.TargetByManager.DealerTargetCardManagerAccDao;
import com.csinfotechbd.collection.KPI.Card.TargetByManagerAmount.DealerTargetAmountCardManager;
import com.csinfotechbd.collection.KPI.Card.TargetByManagerAmount.DealerTargetCardManagerDao;
import com.csinfotechbd.collection.KPI.Card.TargetWeightByAmount.CardKPITargetWeightByAmountEntity;
import com.csinfotechbd.collection.KPI.Loan.TargetByAccount.LoanKPITargetByAccountEntity;
import com.csinfotechbd.collection.KPI.Loan.TargetByAccount.LoanKPITargetByAccountService;
import com.csinfotechbd.collection.KPI.Loan.TargetByAmount.LoanKPITargetByAmountService;
import com.csinfotechbd.collection.KPI.Loan.TargetWeightByAccount.LoanKPITargetWeightByAccountEntity;
import com.csinfotechbd.collection.KPI.Loan.TargetWeightByAccount.LoanKPITargetWeightByAccountService;
import com.csinfotechbd.collection.KPI.Loan.TargetWeightByAmount.LoanKPITargetWeightByAmountService;
import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicInfo;
import com.csinfotechbd.collection.allocationLogic.PeopleAllocationLogicRepository;
import com.csinfotechbd.customerloanprofile.loanpayment.LoanPaymentRepository;
import com.csinfotechbd.retail.card.dashboard.RetailCardDashBoardRepository;
import com.csinfotechbd.retail.card.dataEntry.cardPayment.CardPaymentSummaryModel;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionInfo;
import com.csinfotechbd.collection.distribution.loan.loanAccountDistribution.LoanAccountDistributionService;
import com.csinfotechbd.collection.settings.NPLQueueAccRule.NPLQueueAccService;
import com.csinfotechbd.retail.loan.dashboard.RetailLoanDashboardRepository;
import com.csinfotechbd.retail.loan.setup.nplReleaseLoan.NplReleaseLoanService;
import com.csinfotechbd.collection.settings.PARaccountRuleLoan.PARaccountRuleLoanService;
import com.csinfotechbd.collection.settings.PARqueueAccRuleLoan.PARqueueAccRuleLoanService;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeRepository;
import com.csinfotechbd.collection.settings.nplAccountRule.NPLAccountRuleService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeRepository;
import com.csinfotechbd.customerloanprofile.followup.FollowUpService;

import com.csinfotechbd.customerloanprofile.loanpayment.LoanPayment;
import com.csinfotechbd.retail.loan.dataEntry.ptp.PtpStatusSummary;
import com.csinfotechbd.retail.loan.setup.parReleaseLoan.ParReleaseLoanService;
import com.csinfotechbd.user.UserPrincipal;
import com.csinfotechbd.utillity.DateUtils;
import com.csinfotechbd.utillity.HttpSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/collection/dashboard/")
public class DashboardController {

    private final DashboardService dashboardService;
    private final DPDBucketService dpdBucketService;
    private final ProductTypeRepository productTypeRepository;
    private final PARaccountRuleLoanService paRaccountRuleLoanService;
    private final PARqueueAccRuleLoanService paRqueueAccRuleLoanService;
    private final NPLAccountRuleService nplAccountRuleService;
    private final NPLQueueAccService nplQueueAccService;
    private final ParReleaseLoanService paRreleaseAmountLoanService;
    private final NplReleaseLoanService npLreleaseAmountLoanService;
    private final LoanKPITargetByAmountService loanKPITargetByAmountService;
    private final LoanKPITargetByAccountService loanKPITargetByAccountService;
    private final LoanKPITargetWeightByAmountService loanKPITargetWeightByAmountService;
    private final CardKPITargetByAccountService cardKPITargetByAccountService;
    private final LoanKPITargetWeightByAccountService loanKPITargetWeightByAccountService;
    private final EmployeeRepository employeeRepository;
    private final CardBackendAccDetailService cardBackendAccDetailService;
    private final CardKpiAchDao cardKpiAchDao;
    private final DealerTargetCardManagerAccDao targetCardManagerDao;
    private final DealerTargetCardManagerDao dealerTargetCardManagerDao;
    private final ThreadPerformance threadPerformance;
    private final AmountWiseSummaryModelDao amountWiseSummaryModelDao;
    private final RetailLoanDashboardRepository retailLoanDashboardRepository;
    private final RetailCardDashBoardRepository retailCardDashBoardRepository;
    private final LoanPaymentRepository loanPaymentRepository;

    private final FollowUpService followUpService;
    private final HttpSessionUtils httpSessionUtils;
    private final DateUtils dateUtils;
    private final LoanAccountDistributionService loanAccountDistributionService;

    private final PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    @GetMapping(value = "paymentStatus/card")
    @ResponseBody
    public List<PaymentStatusModel> getPaymentStatusCard(@RequestParam(value = "userPin") String userPin,
                                                         @RequestParam(value = "unit") String unit,
                                                         @RequestParam(value = "designation") String designation,
                                                         @RequestParam(value = "userId") String userId,
                                                         HttpSession session) {

        Date today = dateUtils.getStartingPointOfDay(new Date());

        List<PaymentStatusModel> statusModels = new ArrayList<>();
        PaymentStatusModel totalPaid = new PaymentStatusModel();
        totalPaid.setNarration("Total Paid");

        PaymentStatusModel save = new PaymentStatusModel();
        save.setNarration("SAVE");

        PaymentStatusModel paidNotSave = new PaymentStatusModel();
        paidNotSave.setNarration("Paid But Not SAVE");

        PaymentStatusModel unpaid = new PaymentStatusModel();
        unpaid.setNarration("UNPAID");

        PaymentStatusModel autoDebitAc = new PaymentStatusModel();
        autoDebitAc.setNarration("Auto Debit Acc");

        PaymentStatusModel othersAcc = new PaymentStatusModel();
        othersAcc.setNarration("Others Acc");

        PaymentStatusModel secured = new PaymentStatusModel();
        secured.setNarration("Secured Acc");

        List<CardAccountDistributionInfo> cardDist = (List<CardAccountDistributionInfo>) session.getAttribute("cardDistributionList");

        if (cardDist.isEmpty())
            return statusModels;

        double totalPaidAmt = 0;
        List<CardAccountPaymentDetails> cardAccountPaymentDetails = new ArrayList<>();
        for (CardAccountDistributionInfo info : cardDist) {
            boolean flagToday = false;

            if (info.getAgeCode().toUpperCase().equals("CR")) {
                info.setAgeCode("0");
            }
            List<CardPaymentModel> paymentList = dashboardService.getCardPaymentByAcc(info.getCardAccountBasicInfo().getCardNo(),
                    dateUtils.getMonthStartDate());
            int MO_ageCode = Integer.parseInt(info.getAgeCode());
            //total paid
            if (paymentList.size() > 0) {
                totalPaid.setTotalAc(totalPaid.getTotalAc() + 1);
            } else {
                unpaid.setTotalAc(unpaid.getTotalAc() + 1);
            }

            for (CardPaymentModel paymentModel : paymentList) {
                cardAccountPaymentDetails.add(
                        new CardAccountPaymentDetails(paymentModel.getCardNo(), paymentModel.getAmount(), paymentModel.getPaymentDate()));
                totalPaidAmt = totalPaidAmt + paymentModel.getAmount();
            }

            //secured
            if (info.getSecureCard().length() > 1) {
                if (flagToday == false)
                    secured.setTotalAc(secured.getTotalAc() + 1);
                secured.setPaidAmntToday(totalPaidAmt);
            }


            //boolean flagCounted=false;
            for (CardPaymentModel paymentModel : paymentList) {
                totalPaid.setPaidAmnt(totalPaid.getPaidAmnt() + paymentModel.getAmount());

                int compare = paymentModel.getPaymentDate().compareTo(today);

                //save acc
                if (MO_ageCode == paymentModel.getCurrentAgeCode()) {
                    if (flagToday == false)
                        save.setTotalAc(save.getTotalAc() + 1);
                    save.setPaidAmnt(save.getPaidAmnt() + paymentModel.getAmount());
                } else {
                    if (flagToday == false)
                        paidNotSave.setTotalAc(paidNotSave.getTotalAc() + 1);
                    paidNotSave.setPaidAmnt(paidNotSave.getPaidAmnt() + paymentModel.getAmount());
                }

                //paid today
                if (compare >= 0) {
                    if (flagToday == false)
                        totalPaid.setTotalAcToday(totalPaid.getTotalAcToday() + 1);
                    totalPaid.setPaidAmntToday(totalPaid.getPaidAmntToday() + paymentModel.getAmount());

                    //save acc
                    if (MO_ageCode == paymentModel.getCurrentAgeCode()) {
                        if (flagToday == false)
                            save.setTotalAcToday(save.getTotalAcToday() + 1);
                        save.setPaidAmntToday(save.getPaidAmntToday() + paymentModel.getAmount());
                    } else {
                        if (flagToday == false)
                            paidNotSave.setTotalAcToday(paidNotSave.getTotalAcToday() + 1);
                        paidNotSave.setPaidAmntToday(paidNotSave.getPaidAmntToday() + paymentModel.getAmount());
                    }

                    //secured
                    if (info.getSecureCard().length() > 1) {
                        if (flagToday == false)
                            secured.setTotalAcToday(secured.getTotalAc() + 1);
                        secured.setPaidAmntToday(secured.getPaidAmntToday() + paymentModel.getAmount());
                    }
                }

                if (compare >= 0)
                    flagToday = true;

            }
            totalPaidAmt = 0;
        }

        statusModels.add(totalPaid);
        statusModels.add(save);
        statusModels.add(paidNotSave);
        statusModels.add(unpaid);
        statusModels.add(autoDebitAc);
        statusModels.add(othersAcc);
        statusModels.add(secured);

        PaymentStatusModel paymentStatusModel = new PaymentStatusModel();
        paymentStatusModel.setNarration("TOTAL");
        paymentStatusModel.setCardAccountPaymentDetails(cardAccountPaymentDetails);
        statusModels.add(paymentStatusModel);

        return statusModels;
    }
    
    @GetMapping(value = "payment-summary-card")
    @ResponseBody
    public List<CardPaymentSummaryModel> getPaymentStatusSummaryCard(@RequestParam(value = "userPin") String userPin){
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        List<String> userPinList = new ArrayList<>();
        userPinList.add(userPin);
        List<CardPaymentSummaryModel> cardPaymentSummaryModels= dashboardService.getCategorizedPaymentSummary(userPinList,startDate,endDate);
        return cardPaymentSummaryModels;
    }

    @GetMapping(value = "payment-summary/dealer-wise")
    @ResponseBody
    public Map<String, List<PaymentStatusModel>> getDealerWisePaymentSummary(List<String> dealerPins, HttpSession session) {
        Map<String, List<PaymentStatusModel>> summary = new LinkedHashMap<>();

        for (String pin : dealerPins) {
            summary.put(pin, new LinkedList<>());
        }


        List<LoanAccountDistributionInfo> distributionInfos =
                (List<LoanAccountDistributionInfo>) session.getAttribute("loanDistributionList");


        return summary;

    }

    @GetMapping(value = "distribution/ptp_status/card")
    @ResponseBody
    public List<PtpStatusSummary> getCardPtpStatus(HttpSession session) {

        List<CardAccountDistributionInfo> cardDist = (List<CardAccountDistributionInfo>) session.getAttribute("cardDistributionList");
        //List<//CardPtp> cardPtpList=dashboardService.getCardPtpByCustomer(dist.getCardAccountBasicInfo().getCustomer().getId());

//        PtpStatusSummary brokenPtp = new PtpStatusSummary("Broken", 0, 0);
//        PtpStatusSummary keptPtp = new PtpStatusSummary("Kept", 0, 0);
//        PtpStatusSummary curedPtp = new PtpStatusSummary("Cured", 0, 0);
//
        List<PtpStatusSummary> ptpStatusSummaryList = new ArrayList<>();
//
//        if (cardDist.isEmpty())
//            return ptpStatusSummaryList;
//
//        List<CardPtp> cardPtpList = new ArrayList<>();
//
//
//        for (CardAccountDistributionInfo distributionInfo : cardDist) {
//            cardPtpList = dashboardService.getCardPtpByCustomer(distributionInfo.getCardAccountBasicInfo().getCustomer().getId());
//
//            for (CardPtp loanPtp : cardPtpList) {
//                if (loanPtp.getCard_ptp_status().toLowerCase().contains("broken")) {
//                    brokenPtp.setNoOfAc(brokenPtp.getNoOfAc() + 1);
//                    brokenPtp.setAmount(brokenPtp.getAmount() + Double.parseDouble(distributionInfo.getOutstandingAmount()));
//                } else if (loanPtp.getCard_ptp_status().toLowerCase().contains("cured")) {
//                    curedPtp.setNoOfAc(curedPtp.getNoOfAc() + 1);
//                    curedPtp.setAmount(curedPtp.getAmount() + Double.parseDouble(distributionInfo.getOutstandingAmount()));
//                } else if (loanPtp.getCard_ptp_status().toLowerCase().contains("kept")) {
//                    keptPtp.setNoOfAc(keptPtp.getNoOfAc() + 1);
//                    keptPtp.setAmount(keptPtp.getAmount() + Double.parseDouble(distributionInfo.getOutstandingAmount()));
//                }
//            }
//        }
//        ptpStatusSummaryList.add(brokenPtp);
//        ptpStatusSummaryList.add(keptPtp);
//        ptpStatusSummaryList.add(curedPtp);

        return ptpStatusSummaryList;
    }

    //KPI target vs achievement LOAN
    @GetMapping(value = "distribution/kpi_vs_achiev/loan/account")
    @ResponseBody
    public List<ProductWiseSummary> getProductWiseKpiVsAchvLoanByAc(@RequestParam(value = "userPin") String userPin,
                                                                    @RequestParam(value = "unit") String unit,
                                                                    @RequestParam(value = "designation") String designation,
                                                                    @RequestParam(value = "userId") String userId,
                                                                    HttpSession session) {
        List<ProductWiseSummary> productWiseSummaryList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);

        List<LoanAccountDistributionInfo> accountDistributionList = (List<LoanAccountDistributionInfo>) session.getAttribute("loanDistributionList");

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
                        for (LoanAccountDistributionInfo distributionInfo : accountDistributionList) {
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

                            if (distributionInfo.getDpdBucket().toUpperCase().equals(dpdBucket.getBucketName().toUpperCase()) && distributionInfo.getSchemeCode().toUpperCase().equals(productType.getCode().toUpperCase())
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

    //KPI target vs achievement LOAN
    @GetMapping(value = "distribution/kpi_vs_achiev/loan")
    @ResponseBody
    public List<ProductWiseSummary> getProductWiseKpiVsAchvLoan(@RequestParam(value = "userPin") String userPin,
                                                                @RequestParam(value = "unit") String unit,
                                                                @RequestParam(value = "designation") String designation,
                                                                @RequestParam(value = "userId") String userId,
                                                                HttpSession session) {

        List<LoanAccountDistributionInfo> accountDistributionList = (List<LoanAccountDistributionInfo>) session.getAttribute("loanDistributionList");

        Map<String, Object> productWiseSummary = dashboardService.getProductWiseSummaryForDealer(accountDistributionList,
                userId, designation, unit);

        String dealerName = (String) productWiseSummary.getOrDefault("dealerName", "");
        List<ProductWiseSummary> productWiseSummaryList =
                (List<ProductWiseSummary>) productWiseSummary.getOrDefault("productWiseSummaryList", new LinkedList<>());

        threadPerformance.loanPerformanceCal(productWiseSummaryList, userPin, dealerName);
        return productWiseSummaryList;

    }

    //Queue and status summary LOAN
    @GetMapping(value = "distribution/amountWise")
    @ResponseBody
    public List<AmountWiseSummaryModel> distributionByAmount(@RequestParam(value = "userPin") String userPin,
                                                             @RequestParam(value = "unit") String unit,
                                                             @RequestParam(value = "designation") String designation,
                                                             @RequestParam(value = "userId") String userId,
                                                             HttpSession session) throws ParseException {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthStartDate(1);
        List<String> dealerPinList = peopleAllocationLogicRepository.getAllDealerPinByAnyPin(userPin);
        List<Tuple> summary = retailLoanDashboardRepository.getAmountWiseDistributionAndStatusSummary(dealerPinList, startDate, endDate);
        List<AmountWiseSummaryModel> amountWiseSummaryModels = summary.stream().map(AmountWiseSummaryModel::new).collect(Collectors.toList());
        return amountWiseSummaryModels;

    }

    //Queue and status summary LOAN dealer wise in (before Team lead now supervisor)
//    @GetMapping(value = "distribution/supervisor/queue-status-summary")
//    @ResponseBody
//    public List<AmountWiseSummaryModel> distributionQueue(@RequestParam(value = "userPins") String pin,
//                                                             @RequestParam(value = "unit") String unit,
//                                                             HttpSession session) throws ParseException {
//        Date startDate = dateUtils.getMonthStartDate();
//        Date endDate = dateUtils.getMonthStartDate(1);
//        List<Tuple> summary = retailLoanDashboardRepository.getAmountWiseDistributionAndStatusSummary(Arrays.asList(pin), startDate, endDate);
//        return summary.stream().map(AmountWiseSummaryModel::new).collect(Collectors.toList());
//
//    }

    //Queue and status summary LOAN
    @GetMapping(value = "distribution/accountWise")
    @ResponseBody
    public List<AccountWiseSummaryModel> distributionAccountWise(@RequestParam(value = "userPin") String userPin,
                                                                 @RequestParam(value = "unit") String unit,
                                                                 @RequestParam(value = "designation") String designation,
                                                                 @RequestParam(value = "userId") String userId,
                                                                 HttpSession session) {

        List<String> dealerPinList = peopleAllocationLogicRepository.getAllDealerPinByAnyPin(userPin);

        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthStartDate(1);
        List<Tuple> summary = retailLoanDashboardRepository.getAccountWiseDistributionAndStatusSummary(dealerPinList, startDate, endDate);
        List<AccountWiseSummaryModel> accountWiseSummaryModels = summary.stream().map(AccountWiseSummaryModel::new).collect(Collectors.toList());
        return accountWiseSummaryModels;
    }
    
    @GetMapping(value = "distribution/accountWise/card")
    @ResponseBody
    public List<AccountWiseSummaryModel> cardDistByAccount(@RequestParam(value = "userPin") String userPin,
                                                           @RequestParam(value = "unit") String unit,
                                                           @RequestParam(value = "designation") String designation,
                                                           @RequestParam(value = "userId") String userId,
                                                           HttpSession session){
        
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthStartDate(1);
        List<Tuple> summary = retailCardDashBoardRepository.getAccountWiseDistributionAndStatusSummary(Arrays.asList(userPin), startDate, endDate);
        return summary.stream().map(AccountWiseSummaryModel::new).collect(Collectors.toList());
    }
    //Queue and status summary CARD Account
//    @GetMapping(value = "distribution/accountWise/card")
//    @ResponseBody
//    public List<AccountWiseSummaryModel> cardDistByAccount(@RequestParam(value = "userPin") String userPin,
//                                                           @RequestParam(value = "unit") String unit,
//                                                           @RequestParam(value = "designation") String designation,
//                                                           @RequestParam(value = "userId") String userId,
//                                                           HttpSession session) {
//
//        List<AccountWiseSummaryModel> summaryModelList = new ArrayList<>();
//
//        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();
//
//        if (!designation.toUpperCase().equals("DEALER")) {
//            allocationList = dashboardService.getAllDealerList(userId, designation, unit);
//
//            for (PeopleAllocationLogicInfo logic : allocationList) {
//                List<AccountWiseSummaryModel> temp = accountWiseSummaryModelDao.getListByDealerPin(logic.getDealer().getPin());
//                summaryModelList.addAll(temp);
//            }
//
//        } else {
//            summaryModelList = accountWiseSummaryModelDao.getListByDealerPin(userPin);
//        }
//
//        return summaryModelList;
//    }

    //Queue and status summary CARD Amount
    @GetMapping(value = "distribution/amountWise/card")
    @ResponseBody
    public List<AmountWiseSummaryModel> cardDistByAmount(@RequestParam(value = "userPin") String userPin,
                                                         @RequestParam(value = "unit") String unit,
                                                         @RequestParam(value = "designation") String designation,
                                                         @RequestParam(value = "userId") String userId,
                                                         HttpSession session) {
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthStartDate(1);

        List<String> dealerPinList = peopleAllocationLogicRepository.getAllDealerPinByAnyPin(userPin);

        List<AmountWiseSummaryModel> summaryModelList = new ArrayList<>();

        List<PeopleAllocationLogicInfo> allocationList = new ArrayList<>();



        return summaryModelList;
    }

    //KPI target vs achievement CARD
    @GetMapping(value = "distribution/kpi_vs_achiev/card/account")
    @ResponseBody
    public List<KpiVsAchDataModel> getProductWiseKpiVsAchvCardAcc(@RequestParam(value = "userPin") String userPin,
                                                                  @RequestParam(value = "unit") String unit,
                                                                  @RequestParam(value = "designation") String designation,
                                                                  @RequestParam(value = "userId") String userId,
                                                                  HttpSession session) {
        List<CardAccountDistributionInfo> distributionList = (List<CardAccountDistributionInfo>) session.getAttribute("cardDistributionList");

        List<KpiVsAchDataModel> dataModelList = new ArrayList<>();

        if (distributionList.isEmpty())
            return dataModelList;

        List<CardKpiAchEntity> cardKpiAchList = new ArrayList<>();

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(userPrincipal.getEmpId());


        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);
        List<ProductTypeEntity> ptList = new ArrayList<>();
        List<AgeCodeEntity> ageCodeList = new ArrayList<>();

        for (PeopleAllocationLogicInfo logicInfo : allocationList) {
            for (ProductTypeEntity typeEntity : logicInfo.getProductTypeEntity()) {
                if (!ptList.contains(typeEntity))
                    ptList.add(typeEntity);
            }
            for (AgeCodeEntity ageCode : logicInfo.getAgeCodeEntity()) {
                if (!ageCodeList.contains(ageCode))
                    ageCodeList.add(ageCode);
            }
        }

        List<ProductTypeEntity> tempProducttypeList = new ArrayList<>();

        for (ProductTypeEntity typeEntity : ptList) {

            for (AgeCodeEntity ageCode : ageCodeList) {
                for (CardAccountDistributionInfo dist : distributionList) {
                    if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(typeEntity.getCode().toUpperCase())) {
                        {
                            if (!tempProducttypeList.contains(typeEntity))
                                tempProducttypeList.add(typeEntity);
                        }
                    }
                }

            }
        }

        ptList = tempProducttypeList;

        double amountPerPT = 0;
        double noOfAcPerPT = 0;
        //double noOfAcPerAgeCode;
        double minDueAmntPerAgeCode = 0;
        double amntOfAccPerAgeCode = 0;
        double amntOfSaveAc = 0;
        double amntOfFlowAc = 0;
        double amntOfBackAc = 0;
        double amntOfRegularAc = 0;


        for (ProductTypeEntity productType : ptList) {
            if (!designation.toUpperCase().equals("DEALER")) {
                allocationList = dashboardService.getAllDealerList(userId, designation, unit);
                for (PeopleAllocationLogicInfo logic : allocationList) {
                    //amountPerPT = amountPerPT + dashboardService.getTotalMinDueAmntPerPtCard(logic.getDealer().getPin(), productType.getCode());
                    //noOfAcPerPT = noOfAcPerPT + dashboardService.getNumberOfAcPerPtCard(logic.getDealer().getPin(), productType.getCode());
                }
            } else {
                //amountPerPT = dashboardService.getTotalMinDueAmntPerPtCard(userPin, productType.getCode());
                //noOfAcPerPT = noOfAcPerPT + dashboardService.getNumberOfAcPerPtCard(userPin, productType.getCode());
            }

            for (AgeCodeEntity ageCode : ageCodeList) {
                CardKPITargetWeightByAmountEntity kpiTargetWeight = null;

                double noOfAcPerAgeCode = 0;

                KpiVsAchDataModel dataModel = new KpiVsAchDataModel();

                CardKPITargetByAmountEntity kpiTarget = null;
                String dealerPin = "";
                //List<CardAccountDistributionInfo> checkedItem=new ArrayList<>();
                for (CardAccountDistributionInfo dist : distributionList) {
                    if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(productType.getCode().toUpperCase())) {
                        noOfAcPerAgeCode++;
                    }
                }

                for (CardAccountDistributionInfo dist : distributionList) {

                    if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(productType.getCode().toUpperCase())) {

                        CardKPITargetByAccountEntity kpiTargetByAccount = cardKPITargetByAccountService.getByProductAndAgeCodeByDealerPin(productType, ageCode, dist.getDealerPin(), employeeInfoEntity.getLocation().getCity());

                        dealerPin = dist.getDealerPin();
                        int MO_AgeCode = Integer.parseInt(dist.getAgeCode());
                        dist.getCardAccountBasicInfo().getCardNo();

                        CardBackendAccDetailsEntity backendAccDetail = cardBackendAccDetailService.getByAccNo(dist.getCardAccountBasicInfo().getCardNo(),
                                dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate());
                        CardKpiAchEntity kpiAchEntity = null;
                        try {
                            kpiAchEntity = cardKpiAchDao.getByAccNo(dist.getCardAccountBasicInfo().getCardNo());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        if (kpiAchEntity != null) {

                            kpiAchEntity.setAmntPerPT(amountPerPT);

                            try {
                                kpiAchEntity.setFlowAmntPerDay(kpiAchEntity.getFlowShortFall() / 22);
                                kpiAchEntity.setSaveAmntPerDay(kpiAchEntity.getSaveShortFall() / 22);
                                kpiAchEntity.setBackAmntPerDay(kpiAchEntity.getBackShortFall() / 22);
                                kpiAchEntity.setNplAmntPerDay(kpiAchEntity.getNplShortFall() / 22);
                                kpiAchEntity.setParAmntPerDay(kpiAchEntity.getParShortFall() / 22);
                                kpiAchEntity.setRegAmntPerDay(kpiAchEntity.getRegShortFall() / 22);
                                kpiAchEntity.setRawAmntPerDay(kpiAchEntity.getRawShortFall() / 22);
                            } catch (Exception e) {

                            }

                            dataModel.setCardsCategory(kpiAchEntity.getCardsCategory());
                            dataModel.setAgeCode(kpiAchEntity.getAgeCode());
                            dataModel.setAmntPerAgeCode(noOfAcPerAgeCode);

                            //Back
                            if (kpiAchEntity.getBackAch() > 0)
                                dataModel.setBackAch(dataModel.getBackAch() + 1);
                            if (kpiTargetByAccount != null) {
                                dataModel.setBackTarget((kpiTargetByAccount.getBackTarget() / 100) * noOfAcPerAgeCode);
                            }
                            dataModel.setBackShortFall(dataModel.getBackTarget() - dataModel.getBackAch());

                            if (dataModel.getBackTarget() > 0)
                                dataModel.setBackPerformance((dataModel.getBackAch() / dataModel.getBackTarget()) * 100);
                            dataModel.setBackWeight(kpiAchEntity.getBackWeight());
                            dataModel.setBackWeightedPerformance(dataModel.getBackWeight() * dataModel.getBackPerformance());
                            dataModel.setBackAmntPerDay(dataModel.getBackShortFall() / 22);
                            dataModel.setBackTargetPerc(kpiAchEntity.getBackTargetPerc());

                            //save
                            if (kpiAchEntity.getSaveAch() > 0)
                                dataModel.setSaveAch(dataModel.getSaveAch() + 1);

                            if (kpiTargetByAccount != null) {
                                if (kpiTargetByAccount.getSaveTarget() > 0)
                                    dataModel.setSaveTarget((kpiTargetByAccount.getSaveTarget() / 100) * noOfAcPerAgeCode);
                            }

                            if (dataModel.getSaveTarget() > 0)
                                dataModel.setSavePerformance((dataModel.getSaveAch() / dataModel.getSaveTarget()) * 100);
                            dataModel.setSaveShortFall(dataModel.getSaveTarget() - dataModel.getSaveAch());
                            dataModel.setSaveWeight(kpiAchEntity.getSaveWeight());
                            dataModel.setSaveWeightedPerformance(dataModel.getSaveWeight() * dataModel.getSavePerformance());
                            dataModel.setSaveAmntPerDay(dataModel.getSaveShortFall() / 22);
                            dataModel.setSaveTargetPerc(kpiAchEntity.getSaveTargetPerc());

                            //flow
                            if (kpiAchEntity.getSaveAch() > 0)
                                dataModel.setFlowAch(dataModel.getFlowAch() + 1);
                            if (kpiTargetByAccount != null) {
                                dataModel.setFlowTarget((kpiTargetByAccount.getFlowTarget() / 100) * noOfAcPerAgeCode);
                                dataModel.setRegTarget((kpiTargetByAccount.getRegularTarget() / 100) * noOfAcPerAgeCode);
                                dataModel.setParTarget((kpiTargetByAccount.getPARRelTarget() / 100) * noOfAcPerAgeCode);
                                dataModel.setNplTarget((kpiTargetByAccount.getNPLRelTarget() / 100) * noOfAcPerAgeCode);
                                dataModel.setRawTarget((kpiTargetByAccount.getRawCollectionTarget() / 100) * noOfAcPerAgeCode);
                                dataModel.setMinTarget((kpiTargetByAccount.getMinDueTarget() / 100) * noOfAcPerAgeCode);
                            }

                            dataModel.setFlowShortFall(dataModel.getFlowTarget() - dataModel.getFlowAch());
                            if (dataModel.getFlowTarget() > 0)
                                dataModel.setFlowPerformance((dataModel.getFlowAch() / dataModel.getFlowTarget()) * 100);
                            dataModel.setFlowWeight(kpiAchEntity.getFlowWeight());
                            dataModel.setFlowWeightedPerformance(dataModel.getFlowWeight() * dataModel.getFlowPerformance());
                            dataModel.setFlowAmntPerDay(dataModel.getFlowShortFall() / 22);
                            dataModel.setFlowTargetPerc(kpiAchEntity.getFlowTargetPerc());

                            //regular
                            if (kpiAchEntity.getRegAch() > 0)
                                dataModel.setRegAch(dataModel.getRegAch() + 1);

                            dataModel.setRegShortFall(dataModel.getRegTarget() - dataModel.getRegAch());
                            if (dataModel.getRegTarget() > 0)
                                dataModel.setRegPerformance((dataModel.getRegAch() / dataModel.getRegTarget()) * 100);
                            dataModel.setRegWeight(kpiAchEntity.getRegWeight());
                            dataModel.setRegWeightedPerformance(dataModel.getRegWeight() * dataModel.getRegPerformance());
                            dataModel.setRegAmntPerDay(dataModel.getRegShortFall() / 22);
                            dataModel.setRegTargetPerc(kpiAchEntity.getRegTargetPerc());

                            //par
                            if (kpiAchEntity.getParAch() > 0)
                                dataModel.setParAch(dataModel.getParAch() + 1);

                            dataModel.setParShortFall(dataModel.getParTarget() - dataModel.getParAch());
                            if (dataModel.getParTarget() > 0)
                                dataModel.setParPerformance((dataModel.getParAch() / dataModel.getParTarget()) * 100);
                            dataModel.setParWeight(kpiAchEntity.getParWeight());
                            dataModel.setParWeightedPerformance(dataModel.getParWeight() * dataModel.getParPerformance());
                            dataModel.setParAmntPerDay(dataModel.getParShortFall() / 22);
                            dataModel.setParTargetPerc(kpiAchEntity.getParTargetPerc());

                            //npl
                            if (kpiAchEntity.getNplAch() > 0)
                                dataModel.setNplAch(dataModel.getNplAch() + 1);

                            dataModel.setNplShortFall(dataModel.getNplTarget() - dataModel.getNplAch());
                            if (dataModel.getNplTarget() > 0)
                                dataModel.setNplPerformance((dataModel.getNplAch() / dataModel.getNplTarget()) * 100);
                            dataModel.setNplWeight(kpiAchEntity.getNplWeight());
                            dataModel.setNplWeightedPerformance(dataModel.getNplWeight() * dataModel.getNplPerformance());
                            dataModel.setNplAmntPerDay(dataModel.getNplShortFall() / 22);
                            dataModel.setNplTargetPerc(kpiAchEntity.getNplTargetPerc());

                            //raw
                            if (kpiAchEntity.getRawAch() > 0)
                                dataModel.setRawAch(dataModel.getRawAch() + 1);

                            dataModel.setRawShortFall(dataModel.getRawTarget() - dataModel.getRawAch());
                            if (dataModel.getRawTarget() > 0)
                                dataModel.setRawPerformance((dataModel.getRawAch() / dataModel.getRawTarget()) * 100);
                            dataModel.setRawWeight(kpiAchEntity.getRawWeight());
                            dataModel.setRawWeightedPerformance(dataModel.getRawWeight() * dataModel.getRawPerformance());
                            dataModel.setRawAmntPerDay(dataModel.getRawShortFall());
                            dataModel.setRawTargetPerc(kpiAchEntity.getRawTargetPerc());


                            double min_due_ach = kpiAchEntity.getMinAch();
                            if (min_due_ach > kpiAchEntity.getMoMinDueAmnt()) {
                                min_due_ach = kpiAchEntity.getMoMinDueAmnt();
                            }

                            //min due
                            if (min_due_ach > 0)
                                dataModel.setMinAch(dataModel.getMinAch() + min_due_ach);
                            dataModel.setMinShortFall(dataModel.getMinTarget() - dataModel.getMinAch());
                            if (dataModel.getMinTarget() > 0)
                                dataModel.setMinPerformance((dataModel.getMinAch() / dataModel.getMinTarget()) * 100);
                            dataModel.setMinWeight(kpiAchEntity.getMinWeight());
                            dataModel.setMinWeightedPerformance(dataModel.getMinWeight() * dataModel.getMinPerformance());
                            dataModel.setMinAmntPerDay(dataModel.getMinAmntPerDay() + kpiAchEntity.getMinAmntPerDay());
                            dataModel.setMinTargetPerc(kpiAchEntity.getMinTargetPerc());

                            //cardKpiAchList.add(kpiAchEntity);
                        }
                    }
                }
                dataModelList.add(dataModel);
                minDueAmntPerAgeCode = 0;
                amntOfAccPerAgeCode = 0;
                amntOfSaveAc = 0;
                amntOfFlowAc = 0;
                amntOfBackAc = 0;
                amntOfRegularAc = 0;
            }
            amountPerPT = 0;
            noOfAcPerPT = 0;

        }
        return dataModelList;
    }

    //KPI target vs achievement CARD
    @GetMapping(value = "distribution/kpi_vs_achiev/card")
    @ResponseBody
    public List<KpiVsAchDataModel> getProductWiseKpiVsAchvCard(@RequestParam(value = "userPin") String userPin,
                                                               @RequestParam(value = "unit") String unit,
                                                               @RequestParam(value = "designation") String designation,
                                                               @RequestParam(value = "userId") String userId,
                                                               HttpSession session) {
        List<CardAccountDistributionInfo> distributionList = (List<CardAccountDistributionInfo>) session.getAttribute("cardDistributionList");


        List<KpiVsAchDataModel> dataModelList = new ArrayList<>();

        if (distributionList.isEmpty())
            return dataModelList;

        List<CardKpiAchEntity> cardKpiAchList = new ArrayList<>();


        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);
        List<ProductTypeEntity> ptList = new ArrayList<>();
        List<AgeCodeEntity> ageCodeList = new ArrayList<>();

        for (PeopleAllocationLogicInfo logicInfo : allocationList) {
            for (ProductTypeEntity typeEntity : logicInfo.getProductTypeEntity()) {
                if (!ptList.contains(typeEntity))
                    ptList.add(typeEntity);
            }
            for (AgeCodeEntity ageCode : logicInfo.getAgeCodeEntity()) {
                if (!ageCodeList.contains(ageCode))
                    ageCodeList.add(ageCode);
            }
        }

        List<ProductTypeEntity> tempProducttypeList = new ArrayList<>();

        for (ProductTypeEntity typeEntity : ptList) {

            for (AgeCodeEntity ageCode : ageCodeList) {
                for (CardAccountDistributionInfo dist : distributionList) {
                    if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(typeEntity.getCode().toUpperCase())) {
                        {
                            if (!tempProducttypeList.contains(typeEntity))
                                tempProducttypeList.add(typeEntity);
                        }
                    }
                }

            }
        }

        ptList = tempProducttypeList;
        //UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(userPrincipal.getEmpId());


        double amountPerPT = 0;

        String dealerName = "";
        //List<CardKPITargetByAccountEntity> KpiTargetList = cardKPITargetByAccountService.getAllActive();

//        for(CardKPITargetByAccountEntity kpiTarget: KpiTargetList)
//        {
        for (ProductTypeEntity productType : ptList) {
            if (!designation.toUpperCase().equals("DEALER")) {
                allocationList = dashboardService.getAllDealerList(userId, designation, unit);
                for (PeopleAllocationLogicInfo logic : allocationList) {
                    //amountPerPT = amountPerPT + dashboardService.getTotalMinDueAmntPerPtCard(logic.getDealer().getPin(), productType.getCode());
                    //noOfAcPerPT = noOfAcPerPT + dashboardService.getNumberOfAcPerPtCard(logic.getDealer().getPin(), productType.getCode());
                }
            } else {
                //amountPerPT = dashboardService.getTotalMinDueAmntPerPtCard(userPin, productType.getCode());
                //noOfAcPerPT = noOfAcPerPT + dashboardService.getNumberOfAcPerPtCard(userPin, productType.getCode());
            }

            for (AgeCodeEntity ageCode : ageCodeList) {
                CardKPITargetWeightByAmountEntity kpiTargetWeight = null;

                KpiVsAchDataModel dataModel = new KpiVsAchDataModel();

                CardKPITargetByAmountEntity kpiTarget = null;
                String dealerPin = "";

                //List<CardAccountDistributionInfo> checkedItem=new ArrayList<>();
                for (CardAccountDistributionInfo dist : distributionList) {

                    if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(productType.getCode().toUpperCase())) {
                        dealerPin = dist.getDealerPin();
                        dealerName = dist.getDealerName();
                        int MO_AgeCode = Integer.parseInt(dist.getAgeCode());
                        dist.getCardAccountBasicInfo().getCardNo();

                        CardBackendAccDetailsEntity backendAccDetail = cardBackendAccDetailService.getByAccNo(dist.getCardAccountBasicInfo().getCardNo(),
                                dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate());
                        CardKpiAchEntity kpiAchEntity = null;
                        try {
                            kpiAchEntity = cardKpiAchDao.getByAccNo(backendAccDetail.getCardNo());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        if (kpiAchEntity != null) {
                            kpiAchEntity.setAmntPerPT(amountPerPT);

                            dataModel.setCardsCategory(kpiAchEntity.getCardsCategory());
                            dataModel.setAgeCode(kpiAchEntity.getAgeCode());
                            dataModel.setAmntPerAgeCode(kpiAchEntity.getNewOutstanding() + dataModel.getAmntPerAgeCode());

                            //Back
                            dataModel.setBackAch(dataModel.getBackAch() + kpiAchEntity.getBackAch());
                            dataModel.setBackTarget(dataModel.getBackTarget() + kpiAchEntity.getBackTarget());
                            dataModel.setBackShortFall(dataModel.getBackTarget() - kpiAchEntity.getBackAch());
                            if (dataModel.getBackTarget() > 0)
                                dataModel.setBackPerformance((dataModel.getBackAch() / dataModel.getBackTarget()) * 100);
                            dataModel.setBackWeight(kpiAchEntity.getBackWeight());
                            dataModel.setBackWeightedPerformance(dataModel.getBackWeight() * dataModel.getBackPerformance());
                            dataModel.setBackAmntPerDay(dataModel.getBackShortFall() / 22);
                            dataModel.setBackTargetPerc(kpiAchEntity.getBackTargetPerc());

                            //save
                            dataModel.setSaveAch(dataModel.getSaveAch() + kpiAchEntity.getSaveAch());
                            dataModel.setSaveTarget(dataModel.getSaveTarget() + kpiAchEntity.getSaveTarget());
                            if (dataModel.getSaveTarget() > 0)
                                dataModel.setSavePerformance((dataModel.getSaveAch() / dataModel.getSaveTarget()) * 100);
                            dataModel.setSaveShortFall(dataModel.getSaveTarget() - dataModel.getSaveAch());
                            dataModel.setSaveWeight(kpiAchEntity.getSaveWeight());
                            dataModel.setSaveWeightedPerformance(dataModel.getSaveWeight() * dataModel.getSavePerformance());
                            dataModel.setSaveAmntPerDay(dataModel.getSaveShortFall() / 22);
                            dataModel.setSaveTargetPerc(kpiAchEntity.getSaveTargetPerc());

                            //flow
                            dataModel.setFlowAch(dataModel.getFlowAch() + kpiAchEntity.getFlowAch());
                            dataModel.setFlowTarget(dataModel.getFlowTarget() + kpiAchEntity.getFlowTarget());
                            dataModel.setFlowShortFall(dataModel.getFlowTarget() - dataModel.getFlowAch());
                            if (dataModel.getFlowTarget() > 0)
                                dataModel.setFlowPerformance((dataModel.getFlowAch() / dataModel.getFlowTarget()) * 100);
                            dataModel.setFlowWeight(kpiAchEntity.getFlowWeight());
                            dataModel.setFlowWeightedPerformance(dataModel.getFlowWeight() * dataModel.getFlowPerformance());
                            dataModel.setFlowAmntPerDay(dataModel.getFlowShortFall() / 22);
                            dataModel.setFlowTargetPerc(kpiAchEntity.getFlowTargetPerc());

                            //regular
                            dataModel.setRegAch(dataModel.getRegAch() + kpiAchEntity.getRegAch());
                            dataModel.setRegTarget(dataModel.getRegTarget() + kpiAchEntity.getRegTarget());
                            dataModel.setRegShortFall(dataModel.getRegTarget() - dataModel.getRegAch());
                            if (dataModel.getRegTarget() > 0)
                                dataModel.setRegPerformance((dataModel.getRegAch() / dataModel.getRegTarget()) * 100);
                            dataModel.setRegWeight(kpiAchEntity.getRegWeight());
                            dataModel.setRegWeightedPerformance(dataModel.getRegWeight() * dataModel.getRegPerformance());
                            dataModel.setRegAmntPerDay(dataModel.getRegShortFall() / 22);
                            dataModel.setRegTargetPerc(kpiAchEntity.getRegTargetPerc());

                            //par
                            dataModel.setParAch(dataModel.getParAch() + kpiAchEntity.getParAch());
                            dataModel.setParTarget(dataModel.getParTarget() + kpiAchEntity.getParTarget());
                            dataModel.setParShortFall(dataModel.getParTarget() - dataModel.getParAch());
                            if (dataModel.getParTarget() > 0)
                                dataModel.setParPerformance((dataModel.getParAch() / dataModel.getParTarget()) * 100);
                            dataModel.setParWeight(kpiAchEntity.getParWeight());
                            dataModel.setParWeightedPerformance(dataModel.getParWeight() * dataModel.getParPerformance());
                            dataModel.setParAmntPerDay(dataModel.getParShortFall() / 22);
                            dataModel.setParTargetPerc(kpiAchEntity.getParTargetPerc());

                            //npl
                            dataModel.setNplAch(dataModel.getNplAch() + kpiAchEntity.getNplAch());
                            dataModel.setNplTarget(dataModel.getNplTarget() + kpiAchEntity.getNplTarget());
                            dataModel.setNplShortFall(dataModel.getNplTarget() - dataModel.getNplAch());
                            if (dataModel.getNplTarget() > 0)
                                dataModel.setNplPerformance((dataModel.getNplAch() / dataModel.getNplTarget()) * 100);
                            dataModel.setNplWeight(kpiAchEntity.getNplWeight());
                            dataModel.setNplWeightedPerformance(dataModel.getNplWeight() * dataModel.getNplPerformance());
                            dataModel.setNplAmntPerDay(dataModel.getNplShortFall() / 22);
                            dataModel.setNplTargetPerc(kpiAchEntity.getNplTargetPerc());

                            //raw
                            dataModel.setRawAch(dataModel.getRawAch() + kpiAchEntity.getRawAch());
                            dataModel.setRawTarget(dataModel.getRawTarget() + kpiAchEntity.getRawTarget());
                            dataModel.setRawShortFall(dataModel.getRawTarget() - dataModel.getRawAch());
                            if (dataModel.getRawTarget() > 0)
                                dataModel.setRawPerformance((dataModel.getRawAch() / dataModel.getRawTarget()) * 100);
                            dataModel.setRawWeight(kpiAchEntity.getRawWeight());
                            dataModel.setRawWeightedPerformance(dataModel.getRawWeight() * dataModel.getRawPerformance());
                            dataModel.setRawAmntPerDay(dataModel.getRawShortFall() / 22);
                            dataModel.setRawTargetPerc(kpiAchEntity.getRawTargetPerc());


                            double min_due_ach = kpiAchEntity.getMinAch();
                            if (min_due_ach > kpiAchEntity.getMoMinDueAmnt()) {
                                min_due_ach = kpiAchEntity.getMoMinDueAmnt();
                            }

                            //min due
                            dataModel.setMinAch(dataModel.getMinAch() + min_due_ach);
                            dataModel.setMinTarget(dataModel.getMinTarget() + kpiAchEntity.getMinTarget());
                            dataModel.setMinShortFall(dataModel.getMinTarget() - dataModel.getMinAch());
                            if (dataModel.getMinTarget() > 0)
                                dataModel.setMinPerformance((dataModel.getMinAch() / dataModel.getMinTarget()) * 100);
                            dataModel.setMinWeight(kpiAchEntity.getMinWeight());
                            dataModel.setMinWeightedPerformance(dataModel.getMinWeight() * dataModel.getMinPerformance());
                            dataModel.setMinAmntPerDay(dataModel.getMinShortFall() / 22);
                            dataModel.setMinTargetPerc(kpiAchEntity.getMinTargetPerc());

                            //cardKpiAchList.add(kpiAchEntity);
                        }
                    }
                }
                dataModelList.add(dataModel);
            }
            amountPerPT = 0;

        }


        List<KpiVsAchDataModel> temp = new ArrayList<>();

        for (KpiVsAchDataModel kpiVsAchData : dataModelList) {
            if (kpiVsAchData.getMinTarget() <= 0 && kpiVsAchData.getBackTarget() <= 0 && kpiVsAchData.getFlowTarget() <= 0 && kpiVsAchData.getSaveTarget() <= 0) {
                temp.add(kpiVsAchData);
            }
        }
        dataModelList.removeAll(temp);

        threadPerformance.cardPerformanceCal(dataModelList, userPin, dealerName);
        return dataModelList;
    }

    //KPI Manager target vs achievement CARD
    @GetMapping(value = "targetByManager/amountWise/card")
    @ResponseBody
    public List<KpiVsAchDataModel> getTargetByManByAmountCard(@RequestParam(value = "userPin") String userPin,
                                                              @RequestParam(value = "unit") String unit,
                                                              @RequestParam(value = "designation") String designation,
                                                              @RequestParam(value = "userId") String userId,
                                                              HttpSession session) {

        List<CardAccountDistributionInfo> distributionList = (List<CardAccountDistributionInfo>) session.getAttribute("cardDistributionList");

        List<KpiVsAchDataModel> dataModelList = new ArrayList<>();
        if (distributionList.isEmpty())
            return dataModelList;

        List<CardKpiAchEntity> cardKpiAchList = new ArrayList<>();

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(userPrincipal.getEmpId());


        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);
        List<ProductTypeEntity> ptList = new ArrayList<>();
        List<AgeCodeEntity> ageCodeList = new ArrayList<>();

        for (PeopleAllocationLogicInfo logicInfo : allocationList) {
            for (ProductTypeEntity typeEntity : logicInfo.getProductTypeEntity()) {
                if (!ptList.contains(typeEntity))
                    ptList.add(typeEntity);
            }
            for (AgeCodeEntity ageCode : logicInfo.getAgeCodeEntity()) {
                if (!ageCodeList.contains(ageCode))
                    ageCodeList.add(ageCode);
            }
        }

        List<ProductTypeEntity> tempProducttypeList = new ArrayList<>();

        for (ProductTypeEntity typeEntity : ptList) {

            for (AgeCodeEntity ageCode : ageCodeList) {
                for (CardAccountDistributionInfo dist : distributionList) {
                    if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(typeEntity.getCode().toUpperCase())) {
                        {
                            if (!tempProducttypeList.contains(typeEntity))
                                tempProducttypeList.add(typeEntity);
                        }
                    }
                }

            }
        }

        ptList = tempProducttypeList;

        for (ProductTypeEntity productType : ptList) {
            double amountPerPT = 0;
            if (!designation.toUpperCase().equals("DEALER")) {
                allocationList = dashboardService.getAllDealerList(userId, designation, unit);
                for (PeopleAllocationLogicInfo logic : allocationList) {
                    amountPerPT = amountPerPT + dashboardService.getTotalMinDueAmntPerPtCard(logic.getDealer().getPin(), productType.getCode());
                    //noOfAcPerPT = noOfAcPerPT + dashboardService.getNumberOfAcPerPtCard(logic.getDealer().getPin(), productType.getCode());
                }
            } else {
                amountPerPT = dashboardService.getTotalMinDueAmntPerPtCard(userPin, productType.getCode());
                //noOfAcPerPT = noOfAcPerPT + dashboardService.getNumberOfAcPerPtCard(userPin, productType.getCode());
            }

            for (AgeCodeEntity ageCode : ageCodeList) {


                double noOfAcPerPT = 0;
                //double noOfAcPerAgeCode;
                double minDueAmntPerAgeCode = 0;
                double amntOfAccPerAgeCode = 0;
                double amntOfSaveAc = 0;
                double amntOfFlowAc = 0;
                double amntOfBackAc = 0;
                double amntOfRegularAc = 0;

                CardKPITargetWeightByAmountEntity kpiTargetWeight = null;

                double noOfAcPerAgeCode = 0;

                KpiVsAchDataModel dataModel = new KpiVsAchDataModel();

                CardKPITargetByAmountEntity kpiTarget = null;
                String dealerPin = "";
                //List<CardAccountDistributionInfo> checkedItem=new ArrayList<>();
                for (CardAccountDistributionInfo dist : distributionList) {
                    if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(productType.getCode().toUpperCase())) {
                        noOfAcPerAgeCode++;
                    }
                }

                for (CardAccountDistributionInfo dist : distributionList) {

                    if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(productType.getCode().toUpperCase())) {

                        DealerTargetAmountCardManager dealerTargetCardManager = dealerTargetCardManagerDao.getTargetByAgeSupVLoc(ageCode, dist.getSupervisorPin(), employeeInfoEntity.getLocation().getCity());

                        dealerPin = dist.getDealerPin();
                        int MO_AgeCode = Integer.parseInt(dist.getAgeCode());
                        dist.getCardAccountBasicInfo().getCardNo();

                        CardBackendAccDetailsEntity backendAccDetail = cardBackendAccDetailService.getByAccNo(dist.getCardAccountBasicInfo().getCardNo(),
                                dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate());
                        CardKpiAchManagerEntity cardKpiAchManager = null;
                        try {
                            cardKpiAchManager = cardKpiAchDao.getByAccNoMan(backendAccDetail.getCardNo());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        if (cardKpiAchManager != null) {

                            cardKpiAchManager.setFlowTarget(backendAccDetail.getTargetFlowAmnt());
                            cardKpiAchManager.setAmntPerPT(amountPerPT);

//                            try {
//                                cardKpiAchManager.setFlowAmntPerDay(cardKpiAchManager.getFlowShortFall() / 22);
//                                cardKpiAchManager.setSaveAmntPerDay(cardKpiAchManager.getSaveShortFall() / 22);
//                                cardKpiAchManager.setBackAmntPerDay(cardKpiAchManager.getBackShortFall() / 22);
//                                cardKpiAchManager.setNplAmntPerDay(cardKpiAchManager.getNplShortFall() / 22);
//                                cardKpiAchManager.setParAmntPerDay(cardKpiAchManager.getParShortFall() / 22);
//                                cardKpiAchManager.setRegAmntPerDay(cardKpiAchManager.getRegShortFall() / 22);
//                                cardKpiAchManager.setRawAmntPerDay(cardKpiAchManager.getRawShortFall() / 22);
//                            } catch (Exception e) {
//
//                            }

                            dataModel.setCardsCategory(cardKpiAchManager.getCardsCategory());
                            dataModel.setAgeCode(cardKpiAchManager.getAgeCode());
                            dataModel.setAmntPerAgeCode(dataModel.getAmntPerAgeCode() + cardKpiAchManager.getNewOutstanding());

                            //Back
                            dataModel.setBackAch(dataModel.getBackAch() + cardKpiAchManager.getBackAch());
                            dataModel.setBackTarget(dataModel.getBackTarget() + cardKpiAchManager.getBackTarget());
                            dataModel.setBackShortFall(dataModel.getBackTarget() - cardKpiAchManager.getBackAch());
                            if (dataModel.getBackTarget() > 0)
                                dataModel.setBackPerformance((dataModel.getBackAch() / dataModel.getBackTarget()) * 100);
                            dataModel.setBackWeight(cardKpiAchManager.getBackWeight());
                            dataModel.setBackWeightedPerformance(dataModel.getBackWeight() * dataModel.getBackPerformance());
                            dataModel.setBackAmntPerDay(dataModel.getBackShortFall() / 22);
                            dataModel.setBackTargetPerc(cardKpiAchManager.getBackTargetPerc());

                            //save
                            dataModel.setSaveAch(dataModel.getSaveAch() + cardKpiAchManager.getSaveAch());
                            dataModel.setSaveTarget(dataModel.getSaveTarget() + cardKpiAchManager.getSaveTarget());
                            if (dataModel.getSaveTarget() > 0)
                                dataModel.setSavePerformance((dataModel.getSaveAch() / dataModel.getSaveTarget()) * 100);
                            dataModel.setSaveShortFall(dataModel.getSaveTarget() - dataModel.getSaveAch());
                            dataModel.setSaveWeight(cardKpiAchManager.getSaveWeight());
                            dataModel.setSaveWeightedPerformance(dataModel.getSaveWeight() * dataModel.getSavePerformance());
                            dataModel.setSaveAmntPerDay(dataModel.getSaveShortFall() / 22);
                            dataModel.setSaveTargetPerc(cardKpiAchManager.getSaveTargetPerc());

                            //flow
                            dataModel.setFlowAch(dataModel.getFlowAch() + cardKpiAchManager.getSaveAch());
                            dataModel.setFlowTarget(dataModel.getFlowTarget() + cardKpiAchManager.getFlowTarget());
                            dataModel.setFlowShortFall(dataModel.getFlowTarget() - dataModel.getFlowAch());
                            if (dataModel.getFlowTarget() > 0)
                                dataModel.setFlowPerformance((dataModel.getFlowAch() / dataModel.getFlowTarget()) * 100);
                            dataModel.setFlowWeight(cardKpiAchManager.getFlowWeight());
                            dataModel.setFlowWeightedPerformance(dataModel.getFlowWeight() * dataModel.getFlowPerformance());
                            dataModel.setFlowAmntPerDay(dataModel.getFlowShortFall() / 22);
                            dataModel.setFlowTargetPerc(cardKpiAchManager.getFlowTargetPerc());

                            //regular
                            dataModel.setRegAch(dataModel.getRegAch() + cardKpiAchManager.getRegAch());
                            dataModel.setRegTarget(dataModel.getRegTarget() + cardKpiAchManager.getRegTarget());
                            dataModel.setRegShortFall(dataModel.getRegTarget() - dataModel.getRegAch());
                            if (dataModel.getRegTarget() > 0)
                                dataModel.setRegPerformance((dataModel.getRegAch() / dataModel.getRegTarget()) * 100);
                            dataModel.setRegWeight(cardKpiAchManager.getRegWeight());
                            dataModel.setRegWeightedPerformance(dataModel.getRegWeight() * dataModel.getRegPerformance());
                            dataModel.setRegAmntPerDay(dataModel.getRegShortFall() / 22);
                            dataModel.setRegTargetPerc(cardKpiAchManager.getRegTargetPerc());

                            //par
                            dataModel.setParAch(dataModel.getParAch() + cardKpiAchManager.getParAch());
                            dataModel.setParTarget(dataModel.getParTarget() + cardKpiAchManager.getParTarget());
                            dataModel.setParShortFall(dataModel.getParTarget() - dataModel.getParAch());
                            if (dataModel.getParTarget() > 0)
                                dataModel.setParPerformance((dataModel.getParAch() / dataModel.getParTarget()) * 100);
                            dataModel.setParWeight(cardKpiAchManager.getParWeight());
                            dataModel.setParWeightedPerformance(dataModel.getParWeight() * dataModel.getParPerformance());
                            dataModel.setParAmntPerDay(dataModel.getParShortFall() / 22);
                            dataModel.setParTargetPerc(cardKpiAchManager.getParTargetPerc());

                            //npl
                            dataModel.setNplAch(dataModel.getNplAch() + cardKpiAchManager.getNplAch());
                            dataModel.setNplTarget(dataModel.getNplTarget() + cardKpiAchManager.getNplTarget());
                            dataModel.setNplShortFall(dataModel.getNplTarget() - dataModel.getNplAch());
                            if (dataModel.getNplTarget() > 0)
                                dataModel.setNplPerformance((dataModel.getNplAch() / dataModel.getNplTarget()) * 100);
                            dataModel.setNplWeight(cardKpiAchManager.getNplWeight());
                            dataModel.setNplWeightedPerformance(dataModel.getNplWeight() * dataModel.getNplPerformance());
                            dataModel.setNplAmntPerDay(dataModel.getNplShortFall() / 22);
                            dataModel.setNplTargetPerc(cardKpiAchManager.getNplTargetPerc());

                            //raw
                            dataModel.setRawAch(dataModel.getRawAch() + cardKpiAchManager.getRawAch());
                            dataModel.setRawTarget(dataModel.getRawTarget() + cardKpiAchManager.getRawTarget());
                            dataModel.setRawShortFall(dataModel.getRawTarget() - dataModel.getRawAch());
                            if (dataModel.getRawTarget() > 0)
                                dataModel.setRawPerformance((dataModel.getRawAch() / dataModel.getRawTarget()) * 100);
                            dataModel.setRawWeight(cardKpiAchManager.getRawWeight());
                            dataModel.setRawWeightedPerformance(dataModel.getRawWeight() * dataModel.getRawPerformance());
                            dataModel.setRawAmntPerDay(dataModel.getRawShortFall() / 22);
                            dataModel.setRawTargetPerc(cardKpiAchManager.getRawTargetPerc());


                            double min_due_ach = cardKpiAchManager.getMinAch();
                            if (min_due_ach > cardKpiAchManager.getMoMinDueAmnt()) {
                                min_due_ach = cardKpiAchManager.getMoMinDueAmnt();
                            }

                            //min due
                            dataModel.setMinAch(dataModel.getMinAch() + min_due_ach);
                            dataModel.setMinTarget(dataModel.getMinTarget() + cardKpiAchManager.getMinTarget());
                            dataModel.setMinShortFall(dataModel.getMinTarget() - dataModel.getMinAch());
                            if (dataModel.getMinTarget() > 0)
                                dataModel.setMinPerformance((dataModel.getMinAch() / dataModel.getMinTarget()) * 100);
                            dataModel.setMinWeight(cardKpiAchManager.getMinWeight());
                            dataModel.setMinWeightedPerformance(dataModel.getMinWeight() * dataModel.getMinPerformance());
                            dataModel.setMinAmntPerDay(dataModel.getMinAmntPerDay() + cardKpiAchManager.getMinAmntPerDay());
                            dataModel.setMinTargetPerc(cardKpiAchManager.getMinTargetPerc());
                        }
                    }
                }
                dataModelList.add(dataModel);
                minDueAmntPerAgeCode = 0;
                amntOfAccPerAgeCode = 0;
                amntOfSaveAc = 0;
                amntOfFlowAc = 0;
                amntOfBackAc = 0;
                amntOfRegularAc = 0;
            }
            amountPerPT = 0;
        }
        return dataModelList;

    }

    //KPI Manager target vs achievement CARD
    @GetMapping(value = "targetByManager/accountWise/card")
    @ResponseBody
    public List<KpiVsAchDataModel> getTargetByManByAcCard(@RequestParam(value = "userPin") String userPin,
                                                          @RequestParam(value = "unit") String unit,
                                                          @RequestParam(value = "designation") String designation,
                                                          @RequestParam(value = "userId") String userId,
                                                          HttpSession session) {

        List<CardAccountDistributionInfo> distributionList = (List<CardAccountDistributionInfo>) session.getAttribute("cardDistributionList");

        List<KpiVsAchDataModel> dataModelList = new ArrayList<>();

        if (distributionList.isEmpty())
            return dataModelList;

        List<CardKpiAchEntity> cardKpiAchList = new ArrayList<>();

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(userPrincipal.getEmpId());


        List<PeopleAllocationLogicInfo> allocationList = dashboardService.getAllDealerList(userId, designation, unit);
        List<ProductTypeEntity> ptList = new ArrayList<>();
        List<AgeCodeEntity> ageCodeList = new ArrayList<>();

        for (PeopleAllocationLogicInfo logicInfo : allocationList) {
            for (ProductTypeEntity typeEntity : logicInfo.getProductTypeEntity()) {
                if (!ptList.contains(typeEntity))
                    ptList.add(typeEntity);
            }
            for (AgeCodeEntity ageCode : logicInfo.getAgeCodeEntity()) {
                if (!ageCodeList.contains(ageCode))
                    ageCodeList.add(ageCode);
            }
        }

        double amountPerPT = 0;
        double noOfAcPerPT = 0;
        //double noOfAcPerAgeCode;
        double minDueAmntPerAgeCode = 0;
        double amntOfAccPerAgeCode = 0;
        double amntOfSaveAc = 0;
        double amntOfFlowAc = 0;
        double amntOfBackAc = 0;
        double amntOfRegularAc = 0;


        List<ProductTypeEntity> tempProducttypeList = new ArrayList<>();

        for (ProductTypeEntity typeEntity : ptList) {

            for (AgeCodeEntity ageCode : ageCodeList) {
                for (CardAccountDistributionInfo dist : distributionList) {
                    if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(typeEntity.getCode().toUpperCase())) {
                        {
                            if (!tempProducttypeList.contains(typeEntity))
                                tempProducttypeList.add(typeEntity);
                        }
                    }
                }

            }
        }

        ptList = tempProducttypeList;

        for (ProductTypeEntity productType : ptList) {

//            if (!designation.toUpperCase().equals("DEALER")) {
//                for (PeopleAllocationLogicInfo logic : allocationList) {
//                    //amountPerPT = amountPerPT + dashboardService.getTotalMinDueAmntPerPtCard(logic.getDealer().getPin(), productType.getCode());
//                    //noOfAcPerPT = noOfAcPerPT + dashboardService.getNumberOfAcPerPtCard(logic.getDealer().getPin(), productType.getCode());
//                }
//            } else {
//                //amountPerPT = dashboardService.getTotalMinDueAmntPerPtCard(userPin, productType.getCode());
//                //noOfAcPerPT = noOfAcPerPT + dashboardService.getNumberOfAcPerPtCard(userPin, productType.getCode());
//            }

            for (AgeCodeEntity ageCode : ageCodeList) {
                CardKPITargetWeightByAmountEntity kpiTargetWeight = null;

                double noOfAcPerAgeCode = 0;

                KpiVsAchDataModel dataModel = new KpiVsAchDataModel();

//                CardKPITargetByAmountEntity kpiTarget = null;
                String dealerPin = "";
                //List<CardAccountDistributionInfo> checkedItem=new ArrayList<>();
                for (CardAccountDistributionInfo dist : distributionList) {
                    if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(productType.getCode().toUpperCase())) {
                        noOfAcPerAgeCode++;
                    }
                }

                for (CardAccountDistributionInfo dist : distributionList) {

                    if (dist.getAgeCode().toUpperCase().equals(ageCode.getName().toUpperCase()) && dist.getProductGroup().toUpperCase().equals(productType.getCode().toUpperCase())) {

                        DealerTargetCardManager dealerTargetCardManager = targetCardManagerDao.getByProductAndAgeCodeByDealerPin(productType, ageCode, dist.getSupervisorPin(), employeeInfoEntity.getLocation().getCity());

                        dealerPin = dist.getDealerPin();
                        int MO_AgeCode = Integer.parseInt(dist.getAgeCode());
                        dist.getCardAccountBasicInfo().getCardNo();

                        CardBackendAccDetailsEntity backendAccDetail = cardBackendAccDetailService.getByAccNo(dist.getCardAccountBasicInfo().getCardNo(),
                                dateUtils.getMonthStartDate(), dateUtils.getMonthEndDate());
                        CardKpiAchManagerEntity cardKpiAchManager = null;
                        try {
                            cardKpiAchManager = cardKpiAchDao.getByAccNoMan(backendAccDetail.getCardNo());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        if (cardKpiAchManager != null) {

                            cardKpiAchManager.setAmntPerPT(amountPerPT);

                            try {
//                                cardKpiAchManager.setFlowAmntPerDay(cardKpiAchManager.getFlowShortFall() / 22);
//                                cardKpiAchManager.setSaveAmntPerDay(cardKpiAchManager.getSaveShortFall() / 22);
//                                cardKpiAchManager.setBackAmntPerDay(cardKpiAchManager.getBackShortFall() / 22);
//                                cardKpiAchManager.setNplAmntPerDay(cardKpiAchManager.getNplShortFall() / 22);
//                                cardKpiAchManager.setParAmntPerDay(cardKpiAchManager.getParShortFall() / 22);
//                                cardKpiAchManager.setRegAmntPerDay(cardKpiAchManager.getRegShortFall() / 22);
//                                cardKpiAchManager.setRawAmntPerDay(cardKpiAchManager.getRawShortFall() / 22);
                            } catch (Exception e) {

                            }

                            dataModel.setCardsCategory(cardKpiAchManager.getCardsCategory());
                            dataModel.setAgeCode(cardKpiAchManager.getAgeCode());
                            dataModel.setAmntPerAgeCode(noOfAcPerAgeCode);

                            //Back
                            if (cardKpiAchManager.getBackAch() > 0)
                                dataModel.setBackAch(dataModel.getBackAch() + 1);
                            if (dealerTargetCardManager != null) {
                                dataModel.setBackTarget((dealerTargetCardManager.getBackAccountTarget() / 100) * noOfAcPerAgeCode);
                                dataModel.setSaveTarget((dealerTargetCardManager.getSaveAccountTarget() / 100) * noOfAcPerAgeCode);
                                dataModel.setFlowTarget((dealerTargetCardManager.getFlowAccountTarget() / 100) * noOfAcPerAgeCode);
                                dataModel.setRegTarget((dealerTargetCardManager.getRegularAccountTarget() / 100) * noOfAcPerAgeCode);
                                dataModel.setParTarget((dealerTargetCardManager.getParReleaseAmountTarget() / 100) * noOfAcPerAgeCode);
                                dataModel.setNplTarget((dealerTargetCardManager.getNplReleaseAmountTarget() / 100) * noOfAcPerAgeCode);
                            }

                            dataModel.setBackShortFall(dataModel.getBackTarget() - dataModel.getBackAch());

                            if (dataModel.getBackTarget() > 0)
                                dataModel.setBackPerformance((dataModel.getBackAch() / dataModel.getBackTarget()) * 100);
                            dataModel.setBackWeight(cardKpiAchManager.getBackWeight());
                            dataModel.setBackWeightedPerformance(dataModel.getBackWeight() * dataModel.getBackPerformance());
                            dataModel.setBackAmntPerDay(dataModel.getBackShortFall() / 22);
                            dataModel.setBackTargetPerc(cardKpiAchManager.getBackTargetPerc());

                            //save
                            if (cardKpiAchManager.getSaveAch() > 0)
                                dataModel.setSaveAch(dataModel.getSaveAch() + 1);


                            if (dataModel.getSaveTarget() > 0)
                                dataModel.setSavePerformance((dataModel.getSaveAch() / dataModel.getSaveTarget()) * 100);
                            dataModel.setSaveShortFall(dataModel.getSaveTarget() - dataModel.getSaveAch());
                            dataModel.setSaveWeight(cardKpiAchManager.getSaveWeight());
                            dataModel.setSaveWeightedPerformance(dataModel.getSaveWeight() * dataModel.getSavePerformance());
                            dataModel.setSaveAmntPerDay(dataModel.getSaveShortFall() / 22);
                            dataModel.setSaveTargetPerc(cardKpiAchManager.getSaveTargetPerc());

                            //flow
                            if (cardKpiAchManager.getSaveAch() > 0)
                                dataModel.setFlowAch(dataModel.getFlowAch() + 1);

                            dataModel.setFlowShortFall(dataModel.getFlowTarget() - dataModel.getFlowAch());
                            if (dataModel.getFlowTarget() > 0)
                                dataModel.setFlowPerformance((dataModel.getFlowAch() / dataModel.getFlowTarget()) * 100);
                            dataModel.setFlowWeight(cardKpiAchManager.getFlowWeight());
                            dataModel.setFlowWeightedPerformance(dataModel.getFlowWeight() * dataModel.getFlowPerformance());
                            dataModel.setFlowAmntPerDay(dataModel.getFlowShortFall() / 22);
                            dataModel.setFlowTargetPerc(cardKpiAchManager.getFlowTargetPerc());

                            //regular
                            if (cardKpiAchManager.getRegAch() > 0)
                                dataModel.setRegAch(dataModel.getRegAch() + 1);

                            dataModel.setRegShortFall(dataModel.getRegTarget() - dataModel.getRegAch());
                            if (dataModel.getRegTarget() > 0)
                                dataModel.setRegPerformance((dataModel.getRegAch() / dataModel.getRegTarget()) * 100);
                            dataModel.setRegWeight(cardKpiAchManager.getRegWeight());
                            dataModel.setRegWeightedPerformance(dataModel.getRegWeight() * dataModel.getRegPerformance());
                            dataModel.setRegAmntPerDay(dataModel.getRegShortFall() / 22);
                            dataModel.setRegTargetPerc(cardKpiAchManager.getRegTargetPerc());

                            //par
                            if (cardKpiAchManager.getParAch() > 0)
                                dataModel.setParAch(dataModel.getParAch() + 1);

                            dataModel.setParShortFall(dataModel.getParTarget() - dataModel.getParAch());
                            if (dataModel.getParTarget() > 0)
                                dataModel.setParPerformance((dataModel.getParAch() / dataModel.getParTarget()) * 100);
                            dataModel.setParWeight(cardKpiAchManager.getParWeight());
                            dataModel.setParWeightedPerformance(dataModel.getParWeight() * dataModel.getParPerformance());
                            dataModel.setParAmntPerDay(dataModel.getParShortFall() / 22);
                            dataModel.setParTargetPerc(cardKpiAchManager.getParTargetPerc());

                            //npl
                            if (cardKpiAchManager.getNplAch() > 0)
                                dataModel.setNplAch(dataModel.getNplAch() + 1);

                            dataModel.setNplShortFall(dataModel.getNplTarget() - dataModel.getNplAch());
                            if (dataModel.getNplTarget() > 0)
                                dataModel.setNplPerformance((dataModel.getNplAch() / dataModel.getNplTarget()) * 100);
                            dataModel.setNplWeight(cardKpiAchManager.getNplWeight());
                            dataModel.setNplWeightedPerformance(dataModel.getNplWeight() * dataModel.getNplPerformance());
                            dataModel.setNplAmntPerDay(dataModel.getNplShortFall() / 22);
                            dataModel.setNplTargetPerc(cardKpiAchManager.getNplTargetPerc());

                            //raw
//                            if(cardKpiAchManager.getRawAch() > 0)
//                                dataModel.setRawAch(dataModel.getRawAch() + 1);
//                            dataModel.setRawTarget((dealerTargetCardManager.get()/100)*noOfAcPerAgeCode);
//                            dataModel.setRawShortFall(dataModel.getRawTarget() - dataModel.getRawAch());
//                            if(dataModel.getRawTarget() > 0)
//                                dataModel.setRawPerformance((dataModel.getRawAch()/dataModel.getRawTarget()) * 100);
//                            dataModel.setRawWeight(cardKpiAchManager.getRawWeight());
//                            dataModel.setRawWeightedPerformance(dataModel.getRawWeight() * dataModel.getRawPerformance());
//                            dataModel.setRawAmntPerDay(dataModel.getRawShortFall());
//                            dataModel.setRawTargetPerc(cardKpiAchManager.getRawTargetPerc());


                            double min_due_ach = cardKpiAchManager.getMinAch();
                            if (min_due_ach > cardKpiAchManager.getMoMinDueAmnt()) {
                                min_due_ach = cardKpiAchManager.getMoMinDueAmnt();
                            }

//                            min due
//                            if(min_due_ach > 0)
//                                dataModel.setMinAch(dataModel.getMinAch() + min_due_ach);
//                            dataModel.setMinTarget((dealerTargetCardManager.get()/100)*noOfAcPerAgeCode);
//                            dataModel.setMinShortFall(dataModel.getMinTarget() - dataModel.getMinAch());
//                            if(dataModel.getMinTarget()>0)
//                                dataModel.setMinPerformance((dataModel.getMinAch()/dataModel.getMinTarget()) * 100);
//                            dataModel.setMinWeight(cardKpiAchManager.getMinWeight());
//                            dataModel.setMinWeightedPerformance(dataModel.getMinWeight() * dataModel.getMinPerformance());
//                            dataModel.setMinAmntPerDay(dataModel.getMinAmntPerDay() + cardKpiAchManager.getMinAmntPerDay());
//                            dataModel.setMinTargetPerc(cardKpiAchManager.getMinTargetPerc());

                            //cardKpiAchList.add(kpiAchEntity);
                        }
                    }
                }
                dataModelList.add(dataModel);
                minDueAmntPerAgeCode = 0;
                amntOfAccPerAgeCode = 0;
                amntOfSaveAc = 0;
                amntOfFlowAc = 0;
                amntOfBackAc = 0;
                amntOfRegularAc = 0;
            }
            amountPerPT = 0;
            noOfAcPerPT = 0;
        }
        return dataModelList;
    }


}
