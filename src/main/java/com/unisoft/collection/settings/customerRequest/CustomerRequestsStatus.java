package com.unisoft.collection.settings.customerRequest;

public enum CustomerRequestsStatus {

    DEFAULT("DEFAULT"),
    PENDING("PENDING"),
    CONFIRM("CONFIRMED"),
    REJECT("REJECTED");

    private String status;

    CustomerRequestsStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

}
