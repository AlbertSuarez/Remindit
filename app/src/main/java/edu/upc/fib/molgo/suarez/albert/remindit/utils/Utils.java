package edu.upc.fib.molgo.suarez.albert.remindit.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.CancellationException;

import edu.upc.fib.molgo.suarez.albert.remindit.activity.MainActivity;

/**
 * Utils class
 * @author albert.suarez.molgo
 */
public class Utils {

    public static String[] days = new String[9];

    public static Date createDate(int day, int month, int year) {
        Calendar cDate = Calendar.getInstance();
        cDate.set(Calendar.DATE, day);
        cDate.set(Calendar.MONTH, month);
        cDate.set(Calendar.YEAR, year);
        return cDate.getTime();
    }

    public static long createDate(int day, int month, int year, int hour, int minute) {
        Calendar cDate = Calendar.getInstance();
        cDate.setTimeZone(TimeZone.getTimeZone(MainActivity.UTC_TIME_ZONE));
        cDate.set(Calendar.DATE, day);
        cDate.set(Calendar.MONTH, month);
        cDate.set(Calendar.YEAR, year);
        cDate.set(Calendar.HOUR_OF_DAY, hour);
        cDate.set(Calendar.MINUTE, minute);
        return cDate.getTimeInMillis();
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

    public static String getDayOfWeekInString(Date date) {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        int day = cDay.get(Calendar.DAY_OF_WEEK);
        if (day == Calendar.MONDAY) return MainActivity.MONDAY;
        if (day == Calendar.TUESDAY) return MainActivity.TUESDAY;
        if (day == Calendar.WEDNESDAY) return MainActivity.WEDNESDAY;
        if (day == Calendar.THURSDAY) return MainActivity.THURSDAY;
        if (day == Calendar.FRIDAY) return MainActivity.FRIDAY;
        if (day == Calendar.SATURDAY) return MainActivity.SATURDAY;
        if (day == Calendar.SUNDAY) return MainActivity.SUNDAY;
        return "";
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
        return Utils.getDay(date) + "/" + (Utils.getMonth(date) + 1) +  "/" + Utils.getYear(date) + "\n";
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

    public static String[] nextWeek() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        int delta = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 2;
        calendar.add(Calendar.DAY_OF_MONTH, delta);

        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(days[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(days[7])-1);
        calendar.set(Calendar.YEAR, Integer.parseInt(days[8]));

        calendar.add(Calendar.DAY_OF_MONTH, 7);
        String year = "-1";
        String month = "-1";

        for (int i = 0; i < 7; i++) {
            String day = dateFormat.format(calendar.getTime());
            days[i] = day.substring(0, 2);
            if (i == 0) {
                month = day.substring(3, 5);
                year = day.substring(6);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        days[7] = month;
        days[8] = year;
        return days;
    }

    public static String[] previousWeek() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        int delta = -calendar.get(GregorianCalendar.DAY_OF_WEEK) + 2;
        calendar.add(Calendar.DAY_OF_MONTH, delta);

        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(days[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(days[7])-1);
        calendar.set(Calendar.YEAR, Integer.parseInt(days[8]));

        calendar.add(Calendar.DAY_OF_MONTH, -7);
        String year = "-1";
        String month = "-1";

        for (int i = 0; i < 7; i++) {
            String day = dateFormat.format(calendar.getTime());
            days[i] = day.substring(0, 2);
            if (i == 0) {
                month = day.substring(3, 5);
                year = day.substring(6);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
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

    public static int getHour(String hour) throws ParseException{
        String[] parts = hour.split(":");
        if (parts.length != 2) throw new ParseException("Wrong format", 127);
        int result = Integer.parseInt(parts[0]);
        if (result < 0 || result >= 24) throw new ParseException("Format incorrect", 129);
        return result;
    }

    public static int getMinute(String hour) throws ParseException{
        String[] parts = hour.split(":");
        if (parts.length != 2) throw new ParseException("Wrong format", 134);
        int result = Integer.parseInt(parts[1]);
        if (result < 0 || result >= 60) throw new ParseException("Format incorrect", 136);
        return result;
    }

    public static String getNextWeekDay(String day) {
        if (day.equals(MainActivity.MONDAY)) return MainActivity.TUESDAY;
        if (day.equals(MainActivity.TUESDAY)) return MainActivity.WEDNESDAY;
        if (day.equals(MainActivity.WEDNESDAY)) return MainActivity.THURSDAY;
        if (day.equals(MainActivity.THURSDAY)) return MainActivity.FRIDAY;
        if (day.equals(MainActivity.FRIDAY)) return MainActivity.SATURDAY;
        if (day.equals(MainActivity.SATURDAY)) return MainActivity.SUNDAY;
        if (day.equals(MainActivity.SUNDAY)) return MainActivity.MONDAY;
        return "";
    }

    public static String getFirstDayOfTheCurrentWeek() {
        return days[0] + "/" + days[7] + "/" + days[8];
    }

    public static String getLastDayOfTheCurrentWeek() {
        String month, year;
        if (Integer.parseInt(days[6]) < Integer.parseInt(days[0])) {
            if (days[7].equals("12")) {
                month = "01";
                year = Integer.toString(Integer.parseInt(days[8])+1);
            }
            else {month = Integer.toString(Integer.parseInt(days[7])+1); year = days[8];}
        }
        else {month = days[7]; year = days[8];}
        return days[6] + "/" + month + "/" + year;
    }
}
