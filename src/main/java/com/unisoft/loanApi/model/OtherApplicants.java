package com.unisoft.loanApi.model;

import com.unisoft.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;

/**
 * Created by Yasir Araphat
 * Created at 19 September, 2020
 */
@Data
public class OtherApplicants implements BaseLoanApiModel{

    private String customerName;

    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        this.customerName = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_NAME", "-");
    }
}
