package com.csinfotechbd.collection.dashboard;
/*
  Created by Md. Monirul Islam on 1/1/2020

*/

import lombok.Data;

@Data
public class TeamDetailModel {

    private String designation;
    private String dealerName;
    private String dealerPin;
    private long noOfAcc=0;
    private Double outstanding=0D;
    private double performance=0;
    private double remPar =0;
    private double remParQue =0;
    private double remNpl =0;
    private double remNplQue =0;
    private String lastMonthRating ;


}
