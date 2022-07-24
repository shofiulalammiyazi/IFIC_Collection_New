package com.unisoft.reports.card.nonStarterCardDetails;

import lombok.Data;

import java.util.Date;

@Data
public class NonStarterCardDetailsDto {
    private String clientId;
    private String contractNo;
    private String customerName;
    private String cardNo;
    private String usage;
    private String activeOrInactive;
    private String state;
    private String bkt;
    private Date anniversaryDate;
    private Double limitAtmDom;
    private Double outstandingAtmDom;
    private Double minDueAtmDom;
    private Double limitAtmInt;
    private Double outstandingAtmInt;
    private Double minDueAtmInt;
}
