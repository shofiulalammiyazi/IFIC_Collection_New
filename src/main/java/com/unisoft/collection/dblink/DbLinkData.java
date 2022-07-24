package com.unisoft.collection.dblink;

import javax.persistence.*;

@Entity(name = "CMS_CUSTOM")
public class DbLinkData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FORACID")
    private String accountId;

    @Column(name = "CLR_BAL_AMT")
    private String clrBalAmt;

    @Column(name = "SCHM_TYPE")
    private String schemeType;

    @Column(name = "OVERDUE")
    private String overDue;

    @Column(name = "CUMULATIVE_REPAYMENT_TOTAL")
    private String cumulativeRepayMent;

    @Column(name = "PRIN_ADJ")
    private String prinAdj;

    @Column(name = "INT_ADJ")
    private String intAdj;

    @Column(name = "INT_RATE")
    private String intRate;

    @Column(name = "ATE")
    private String ate;

    @Column(name = "DPD")
    private String dpd;

    @Column(name = "SCHM_CODE")
    private String schemeCode;

    @Column(name = "FLOW_AMT")
    private String flowAmt;

    @Column(name = "SCS")
    private String scs;

    @Column(name = "UCS")
    private String ucs;

    @Column(name = "EMI_DAY")
    private String emiDay;

    @Column(name = "TENOR")
    private String tenor;

    public DbLinkData() {
    }

    public DbLinkData(String clrBalAmt, String schemeType, String overDue, String cumulativeRepayMent, String prinAdj, String intAdj, String intRate, String ate, String dpd, String schemeCode, String flowAmt, String scs, String ucs, String emiDay, String tenor) {
        this.clrBalAmt = clrBalAmt;
        this.schemeType = schemeType;
        this.overDue = overDue;
        this.cumulativeRepayMent = cumulativeRepayMent;
        this.prinAdj = prinAdj;
        this.intAdj = intAdj;
        this.intRate = intRate;
        this.ate = ate;
        this.dpd = dpd;
        this.schemeCode = schemeCode;
        this.flowAmt = flowAmt;
        this.scs = scs;
        this.ucs = ucs;
        this.emiDay = emiDay;
        this.tenor = tenor;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getClrBalAmt() {
        return clrBalAmt;
    }

    public void setClrBalAmt(String clrBalAmt) {
        this.clrBalAmt = clrBalAmt;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public String getOverDue() {
        return overDue;
    }

    public void setOverDue(String overDue) {
        this.overDue = overDue;
    }

    public String getCumulativeRepayMent() {
        return cumulativeRepayMent;
    }

    public void setCumulativeRepayMent(String cumulativeRepayMent) {
        this.cumulativeRepayMent = cumulativeRepayMent;
    }

    public String getPrinAdj() {
        return prinAdj;
    }

    public void setPrinAdj(String prinAdj) {
        this.prinAdj = prinAdj;
    }

    public String getIntAdj() {
        return intAdj;
    }

    public void setIntAdj(String intAdj) {
        this.intAdj = intAdj;
    }

    public String getIntRate() {
        return intRate;
    }

    public void setIntRate(String intRate) {
        this.intRate = intRate;
    }

    public String getAte() {
        return ate;
    }

    public void setAte(String ate) {
        this.ate = ate;
    }

    public String getDpd() {
        return dpd;
    }

    public void setDpd(String dpd) {
        this.dpd = dpd;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getFlowAmt() {
        return flowAmt;
    }

    public void setFlowAmt(String flowAmt) {
        this.flowAmt = flowAmt;
    }

    public String getScs() {
        return scs;
    }

    public void setScs(String scs) {
        this.scs = scs;
    }

    public String getUcs() {
        return ucs;
    }

    public void setUcs(String ucs) {
        this.ucs = ucs;
    }

    public String getEmiDay() {
        return emiDay;
    }

    public void setEmiDay(String emiDay) {
        this.emiDay = emiDay;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }
}
