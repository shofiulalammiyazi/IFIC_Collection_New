package com.unisoft.collection.samd.dataEntry.proposalManagementSettlement.settlementInterestChargeDetails;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "lms_collection_sam_settlement_interest_charge_details")
public class SettlementInterestChargeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerId;
    private String year;

    private double interestCharged;
    private double interestSuspense;
    private double recovery;
    private double settlementRecovery;

}
