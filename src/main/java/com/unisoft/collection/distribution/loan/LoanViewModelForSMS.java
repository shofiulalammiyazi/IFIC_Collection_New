package com.unisoft.collection.distribution.loan;

import lombok.Data;

@Data
public class LoanViewModelForSMS {

    private String customerId;

    private String accountNo;

    private String customerName;

    private String mobileNo;

    private String productName;

    private String installmentAmount;

    private String nextEmiDate;

    private String currentMonth;
}
