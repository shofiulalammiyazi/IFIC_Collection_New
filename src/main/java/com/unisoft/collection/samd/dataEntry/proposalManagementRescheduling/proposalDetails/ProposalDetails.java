package com.unisoft.collection.samd.dataEntry.proposalManagementRescheduling.proposalDetails;


import com.unisoft.collection.samd.setup.approvalLevel.ApprovalLevel;
import com.unisoft.collection.samd.setup.bbApprovalRequirement.BBApprovalRequirement;
import com.unisoft.collection.samd.setup.hrPosition.HrPosition;
import com.unisoft.collection.samd.setup.proposalStatus.ProposalStatus;
import com.unisoft.collection.samd.setup.proposedRescheduleTimes.ProposedRescheduleTimes;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.common.CommonEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
public class ProposalDetails extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerId;

    private String proposalType;
    private String branchName;
    private String loanAccountNo;
    private String loanAccountName;
    private String facilityNature;

    @OneToOne(cascade = CascadeType.REFRESH)
    private AssetClassificationLoanEntity cLStatus;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date disbursementDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date expiryDate;
    private String proposalSendBy;
    private String memoNotePreparedBy;

    @OneToOne(cascade = CascadeType.REFRESH)
    private HrPosition proposalPlacedTo;

    @OneToOne(cascade = CascadeType.REFRESH)
    private ApprovalLevel approvalLevel;

    @OneToOne(cascade = CascadeType.REFRESH)
    private ProposalStatus proposalStatus;

    private Double totalRecovery;
    private Double overdue;
    private Double outstanding;
    private Double fillingAmount;
    private String typeOfCase;
    private Double interestSuspense;
    private Double provision;
    private Double unappliedInterest;
    private Double totalClaimAmount;
    private Double valueEligibleSecurity;
    private Double proposedWaiverUnappliedInterest;
    private Double proposedWaiverInterestSuspense;
    private Double proposedWaiverIncome;
    private Double proposedTotalWaiver;
    private Double outstandingBeforeDownPayment;
    private Double downPaymentDeterminingCriteria;
    private Double basisObtainingDownPayment;
    private Double requiredDownPayment;
    private Double realizedDownPayment;
    private Double remainingDownPayment;
    private Double outstandingAfterDownPayment;
    private String existingCaseStatus;
    private String dynamicCalculator;

    @OneToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    private ProposedRescheduleTimes proposedRescheduleTimes;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date allowableMaxRescheduleMonths;
    private String allowedTimeReshedulement;
    private String relevantSubjectMemo;
    private String dateLastCIBReport;

    @OneToOne(cascade = CascadeType.REFRESH)
    private BBApprovalRequirement bBApprovalRequirement;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date letterIssuingDateBBApproval;    //new date

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date bBApprovalReceivingDate;    //new date do not found this value

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date sanctionIssuingDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date rescheduleExecutionDate;   //new date
    private String rescheduledAccountNo;
    private Double interestIncome;
    private String capitalRelease;
    private String npLReduced;
    private String remarks;
    private Double totalLegalAndOtherExpense;

    @Transient
    private MultipartFile file;

    @Transient
    private Long clStatusId;




    public ProposalDetails() {
    }

    public ProposalDetails(Long id) {
        this.id = id;
    }


}
