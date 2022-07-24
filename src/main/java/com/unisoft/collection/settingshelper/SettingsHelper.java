package com.unisoft.collection.settingshelper;

import java.util.Calendar;
import java.util.Date;

public class SettingsHelper {
    public static Date getStartDate() {
        Calendar c1 = Calendar.getInstance();

        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        return c1.getTime();
    }

    public static Date getEndDate() {
        Calendar cal = Calendar.getInstance();
        int totalDays = cal.getActualMaximum(Calendar.DATE);

        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.DAY_OF_MONTH, totalDays);
        c2.set(Calendar.HOUR_OF_DAY, 23);
        c2.set(Calendar.MINUTE, 59);
        c2.set(Calendar.SECOND, 59);
        c2.set(Calendar.MILLISECOND, 0);

        return c2.getTime();
    }
}
