package com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo;

import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/*
Created by   Islam at 9/8/2019
*/
@Data
@Entity
public class AutoDistributionCardTemp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private CardAccountBasicInfo cardAccountBasicInfo;
    private String supervisorPin;
    private String supervisorName;
    private String dealerPin;
    private String productGroup;
    private String ageCode;
    private String samAccount;
    private String writeOffAccount;
    private String dealerName;
    private String vvip;
    private String plasticCd;
    private String billingCycle;
    private String customerName;
    private String outstandingAmount;
    private String secureCard;
    @Temporal(TemporalType.DATE)
    private Date statusDate;
}
