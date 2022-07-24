package com.unisoft.reports.card.excessOverLimitCardList;

import lombok.Data;

@Data
public class ExcessOverLimitCardListDto {
    private String clientId;
    private String contractNo;
    private String customerName;
    private String cardNo;
    private String anniversaryDate;
    private String bkt;
    private String limitAmtDom;
    private String limitAmtInt;
    private String overLimitAmount;
    private String designation;
    private String companyName;
    private String state;
}
