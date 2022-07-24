package com.unisoft.collection.connectplus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "RDMS_DATADUMPSTORENEW")
public class ConnectPlusNew {

    @Id
    @Column(name = "LOANID")
    private String loanId;

    @Column(name = "APPLICANTDESIGNATION")
    private String applicantDesignation;

    @Column(name = "APPDBR")
    private String appDbr;

    @Column(name = "APPCASHSALARYPORTION")
    private String appCashSalaryPortion;

    @Column(name = "COAPPNAME3")
    private String coAppName3;

    @Column(name = "COAPPHOMECELL1")
    private String coAppHomeCell1;

    @Column(name = "COAPPHOMECELL2")
    private String coAppHomeCell2;

    @Column(name = "COAPPHOMECELL3")
    private String coAppHomeCell3;

    @Column(name = "REFHOMECELL1")
    private String refHomeCell1;

    @Column(name = "REFHOMECELL2")
    private String refHomeCell2;

    @Column(name = "PROPERTYPRICE")
    private String propertyPrice;

    @Column(name = "REGISTRATIONTYPE")
    private String registrationType;

    @Column(name = "TOTALFLATPRICE")
    private String totalFlatPrice;

    @Column(name = "DEVELOPERCATEGORY")
    private String develooperCategory;

    @Column(name = "CC")
    private String cc;

    @Column(name = "TYPEOFVEHICLE")
    private String typeOfVechile;

    public ConnectPlusNew() {
    }

    public ConnectPlusNew(String loanId, String applicantDesignation, String appDbr, String appCashSalaryPortion, String coAppName3, String coAppHomeCell1, String coAppHomeCell2, String coAppHomeCell3, String refHomeCell1, String refHomeCell2, String propertyPrice, String registrationType, String totalFlatPrice, String develooperCategory, String cc, String typeOfVechile) {
        this.loanId = loanId;
        this.applicantDesignation = applicantDesignation;
        this.appDbr = appDbr;
        this.appCashSalaryPortion = appCashSalaryPortion;
        this.coAppName3 = coAppName3;
        this.coAppHomeCell1 = coAppHomeCell1;
        this.coAppHomeCell2 = coAppHomeCell2;
        this.coAppHomeCell3 = coAppHomeCell3;
        this.refHomeCell1 = refHomeCell1;
        this.refHomeCell2 = refHomeCell2;
        this.propertyPrice = propertyPrice;
        this.registrationType = registrationType;
        this.totalFlatPrice = totalFlatPrice;
        this.develooperCategory = develooperCategory;
        this.cc = cc;
        this.typeOfVechile = typeOfVechile;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getApplicantDesignation() {
        return applicantDesignation;
    }

    public void setApplicantDesignation(String applicantDesignation) {
        this.applicantDesignation = applicantDesignation;
    }

    public String getAppDbr() {
        return appDbr;
    }

    public void setAppDbr(String appDbr) {
        this.appDbr = appDbr;
    }

    public String getAppCashSalaryPortion() {
        return appCashSalaryPortion;
    }

    public void setAppCashSalaryPortion(String appCashSalaryPortion) {
        this.appCashSalaryPortion = appCashSalaryPortion;
    }

    public String getCoAppName3() {
        return coAppName3;
    }

    public void setCoAppName3(String coAppName3) {
        this.coAppName3 = coAppName3;
    }

    public String getCoAppHomeCell1() {
        return coAppHomeCell1;
    }

    public void setCoAppHomeCell1(String coAppHomeCell1) {
        this.coAppHomeCell1 = coAppHomeCell1;
    }

    public String getCoAppHomeCell2() {
        return coAppHomeCell2;
    }

    public void setCoAppHomeCell2(String coAppHomeCell2) {
        this.coAppHomeCell2 = coAppHomeCell2;
    }

    public String getCoAppHomeCell3() {
        return coAppHomeCell3;
    }

    public void setCoAppHomeCell3(String coAppHomeCell3) {
        this.coAppHomeCell3 = coAppHomeCell3;
    }

    public String getRefHomeCell1() {
        return refHomeCell1;
    }

    public void setRefHomeCell1(String refHomeCell1) {
        this.refHomeCell1 = refHomeCell1;
    }

    public String getRefHomeCell2() {
        return refHomeCell2;
    }

    public void setRefHomeCell2(String refHomeCell2) {
        this.refHomeCell2 = refHomeCell2;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getTotalFlatPrice() {
        return totalFlatPrice;
    }

    public void setTotalFlatPrice(String totalFlatPrice) {
        this.totalFlatPrice = totalFlatPrice;
    }

    public String getDevelooperCategory() {
        return develooperCategory;
    }

    public void setDevelooperCategory(String develooperCategory) {
        this.develooperCategory = develooperCategory;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getTypeOfVechile() {
        return typeOfVechile;
    }

    public void setTypeOfVechile(String typeOfVechile) {
        this.typeOfVechile = typeOfVechile;
    }
}
