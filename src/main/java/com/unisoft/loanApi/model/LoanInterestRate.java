package com.unisoft.loanApi.model;

import com.unisoft.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;
import java.util.Date;

/**
 * Created by Yasir Araphat
 * Created at 30 December 2020
 */
@Data
public class LoanInterestRate implements BaseLoanApiModel{

    private String accountNumber;
    private String branchCode;
    private Date effectiveDate;
    private String udeId;
    private double udeValue;
    private String rateCode;
    private String codeUsage;
    private String maintRslvFlag;
    private String resolvedValue;

    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        this.accountNumber = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_NUMBER", "-");
        this.branchCode = ResultSetExtractor.getValueFromResultSet(data, "BRANCH_CODE", "-");
        this.effectiveDate = ResultSetExtractor.getDateFromResultSet(data, "EFFECTIVE_DATE", null);
        this.udeId = ResultSetExtractor.getValueFromResultSet(data, "UDE_ID", "-");
        this.udeValue = ResultSetExtractor.getDoubleFromResultSet(data, "UDE_VALUE", 0D);
//        this.rateCode = ResultSetExtractor.getValueFromResultSet(data, "RATE_CODE", "-");
//        this.codeUsage = ResultSetExtractor.getValueFromResultSet(data, "CODE_USAGE", "-");
//        this.maintRslvFlag = ResultSetExtractor.getValueFromResultSet(data, "MAINT_RSLV_FLAG", "-");
//        this.resolvedValue = ResultSetExtractor.getValueFromResultSet(data, "RESOLVED_VALUE", "-");
    }
}
