package com.unisoft.reports.retail.loan.vipListReport;

import lombok.Data;

@Data
public class VipListReportDto {

    private String accountNumber;
    private String customerName;
    private String companyName;
    private String vipStatus;
    private String remarks;

    public VipListReportDto(String accountNumber, String customerName, String companyName, String remarks) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.companyName = companyName;
        this.remarks = remarks;
    }
}
