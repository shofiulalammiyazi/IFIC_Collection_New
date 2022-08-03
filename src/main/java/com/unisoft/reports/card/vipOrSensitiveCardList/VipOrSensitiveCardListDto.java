package com.unisoft.reports.card.vipOrSensitiveCardList;

import lombok.Data;

import java.util.Date;

@Data
public class VipOrSensitiveCardListDto {
    private String clientId;
    private String contractNo;
    private String cardNo;
    private String customerName;
    private String remarks;
    private Date anniversaryDate;
    private String btkAge;
    private Double limitAtmDom;
    private Double outstandingAtmDom;
    private Double minDueAtmDom;
    private Double limitAtmInt;
    private Double outstandingAtmInt;
    private Double minDueAtmInt;
}
