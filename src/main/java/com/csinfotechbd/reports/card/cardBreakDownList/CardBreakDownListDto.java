package com.csinfotechbd.reports.card.cardBreakDownList;

import lombok.Data;

@Data
public class CardBreakDownListDto {
    private String clientId;
    private String contractNo;
    private String customerName;
    private String cardNo;
    private String totalUsagesDom;
    private String totalInterestDom;
    private String totalFeesAndChargesDom;
    private String totalPaymentDom;
    private String totalUsagesInt;
    private String totalInterestInt;
    private String totalFeesAndChargesInt;
    private String totalPaymentInt;
}
