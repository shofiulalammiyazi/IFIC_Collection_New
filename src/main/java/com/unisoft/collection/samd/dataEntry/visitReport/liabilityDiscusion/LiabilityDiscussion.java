package com.unisoft.collection.samd.dataEntry.visitReport.liabilityDiscusion;


import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class LiabilityDiscussion extends CommonEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String borrowerName;
    private String accountNum;
    private Double limit;
    private String osdt;
    private Double suspense;
    private Double unapplied;
    private String bankClaim;
    private Double marketvalue;
    private Double forcedSalesValue;
    private Double eiSec;

    @OneToOne(cascade = CascadeType.REFRESH)
    private AssetClassificationLoanEntity clStatus;

    private String customerId;
}
