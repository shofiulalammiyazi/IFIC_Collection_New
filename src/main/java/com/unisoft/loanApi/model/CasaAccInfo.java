package com.unisoft.loanApi.model;

import com.unisoft.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;


/**
 * Created by Yasir Araphat
 * Created at 30 December 2020
 */
@Data
public class CasaAccInfo implements BaseLoanApiModel{

    private String customerNo;
    private String acCcy;
    private String jointAc;
    private String fatherName;
    private String fullName;
    private String customerType;
    private String acOpenDate;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String authStat;
    private String localBranch;
    private String custAcNo;
    private String accountType;
    private String previousStatementDate;
    private String branchName;
    private String telephone;
    private String oldacno;
    private String city;
    private String acyAvlBal;
    private String acyUncollected;


    private boolean is_Blocked;
    private String cust_Account;


    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        this.customerNo = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_NO", "-");
        this.acCcy = ResultSetExtractor.getValueFromResultSet(data, "AC_CCY", "-");
        this.jointAc = ResultSetExtractor.getValueFromResultSet(data, "JOINT_AC", "-");
        this.fatherName = ResultSetExtractor.getValueFromResultSet(data, "FATHER_NAME", "-");
        this.fullName = ResultSetExtractor.getValueFromResultSet(data, "FULL_NAME", "-");
        this.customerType = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_TYPE", "-");
        this.acOpenDate = ResultSetExtractor.getValueFromResultSet(data, "AC_OPEN_DATE", "-");
        this.addressLine1 = ResultSetExtractor.getValueFromResultSet(data, "ADDRESS_LINE1", "-");
        this.addressLine2 = ResultSetExtractor.getValueFromResultSet(data, "ADDRESS_LINE2", "-");
        this.addressLine3 = ResultSetExtractor.getValueFromResultSet(data, "ADDRESS_LINE3", "-");
        this.addressLine4 = ResultSetExtractor.getValueFromResultSet(data, "ADDRESS_LINE4", "-");
        this.authStat = ResultSetExtractor.getValueFromResultSet(data, "AUTH_STAT", "-");
        this.localBranch = ResultSetExtractor.getValueFromResultSet(data, "LOCAL_BRANCH", "-");
        this.custAcNo = ResultSetExtractor.getValueFromResultSet(data, "CUST_AC_NO", "-");
        this.accountType = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_TYPE", "-");
        this.previousStatementDate = ResultSetExtractor.getValueFromResultSet(data, "PREVIOUS_STATEMENT_DATE", "-");
        this.branchName = ResultSetExtractor.getValueFromResultSet(data, "BRANCH_NAME", "-");
        this.telephone = ResultSetExtractor.getValueFromResultSet(data, "TELEPHONE", "-");
        this.oldacno = ResultSetExtractor.getValueFromResultSet(data, "OLDACNO", "-");
        this.city = ResultSetExtractor.getValueFromResultSet(data, "CITY", "-");
        this.acyAvlBal = ResultSetExtractor.getValueFromResultSet(data, "ACY_AVL_BAL", "-");
        this.acyUncollected = ResultSetExtractor.getValueFromResultSet(data, "ACY_UNCOLLECTED", "-");
        this.is_Blocked = ResultSetExtractor.getValueFromResultSet(data, "IS_BLOCKED", false);
        this.cust_Account = ResultSetExtractor.getValueFromResultSet(data, "CUST_ACCOUNT", "-");
    }
}
