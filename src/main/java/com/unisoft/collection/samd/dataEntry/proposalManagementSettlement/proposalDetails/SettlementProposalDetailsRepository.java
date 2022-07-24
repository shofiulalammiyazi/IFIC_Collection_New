package com.unisoft.collection.samd.dataEntry.proposalManagementSettlement.proposalDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;

@Repository
public interface SettlementProposalDetailsRepository extends JpaRepository<SettlementProposalDetails, Long> {

    SettlementProposalDetails findProposalDetailsByCustomerId(String customerId);

    @Query(value = " SELECT PD.ID    as id, " +
            "       PD.PROPOSAL_TYPE                      as proposalType, " +
            "       PD.BRANCH_NAME                        as branchName, " +
            "       PD.LOAN_ACCOUNT_NO                    as loanAccountNo, " +
            "       PD.LOAN_ACCOUNT_NAME                  as loanAccountName, " +
            "       PD.FACILITY_NATURE                    as facilityNature, " +
            "       ASCL.NAME                             as cLStatus, " +
            "       PD.CLSTATUS_ID                        as cLStatusId, " +
            "       PD.PROPOSAL_PLACED_TO_ID              as proposalPlacedToId, " +
            "       HR.NAME                               as propPlaceName, " +
            "       PD.APPROVAL_LEVEL_ID                  as approvalLevelId, " +
            "       AP.NAME                               as approvalLevelName, " +
            "       PD.PROPOSAL_STATUS_ID                 as proposalStatusId, " +
            "       PS.NAME                               as proposalStatus, " +
            "       PD.DISBURSEMENT_DATE                  as disbursementDate, " +
            "       PD.EXPIRY_DATE                        as expiryDate, " +
            "       PD.PROPOSAL_SEND_BY                   as proposalSendBy, " +
            "       PD.MEMO_NOTE_PREPARED_BY              as memoNotePreparedBy, " +
            "       PD.FILLING_AMOUNT                     as fillingAmount, " +
            "       PD.TYPE_OF_CASE                       as typeOfCase, " +
            "       PD.VALUE_ELIGIBLE_SECURITY            as valueEligibleSecurity, " +
            "       PD.PROVISION                          as provision, " +
            "       PD.PRINCIPLE_AMOUNT                   as principleAmount, " +
            "       PD.INTEREST_TAKEN                     as interestTaken, " +
            "       PD.INTEREST_KEPT                      as interestKept, " +
            "       PD.LEGAL_BILL_OR_OTHER_EXPENSE        as legalBillOrOtherExpense, " +
            "       PD.SUB_TOTAL                          as subTotal, " +
            "       PD.TOTAL_RECOVERY                     as totalRecovery, " +
            "       PD.LEDGER_OUTSTANDING                 as ledgerOutstanding, " +
            "       PD.UNAPPLIED_INTEREST                 as unappliedInterest, " +
            "       PD.TOTAL_CLAIM_AMOUNT                 as totalClaimAmount, " +
            "       PD.EXISTING_CASE_STATUS               as existingCaseStatus, " +
            "       PD.DYNAMIC_CALCULATOR                 as dynamicCalculator,     " +
            "       PD.PROPOSED_WAIVER_UNAPPLIED_INTEREST as proposedWaiverUnappliedInterest, " +
            "       PD.PROPOSED_WAIVER_INTEREST_SUSPENSE  as proposedWaiverInterestSuspense, " +
            "       PD.PROPOSED_WAIVER_INCOME             as proposedWaiverIncome, " +
            "       PD.PROPOSED_TOTAL_WAIVER              as proposedTotalWaiver, " +
            "       PD.BORROWER_WANTS_TO_PAY              as borrowerWantsToPay, " +
            "       PD.TENTATIVE_SETTLEMENT_DATE          as tentativeSettlementDate, " +
            "       PD.INTEREST_INCOME                    as interestIncome, " +
            "       PD.CAPITAL_RELEASE                    as capitalRelease, " +
            "       PD.NPLREDUCED                         as npLReduced, " +
            "       PD.REMARKS                            as remarks, " +
            "       PD.LEGAL_DETAIL_STATUS                as legalDetailStatus, " +
            "       PD.TOTAL_LEGAL_AND_OTHER_EXPENSE      as totalLegalAndOtherExpense, " +
            "       PDF.FILE_NAME                         as fileName, " +
            "       PDF.DMS_FILE_ID                       as dmsFileId, " +
            "       PDF.DMS_FILE_TYPE                     as dmsFileType " +
            " FROM   LMS_COLLECTION_SAM_SETTLEMENT_PROPOSAL_DETAILS PD " +
            "       LEFT JOIN SETTLEMENT_PROPOSAL_DETAILS_FILES PDF ON PDF.SETTLEMENT_PROPOSAL_DETAILS_ID = PD.ID " +
            "       LEFT JOIN ASSET_CLASS_LOAN ASCL ON PD.CLSTATUS_ID = ASCL.ID " +
            "       LEFT JOIN HR_POSITION HR ON PD.PROPOSAL_PLACED_TO_ID = HR.ID " +
            "       LEFT JOIN LMS_COLLECTION_SAM_APPROVAL_LEVEL AP ON PD.APPROVAL_LEVEL_ID = AP.ID " +
            "       LEFT JOIN PROPOSAL_STATUS PS ON PD.PROPOSAL_STATUS_ID = PS.ID " +
            " WHERE PD.CUSTOMER_ID=? ", nativeQuery = true)
    Tuple getBySettleMentDetailsCustomerId(String customerId);


}
