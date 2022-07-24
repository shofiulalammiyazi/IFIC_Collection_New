package com.unisoft.reports.card.monthWiseCardDisbursalList;

import lombok.Data;

@Data
public class MonthWiseCardDisbursalListDto {
    private String clientId;
    private String contractNo;
    private String customerName;
    private String cardNo;
    private String anniversaryDate;
    private String designation;
    private String companyName;
    private String limitAmtDom;
    private String limitAmtInt;
    private String month;
}
