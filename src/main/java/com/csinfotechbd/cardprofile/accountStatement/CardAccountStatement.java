package com.csinfotechbd.cardprofile.accountStatement;
/*
  Created by MR on 9/30/2021
*/

import lombok.Data;

import java.util.Date;

@Data
public class CardAccountStatement {

    private String contractNo;

    private Date transDate;

    private String transId;

    private String transParticular;

    private double deposit;

    private double withdraw;

    private double balance;

    private double principalDebit;

    private double interestDebit;

    private String descriptoion;

    private String valueDate;
}
