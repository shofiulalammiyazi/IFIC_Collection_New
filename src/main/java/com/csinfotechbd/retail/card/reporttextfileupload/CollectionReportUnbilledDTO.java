package com.csinfotechbd.retail.card.reporttextfileupload;

import lombok.Data;

@Data
public class CollectionReportUnbilledDTO {
    private String contractNo;
    private String clientId;
    private String creditLimit;
    private String totalBdtPay;
    private String totalUsdPay;
    private String firstTxnDate;
    private String firstTxnPoste;
    private String firstTxnAmt;
    private String cardHolderName;
    private String dueDate;
    private String cardNo;
}
