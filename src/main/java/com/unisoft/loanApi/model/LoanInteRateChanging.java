package com.unisoft.loanApi.model;


import com.unisoft.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;
import java.util.Date;

@Data
public class LoanInteRateChanging implements BaseLoanApiModel{

    private String accNo;
    private Date changingTime;
    private double udeValue;

    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        this.accNo = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_NUMBER", "-");
        this.changingTime = ResultSetExtractor.getDateFromResultSet(data, "EFFECTIVE_DATE", null);
        this.udeValue = ResultSetExtractor.getDoubleFromResultSet(data, "UDE_VALUE", 0D);
    }
}
