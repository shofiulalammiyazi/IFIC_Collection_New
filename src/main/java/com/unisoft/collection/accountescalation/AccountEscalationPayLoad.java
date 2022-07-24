package com.unisoft.collection.accountescalation;

public class AccountEscalationPayLoad {

    private String account;

    private String notes;

    public AccountEscalationPayLoad() {
    }

    public AccountEscalationPayLoad(String account, String notes) {
        this.account = account;
        this.notes = notes;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
