package com.csinfotechbd.collection.distribution.samAccountHandover;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SamAccountHandoverViewModel {

    private Long id;

    private String loanAccountNo;

    private String accountName;

    private String cardNo;

    private String cardName;

    private String customerId;

    private String productUnit;

    private String ageCode;

    private String dpdBucket;

    public SamAccountHandoverViewModel() {
    }

    public SamAccountHandoverViewModel(Long id, String loanAccountNo, String accountName, String cardNo, String cardName, String customerId, String productUnit, String ageCode, String dpdBucket) {
        this.id = id;
        this.loanAccountNo = loanAccountNo;
        this.accountName = accountName;
        this.cardNo = cardNo;
        this.cardName = cardName;
        this.customerId = customerId;
        this.productUnit = productUnit;
        this.ageCode = ageCode;
        this.dpdBucket = dpdBucket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanAccountNo() {
        return loanAccountNo;
    }

    public void setLoanAccountNo(String loanAccountNo) {
        this.loanAccountNo = loanAccountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getAgeCode() {
        return ageCode;
    }

    public void setAgeCode(String ageCode) {
        this.ageCode = ageCode;
    }

    public String getDpdBucket() {
        return dpdBucket;
    }

    public void setDpdBucket(String dpdBucket) {
        this.dpdBucket = dpdBucket;
    }
}
