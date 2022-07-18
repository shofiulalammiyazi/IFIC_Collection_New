package com.csinfotechbd.reports.ptp;

import lombok.Data;

@Data
public class LoanPtpreport {

    private String accountno;
    private String createddate;
    private String loanptpdate;
    private String loanptpamount;
    private String loanptpstatus;
    private String accountname;
    private String unit;

    public LoanPtpreport(String accountno, String createddate, String loanptpdate, String loanptpamount, String loanptpstatus, String accountname, String unit) {
        this.accountno = accountno;
        this.createddate = createddate;
        this.loanptpdate = loanptpdate;
        this.loanptpamount = loanptpamount;
        this.loanptpstatus = loanptpstatus;
        this.accountname = accountname;
        this.unit = unit;
    }
}
