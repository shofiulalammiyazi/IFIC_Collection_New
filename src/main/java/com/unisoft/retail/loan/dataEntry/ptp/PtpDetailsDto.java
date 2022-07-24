package com.unisoft.retail.loan.dataEntry.ptp;

import lombok.Data;

/**
 * Created by Yasir Araphat
 * Created at 05 April, 2021
 */

@Data
public class PtpDetailsDto {
    private String accountNo;
    private String accountName;
    private String amount;
    private String dealerPin;
    private String ptpDate;
    private String ptpTime;
    private String contactDetail;
    private String contactLocation;
    private String promisorDetails;
    private String dpdBucket;
    private String productName;

}
