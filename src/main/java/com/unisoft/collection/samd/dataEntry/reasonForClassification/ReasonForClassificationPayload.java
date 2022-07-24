package com.unisoft.collection.samd.dataEntry.reasonForClassification;

import lombok.Data;

@Data
public class ReasonForClassificationPayload {
    private String borrowerPointOfView;
    private String account;
    private String bankPointOfView;

    public ReasonForClassificationPayload(){

    }

    public ReasonForClassificationPayload(String borrowerPointOfView, String bankPointOfView, String account){
        this.account = account;
        this.borrowerPointOfView = borrowerPointOfView;
        this.bankPointOfView = bankPointOfView;
    }
}
