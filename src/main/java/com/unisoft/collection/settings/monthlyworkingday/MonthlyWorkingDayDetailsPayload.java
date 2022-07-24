package com.unisoft.collection.settings.monthlyworkingday;

public class MonthlyWorkingDayDetailsPayload {
    private String id;
    private String daynumber;
    private String dayname;
    private boolean workingdays;

    public MonthlyWorkingDayDetailsPayload() {
    }

    public MonthlyWorkingDayDetailsPayload(String id, String daynumber, String dayname, boolean workingdays) {
        this.id = id;
        this.daynumber = daynumber;
        this.dayname = dayname;
        this.workingdays = workingdays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDaynumber() {
        return daynumber;
    }

    public void setDaynumber(String daynumber) {
        this.daynumber = daynumber;
    }

    public String getDayname() {
        return dayname;
    }

    public void setDayname(String dayname) {
        this.dayname = dayname;
    }

    public boolean isWorkingdays() {
        return workingdays;
    }

    public void setWorkingdays(boolean workingdays) {
        this.workingdays = workingdays;
    }
}
