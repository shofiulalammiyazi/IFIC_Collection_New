package com.unisoft.collection.samd.dataEntry.futureRecoveryPlan;

import com.unisoft.base.BaseInfo;
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
