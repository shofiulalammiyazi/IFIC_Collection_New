package com.csinfotechbd.reports.card.fidCardDetails;

import lombok.Data;

import java.util.Date;

@Data
public class FidCardDetailDto {
    private Integer clientId;
    private Integer contractNo;
    private String customerName;
    private String cardNo;
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
