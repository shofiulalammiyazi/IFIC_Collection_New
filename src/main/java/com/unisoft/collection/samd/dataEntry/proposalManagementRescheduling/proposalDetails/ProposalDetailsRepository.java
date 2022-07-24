package com.unisoft.collection.samd.dataEntry.proposalManagementRescheduling.proposalDetails;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;

@Repository
public interface ProposalDetailsRepository extends JpaRepository<ProposalDetails, Long> {

    ProposalDetails findProposalDetailsByCustomerId(String customerId);


    @Query(value = "SELECT PD.ID                                 as id,  " +
           "PD.PROPOSAL_TYPE                      as proposalType, " +
            "       PD.BRANCH_NAME                        as branchName, " +
            "       PD.LOAN_ACCOUNT_NO                    as loanAccountNo, " +
            "       PD.LOAN_ACCOUNT_NAME                  as loanAccountName, " +
            "       PD.FACILITY_NATURE                    as facilityNature, " +
            "       ASCL.NAME                             as cLStatus, " +
            "       PD.CLSTATUS_ID                        as cLStatusId, " +
            "       PD.DISBURSEMENT_DATE                  as disbursementDate, " +
            "       PD.EXPIRY_DATE                        as expiryDate, " +
            "       PD.PROPOSAL_SEND_BY                   as proposalSendBy, " +
            "       PD.MEMO_NOTE_PREPARED_BY              as memoNotePreparedBy, " +
            "       PD.PROPOSAL_PLACED_TO_ID              as proposalPlacedToId, " +
            "       HR.NAME                               as propPlaceName, " +
            "       PD.APPROVAL_LEVEL_ID                  as approvalLevelId, " +
            "       AP.NAME                               as approvalLevelName, " +
            "       PD.PROPOSAL_STATUS_ID                 as proposalStatusId, " +
            "       PS.NAME                               as proposalStatus, " +
            "       PD.TOTAL_RECOVERY                     as totalRecovery, " +
            "       PD.OVERDUE                            as overdue, " +
            "       PD.OUTSTANDING                        as outstanding, " +
            "       PD.FILLING_AMOUNT                     as fillingAmount, " +
            "       PD.TYPE_OF_CASE                       as typeOfCase, " +
            "       PD.INTEREST_SUSPENSE                  as interestSuspense, " +
            "       PD.PROVISION                          as provision, " +
            "       PD.UNAPPLIED_INTEREST                 as unappliedInterest, " +
            "       PD.TOTAL_CLAIM_AMOUNT                 as totalClaimAmount, " +
            "       PD.VALUE_ELIGIBLE_SECURITY            as valueEligibleSecurity, " +
            "       PD.PROPOSED_WAIVER_UNAPPLIED_INTEREST as proposedWaiverUnappliedInterest, " +
            "       PD.PROPOSED_WAIVER_INTEREST_SUSPENSE  as proposedWaiverInterestSuspense, " +
            "       PD.PROPOSED_WAIVER_INCOME             as proposedWaiverIncome, " +
            "       PD.PROPOSED_TOTAL_WAIVER              as proposedTotalWaiver, " +
            "       PD.OUTSTANDING_BEFORE_DOWN_PAYMENT    as outstandingBeforeDownPayment, " +
            "       PD.DOWN_PAYMENT_DETERMINING_CRITERIA  as downPaymentDeterminingCriteria, " +
            "       PD.BASIS_OBTAINING_DOWN_PAYMENT       as basisObtainingDownPayment, " +
            "       PD.REQUIRED_DOWN_PAYMENT              as requiredDownPayment, " +
            "       PD.REALIZED_DOWN_PAYMENT              as realizedDownPayment, " +
            "       PD.REMAINING_DOWN_PAYMENT             as remainingDownPayment, " +
            "       PD.OUTSTANDING_BEFORE_DOWN_PAYMENT    as outstandingAfterDownPayment, " +
            "       PD.EXISTING_CASE_STATUS               as existingCaseStatus, " +
            "       PD.DYNAMIC_CALCULATOR                 as dynamicCalculator, " +
            "       PRT.RESCHEDULE_TIMES                  as proposedRescheduleTimes, " +
            "       PD.PROPOSED_RESCHEDULE_TIMES_ID       AS proposedRescheduleTimesId, " +
            "       PD.ALLOWABLE_MAX_RESCHEDULE_MONTHS    AS allowableMaxRescheduleMonths, " +
            "       PD.ALLOWED_TIME_RESHEDULEMENT         as allowedTimeReshedulement, " +
            "       PD.RELEVANT_SUBJECT_MEMO              as relevantSubjectMemo, " +
            "       PD.DATE_LASTCIBREPORT                 as dateLastCIBReport, " +
            "       BBA.APPROVAL_REQUIREMENT              as bBApprovalRequirement, " +
            "       PD.BBAPPROVAL_REQUIREMENT_ID          as bBApprovalRequirementId, " +
            "       PD.LETTER_ISSUING_DATEBBAPPROVAL      as letterIssuingDateBBApproval, " +
            "       PD.BBAPPROVAL_RECEIVING_DATE          as bBApprovalReceivingDate, " +
            "       PD.SANCTION_ISSUING_DATE              as sanctionIssuingDate, " +
            "       PD.RESCHEDULE_EXECUTION_DATE          as rescheduleExecutionDate, " +
            "       PD.RESCHEDULED_ACCOUNT_NO             as rescheduledAccountNo, " +
            "       PD.INTEREST_INCOME                    as interestIncome, " +
            "       PD.CAPITAL_RELEASE                    as capitalRelease, " +
            "       PD.NPLREDUCED                         as npLReduced, " +
            "       PD.REMARKS                            as remarks, " +
            "       PD.TOTAL_LEGAL_AND_OTHER_EXPENSE      as totalLegalAndOtherExpense, " +
            "       PDF.FILE_NAME                         as fileName, " +
            "       PDF.DMS_FILE_ID                       as dmsFileId, " +
            "       PDF.DMS_FILE_TYPE                     as dmsFileType " +
            " FROM PROPOSAL_DETAILS PD " +
            " LEFT JOIN PROPOSAL_DETAILS_FILES PDF ON PDF.PROPOSAL_DETAILS_ID = PD.ID " +
            "       LEFT JOIN ASSET_CLASS_LOAN ASCL ON PD.CLSTATUS_ID = ASCL.ID " +
            "       LEFT JOIN HR_POSITION HR ON PD.PROPOSAL_PLACED_TO_ID = HR.ID " +
            "       LEFT JOIN LMS_COLLECTION_SAM_APPROVAL_LEVEL AP ON PD.APPROVAL_LEVEL_ID = AP.ID " +
            "       LEFT JOIN PROPOSAL_STATUS PS ON PD.PROPOSAL_STATUS_ID = PS.ID " +
            "       LEFT JOIN PROPOSED_RESCHEDULE_TIMES PRT ON PD.PROPOSED_RESCHEDULE_TIMES_ID = PRT.ID " +
            "       LEFT JOIN BBAPPROVAL_REQUIREMENT BBA ON PD.BBAPPROVAL_REQUIREMENT_ID = BBA.ID " +
            " WHERE PD.CUSTOMER_ID=? ", nativeQuery = true)
    Tuple getByProposalDetailCustomerId(String customerId);
}
