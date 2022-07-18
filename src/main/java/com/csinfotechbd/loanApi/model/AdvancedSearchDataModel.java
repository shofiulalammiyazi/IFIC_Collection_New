package com.csinfotechbd.loanApi.model;

import com.csinfotechbd.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;

@Data
public class AdvancedSearchDataModel implements BaseLoanApiModel{

    private String ldNumber;
    private String loanAccountNumber;
    private String loanAccountName;
    private String caseNumber;
    private String customerId;
    private String lln;
    private String lawyersId;
    private String lawyersName;
    private String caseType;
    private String caseStatus;
    private String nextDate;
    private String nextDateStatus;
    private String borrowersOrganization;
    private String borrowersName;
    private String borrowersDob;
    private String borrowersDrivingLicense;
    private String borrowersMobileNo;
    private String existingCaseStatus;
    private String borrowersEmail;
    private String borrowersNid;
    private String resultOfTheCase;
    private String borrowersPassport;
    private String borrowersFathersName;
    private String acStatus;

    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        ldNumber = ResultSetExtractor.getValueFromResultSet(data, "LD_NUMBER", "-");
        loanAccountNumber = ResultSetExtractor.getValueFromResultSet(data, "LOAN_ACCOUNT_NUMBER", "-");
        loanAccountName = ResultSetExtractor.getValueFromResultSet(data, "LOAN_ACCOUNT_NAME", "-");
        caseNumber = ResultSetExtractor.getValueFromResultSet(data, "CASE_NUMBER", "-");
        customerId = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_ID", "-");
        lln = ResultSetExtractor.getValueFromResultSet(data, "LOAN_LIABILITY", "-");
        lawyersId = ResultSetExtractor.getValueFromResultSet(data, "LAWYERS_ID", "-");
        lawyersName = ResultSetExtractor.getValueFromResultSet(data, "LAWYERS_NAME", "-");
        caseType = ResultSetExtractor.getValueFromResultSet(data, "CASE_TYPE", "-");
        caseStatus = ResultSetExtractor.getValueFromResultSet(data, "CASE_STATUS", "-");
        nextDate = ResultSetExtractor.getValueFromResultSet(data, "NEXT_DATE", "-");
        nextDateStatus = ResultSetExtractor.getValueFromResultSet(data, "NEXT_DATE_STATUS", "-");
        borrowersOrganization = ResultSetExtractor.getValueFromResultSet(data, "BORROWER_ORGANIZATION", "-");
        borrowersName = ResultSetExtractor.getValueFromResultSet(data, "BORROWER_NAME", "-");
        borrowersDob = ResultSetExtractor.getValueFromResultSet(data, "BORROWER_DOB", "-");
        borrowersDrivingLicense = ResultSetExtractor.getValueFromResultSet(data, "BORROWER_DL", "-");
        borrowersMobileNo = ResultSetExtractor.getValueFromResultSet(data, "BORROWER_MOBILE_NO", "-");
        existingCaseStatus = ResultSetExtractor.getValueFromResultSet(data, "EXISTING_CASE_STATUS", "-");
        borrowersEmail = ResultSetExtractor.getValueFromResultSet(data, "BORROWER_EMAIL", "-");
        borrowersNid = ResultSetExtractor.getValueFromResultSet(data, "BORROWER_NID", "-");
        resultOfTheCase = ResultSetExtractor.getValueFromResultSet(data, "RESULT_OF_THE_CASE", "-");
        borrowersPassport = ResultSetExtractor.getValueFromResultSet(data, "BORROWER_PASSPORT", "-");
        borrowersFathersName = ResultSetExtractor.getValueFromResultSet(data, "BORROWER_FATHER_NAME", "-");
        acStatus = ResultSetExtractor.getValueFromResultSet(data, "AC_STATUS", "-");
    }
}
