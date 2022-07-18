package com.csinfotechbd.retail.card.reporttextfileupload;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class ApproveTransactionsUnbilledTransactionsPayment extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contractNo;
    private String postDate;
    private String txnDate;
    private String acNo;
    private String cardNo;
    private String billingCurrenc;
    private String billingAmount;
    private String acqCountry;
    private String txnAmount;
    private String txnCurrency;
    private String txnDescription;
    private String txnType;
    private String approvalCode;
    private String termId;
    private String retailerId;
    private String mcc;
    private String termLocation;
    private String posEntry;
    private String sIndicator;

}
