package com.unisoft.collection.distribution.samAccountHandover;
/*
Created by   Islam at 8/1/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class SamAccountHandoverInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String loanAccountNo;

    private String accountName;

    private String cardNo;

    private String cardName;

    private String customerId;

    private String schemaCode;

    private String productUnit;

    @Temporal(value = TemporalType.DATE)
    private Date samRecievedDate;

    @Temporal(value = TemporalType.DATE)
    private Date disbursementDate;

    @Temporal(value = TemporalType.DATE)
    private Date embossedDate;

    private String outstandingAmount;

    private String emiAccount;

    private String overDueAmount;

    private String dueAmount;

    private String ageCode;

    private String dpdBucket;

    private String letterType;

    private String remarks;

    @Temporal(value = TemporalType.DATE)
    private Date writeOffDate;

    private String writeOffOrNot;

    private String samAccount;

    private String billingCycle;

    private String plasticCd;

    private String productGroup;

    @Temporal(value = TemporalType.DATE)
    private Date statusDate;

    private String secureCard;

    private String vvip;

    private String latest;

    private Date firstLegalLetterNoticeDate;

    private Double writeOffAmount;

    private Date fileDateNiAct;

    private Double caseAmountNiAct;

    private Date fileDateArtharin;

    private Double caseAmountArtharin;

    private Date fileDatePenalCode;

    private Double caseAmountPenalCode;

    private Date nextAskingDate;

    private String caseStatus;

    private String caseDealingPerson;

    private String lawyerName;

    private String courtName;

    private String presentOutstanding;

    private Date auctionNoticeDate;

    private Date newspaperPublicationDate;

    private Date auctionDate;

    @OneToOne
    private LoanAccountBasicInfo loanAccountBasicInfo;

    @OneToOne
    private CardAccountBasicInfo cardAccountBasicInfo;
}
