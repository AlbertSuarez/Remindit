package edu.upc.fib.molgo.suarez.albert.remindit.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    /**
     * days[0]-[6]: number of the days
     * days[7]: month of the first day of the week
     * days[8]: year of the first day of the week
     */
    public static String[] getDaysOfWeek() {
        String[] days = new String[9];
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar now = Calendar.getInstance();

        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2;
        now.add(Calendar.DAY_OF_MONTH, delta);

        String year = "-1";
        String month = "-1";

        for (int i = 0; i < 7; i++) {
            String day = dateFormat.format(now.getTime());
            days[i] = day.substring(0, 2);
            if (i == 0) {
                month = day.substring(3, 5);
                year = day.substring(6);
            }
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        days[7] = month;
        days[8] = year;
        return days;
    }

    public static String compress(String month) {
        if (month.equals("01")) return "JA";
        if (month.equals("02")) return "FE";
        if (month.equals("03")) return "MR";
        if (month.equals("04")) return "AP";
        if (month.equals("05")) return "MY";
        if (month.equals("06")) return "JN";
        if (month.equals("07")) return "JL";
        if (month.equals("08")) return "AU";
        if (month.equals("09")) return "SE";
        if (month.equals("10")) return "OC";
        if (month.equals("11")) return "NO";
        if (month.equals("12")) return "DE";
        return "ERR";
    }
}
