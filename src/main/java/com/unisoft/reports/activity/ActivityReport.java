package com.unisoft.reports.activity;

import lombok.Data;

@Data
public class ActivityReport {


    private String customerid, acountname, accountno, ptp, dairynote, dailynote, hotnote, followup, unit;

    public ActivityReport(String customerid, String acountname, String name, String ptp, String dairynote, String dailynote, String hotnote, String followup, String unit) {
        this.customerid = customerid;
        this.acountname = acountname;
        this.accountno = name;
        this.ptp = ptp;
        this.dairynote = dairynote;
        this.dailynote = dailynote;
        this.hotnote = hotnote;
        this.followup = followup;
        this.unit = unit;
    }


}
