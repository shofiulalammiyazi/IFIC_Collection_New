package com.unisoft.retail.loan.dataEntry.writeOffAccountDistribution;

import com.unisoft.collection.distribution.loan.loanAccountBasic.LoanAccountBasicInfo;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@Data
public class WriteOffAccountDistribution extends CommonEntity {

    private String accountNumber;
    private String accountName;
    private String accountStatus;
    private String location;
    private String branchCode;
    private String branchName;
    private String businessRegion;
    private String businessSegment;
    private Date wOffDate;
    private Double wOffInterestSuspenseAmount;
    private Double wOffProvisionAmount;
    private Double totalWOffAmount;     //wOffInterestSuspenseAmount + wOffProvisionAmount
    private String DealerPin;
    private String DealerName;
    private String productCode;
    private String customerId;

    private Boolean latest = true;



//    new field


    private Double dpdBucket;
    private String outstanding;
    private Double overdueAmount;
    private Double emiAmount;
    private Double lastPayment;
    private String currentMonthPayment;
    private String riskCategory;
    private String allocationDat;

    @OneToOne
    private AssetClassificationLoanEntity clStatus;

    @ManyToOne
    private LoanAccountBasicInfo loanAccountBasicInfo;
}
