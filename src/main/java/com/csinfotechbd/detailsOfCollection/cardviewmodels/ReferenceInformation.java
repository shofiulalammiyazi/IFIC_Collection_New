package com.csinfotechbd.detailsOfCollection.cardviewmodels;

public class ReferenceInformation {

    private String refName;

    private String refAddress;

    private String refPhoneNo;

    private String refWithRelationship;

    public ReferenceInformation() {
    }

    public ReferenceInformation(String refName, String refAddress, String refPhoneNo, String refWithRelationship) {
        this.refName = refName;
        this.refAddress = refAddress;
        this.refPhoneNo = refPhoneNo;
        this.refWithRelationship = refWithRelationship;
    }

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefAddress() {
        return refAddress;
    }

    public void setRefAddress(String refAddress) {
        this.refAddress = refAddress;
    }

    public String getRefPhoneNo() {
        return refPhoneNo;
    }

    public void setRefPhoneNo(String refPhoneNo) {
        this.refPhoneNo = refPhoneNo;
    }

    public String getRefWithRelationship() {
        return refWithRelationship;
    }

    public void setRefWithRelationship(String refWithRelationship) {
        this.refWithRelationship = refWithRelationship;
    }
}
