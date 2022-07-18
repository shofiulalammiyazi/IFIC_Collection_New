package com.csinfotechbd.loanApi.model;

import com.csinfotechbd.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;

@Data
public class LinkAccStatement implements BaseLoanApiModel {

    private String trnDt;
    private String valueDt;
    private String narration;
    private double debit;
    private double credit;
    private double balance;

    public void setPropertiesFromResultset(ResultSet data) {
        try {
            trnDt = ResultSetExtractor.getValueFromResultSet(data, "TRN_DT", "");
            valueDt = ResultSetExtractor.getValueFromResultSet(data, "VALUE_DT", "");
            narration = ResultSetExtractor.getValueFromResultSet(data, "NARRATION", "-");

            debit = ResultSetExtractor.getDoubleFromResultSet(data, "DEBIT", 0D);
            credit = ResultSetExtractor.getDoubleFromResultSet(data, "CREDIT", 0D);
            balance = ResultSetExtractor.getDoubleFromResultSet(data, "BALANCE", 0D);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
