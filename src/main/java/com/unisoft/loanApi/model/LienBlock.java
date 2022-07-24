package com.unisoft.loanApi.model;

import com.unisoft.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;


/**
 * Created by Yasir Araphat
 * Created at 30 December 2020
 */
@Data
public class LienBlock implements BaseLoanApiModel{

    private String blockedAccountNo;
    private String custNo;
    private String accountDescription;
    private String accountType;
    private String lcyCurrBalance;
    private String fromDate;
    private String tillDate;
    private String lienBlockedAmount;
    private String fdrRate;
    private String fdrMaturityDate;
    private String loanAccountNo;
    private String loanAccountName;
    private String loanAcInterestRate;
    private String loanAccountExpiryDate;
    private String narration;
    private String amountBlockNo;

    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        this.blockedAccountNo = ResultSetExtractor.getValueFromResultSet(data, "BLOCKED_ACCOUNT_NO", "-");
        this.custNo = ResultSetExtractor.getValueFromResultSet(data, "CUST_NO", "-");
        this.accountDescription = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_DESCRIPTION", "-");
        this.accountType = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_TYPE", "-");
        this.lcyCurrBalance = ResultSetExtractor.getValueFromResultSet(data, "LCY_CURRBALANCE", "-");
        this.fromDate = ResultSetExtractor.getValueFromResultSet(data, "FROM_DATE", "-");
        this.tillDate = ResultSetExtractor.getValueFromResultSet(data, "TILL_DATE", "-");
        this.lienBlockedAmount = ResultSetExtractor.getValueFromResultSet(data, "LIEN_BLOCKED_AMOUNT", "-");
        this.fdrRate = ResultSetExtractor.getValueFromResultSet(data, "FDR_RATE", "-");
        this.fdrMaturityDate = ResultSetExtractor.getValueFromResultSet(data, "fdr_Maturity_Date", "-");
        this.loanAccountNo = ResultSetExtractor.getValueFromResultSet(data, "LOAN_ACCOUNT_NO", "-");
        this.loanAccountName = ResultSetExtractor.getValueFromResultSet(data, "LOAN_ACCOUNT_NAME", "-");
        this.loanAcInterestRate = ResultSetExtractor.getValueFromResultSet(data, "LOAN_AC_INTEREST_RATE", "-");
        this.loanAccountExpiryDate = ResultSetExtractor.getValueFromResultSet(data, "LOAN_ACCOUNT_EXPIRY_DATE", "-");
        this.narration = ResultSetExtractor.getValueFromResultSet(data, "NARRATION", "-");
        this.amountBlockNo = ResultSetExtractor.getValueFromResultSet(data, "AMOUNT_BLOCK_NO", "-");
    }
}
