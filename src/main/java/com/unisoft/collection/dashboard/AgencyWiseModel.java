package com.unisoft.collection.dashboard;

import lombok.Data;

/*
  Created by Md.   Islam on 1/4/2020
*/
@Data
public class AgencyWiseModel {

    private String product;
    private String dpd;
    private String agencyName;
    private long totalAc;
    private double totalOutstanding;
    long noOfPtp;
    long noOfFollowUp;

}
