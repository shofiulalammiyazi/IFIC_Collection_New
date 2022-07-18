package com.csinfotechbd.reports.card.WrittenOffCardsRemainingBalanceList;

import lombok.Data;

@Data
public class WrittenOffCardsRemainingBalanceListDto {
    private String clientId;
    private String contractNo;
    private String customerName;
    private String cardNo;
    private String woYear;
    private String suitNo;
    private String jariNo;
    private String totalDues;
    private String totalPayment;
    private String waiverAmount;
    private String remainingBalance;
    private String totalMonthForCostOfFund;
    private String settlementDate;
}
