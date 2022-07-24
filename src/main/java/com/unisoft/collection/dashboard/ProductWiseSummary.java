package com.unisoft.collection.dashboard;

import lombok.Data;

@Data
public class ProductWiseSummary {

    private String product;
    private String ageCode;
    private String dpdBucket;

    private double noOfAcEachDpd;
    private double amntEachDpd;

    private double weightBackPerc;
    private double weightFlowPerc;
    private double weightSavePerc;
    private double weightRegularPerc;
    private double weightOverdDuePerc;

    private double amntRegular;
    private double tarAmntRegular;
    private double tarAmntRegularPerc;
    private double regularPerFormance;
    private double amntRegularPerc;

    private double regularAcNo;
    private double tarNoOfAcRegular;
    private double noOfAcRegularPerc;

    private double weightBackPerformmance;
    private double weightSavePerformmance;
    private double weightFlowPerformmance;
    private double weightRegularPerformmance;
    private double weightOverDuePerformmance;

    private double savePerformance;
    private double flowPerformance;
    private double backPerformance;
    private double overDuePerformance;

    private double amntOverDue;
    private double amntOverDuePerc;
    private double tarAmntOverDue;
    private double tarAmntOverDuePerc;
    private double noOfOverDue;
    private double tarNoOfOverDue;
    private double noOfAmntOverDuePerc;

    private double noOfAcPerPT;
    private double amntPerPT;
    private double tarOfNpl;
    private double tarOfPar;
    private double tarOfNplPerc;
    private double tarOfParPerc;

    private double shortFallFlow;
    private double shortFallSave;
    private double shortFallBack;
    private double shortFallNpl;
    private double shortFallPar;
    private double shortFallOverDue;
    private double shortFallRegular;

    private double noOfFlowAc;
    private double noOfFlowAcPerc;
    private double noOfSaveAc;
    private double noOfSaveAcPerc;
    private double noOfBackAc;
    private double noOfBackAcPerc;
    private double noOfMinDue;
    private double noOfMinDuePerc;
    private double noOfTotalPaid;
    private double noOfTotalPaidPerc;
    private double noOfTotalUnPaid;
    private double noOfTotalUnPaidPerc;

    private double tarOfFlowAc;
    private double tarOfFlowAcPerc;
    private double tarOfSaveAc;
    private double tarOfSaveAcPerc;
    private double tarOfBackAc;
    private double tarOfBackAcPerc;
    private double tarOfMinDue;
    private double tarOfMinDuePerc;
    private double tarOfTotalPaid;
    private double tarOfTotalPaidPerc;
    private double tarOfTotalUnPaid;
    private double tarOfTotalUnPaidPerc;


    private double amntOfFlowAc;
    private double amntOfFlowAcPerc;
    private double amntOfSaveAc;
    private double amntOfSaveAcPerc;
    private double amntOfBackAc;
    private double amntOfBackAcPerc;
    private double amntOfMinDue;
    private double amntOfMinDuePerc;
    private double amntOfTotalPaid;
    private double amntOfTotalPaidPerc;
    private double amntOfTotalUnPaid;
    private double amntOfTotalUnPaidPerc;

    private double tarAmntOfFlowAc;
    private double tarAmntOfFlowAcPerc;
    private double tarAmntOfSaveAc;
    private double tarAmntOfSaveAcPerc;
    private double tarAmntOfBackAc;
    private double tarAmntOfBackAcPerc;
    private double tarAmntOfMinDue;
    private double tarAmntOfMinDuePerc;
    private double tarAmntOfTotalPaid;
    private double tarAmntOfTotalPaidPerc;
    private double tarAmntOfTotalUnPaid;
    private double tarAmntOfTotalUnPaidPerc;

}