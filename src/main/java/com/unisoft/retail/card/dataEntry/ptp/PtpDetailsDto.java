package com.unisoft.retail.card.dataEntry.ptp;

import lombok.Data;

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
