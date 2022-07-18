package com.csinfotechbd.retail.card.cardAPIDto;

import lombok.Data;

@Data
public class PaymentDuringMonthDto {

    private String contractNo;
    private String bdtPayment;
    private String usdPayment;
    private Double currBdtPayment;
    private Double currUsdPayment;
}
