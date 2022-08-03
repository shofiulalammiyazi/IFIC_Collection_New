package com.unisoft.retail.card.reporttextfileupload;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table

public class CollectionReportTwo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contractNo;
    private String clientId;
    private String cmOnBoardDate;
    private String contractStatus;
    private String contractType;
    private String fdrValue;
    private String branchValue;
    private String instaBuy;
    private String instaBuyAmt;
    private String instaBuyPaid;
    private String instaBuyUnpaid;
    private String mrContract;
    private String mrAccount;
    private String rewardPoint;
    private String creditShield;
    private String totalBdtPay;
    private String totalUsdPay;
    private String firstTxnDate;
    private String firstTxnPoste;
    private String firstTxnAmt;
}
