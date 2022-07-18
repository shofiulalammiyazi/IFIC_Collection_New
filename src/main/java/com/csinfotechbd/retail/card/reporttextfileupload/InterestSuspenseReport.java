package com.csinfotechbd.retail.card.reporttextfileupload;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class InterestSuspenseReport extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerId;
    private String contractNo;
    private String contractAccount;
    private String accCurrency;
    private String outstanding;
    private String feeAndCharges;
    private String interest;
    private String atmTxn;
    private String posTxn;
    private String fundTransfer;
    private String balanceTransferUnpaid;
    private String cardCheque;
    private String fowardBalance;
    private String unadjustedDebitTxn;
    private String otherTxn;
    private String reversal;
    private String payment;
    private String unadjustedCreditTxn;

}
