package com.unisoft.reports.card.secureCardDetails;

import lombok.Data;

import java.util.Date;

@Data
public class SecureCardDetailsDto {
    private String clientId;
    private String contractNo;
    private String customerName;
    private String cardNo;
    private String instrumentValue;
    private String lienAmount;
    private String lienACNo;
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
