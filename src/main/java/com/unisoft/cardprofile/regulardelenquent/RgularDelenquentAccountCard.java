package com.unisoft.cardprofile.regulardelenquent;

public class RgularDelenquentAccountCard {

    private String cardNumber;

    private String customerName;

    private String ageCode;

    private String finAccount;

    private String customerId;

    private String outstanding;

    private String minDue;

    private String productType;

    private String securedFlag;


    public RgularDelenquentAccountCard() {
    }

    public RgularDelenquentAccountCard(String cardNumber, String customerName, String ageCode, String finAccount, String customerId, String outstanding, String minDue, String productType, String securedFlag) {
        this.cardNumber = cardNumber;
        this.customerName = customerName;
        this.ageCode = ageCode;
        this.finAccount = finAccount;
        this.customerId = customerId;
        this.outstanding = outstanding;
        this.minDue = minDue;
        this.productType = productType;
        this.securedFlag = securedFlag;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAgeCode() {
        return ageCode;
    }

    public void setAgeCode(String ageCode) {
        this.ageCode = ageCode;
    }

    public String getFinAccount() {
        return finAccount;
    }

    public void setFinAccount(String finAccount) {
        this.finAccount = finAccount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public String getMinDue() {
        return minDue;
    }

    public void setMinDue(String minDue) {
        this.minDue = minDue;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSecuredFlag() {
        return securedFlag;
    }

    public void setSecuredFlag(String securedFlag) {
        this.securedFlag = securedFlag;
    }

}
