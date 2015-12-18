package edu.upc.fib.molgo.suarez.albert.remindit.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Utils class
 * @author albert.suarez.molgo
 */
public class Utils {

    public static Date createDate(int day, int month, int year) {
        Calendar cDate = Calendar.getInstance();
        cDate.set(Calendar.DATE, day);
        cDate.set(Calendar.MONTH, month);
        cDate.set(Calendar.YEAR, year);
        return cDate.getTime();
    }

    public static Date createHour(Date date, int hour, int minute) {
        Calendar cHour = Calendar.getInstance();
        cHour.setTime(date);
        cHour.set(Calendar.HOUR_OF_DAY, hour);
        cHour.set(Calendar.MINUTE, minute);
        return cHour.getTime();
    }

    public static int getDay(Date date) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        return cDay.get(Calendar.DATE);
    }

    public static int getMonth(Date date) {
        Calendar cMonth = Calendar.getInstance();
        cMonth.setTime(date);
        return cMonth.get(Calendar.MONTH);
    }

    public static int getYear(Date date) {
        Calendar cYear = Calendar.getInstance();
        cYear.setTime(date);
        return cYear.get(Calendar.YEAR);
    }

    public static int getHour(Date date) {
        Calendar cHour = Calendar.getInstance();
        cHour.setTime(date);
        return cHour.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date) {
        Calendar cMinute = Calendar.getInstance();
        cMinute.setTime(date);
        return cMinute.get(Calendar.MINUTE);
    }

    public static int compareTo(Date date1, Date date2) {
        return date1.compareTo(date2);
    }

    public static String dateToString(Date date) {
        return Utils.getDay(date) + "/" + Utils.getMonth(date) +  "/" + Utils.getYear(date) + "\n";
    }

    public static String hourToString(Date date) {
        String result = "";
        int hour = Utils.getHour(date);
        if (hour < 10) result += "0" + hour; else result += hour;
        result += ":";
        int minute = Utils.getMinute(date);
        if (minute < 10) result += "0" + minute; else result += minute;
        return result + "\n";
    }

}
