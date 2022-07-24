package com.unisoft.loanApi.model;

public enum CustomerAndBorrowerInfoStatus {

    DEFAULT("DEFAULT"),
    PENDING("PENDING"),
    CONFIRM("CONFIRMED"),
    REJECT("REJECTED");

    private String status;

    CustomerAndBorrowerInfoStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
