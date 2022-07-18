package com.csinfotechbd.collection.samd.dataEntry.initiativeActionTaken;

import lombok.Data;

@Data
public class InitiativeActionTakenPayload {
    private String initiativeActionTaken;
    private String account;

    public InitiativeActionTakenPayload(){}

    public InitiativeActionTakenPayload(String account, String initiativeActionTaken){
        this.account = account;
        this.initiativeActionTaken = initiativeActionTaken;
    }
}
