package com.unisoft.collection.datamigration;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ModeRepaymentFid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String accountNo;

    private String name;

    private String modeRepayment;

    @Temporal(TemporalType.DATE)
    private Date fidDate;

    public ModeRepaymentFid() {
    }

    public ModeRepaymentFid(String accountNo, String modeRepayment, Date fidDate) {
        this.accountNo = accountNo;
        this.modeRepayment = modeRepayment;
        this.fidDate = fidDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getModeRepayment() {
        return modeRepayment;
    }

    public void setModeRepayment(String modeRepayment) {
        this.modeRepayment = modeRepayment;
    }

    public Date getFidDate() {
        return fidDate;
    }

    public void setFidDate(Date fidDate) {
        this.fidDate = fidDate;
    }
}
