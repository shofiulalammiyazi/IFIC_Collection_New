package com.csinfotechbd.collection.finalSattlementAmount;

import com.csinfotechbd.common.CommonEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

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
