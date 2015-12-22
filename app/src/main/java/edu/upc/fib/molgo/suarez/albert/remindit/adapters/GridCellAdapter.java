package edu.upc.fib.molgo.suarez.albert.remindit.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by usuario on 22/12/2015.
 */
public class GridCellAdapter extends BaseAdapter implements DialogInterface.OnClickListener {

    private static final String tag = "GridCellAdapter";
    private static final int DAY_OFFSET = 1;
    private final String[] weekDays = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private final String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private final int[] daysOfMonths = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final int month, year;
    private final Context context;
    private final List<String> list;
    //private final HashMap eventsPerMonthMap;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

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
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
