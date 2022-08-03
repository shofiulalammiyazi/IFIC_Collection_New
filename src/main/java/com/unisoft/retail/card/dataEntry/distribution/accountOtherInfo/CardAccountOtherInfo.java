package com.unisoft.retail.card.dataEntry.distribution.accountOtherInfo;
/*
Created by   Islam at 7/21/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CARD_ACCOUNT_OTHER_INFO",
        uniqueConstraints = @UniqueConstraint(columnNames = {"CARD_ACCOUNT_BASIC_INFO_ID"})
)
public class CardAccountOtherInfo extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private CardAccountBasicInfo cardAccountBasicInfo;
    private String notificationStatus;
    private String modeOfPayment;
    private String orgType;
    private String companyCategory;
    private String vvip;
    private String securedCreditcard;

    public CardAccountOtherInfo() {
    }

    public CardAccountOtherInfo(CardAccountBasicInfo cardAccountBasicInfo, String notificationStatus, String modeOfPayment, String orgType, String companyCategory, String vvip, String securedCreditcard) {
        this.cardAccountBasicInfo = cardAccountBasicInfo;
        this.notificationStatus = notificationStatus;
        this.modeOfPayment = modeOfPayment;
        this.orgType = orgType;
        this.companyCategory = companyCategory;
        this.vvip = vvip;
        this.securedCreditcard = securedCreditcard;
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

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(String companyCategory) {
        this.companyCategory = companyCategory;
    }

    public String getVvip() {
        return vvip;
    }

    public void setVvip(String vvip) {
        this.vvip = vvip;
    }

    public String getSecuredCreditcard() {
        return securedCreditcard;
    }

    public void setSecuredCreditcard(String securedCreditcard) {
        this.securedCreditcard = securedCreditcard;
    }
}
