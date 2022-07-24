package com.unisoft.collection.samd.dataEntry.proposalManagementSettlement.proposalDetails;

import com.unisoft.collection.samd.setup.approvalLevel.ApprovalLevel;
import com.unisoft.collection.samd.setup.hrPosition.HrPosition;
import com.unisoft.collection.samd.setup.proposalStatus.ProposalStatus;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.common.CommonEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "lms_collection_sam_settlement_proposal_details")
public class SettlementProposalDetails extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerId;
    private String proposalType;
    private String branchName;

    private String loanAccountNo;
    private String loanAccountName;
    private String facilityNature;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private AssetClassificationLoanEntity cLStatus;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private HrPosition proposalPlacedTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date disbursementDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date expiryDate;
    private String proposalSendBy;
    private String memoNotePreparedBy;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private ApprovalLevel approvalLevel;

    private String fillingAmount;
    private String typeOfCase;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private ProposalStatus proposalStatus;

    private String valueEligibleSecurity;
    private String provision;
    private Double principleAmount;
    private Double interestTaken;

    private Double interestKept;
    private Double legalBillOrOtherExpense;
    private Double subTotal;
    private Double totalRecovery;

    private Double ledgerOutstanding;
    private Double unappliedInterest;
    private Double totalClaimAmount;
    private String existingCaseStatus;

    private String dynamicCalculator;
    private Double proposedWaiverUnappliedInterest;
    private Double proposedWaiverInterestSuspense;
    private Double proposedWaiverIncome;

    private Double proposedTotalWaiver;
    private Double borrowerWantsToPay;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date tentativeSettlementDate;
    private Double interestIncome;

    private Double capitalRelease;
    private Double npLReduced;
    private String remarks;
    private String legalDetailStatus;
    private Double totalLegalAndOtherExpense;

    @Transient
    @JsonIgnore
    private MultipartFile file;


    public SettlementProposalDetails() {
    }

    public SettlementProposalDetails(Long id) {
        this.id = id;
    }

}
