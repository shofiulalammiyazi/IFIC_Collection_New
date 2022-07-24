package com.unisoft.collection.samd.dataEntry.timeExtensionTdConversion;

import com.unisoft.collection.samd.setup.approvalLevel.ApprovalLevel;
import com.unisoft.collection.samd.setup.hrPosition.HrPosition;
import com.unisoft.collection.samd.setup.proposalPreparedFor.ProposalPreparedReason;
import com.unisoft.collection.samd.setup.proposalPurpose.ProposalPurpose;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.common.CommonEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter @Setter
@Table(name = "lms_collection_sam_time_extension_td_conversion")
public class TimeExtensionTdConversion extends CommonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long customerId;

    private String proposalType;
    private String branchName;
    private String loanAccountNo;
    private String loanAccountName;

    private String facilityNature;
    private Long clStatus;
    @JoinColumn(name = "clStatus", updatable = false, insertable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private AssetClassificationLoanEntity assetClassificationLoanEntity;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date disbursementDate;
    private String legalDetailStatus;

    private Double fillingAmount;
    private String typeOfCase;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date expiryDate;
    private String proposalSendBy;
    private String memoNotePreparedBy;

    private Long proposalPlacedTo;
    @JoinColumn(name = "proposalPlacedTo", updatable = false, insertable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private HrPosition hrPosition;

    private Long approvalLevel;
    @JoinColumn(name = "approvalLevel", updatable = false, insertable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private ApprovalLevel approvalLevelEntity;

    private Long proposalPreparedFor;
    @JoinColumn(name = "proposalPreparedFor", updatable = false, insertable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private ProposalPreparedReason proposalPreparedReason;

    private Long purposeOfTheProposal;
    @JoinColumn(name = "purposeOfTheProposal", updatable = false, insertable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private ProposalPurpose proposalPurpose;

    private String existingCaseStatus;
    private String dynamicCalculatorForTETDConversion;
    private Integer totalNoOfTETDC;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date proposedExtendedDate;
    private String sanctionAcceptedByBorrower;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date sanctionAcceptedDateByBorrower;

    private Double capitalRelease;
    private String nplReduced;
    private String remarksOrComments;
    private Double totalLegalAndOtherExpense;

    /* ----- For Attachment ----- */
    private String fileName;
    private String dmsFileId;
    private String dmsFileType;

    @Transient
    @JsonIgnore
    private MultipartFile file;

    public TimeExtensionTdConversion(){

    }

    public TimeExtensionTdConversion(Long id){
        this.id = id;
    }

}
