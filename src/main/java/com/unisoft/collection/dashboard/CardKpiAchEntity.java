package com.unisoft.collection.dashboard;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class CardKpiAchEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern ="yyyy-MM-dd hh:mm:ss" )
    private Date updateDate;

    @Column(unique = true)
    private String finAccNo;

    private Double totalPayment=0d;

    private String cardAccNo;
    private String dealerPin;
    private String cusId;
    private int ageCode;
    private String cardsCategory;

    @Transient
    private Double amntPerPT;

    //Flow
    private Double flowAch = 0d;
    private Double flowPerformance= 0d;
    private Double flowShortFall= 0d;
    private Double flowWeight= 0d;
    private Double flowWeightedPerformance= 0d;
    private double flowTarget= 0d;
    private double flowTargetPerc= 0d;
    @Transient
    private double flowAmntPerDay= 0d;

    //save
    private Double saveAch= 0d;
    private Double savePerformance= 0d;
    private Double saveShortFall= 0d;
    private Double saveWeight= 0d;
    private Double saveWeightedPerformance= 0d;
    private double saveTarget= 0d;
    private double saveTargetPerc=0d;
    @Transient
    private double saveAmntPerDay= 0d;

    //back
    private Double backAch= 0d;
    private Double backPerformance= 0d;
    private Double backShortFall= 0d;
    private Double backWeight= 0d;
    private Double backWeightedPerformance= 0d;
    private double backTarget= 0d;
    private double backTargetPerc=0d;
    @Transient
    private double backAmntPerDay= 0d;

    //reg
    private Double regAch= 0d;
    private Double regPerformance= 0d;
    private Double regShortFall= 0d;
    private Double regWeight= 0d;
    private Double regWeightedPerformance= 0d;
    private double regTarget= 0d;
    private double regTargetPerc=0d;
    @Transient
    private double regAmntPerDay= 0d;

    //par
    private Double parAch= 0d;
    private Double parPerformance= 0d;
    private Double parShortFall= 0d;
    private Double parWeight= 0d;
    private Double parWeightedPerformance= 0d;
    private double parTarget= 0d;
    private double parTargetPerc=0d;
    @Transient
    private double parAmntPerDay= 0d;

    //npl
    private Double nplAch= 0d;
    private Double nplPerformance= 0d;
    private Double nplShortFall= 0d;
    private Double nplWeight= 0d;
    private Double nplWeightedPerformance= 0d;
    private double nplTarget= 0d;
    private double nplTargetPerc=0d;
    @Transient
    private double nplAmntPerDay= 0d;

    //raw
    private Double rawAch= 0d;
    private Double rawPerformance= 0d;
    private Double rawShortFall= 0d;
    private Double rawWeight= 0d;
    private Double rawWeightedPerformance= 0d;
    private double rawTarget= 0d;
    private double rawTargetPerc=0d;
    @Transient
    private double rawAmntPerDay= 0d;

    //min due
    private Double moMinDueAmnt=0d;
    private Double minAch= 0d;
    private Double minPerformance= 0d;
    private Double minShortFall= 0d;
    private Double minWeight= 0d;
    private Double minWeightedPerformance= 0d;
    private double minTarget= 0d;
    private double minTargetPerc=0d;
    @Transient
    private double minAmntPerDay= 0d;

    private String revCode="";
    private double revAmnt;
    private double newOutstanding;
    private boolean disputeIndicator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getFinAccNo() {
        return finAccNo;
    }

    public void setFinAccNo(String finAccNo) {
        this.finAccNo = finAccNo;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getCardAccNo() {
        return cardAccNo;
    }

    public void setCardAccNo(String cardAccNo) {
        this.cardAccNo = cardAccNo;
    }

    public String getDealerPin() {
        return dealerPin;
    }

    public void setDealerPin(String dealerPin) {
        this.dealerPin = dealerPin;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public int getAgeCode() {
        return ageCode;
    }

    public void setAgeCode(int ageCode) {
        this.ageCode = ageCode;
    }

    public String getCardsCategory() {
        return cardsCategory;
    }

    public void setCardsCategory(String cardsCategory) {
        this.cardsCategory = cardsCategory;
    }

    public Double getAmntPerPT() {
        return amntPerPT;
    }

    public void setAmntPerPT(Double amntPerPT) {
        this.amntPerPT = amntPerPT;
    }

    public Double getFlowAch() {
        return flowAch;
    }

    public void setFlowAch(Double flowAch) {
        this.flowAch = flowAch;
    }

    public Double getFlowPerformance() {
        return flowPerformance;
    }

    public void setFlowPerformance(Double flowPerformance) {
        this.flowPerformance = flowPerformance;
    }

    public Double getFlowShortFall() {
        return flowShortFall;
    }

    public void setFlowShortFall(Double flowShortFall) {
        this.flowShortFall = flowShortFall;
    }

    public Double getFlowWeight() {
        return flowWeight;
    }

    public void setFlowWeight(Double flowWeight) {
        this.flowWeight = flowWeight;
    }

    public Double getFlowWeightedPerformance() {
        return flowWeightedPerformance;
    }

    public void setFlowWeightedPerformance(Double flowWeightedPerformance) {
        this.flowWeightedPerformance = flowWeightedPerformance;
    }

    public double getFlowTarget() {
        return flowTarget;
    }

    public void setFlowTarget(double flowTarget) {
        this.flowTarget = flowTarget;
    }

    public double getFlowTargetPerc() {
        return flowTargetPerc;
    }

    public void setFlowTargetPerc(double flowTargetPerc) {
        this.flowTargetPerc = flowTargetPerc;
    }

    public double getFlowAmntPerDay() {
        return flowAmntPerDay;
    }

    public void setFlowAmntPerDay(double flowAmntPerDay) {
        this.flowAmntPerDay = flowAmntPerDay;
    }

    public Double getSaveAch() {
        return saveAch;
    }

    public void setSaveAch(Double saveAch) {
        this.saveAch = saveAch;
    }

    public Double getSavePerformance() {
        return savePerformance;
    }

    public void setSavePerformance(Double savePerformance) {
        this.savePerformance = savePerformance;
    }

    public Double getSaveShortFall() {
        return saveShortFall;
    }

    public void setSaveShortFall(Double saveShortFall) {
        this.saveShortFall = saveShortFall;
    }

    public Double getSaveWeight() {
        return saveWeight;
    }

    public void setSaveWeight(Double saveWeight) {
        this.saveWeight = saveWeight;
    }

    public Double getSaveWeightedPerformance() {
        return saveWeightedPerformance;
    }

    public void setSaveWeightedPerformance(Double saveWeightedPerformance) {
        this.saveWeightedPerformance = saveWeightedPerformance;
    }

    public double getSaveTarget() {
        return saveTarget;
    }

    public void setSaveTarget(double saveTarget) {
        this.saveTarget = saveTarget;
    }

    public double getSaveTargetPerc() {
        return saveTargetPerc;
    }

    public void setSaveTargetPerc(double saveTargetPerc) {
        this.saveTargetPerc = saveTargetPerc;
    }

    public double getSaveAmntPerDay() {
        return saveAmntPerDay;
    }

    public void setSaveAmntPerDay(double saveAmntPerDay) {
        this.saveAmntPerDay = saveAmntPerDay;
    }

    public Double getBackAch() {
        return backAch;
    }

    public void setBackAch(Double backAch) {
        this.backAch = backAch;
    }

    public Double getBackPerformance() {
        return backPerformance;
    }

    public void setBackPerformance(Double backPerformance) {
        this.backPerformance = backPerformance;
    }

    public Double getBackShortFall() {
        return backShortFall;
    }

    public void setBackShortFall(Double backShortFall) {
        this.backShortFall = backShortFall;
    }

    public Double getBackWeight() {
        return backWeight;
    }

    public void setBackWeight(Double backWeight) {
        this.backWeight = backWeight;
    }

    public Double getBackWeightedPerformance() {
        return backWeightedPerformance;
    }

    public void setBackWeightedPerformance(Double backWeightedPerformance) {
        this.backWeightedPerformance = backWeightedPerformance;
    }

    public double getBackTarget() {
        return backTarget;
    }

    public void setBackTarget(double backTarget) {
        this.backTarget = backTarget;
    }

    public double getBackTargetPerc() {
        return backTargetPerc;
    }

    public void setBackTargetPerc(double backTargetPerc) {
        this.backTargetPerc = backTargetPerc;
    }

    public double getBackAmntPerDay() {
        return backAmntPerDay;
    }

    public void setBackAmntPerDay(double backAmntPerDay) {
        this.backAmntPerDay = backAmntPerDay;
    }

    public Double getRegAch() {
        return regAch;
    }

    public void setRegAch(Double regAch) {
        this.regAch = regAch;
    }

    public Double getRegPerformance() {
        return regPerformance;
    }

    public void setRegPerformance(Double regPerformance) {
        this.regPerformance = regPerformance;
    }

    public Double getRegShortFall() {
        return regShortFall;
    }

    public void setRegShortFall(Double regShortFall) {
        this.regShortFall = regShortFall;
    }

    public Double getRegWeight() {
        return regWeight;
    }

    public void setRegWeight(Double regWeight) {
        this.regWeight = regWeight;
    }

    public Double getRegWeightedPerformance() {
        return regWeightedPerformance;
    }

    public void setRegWeightedPerformance(Double regWeightedPerformance) {
        this.regWeightedPerformance = regWeightedPerformance;
    }

    public double getRegTarget() {
        return regTarget;
    }

    public void setRegTarget(double regTarget) {
        this.regTarget = regTarget;
    }

    public double getRegTargetPerc() {
        return regTargetPerc;
    }

    public void setRegTargetPerc(double regTargetPerc) {
        this.regTargetPerc = regTargetPerc;
    }

    public double getRegAmntPerDay() {
        return regAmntPerDay;
    }

    public void setRegAmntPerDay(double regAmntPerDay) {
        this.regAmntPerDay = regAmntPerDay;
    }

    public Double getParAch() {
        return parAch;
    }

    public void setParAch(Double parAch) {
        this.parAch = parAch;
    }

    public Double getParPerformance() {
        return parPerformance;
    }

    public void setParPerformance(Double parPerformance) {
        this.parPerformance = parPerformance;
    }

    public Double getParShortFall() {
        return parShortFall;
    }

    public void setParShortFall(Double parShortFall) {
        this.parShortFall = parShortFall;
    }

    public Double getParWeight() {
        return parWeight;
    }

    public void setParWeight(Double parWeight) {
        this.parWeight = parWeight;
    }

    public Double getParWeightedPerformance() {
        return parWeightedPerformance;
    }

    public void setParWeightedPerformance(Double parWeightedPerformance) {
        this.parWeightedPerformance = parWeightedPerformance;
    }

    public double getParTarget() {
        return parTarget;
    }

    public void setParTarget(double parTarget) {
        this.parTarget = parTarget;
    }

    public double getParTargetPerc() {
        return parTargetPerc;
    }

    public void setParTargetPerc(double parTargetPerc) {
        this.parTargetPerc = parTargetPerc;
    }

    public double getParAmntPerDay() {
        return parAmntPerDay;
    }

    public void setParAmntPerDay(double parAmntPerDay) {
        this.parAmntPerDay = parAmntPerDay;
    }

    public Double getNplAch() {
        return nplAch;
    }

    public void setNplAch(Double nplAch) {
        this.nplAch = nplAch;
    }

    public Double getNplPerformance() {
        return nplPerformance;
    }

    public void setNplPerformance(Double nplPerformance) {
        this.nplPerformance = nplPerformance;
    }

    public Double getNplShortFall() {
        return nplShortFall;
    }

    public void setNplShortFall(Double nplShortFall) {
        this.nplShortFall = nplShortFall;
    }

    public Double getNplWeight() {
        return nplWeight;
    }

    public void setNplWeight(Double nplWeight) {
        this.nplWeight = nplWeight;
    }

    public Double getNplWeightedPerformance() {
        return nplWeightedPerformance;
    }

    public void setNplWeightedPerformance(Double nplWeightedPerformance) {
        this.nplWeightedPerformance = nplWeightedPerformance;
    }

    public double getNplTarget() {
        return nplTarget;
    }

    public void setNplTarget(double nplTarget) {
        this.nplTarget = nplTarget;
    }

    public double getNplTargetPerc() {
        return nplTargetPerc;
    }

    public void setNplTargetPerc(double nplTargetPerc) {
        this.nplTargetPerc = nplTargetPerc;
    }

    public double getNplAmntPerDay() {
        return nplAmntPerDay;
    }

    public void setNplAmntPerDay(double nplAmntPerDay) {
        this.nplAmntPerDay = nplAmntPerDay;
    }

    public Double getRawAch() {
        return rawAch;
    }

    public void setRawAch(Double rawAch) {
        this.rawAch = rawAch;
    }

    public Double getRawPerformance() {
        return rawPerformance;
    }

    public void setRawPerformance(Double rawPerformance) {
        this.rawPerformance = rawPerformance;
    }

    public Double getRawShortFall() {
        return rawShortFall;
    }

    public void setRawShortFall(Double rawShortFall) {
        this.rawShortFall = rawShortFall;
    }

    public Double getRawWeight() {
        return rawWeight;
    }

    public void setRawWeight(Double rawWeight) {
        this.rawWeight = rawWeight;
    }

    public Double getRawWeightedPerformance() {
        return rawWeightedPerformance;
    }

    public void setRawWeightedPerformance(Double rawWeightedPerformance) {
        this.rawWeightedPerformance = rawWeightedPerformance;
    }

    public double getRawTarget() {
        return rawTarget;
    }

    public void setRawTarget(double rawTarget) {
        this.rawTarget = rawTarget;
    }

    public double getRawTargetPerc() {
        return rawTargetPerc;
    }

    public void setRawTargetPerc(double rawTargetPerc) {
        this.rawTargetPerc = rawTargetPerc;
    }

    public double getRawAmntPerDay() {
        return rawAmntPerDay;
    }

    public void setRawAmntPerDay(double rawAmntPerDay) {
        this.rawAmntPerDay = rawAmntPerDay;
    }

    public Double getMoMinDueAmnt() {
        return moMinDueAmnt;
    }

    public void setMoMinDueAmnt(Double moMinDueAmnt) {
        this.moMinDueAmnt = moMinDueAmnt;
    }

    public Double getMinAch() {
        return minAch;
    }

    public void setMinAch(Double minAch) {
        this.minAch = minAch;
    }

    public Double getMinPerformance() {
        return minPerformance;
    }

    public void setMinPerformance(Double minPerformance) {
        this.minPerformance = minPerformance;
    }

    public Double getMinShortFall() {
        return minShortFall;
    }

    public void setMinShortFall(Double minShortFall) {
        this.minShortFall = minShortFall;
    }

    public Double getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(Double minWeight) {
        this.minWeight = minWeight;
    }

    public Double getMinWeightedPerformance() {
        return minWeightedPerformance;
    }

    public void setMinWeightedPerformance(Double minWeightedPerformance) {
        this.minWeightedPerformance = minWeightedPerformance;
    }

    public double getMinTarget() {
        return minTarget;
    }

    public void setMinTarget(double minTarget) {
        this.minTarget = minTarget;
    }

    public double getMinTargetPerc() {
        return minTargetPerc;
    }

    public void setMinTargetPerc(double minTargetPerc) {
        this.minTargetPerc = minTargetPerc;
    }

    public double getMinAmntPerDay() {
        return minAmntPerDay;
    }

    public void setMinAmntPerDay(double minAmntPerDay) {
        this.minAmntPerDay = minAmntPerDay;
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

    @Override
    public String toString() {
        return "CardKpiAchEntity{" +
                "id=" + id +
                ", updateDate=" + updateDate +
                ", finAccNo='" + finAccNo + '\'' +
                ", lastPayment=" + totalPayment +
                ", cardAccNo='" + cardAccNo + '\'' +
                ", dealerPin='" + dealerPin + '\'' +
                ", cusId='" + cusId + '\'' +
                ", ageCode=" + ageCode +
                ", cardsCategory='" + cardsCategory + '\'' +
                ", amntPerPT=" + amntPerPT +
                ", flowAch=" + flowAch +
                ", flowPerformance=" + flowPerformance +
                ", flowShortFall=" + flowShortFall +
                ", flowWeight=" + flowWeight +
                ", flowWeightedPerformance=" + flowWeightedPerformance +
                ", flowTarget=" + flowTarget +
                ", flowTargetPerc=" + flowTargetPerc +
                ", flowAmntPerDay=" + flowAmntPerDay +
                ", saveAch=" + saveAch +
                ", savePerformance=" + savePerformance +
                ", saveShortFall=" + saveShortFall +
                ", saveWeight=" + saveWeight +
                ", saveWeightedPerformance=" + saveWeightedPerformance +
                ", saveTarget=" + saveTarget +
                ", saveTargetPerc=" + saveTargetPerc +
                ", saveAmntPerDay=" + saveAmntPerDay +
                ", backAch=" + backAch +
                ", backPerformance=" + backPerformance +
                ", backShortFall=" + backShortFall +
                ", backWeight=" + backWeight +
                ", backWeightedPerformance=" + backWeightedPerformance +
                ", backTarget=" + backTarget +
                ", backTargetPerc=" + backTargetPerc +
                ", backAmntPerDay=" + backAmntPerDay +
                ", regAch=" + regAch +
                ", regPerformance=" + regPerformance +
                ", regShortFall=" + regShortFall +
                ", regWeight=" + regWeight +
                ", regWeightedPerformance=" + regWeightedPerformance +
                ", regTarget=" + regTarget +
                ", regTargetPerc=" + regTargetPerc +
                ", regAmntPerDay=" + regAmntPerDay +
                ", parAch=" + parAch +
                ", parPerformance=" + parPerformance +
                ", parShortFall=" + parShortFall +
                ", parWeight=" + parWeight +
                ", parWeightedPerformance=" + parWeightedPerformance +
                ", parTarget=" + parTarget +
                ", parTargetPerc=" + parTargetPerc +
                ", parAmntPerDay=" + parAmntPerDay +
                ", nplAch=" + nplAch +
                ", nplPerformance=" + nplPerformance +
                ", nplShortFall=" + nplShortFall +
                ", nplWeight=" + nplWeight +
                ", nplWeightedPerformance=" + nplWeightedPerformance +
                ", nplTarget=" + nplTarget +
                ", nplTargetPerc=" + nplTargetPerc +
                ", nplAmntPerDay=" + nplAmntPerDay +
                ", rawAch=" + rawAch +
                ", rawPerformance=" + rawPerformance +
                ", rawShortFall=" + rawShortFall +
                ", rawWeight=" + rawWeight +
                ", rawWeightedPerformance=" + rawWeightedPerformance +
                ", rawTarget=" + rawTarget +
                ", rawTargetPerc=" + rawTargetPerc +
                ", rawAmntPerDay=" + rawAmntPerDay +
                ", moMinDueAmnt=" + moMinDueAmnt +
                ", minAch=" + minAch +
                ", minPerformance=" + minPerformance +
                ", minShortFall=" + minShortFall +
                ", minWeight=" + minWeight +
                ", minWeightedPerformance=" + minWeightedPerformance +
                ", minTarget=" + minTarget +
                ", minTargetPerc=" + minTargetPerc +
                ", minAmntPerDay=" + minAmntPerDay +
                ", revCode='" + revCode + '\'' +
                ", revAmnt=" + revAmnt +
                ", newOutstanding=" + newOutstanding +
                ", disputeIndicator=" + disputeIndicator +
                '}';
    }
}
