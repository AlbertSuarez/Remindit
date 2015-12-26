package edu.upc.fib.molgo.suarez.albert.remindit.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import edu.upc.fib.molgo.suarez.albert.remindit.R;

/**
 * Created by usuario on 22/12/2015.
 */
public class GridCellAdapter extends BaseAdapter implements DialogInterface.OnClickListener {

    public static final int JANUARY = 0;
    public static final int FEBRUARY = 1;
    public static final int MARCH = 2;
    public static final int ABRIL = 3;
    public static final int MAY = 4;
    public static final int JUNE = 5;
    public static final int JULY = 6;
    public static final int AUGUST = 7;
    public static final int SEPTEMBER = 8;
    public static final int OCTOBER = 9;
    public static final int NOVEMBER = 10;
    public static final int DECEMBER = 11;

    private static final String tag = "GridCellAdapter";
    private static final int DAY_OFFSET = 1;
    private final String[] weekDays = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private final String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private final int[] daysOfMonths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final int month, year;
    private final Context context;
    private final List<String> list;
    private final HashMap eventsPerMonthMap;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");

    private int daysInMonth, prevMonthDays;
    private int currentDayOfMonth;
    private int currentWeekDay;
    private Button gridcell;
    private TextView numEventsPerDay;


    public GridCellAdapter(int month, int year, Context context) {
        this.month = month;
        this.year = year;
        this.context = context;
        list = new ArrayList<String>();
        eventsPerMonthMap = new HashMap<String, Integer>();

        Calendar calendar = Calendar.getInstance();
        this.currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        this.currentWeekDay = calendar.get(Calendar.DAY_OF_WEEK);

        print(month, year);
    }

    private void print(int month, int year) {
        int trailingSpaces, leadSpaces, daysInPreviousMonth, previousMonth, previousYear, nextMonth, nextYear;
        trailingSpaces = leadSpaces = daysInPreviousMonth = previousMonth = previousYear = nextMonth = nextYear = 0;

        int currentMonth = month - 1;
        String currentMonthName = getMonthAsString(currentMonth);
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        // Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, currentMonth, 1);

        // Case of December
        if (currentMonth == DECEMBER)
        {
            previousMonth = currentMonth - 1;
            daysInPreviousMonth = getNumberOfDaysOfMonth(previousMonth);
            nextMonth = JANUARY;
            previousYear = year;
            nextYear = year + 1;
        }
        // Case of January
        else if (currentMonth == JANUARY)
        {
            previousMonth = DECEMBER;
            previousYear = year - 1;
            nextYear = year;
            daysInPreviousMonth = getNumberOfDaysOfMonth(previousMonth);
            nextMonth = FEBRUARY;
        }
        // Other cases
        else
        {
            previousMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = year;
            previousYear = year;
            daysInPreviousMonth = getNumberOfDaysOfMonth(previousMonth);
        }

        // Compute how much to leave before before the first day of the month.
        // getDay() returns 0 for Sunday.
        trailingSpaces = gregorianCalendar.get(Calendar.DAY_OF_WEEK) - 1;

        // Check leap year (año bisiesto)
        if (gregorianCalendar.isLeapYear(gregorianCalendar.get(Calendar.YEAR)) && month == FEBRUARY)
            ++daysInMonth;

        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++)
            list.add(String.valueOf((daysInPreviousMonth - trailingSpaces + DAY_OFFSET) + i)
                    + "-GREY" + "-" + getMonthAsString(previousMonth) + "-" + previousYear);

        // Current Month Days
        // Current day -> BLUE      Other days -> WHITE
        for (int i = 1; i <= daysInMonth; i++)
        {
            if (i == this.currentDayOfMonth)
                list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + year);
            else
                list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + year);
        }

        // Leading Month days
        for (int i = 0; i < list.size() % 7; i++)
            list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
    }

    private int getNumberOfDaysOfMonth(int currentMonth) {
        return daysOfMonths[currentMonth];
    }

    private String getMonthAsString(int currentMonth) {
        return months[currentMonth];
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = view;
        if (row == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.day_gridcell, viewGroup, false);
        }

        // Get a reference to the Day gridcell
        gridcell = (Button) row.findViewById(R.id.calendarDayGridcell);
        gridcell.setOnClickListener((View.OnClickListener) this);

        // ACCOUNT FOR SPACING
        String[] day_color = list.get(i).split("-");
        String theDay = day_color[0];
        String theMonth = day_color[2];
        String theYear = day_color[3];
        if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null))
            if (eventsPerMonthMap.containsKey(theDay))
            {
                num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
                Integer numEvents = (Integer) eventsPerMonthMap.get(theDay);
                num_events_per_day.setText(numEvents.toString());
            }

        // Set the Day GridCell
        gridcell.setText(theDay);
        gridcell.setTag(theDay + "-" + theMonth + "-" + theYear);
        Log.d(tag, "Setting GridCell " + theDay + "-" + theMonth + "-" + theYear);

        if (day_color[1].equals("GREY"))
            gridcell.setTextColor(Color.LTGRAY);

        if (day_color[1].equals("WHITE"))
            gridcell.setTextColor(Color.WHITE);

        if (day_color[1].equals("BLUE"))
            gridcell.setTextColor(getResources().getColor(R.color.static_text_color));

        return row;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
