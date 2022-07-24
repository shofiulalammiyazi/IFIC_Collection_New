package com.unisoft.collection.distribution.accountReschedule;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/*
Created by   Islam at 8/26/2019
*/
@Data
@Entity
public class AccountReschedule extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String accountNo;

    private String status;

    @Temporal(value = TemporalType.DATE)
    private Date executionDate;

    private String outStandingAmount;

    private String overdueAmount;

    private String assetStatus;

    private String downPaymentAmount;

    private String exsistingEmiAmount;

    private String existingTenor;

    private String exsistingInterestRate;

    @Temporal(value = TemporalType.DATE)
    private Date exsistingEmiDate;

    private String newEmiAmount;

    private String newTenor;

    private String newInterestRate;

    @Temporal(value = TemporalType.DATE)
    private Date newEmiDate;

    private String executorName;

    private String executorPin;

    private String executorDepartMent;

    private String approverName;

    private String approverPin;

    private String approverDepartMent;

    @Temporal(value = TemporalType.DATE)
    private Date approvalDate;
}
