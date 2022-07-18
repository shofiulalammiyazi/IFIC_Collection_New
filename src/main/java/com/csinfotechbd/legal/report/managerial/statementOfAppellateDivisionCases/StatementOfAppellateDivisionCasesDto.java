package com.csinfotechbd.legal.report.managerial.statementOfAppellateDivisionCases;

import lombok.Data;

import javax.persistence.Tuple;
import java.util.Objects;
import java.util.Optional;

@Data
public class StatementOfAppellateDivisionCasesDto {

    private String caseType;
    private String branchName;
    private String accountNumber;
    private String petitionerName;
    private String subjectMatterOfCase;
    private String firstOrderDate;
    private String caseNumber;
    private String dateOfFiling;
    private double suitValue;
    private String lawyerName;
    private String lawyerMobileNo;
    private String caseStatus;

    public StatementOfAppellateDivisionCasesDto() {
    }

    public StatementOfAppellateDivisionCasesDto(Tuple data) {
        caseType = Objects.toString(data.get("CASE_TYPE"), "-");
        branchName = Objects.toString(data.get("BRANCH_NAME"), "-");
        accountNumber = Objects.toString(data.get("ACCOUNT_NUMBER"), "-");
        petitionerName = Objects.toString(data.get("PETITIONER_NAME"), "-");
        subjectMatterOfCase = Objects.toString(data.get("SUBJECT_MATTER_OF_CASE"), "-");
        firstOrderDate = Objects.toString(data.get("FIRST_ORDER_DATE"), "-");
        caseNumber = Objects.toString(data.get("CASE_NUMBER"), "-");
        dateOfFiling = Objects.toString(data.get("DATE_OF_FILING"), "-");
        suitValue = ((Number) Optional.ofNullable(data.get("SUIT_VALUE")).orElse(0)).doubleValue();
        lawyerName = Objects.toString(data.get("LAWYER_NAME"), "-");
        lawyerMobileNo = Objects.toString(data.get("LAWYER_MOBILE_NO"), "-");
        caseStatus = Objects.toString(data.get("CASE_STATUS"), "-");
    }
}
