package com.unisoft.collection.distribution.samAccountHandover;
/*
Created by   Islam at 9/15/2019
*/

import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class AutoDistributionLoanSamTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String supervisorName;

    private String supervisorPin;

    private String dealerName;

    private String dealerPin;

    private String samAccount;

    private String writeOffAccount;

    @Temporal(TemporalType.DATE)
    private Date statusDate;

    @OneToOne
    private LoanAccountBasicInfo loanAccountBasicInfo;

    private String productGroup;

    private String dpdBucket;

    private String outStanding;

    private String schemaCode;
}
