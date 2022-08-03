package com.unisoft.collection.dashboard;


import lombok.Data;

@Data
public class LoanPerformanceDataModel {

    private String dpdBucket="";
    private Double dpdOutStanding=0d;
    private double sumOfAllWp=0d;

}
