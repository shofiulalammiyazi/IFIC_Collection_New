package com.unisoft.loanApi.model;

import com.unisoft.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;


/**
 * Created by Yasir Araphat
 * Created at 30 December 2020
 */
@Data
public class OdAccDetails implements BaseLoanApiModel{
    private String custAcNo;
    private String customerNo;
    private String linkedRefNo;
    private String lineExpiryDate;
    private String sanctiondate;
    private String acOpenDate;
    private String limitAmount;
    private String balanceSum;
    private String receivableinst;
    private String accStatus;
    private String accountStatus;
    private String ccy;
    private String acyAvlBal;
    private String acyUncollected;

    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        this.custAcNo = ResultSetExtractor.getValueFromResultSet(data, "CUST_AC_NO", "-");
        this.customerNo = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_NO", "-");
        this.linkedRefNo = ResultSetExtractor.getValueFromResultSet(data, "LINKED_REF_NO", "-");
        this.lineExpiryDate = ResultSetExtractor.getValueFromResultSet(data, "LINE_EXPIRY_DATE", "-");
        this.sanctiondate = ResultSetExtractor.getValueFromResultSet(data, "SANCTIONDATE", "-");
        this.acOpenDate = ResultSetExtractor.getValueFromResultSet(data, "AC_OPEN_DATE", "-");
        this.limitAmount = ResultSetExtractor.getValueFromResultSet(data, "LIMIT_AMOUNT", "-");
        this.balanceSum = ResultSetExtractor.getValueFromResultSet(data, "BALANCE_SUM", "-");
        this.receivableinst = ResultSetExtractor.getValueFromResultSet(data, "RECEIVABLEINST", "-");
        this.accStatus = ResultSetExtractor.getValueFromResultSet(data, "ACC_STATUS", "-");
        this.accountStatus = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_STATUS", "-");
        this.ccy = ResultSetExtractor.getValueFromResultSet(data, "CCY", "-");
        this.acyAvlBal = ResultSetExtractor.getValueFromResultSet(data, "ACY_AVL_BAL", "-");
        this.acyUncollected = ResultSetExtractor.getValueFromResultSet(data, "ACY_UNCOLLECTED", "-");
    }
}
