package com.unisoft.collection.samd.dataEntry.timeExtensionTdConversion;

import lombok.Data;

@Data
public class TimeExtensionTdConversionDto {

    private Object id;
    private Object proposalType;
    private Object branchName;
    private Object loanAccountNo;
    private Object loanAccountName;
    private Object facilityNature;
    private Object disbursementDate;
    private Object legalDetailStatus;
    private Object fillingAmount;
    private Object typeOfCase;
    private Object expiryDate;
    private Object proposalSendBy;
    private Object memoNotePreparedBy;

    private Object cLStatus;
    private Object cLStatusId;
    private Object proposalPlacedToId;
    private Object propPlaceName;
    private Object approvalLevelId;
    private Object approvalLevelName;
    private Object proposalPreparedFor;
    private Object proposalPreparedForId;
    private Object purposeOfTheProposal;
    private Object purposeOfTheProposalName;

    private Object existingCaseStatus;
    private Object dynamicCalculatorForTETDConversion;
    private Object totalNoOfTETDC;
    private Object proposedExtendedDate;
    private Object sanctionAcceptedByBorrower;
    private Object sanctionAcceptedDateByBorrower;
    private Object capitalRelease;
    private Object nplReduced;
    private Object remarksOrComments;
    private Object totalLegalAndOtherExpense;

    private Object fileName;
    private Object dmsFileId;
    private Object dmsFileType;
}
