package com.unisoft.collection.samd.customerprofile.previousloanrescheduling;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
public class PreviousLoanRescheduling extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String loanAccountNo;
    private String dealerPin;
    private String approvalReference;
    private String approvalDate;
    private String approvalDetails;

}

