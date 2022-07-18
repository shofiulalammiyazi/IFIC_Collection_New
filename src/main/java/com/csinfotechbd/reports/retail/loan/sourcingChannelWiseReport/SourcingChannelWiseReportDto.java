package com.csinfotechbd.reports.retail.loan.sourcingChannelWiseReport;

import lombok.Data;

import javax.persistence.Tuple;

@Data
public class SourcingChannelWiseReportDto {


    private String sourcingChannel;
    private double totalExposureOutstanding;
    private long   totalExposureAccount;
    private double totalClOutstanding;
    private double totalClPercentage;
    private long   totalClAccount;
    private double totalParOutstanding;
    private double totalParPercentage;
    private long   totalParAccount;

}
