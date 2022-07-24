package com.unisoft.reports.retail.loan.vintageAnalysisReport;

import lombok.Data;

import javax.persistence.Tuple;

@Data
public class VintageAnalysisReportDto {

    private String disburseYear;
    private double ucOutstanding;
    private double ucPercentage;
    private long ucAccount;
    private double smaOutstanding;
    private double smaPercentage;
    private long   smaAccount;
    private double ssOutstanding;
    private double ssPercentage;
    private long   ssAccount;
    private double dfOutstanding;
    private double dfPercentage;
    private long   dfAccount;
    private double blOutstanding;
    private double blPercentage;
    private long   blAccount;
    private double grandTotalOutstanding;
    private double grandTotalPercentage;
    private long   grandTotalAccount;
    private double totalClOutstanding;
    private double totalClPercentage;
    private long   totalClAccount;
    private double totalParOutstanding;
    private double totalParPercentage;
    private long   totalParAccount;

    public VintageAnalysisReportDto() {
    }

    public VintageAnalysisReportDto(Tuple data) {

    }
}
