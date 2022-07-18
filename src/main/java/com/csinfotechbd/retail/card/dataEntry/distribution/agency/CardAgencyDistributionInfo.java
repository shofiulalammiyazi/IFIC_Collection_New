package com.csinfotechbd.retail.card.dataEntry.distribution.agency;
/*
Created by Monirul Islam at 7/22/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.csinfotechbd.collection.distribution.loan.LoanDualEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class CardAgencyDistributionInfo extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String outstanding;
    private String overdue;
    private String ageCode;
    private String cashCollection;
    private String agencyName;
    private String agentName;
    private String mobileNo;
    private String handoverDate;
    private String expireDate;
    private String monitorStatus;
    @Enumerated(EnumType.STRING)
    private LoanDualEnum loanDualEnum;
    @OneToOne
    private CardAccountBasicInfo cardAccountBasicInfo;
    private String billingCycle;
    private String plasticCd;
    @Temporal(value = TemporalType.DATE)
    private Date statusDate;
    private String vvip;
    private String secureCard;
    private String productGroup;

    private Double totalOutstanding;
    private Double bdtUsdConversionRate;

    private String clientId;
    private String stateCode;

    private String latest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public String getAgeCode() {
        return ageCode;
    }

    public void setAgeCode(String ageCode) {
        this.ageCode = ageCode;
    }

    public String getCashCollection() {
        return cashCollection;
    }

    public void setCashCollection(String cashCollection) {
        this.cashCollection = cashCollection;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getHandoverDate() {
        return handoverDate;
    }

    public void setHandoverDate(String handoverDate) {
        this.handoverDate = handoverDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(String monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    public LoanDualEnum getLoanDualEnum() {
        return loanDualEnum;
    }

    public void setLoanDualEnum(LoanDualEnum loanDualEnum) {
        this.loanDualEnum = loanDualEnum;
    }

    public CardAccountBasicInfo getCardAccountBasicInfo() {
        return cardAccountBasicInfo;
    }

    public void setCardAccountBasicInfo(CardAccountBasicInfo cardAccountBasicInfo) {
        this.cardAccountBasicInfo = cardAccountBasicInfo;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public String getPlasticCd() {
        return plasticCd;
    }

    public void setPlasticCd(String plasticCd) {
        this.plasticCd = plasticCd;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getVvip() {
        return vvip;
    }

    public void setVvip(String vvip) {
        this.vvip = vvip;
    }

    public String getSecureCard() {
        return secureCard;
    }

    public void setSecureCard(String secureCard) {
        this.secureCard = secureCard;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }

}
