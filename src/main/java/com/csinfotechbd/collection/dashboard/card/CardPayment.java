package com.csinfotechbd.collection.dashboard.card;
/*
  Created by MR on 10/5/2021
*/

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class CardPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cardNo;

    private String contractId;

    private double PaymentAmount;

    private Date paymentDate;

    @Transient
    private double lastPayment;
    @Transient
    private Date lastPaymentDate;
    @Transient
    private String dealerName;
    @Transient
    private String outstanding;
    @Transient
    private String loanName;
    @Transient
    private double overDue;

}
