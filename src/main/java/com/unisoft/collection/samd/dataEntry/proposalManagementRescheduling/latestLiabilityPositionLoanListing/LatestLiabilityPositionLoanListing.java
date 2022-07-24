package com.unisoft.collection.samd.dataEntry.proposalManagementRescheduling.latestLiabilityPositionLoanListing;


import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.common.CommonEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
public class LatestLiabilityPositionLoanListing extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String natureOfAccount;
    private String accountNo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date sanctionOnDate;
    private String validity;
    private Double limit;
    private Double rateOfInt;
    private Double outstanding;
    private String suspenseAsOn;
    private String unappliedAsOn;
    private Double amount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date month;
    private String ciSec;
    private Double marketValue;
    private String forcedSalesValue;
    @OneToOne(cascade = CascadeType.REFRESH)
    private AssetClassificationLoanEntity cLStatus;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnore
    private CustomerBasicInfoEntity customerBasicInfo;


}
