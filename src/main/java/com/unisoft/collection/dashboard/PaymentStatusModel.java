package com.unisoft.collection.dashboard;
/*
  Created by Md.   Islam on 1/8/2020
*/

import com.unisoft.customerloanprofile.loanpayment.LoanPayment;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaymentStatusModel {

    private String narration;
    int totalAc=0;
    int totalAcToday=0;
    double paidAmnt=0;
    double paidAmntToday=0;

    List<CardAccountPaymentDetails> cardAccountPaymentDetails = new ArrayList<>();
    List<LoanPayment> loanAccountPaymentDetails = new ArrayList<>();

    public List<LoanPayment> getLoanAccountPaymentDetails() {
        return loanAccountPaymentDetails;
    }

    public void setLoanAccountPaymentDetails(List<LoanPayment> loanAccountPaymentDetails) {
        this.loanAccountPaymentDetails = loanAccountPaymentDetails;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public int getTotalAc() {
        return totalAc;
    }

    public void setTotalAc(int totalAc) {
        this.totalAc = totalAc;
    }

    public int getTotalAcToday() {
        return totalAcToday;
    }

    public void setTotalAcToday(int totalAcToday) {
        this.totalAcToday = totalAcToday;
    }

    public double getPaidAmnt() {
        return paidAmnt;
    }

    public void setPaidAmnt(double paidAmnt) {
        this.paidAmnt = paidAmnt;
    }

    public double getPaidAmntToday() {
        return paidAmntToday;
    }

    public void setPaidAmntToday(double paidAmntToday) {
        this.paidAmntToday = paidAmntToday;
    }

    public List<CardAccountPaymentDetails> getCardAccountPaymentDetails() {
        return cardAccountPaymentDetails;
    }

    public void setCardAccountPaymentDetails(List<CardAccountPaymentDetails> cardAccountPaymentDetails) {
        this.cardAccountPaymentDetails = cardAccountPaymentDetails;
    }
}
