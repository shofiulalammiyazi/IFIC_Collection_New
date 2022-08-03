package com.unisoft.collection.finalSattlementAmount;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class FinalSattlementAmount extends CommonEntity {

    private Long customerId;

    private double payOffTotalPayment;

    private double payOffPrincipleAmount;

    private double payOffInterestAmount;

    private double payOffPenalInterest;

    private double payOffOtherCharges;

    private double earlySettlementFee;

    private double earlySettlementFeeVat;
}
