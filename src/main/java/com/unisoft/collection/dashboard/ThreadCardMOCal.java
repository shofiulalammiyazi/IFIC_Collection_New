package com.unisoft.collection.dashboard;
/*
  Created by Md.   Islam on 1/20/2020
*/

import com.unisoft.collection.KPI.Card.TargetByAmount.CardKPITargetByAmountEntity;
import com.unisoft.collection.KPI.Card.TargetByAmount.CardKPITargetByAmountService;
import com.unisoft.collection.KPI.Card.TargetByManagerAmount.DealerTargetAmountCardManager;
import com.unisoft.collection.KPI.Card.TargetByManagerAmount.DealerTargetCardManagerDao;
import com.unisoft.collection.settings.PARAccountRuleCard.PARAccountRuleCardEntity;
import com.unisoft.collection.settings.PARAccountRuleCard.PARAccountRuleCardService;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.nplAccountRuleCard.NPLAccountRuleCardEntity;
import com.unisoft.collection.settings.nplAccountRuleCard.NPLAccountRuleCardService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeService;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ThreadCardMOCal {

    @Autowired
    private CardBackendAccDetailService cardBackendAccDetailService;
    //private LoanDistributionListTransporter loanDistributionListTransporter=new LoanDistributionListTransporter();
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private CardKPITargetByAmountService cardKPITargetByAmountService;
    @Autowired
    private NPLAccountRuleCardService NPLAccountRuleCardService;
    @Autowired
    private PARAccountRuleCardService parAccountRuleCardService;
    @Autowired
    private AgeCodeService ageCodeService;
    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private DealerTargetCardManagerDao dealerTargetCardManagerDao;


    Date getStartDate() {
        Calendar c1 = Calendar.getInstance();

        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        Date startDate = c1.getTime();

        return startDate;
    }

    Date getEndDate() {
        Calendar cal = Calendar.getInstance();
        int totalDays = cal.getActualMaximum(Calendar.DATE);

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.DAY_OF_MONTH, totalDays);
        c2.set(Calendar.HOUR_OF_DAY, 23);
        c2.set(Calendar.MINUTE, 59);
        c2.set(Calendar.SECOND, 59);
        c2.set(Calendar.MILLISECOND, 0);
        Date endDate = c2.getTime();

        return endDate;
    }

    //Thread used here
    public void calThread(CardAccountDistributionInfo cardAccountDistributionInfo, EmployeeInfoEntity employeeInfoEntity) {
        Thread cardThread = new Thread(new Runnable() {
            @Override
            public void run() {

                CardBackendAccDetailsEntity cardBackendAccDetail = new CardBackendAccDetailsEntity();
                if (cardAccountDistributionInfo != null && employeeInfoEntity != null) {
                    List<ProductTypeEntity> productTypeList = productTypeService.getAllActive();

                    List<CardAccountDistributionInfo> cardAccountDist = new ArrayList<>();
                    cardAccountDist.add(cardAccountDistributionInfo);

                    List<AgeCodeEntity> ageCodeList = ageCodeService.getActiveList();
                    AgeCodeEntity ageCode = new AgeCodeEntity();

                    NPLAccountRuleCardEntity npLlogic = NPLAccountRuleCardService.getNPL();
                    PARAccountRuleCardEntity parLogic = parAccountRuleCardService.getPar();

                    double regularAmnt = 0;
                    double backAmnt = 0;
                    double saveAmnt = 0;
                    double nplRelAmount = 0;
                    double parRelAmount = 0;
                    double totalPayment = 0;

                    String finAccNo = "";

                    for (CardAccountDistributionInfo distributionInfo : cardAccountDist) {

                        regularAmnt = 0;
                        backAmnt = 0;
                        saveAmnt = 0;
                        nplRelAmount = 0;
                        parRelAmount = 0;
                        totalPayment = 0;

//                        List<CardPtp> cardPtpList = dashboardService.getCardPtpByCustomer(distributionInfo.getCardAccountBasicInfo().getCustomer().getId());
//
//                        distributionInfo.getCardAccountBasicInfo().getCustomer().setCardPtpList(cardPtpList);

                        String ageCode1 = distributionInfo.getAgeCode();
                        int MO_ageCode = Integer.parseInt(ageCode1.equals("CR") ? "0" : ageCode1);
                        for (ProductTypeEntity productType : productTypeList) {
                            String temp = distributionInfo.getProductGroup();

                            if (temp.contains("."))
                                temp = temp.substring(0, temp.indexOf("."));
                            //System.err.println("CODE :"+temp);
                            distributionInfo.setProductGroup(temp);
                            if (productType.getProductGroupEntity().getCode().equals(temp)) {
                                distributionInfo.setProductType(productType);
                            }
                        }

                        List<CardDetailPaymentModel> cardDetailPaymentList = dashboardService.getCardDetailedPaymentInfo(distributionInfo.getCardAccountBasicInfo().getCardNo(), getStartDate(), MO_ageCode);

                        for (CardDetailPaymentModel detailPaymentModel : cardDetailPaymentList) {
                            totalPayment = totalPayment + detailPaymentModel.getAmount();
                        }
                        distributionInfo.setTotalPayment(totalPayment);

                        CardDetailPaymentModel lastPaymentDetail = null;

                        if (cardDetailPaymentList.size() > 0) {
                            lastPaymentDetail = cardDetailPaymentList.get(0);
                        } else {
                            lastPaymentDetail = dashboardService.getCardDetailedPaymentInfoForUnPaidThisMonth(distributionInfo.getCardAccountBasicInfo().getCardNo(), MO_ageCode);
                        }

                        if (lastPaymentDetail != null) {
                            //lastPayment = lastPaymentDetail.getCurrentMonthBal();
                            finAccNo = lastPaymentDetail.getFinAccNo();

                            if (lastPaymentDetail.getAgeCodeDataList() != null) {
                                for (AgeCodeData ageCodeData : lastPaymentDetail.getAgeCodeDataList()) {
                                    regularAmnt = regularAmnt + ageCodeData.getBalance();

                                    for (AgeCodeEntity ageCodeEntity : npLlogic.getAgeCodeList()) {
                                        if (ageCodeEntity.getName().equals(ageCodeData.getAgeCodeName())) {
                                            nplRelAmount = nplRelAmount + ageCodeData.getBalance();
                                        }
                                    }
                                    for (AgeCodeEntity ageCodeEntity : parLogic.getAgeCodeList()) {
                                        if (ageCodeEntity.getName().equals(ageCodeData.getAgeCodeName())) {
                                            parRelAmount = parRelAmount + ageCodeData.getBalance();
                                        }
                                    }
                                }

//                                if (lastPaymentDetail != null) {
                                    if (lastPaymentDetail.getAgeCodeDataList().size() > 0) {
                                        int i = MO_ageCode - 1;
                                        if (MO_ageCode > 1) {
                                            if(lastPaymentDetail.getAgeCodeDataList().size() > i){
                                                backAmnt = lastPaymentDetail.getAgeCodeDataList().get(i).getBalance();
                                            }
                                            if(MO_ageCode>2) {
                                                int i1 = MO_ageCode - 2;
                                                if(lastPaymentDetail.getAgeCodeDataList().size() > i1){
                                                    backAmnt = backAmnt + lastPaymentDetail.getAgeCodeDataList().get(i1).getBalance();
                                                }
                                            }
                                        }

                                        if (MO_ageCode > 0) {
                                            if(lastPaymentDetail.getAgeCodeDataList().size() > i){
                                                saveAmnt = lastPaymentDetail.getAgeCodeDataList().get(i).getBalance();
                                            }
//                                            if(MO_ageCode == 1) backAmnt  = distributionInfo.getMinDuePayment();
                                        }
                                    }
//                                }

                                try {
                                    distributionInfo.setCurrentMonthPayDueDate(lastPaymentDetail.getCurrentMonthPayDueDate());

                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(lastPaymentDetail.getCurrentMonthPayDueDate()); ////null pointer exception
                                    cal.add(Calendar.DATE, 2);

                                    distributionInfo.setDueDateWithGracePeriod(cal.getTime());
                                } catch (Exception e) {
//                                    System.out.println(e.getMessage());
                                }
                            }
                        }


                        if (distributionInfo.getAgeCode().toUpperCase().equals("CR")) {
                            distributionInfo.setAgeCode("0");
                        }

                        for (AgeCodeEntity ageCodeEntity : ageCodeList) {
                            if (ageCodeEntity.getName().equals(distributionInfo.getAgeCode())) {
                                ageCode = ageCodeEntity;
                            }
                        }

                        CardKPITargetByAmountEntity kpiTarget = null;
                        try {
                            kpiTarget = cardKPITargetByAmountService.getByProductAgeCodeAndDealerPin(distributionInfo.getProductType(), ageCode, distributionInfo.getDealerPin(), employeeInfoEntity.getLocation());

                            if (kpiTarget != null)
                                distributionInfo.setCashCollection((kpiTarget.getRawCollectionTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
                            else
                                distributionInfo.setCashCollection(0);

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        distributionInfo.setSaveAmount(saveAmnt);
                        distributionInfo.setRemSaveAmount(saveAmnt - totalPayment);
                        distributionInfo.setBackAmount(backAmnt);
                        distributionInfo.setRemBackAmount(backAmnt - totalPayment);
                        distributionInfo.setRemRegularkAmount(distributionInfo.getMinDuePayment() - totalPayment);
                        distributionInfo.setRemRawCollAmount(distributionInfo.getCashCollection() - totalPayment);
                        distributionInfo.setNplRelAmnt(nplRelAmount);
                        distributionInfo.setParRelAmnt(parRelAmount);

                        cardBackendAccDetail.setAgeCode(MO_ageCode);
                        cardBackendAccDetail.setAllocationMinDueAmnt(distributionInfo.getMinDuePayment());
                        cardBackendAccDetail.setAllocationOutstanding(Double.parseDouble(distributionInfo.getOutstandingAmount()));
                        cardBackendAccDetail.setBillingCycleDate(distributionInfo.getBillingCycle());
                        cardBackendAccDetail.setCardNo(distributionInfo.getCardAccountBasicInfo().getCardNo());
                        cardBackendAccDetail.setCurrentMnthPayDueDate(distributionInfo.getCurrentMonthPayDueDate());
                        cardBackendAccDetail.setCardCustomerId(distributionInfo.getCardAccountBasicInfo().getCustomer().getCustomerId());
                        cardBackendAccDetail.setFinAccNo(finAccNo);
                        cardBackendAccDetail.setMoBackAmnt(distributionInfo.getBackAmount());
                        cardBackendAccDetail.setMoSaveAmnt(distributionInfo.getSaveAmount());
                        cardBackendAccDetail.setMoRegularAmnt(regularAmnt);
                        cardBackendAccDetail.setMoNPLRelReqAmnt(distributionInfo.getNplRelAmnt());
                        cardBackendAccDetail.setMoPARRelReqAmnt(distributionInfo.getParRelAmnt());
                        cardBackendAccDetail.setMoFlowAmnt(0);
                        cardBackendAccDetail.setMoMinDueAmnt(distributionInfo.getMinDuePayment());

                        cardBackendAccDetail.setCreatedDate(new Date());
                        cardBackendAccDetail.setCreatorPin(employeeInfoEntity.getPin());

                        if (kpiTarget != null) {
                            try {
                                cardBackendAccDetail.setTargetBackAmnt((kpiTarget.getBackTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
                                cardBackendAccDetail.setTargetFlowAmnt((kpiTarget.getFlowTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
                                cardBackendAccDetail.setTargetSaveAmnt((kpiTarget.getSaveTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
                                cardBackendAccDetail.setTargetMinDueAmnt((kpiTarget.getMinDueTarget() / 100) * distributionInfo.getMinDuePayment());

                                cardBackendAccDetail.setTargetBackPerc(kpiTarget.getBackTarget());
                                cardBackendAccDetail.setTargetFlowPerc(kpiTarget.getFlowTarget());
                                cardBackendAccDetail.setTargetSavePerc(kpiTarget.getSaveTarget());
                                cardBackendAccDetail.setTargetParPerc(kpiTarget.getPARRelTarget());
                                cardBackendAccDetail.setTargetNplPerc(kpiTarget.getNPLRelTarget());
                                cardBackendAccDetail.setTargetRegularPerc(kpiTarget.getRegularTarget());
                                cardBackendAccDetail.setTargetRawColPerc(kpiTarget.getRawCollectionTarget());
                                cardBackendAccDetail.setTargetMinDuePerc(kpiTarget.getMinDueTarget());

                                cardBackendAccDetail.setTargetOverDueAmnt((kpiTarget.getMinDueTarget() / 100) * distributionInfo.getMinDuePayment());

                                cardBackendAccDetail.setTargetNplAmnt((kpiTarget.getNPLRelTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
                                cardBackendAccDetail.setTargetParAmnt((kpiTarget.getPARRelTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
                                cardBackendAccDetail.setTargetRegularAmnt((kpiTarget.getRegularTarget() / 100) * distributionInfo.getMinDuePayment());

                                if (totalPayment < saveAmnt && totalPayment < backAmnt && totalPayment < regularAmnt) {
                                    cardBackendAccDetail.setMoFlowAmnt(Double.parseDouble(distributionInfo.getOutstandingAmount()));
                                    //cardBackendAccDetail.setTargetFlowAmnt((kpiTarget.getFlowTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
                                }
                                cardBackendAccDetail.setTargetRawCol(((kpiTarget.getRawCollectionTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount())));

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        DealerTargetAmountCardManager managerKpiAmount = dealerTargetCardManagerDao.getTargetByAgeSupVLoc(ageCode, distributionInfo.getSupervisorPin(), distributionInfo.getCardAccountBasicInfo().getLocation());

                        try {
                            if (managerKpiAmount != null) {
                                cardBackendAccDetail.setTargetManBackAmnt((managerKpiAmount.getBackAmountTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
                                cardBackendAccDetail.setTargetManFlowAmnt((managerKpiAmount.getFlowAmountTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
                                cardBackendAccDetail.setTargetManSaveAmnt((managerKpiAmount.getSaveAmountTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
                                cardBackendAccDetail.setTargetManMinDueAmnt((managerKpiAmount.getMinDueAmountTarget() / 100) * 100);
//                            cardBackendAccDetail.setTargetManOverDueAmnt((managerKpiAmount.get));

                                cardBackendAccDetail.setTargetManNplAmnt((managerKpiAmount.getNplReleaseAmountTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
                                cardBackendAccDetail.setTargetManParAmnt((managerKpiAmount.getParReleaseAmountTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));
                                cardBackendAccDetail.setTargetManRegularAmnt((managerKpiAmount.getRegularAmountTarget() / 100) * distributionInfo.getMinDuePayment());
                                cardBackendAccDetail.setTargetManRawCol((managerKpiAmount.getCashCollectionTarget() / 100) * Double.parseDouble(distributionInfo.getOutstandingAmount()));

                                cardBackendAccDetail.setTargetManSavePerc(managerKpiAmount.getSaveAmountTarget());
                                cardBackendAccDetail.setTargetManFlowPerc(managerKpiAmount.getFlowAmountTarget());
                                cardBackendAccDetail.setTargetManBackPerc(managerKpiAmount.getBackAmountTarget());
                                cardBackendAccDetail.setTargetManParPerc(managerKpiAmount.getParReleaseAmountTarget());
                                cardBackendAccDetail.setTargetManNplPerc(managerKpiAmount.getNplReleaseAmountTarget());
                                cardBackendAccDetail.setTargetRegularPerc(managerKpiAmount.getRegularAmountTarget());
                                cardBackendAccDetail.setTargetManRawColPerc(managerKpiAmount.getCashCollectionTarget());
                                cardBackendAccDetail.setTargetMinDuePerc(managerKpiAmount.getMinDueAmountTarget());

                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }


                        cardBackendAccDetail.setMobileNo(dashboardService.getCardCusMblByCusId(distributionInfo.getCardAccountBasicInfo().getCustomer().getCustomerId()));
                        distributionInfo.getCardAccountBasicInfo().getCustomer().setMobileNumber(cardBackendAccDetail.getMobileNo());

                        CardBackendAccDetailsEntity oldOnj=cardBackendAccDetailService.getByAccNo(cardBackendAccDetail.getCardNo(),getStartDate(),getEndDate());
                        if(oldOnj != null)
                        {
                            cardBackendAccDetail.setId(oldOnj.getId());
                            cardBackendAccDetailService.updateBack(cardBackendAccDetail);
                        }else {
                            cardBackendAccDetailService.saveNew(cardBackendAccDetail);
                        }
                    }
                } else {

                }
            }
        });
        cardThread.start();
    }
}
