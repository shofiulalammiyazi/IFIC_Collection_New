package com.csinfotechbd.collection.dashboard;
/*
  Created by Md. Monirul Islam on 1/19/2020
*/

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class CardBackendAccDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern ="yyyy-MM-dd hh:mm:ss" )
    private Date createdDate;
    private String creatorPin;

    @Column(unique = true)
    private String finAccNo;
    private String cardCustomerId;
    private String cardNo;
    private double allocationOutstanding;
    private double allocationMinDueAmnt;
    private int ageCode;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    private Date currentMnthPayDueDate;

    private String billingCycleDate;

    private double moRegularAmnt;
    private double moBackAmnt;
    private double moSaveAmnt;
    private double moFlowAmnt;
    private double moCashColletionAmnt;
    private double moPARRelReqAmnt;
    private double moNPLRelReqAmnt;
    private double moMinDueAmnt;

    private double targetSaveAmnt;
    private double targetBackAmnt;
    private double targetFlowAmnt;
    private double targetParAmnt;
    private double targetNplAmnt;
    private double targetOverDueAmnt;
    private double targetRegularAmnt;
    private double targetRawCol;
    private double targetMinDueAmnt;

    private double targetSavePerc;
    private double targetBackPerc;
    private double targetFlowPerc;
    private double targetParPerc;
    private double targetNplPerc;
    private double targetOverDuePerc;
    private double targetRegularPerc;
    private double targetRawColPerc;
    private double targetMinDuePerc;

    private double targetManSaveAmnt;
    private double targetManBackAmnt;
    private double targetManFlowAmnt;
    private double targetManParAmnt;
    private double targetManNplAmnt;
    private double targetManOverDueAmnt;
    private double targetManRegularAmnt;
    private double targetManRawCol;
    private double targetManMinDueAmnt;

    private double targetManSavePerc;
    private double targetManBackPerc;
    private double targetManFlowPerc;
    private double targetManParPerc;
    private double targetManNplPerc;
    private double targetManOverDuePerc;
    private double targetManRegularPerc;
    private double targetManRawColPerc;
    private double targetManMinDuePerc;

    private String mobileNo;
    private String revCode;
    private double revAmnt;
    private double newOutstanding;
    private boolean disputeIndicator;
    //private String disputeCode;


    public CardBackendAccDetailsEntity() {
    }

    public CardBackendAccDetailsEntity(Date createdDate, String creatorPin, String finAccNo, String cardCustomerId, String cardNo, double allocationOutstanding, double allocationMinDueAmnt, int ageCode, Date currentMnthPayDueDate, String billingCycleDate, double moRegularAmnt, double moBackAmnt, double moSaveAmnt, double moFlowAmnt, double moCashColletionAmnt, double moPARRelReqAmnt, double moNPLRelReqAmnt, double moMinDueAmnt, double targetSaveAmnt, double targetBackAmnt, double targetFlowAmnt, double targetParAmnt, double targetNplAmnt, double targetOverDueAmnt, double targetRegularAmnt, double targetRawCol, double targetMinDueAmnt, double targetSavePerc, double targetBackPerc, double targetFlowPerc, double targetParPerc, double targetNplPerc, double targetOverDuePerc, double targetRegularPerc, double targetRawColPerc, double targetMinDuePerc, double targetManSaveAmnt, double targetManBackAmnt, double targetManFlowAmnt, double targetManParAmnt, double targetManNplAmnt, double targetManOverDueAmnt, double targetManRegularAmnt, double targetManRawCol, double targetManMinDueAmnt, double targetManSavePerc, double targetManBackPerc, double targetManFlowPerc, double targetManParPerc, double targetManNplPerc, double targetManOverDuePerc, double targetManRegularPerc, double targetManRawColPerc, double targetManMinDuePerc, String mobileNo, String revCode, double revAmnt, double newOutstanding, boolean disputeIndicator) {
        this.createdDate = createdDate;
        this.creatorPin = creatorPin;
        this.finAccNo = finAccNo;
        this.cardCustomerId = cardCustomerId;
        this.cardNo = cardNo;
        this.allocationOutstanding = allocationOutstanding;
        this.allocationMinDueAmnt = allocationMinDueAmnt;
        this.ageCode = ageCode;
        this.currentMnthPayDueDate = currentMnthPayDueDate;
        this.billingCycleDate = billingCycleDate;
        this.moRegularAmnt = moRegularAmnt;
        this.moBackAmnt = moBackAmnt;
        this.moSaveAmnt = moSaveAmnt;
        this.moFlowAmnt = moFlowAmnt;
        this.moCashColletionAmnt = moCashColletionAmnt;
        this.moPARRelReqAmnt = moPARRelReqAmnt;
        this.moNPLRelReqAmnt = moNPLRelReqAmnt;
        this.moMinDueAmnt = moMinDueAmnt;
        this.targetSaveAmnt = targetSaveAmnt;
        this.targetBackAmnt = targetBackAmnt;
        this.targetFlowAmnt = targetFlowAmnt;
        this.targetParAmnt = targetParAmnt;
        this.targetNplAmnt = targetNplAmnt;
        this.targetOverDueAmnt = targetOverDueAmnt;
        this.targetRegularAmnt = targetRegularAmnt;
        this.targetRawCol = targetRawCol;
        this.targetMinDueAmnt = targetMinDueAmnt;
        this.targetSavePerc = targetSavePerc;
        this.targetBackPerc = targetBackPerc;
        this.targetFlowPerc = targetFlowPerc;
        this.targetParPerc = targetParPerc;
        this.targetNplPerc = targetNplPerc;
        this.targetOverDuePerc = targetOverDuePerc;
        this.targetRegularPerc = targetRegularPerc;
        this.targetRawColPerc = targetRawColPerc;
        this.targetMinDuePerc = targetMinDuePerc;
        this.targetManSaveAmnt = targetManSaveAmnt;
        this.targetManBackAmnt = targetManBackAmnt;
        this.targetManFlowAmnt = targetManFlowAmnt;
        this.targetManParAmnt = targetManParAmnt;
        this.targetManNplAmnt = targetManNplAmnt;
        this.targetManOverDueAmnt = targetManOverDueAmnt;
        this.targetManRegularAmnt = targetManRegularAmnt;
        this.targetManRawCol = targetManRawCol;
        this.targetManMinDueAmnt = targetManMinDueAmnt;
        this.targetManSavePerc = targetManSavePerc;
        this.targetManBackPerc = targetManBackPerc;
        this.targetManFlowPerc = targetManFlowPerc;
        this.targetManParPerc = targetManParPerc;
        this.targetManNplPerc = targetManNplPerc;
        this.targetManOverDuePerc = targetManOverDuePerc;
        this.targetManRegularPerc = targetManRegularPerc;
        this.targetManRawColPerc = targetManRawColPerc;
        this.targetManMinDuePerc = targetManMinDuePerc;
        this.mobileNo = mobileNo;
        this.revCode = revCode;
        this.revAmnt = revAmnt;
        this.newOutstanding = newOutstanding;
        this.disputeIndicator = disputeIndicator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatorPin() {
        return creatorPin;
    }

    public void setCreatorPin(String creatorPin) {
        this.creatorPin = creatorPin;
    }

    public String getFinAccNo() {
        return finAccNo;
    }

    public void setFinAccNo(String finAccNo) {
        this.finAccNo = finAccNo;
    }

    public String getCardCustomerId() {
        return cardCustomerId;
    }

    public void setCardCustomerId(String cardCustomerId) {
        this.cardCustomerId = cardCustomerId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public double getAllocationOutstanding() {
        return allocationOutstanding;
    }

    public void setAllocationOutstanding(double allocationOutstanding) {
        this.allocationOutstanding = allocationOutstanding;
    }

    public double getAllocationMinDueAmnt() {
        return allocationMinDueAmnt;
    }

    public void setAllocationMinDueAmnt(double allocationMinDueAmnt) {
        this.allocationMinDueAmnt = allocationMinDueAmnt;
    }

    public int getAgeCode() {
        return ageCode;
    }

    public void setAgeCode(int ageCode) {
        this.ageCode = ageCode;
    }

    public Date getCurrentMnthPayDueDate() {
        return currentMnthPayDueDate;
    }

    public void setCurrentMnthPayDueDate(Date currentMnthPayDueDate) {
        this.currentMnthPayDueDate = currentMnthPayDueDate;
    }

    public String getBillingCycleDate() {
        return billingCycleDate;
    }

    public void setBillingCycleDate(String billingCycleDate) {
        this.billingCycleDate = billingCycleDate;
    }

    public double getMoRegularAmnt() {
        return moRegularAmnt;
    }

    public void setMoRegularAmnt(double moRegularAmnt) {
        this.moRegularAmnt = moRegularAmnt;
    }

    public double getMoBackAmnt() {
        return moBackAmnt;
    }

    public void setMoBackAmnt(double moBackAmnt) {
        this.moBackAmnt = moBackAmnt;
    }

    public double getMoSaveAmnt() {
        return moSaveAmnt;
    }

    public void setMoSaveAmnt(double moSaveAmnt) {
        this.moSaveAmnt = moSaveAmnt;
    }

    public double getMoFlowAmnt() {
        return moFlowAmnt;
    }

    public void setMoFlowAmnt(double moFlowAmnt) {
        this.moFlowAmnt = moFlowAmnt;
    }

    public double getMoCashColletionAmnt() {
        return moCashColletionAmnt;
    }

    public void setMoCashColletionAmnt(double moCashColletionAmnt) {
        this.moCashColletionAmnt = moCashColletionAmnt;
    }

    public double getMoPARRelReqAmnt() {
        return moPARRelReqAmnt;
    }

    public void setMoPARRelReqAmnt(double moPARRelReqAmnt) {
        this.moPARRelReqAmnt = moPARRelReqAmnt;
    }

    public double getMoNPLRelReqAmnt() {
        return moNPLRelReqAmnt;
    }

    public void setMoNPLRelReqAmnt(double moNPLRelReqAmnt) {
        this.moNPLRelReqAmnt = moNPLRelReqAmnt;
    }

    public double getMoMinDueAmnt() {
        return moMinDueAmnt;
    }

    public void setMoMinDueAmnt(double moMinDueAmnt) {
        this.moMinDueAmnt = moMinDueAmnt;
    }

    public double getTargetSaveAmnt() {
        return targetSaveAmnt;
    }

    public void setTargetSaveAmnt(double targetSaveAmnt) {
        this.targetSaveAmnt = targetSaveAmnt;
    }

    public double getTargetBackAmnt() {
        return targetBackAmnt;
    }

    public void setTargetBackAmnt(double targetBackAmnt) {
        this.targetBackAmnt = targetBackAmnt;
    }

    public double getTargetFlowAmnt() {
        return targetFlowAmnt;
    }

    public void setTargetFlowAmnt(double targetFlowAmnt) {
        this.targetFlowAmnt = targetFlowAmnt;
    }

    public double getTargetParAmnt() {
        return targetParAmnt;
    }

    public void setTargetParAmnt(double targetParAmnt) {
        this.targetParAmnt = targetParAmnt;
    }

    public double getTargetNplAmnt() {
        return targetNplAmnt;
    }

    public void setTargetNplAmnt(double targetNplAmnt) {
        this.targetNplAmnt = targetNplAmnt;
    }

    public double getTargetOverDueAmnt() {
        return targetOverDueAmnt;
    }

    public void setTargetOverDueAmnt(double targetOverDueAmnt) {
        this.targetOverDueAmnt = targetOverDueAmnt;
    }

    public double getTargetRegularAmnt() {
        return targetRegularAmnt;
    }

    public void setTargetRegularAmnt(double targetRegularAmnt) {
        this.targetRegularAmnt = targetRegularAmnt;
    }

    public double getTargetRawCol() {
        return targetRawCol;
    }

    public void setTargetRawCol(double targetRawCol) {
        this.targetRawCol = targetRawCol;
    }

    public double getTargetMinDueAmnt() {
        return targetMinDueAmnt;
    }

    public void setTargetMinDueAmnt(double targetMinDueAmnt) {
        this.targetMinDueAmnt = targetMinDueAmnt;
    }

    public double getTargetSavePerc() {
        return targetSavePerc;
    }

    public void setTargetSavePerc(double targetSavePerc) {
        this.targetSavePerc = targetSavePerc;
    }

    public double getTargetBackPerc() {
        return targetBackPerc;
    }

    public void setTargetBackPerc(double targetBackPerc) {
        this.targetBackPerc = targetBackPerc;
    }

    public double getTargetFlowPerc() {
        return targetFlowPerc;
    }

    public void setTargetFlowPerc(double targetFlowPerc) {
        this.targetFlowPerc = targetFlowPerc;
    }

    public double getTargetParPerc() {
        return targetParPerc;
    }

    public void setTargetParPerc(double targetParPerc) {
        this.targetParPerc = targetParPerc;
    }

    public double getTargetNplPerc() {
        return targetNplPerc;
    }

    public void setTargetNplPerc(double targetNplPerc) {
        this.targetNplPerc = targetNplPerc;
    }

    public double getTargetOverDuePerc() {
        return targetOverDuePerc;
    }

    public void setTargetOverDuePerc(double targetOverDuePerc) {
        this.targetOverDuePerc = targetOverDuePerc;
    }

    public double getTargetRegularPerc() {
        return targetRegularPerc;
    }

    public void setTargetRegularPerc(double targetRegularPerc) {
        this.targetRegularPerc = targetRegularPerc;
    }

    public double getTargetRawColPerc() {
        return targetRawColPerc;
    }

    public void setTargetRawColPerc(double targetRawColPerc) {
        this.targetRawColPerc = targetRawColPerc;
    }

    public double getTargetMinDuePerc() {
        return targetMinDuePerc;
    }

    public void setTargetMinDuePerc(double targetMinDuePerc) {
        this.targetMinDuePerc = targetMinDuePerc;
    }

    public double getTargetManSaveAmnt() {
        return targetManSaveAmnt;
    }

    public void setTargetManSaveAmnt(double targetManSaveAmnt) {
        this.targetManSaveAmnt = targetManSaveAmnt;
    }

    public double getTargetManBackAmnt() {
        return targetManBackAmnt;
    }

    public void setTargetManBackAmnt(double targetManBackAmnt) {
        this.targetManBackAmnt = targetManBackAmnt;
    }

    public double getTargetManFlowAmnt() {
        return targetManFlowAmnt;
    }

    public void setTargetManFlowAmnt(double targetManFlowAmnt) {
        this.targetManFlowAmnt = targetManFlowAmnt;
    }

    public double getTargetManParAmnt() {
        return targetManParAmnt;
    }

    public void setTargetManParAmnt(double targetManParAmnt) {
        this.targetManParAmnt = targetManParAmnt;
    }

    public double getTargetManNplAmnt() {
        return targetManNplAmnt;
    }

    public void setTargetManNplAmnt(double targetManNplAmnt) {
        this.targetManNplAmnt = targetManNplAmnt;
    }

    public double getTargetManOverDueAmnt() {
        return targetManOverDueAmnt;
    }

    public void setTargetManOverDueAmnt(double targetManOverDueAmnt) {
        this.targetManOverDueAmnt = targetManOverDueAmnt;
    }

    public double getTargetManRegularAmnt() {
        return targetManRegularAmnt;
    }

    public void setTargetManRegularAmnt(double targetManRegularAmnt) {
        this.targetManRegularAmnt = targetManRegularAmnt;
    }

    public double getTargetManRawCol() {
        return targetManRawCol;
    }

    public void setTargetManRawCol(double targetManRawCol) {
        this.targetManRawCol = targetManRawCol;
    }

    public double getTargetManMinDueAmnt() {
        return targetManMinDueAmnt;
    }

    public void setTargetManMinDueAmnt(double targetManMinDueAmnt) {
        this.targetManMinDueAmnt = targetManMinDueAmnt;
    }

    public double getTargetManSavePerc() {
        return targetManSavePerc;
    }

    public void setTargetManSavePerc(double targetManSavePerc) {
        this.targetManSavePerc = targetManSavePerc;
    }

    public double getTargetManBackPerc() {
        return targetManBackPerc;
    }

    public void setTargetManBackPerc(double targetManBackPerc) {
        this.targetManBackPerc = targetManBackPerc;
    }

    public double getTargetManFlowPerc() {
        return targetManFlowPerc;
    }

    public void setTargetManFlowPerc(double targetManFlowPerc) {
        this.targetManFlowPerc = targetManFlowPerc;
    }

    public double getTargetManParPerc() {
        return targetManParPerc;
    }

    public void setTargetManParPerc(double targetManParPerc) {
        this.targetManParPerc = targetManParPerc;
    }

    public double getTargetManNplPerc() {
        return targetManNplPerc;
    }

    public void setTargetManNplPerc(double targetManNplPerc) {
        this.targetManNplPerc = targetManNplPerc;
    }

    public double getTargetManOverDuePerc() {
        return targetManOverDuePerc;
    }

    public void setTargetManOverDuePerc(double targetManOverDuePerc) {
        this.targetManOverDuePerc = targetManOverDuePerc;
    }

    public double getTargetManRegularPerc() {
        return targetManRegularPerc;
    }

    public void setTargetManRegularPerc(double targetManRegularPerc) {
        this.targetManRegularPerc = targetManRegularPerc;
    }

    public double getTargetManRawColPerc() {
        return targetManRawColPerc;
    }

    public void setTargetManRawColPerc(double targetManRawColPerc) {
        this.targetManRawColPerc = targetManRawColPerc;
    }

    public double getTargetManMinDuePerc() {
        return targetManMinDuePerc;
    }

    public void setTargetManMinDuePerc(double targetManMinDuePerc) {
        this.targetManMinDuePerc = targetManMinDuePerc;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRevCode() {
        return revCode;
    }

    public void setRevCode(String revCode) {
        this.revCode = revCode;
    }

    public double getRevAmnt() {
        return revAmnt;
    }

    public void setRevAmnt(double revAmnt) {
        this.revAmnt = revAmnt;
    }

    public double getNewOutstanding() {
        return newOutstanding;
    }

    public void setNewOutstanding(double newOutstanding) {
        this.newOutstanding = newOutstanding;
    }

    public boolean isDisputeIndicator() {
        return disputeIndicator;
    }

    public void setDisputeIndicator(boolean disputeIndicator) {
        this.disputeIndicator = disputeIndicator;
    }
}
