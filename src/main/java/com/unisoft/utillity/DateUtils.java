package com.unisoft.utillity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * @author
 * @since December 1, 2020
 */
@Component
@RequiredArgsConstructor
public class DateUtils {

    private final NumberUtils numberUtils;
    private final String[] banglaLongMonths = {"জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল", "মে", "জুন",
            "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"};


    public String getBanglaShortDate(Date date) {
        Calendar calendar = Calendar.getInstance(new Locale("bn", "BD"));
        calendar.setTime(date);
        String day = numberUtils.convertToBanglaNumber(calendar.get(Calendar.DAY_OF_MONTH));
        day = day.length() == 1 ? "০" + day : day;
        String month = numberUtils.convertToBanglaNumber(calendar.get(Calendar.MONTH) + 1);
        month = month.length() == 1 ? "০" + month : month;
        String year = numberUtils.convertToBanglaNumber(calendar.get(Calendar.YEAR));
        String banglaDate = day + "-" + month + "-" + year;
        return banglaDate.replace(",", "");
    }

    public Date getMonthStartDate() {
        Calendar calendar = Calendar.getInstance();
        return getDateOfMonthAtStartingPoint(calendar, 1);
    }

    public Date getMonthStartDate(Date month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);
        return getDateOfMonthAtStartingPoint(calendar, 1);
    }

    /**
     * Provides start date of a previous (@param < 0) month or future month (@param > 0)
     * or current month (@param == 0)
     *
     * @param numberOfMonths
     * @return
     */
    public Date getMonthStartDate(int numberOfMonths) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, numberOfMonths);
        return getDateOfMonthAtStartingPoint(calendar, 1);
    }

    /**
     * Generates start date of month counting by the month index.
     * For example, if the given date parameter represents '2020-01-01' and
     * the month index is 1, the return value will be
     *
     * @param month
     * @param numberOfMonths
     * @return
     */
    public Date getMonthStartDate(Date month, int numberOfMonths) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);
        calendar.add(Calendar.MONTH, numberOfMonths);
        return getDateOfMonthAtStartingPoint(calendar, 1);
    }

    public Date getMonthEndDate() {
        return getMonthEndDate(Calendar.getInstance());
    }

    public Date getMonthEndDate(Date month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);
        return getMonthEndDate(calendar);
    }

    public Date getMonthEndDate(int numberOfMonths) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, numberOfMonths);
        return getMonthEndDate(calendar);
    }

    public Date getMonthEndDate(Calendar calendar) {
        calendar.set(Calendar.DATE, getTotalDaysInMonth(calendar));
        return getEndingPointOfDay(calendar);
    }

    public Date getDayOfMonth(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return getDayOfMonth(calendar, day);
    }

    private Date getDayOfMonth(Calendar calendar, int day) {
        int verifiedDayIndex = isValidDay(day, calendar) ? day : 1;
        return getDateOfMonthAtStartingPoint(calendar, verifiedDayIndex);
    }

    public Date getDateOfMonthAtStartingPoint(Calendar calendar, int dayIndex) {
        calendar.set(Calendar.DAY_OF_MONTH, dayIndex);
        return getStartingPointOfDay(calendar);
    }

    public boolean isValidDay(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return isValidDay(day, calendar);
    }

    public boolean isValidDay(int day, Calendar calendar) {
        int totalDays = getTotalDaysInMonth(calendar);
        return day > 0 || day <= totalDays;
    }

    /**
     * Provides a date prior or next to a given date calculated by day index.
     *
     * @param date
     * @param dayIndex
     * @return Future date if dayIndex > 0. Previous date if dayIndex < less than 0. Given date if dayIndex == 0.
     */
    public Date getNextOrPreviousDate(Date date, int dayIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dayIndex);
        return getFormattedDate(calendar.getTime(), "yyyy-MM-dd");
    }

    /**
     * Generates a customized date from valid inputs.
     * Year must be greater than 1969
     *
     * @param day
     * @param month
     * @param year  > 1969
     * @return Custom date in yyyy-MM-dd format. Null if any of the inputs are invalid
     */
    public Date getCustomDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        if (month < 0 || month > 11) return null;
        calendar.set(Calendar.MONTH, month);
        if (day < 0 || day > getTotalDaysInMonth(calendar))
            return null;
        calendar.set(Calendar.DAY_OF_MONTH, day);
        if (year < 1969) return null;
        calendar.set(Calendar.YEAR, year);
        return getFormattedDate(getStartingPointOfDay(calendar), "yyyy-MM-dd");
    }

    public Date getDateUsingNonWordDelimeter(String dateString) {
        try {
            String[] values = dateString.split("\\W");
            int day = Integer.parseInt(values[0]);
            int month = Integer.parseInt(values[1]);
            int year = Integer.parseInt(values[2]);
            if (month > 12) {
                NumberUtils.swap(day, month);
            }
            if (day > 31) {
                NumberUtils.swap(day, year);
            }
            if (month > 12) {
                NumberUtils.swap(year, month);
            }
            return getCustomDate(day, month, year);
        } catch (Exception e) {
            return null;
        }
    }

    public Date getStartingPointOfDay(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        return getStartingPointOfDay(calendar);
    }

    public Date getStartingPointOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public Date getEndingPointOfDay(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        return getEndingPointOfDay(calendar);
    }

    public Date getEndingPointOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public String calculateEmiDueDate(String dateString) {
        Date date = getFormattedDate(dateString, "DD-MMM-YYYY");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        return (day < 10 ? "0" : Integer.toString(day)).concat(" (M)");
    }

    public Date getDateOfCurrentMonth(int date) {
        LocalDate today = LocalDate.now();
        if (date < 1 || date > today.lengthOfMonth()) date = 1;
        LocalDate startDateOfMonth = today.withDayOfMonth(date);
        return Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getLocalMonthEndDate() {
        LocalDate today = LocalDate.now();
        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            return simpleDateFormat.parse(endDateOfMonthString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Formats a date to a given pattern. Example pattern - "yyyy-MM-dd hh:mm a"
     *
     * @param date
     * @param pattern
     * @return Formatted date or today if fails to format
     */
    public Date getFormattedDate(String date, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Formats a date to a given pattern. Example pattern - "yyyy-MM-dd hh:mm a"
     *
     * @param date
     * @param pattern
     * @return Formatted date or today if fails to format
     */
    public Date getFormattedDate(Date date, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(simpleDateFormat.format(date));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Coverts a date to string by a given pattern. Example pattern - "yyyy-MM-dd hh:mm a"
     *
     * @param date
     * @param pattern
     * @return Formatted date string. Returns empty string when fails to format
     */
    public String getFormattedDateString(Date date, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "";
    }

    /**
     * Returns total days in current month
     *
     * @return
     */
    public int getTotalDaysInMonth() {
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Returns total days in given month
     *
     * @param month
     * @return
     */
    public int getTotalDaysInMonth(Date month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);
        return getTotalDaysInMonth(calendar);
    }

    /**
     * Returns total days in given month
     *
     * @param calendar
     * @return
     */
    public int getTotalDaysInMonth(Calendar calendar) {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Check if two date instances are on the same day
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }
    /**
     * get Current Date Format String
     *

     * @return yyyy-MM-dd String Date
     */
    public String dateStringFormat( Date date ) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        return strDate;
    }
    public String db2ToOracleDateFormat(String s){
        int i;
        String t = "";
        Map<Integer, String> map = new HashMap<>();
        int cnt = 1;
        if ((s.length() == 7 && s.charAt(0) == '0') || s.length() == 6) {
            String data = "";
            if (s.length() == 7) {
                for (i = 1; i < s.length(); i++) {
                    data += s.charAt(i);
                }
            } else {
                data = s;
                for (i = 0; i < data.length(); i++) {
                    t += data.charAt(i);
                    if (t.length() == 2) {
                        map.put(cnt, t);
                        cnt++;
                        t = "";
                    }
                }
            }
            int year = 1900 + Integer.parseInt(map.get(1));
            return year + "-" + map.get(2) + "-" + map.get(3);
        } else {
            String data = "";
            for (i = 1; i < s.length(); i++) {
                data += s.charAt(i);
            }
            for (i = 0; i < data.length(); i++) {
                t += data.charAt(i);
                if (t.length() == 2) {
                    map.put(cnt, t);
                    cnt++;
                    t = "";
                }
            }
            int year = 2000 + Integer.parseInt(map.get(1));

            return year + "-" + map.get(2) + "-" + map.get(3);
        }
    }

   public String changeStringDatePattern(String d, String currentPattern, String targetPattern){
       SimpleDateFormat format1 = new SimpleDateFormat(currentPattern);
       SimpleDateFormat format2 = new SimpleDateFormat(targetPattern);
       Date date = null;
       try {
           date = format1.parse(d);
       } catch (ParseException e) {
           System.err.println(e.getMessage());
       }
       return format2.format(date);
   }

}
