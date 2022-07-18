package com.csinfotechbd.legal.dataEntry.caseEntry;

/*
 Revised and modified by Yasir Araphat on 09 March, 2021
  */

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.legal.dataEntry.blaAttendanceHistory.BLAAttendanceHistory;
import com.csinfotechbd.legal.setup.caseFiledSubType.CaseFiledSubType;
import com.csinfotechbd.legal.setup.caseFiledType.CaseFiledType;
import com.csinfotechbd.legal.setup.caseStatus.CaseStatus;
import com.csinfotechbd.legal.setup.caseType.CaseType;
import com.csinfotechbd.legal.setup.collateralSecurity.CaseEntryCollateralDetails;
import com.csinfotechbd.legal.setup.collateralSecurity.CollateralSecurity;
import com.csinfotechbd.legal.setup.courseOfAction.CourseOfAction;
import com.csinfotechbd.legal.setup.courts.Courts;
import com.csinfotechbd.legal.setup.lawyers.Lawyer;
import com.csinfotechbd.legal.setup.legalExpense.LegalExpenseDetailsEntity;
import com.csinfotechbd.user.UserPrincipal;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Data
@Audited
@AuditOverride(forClass = BaseInfo.class)
@Entity
public class LitigationCaseInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToOne(cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CaseFiledType caseFiled;

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToOne(cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CaseFiledSubType caseFiledSubType;

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToOne(cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CaseType caseType;

    //    Modifications to generalize writ and fraud
    private String otherCaseType; // Replacement of otherCase
    private String caseTypeSubType; // Replacement of writSubCaseType
    private String otherCaseTypeSubType; // Replacement of writSubCaseTypeOthers

    private String natureOfWrit; // New
    private String natureOfWritOther; // New

    //    @Column(unique = true)
    private String ldNo; // ID of Case File. Several law suits can be filed in a case file.
    private String zone;
    private String branchName;
    private String branchCode;
    private String nameOfAcc;
    private String accusedName;
    private String borrowerName;
    private String customerCifNo;
    private String customerAccNum;
    private String clientId;
    private String accountNoIf; // For extra Account No field in Artharin Execution

    private String rmName;
    private String supervisorName;
    private String niActCaseClaimAmount;

    private String businessSegment;


    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<LitigationCaseInfoAccountDetails> litigationCaseInfoAccountDetailsList;


    // Writ related fields
    private String petitionerName;
    private String oppositePartyName;
    private String byWhomFiled;
    private String subjectMatterOfCase;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date firstOrderDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date hearingDate;

    private String typeOfFraud; // Only of by bank -> criminal case -> fraud

    private String plaintiffName;
    private String plaintiffDesignation;
    private String plaintiffPhoneNo;
    private String otherDefendantName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date plaintiffChangesDate;
//    @Transient
//    private List<String> plaintiffChange = new ArrayList<>();

    //    Money Suit related data
    private String defendantName;
    private String defendantPhone;
    private String commentImpactOnBank;
    private String landDetails;


    // Fraud fields
    private String techniquesTofraud;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date dateOfOccurrence;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date dateOfDetection;
    private Double amountInvolved;
    private Double adjustmentOfDefraudAmount;
    private String nameOfOfficerEmployeeOtherInvolved;
    private String actionAgainstDelinquent;
    private String currentStatusOfCase;
    private String initiativesToPreventIncidentRecurrence;
    @Lob
    private String remarks;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date statusDate;
    private String division;

    private Double outstanding; // For NI Act

    private String caseNumber;
    private String caseYear;
    private String section;// For Fraud

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToOne(cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Courts court;
    private String otherCourt;  // New

    private String district;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date dateOfFilingAR;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date dateOfFiling;

    private Double suitValueAr;
    private Double suitValue; // Case Amount/Outstanding from an account
    private Double chequeAmount; // For NI Act

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
//    @DateTimeFormat(pattern = "dd/MMM/yyyy")
    private Date nextDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date previousDate;
    private Long courseOfActionIds;
    private String courseOfActionNames;

    private boolean nextDateFixed;  // New


    /*********bla attendance***********/

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<BLAAttendanceHistory> blaAttendanceHistoryList;




    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToOne(cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CourseOfAction courseOfAction;
    private String courseOfActionContestedType;
    private boolean possessionInFavourOfBank; // Artharing execution only
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date registrationDate; // Artharing execution only
    private String deedNo; // Artharing execution only
    private String otherCourseOfAction; // Artharing execution only

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date deedDate; // Artharing execution only

    private boolean applicationForMutation; // Artharing execution only

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date mutationDate; // Artharing execution only

    private String propertyDisputed; // Artharing execution only

    private String niActCaseNo;
    private String artharinSuitNo;
    private String writPetitionNum;
    private String others; // Other suit/case if any
    private boolean blaAttendance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date blaAttendanceDate;
    private boolean writtenOff;

    private Double recoveryBeforeCaseAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date recoveryBeforeCaseDate;

    private Double recoveryAfterCaseAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date recoveryAfterCaseDate;

    private Double recoveredAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date recoveredAmountDate;

  /*  private Double legalExpense;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date legalExpenseDate;*/

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<RecoveryBeforeCaseDetails> recoveryBeforeCaseDetailsList;

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<RecoveryAfterCaseDetails> recoveryAfterCaseDetailsList;



    /**************Shahin by****************/
    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    //@JsonManagedReference
    private List<LegalExpenseDetailsEntity> legalExpenseDetailsEntityList;

    private Double totalLegalExpenseAmount;

    private boolean underSolenama;

    @Audited(targetAuditMode = NOT_AUDITED)
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Lawyer> lawyer;

    private String opponentLawyerName;

    private String lawyerCellNum;

//    @Audited(targetAuditMode = NOT_AUDITED)
//    @ManyToMany(cascade = CascadeType.REFRESH)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @Transient
//    private List<String> lawyerChange = new ArrayList<>();

    @Audited(targetAuditMode = NOT_AUDITED)
    //@OneToOne(cascade = CascadeType.REFRESH)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonManagedReference
    private List<CaseEntryCollateralDetails> collateralSecurityList;

/*    @Transient
    private List<Long> collateralSecurityIds;*/
    //private List<CollateralSecurity> collateralSecurityList = new ArrayList<>();

    //private String collateralSecurity;

/*
    private String collateralSecurityFixedAssetDetails;
    private String collSecurityCityCorporation;
    private String collSecurityPourasava;
    private String outOfCityCorporationPourasava;
    private String collSecurityDistrict;
    private String collSecurityMouza;
    private String collSecuritySro;
    private String collSecurityPs;
    private String khatianCs;
    private String khatianSa;
    private String khatianRs;
    private String khatianBs;
    private String khatianCity;
    private String jlCs;
    private String jlSa;
    private String jlRs;
    private String jlBs;
    private String jlCity;
    private String plotCs;
    private String plotSa;
    private String plotRs;
    private String plotBs;
    private String plotCity;
    private String boundedByNorth;
    private String boundedBySouth;
    private String boundedByEast;
    private String boundedByWest;
    private String otherCollateralSecurity;*/ // New

    private Double totalMarketValue;
    private Double totalForcedSaleValue;

    private Double marketValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date marketValueDate;
    private Double forcedSaleValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date forcedSaleValueDate;
    private String assesedBy;
    private String surveyorDetail;

        /************new add by shahin**************/
    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<MarketValueSalesValueDetails> marketValueSalesValueDetailsList;



    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToOne(cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CaseStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date dateOfJudgement;
    private Double amountDecreed;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date stipulatedDateOfPayment;
    private String punishmentPrisonTerm;

    private String otherStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date statusChange;

    // Report related columns
    private String courtJurisdiction;
    private String writPetitionStatus;

    // Unresolved fields
//    private String petitionerNameOption;
//    private String otherSuitCase;

    public void setAmountInvolved(Double amountInvolved) {
        this.amountInvolved = amountInvolved == null ? 0D : amountInvolved;
    }

    public void setAdjustmentOfDefraudAmount(Double adjustmentOfDefraudAmount) {
        this.adjustmentOfDefraudAmount = adjustmentOfDefraudAmount == null ? 0D : adjustmentOfDefraudAmount;
    }

    public void setOutstanding(Double outstanding) {
        this.outstanding = outstanding == null ? 0D : outstanding;
    }

    public void setSuitValueAr(Double suitValueAr) {
        this.suitValueAr = suitValueAr == null ? 0D : suitValueAr;
    }

    public void setSuitValue(Double suitValue) {
        this.suitValue = suitValue == null ? 0D : suitValue;
    }

    public void setChequeAmount(Double chequeAmount) {
        this.chequeAmount = chequeAmount == null ? 0D : chequeAmount;
    }

    public void setRecoveryBeforeCaseAmount(Double recoveryBeforeCaseAmount) {
        this.recoveryBeforeCaseAmount = recoveryBeforeCaseAmount == null ? 0D : recoveryBeforeCaseAmount;
    }

    public void setRecoveryAfterCaseAmount(Double recoveryAfterCaseAmount) {
        this.recoveryAfterCaseAmount = recoveryAfterCaseAmount == null ? 0D : recoveryAfterCaseAmount;
    }

    public void setRecoveredAmount(Double recoveredAmount) {
        this.recoveredAmount = recoveredAmount == null ? 0D : recoveredAmount;
    }

    /*public void setLegalExpense(Double legalExpense) {
        this.legalExpense = legalExpense == null ? 0D : legalExpense;
    }*/

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue == null ? 0D : marketValue;
    }

    public void setForcedSaleValue(Double forcedSaleValue) {
        this.forcedSaleValue = forcedSaleValue == null ? 0D : forcedSaleValue;
    }

   /* public void setCollateralSecurityIds(List<Long> collateralSecurityIds) {
        this.collateralSecurityList = collateralSecurityIds != null ? collateralSecurityIds.stream().map(CollateralSecurity::new).collect(Collectors.toList()) : null;
    }*/

    @PrePersist
    void onCreate() {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        setCreatedBy(user.getUsername());
        setCreatedDate(new Date());
    }

    @PreUpdate
    void onUpdate() {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        setModifiedBy(user.getUsername());
        setModifiedDate(new Date());
    }


}
