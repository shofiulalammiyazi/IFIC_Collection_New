package com.csinfotechbd.customerloanprofile.guarantorinfo;

public enum  GuarantorInfoStatus {

    DEFAULT("DEFAULT"),
    PENDING("PENDING"),
    CONFIRM("CONFIRMED"),
    REJECT("REJECTED");

    private String status;

     GuarantorInfoStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
