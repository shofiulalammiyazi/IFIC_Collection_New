package com.csinfotechbd.reports.card.writtenOffCardPaymentList;

import lombok.Data;

@Data
public class WrittenOffCardPaymentListDto {
    private String clientId;
    private String contractNo;
    private String customerName;
    private String cardNo;
    private String paymentBdt;
    private String paymentUsd;
    private String totalPayment;
}
