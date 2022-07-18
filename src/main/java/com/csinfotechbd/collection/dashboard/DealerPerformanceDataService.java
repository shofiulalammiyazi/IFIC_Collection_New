package com.csinfotechbd.collection.dashboard;

import com.csinfotechbd.collection.settings.ageCode.AgeCodeEntity;
import com.csinfotechbd.collection.settings.ageCode.AgeCodeService;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketService;
import com.csinfotechbd.collection.settings.productType.ProductTypeEntity;
import com.csinfotechbd.collection.settings.productType.ProductTypeService;
import com.csinfotechbd.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DealerPerformanceDataService {
    private final ProductTypeService productTypeService;
    private final AgeCodeService ageCodeService;
    private final DPDBucketService dpdBucketService;
    private final DealerPerformanceDataDao dealerPerformanceDataDao;
    private final DateUtils dateUtils;

    public void updateLoanDealerPerformanceData(List<ProductWiseSummary> summaryList, String dealerPin, String dealerName) {
        List<DPDBucketEntity> dpdBucketList = dpdBucketService.getActiveList();

        List<LoanPerformanceDataModel> performanceDataList = new ArrayList<>();

        Double totalOutstanding = 0d;
        Double performance = 0d;

        for (ProductWiseSummary summary : summaryList) {
            totalOutstanding = totalOutstanding + summary.getAmntEachDpd();
        }

        for (DPDBucketEntity bucket : dpdBucketList) {
            LoanPerformanceDataModel dataModel = new LoanPerformanceDataModel();
            double sumOfWp = 0;

            for (ProductWiseSummary summary : summaryList) {
                if (bucket.getBucketName().equals(summary.getDpdBucket())) {
                    sumOfWp = sumOfWp + summary.getWeightFlowPerformmance() + summary.getWeightBackPerformmance() + summary.getWeightOverDuePerformmance() + summary.getWeightRegularPerformmance();

                    dataModel.setDpdBucket(bucket.getBucketName());
                    dataModel.setSumOfAllWp(sumOfWp);
                    dataModel.setDpdOutStanding(summary.getAmntEachDpd());

                    performanceDataList.add(dataModel);
                }
            }
        }

        for (LoanPerformanceDataModel dataModel : performanceDataList) {
            performance = performance + ((dataModel.getDpdOutStanding() / totalOutstanding) * dataModel.getSumOfAllWp());
        }

        String tempFinalAvg = performance.toString() + "000000";
        tempFinalAvg = tempFinalAvg.substring(0, tempFinalAvg.indexOf(".") + 5);

        /*
            Performance is actually calculated for previous date as payment information
            is only available till previous date
         */
        Date yesterday = dateUtils.getNextOrPreviousDate(new Date(), -1);
        yesterday = dateUtils.getEndingPointOfDay(yesterday);
        Date monthStartDate = dateUtils.getMonthStartDate(yesterday);
        DealerPerformanceDataEntity dealerPerformanceData = dealerPerformanceDataDao
                .getPerformanceDataWithinDateRange(dealerPin, "LOAN", monthStartDate, yesterday);

        if (dealerPerformanceData == null)
            dealerPerformanceData = new DealerPerformanceDataEntity();

        dealerPerformanceData.setUnit("LOAN");
        dealerPerformanceData.setPerformanceAvg(Double.parseDouble(tempFinalAvg));
        dealerPerformanceData.setPerformanceDate(yesterday);
        dealerPerformanceData.setName(dealerName);
        dealerPerformanceData.setDealerPin(dealerPin);

        dealerPerformanceDataDao.saveOrUpdateData(dealerPerformanceData);
    }

    public void updateCardDealerPerformanceData(List<KpiVsAchDataModel> dataModelList, String dealerPin, String dealerName) {
        List<AgeCodeEntity> ageCodeList = ageCodeService.getActiveList();

        List<ProductTypeEntity> productTypeList = productTypeService.getAllActive();

        List<CardPerformanceDatamodel> cardPerformanceDatamodelList = new ArrayList<>();

        for (AgeCodeEntity ageCode : ageCodeList) {
            int productTypeCount = 0;
            for (ProductTypeEntity typeEntity : productTypeList) {
                if (typeEntity.getCardsCategory() != null) {
                    for (KpiVsAchDataModel kpiVsAch : dataModelList) {   //counting products
                        if (ageCode.getName().equals(Integer.toString(kpiVsAch.getAgeCode())) && typeEntity.getCardsCategory().equals(kpiVsAch.getCardsCategory())) {
                            productTypeCount++;
                        }
                    }
                }
            }
            CardPerformanceDatamodel performanceDatamodel = new CardPerformanceDatamodel();

            for (KpiVsAchDataModel kpiVsAch : dataModelList) {   //sum of wp of a particular age code
                if (ageCode.getName().equals(Integer.toString(kpiVsAch.getAgeCode()))) {
                    performanceDatamodel.setAgeCode(Integer.parseInt(ageCode.getName()));
                    performanceDatamodel.setProducttypeCount(productTypeCount);

                    performanceDatamodel.setSavePerf(performanceDatamodel.getSavePerf() + kpiVsAch.getSaveWeightedPerformance());
                    performanceDatamodel.setBackPerf(performanceDatamodel.getBackPerf() + kpiVsAch.getBackWeightedPerformance());
                    performanceDatamodel.setFlowPerf(performanceDatamodel.getFlowPerf() + kpiVsAch.getFlowWeightedPerformance());
                    performanceDatamodel.setMinDuePerf(performanceDatamodel.getMinDuePerf() + kpiVsAch.getMinWeightedPerformance());
                }
            }
            cardPerformanceDatamodelList.add(performanceDatamodel);
        }

        List<CardPerformanceDatamodel> avg1List = new ArrayList<>();

        for (CardPerformanceDatamodel datamodel : cardPerformanceDatamodelList) {
            //sum of wp of a particular age code/product count
            CardPerformanceDatamodel avg1Obj = new CardPerformanceDatamodel();

            if (datamodel.getProducttypeCount() > 0) {
                avg1Obj.setSavePerf(datamodel.getSavePerf() / datamodel.getProducttypeCount());
                avg1Obj.setFlowPerf(datamodel.getFlowPerf() / datamodel.getProducttypeCount());
                avg1Obj.setBackPerf(datamodel.getBackPerf() / datamodel.getProducttypeCount());
                avg1Obj.setMinDuePerf(datamodel.getMinDuePerf() / datamodel.getProducttypeCount());

                avg1List.add(avg1Obj);
            }
        }

        List<String> uniqueAgeCodeList = new ArrayList<>();

        //creating a list of unique age code
        for (AgeCodeEntity ageCode : ageCodeList) {
            for (KpiVsAchDataModel dataModel : dataModelList) {
                if (uniqueAgeCodeList.size() <= 0) {
                    uniqueAgeCodeList.add(ageCode.getName());
                }

                if (ageCode.getName().equals(Integer.toString(dataModel.getAgeCode()))) {
                    if (!uniqueAgeCodeList.contains(ageCode.getName())) {
                        uniqueAgeCodeList.add(ageCode.getName());
                    }
                }
            }
        }

        double avg2SumSave = 0;
        double avg2SumFlow = 0;
        double avg2SumBack = 0;
        double avg2SumMinDue = 0;

        double avg2Save = 0;
        double avg2Flow = 0;
        double avg2back = 0;
        double avg2MinDue = 0;

        for (CardPerformanceDatamodel avg1 : avg1List) {
            avg2SumSave = avg2SumSave + avg1.getSavePerf();
            avg2SumFlow = avg2SumFlow + avg1.getFlowPerf();
            avg2SumBack = avg2SumBack + avg1.getBackPerf();
            avg2SumMinDue = avg2SumMinDue + avg1.getMinDuePerf();
        }

        if (uniqueAgeCodeList.size() > 0) {
            avg2Save = avg2SumSave / uniqueAgeCodeList.size();
            avg2Flow = avg2SumFlow / uniqueAgeCodeList.size();
            avg2back = avg2SumBack / uniqueAgeCodeList.size();
            avg2MinDue = avg2SumMinDue / uniqueAgeCodeList.size();
        }

        Double finalAvg = avg2Save + avg2Flow + avg2back + avg2MinDue;

        String tempFinalAvg = finalAvg.toString() + "000000";
        tempFinalAvg = tempFinalAvg.substring(0, tempFinalAvg.indexOf(".") + 5);

        finalAvg = Double.parseDouble(tempFinalAvg);

        Date yesterday = dateUtils.getNextOrPreviousDate(new Date(), -1);
        yesterday = dateUtils.getEndingPointOfDay(yesterday);
        Date monthStartDate = dateUtils.getMonthStartDate(yesterday);
        DealerPerformanceDataEntity dealerPerformanceData = dealerPerformanceDataDao
                .getPerformanceDataWithinDateRange(dealerPin, "LOAN", monthStartDate, yesterday);

        if (dealerPerformanceData == null)
            dealerPerformanceData = new DealerPerformanceDataEntity();

        dealerPerformanceData.setDealerPin(dealerPin);
        dealerPerformanceData.setName(dealerName);
        // Previous date to update previous month performance at first date of current month in a scheduler
        dealerPerformanceData.setPerformanceDate(yesterday);
        dealerPerformanceData.setPerformanceAvg(finalAvg);
        dealerPerformanceData.setUnit("CARD");

        dealerPerformanceDataDao.saveOrUpdateData(dealerPerformanceData);
    }

    public double getCurrentMonthDealerPerformanceAverage(String userPins, String unit) {
        DealerPerformanceDataEntity performanceData = dealerPerformanceDataDao.getCurrentMonthPerformanceByUserPin(userPins, unit);
        return performanceData.getPerformanceAvg();
    }


}
