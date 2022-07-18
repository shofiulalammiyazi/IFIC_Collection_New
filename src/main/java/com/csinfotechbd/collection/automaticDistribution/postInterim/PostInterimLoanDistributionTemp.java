package com.csinfotechbd.collection.automaticDistribution.postInterim;

import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/*
Created by Monirul Islam at 9/17/2019
*/
@Data
@Entity
public class PostInterimLoanDistributionTemp {

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

    @Transient
    private String dpd;
}
