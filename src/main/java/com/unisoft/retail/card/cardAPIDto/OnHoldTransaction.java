package com.unisoft.retail.card.cardAPIDto;

import lombok.Data;

@Data
public class OnHoldTransaction {

    private String contractNo;
    private String drOnHoldBdt;
    private String crOnHoldBdt;
    private String drOnHoldUsd;
    private String crOnHoldUsd;

}
