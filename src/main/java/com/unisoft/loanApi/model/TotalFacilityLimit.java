package com.unisoft.loanApi.model;

import com.unisoft.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;

@Data
public class TotalFacilityLimit implements BaseLoanApiModel {
    private String accountNo;
    private double loanLimit;
    private String productCode;
    private String productDesc;
    private double outstanding;
    private String overdue;
    private String status;

    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        this.accountNo = ResultSetExtractor.getValueFromResultSet(data, "CONT_REF_NO", "-");
        this.loanLimit = ResultSetExtractor.getDoubleFromResultSet(data, "LIMIT_AMOUNT", 0D);
        this.productDesc = ResultSetExtractor.getValueFromResultSet(data, "PRODDES", "-");
        this.outstanding = ResultSetExtractor.getDoubleFromResultSet(data, "OUTSTANDING_LOCAL_CURRENCY", 0D);
    }
}
