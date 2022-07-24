package com.unisoft.collection.dashboard;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CardDetailPaymentModel {

    private String cardNo;
    private Date paymentDate;
    private double amount=0;
    private int currentAgeCode;
    private String finAccNo;

    private String accType;
    private String productCD;
    private Date lastAgingDate;
    private double minPayment;
    private double currentMonthBal;
    private double currentMontCount;
    private Date currentMonthPayDueDate;
    private Date lastPaymentDate;
    private double lastPaymentAmount;
    private String individualAccNo;
    private Date lastActualStatementDate;

    private List<AgeCodeData> ageCodeDataList=new ArrayList<>();



}
