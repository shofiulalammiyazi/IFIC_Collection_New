package com.unisoft.collection.samd.dataEntry.proposalManagementRescheduling.interestChargedRecoveryDetails;


import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class InterestChargedRecoveryDetails extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double interestCharged;
    private double interestSuspense;
    private double recovery;
    private double settlementRecovery;

    private String year;

    private String customerId;

}
