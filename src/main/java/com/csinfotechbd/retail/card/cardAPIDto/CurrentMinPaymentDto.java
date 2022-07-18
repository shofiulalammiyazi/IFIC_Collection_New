package com.csinfotechbd.retail.card.cardAPIDto;

import lombok.Data;

@Data
public class CurrentMinPaymentDto {

    private String contractNo;
    private String bdtAmount;
    private String usdAmount;
}
