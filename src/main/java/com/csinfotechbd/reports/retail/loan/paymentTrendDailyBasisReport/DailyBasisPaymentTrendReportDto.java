package com.csinfotechbd.reports.retail.loan.paymentTrendDailyBasisReport;

import lombok.Data;

import javax.persistence.Tuple;

@Data
public class DailyBasisPaymentTrendReportDto {

    private String dpdBucket;
    private double outstanding;
    private double overdue;
    private long   overdueAccounts;
    private double payment;
    private long   paymentAccounts;
    private long   zeroOverdueAccounts;

}
