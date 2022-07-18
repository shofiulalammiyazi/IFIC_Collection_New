package com.csinfotechbd.detailsOfCollection.cardviewmodels;

import lombok.Data;

@Data
public class RecoveryViewDTO {

    private String availableBalanceCashBdt;
    private String availableBalancePurchaseBdt;
    private String autoPayAcAvailableBalanceBdt;
    private String lastPaymentAmountBdt;
    private String lastPaymentDateBdt;
    private String availableBalanceCashUsd;
    private String availableBalancePurchaseUsd;
    private String autoPayAcAvailableBalanceUsd;
    private String lastPaymentAmountUsd;
    private String lastPaymentDateUsd;
    private String totalPaymentDateUsd;

}
