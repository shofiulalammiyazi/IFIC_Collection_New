package com.csinfotechbd.loanApi.model;

import com.csinfotechbd.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;


/**
 * Created by Yasir Araphat
 * Modified by Abu Salek Shaon
 * Created at 30 December 2020
 */
@Data
public class LoanAccInfo implements BaseLoanApiModel{


    private double disburseLoanAmount;
    private String branchCode;
    private String altAccNo;
    private String disbursmentDt; // Format dd-MMM-yyyy
    private String sanctionedLoanAmount;
    private String nextInstallmentDate;
    private String overdueAmount;
    private Long tenor;
    private double noOfOverdueInstallment;
    private String interestRate;
    private String customerId;
    private String accountNumber;
    private String accountName;
    private String accountStatus;       //Loan Status
    private long noOfInstallmentPaid;
    private double installmentAmount;
    private String loanAccountNo;
    private String loanAccountName;

//    new field

    private String optimaId;
    private String rmCode;
    private String ecoPurposeCode;
    private String ecoPurposeName;
    private double exciseDuty;
    private String isWriteOff;
    private String source;

    private double interestIncome;

    private double otherCharge;

    private double totalRecoveryDuringMonth;

    private String userDefineStatus;


    private String productCategory;

    private double principalDue;
    private double totalOverdue;

    private String scheduleCurDate;




    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        this.disburseLoanAmount = ResultSetExtractor.getDoubleFromResultSet(data, "DISBURSE_LOAN_AMOUNT", 0D);
        this.branchCode = ResultSetExtractor.getValueFromResultSet(data, "BRANCH_CODE", "-");
        this.altAccNo = ResultSetExtractor.getValueFromResultSet(data, "ALT_ACC_NO", "-");
        this.disbursmentDt = ResultSetExtractor.getValueFromResultSet(data, "DISBURSMENT_DT", "-");
        this.tenor = ResultSetExtractor.getLongFromResultSet(data, "TOTAL_INSTALLMENT", 0L);
        this.noOfOverdueInstallment = ResultSetExtractor.getDoubleFromResultSet(data, "NO_OF_OVERDUE_INSTALLMENT", 0D);
//        this.interestRate = ResultSetExtractor.getValueFromResultSet(data, "INTEREST_RATE", "-");
        this.customerId = ResultSetExtractor.getValueFromResultSet(data, "CUSTOMER_ID", "-");
        this.accountNumber = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_NUMBER", "-");
        this.accountName = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_NAME", "-");
        this.accountStatus = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_STATUS", "-");
        this.noOfInstallmentPaid = ResultSetExtractor.getLongFromResultSet(data, "NO_OF_INSTALLMENT_PAID", 0L);
        this.installmentAmount = ResultSetExtractor.getDoubleFromResultSet(data, "INSTALLMENT_AMOUNT", 0D);
        this.loanAccountNo = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_NUMBER", "-");
        this.loanAccountName = ResultSetExtractor.getValueFromResultSet(data, "ACCOUNT_NAME", "-");
        this.optimaId = ResultSetExtractor.getValueFromResultSet(data, "OPTIMA_ID", "-");
        this.rmCode = ResultSetExtractor.getValueFromResultSet(data, "RM_CODE", "-");
        this.ecoPurposeCode = ResultSetExtractor.getValueFromResultSet(data, "ECO_PURPOSE_CODE", "-");
        this.ecoPurposeName = ResultSetExtractor.getValueFromResultSet(data, "ECO_PURPOSE_NAME", "-");
        this.exciseDuty = ResultSetExtractor.getDoubleFromResultSet(data, "EXCISE_DUTY", 0D);
        this.isWriteOff = ResultSetExtractor.getValueFromResultSet(data,"IS_WRITEOFF", "-");
        this.source = ResultSetExtractor.getValueFromResultSet(data,"SOURCE", "-");
        this.interestIncome = ResultSetExtractor.getDoubleFromResultSet(data, "INTEREST_INCOME", 0D);
        this.otherCharge = ResultSetExtractor.getDoubleFromResultSet(data, "OTHER_CHARGES", 0D);
        this.totalRecoveryDuringMonth = ResultSetExtractor.getDoubleFromResultSet(data, "TOTAL_RECOVARY_CUR_MONTH", 0D);
        this.userDefineStatus = ResultSetExtractor.getValueFromResultSet(data, "USER_DEFINED_STATUS", "-");
        this.productCategory = ResultSetExtractor.getValueFromResultSet(data, "PRODUCT_CATEGORY", "-");
        this.principalDue = ResultSetExtractor.getDoubleFromResultSet(data, "PRINCIPAL_DUE", 0D);
        this.totalOverdue = ResultSetExtractor.getDoubleFromResultSet(data, "TOTAL_OVERDUE", 0D);

        this.scheduleCurDate = ResultSetExtractor.getValueFromResultSet(data,"SCHEDULE_CUR_DATE", "-");

    }

    public LoanAccInfo() {

    }
}
