package com.csinfotechbd.customerloanprofile.referenceinfo;

public enum ReferenceInfoStatus {

    DEFAULT("DEFAULT"),
    PENDING("PENDING"),
    CONFIRM("CONFIRMED"),
    REJECT("REJECTED");

    private String status;

    ReferenceInfoStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
