package com.csinfotechbd.collection.samd.dataEntry.proposalManagementRescheduling.interestChargedRecoveryDetails;


import com.csinfotechbd.common.CommonEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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
