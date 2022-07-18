package com.csinfotechbd.detailsOfCollection.cardviewmodels;

public class PersonalInformation {
    private String fatherName;
    private String motherName;
    private String spouseName;
    private String spouseOccupation;
    private String passport;
    private String nid;
    private String emailAddress1;
    private String emailAddress2;
    private String emailAddress3;
    private String tin;
    private String professionSegment;

    public PersonalInformation() {
    }

    public PersonalInformation(String fatherName, String motherName, String spouseName, String spouseOccupation, String passport, String nid, String emailAddress1, String emailAddress2, String emailAddress3, String tin) {
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.spouseName = spouseName;
        this.spouseOccupation = spouseOccupation;
        this.passport = passport;
        this.nid = nid;
        this.emailAddress1 = emailAddress1;
        this.emailAddress2 = emailAddress2;
        this.emailAddress3 = emailAddress3;
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

    public String getSpouseOccupation() {
        return spouseOccupation;
    }

    public void setSpouseOccupation(String spouseOccupation) {
        this.spouseOccupation = spouseOccupation;
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

    public String getEmailAddress1() {
        return emailAddress1;
    }

    public void setEmailAddress1(String emailAddress1) {
        this.emailAddress1 = emailAddress1;
    }

    public String getEmailAddress2() {
        return emailAddress2;
    }

    public void setEmailAddress2(String emailAddress2) {
        this.emailAddress2 = emailAddress2;
    }

    public String getEmailAddress3() {
        return emailAddress3;
    }

    public void setEmailAddress3(String emailAddress3) {
        this.emailAddress3 = emailAddress3;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getProfessionSegment() {
        return professionSegment;
    }

    public void setProfessionSegment(String professionSegment) {
        this.professionSegment = professionSegment;
    }

    @Override
    public String toString() {
        return "PersonalInformation{" +
                "fatherName='" + fatherName + '\'' +
                ", motherName='" + motherName + '\'' +
                ", spouseName='" + spouseName + '\'' +
                ", spouseOccupation='" + spouseOccupation + '\'' +
                ", passport='" + passport + '\'' +
                ", nid='" + nid + '\'' +
                ", emailAddress1='" + emailAddress1 + '\'' +
                ", emailAddress2='" + emailAddress2 + '\'' +
                ", emailAddress3='" + emailAddress3 + '\'' +
                ", tin='" + tin + '\'' +
                '}';
    }
}
