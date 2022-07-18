package com.csinfotechbd.collection;

import com.csinfotechbd.collection.KPI.Card.TargetByAmount.CardKPITargetByAmountEntity;
import com.csinfotechbd.collection.KPI.Card.TargetByAmount.CardKPITargetByAmountService;
import com.csinfotechbd.collection.KPI.Card.TargetByManagerAmount.DealerTargetAmountCardManager;
import com.csinfotechbd.collection.KPI.Card.TargetByManagerAmount.DealerTargetCardManagerDao;
import com.csinfotechbd.collection.dashboard.*;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicRepository;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.csinfotechbd.collection.settings.nplAccountRuleCard.NPLAccountRuleCardEntity;
import com.csinfotechbd.collection.settings.nplAccountRuleCard.NPLAccountRuleCardService;
import com.csinfotechbd.collection.settings.PARAccountRuleCard.PARAccountRuleCardEntity;
import com.csinfotechbd.collection.settings.PARAccountRuleCard.PARAccountRuleCardService;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeRepository;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeService;
import com.csinfotechbd.collection.settingshelper.SettingsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/collection/modata/")
public class MoCheckController {

    @Autowired
    private CardAccountBasicRepository cardAccountBasicRepository;

    @Autowired
    private CardAccountDistributionRepository cardAccountDistributionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private AgeCodeService ageCodeService;

    @Autowired
    private NPLAccountRuleCardService NPLAccountRuleCardService;

    @Autowired
    private PARAccountRuleCardService parAccountRuleCardService;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private CardKPITargetByAmountService cardKPITargetByAmountService;

    @Autowired
    private DealerTargetCardManagerDao dealerTargetCardManagerDao;

    @Autowired
    private CardBackendAccDetailService cardBackendAccDetailService;


    @GetMapping(value = "check")
    public Map getCheck(@RequestParam(name = "account") String accountNo) {
        Map map = new HashMap();
        map.put("data", "");

        CardAccountBasicInfo cardAccountBasicInfo = cardAccountBasicRepository.findByCardNo(accountNo);
        CardAccountDistributionInfo cardAccountDistributionInfo =
                cardAccountDistributionRepository.findFirstByCreatedDateIsBetweenAndCardAccountBasicInfoAndLatestOrderByCreatedDateDesc(
                        SettingsHelper.getStartDate(), SettingsHelper.getEndDate(), cardAccountBasicInfo, "1");

        EmployeeInfoEntity employeeInfoEntity = employeeRepository.findByPin(cardAccountDistributionInfo.getDealerPin());

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

                String ageCode1 = distributionInfo.getAgeCode();
                int MO_ageCode = Integer.parseInt(ageCode1.equals("CR") ? "0" : ageCode1);
                for (ProductTypeEntity productType : productTypeList) {
                    String temp = distributionInfo.getProductGroup();

                    if (temp.contains("."))
                        temp = temp.substring(0, temp.indexOf("."));
                    distributionInfo.setProductGroup(temp);
                    if (productType.getProductGroupEntity().getCode().equals(temp)) {
                        distributionInfo.setProductType(productType);
                    }
                }

                List<CardDetailPaymentModel> cardDetailPaymentList = dashboardService.getCardDetailedPaymentInfo(distributionInfo.getCardAccountBasicInfo().getCardNo(), SettingsHelper.getStartDate(), MO_ageCode);

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

                        //Back amount is the sum  of current agecode amount and previeous agecode amount
                        if (lastPaymentDetail.getAgeCodeDataList().size() > 0) {
                            int i = MO_ageCode - 1;
                            if (MO_ageCode > 1) {
                                if (lastPaymentDetail.getAgeCodeDataList().size() > i) {
                                    backAmnt = lastPaymentDetail.getAgeCodeDataList().get(i).getBalance();
                                }
                                if (MO_ageCode > 2) {
                                    int i1 = MO_ageCode - 2;
                                    if (lastPaymentDetail.getAgeCodeDataList().size() > i1) {
                                        backAmnt = backAmnt + lastPaymentDetail.getAgeCodeDataList().get(i1).getBalance();
                                    }
                                }
                            }

                            if (MO_ageCode > 0) {
                                if (lastPaymentDetail.getAgeCodeDataList().size() > i) {
                                    saveAmnt = lastPaymentDetail.getAgeCodeDataList().get(i).getBalance();
                                }
                            }
                        }

                        try {
                            distributionInfo.setCurrentMonthPayDueDate(lastPaymentDetail.getCurrentMonthPayDueDate());

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(lastPaymentDetail.getCurrentMonthPayDueDate());
                            cal.add(Calendar.DATE, 2);

                            distributionInfo.setDueDateWithGracePeriod(cal.getTime());
                        } catch (Exception e) {
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
                distributionInfo.setBackAmount(backAmnt);
                distributionInfo.setNplRelAmnt(nplRelAmount);
                distributionInfo.setParRelAmnt(parRelAmount);

                distributionInfo.setRemSaveAmount(saveAmnt - totalPayment);
                distributionInfo.setRemBackAmount(backAmnt - totalPayment);
                distributionInfo.setRemRegularkAmount(distributionInfo.getMinDuePayment() - totalPayment);
                distributionInfo.setRemRawCollAmount(distributionInfo.getCashCollection() - totalPayment);

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
                CardBackendAccDetailsEntity oldOnj = cardBackendAccDetailService.getByAccNo(cardBackendAccDetail.getCardNo(), SettingsHelper.getStartDate(), SettingsHelper.getEndDate());
                if (oldOnj != null) {
                    cardBackendAccDetail.setId(oldOnj.getId());
                    cardBackendAccDetailService.updateBack(cardBackendAccDetail);
                } else {
                    cardBackendAccDetailService.saveNew(cardBackendAccDetail);
                }
                map.put("data", cardBackendAccDetail);
                return map;
            }
        }
        return map;
    }
}
