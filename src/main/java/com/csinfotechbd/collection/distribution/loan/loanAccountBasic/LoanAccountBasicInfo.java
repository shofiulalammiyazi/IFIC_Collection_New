package com.csinfotechbd.collection.distribution.loan.loanAccountBasic;
/*
Created by Monirul Islam at 7/17/2019
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "LOAN_ACCOUNT_BASIC_INFO",
        uniqueConstraints = @UniqueConstraint(columnNames = {"CUSTOMER_ID"})
)
public class LoanAccountBasicInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String accountNo;

    private String accountName;

    private String linkAccountNumber;

    private Double linkAccountBalance;

    @Temporal(TemporalType.DATE)
    private Date disbursementDate;

    private double disbursementAmount;

    private String location; // Treated as 'Division' in 'Branch wise CL Report'

    private String connectPlusLoanId;

    private Date expiryDate;
    private Boolean fid = false;
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private CustomerBasicInfoEntity customer;

    public LoanAccountBasicInfo() {
    }

    public LoanAccountBasicInfo(CustomerBasicInfoEntity customer) {
        if (customer == null) return;
        this.accountNo = customer.getAccountNo();
        this.customer = customer;
    }

    public LoanAccountBasicInfo(String location, Date expiryDate, CustomerBasicInfoEntity customer) {
        if (customer == null || (customer.getId() == null && customer.getAccountNo() == null)) return;
        this.accountNo = customer.getAccountNo();
        this.location = location;
        this.expiryDate = expiryDate;
        this.customer = customer;
    }

    public LoanAccountBasicInfo(String accountNo, String accountName, Date disbursementDate, double disbursementAmount, String location, String connectPlusLoanId, CustomerBasicInfoEntity customer) {
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.disbursementDate = disbursementDate;
        this.disbursementAmount = disbursementAmount;
        this.location = location;
        this.connectPlusLoanId = connectPlusLoanId;
        this.customer = customer;
    }

    public Double getLinkAccountBalance() {
        return linkAccountBalance == null ? 0D : linkAccountBalance;
    }

    public void setLinkAccountBalance(Double linkAccountBalance) {
        this.linkAccountBalance = linkAccountBalance == null ? 0D : linkAccountBalance;
    }
}
