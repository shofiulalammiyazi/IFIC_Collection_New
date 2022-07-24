package com.unisoft.collection.customerComplain;

public enum CustomerComplainStatus {
    DEFAULT("DEFAULT"),
    PENDING("PENDING"),
    RESOLVED("RESOLVED"),
    REJECT("REJECTED");

    private String status;

    CustomerComplainStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
