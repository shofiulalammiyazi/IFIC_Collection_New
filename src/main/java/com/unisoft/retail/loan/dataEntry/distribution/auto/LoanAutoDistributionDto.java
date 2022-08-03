package com.unisoft.retail.loan.dataEntry.distribution.auto;

import lombok.Data;

@Data
public class LoanAutoDistributionDto {
    private String accountNo;
    private String dealerPin;
    private String dealerName;
    private Long agencyId;
    private String dpdBucket;
    private String location;
    private String productCode;
    private double outstanding;
    private String remarks;

    public LoanAutoDistributionDto() {
    }

    public LoanAutoDistributionDto(LoanAutoDistributionInfo entity) {
        accountNo = entity.getAccountNo();
        dealerPin = entity.getDealerPin();
        dealerName = entity.getDealerName();
        agencyId = entity.getAgencyId();
        dpdBucket = entity.getDpdBucket();
        location = entity.getLocation();
        productCode = entity.getProductCode();
        outstanding = entity.getOutstanding();
        remarks = entity.getRemark();
    }
}
