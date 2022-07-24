package com.unisoft.collection.samd.dataEntry.monitoringFollowUp;



import com.unisoft.collection.samd.setup.bBAuditType.BBAuditType;
import com.unisoft.collection.samd.setup.bbcommentsforclassification.BbCommentsForClassification;
import com.unisoft.collection.samd.setup.borrowerGuarantorsCapabilityToRepay.BorrowerGuarantorCapabilityRepay;
import com.unisoft.collection.samd.setup.borrowerPresentStatus.BorrowerPresentStatus;
import com.unisoft.collection.samd.setup.borrowerbusinessstatus.BorrowerBusinessStatus;
import com.unisoft.collection.samd.setup.borrowerguarantoravailability.BorrowerAndGuarantorAvailability;
import com.unisoft.collection.samd.setup.borrowerstayingat.BorrowerStaying;
import com.unisoft.collection.samd.setup.capableInfluenceCustomerBankDue.CapableInfluenceCustomerBankDue;
import com.unisoft.collection.samd.setup.categoryForDelinquency.CategoryDelinquency;
import com.unisoft.collection.samd.setup.interestedFurtherLoan.InterestedFurtherLoan;
import com.unisoft.collection.samd.setup.loanLiabilityRecoverability.LoanLiabilityRecoverability;
import com.unisoft.collection.samd.setup.logicInTerms.LogicInTerms;
import com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplUnclassified.PossibilityProbability;
import com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplclassified.PossibilityProbabilityClassified;
import com.unisoft.collection.samd.setup.sourceOfRecovery.SourceOfRecovery;
import com.unisoft.collection.samd.setup.sourceofrecoverytools.SourceOfRecoveryTools;
import com.unisoft.collection.samd.setup.wayForwardReduceFromNPL.WayForwardReduceFromNPL;
import com.unisoft.collection.samd.setup.whethertheloanistetd.WhetherTheLoanTeTdRcRs;
import com.unisoft.common.CommonEntity;
import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
public class MonitoringAndFollowUp extends CommonEntity {

    private String borrowerNature;

       @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private WhetherTheLoanTeTdRcRs whetherTheLoanTeTdRcRs;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private SourceOfRecoveryTools sourceOfRecoveryTools;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private BorrowerStaying borrowerStaying;

    private String noTeTdRCRs;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date proReCurrentYear;     //projectedRecoveryCurrentYear

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private BorrowerBusinessStatus borrowerBusinessStatus;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private InterestedFurtherLoan interestedFurtherLoan;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private SourceOfRecovery sourceOfRecovery;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private BorrowerPresentStatus borrowerPresentStatus;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private LogicInTerms logicInTerms;

    private Double previousMonthCashRecoveryAmount;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private BorrowerAndGuarantorAvailability borrowerAndGuarantorAvailability;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private PossibilityProbability possibilityProbabilityUnclassified;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date PrevMonthCashRecoDate;     //PreviousMonthCashRecoveryDate

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private BorrowerGuarantorCapabilityRepay borrowerGuarantorCapabilityRepay;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private PossibilityProbabilityClassified possibilityProbabilityClassified;

    private Double CurrentMonthCashRecoveryAmount;

    private String auctionNoticePublishedYearLatest;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private WayForwardReduceFromNPL wayForwardReduceFromNPL;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date currentMonthCashRecoveryDate;

    private String PossibleArrangeProspectiveBuyerAuctionSale;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private LoanLiabilityRecoverability loanLiabilityRecoverability;

    private Double cumulativeCashRecoveryAmount;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private List<CategoryDelinquency> categoryDelinquencyList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private CapableInfluenceCustomerBankDue capableInfluenceCustomerBankDue;

    private String remarks;

    private String reasonsDelinBorroView;   // reasonsDelinquencyBorrowerPointView

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private BBAuditType bbAuditType;


//    @Transient
//    private MultipartFile file;

    private String reasonsDelinqBankerView;  //reasonsDelinquencyBankerPointView

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private BbCommentsForClassification bbCommentsForClassification;

    private String backgroundBorrowerBusiness;
    private String initiativeActionsTakenAfterDelinquency;
    private String reasonClassificationBorrowerPointView;
    private String distanceBranchBorrowerOfficeKilometres;
    private String borrPrePropoRegaAdjLoanLiabilities;
    private String reasonClassificationBankerPointView;
    private String distanceBranchBorrowerResidenceKilometres;
    private String futureRecoveryPlanSettleLoanLiability;
    private String justificationBeingClassified;
    private String distanceBranch3rdpartyGuarantorKilometres;
    private String distanceBranchBorrowerBusiness;
    private String distanceBranch3rdPartyGuarantorResidenceKilometres;


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnore
    private CustomerBasicInfoEntity customerBasicInfo;


    @Transient
    private List<Long> categoryDelinquencyIds;

    @Transient
    private Long whetherTheLoanTeTdRcRsId;
    @Transient
    private Long sourceOfRecoveryToolsId;
    @Transient
    private Long borrowerStayingId;
    @Transient
    private Long borrowerBusinessStatusId;
    @Transient
    private Long interestedFurtherLoanId;
    @Transient
    private Long sourceOfRecoveryId;
    @Transient
    private Long borrowerPresentStatusId;
    @Transient
    private Long logicInTermsId;
    @Transient
    private Long borrowerAndGuarantorAvailabilityId;
    @Transient
    private Long possibilityProbabilityUnclassifiedId;
    @Transient
    private Long borrowerGuarantorCapabilityRepayId;
    @Transient
    private Long possibilityProbabilityClassifiedId;
    @Transient
    private Long wayForwardReduceFromNPLId;
    @Transient
    private Long loanLiabilityRecoverabilityId;
    @Transient
    private Long capableInfluenceCustomerBankDueId;
    @Transient
    private Long bbAuditTypeId;
    @Transient
    private Long bbCommentsForClassificationId;


//    public WhetherTheLoanTeTdRcRs getWhetherTheLoanTeTdRcRs() {
//        return whetherTheLoanTeTdRcRs = (whetherTheLoanTeTdRcRs != null) ? whetherTheLoanTeTdRcRs : new WhetherTheLoanTeTdRcRs();
//    }
//
//    public SourceOfRecoveryTools getSourceOfRecoveryTools() {
//        return sourceOfRecoveryTools = (sourceOfRecoveryTools != null) ? sourceOfRecoveryTools : new SourceOfRecoveryTools();
//    }
//
//    public BorrowerStaying getBorrowerStaying() {
//        return borrowerStaying = (borrowerStaying != null) ? borrowerStaying : new BorrowerStaying();
//    }
//
//    public BorrowerBusinessStatus getBorrowerBusinessStatus() {
//        return borrowerBusinessStatus = (borrowerBusinessStatus != null) ? borrowerBusinessStatus : new BorrowerBusinessStatus();
//    }
//
//    public InterestedFurtherLoan getInterestedFurtherLoan() {
//        return interestedFurtherLoan = (interestedFurtherLoan != null) ? interestedFurtherLoan : new InterestedFurtherLoan();
//    }
//
//    public SourceOfRecovery getSourceOfRecovery() {
//        return sourceOfRecovery = (sourceOfRecovery != null) ? sourceOfRecovery : new SourceOfRecovery();
//    }
//
//    public BorrowerPresentStatus getBorrowerPresentStatus() {
//        return borrowerPresentStatus = (borrowerPresentStatus != null) ? borrowerPresentStatus : new BorrowerPresentStatus();
//    }
//
//    public LogicInTerms getLogicInTerms() {
//        return logicInTerms = (logicInTerms != null) ? logicInTerms : new LogicInTerms();
//    }
//
//    public BorrowerAndGuarantorAvailability getBorrowerAndGuarantorAvailability() {
//        return borrowerAndGuarantorAvailability = (borrowerAndGuarantorAvailability != null) ? borrowerAndGuarantorAvailability : new BorrowerAndGuarantorAvailability();
//    }
//
//    public PossibilityProbability getPossibilityProbabilityUnclassified() {
//        return possibilityProbabilityUnclassified = (possibilityProbabilityUnclassified != null) ? possibilityProbabilityUnclassified : new PossibilityProbability();
//    }
//
//    public BorrowerGuarantorCapabilityRepay getBorrowerGuarantorCapabilityRepay() {
//        return borrowerGuarantorCapabilityRepay = (borrowerGuarantorCapabilityRepay != null) ? borrowerGuarantorCapabilityRepay : new BorrowerGuarantorCapabilityRepay();
//    }
//
//    public PossibilityProbabilityClassified getPossibilityProbabilityClassified() {
//        return possibilityProbabilityClassified = (possibilityProbabilityClassified != null) ? possibilityProbabilityClassified : new PossibilityProbabilityClassified();
//    }
//
//    public WayForwardReduceFromNPL getWayForwardReduceFromNPL() {
//        return wayForwardReduceFromNPL = (wayForwardReduceFromNPL != null) ? wayForwardReduceFromNPL : new WayForwardReduceFromNPL();
//    }
//
//
//    public LoanLiabilityRecoverability getLoanLiabilityRecoverability() {
//        return loanLiabilityRecoverability = (loanLiabilityRecoverability != null) ? loanLiabilityRecoverability : new LoanLiabilityRecoverability();
//    }
//
//    public CapableInfluenceCustomerBankDue getCapableInfluenceCustomerBankDue() {
//        return capableInfluenceCustomerBankDue = (capableInfluenceCustomerBankDue != null) ? capableInfluenceCustomerBankDue : new CapableInfluenceCustomerBankDue();
//    }
//
//        public BBAuditType getBbAuditType () {
//            return bbAuditType = (bbAuditType != null) ? bbAuditType : new BBAuditType();
//        }
//
//
//        public BbCommentsForClassification getBbCommentsForClassification () {
//            return bbCommentsForClassification = (bbCommentsForClassification != null) ? bbCommentsForClassification : new BbCommentsForClassification();
//        }


    public void setCategoryDelinquencyIds(List<Long> categoryDelinquencyIds) {
        this.categoryDelinquencyList = (categoryDelinquencyIds != null) ? categoryDelinquencyIds.stream().map(CategoryDelinquency:: new).collect(Collectors.toList()): null;
    }



        public void setWhetherTheLoanTeTdRcRsId (Long whetherTheLoanTeTdRcRsId){
            this.whetherTheLoanTeTdRcRs = (whetherTheLoanTeTdRcRsId == null) ? null : new WhetherTheLoanTeTdRcRs(whetherTheLoanTeTdRcRsId);
        }

        public void setSourceOfRecoveryToolsId (Long sourceOfRecoveryToolsId){
            this.sourceOfRecoveryTools = (sourceOfRecoveryToolsId == null) ? null : new SourceOfRecoveryTools(sourceOfRecoveryToolsId);
        }

        public void setBorrowerStayingId (Long borrowerStayingId){
            this.borrowerStaying = (borrowerStayingId == null) ? null : new BorrowerStaying(borrowerStayingId);
        }

        public void setBorrowerBusinessStatusId (Long borrowerBusinessStatusId){
            this.borrowerBusinessStatus = (borrowerBusinessStatusId == null) ? null : new BorrowerBusinessStatus(borrowerBusinessStatusId);
        }

        public void setInterestedFurtherLoanId (Long interestedFurtherLoanId){
            this.interestedFurtherLoan = (interestedFurtherLoanId == null) ? null : new InterestedFurtherLoan(interestedFurtherLoanId);
        }

        public void setSourceOfRecoveryId (Long sourceOfRecoveryId){
            this.sourceOfRecovery = (sourceOfRecoveryId == null) ? null : new SourceOfRecovery(sourceOfRecoveryId);
        }

        public void setBorrowerPresentStatusId (Long borrowerPresentStatusId){
            this.borrowerPresentStatus = (borrowerPresentStatusId == null) ? null : new BorrowerPresentStatus(borrowerPresentStatusId);
        }

        public void setLogicInTermsId (Long logicInTermsId){
            this.logicInTerms = (logicInTermsId == null) ? null : new LogicInTerms(logicInTermsId);
        }

        public void setBorrowerAndGuarantorAvailabilityId (Long borrowerAndGuarantorAvailabilityId){
            this.borrowerAndGuarantorAvailability = (borrowerAndGuarantorAvailabilityId == null) ? null : new BorrowerAndGuarantorAvailability(borrowerAndGuarantorAvailabilityId);
        }

        public void setPossibilityProbabilityUnclassifiedId (Long possibilityProbabilityUnclassifiedId){
            this.possibilityProbabilityUnclassified = (possibilityProbabilityUnclassifiedId == null) ? null : new PossibilityProbability(possibilityProbabilityUnclassifiedId);
        }

        public void setBorrowerGuarantorCapabilityRepayId (Long borrowerGuarantorCapabilityRepayId){
            this.borrowerGuarantorCapabilityRepay = (borrowerGuarantorCapabilityRepayId == null) ? null : new BorrowerGuarantorCapabilityRepay(borrowerGuarantorCapabilityRepayId);
        }

        public void setPossibilityProbabilityClassifiedId (Long possibilityProbabilityClassifiedId){
            this.possibilityProbabilityClassified = (possibilityProbabilityClassifiedId == null) ? null : new PossibilityProbabilityClassified(possibilityProbabilityClassifiedId);
        }

        public void setWayForwardReduceFromNPLId (Long wayForwardReduceFromNPLId){
            this.wayForwardReduceFromNPL = (wayForwardReduceFromNPLId == null) ? null : new WayForwardReduceFromNPL(wayForwardReduceFromNPLId);
        }

        public void setLoanLiabilityRecoverabilityId (Long loanLiabilityRecoverabilityId){
            this.loanLiabilityRecoverability = (loanLiabilityRecoverabilityId == null) ? null : new LoanLiabilityRecoverability(loanLiabilityRecoverabilityId);
        }

        public void setCapableInfluenceCustomerBankDueId (Long capableInfluenceCustomerBankDueId){
            this.capableInfluenceCustomerBankDue = (capableInfluenceCustomerBankDueId == null) ? null : new CapableInfluenceCustomerBankDue(capableInfluenceCustomerBankDueId);
        }

        public void setBbAuditTypeId (Long bbAuditTypeId){
            this.bbAuditType = (bbAuditTypeId == null) ? null : new BBAuditType(bbAuditTypeId);
        }

        public void setBbCommentsForClassificationId (Long bbCommentsForClassificationId){
            this.bbCommentsForClassification = (bbCommentsForClassificationId == null) ? null : new BbCommentsForClassification(bbCommentsForClassificationId);
        }
    }

