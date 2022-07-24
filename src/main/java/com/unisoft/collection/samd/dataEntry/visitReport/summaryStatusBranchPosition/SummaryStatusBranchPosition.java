package com.unisoft.collection.samd.dataEntry.visitReport.summaryStatusBranchPosition;

import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class SummaryStatusBranchPosition extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double profitTarget;
    private Double profitAchieved;
    private Double profitGap;

    private Double recoveryPossibility;
    private Double recoveryAchieved;
    private Double recoveryGap;

    private Double interestSuspense;
    private Double interestIncome;
    private Double provisionRelease;
    private Double nplRelease;

    private String customerId;

}
