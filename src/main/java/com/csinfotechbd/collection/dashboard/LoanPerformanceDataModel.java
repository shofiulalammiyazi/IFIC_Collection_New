package com.csinfotechbd.collection.dashboard;
/*
  Created by Md. Monirul Islam on 2/6/2020
*/

import lombok.Data;

@Data
public class LoanPerformanceDataModel {

    private String dpdBucket="";
    private Double dpdOutStanding=0d;
    private double sumOfAllWp=0d;

}
