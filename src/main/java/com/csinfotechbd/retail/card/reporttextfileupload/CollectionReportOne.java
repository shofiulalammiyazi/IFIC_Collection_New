package com.csinfotechbd.retail.card.reporttextfileupload;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table

public class CollectionReportOne extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String contractNo;
    private String clientId;
    private String lastCard;
    private String cardProduct;
    private String contractType;
    private String contractStatus;
    private String statementDate;
    private String payDueDate;
    private String bdtLimit;
    private String currBdtOs;
    private String stOsBdt;
    private String stMinPayBdt;
    private String usdLimit;
    private String currUsdOs;
    private String stOsUsd;
    private String stMinPayUsd;
    private String ageCode;
}
