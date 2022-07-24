package com.unisoft.customerloanprofile.loanpayment;

import lombok.Data;

import javax.persistence.Tuple;

/**
 * Implementation by Yasir Araphat
 * at 18 April 2021
 */
@Data
public class DealerWisePayment {

    private String dealerPin;
    private String dealerName;

    private int currentMonthTotalPaidAccount;
    private double currentMonthTotalPaidCollectionAmount;

    private int currentMonthUnpaidAccount;
    private double currentMonthUnpaidOutstanding;

    private int currentMonthSaveAccount;
    private double currentMonthSaveOutstanding;

    private int currentMonthOneEmiAccount;
    private double currentMonthOneEmiOutstanding;

    private int currentMonthPaidButNotSaveAccount;
    private double currentMonthPaidButNotSaveOutstanding;

    private int lastDateTotalPaidAccount;
    private double lastDateTotalPaidCollectionAmount;

    private int lastDateUnpaidAccount;
    private double lastDateUnpaidOutstanding;

    private int lastDateSaveAccount;
    private double lastDateSaveOutstanding;

    private int lastDateOneEmiAccount;
    private double lastDateOneEmiOutstanding;

    private int lastDatePaidButNotSaveAccount;
    private double lastDatePaidButNotSaveOutstanding;

    public DealerWisePayment() {
    }

    public DealerWisePayment(Tuple data) {
        dealerPin = data.get("DEALER_PIN").toString();
        dealerName = data.get("DEALER_NAME").toString();

        currentMonthTotalPaidAccount = ((Number)data.get("CURRENT_MONTH_PAID_ACCOUNT")).intValue();
        currentMonthTotalPaidCollectionAmount = ((Number)data.get("CURRENT_MONTH_TOTAL_PAYMENT")).doubleValue();
        currentMonthUnpaidAccount = ((Number)data.get("CURRENT_MONTH_UNPAID_ACCOUNT")).intValue();
        currentMonthUnpaidOutstanding = ((Number)data.get("CURRENT_MONTH_UNPAID_OUTSTANDING")).doubleValue();
        currentMonthSaveAccount = ((Number)data.get("CURRENT_MONTH_SAVE_ACCOUNT")).intValue();
        currentMonthSaveOutstanding = ((Number)data.get("CURRENT_MONTH_SAVE_OUTSTANDING")).doubleValue();
        currentMonthOneEmiAccount = ((Number)data.get("CURRENT_MONTH_ONE_EMI_ACCOUNT")).intValue();
        currentMonthOneEmiOutstanding = ((Number)data.get("CURRENT_MONTH_ONE_EMI_OUTSTANDING")).doubleValue();
        currentMonthPaidButNotSaveAccount = ((Number)data.get("CURRENT_MONTH_PAID_NOT_SAVE_ACCOUNT")).intValue();
        currentMonthPaidButNotSaveOutstanding = ((Number)data.get("CURRENT_MONTH_PAID_NOT_SAVE_OUTSTANDING")).doubleValue();

        lastDateTotalPaidAccount = ((Number)data.get("LAST_DATE_TOTAL_ACCOUNT")).intValue();
        lastDateTotalPaidCollectionAmount = ((Number)data.get("LAST_DATE_TOTAL_PAYMENT")).doubleValue();
        lastDateUnpaidAccount = ((Number)data.get("LAST_DATE_UNPAID_ACCOUNT")).intValue();
        lastDateUnpaidOutstanding = ((Number)data.get("LAST_DATE_UNPAID_OUTSTANDING")).doubleValue();
        lastDateSaveAccount = ((Number)data.get("LAST_DATE_SAVE_ACCOUNT")).intValue();
        lastDateSaveOutstanding = ((Number)data.get("LAST_DATE_SAVE_OUTSTANDING")).doubleValue();
        lastDateOneEmiAccount = ((Number)data.get("LAST_DATE_ONE_EMI_ACCOUNT")).intValue();
        lastDateOneEmiOutstanding = ((Number)data.get("LAST_DATE_ONE_EMI_OUTSTANDING")).doubleValue();
        lastDatePaidButNotSaveAccount = ((Number)data.get("LAST_DATE_PAID_NOT_SAVE_ACCOUNT")).intValue();
        lastDatePaidButNotSaveOutstanding = ((Number)data.get("LAST_DATE_PAID_NOT_SAVE_OUTSTANDING")).doubleValue();
    }



}
