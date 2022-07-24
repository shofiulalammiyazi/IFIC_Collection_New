package com.unisoft.loanApi.model;

import com.unisoft.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;
import java.util.Date;


/**
 * Created by Yasir Araphat
 * Created at 23 February 2021
 */
@Data
public class AdvanceSearchDataModel implements BaseLoanApiModel {

    private String accountNo;
    private String cifNo;
    private String customerName;
    private Date dob;
    private String mobileNo;
    private String outstanding;
    private String overdue;
    private String linkProxyAcc;
    private String mothersName;
    private String email;
    private String acStatus;
    private String nid;
    private String homePhone;
    private String workPhone;

    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        accountNo = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_NO", "-");
        cifNo = ResultSetExtractor.getValueFromResultSet(data, "CIF_NO", "-");
        customerName = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_NAME", "-");
        dob = ResultSetExtractor.getDateFromResultSet(data, "DOB", null);
        mobileNo = ResultSetExtractor.getValueFromResultSet(data, "MOBILE_NO", "-");
        outstanding = ResultSetExtractor.getValueFromResultSet(data, "OUTSTANDING", "-");
        overdue = ResultSetExtractor.getValueFromResultSet(data, "OVERDUE", "-");
        linkProxyAcc = ResultSetExtractor.getValueFromResultSet(data, "LINK_PROXY_ACC", "-");
        mothersName = ResultSetExtractor.getValueFromResultSet(data, "MOTHERS_NAME", "-");
        email = ResultSetExtractor.getValueFromResultSet(data, "EMAIL", "-");
        acStatus = ResultSetExtractor.getValueFromResultSet(data, "AC_STATUS", "-");
        nid = ResultSetExtractor.getValueFromResultSet(data, "NID", "-");
        homePhone = ResultSetExtractor.getValueFromResultSet(data, "HOME_PHONE", "-");
        workPhone = ResultSetExtractor.getValueFromResultSet(data, "WORK_PHONE", "-");
    }
}
