package com.unisoft.collection.dashboard;

import java.util.Date;

public class CardAccountPaymentDetails {
    private String accountNo;
    private double amount;
    private Date paymentDate;

    public CardAccountPaymentDetails(String accountNo, double amount, Date paymentDate) {
        this.accountNo = accountNo;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
