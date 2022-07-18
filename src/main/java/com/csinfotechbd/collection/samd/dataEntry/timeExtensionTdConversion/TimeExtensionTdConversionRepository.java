package com.csinfotechbd.collection.samd.dataEntry.timeExtensionTdConversion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;

@Repository
public interface TimeExtensionTdConversionRepository extends JpaRepository<TimeExtensionTdConversion, Long> {

    TimeExtensionTdConversion findByCustomerId(Long customerId);

    @Query(value = " SELECT PD.ID                         as id, " +
            "       PD.PROPOSAL_TYPE                      as proposalType, " +
            "       PD.BRANCH_NAME                        as branchName, " +
            "       PD.LOAN_ACCOUNT_NO                    as loanAccountNo, " +
            "       PD.LOAN_ACCOUNT_NAME                  as loanAccountName, " +
            "       PD.FACILITY_NATURE                    as facilityNature, " +
            "       PD.DISBURSEMENT_DATE                  as disbursementDate, " +
            "       PD.LEGAL_DETAIL_STATUS                as legalDetailStatus, " +
            "       PD.FILLING_AMOUNT                     as fillingAmount, " +
            "       PD.TYPE_OF_CASE                       as typeOfCase, " +
            "       PD.EXPIRY_DATE                        as expiryDate, " +
            "       PD.PROPOSAL_SEND_BY                   as proposalSendBy, " +
            "       PD.MEMO_NOTE_PREPARED_BY              as memoNotePreparedBy, " +
            "       ASCL.NAME                             as cLStatus, " +
            "       PD.CL_STATUS                          as cLStatusId, " +
            "       PD.PROPOSAL_PLACED_TO                 as proposalPlacedToId, " +
            "       HR.NAME                               as propPlaceName, " +
            "       PD.APPROVAL_LEVEL                     as approvalLevelId, " +
            "       AP.NAME                               as approvalLevelName,  " +
            "       PD.PROPOSAL_PREPARED_FOR              as proposalPreparedForId, " +
            "       PPR.NAME                              as proposalPreparedFor,       " +
            "       PD.PURPOSE_OF_THE_PROPOSAL            as purposeOfTheProposalId, " +
            "       PP.NAME                               as purposeOfTheProposal,       " +
            "       PD.EXISTING_CASE_STATUS               as existingCaseStatus, " +
            "       PD.DYNAMIC_CALCULATOR_FORTETDCONVERSION  as dynamicCalculatorForTETDConversion, " +
            "       PD.TOTAL_NO_OFTETDC                   as totalNoOfTETDC, " +
            "       PD.PROPOSED_EXTENDED_DATE             as proposedExtendedDate, " +
            "       PD.SANCTION_ACCEPTED_BY_BORROWER      as sanctionAcceptedByBorrower, " +
            "       PD.SANCTION_ACCEPTED_DATE_BY_BORROWER as sanctionAcceptedDateByBorrower, " +
            "       PD.CAPITAL_RELEASE                    as capitalRelease, " +
            "       PD.NPL_REDUCED                        as npLReduced, " +
            "       PD.REMARKS_OR_COMMENTS                as remarksOrComments, " +
            "       PD.TOTAL_LEGAL_AND_OTHER_EXPENSE      as totalLegalAndOtherExpense, " +
            "       PDF.FILE_NAME                         as fileName, " +
            "       PDF.DMS_FILE_ID                       as dmsFileId, " +
            "       PDF.DMS_FILE_TYPE                     as dmsFileType " +
            " FROM   LMS_COLLECTION_SAM_TIME_EXTENSION_TD_CONVERSION PD " +
            "       LEFT JOIN TIME_EXTENSION_TD_CONVERSION_FILES PDF ON PDF.TIME_EXTENSION_TD_CONVERSION_ID = PD.ID " +
            "       LEFT JOIN ASSET_CLASS_LOAN ASCL ON PD.CL_STATUS = ASCL.ID " +
            "       LEFT JOIN HR_POSITION HR ON PD.PROPOSAL_PLACED_TO = HR.ID " +
            "       LEFT JOIN LMS_COLLECTION_SAM_APPROVAL_LEVEL AP ON PD.APPROVAL_LEVEL = AP.ID " +
            "       LEFT JOIN LMS_COLLECTION_SAM_PROPOSAL_PURPOSE PP ON PD.PURPOSE_OF_THE_PROPOSAL=PP.ID " +
            "       LEFT JOIN LMS_COLLECTION_SAM_PROPOSAL_PREPARED_REASON PPR ON PD.PROPOSAL_PREPARED_FOR = PPR.ID " +
            " WHERE PD.CUSTOMER_ID =? ", nativeQuery = true)
    Tuple findByTimeExtensionCustomerId(Long customerId);


}
