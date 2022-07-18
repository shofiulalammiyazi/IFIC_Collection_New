package com.csinfotechbd.collection.dashboard;
/*
  Created by Md. Monirul Islam on 1/8/2020
*/

import lombok.Data;

import java.util.Date;

@Data
public class CardPaymentModel {

    private String cardNo;
    private Date paymentDate;
    private double amount=0;
    private int currentAgeCode;
    private String finAccNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getCurrentAgeCode() {
        return currentAgeCode;
    }

    public void setCurrentAgeCode(int currentAgeCode) {
        this.currentAgeCode = currentAgeCode;
    }

    public String getFinAccNo() {
        return finAccNo;
    }

    public void setFinAccNo(String finAccNo) {
        this.finAccNo = finAccNo;
    }
}
