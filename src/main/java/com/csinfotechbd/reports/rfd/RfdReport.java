package com.csinfotechbd.reports.rfd;

import lombok.Data;

@Data
public class RfdReport {
String date,accountno,accountname,menu,submenuone,submenutwo,submenuthree,shortnote,unit;

    public RfdReport(String date, String accountno, String accountname, String menu, String submenuone, String submenutwo, String submenuthree, String shortnote,String unit) {
        this.date = date;
        this.accountno = accountno;
        this.accountname = accountname;
        this.menu = menu;
        this.submenuone = submenuone;
        this.submenutwo = submenutwo;
        this.submenuthree = submenuthree;
        this.shortnote = shortnote;
        this.unit = unit;
    }
}
