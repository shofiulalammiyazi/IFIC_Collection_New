package com.csinfotechbd.loanApi.model;

import com.csinfotechbd.loanApi.utils.ResultSetExtractor;
import lombok.Data;

import java.sql.ResultSet;
import java.util.Date;

@Data
public class LoanAccStatement implements BaseLoanApiModel {

    //    private String sl;
    private String trndate;
    private String valdate;
    private Date valueDate;
    private double lcyAmount;
    private double principalDebit;
    private double principalCredit;
    private double interestDebit;
    private double interestCredit;
    private double interestBal;
    private double totalInterest;
    private double principalBal;
    private double totalPrinciple;
    private double totalOutstanding;
    private String descriptoion;
    private String transactionRef;

    private double lastPayment;
    private double totalPrincipalPaid;
    private double totalInterestPaid;
    private double totalPaymentCurrentMonth;
    private double lastPaidAmount;
    private double totalSubsidyFromBbCovid;
    private double totalRolloverOfContract;
    private double totalWaivedInterest;
    private String event;

    private double totalPayment;

    private double totalInteWithOutRoll;
    private double otherCharge;

    private double totalCustomerPaid;



    @Override
    public void setPropertiesFromResultset(ResultSet data) {
        try {
            trndate = ResultSetExtractor.getValueFromResultSet(data, "TRNDATE", "-");
            valdate = ResultSetExtractor.getValueFromResultSet(data, "VALDATE", "-");

            valueDate = ResultSetExtractor.getDateFromResultSet(data, "VALUE_DATE", null);

            this.lcyAmount = ResultSetExtractor.getDoubleFromResultSet(data, "LCY_AMOUNT", 0D);
            this.principalDebit = ResultSetExtractor.getDoubleFromResultSet(data, "PRINCIPAL_DEBIT", 0D);
            this.principalCredit = ResultSetExtractor.getDoubleFromResultSet(data, "PRINCIPAL_CREDIT", 0D);
            this.interestDebit = ResultSetExtractor.getDoubleFromResultSet(data, "INTEREST_DEBIT", 0D);
            this.interestCredit = ResultSetExtractor.getDoubleFromResultSet(data, "INTEREST_CREDIT", 0D);
            this.interestBal = ResultSetExtractor.getDoubleFromResultSet(data, "INTEREST_BAL", 0D);
            this.totalInterest = ResultSetExtractor.getDoubleFromResultSet(data, "TOTAL_INTEREST", 0D);
            this.principalBal = ResultSetExtractor.getDoubleFromResultSet(data, "PRINCIPAL_BAL", 0D);
            this.totalPrinciple = ResultSetExtractor.getDoubleFromResultSet(data, "TOTAL_PRINCIPLE", 0D);
            this.totalOutstanding = ResultSetExtractor.getDoubleFromResultSet(data, "TOTAL_OUTSTANDING", 0D);
            this.lastPayment = this.principalCredit + this.interestCredit;
            this.descriptoion = ResultSetExtractor.getValueFromResultSet(data, "DESCRIPTOION", "-");
            this.event = ResultSetExtractor.getValueFromResultSet(data, "EVENT","-");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("total : "+totalSubsidyFromBbCovid);
    }




    public void setLastPayment() {
        this.lastPayment = this.principalCredit + this.interestCredit;
    }

    public void setTotalInteWithOutRoll(double totalInteWithOutRoll) {
        this.totalInteWithOutRoll = totalInterest - totalRolloverOfContract;
    }


}
