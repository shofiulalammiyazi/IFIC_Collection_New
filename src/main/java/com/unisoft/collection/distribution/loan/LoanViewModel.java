package com.unisoft.collection.distribution.loan;
/*
Created by   Islam at 7/22/2019
*/

import lombok.Data;

import java.util.Date;

@Data
public class LoanViewModel {

    private String customerId;

    private String accountNo;

    private String customerName;

    private Date createdDate;

    private String outstandingAmount;

    private String minimumDueAmount;

    private String location;

    private String dpdBucket;

    private String ageCode;

    private String supervisorName;

    private String delaerPin;

    private String delaerName;

    private String AgencyName;

    private String branchName;

    private String monirotingStatus;
}
