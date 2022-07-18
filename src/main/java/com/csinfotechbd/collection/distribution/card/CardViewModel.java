package com.csinfotechbd.collection.distribution.card;

import lombok.Data;

import java.util.Date;

/*
Created by Monirul Islam at 7/23/2019
*/
@Data
public class CardViewModel {

    private String customerId;

    private String accountNo;

    private String customerName;

    private String outstandingAmount;

    private String minimumDueAmount;

    private Date createdDate;

    private String location;

    private String dpdBucket;

    private String ageCode;

    private String supervisorName;

    private String delaerName;

    private String AgencyName;

    private String monirotingStatus;

    private String dealerPin;

    public void setDealerPin(String dealerPin) {
        this.dealerPin = dealerPin;
    }

    public String getDealerPin() {
        return dealerPin;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(String outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public String getMinimumDueAmount() {
        return minimumDueAmount;
    }

    public void setMinimumDueAmount(String minimumDueAmount) {
        this.minimumDueAmount = minimumDueAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDpdBucket() {
        return dpdBucket;
    }

    public void setDpdBucket(String dpdBucket) {
        this.dpdBucket = dpdBucket;
    }

    public String getAgeCode() {
        return ageCode;
    }

    public void setAgeCode(String ageCode) {
        this.ageCode = ageCode;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getDelaerName() {
        return delaerName;
    }

    public void setDelaerName(String delaerName) {
        this.delaerName = delaerName;
    }

    public String getAgencyName() {
        return AgencyName;
    }

    public void setAgencyName(String agencyName) {
        AgencyName = agencyName;
    }

    public String getMonirotingStatus() {
        return monirotingStatus;
    }

    public void setMonirotingStatus(String monirotingStatus) {
        this.monirotingStatus = monirotingStatus;
    }
}
