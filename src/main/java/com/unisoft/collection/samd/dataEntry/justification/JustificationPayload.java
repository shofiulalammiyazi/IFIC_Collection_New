package com.unisoft.collection.samd.dataEntry.justification;

import lombok.Data;

@Data
public class JustificationPayload {
    private String justification;
    private String account;

    public JustificationPayload(){}

    public JustificationPayload(String account, String justification){
        this.account = account;
        this.justification = justification;
    }
}
