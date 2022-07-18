package com.csinfotechbd.detailsOfCollection.viewmodels;

public class CustomerInterestDetail {
    private String demandInterestAmount;
    private String accuredInterestAmount;
    private String demandPenalInterest;
    private String accuredPenalInterest;
    private String penalSatisfied;

    public CustomerInterestDetail() {
    }

    public CustomerInterestDetail(String demandInterestAmount, String accuredInterestAmount, String demandPenalInterest, String accuredPenalInterest, String penalSatisfied) {
        this.demandInterestAmount = demandInterestAmount;
        this.accuredInterestAmount = accuredInterestAmount;
        this.demandPenalInterest = demandPenalInterest;
        this.accuredPenalInterest = accuredPenalInterest;
        this.penalSatisfied = penalSatisfied;
    }

    public String getDemandInterestAmount() {
        return demandInterestAmount;
    }

    public void setDemandInterestAmount(String demandInterestAmount) {
        this.demandInterestAmount = demandInterestAmount;
    }

    public String getAccuredInterestAmount() {
        return accuredInterestAmount;
    }

    public void setAccuredInterestAmount(String accuredInterestAmount) {
        this.accuredInterestAmount = accuredInterestAmount;
    }

    public String getDemandPenalInterest() {
        return demandPenalInterest;
    }

    public void setDemandPenalInterest(String demandPenalInterest) {
        this.demandPenalInterest = demandPenalInterest;
    }

    public String getAccuredPenalInterest() {
        return accuredPenalInterest;
    }

    public void setAccuredPenalInterest(String accuredPenalInterest) {
        this.accuredPenalInterest = accuredPenalInterest;
    }

    public String getPenalSatisfied() {
        return penalSatisfied;
    }

    public void setPenalSatisfied(String penalSatisfied) {
        this.penalSatisfied = penalSatisfied;
    }
}
