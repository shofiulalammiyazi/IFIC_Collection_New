package com.unisoft.loanApi.model;

import com.unisoft.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;


/**
 * Created by
 * Created at 23 February 2021
 */
@Data
public class BranchInfo implements BaseLoanApiModel {

    private String branchName;

    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        this.branchName = ResultSetExtractor.getValueFromResultSet(data, "BRANCH_NAME", "-");
    }
}
