package com.csinfotechbd.collection.samd.dataEntry.futureRecoveryPlan;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class FutureRecoveryPlan extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String futureRecoveryPlan;

    private String accountNumber;
    private String dealerId;
}
