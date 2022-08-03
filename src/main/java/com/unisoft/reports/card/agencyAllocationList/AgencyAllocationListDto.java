package com.unisoft.reports.card.agencyAllocationList;

import lombok.Data;

@Data
public class AgencyAllocationListDto {
    private String clientId;
    private String customerName;
    private String agencyName;
    private String agencyReferedDate;
    private String woValue;
    private String stampCharges;
    private String totalClaimAmount;
    private String totalPayment;
    private String remainingBalance;
    private String suitNo;
    private String suitDate;
    private String jariSuitNo;
    private String jariSuitDate;
    private String jariSuitValue;
    private String arrestWarrentIssueDate;
}
