package com.csinfotechbd.collection.samd.dataEntry.latestApprovalRelatedToLoanRescheduling;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class LatestApprovalRelatedToLoanRescheduling extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String approvalReference;
    private String latestApprovalDate;
    private String details;
    private String dealerPin;
    private String loanAccountNo;

}
