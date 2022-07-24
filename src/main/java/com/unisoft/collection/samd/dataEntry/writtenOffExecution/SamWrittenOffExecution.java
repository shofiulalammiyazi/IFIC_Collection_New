package com.unisoft.collection.samd.dataEntry.writtenOffExecution;

import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "lms_collection_sam_written_off_execution")
public class SamWrittenOffExecution extends CommonEntity {

    private String branchCode;
    private String branchName;

    private String businessRegion;
    private String businessSegment;
//    private String loanLiabilityNo;
//    private String cifNo;

    private String loanAccountNo;
    private String loanAccountName;

//    private String borrowersName;
//    private String facilityNature;
    private String writtenOffDate;

    private Double writtenOffInttSuspenseAmount;
    private Double writtenOffProvisionAmount;
    private Double writtenOffAmount; // writtenOffInttSuspenseAmount + writtenOffProvisionAmount

    private String clStatus;
    private String legalStatus;

//    private String caseFilingDate;
//    private String typeOfCase;
//
//    private String obtainedBbGuidelineRequirements;
//    private String remarks;
    private boolean latest;

    @ManyToOne
    private LoanAccountBasicInfo loanAccountBasicInfo;
}
