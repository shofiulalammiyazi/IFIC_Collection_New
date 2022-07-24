package com.unisoft.customerloanprofile.loanpayment;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class LoanPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentDate;
    private String accountNo;
    private double payment;


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

    public LoanPayment() {
    }

    public LoanPayment(Date paymentDate, String accountNo, double payment) {
        this.paymentDate = paymentDate;
        this.accountNo = accountNo;
        this.payment = payment;
    }

    public LoanPayment(Date paymentDate, String accountNo, double payment, double lastPayment, Date lastPaymentDate, String dealerName, String outstanding, String loanName, double overDue) {
        this.paymentDate = paymentDate;
        this.accountNo = accountNo;
        this.payment = payment;
        this.lastPayment = lastPayment;
        this.lastPaymentDate = lastPaymentDate;
        this.dealerName = dealerName;
        this.outstanding = outstanding;
        this.loanName = loanName;
        this.overDue = overDue;
    }

}
