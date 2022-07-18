package com.csinfotechbd.collection.distribution.loan;

public class CurrentOverDueATE {
    private double currentOverdue;
    private double currentAte;

    public CurrentOverDueATE() {
    }

    public CurrentOverDueATE(double currentOverdue, double currentAte) {
        this.currentOverdue = currentOverdue;
        this.currentAte = currentAte;
    }

    public double getCurrentOverdue() {
        return currentOverdue;
    }

    public void setCurrentOverdue(double currentOverdue) {
        this.currentOverdue = currentOverdue;
    }

    public double getCurrentAte() {
        return currentAte;
    }

    public void setCurrentAte(double currentAte) {
        this.currentAte = currentAte;
    }
}
