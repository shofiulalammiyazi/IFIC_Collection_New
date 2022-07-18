package com.csinfotechbd.retail.card.reporttextfileupload;

import lombok.Data;

@Data
public class TransactionApprovedHistoryDTO {

    private String date;
    private String time;
    private String cardNo;
    private String transactionCurrencyCode;
    private String transactionAmount;
    private String transactionOriginalCountry;
    private String authorizedBillingCurrencyCode;
    private String authorizedBillingAmount;
    private String cnpSettlementBillingAmount;
    private String decisionResponseRef;
    private String merchantDescription;
    private String mcc;
    private String posEntryMode;
    private String settledIndicator;
}
