package com.csinfotechbd.detailsOfCollection.cardviewmodels;

public class ProfileInformation {

    private String customerID;

    private String customerName;

    private String occupation;

    private String designation;

    private String natureOfBusiness;

    private String dateofBirth;

    private String gender;

    private String customerType;

    private String companyName;

    private String companyPhone;

    private String homeAddress;

    private String officeAddress;

    private String permanentAddress;

    private String contactNo;

    private String homePhoneNo;

    private String accountName;

    private String smsSubscribe;

    private String directMainInd;

    public ProfileInformation() {
    }

    public ProfileInformation(String customerID, String customerName, String occupation, String designation, String natureOfBusiness, String dateofBirth, String gender, String customerType, String companyName, String companyPhone, String homeAddress, String officeAddress, String permanentAddress, String contactNo, String homePhoneNo, String accountName, String smsSubscribe, String directMainInd) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.occupation = occupation;
        this.designation = designation;
        this.natureOfBusiness = natureOfBusiness;
        this.dateofBirth = dateofBirth;
        this.gender = gender;
        this.customerType = customerType;
        this.companyName = companyName;
        this.companyPhone = companyPhone;
        this.homeAddress = homeAddress;
        this.officeAddress = officeAddress;
        this.permanentAddress = permanentAddress;
        this.contactNo = contactNo;
        this.homePhoneNo = homePhoneNo;
        this.accountName = accountName;
        this.smsSubscribe = smsSubscribe;
        this.directMainInd = directMainInd;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getNatureOfBusiness() {
        return natureOfBusiness;
    }

    public void setNatureOfBusiness(String natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getHomePhoneNo() {
        return homePhoneNo;
    }

    public void setHomePhoneNo(String homePhoneNo) {
        this.homePhoneNo = homePhoneNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getSmsSubscribe() {
        return smsSubscribe;
    }

    public void setSmsSubscribe(String smsSubscribe) {
        this.smsSubscribe = smsSubscribe;
    }

    public String getDirectMainInd() {
        return directMainInd;
    }

    public void setDirectMainInd(String directMainInd) {
        this.directMainInd = directMainInd;
    }

    @Override
    public String toString() {
        return "ProfileInformation{" +
                "customerID='" + customerID + '\'' +
                ", customerName='" + customerName + '\'' +
                ", occupation='" + occupation + '\'' +
                ", designation='" + designation + '\'' +
                ", natureOfBusiness='" + natureOfBusiness + '\'' +
                ", dateofBirth='" + dateofBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", customerType='" + customerType + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", officeAddress='" + officeAddress + '\'' +
                ", permanentAddress='" + permanentAddress + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", homePhoneNo='" + homePhoneNo + '\'' +
                ", accountName='" + accountName + '\'' +
                ", smsSubscribe='" + smsSubscribe + '\'' +
                ", directMainInd='" + directMainInd + '\'' +
                '}';
    }
}
