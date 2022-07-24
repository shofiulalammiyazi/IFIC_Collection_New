package com.unisoft.collection.distribution.loan.loanAccountOther;
/*
Created by   Islam at 7/17/2019

Unique constraint on LOAN_ACCOUNT_BASIC_INFO_ID added by    on 25-03-2021
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "LOAN_ACCOUNT_OTHER_INFO",
        uniqueConstraints = @UniqueConstraint(columnNames = {"LOAN_ACCOUNT_BASIC_INFO_ID"})
)
public class LoanAccountOtherInfo extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String notificationStatus;

    private String modeOfPayment;

    private String orgType;

    private String companyCategory;

    @OneToOne
    @JoinColumn(name = "LOAN_ACCOUNT_BASIC_INFO_ID")
    private LoanAccountBasicInfo loanAccountBasicInfo;

    public LoanAccountOtherInfo() {
    }

    public LoanAccountOtherInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        this.loanAccountBasicInfo = loanAccountBasicInfo;
    }

    public LoanAccountOtherInfo(String notificationStatus, String modeOfPayment, String orgType, String companyCategory, LoanAccountBasicInfo loanAccountBasicInfo) {
        this.notificationStatus = notificationStatus;
        this.modeOfPayment = modeOfPayment;
        this.orgType = orgType;
        this.companyCategory = companyCategory;
        this.loanAccountBasicInfo = loanAccountBasicInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(String companyCategory) {
        this.companyCategory = companyCategory;
    }

    public LoanAccountBasicInfo getLoanAccountBasicInfo() {
        return loanAccountBasicInfo;
    }

    public void setLoanAccountBasicInfo(LoanAccountBasicInfo loanAccountBasicInfo) {
        this.loanAccountBasicInfo = loanAccountBasicInfo;
    }
}
