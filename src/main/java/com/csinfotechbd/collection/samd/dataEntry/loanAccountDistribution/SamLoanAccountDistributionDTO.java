package com.csinfotechbd.collection.samd.dataEntry.loanAccountDistribution;

import lombok.Data;

@Data
public class SamLoanAccountDistributionDTO {

    private Long dealerId;
    private Long agencyId;
    private String monitoringStatus;
    private Long[] selectedIds;
}
