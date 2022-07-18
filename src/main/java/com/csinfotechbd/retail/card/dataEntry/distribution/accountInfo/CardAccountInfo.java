package com.csinfotechbd.retail.card.dataEntry.distribution.accountInfo;
/*
Created by Monirul Islam at 7/21/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "CARD_ACCOUNT_INFO",
        uniqueConstraints = @UniqueConstraint(columnNames = {"CARD_ACCOUNT_BASIC_INFO_ID"})
)
public class CardAccountInfo extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private CardAccountBasicInfo cardAccountBasicInfo;
    private String prdGrp;
    private String moAccLimit;
    private String moAccOutstanding;
    private String moAccMinPayment;
    private String moAgeCode;
    private String moBillCycle;
    private String moStatus_Cd;
    private String lasOneMonthPayment;
    private Double bdtLimit;    // domestic
    private Double internationalLimit;
    private Double bdtOutstanding;
    private Double internationalOutstanding;
    private Double bdtMinDue;
    private Double internationalMinDue;

    //new field added by shanto for card file upload
    private String clientId;
    private String contractNo;
    private String contState;
    private String domAccountNo;
    /*private String domLimitAmt;
    private String domAccountNo;
    private String domOutstandingAmt;
    private String domMinDueAmt;*/
    private String autoPayBdt;
    private String autoPayAcBdt;
   /* private String intAccountNo;
    private String intLimitAmt;
    private String intOutstandingAmt;
    private String intMinDueAmt;*/
   private String intAccountNo;
    private String autoPayUsd;
    private String autoPayAcUsd;
    private String riskGroup;
    private String state;
    private String noOfDays;
    private String age;
    private String cardType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date statementDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date dueDate;

    public CardAccountInfo() {
    }

    public CardAccountInfo(CardAccountBasicInfo cardAccountBasicInfo, String prdGrp, String moAccLimit, String moAccOutstanding, String moAccMinPayment, String moAgeCode, String moBillCycle, String moStatus_Cd, String lasOneMonthPayment) {
        this.cardAccountBasicInfo = cardAccountBasicInfo;
        this.prdGrp = prdGrp;
        this.moAccLimit = moAccLimit;
        this.moAccOutstanding = moAccOutstanding;
        this.moAccMinPayment = moAccMinPayment;
        this.moAgeCode = moAgeCode;
        this.moBillCycle = moBillCycle;
        this.moStatus_Cd = moStatus_Cd;
        this.lasOneMonthPayment = lasOneMonthPayment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardAccountBasicInfo getCardAccountBasicInfo() {
        return cardAccountBasicInfo;
    }

    public void setCardAccountBasicInfo(CardAccountBasicInfo cardAccountBasicInfo) {
        this.cardAccountBasicInfo = cardAccountBasicInfo;
    }

    public String getPrdGrp() {
        return prdGrp;
    }

    public void setPrdGrp(String prdGrp) {
        this.prdGrp = prdGrp;
    }

    public String getMoAccLimit() {
        return moAccLimit;
    }

    public void setMoAccLimit(String moAccLimit) {
        this.moAccLimit = moAccLimit;
    }

    public String getMoAccOutstanding() {
        return moAccOutstanding;
    }

    public void setMoAccOutstanding(String moAccOutstanding) {
        this.moAccOutstanding = moAccOutstanding;
    }

    public String getMoAccMinPayment() {
        return moAccMinPayment;
    }

    public void setMoAccMinPayment(String moAccMinPayment) {
        this.moAccMinPayment = moAccMinPayment;
    }

    public String getMoAgeCode() {
        return moAgeCode;
    }

    public void setMoAgeCode(String moAgeCode) {
        this.moAgeCode = moAgeCode;
    }

    public String getMoBillCycle() {
        return moBillCycle;
    }

    public void setMoBillCycle(String moBillCycle) {
        this.moBillCycle = moBillCycle;
    }

    public String getMoStatus_Cd() {
        return moStatus_Cd;
    }

    public void setMoStatus_Cd(String moStatus_Cd) {
        this.moStatus_Cd = moStatus_Cd;
    }

    public String getLasOneMonthPayment() {
        return lasOneMonthPayment;
    }

    public void setLasOneMonthPayment(String lasOneMonthPayment) {
        this.lasOneMonthPayment = lasOneMonthPayment;
    }
}
