package com.csinfotechbd.reports.card.emiCardDetails;

import lombok.Data;

import java.util.Date;

@Data
public class EmiCardDetailsDto {
    private String clientId;
    private String contractNo;
    private String customerName;
    private String cardNo;
    private String state;
    private String settlementAmount;
    private String noOfEmi;
    private String emiAmount;
    private String tenor;
    private Date startsFrom;
    private Date endDate;
    private String udcStatus;
    private String chqNo;
    private String bankName;
    private String remarks;
}
