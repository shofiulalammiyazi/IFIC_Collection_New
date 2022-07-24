package com.unisoft.collection.dashboard;

import com.unisoft.collection.KPI.Card.TargetByAmount.CardKPITargetByAmountService;
import com.unisoft.collection.KPI.Card.TargetByManagerAmount.DealerTargetCardManagerDao;
import com.unisoft.collection.KPI.Card.TargetWeightByAmount.CardKPITargetWeightByAmountEntity;
import com.unisoft.collection.KPI.Card.TargetWeightByAmount.CardKPITargetWeightByAmountService;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.collection.settings.nplAccountRuleCard.NPLAccountRuleCardService;
import com.unisoft.collection.settings.PARAccountRuleCard.PARAccountRuleCardService;
import com.unisoft.collection.settings.ageCode.AgeCodeEntity;
import com.unisoft.collection.settings.ageCode.AgeCodeService;
import com.unisoft.collection.settings.productType.ProductTypeEntity;
import com.unisoft.collection.settings.productType.ProductTypeRepository;
import com.unisoft.collection.settings.productType.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class ThreadKpiCalCard {

    @Autowired
    private CardBackendAccDetailService cardBackendAccDetailService;

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

    @Autowired
    private CardKPITargetWeightByAmountService cardKPITargetWeightByAmountService;

    @Autowired
    private CardKpiAchDao cardKpiAchDao;

    @Autowired
    private PaymentAndReversalCodeCardDao paymentAndReversalCodeCardDao;
    @Autowired
    private CardKpiAchRepository cardKpiAchRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;


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

    public static int interval=10;

    //Thread used here
    public void calculateKpiAch() {
        List<CardAccountDistributionInfo> masterList = dashboardService.getAllCardsForKipCal();

        List<AgeCodeEntity> ageCodeList = ageCodeService.getActiveList();
//        List<ProductTypeEntity> productTypeList = productTypeService.getAllActive();
        int count = 0;

        while (masterList.size() > 0) {
            List<CardAccountDistributionInfo> calculationList = new ArrayList<>();
            count = 0;
            for (CardAccountDistributionInfo distributionInfo : masterList) {
                ProductTypeEntity productTypeEntity = productTypeRepository.findByCode(distributionInfo.getProductGroup());
                distributionInfo.setProductType(productTypeEntity);
//                for (ProductTypeEntity productType : productTypeList) {
//                    String temp = distributionInfo.getProductGroup();
//
//                    if (temp.contains("."))
//                        temp = temp.substring(0, temp.indexOf("."));
//                    //System.err.println("CODE :"+temp);
//                    distributionInfo.setProductGroup(temp);
//                    if (productType.getProductGroupEntity().getCode().equals(temp)) {
//                        distributionInfo.setProductType(productType);
//                    }
//                }
                calculationList.add(distributionInfo);
                count++;
                if (count == 100)
                    break;
                //System.err.println("COUNT : "+count+"  "+masterList.size());
            }
            //seperating calculative list
            masterList.removeAll(calculationList);

            //starting calculation from calculative list
            for (CardAccountDistributionInfo dist : calculationList) {
                Thread kpiThread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        double regTarget = 0;
                        double flowTarget = 0;
                        double backTarget = 0;
                        double saveTarget = 0;
                        double nplTarget = 0;
                        double parTarget = 0;
                        double rawTarget = 0;
                        double totalPayment = 0;

                        CardBackendAccDetailsEntity cardBackendAccDetail = cardBackendAccDetailService.getByAccNo(dist.getCardAccountBasicInfo().getCardNo(), getStartDate(), getEndDate());

                        if (cardBackendAccDetail != null) {
                            flowTarget = cardBackendAccDetail.getTargetFlowAmnt();
                            saveTarget = cardBackendAccDetail.getTargetSaveAmnt();
                            backTarget = cardBackendAccDetail.getTargetBackAmnt();
                            nplTarget = cardBackendAccDetail.getTargetNplAmnt();
                            parTarget = cardBackendAccDetail.getTargetParAmnt();
                            regTarget = cardBackendAccDetail.getTargetRegularAmnt();
                            rawTarget = cardBackendAccDetail.getTargetRawCol();

                            AgeCodeEntity ageCode = new AgeCodeEntity();

                            CardKpiAchEntity kpiAch = new CardKpiAchEntity();
                            CardKpiAchManagerEntity cardKpiAchManager=new CardKpiAchManagerEntity();

                            int MO_ageCode = 0;
                            if (dist.getAgeCode().toUpperCase().equals("CR"))
                                MO_ageCode = 0;
                            else
                                MO_ageCode = Integer.parseInt(dist.getAgeCode());

                            List<CardDetailPaymentModel> cardDetailPaymentList = dashboardService.getCardDetailedPaymentInfo(dist.getCardAccountBasicInfo().getCardNo(), getStartDate(), MO_ageCode);

                            if(cardDetailPaymentList.size() > 0)
                            {
                                for (CardDetailPaymentModel detailPaymentModel : cardDetailPaymentList) {
                                    totalPayment = totalPayment + detailPaymentModel.getAmount();
                                }
                            }

                            List<PaymentAndReversalCodeCard> reversalCodeCardList= paymentAndReversalCodeCardDao.getRevList();

                            List<RevCodePayment> revPaymentList=paymentAndReversalCodeCardDao.getRevPayment(cardBackendAccDetail.getFinAccNo(),getStartDate(),getEndDate());

                            double totalRevPayment=0;

                            for(RevCodePayment revPayment : revPaymentList)
                            {
                                for(PaymentAndReversalCodeCard revCode : reversalCodeCardList)
                                {
                                    if(revPayment.getCode().toUpperCase().equals(revCode.getCode().toUpperCase()))
                                    {
                                        totalRevPayment=totalRevPayment+revPayment.getAmount();
                                        cardBackendAccDetail.setRevCode(revPayment.getCode()+" ");
                                        cardBackendAccDetail.setRevAmnt(totalRevPayment);

                                        kpiAch.setRevCode(revPayment.getCode()+" ");
                                        kpiAch.setRevAmnt(totalRevPayment);

                                        cardKpiAchManager.setRevCode(revPayment.getCode()+" ");
                                        cardKpiAchManager.setRevAmnt(totalRevPayment);
                                    }
                                }
                            }

                            if(totalRevPayment > 0)
                            {
                                cardBackendAccDetail.setDisputeIndicator(true);
                                cardBackendAccDetail.setNewOutstanding(cardBackendAccDetail.getAllocationOutstanding()+totalRevPayment);
                                cardBackendAccDetailService.updateBack(cardBackendAccDetail);

                                kpiAch.setDisputeIndicator(true);
                                kpiAch.setNewOutstanding(cardBackendAccDetail.getAllocationOutstanding()+totalRevPayment);

                                cardKpiAchManager.setDisputeIndicator(true);
                                cardKpiAchManager.setNewOutstanding(cardBackendAccDetail.getAllocationOutstanding()+totalRevPayment);
                            }else{
                                cardKpiAchManager.setNewOutstanding(cardBackendAccDetail.getAllocationOutstanding());
                                cardKpiAchManager.setDisputeIndicator(false);

                                kpiAch.setDisputeIndicator(false);
                                kpiAch.setNewOutstanding(cardBackendAccDetail.getAllocationOutstanding());
                            }

                            totalPayment=totalPayment-totalRevPayment;
                            kpiAch.setTotalPayment(totalPayment);
                            cardKpiAchManager.setTotalPayment(totalPayment);

                            dist.setTotalPayment(totalPayment);

//                            CardDetailPaymentModel lastPaymentDetail = null;
//
//                            if (cardDetailPaymentList.size() > 0) {
//                                lastPaymentDetail = cardDetailPaymentList.get(0);
//                            } else {
//                                lastPaymentDetail = dashboardService.getCardDetailedPaymentInfoForUnPaidThisMonth(dist.getCardAccountBasicInfo().getCardNo(), MO_ageCode);
//                            }

                            for (AgeCodeEntity ageCodeEntity : ageCodeList) {
                                if (ageCodeEntity.getName().equals(dist.getAgeCode())) {
                                    ageCode = ageCodeEntity;
                                }
                            }

                            CardKPITargetWeightByAmountEntity kpiTargetWeight = cardKPITargetWeightByAmountService.getByProductAgeCodeAndDealerPin(dist.getProductType(),ageCode,dist.getDealerPin());


                            kpiAch.setUpdateDate(new Date());
                            kpiAch.setFinAccNo(cardBackendAccDetail.getFinAccNo());
                            kpiAch.setCardAccNo(cardBackendAccDetail.getCardNo());
                            kpiAch.setDealerPin(dist.getDealerPin());
                            kpiAch.setCusId(dist.getCardAccountBasicInfo().getCustomer().getCustomerId());
                            String cardCategory = "";
                            String productCode = "";
                            if(dist.getProductType() != null) {
                                if(dist.getProductType().getCardsCategory() != null){
                                    cardCategory = dist.getProductType().getCardsCategory();
                                }
                                if(dist.getProductType().getCode() != null) productCode = dist.getProductType().getCode();
                            }
                            kpiAch.setCardsCategory(cardCategory+"-"+productCode);
                            kpiAch.setAgeCode(MO_ageCode);

                            //manager
                            cardKpiAchManager.setUpdateDate(new Date());
                            cardKpiAchManager.setFinAccNo(cardBackendAccDetail.getFinAccNo());
                            cardKpiAchManager.setCardAccNo(cardBackendAccDetail.getCardNo());
                            cardKpiAchManager.setDealerPin(dist.getDealerPin());
                            cardKpiAchManager.setCusId(dist.getCardAccountBasicInfo().getCustomer().getCustomerId());
                            cardKpiAchManager.setCardsCategory(cardCategory+"-"+productCode);
                            cardKpiAchManager.setAgeCode(MO_ageCode);

                            //All Target
                            kpiAch.setFlowTarget(flowTarget);
                            kpiAch.setSaveTarget(saveTarget);
                            kpiAch.setBackTarget(backTarget);
                            kpiAch.setParTarget(parTarget);
                            kpiAch.setNplTarget(nplTarget);
                            kpiAch.setRawTarget(rawTarget);
                            kpiAch.setRegTarget(regTarget);
                            kpiAch.setMinTarget(cardBackendAccDetail.getTargetMinDueAmnt());

                            //target Percentage
                            kpiAch.setSaveTargetPerc(cardBackendAccDetail.getTargetSavePerc());
                            kpiAch.setBackTargetPerc(cardBackendAccDetail.getTargetBackPerc());
                            kpiAch.setFlowTargetPerc(cardBackendAccDetail.getTargetFlowPerc());
                            kpiAch.setParTargetPerc(cardBackendAccDetail.getTargetParPerc());
                            kpiAch.setNplTargetPerc(cardBackendAccDetail.getTargetNplPerc());
                            kpiAch.setRegTargetPerc(cardBackendAccDetail.getTargetRegularPerc());
                            kpiAch.setRawTargetPerc(cardBackendAccDetail.getTargetRawColPerc());
                            kpiAch.setMinTargetPerc(cardBackendAccDetail.getTargetMinDuePerc());

                            //manager all Target
                            cardKpiAchManager.setFlowTarget(cardBackendAccDetail.getTargetManFlowAmnt());
                            cardKpiAchManager.setSaveTarget(cardBackendAccDetail.getTargetManSaveAmnt());
                            cardKpiAchManager.setBackTarget(cardBackendAccDetail.getTargetManBackAmnt());
                            cardKpiAchManager.setParTarget(cardBackendAccDetail.getTargetManParAmnt());
                            cardKpiAchManager.setNplTarget(cardBackendAccDetail.getTargetManNplAmnt());
                            cardKpiAchManager.setRawTarget(cardBackendAccDetail.getTargetManRawCol());
                            cardKpiAchManager.setRegTarget(cardBackendAccDetail.getTargetManRegularAmnt());
                            cardKpiAchManager.setMinTarget(cardBackendAccDetail.getTargetManMinDueAmnt());

                            //target Percentage
                            cardKpiAchManager.setFlowTargetPerc(cardBackendAccDetail.getTargetManFlowPerc());
                            cardKpiAchManager.setSaveTargetPerc(cardBackendAccDetail.getTargetManSavePerc());
                            cardKpiAchManager.setBackTargetPerc(cardBackendAccDetail.getTargetManBackPerc());
                            cardKpiAchManager.setParTargetPerc(cardBackendAccDetail.getTargetManParPerc());
                            cardKpiAchManager.setNplTargetPerc(cardBackendAccDetail.getTargetManNplPerc());
                            cardKpiAchManager.setRawTargetPerc(cardBackendAccDetail.getTargetManRawColPerc());
                            cardKpiAchManager.setRegTargetPerc(cardBackendAccDetail.getTargetManRegularPerc());
                            cardKpiAchManager.setMinTargetPerc(cardBackendAccDetail.getTargetManMinDuePerc());

                            //regular
                            if (cardBackendAccDetail.getAgeCode() == 0) {
                                kpiAch.setRegAch(totalPayment);
                                kpiAch.setRegShortFall(kpiAch.getRegTarget()-totalPayment);

                                cardKpiAchManager.setRegAch(totalPayment);
                                cardKpiAchManager.setRegShortFall(cardKpiAchManager.getRegTarget()-totalPayment);
                                if(totalPayment>0)
                                {
                                    if(kpiAch.getRegTarget() > 0)
                                        kpiAch.setRegPerformance((kpiAch.getRegAch()/kpiAch.getRegTarget()) * 100);
                                    if(cardKpiAchManager.getRegTarget() > 0)
                                        cardKpiAchManager.setRegPerformance((cardKpiAchManager.getRegAch()/cardKpiAchManager.getRegTarget()) * 100);
                                    if(kpiTargetWeight != null)
                                    {
                                        kpiAch.setRegWeight(kpiTargetWeight.getRegularTarget());
                                        kpiAch.setRegWeightedPerformance(kpiAch.getRegPerformance() * kpiAch.getRegWeight());

                                        cardKpiAchManager.setRegWeight(kpiTargetWeight.getRegularTarget());
                                        cardKpiAchManager.setRegWeightedPerformance(cardKpiAchManager.getRegPerformance() * cardKpiAchManager.getRegWeight());
                                    }
                                }

                            }//back
                            else if (cardBackendAccDetail.getAgeCode() < MO_ageCode) {
                                kpiAch.setBackAch(totalPayment);
                                kpiAch.setBackShortFall(kpiAch.getBackTarget()-totalPayment);

                                cardKpiAchManager.setBackAch(totalPayment);
                                cardKpiAchManager.setBackShortFall(cardKpiAchManager.getBackTarget()-totalPayment);
                                if(totalPayment>0)
                                {
                                    if(kpiAch.getBackTarget() > 0)
                                        kpiAch.setBackPerformance((kpiAch.getBackAch()/kpiAch.getBackTarget()) * 100);
                                    if(cardKpiAchManager.getBackTarget() > 0)
                                        cardKpiAchManager.setBackPerformance((cardKpiAchManager.getBackAch()/cardKpiAchManager.getBackTarget()) * 100);
                                    if(kpiTargetWeight != null)
                                    {
                                        kpiAch.setBackWeight(kpiTargetWeight.getBackTarget());
                                        kpiAch.setBackWeightedPerformance(kpiAch.getBackWeight() * kpiAch.getBackPerformance());

                                        cardKpiAchManager.setBackWeight(kpiTargetWeight.getBackTarget());
                                        cardKpiAchManager.setBackWeightedPerformance(cardKpiAchManager.getBackWeight() * cardKpiAchManager.getBackPerformance());
                                    }
                                }
                            }//save
                            else if (cardBackendAccDetail.getAgeCode() == MO_ageCode) {
                                //System.err.println(" AccNo SAVE  "+cardBackendAccDetail.getCardNo());
                                kpiAch.setSaveAch(totalPayment);
                                kpiAch.setSaveShortFall(kpiAch.getSaveTarget()-totalPayment);

                                cardKpiAchManager.setSaveAch(totalPayment);
                                cardKpiAchManager.setSaveShortFall(cardKpiAchManager.getSaveTarget() - totalPayment);
                                if(totalPayment>0)
                                {
                                    if(kpiAch.getSaveTarget() > 0)
                                        kpiAch.setSavePerformance((kpiAch.getSaveAch()/kpiAch.getSaveTarget()) * 100);
                                    if(cardKpiAchManager.getSaveTarget() > 0)
                                        cardKpiAchManager.setSavePerformance((cardKpiAchManager.getSaveAch()/cardKpiAchManager.getSaveTarget()) * 100);
                                    if(kpiTargetWeight != null)
                                    {
                                        kpiAch.setSaveWeight(kpiTargetWeight.getSaveTarget());
                                        kpiAch.setSaveWeightedPerformance(kpiAch.getSaveWeight() * kpiAch.getSavePerformance());

                                        cardKpiAchManager.setSaveWeight(kpiTargetWeight.getSaveTarget());
                                        cardKpiAchManager.setSaveWeightedPerformance(cardKpiAchManager.getSaveTarget() * cardKpiAchManager.getSavePerformance());
                                    }
                                }
                            }//flow
                            else if (cardBackendAccDetail.getAgeCode() > MO_ageCode) {
                                System.err.println(" AccNo FLOW  "+cardBackendAccDetail.getCardNo());
                                kpiAch.setFlowAch(totalPayment);
                                kpiAch.setFlowShortFall(kpiAch.getFlowTarget()-totalPayment);

                                cardKpiAchManager.setFlowAch(totalPayment);
                                cardKpiAchManager.setFlowShortFall(cardKpiAchManager.getFlowTarget()-totalPayment);
                                if(totalPayment>0)
                                {
                                    if(kpiAch.getFlowTarget() > 0)
                                        kpiAch.setFlowPerformance((kpiAch.getFlowAch()/kpiAch.getFlowTarget()) * 100);
                                    if(cardKpiAchManager.getFlowTarget() > 0)
                                        cardKpiAchManager.setFlowPerformance((cardKpiAchManager.getFlowAch()/cardKpiAchManager.getFlowTarget()) * 100);
                                    if(kpiTargetWeight != null)
                                    {
                                        kpiAch.setFlowWeight(kpiTargetWeight.getFlowTarget());
                                        kpiAch.setFlowWeightedPerformance(kpiAch.getFlowWeight() * kpiAch.getFlowPerformance());

                                        cardKpiAchManager.setFlowWeight(kpiTargetWeight.getFlowTarget());
                                        cardKpiAchManager.setFlowWeightedPerformance(cardKpiAchManager.getFlowWeight() * cardKpiAchManager.getFlowPerformance());
                                    }
                                }
                            }

                            //par
                            if(totalPayment >= cardBackendAccDetail.getTargetParAmnt())
                            {
                                kpiAch.setParAch(totalPayment);
                                kpiAch.setParShortFall(kpiAch.getParTarget() - totalPayment);

                                cardKpiAchManager.setParAch(totalPayment);
                                cardKpiAchManager.setParShortFall(cardKpiAchManager.getParTarget() - totalPayment);
                                if(totalPayment>0)
                                {
                                    if(kpiAch.getParTarget() > 0)
                                        kpiAch.setParPerformance((kpiAch.getParAch()/kpiAch.getParTarget()) * 100);
                                    if(cardKpiAchManager.getParTarget() > 0)
                                        cardKpiAchManager.setParPerformance((cardKpiAchManager.getParAch()/cardKpiAchManager.getParTarget()) * 100);
                                    if(kpiTargetWeight != null)
                                    {
                                        kpiAch.setParWeight(kpiTargetWeight.getPARRelTarget());
                                        kpiAch.setParWeightedPerformance(kpiAch.getParWeight() * kpiAch.getParPerformance());

                                        cardKpiAchManager.setParWeight(kpiTargetWeight.getPARRelTarget());
                                        cardKpiAchManager.setParWeightedPerformance(cardKpiAchManager.getParWeight() * cardKpiAchManager.getParPerformance());
                                    }
                                }
                            }else {
                                //npl
                                kpiAch.setNplAch(totalPayment);
                                kpiAch.setNplShortFall(kpiAch.getNplTarget() - totalPayment);

                                cardKpiAchManager.setNplAch(totalPayment);
                                cardKpiAchManager.setNplShortFall(cardKpiAchManager.getNplTarget() - totalPayment);
                                if(totalPayment>0)
                                {
                                    if(kpiAch.getNplTarget() > 0)
                                        kpiAch.setNplPerformance((kpiAch.getNplAch()/kpiAch.getNplTarget()) * 100);
                                    if(cardKpiAchManager.getNplTarget() > 0)
                                        cardKpiAchManager.setNplPerformance((cardKpiAchManager.getNplAch()/cardKpiAchManager.getNplTarget()) * 100);
                                    if(kpiTargetWeight != null)
                                    {
                                        kpiAch.setNplWeight(kpiTargetWeight.getNPLRelTarget());
                                        kpiAch.setNplWeightedPerformance((kpiAch.getNplWeight()) * kpiAch.getNplPerformance());

                                        cardKpiAchManager.setNplWeight(kpiTargetWeight.getNPLRelTarget());
                                        cardKpiAchManager.setNplWeightedPerformance(cardKpiAchManager.getNplWeight() * cardKpiAchManager.getNplPerformance());
                                    }
                                }
                            }

                            //raw col.
                            kpiAch.setRawAch(totalPayment);
                            kpiAch.setRawShortFall(kpiAch.getRawTarget() - totalPayment);

                            cardKpiAchManager.setRawAch(totalPayment);
                            cardKpiAchManager.setRawShortFall(cardKpiAchManager.getRawTarget()-totalPayment);
                            if(totalPayment>0)
                            {
                                if(kpiAch.getRawTarget() > 0)
                                    kpiAch.setRawPerformance((kpiAch.getRawAch()/kpiAch.getRawTarget()) * 100);
                                if(cardKpiAchManager.getRawTarget() > 0)
                                    cardKpiAchManager.setRawPerformance((cardKpiAchManager.getRawAch()/cardKpiAchManager.getRawTarget()) * 100);
                                if(kpiTargetWeight != null)
                                {
                                    kpiAch.setRawWeight(kpiTargetWeight.getRawCollectionTarget());
                                    kpiAch.setRawWeightedPerformance(kpiAch.getRawWeight() * kpiAch.getRawPerformance());

                                    cardKpiAchManager.setRawWeight(kpiTargetWeight.getRawCollectionTarget());
                                    cardKpiAchManager.setRawWeightedPerformance(cardKpiAchManager.getRawWeight() * cardKpiAchManager.getRawPerformance());
                                }
                            }

                            //MIN DUE
                            kpiAch.setMinAch(totalPayment);
                            kpiAch.setMinShortFall(kpiAch.getMinTarget() - totalPayment);
                            kpiAch.setMoMinDueAmnt(cardBackendAccDetail.getMoMinDueAmnt());

                            cardKpiAchManager.setMinAch(totalPayment);
                            cardKpiAchManager.setMinShortFall(cardKpiAchManager.getMinTarget() - totalPayment);
                            cardKpiAchManager.setMoMinDueAmnt(cardBackendAccDetail.getMoMinDueAmnt());
                            if(totalPayment>0)
                            {
                                if(kpiAch.getMinTarget() > 0)
                                    kpiAch.setMinPerformance((kpiAch.getMinAch()/kpiAch.getMinTarget()));
                                if(cardKpiAchManager.getMinTarget() > 0)
                                    cardKpiAchManager.setMinShortFall((cardKpiAchManager.getMinAch()/cardKpiAchManager.getMinTarget()));
                                if(kpiTargetWeight != null)
                                {
                                    kpiAch.setMinWeight(kpiTargetWeight.getMinDueTarget());
                                    kpiAch.setMinWeightedPerformance(kpiAch.getMinWeight() * kpiAch.getMinPerformance());

                                    cardKpiAchManager.setMinWeight(kpiTargetWeight.getMinDueTarget());
                                    cardKpiAchManager.setMinWeightedPerformance(cardKpiAchManager.getMinWeight() * cardKpiAchManager.getMinPerformance());
                                }
                            }

                            try {
                                interval=interval+10;
                                if(interval >= 60)
                                    interval=10;
                                Thread.sleep(interval);
                            } catch (InterruptedException e) {
                                System.out.println(e.getMessage());
                            }

                            CardKpiAchEntity oldObj=cardKpiAchDao.getByAccNo(kpiAch.getCardAccNo());

                            if(oldObj !=null)
                            {
                                kpiAch.setId(oldObj.getId());
//                                cardKpiAchDao.updateKpiAch(kpiAch);
                                cardKpiAchRepository.save(kpiAch);
                            }else {
//                                cardKpiAchDao.saveNew(kpiAch);
                                cardKpiAchRepository.save(kpiAch);
                            }

                            CardKpiAchManagerEntity oldObjMan=cardKpiAchDao.getByAccNoMan(cardKpiAchManager.getCardAccNo());
                            if(oldObjMan != null)
                            {
                                cardKpiAchManager.setId(oldObjMan.getId());
                                cardKpiAchDao.updateKpiAchMan(cardKpiAchManager);
                            }else {
                                cardKpiAchDao.saveNewMan(cardKpiAchManager);
                            }
                        }

                    }
                });
                kpiThread.start();
            }
            try {
                Thread.sleep(70000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
