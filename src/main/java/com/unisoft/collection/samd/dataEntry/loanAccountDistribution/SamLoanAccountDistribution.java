package com.unisoft.collection.samd.dataEntry.loanAccountDistribution;

import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.common.CommonEntity;
import com.unisoft.retail.loan.dataEntry.ptp.LoanPtp;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity @Getter @Setter
@Table(name = "lms_collection_sam_loan_account_distributions")
@EntityListeners(AuditingEntityListener.class)
public class SamLoanAccountDistribution extends CommonEntity {

    private String loanLiabilityNo;
    private String dealerPin;
    private String dealerName;

    private Long agencyId;
    private String agencyName;
    private String monitoringStatus;

    //TODO: can be changed due to new poc/tasks

    private String supervisorName;
    private String supervisorPin;
    private String samAccount;
    private String writeOffAccount;
    private String remarks;
    private String productGroup;
    private String dpdBucket;
    private String currentDpdBucket;
    private String outStanding;
    private String schemeCode;
    private Double openingOverDue;
    private Double currentOverdue;
    private Double emiAmount = 0d;
    private String emiDueDate;
    private Double dpd = 0d;
    private Double totalPayment = 0d;

    private Boolean latest = true;
    private Date statusDate;

    @Transient
    private Date lastPaymnetDate;
    @Transient
    private double lastPaidAmount;
    @Transient
    private double parRelAmnt=0;
    @Transient
    private double nplRelAmnt=0;
    @Transient
    private double saveAmount;
    @Transient
    private double backAmount;
    @Transient
    private double presentOverDue;
    @Transient
    private double liveDpd;
    @Transient
    private double remSaveAmount=0;
    @Transient
    private double remBackAmount=0;
    @Transient
    private double remNplRelAmount=0;
    @Transient
    private double remParRelAmount=0;
    @Transient
    private String visitDate;
    @Transient
    private LoanPtp loanPtp;
    // TODO: end of todo

    @Column(nullable = false)
    private boolean isDealerAccount;

    @ManyToOne
    private LoanAccountBasicInfo loanAccountBasicInfo;

}
