package com.csinfotechbd.loanApi.model;

import com.csinfotechbd.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;

@Data
public class CustAccList implements BaseLoanApiModel {

    private String accountNo;
    private String accountName;
    private String productDesc;
    private double loanLimit;
    private double outstanding;
    private double deposit;


    @Override
    public void setPropertiesFromResultset(ResultSet data) {

        this.accountNo = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNTNO", "-");
        this.accountName = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNTNAME", "-");
        this.loanLimit = ResultSetExtractor.getDoubleFromResultSet(data, "LIMITAM", 0D);
        this.productDesc = ResultSetExtractor.getValueFromResultSet(data, "PRODUCT", "-");
        this.outstanding = ResultSetExtractor.getDoubleFromResultSet(data, "BALANCE", 0D);
        this.deposit = ResultSetExtractor.getDoubleFromResultSet(data, "TOTAL_DEPOSIT", 0D);


    }
}
