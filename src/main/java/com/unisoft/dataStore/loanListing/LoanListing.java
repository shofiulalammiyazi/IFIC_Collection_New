package com.unisoft.dataStore.loanListing;

import com.unisoft.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanListing extends CommonEntity {

    private String accNo;
    private String clStatus;
    private Date clStatusDate;      ///new Date
    private double outstanding;
    private double overdueAmt;
    private double bucket;
    private String productName;
    private String productGroup;
    private String segment;
    private String branchName;
    private String branchCode;
    private String fid;
    private String firstDisbursementDate;
    private String sourceChannel;
    private  Date listingDownDate;
    private String loanAccPar;
    private String productCode;
}
