package com.csinfotechbd.collection.distribution.unallocated;

import com.csinfotechbd.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class UnallocatedLoanList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String supervisorName;

    private String supervisorPin;

    private String dealerName;

    private String dealerPin;

    private String samAccount;

    private String writeOffAccount;

    @Temporal(TemporalType.DATE)
    private Date statusDate;

    @OneToOne
    private LoanAccountBasicInfo loanAccountBasicInfo;

    private String productGroup;

    @Expose
    private String dpdBucket;

    private double outStanding;

    private String schemaCode;

    private double openingOverDue;

    @Expose
    private double emiAmount;

    private String emiDueDate;

    @Expose
    private float dpd;

    private Double totalPayment;

    private String monitoringStatus;//default=SINGLE

    private String latest;//latest=0

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")

    private Date createdDate;

    @Transient
    private double parRelAmnt = 0;

    @Transient
    private double nplRelAmnt = 0;

    @Transient
    private double saveAmount;

    @Transient
    private double backAmount;
}
