package com.csinfotechbd.reports.card.emiTrackerAndStatementReport;

import lombok.Data;

@Data
public class EmiTrackerAndStatementReportDto {
    private String pmtNo;
    private String emiAmount;
    private String paymentAmount;
    private String paymentDate;
    private String endingBalance;
    private String remarks;
}
