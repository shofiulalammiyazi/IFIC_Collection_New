package com.csinfotechbd.legal.dataEntry.fileFollowUp;

import com.csinfotechbd.common.CommonEntity;
import com.csinfotechbd.legal.dataEntry.blaAttendanceHistory.BLAAttendanceHistory;
import com.csinfotechbd.legal.setup.collateralSecurity.CaseEntryCollateralDetails;
import com.csinfotechbd.legal.setup.collateralSecurity.FileFollowCollateralSecurityDetails;
import com.csinfotechbd.workflow.propertyBasedMakerChecker.PropertyBasedMakerCheckerModel;
import com.csinfotechbd.workflow.propertyBasedMakerChecker.PropertyBasedMakerCheckerService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = CommonEntity.class)
public class LitigationFileFollowUp extends CommonEntity implements PropertyBasedMakerCheckerModel {

    @NotNull(message = "Please fill up this field")
    private String ldNo;
    @NotNull(message = "Please fill up this field")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date fileTransferToSAMD;
    private String branchCode;
    private String branchName;

    private String accountName;
    private String borrowerName;
    private String customerCifNumber;
    private String loanAccountNumber;
    private String customerMobileNumber;
    private String businessSegment;


    /*private boolean legalNoticeServedForUS138OfNIAct;
    private String legalNoticeServedForUS138ClaimedAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfLegalNoticeServedForUS138OfNIAct;*/

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfAuctionHeldOn;
    private String newspaperNameForActionHeldOn;
    private boolean biddersParticipated;
    private Long numberOfParticipants;
    private Double highestBidAmount;
    private Double loanOutstanding;
    private Double forceSaleValue;
    private Double marketValue;
    //    private Long numberOfScheduledLand;
    private String totalLandArea;
    private String biddersParticipation;
    private boolean auctionAcceptedByManagement;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfAuctionAcceptedByManagement;
    private boolean propertySoldThroughAuction;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfPropertySoldThroughAuction;
    private boolean accountAdjustedBeforeFilingArtharinSuit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfAccountAdjustedBeforeFilingArtharinSuit;

    private String accountAdjustedAmount;
    private String adjustedWithExcessAmount;

    private Double amountAfterPartialAdjustmentThroughAuction;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfPartialAdjustmentThroughAuction;

    //    private boolean lastModifiedEntry;
    private String remark;


    //new field add
    private String zone;
    private String district;
    private String division;
    private boolean callBackNoticeServed;
    private boolean demandNoticeServed;
    private Double claimAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date demandNoticeServedDate;
    private Double outStanding;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date outstandingDate;
    private boolean writtenOff;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date writtenOfDate;

    private Double writtenOffAmount;
    private String numberOfSchedule;
    private String collateralTotalLandArea;
    private Double totalMarketValue;
    private Double totalForcedSaleValue;

/*    private boolean accountRescheduled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfAccountRescheduled;
    private String installmentSize;
    private String numberOfInstallment;
    private String tenure;*/

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<FileFollowUpAccountResheduled> fileFollowUpAccountResheduledList;

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<FileFollowUpLegalNoticeServedForArtharinSuit> fileFollowUpLegalNoticeServedForArtharinSuits;

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<FileFollowUpLegalNoticeServedNIAct> fileFollowUpLegalNoticeServedNIActs;

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonManagedReference
    private List<FileFollowCollateralSecurityDetails> fileFollowUpCollateralSecurityDetailsList;

    private boolean niActCaseFiled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date niActCaseFiledDate;
    private String niActCaseNo;

    private boolean arsFiled;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date arsFiledDate;
    private String arsFiledCaseNo;
    private String stayStatus;


    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<AuctionNoticePublishedEntity> auctionNoticePublishedEntityList;

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<FileFollowUpLegalExpenseEntity> fileFollowUpLegalExpenseEntityList;
    private Double totalLegalExpenseAmount;


    private boolean auctionStayed;
    private String auctionStayedCaseNo;
    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<AuctionStayedEntity> auctionStayedEntityList;

   /* private boolean auctionNoticePublished;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfAuctionNoticePublished;*/
}
