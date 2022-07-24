package com.unisoft.customerloanprofile.loanpayment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DelenquentAccount {
    private String accountNo;
    private double overDue;
    private double dpd;
    private double ate;
    private String schemeCode;
    private double outstanding;

    public DelenquentAccount() {
    }

    public DelenquentAccount(String accountNo, double overDue, double dpd, double ate, String schemeCode, double outstanding) {
        this.accountNo = accountNo;
        this.overDue = overDue;
        this.dpd = dpd;
        this.ate = ate;
        this.schemeCode = schemeCode;
        this.outstanding = outstanding;
    }

    public double getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(double outstanding) {
        this.outstanding = outstanding;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public double getOverDue() {
        return overDue;
    }

    public void setOverDue(double overDue) {
        this.overDue = overDue;
    }

    public double getDpd() {
        return dpd;
    }

    public void setDpd(double dpd) {
        this.dpd = dpd;
    }

    public double getAte() {
        return ate;
    }

    public void setAte(double ate) {
        this.ate = ate;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public Date getStartDate(){
        LocalDate today = LocalDate.now();
        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        return Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getEndDate(){
        LocalDate today = LocalDate.now();
        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";
        try {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(endDateOfMonthString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
