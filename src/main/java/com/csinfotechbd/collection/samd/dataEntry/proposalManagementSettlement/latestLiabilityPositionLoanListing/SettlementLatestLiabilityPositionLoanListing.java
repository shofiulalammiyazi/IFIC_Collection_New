package com.csinfotechbd.collection.samd.dataEntry.proposalManagementSettlement.latestLiabilityPositionLoanListing;

import com.csinfotechbd.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.csinfotechbd.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "lms_collection_sam_settlement_latest_liability_position_loan_listing")
public class SettlementLatestLiabilityPositionLoanListing extends CommonEntity {

    private String customerId;
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
    private String aMT;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date month;
    private String ciSec;

    @OneToOne(cascade = CascadeType.REFRESH)
    private AssetClassificationLoanEntity cLStatus;

}
