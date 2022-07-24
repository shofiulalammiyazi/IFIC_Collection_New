package com.unisoft.customerloanprofile.AdditionalInfo;

public enum  AdditionalInfoStatus {

    DEFAULT("DEFAULT"),
    PENDING("PENDING"),
    CONFIRM("CONFIRMED"),
    REJECT("REJECTED");

    private String status;

    AdditionalInfoStatus(String status) {

        this.status = status;
    }

    public String getStatus(){

        return status;
    }

}
