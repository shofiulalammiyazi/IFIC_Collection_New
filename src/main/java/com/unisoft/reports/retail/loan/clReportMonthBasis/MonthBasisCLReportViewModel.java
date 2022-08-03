package com.unisoft.reports.retail.loan.clReportMonthBasis;

import lombok.Data;

@Data
public class MonthBasisCLReportViewModel {

    private int blNoAcc;
    private int dfNoAcc;
    private int ssNoAcc;
    private int smaNoAcc;
    private int stdoAcc;

    private double blOutstanding;
    private double dfOutstanding;
    private double ssOutstanding;
    private double smOutstanding;
    private double stOutstanding;

    // previous month data
    private double prevBlOutstanding;
    private double prevDfOutstanding;
    private double prevSsOutstanding;
    private double prevSmOutstanding;
    private double prevStOutstanding;

    private double blDfSsGroup;
    private double smaStGroup;

    private int totalAcc;
    private double totalOutstanding;
    private double totalGroupValue;
    private double totalValuePreviousMnth;

    private double totalExposure;  // current month OS - previous month OS
    private double totalCl;  // current month Cl - previous month Cl
    private double totalPar; // current month par - previous month par


    public int getBlNoAcc() {
        return blNoAcc;
    }

    public void setBlNoAcc(int blNoAcc) {
        this.blNoAcc = blNoAcc;
    }

    public int getDfNoAcc() {
        return dfNoAcc;
    }

    public void setDfNoAcc(int dfNoAcc) {
        this.dfNoAcc = dfNoAcc;
    }

    public int getSsNoAcc() {
        return ssNoAcc;
    }

    public void setSsNoAcc(int ssNoAcc) {
        this.ssNoAcc = ssNoAcc;
    }

    public int getSmaNoAcc() {
        return smaNoAcc;
    }

    public void setSmaNoAcc(int smaNoAcc) {
        this.smaNoAcc = smaNoAcc;
    }

    public int getStdoAcc() {
        return stdoAcc;
    }

    public void setStdoAcc(int stdoAcc) {
        this.stdoAcc = stdoAcc;
    }

    public double getBlOutstanding() {
        return blOutstanding;
    }

    public void setBlOutstanding(double blOutstanding) {
        this.blOutstanding = blOutstanding;
    }

    public double getDfOutstanding() {
        return dfOutstanding;
    }

    public void setDfOutstanding(double dfOutstanding) {
        this.dfOutstanding = dfOutstanding;
    }

    public double getSsOutstanding() {
        return ssOutstanding;
    }

    public void setSsOutstanding(double ssOutstanding) {
        this.ssOutstanding = ssOutstanding;
    }

    public double getSmOutstanding() {
        return smOutstanding;
    }

    public void setSmOutstanding(double smOutstanding) {
        this.smOutstanding = smOutstanding;
    }

    public double getStOutstanding() {
        return stOutstanding;
    }

    public void setStOutstanding(double stOutstanding) {
        this.stOutstanding = stOutstanding;
    }

    public double getPrevBlOutstanding() {
        return prevBlOutstanding;
    }

    public void setPrevBlOutstanding(double prevBlOutstanding) {
        this.prevBlOutstanding = prevBlOutstanding;
    }

    public double getPrevDfOutstanding() {
        return prevDfOutstanding;
    }

    public void setPrevDfOutstanding(double prevDfOutstanding) {
        this.prevDfOutstanding = prevDfOutstanding;
    }

    public double getPrevSsOutstanding() {
        return prevSsOutstanding;
    }

    public void setPrevSsOutstanding(double prevSsOutstanding) {
        this.prevSsOutstanding = prevSsOutstanding;
    }

    public double getPrevSmOutstanding() {
        return prevSmOutstanding;
    }

    public void setPrevSmOutstanding(double prevSmOutstanding) {
        this.prevSmOutstanding = prevSmOutstanding;
    }

    public double getPrevStOutstanding() {
        return prevStOutstanding;
    }

    public void setPrevStOutstanding(double prevStOutstanding) {
        this.prevStOutstanding = prevStOutstanding;
    }

    public double getBlDfSsGroup() {
        return blDfSsGroup;
    }

    public void setBlDfSsGroup(double blDfSsGroup) {
        this.blDfSsGroup = blDfSsGroup;
    }

    public double getSmaStGroup() {
        return smaStGroup;
    }

    public void setSmaStGroup(double smaStGroup) {
        this.smaStGroup = smaStGroup;
    }

    public int getTotalAcc() {
        return totalAcc;
    }

    public void setTotalAcc(int totalAcc) {
        this.totalAcc = totalAcc;
    }

    public double getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(double totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }

    public double getTotalGroupValue() {
        return totalGroupValue;
    }

    public void setTotalGroupValue(double totalGroupValue) {
        this.totalGroupValue = totalGroupValue;
    }

    public double getTotalValuePreviousMnth() {
        return totalValuePreviousMnth;
    }

    public void setTotalValuePreviousMnth(double totalValuePreviousMnth) {
        this.totalValuePreviousMnth = totalValuePreviousMnth;
    }

    public double getTotalExposure() {
        return totalExposure;
    }

    public void setTotalExposure(double totalExposure) {
        this.totalExposure = totalExposure;
    }

    public double getTotalCl() {
        return totalCl;
    }

    public void setTotalCl(double totalCl) {
        this.totalCl = totalCl;
    }

    public double getTotalPar() {
        return totalPar;
    }

    public void setTotalPar(double totalPar) {
        this.totalPar = totalPar;
    }
}
