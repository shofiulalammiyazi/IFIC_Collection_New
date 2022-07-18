package com.csinfotechbd.reports.retail.loan.clReportProfessionSegmentWise;

import lombok.Data;

@Data
public class ProfessionSegmentReportDto {

    private String segment;
    private Long totalAcc;
    private double totalOs;
    private Long clAccTotal;
    private double clOsTotal;
    private double clPercent;
    private Long parAccTotal;
    private double parOsTotal;
    private double parPercent;

    public ProfessionSegmentReportDto(){

    }


}
