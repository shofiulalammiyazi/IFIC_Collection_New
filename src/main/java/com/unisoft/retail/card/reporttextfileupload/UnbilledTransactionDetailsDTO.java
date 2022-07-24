package com.unisoft.retail.card.reporttextfileupload;

import lombok.Data;

@Data
public class UnbilledTransactionDetailsDTO {

    private String cardNo;
    private String transactionDate;
    private String transactionPostDate;
    private String transactionCode;
    private String merchantCode;
    private String description;
    private String transactionRefNo;
    private String currency;
    private String bdtUnbilledAmount;
    private String billingCurrency;
    private String billingAmount;
}
