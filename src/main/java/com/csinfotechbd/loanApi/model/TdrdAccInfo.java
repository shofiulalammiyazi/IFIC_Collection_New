package com.csinfotechbd.loanApi.model;

import com.csinfotechbd.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;

/**
 * Created by Yasir Araphat
 * Created at 30 December 2020
 */
@Data
public class TdrdAccInfo implements BaseLoanApiModel{

    private String customerNo;
    private String jointAc;
    private String fatherName;
    private String fullName;
    private String customerType;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String acyBlockedAmount;
    private String acOpenDate;
    private String acyAccruedCrIc;
    private String authStat;
    private String penalty;
    private String localBranch;
    private String custAcNo;
    private String interestRate;
    private String accountType;
    private String previousStatementDate;
    private String branchName;
    private String telephone;
    private String oldacno;
    private String autoRollover;
    private String rolloverType;
    private String maturityDate;
    private String city;

    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        this.customerNo = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_NO", "-");
        this.jointAc = ResultSetExtractor.getValueFromResultSet(data, "JOINT_AC", "-");
        this.fatherName = ResultSetExtractor.getValueFromResultSet(data, "FATHER_NAME", "-");
        this.fullName = ResultSetExtractor.getValueFromResultSet(data, "FULL_NAME", "-");
        this.customerType = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_TYPE", "-");
        this.addressLine1 = ResultSetExtractor.getValueFromResultSet(data, "ADDRESSLINE1", "-");
        this.addressLine2 = ResultSetExtractor.getValueFromResultSet(data, "ADDRESSLINE2", "-");
        this.addressLine3 = ResultSetExtractor.getValueFromResultSet(data, "ADDRESSLINE3", "-");
        this.addressLine4 = ResultSetExtractor.getValueFromResultSet(data, "ADDRESSLINE4", "-");
        this.acyBlockedAmount = ResultSetExtractor.getValueFromResultSet(data, "ACY_BLOCKED_AMOUNT", "-");
        this.acOpenDate = ResultSetExtractor.getValueFromResultSet(data, "AC_OPEN_DATE", "-");
        this.acyAccruedCrIc = ResultSetExtractor.getValueFromResultSet(data, "ACY_ACCRUED_CRIC", "-");
        this.authStat = ResultSetExtractor.getValueFromResultSet(data, "AUTH_STAT", "-");
        this.penalty = ResultSetExtractor.getValueFromResultSet(data, "PENALTY", "-");
        this.localBranch = ResultSetExtractor.getValueFromResultSet(data, "LOCAL_BRANCH", "-");
        this.custAcNo = ResultSetExtractor.getValueFromResultSet(data, "CUST_AC_NO", "-");
        this.interestRate = ResultSetExtractor.getValueFromResultSet(data, "INTEREST_RATE", "-");
        this.accountType = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_TYPE", "-");
        this.previousStatementDate = ResultSetExtractor.getValueFromResultSet(data, "PREVIOUS_STATEMENT_DATE", "-");
        this.branchName = ResultSetExtractor.getValueFromResultSet(data, "BRANCH_NAME", "-");
        this.telephone = ResultSetExtractor.getValueFromResultSet(data, "TELEPHONE", "-");
        this.oldacno = ResultSetExtractor.getValueFromResultSet(data, "OLDACNO", "-");
        this.autoRollover = ResultSetExtractor.getValueFromResultSet(data, "AUTO_ROLLOVER", "-");
        this.rolloverType = ResultSetExtractor.getValueFromResultSet(data, "ROLLOVER_TYPE", "-");
        this.maturityDate = ResultSetExtractor.getValueFromResultSet(data, "MATURITY_DATE", "-");
        this.city = ResultSetExtractor.getValueFromResultSet(data, "CITY", "-");
    }
}
