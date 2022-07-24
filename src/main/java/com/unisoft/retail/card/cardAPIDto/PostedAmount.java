package com.unisoft.retail.card.cardAPIDto;

import lombok.Data;

@Data
public class PostedAmount {

    private String contractNo;
    private String bdtUnbilledAmount;
    private String usdUnbilledAmount;
}
