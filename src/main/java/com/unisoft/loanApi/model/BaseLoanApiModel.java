package com.unisoft.loanApi.model;

import java.sql.ResultSet;

public interface BaseLoanApiModel {

    void setPropertiesFromResultset(ResultSet data);

}
