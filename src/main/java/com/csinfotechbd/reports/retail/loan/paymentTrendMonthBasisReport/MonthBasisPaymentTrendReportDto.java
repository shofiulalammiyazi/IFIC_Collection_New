package com.csinfotechbd.reports.retail.loan.paymentTrendMonthBasisReport;

import lombok.Data;

@Data
public class MonthBasisPaymentTrendReportDto {

    private Object limitSegment;
    private Object greaterThanHundred = 0;
    private Object greaterThanFifty = 0;
    private Object lessThanFifty = 0;
    private Object noPayment = 0;
    private Object totalAccount = 0.00;

    private Object percentageOfGreaterThanHundred = 0.00;
    private Object percentageOfGreaterThanFifty = 0.00;
    private Object percentageOfLessThanFifty = 0.00;
    private Object percentageOfNoPayment = 0.00;

}
