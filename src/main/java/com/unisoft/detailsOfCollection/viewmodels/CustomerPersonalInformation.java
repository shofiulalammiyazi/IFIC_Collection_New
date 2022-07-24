package com.unisoft.detailsOfCollection.viewmodels;

public class CustomerPersonalInformation {
    private String fatherName;
    private String motherName;
    private String spouseName;
    private String passport;
    private String nid;
    private String emailAddress;
    private String tin;

    public CustomerPersonalInformation() {
    }

    public CustomerPersonalInformation(String fatherName, String motherName, String spouseName, String spouseOccupation, String passport, String nid, String emailAddress, String tin) {
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.spouseName = spouseName;
        this.passport = passport;
        this.nid = nid;
        this.emailAddress = emailAddress;
        this.tin = tin;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    @Override
    public String toString() {
        return "CustomerPersonalInformation{" +
                "fatherName='" + fatherName + '\'' +
                ", motherName='" + motherName + '\'' +
                ", spouseName='" + spouseName + '\'' +
                ", passport='" + passport + '\'' +
                ", nid='" + nid + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", tin='" + tin + '\'' +
                '}';
    }
}
