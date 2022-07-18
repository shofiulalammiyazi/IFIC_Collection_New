package com.csinfotechbd.retail.card.cardAPIDto;

import lombok.Data;

@Data
public class PaymentInfo {

    private String contractNo;
    private String bdtPayment;
    private String usdPayment;
}
