package com.unisoft.collection.samd.dataEntry.futureRecoveryPlan;

import lombok.Data;

@Data
public class FutureRecoveryPlanPayload {
    private String futureRecoveryPlan;
    private String account;

    public FutureRecoveryPlanPayload(){}

    public FutureRecoveryPlanPayload(String account, String futureRecoveryPlan){
        this.account = account;
        this.futureRecoveryPlan = futureRecoveryPlan;
    }
}
