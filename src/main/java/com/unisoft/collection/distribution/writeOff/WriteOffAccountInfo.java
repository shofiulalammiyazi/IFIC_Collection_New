package com.unisoft.collection.distribution.writeOff;
/*
Created by   Islam at 8/3/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class WriteOffAccountInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String loanAccountNo;

    private String contractId;

    private String loanAccountName;

    private String cardName;

    private String customerId;

    private String schemeCode;

    private String prductUnit;

    @Temporal(value = TemporalType.DATE)
    private Date fileDateNiAct;

    private String caseAmountNiAct;

    @Temporal(value = TemporalType.DATE)
    private Date fileDateArthorin;

    private String caseAmountArthorin;

    @Temporal(value = TemporalType.DATE)
    private Date fileDatePenalCode;

    private String caseAmountPenalCode;

    @Temporal(value = TemporalType.DATE)
    private Date nextAskingDate;

    private String caseStatus;

    private String caseDealingPerson;

    private String lawyerName;

    private String courtName;

    private String productType;

    private String dpdBucket;

    private String ageCode;

    private String presentOutstanding;

    @Temporal(value = TemporalType.DATE)
    private Date auctionNoticeDate;

    @Temporal(value = TemporalType.DATE)
    private Date nPublicDate;

    @Temporal(value = TemporalType.DATE)
    private Date auctionDate;

    private String writeOffAccount;

    @OneToOne
    private LoanAccountBasicInfo loanAccountBasicInfo;

    @OneToOne
    private CardAccountBasicInfo cardAccountBasicInfo;
}
