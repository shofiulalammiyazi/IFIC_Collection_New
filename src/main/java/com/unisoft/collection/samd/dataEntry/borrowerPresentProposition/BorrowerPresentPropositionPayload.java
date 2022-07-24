package com.unisoft.collection.samd.dataEntry.borrowerPresentProposition;

import lombok.Data;

@Data
public class BorrowerPresentPropositionPayload {

    private String borrowerPresentProposition;
    private String account;

    public BorrowerPresentPropositionPayload(){}

    public BorrowerPresentPropositionPayload(String account, String borrowerPresentProposition){
        this.account = account;
        this.borrowerPresentProposition = borrowerPresentProposition;
    }
}
