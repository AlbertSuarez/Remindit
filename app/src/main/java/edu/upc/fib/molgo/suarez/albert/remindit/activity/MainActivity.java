package edu.upc.fib.molgo.suarez.albert.remindit.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import edu.upc.fib.molgo.suarez.albert.remindit.R;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Event;
import edu.upc.fib.molgo.suarez.albert.remindit.utils.Utils;

public class MainActivity extends ActionBarActivity
{

    public static final String MY_ACCOUNT_NAME = "albert.suarez.molgo";
    public static final String CALENDAR_NAME = "Remind it Calendar";
    public static final String EVENT_TO_ADD = "EventToAdd";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeProvider();
        initializeView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.add_event:
                Event event = new Event();
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                i.putExtra(EVENT_TO_ADD, event);
                startActivityForResult(i, 0);
                break;
            case R.id.list_undone_tasks:

                break;
            case R.id.action_settings:

                break;
            case R.id.help:

                break;
            case R.id.about:

                break;
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeView()
    {
        TextView monday = (TextView) findViewById(R.id.mondayText1);
        TextView tuesday = (TextView) findViewById(R.id.tuesdayText1);
        TextView wednesday = (TextView) findViewById(R.id.wednesdayText1);
        TextView thursday = (TextView) findViewById(R.id.thursdayText1);
        TextView friday = (TextView) findViewById(R.id.fridayText1);
        TextView saturday = (TextView) findViewById(R.id.saturdayText1);
        TextView sunday = (TextView) findViewById(R.id.sundayText1);
        TextView month = (TextView) findViewById(R.id.currentMonthTextView);
        TextView year = (TextView) findViewById(R.id.currentYearTextView);

        String[] days = Utils.getDaysOfWeek();

        monday.setText(days[0]);
        tuesday.setText(days[1]);
        wednesday.setText(days[2]);
        thursday.setText(days[3]);
        friday.setText(days[4]);
        saturday.setText(days[5]);
        sunday.setText(days[6]);
        month.setText(Utils.compress(days[7]));
        year.setText(days[8]);
    }

    private void initializeProvider()
    {
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Calendars.ACCOUNT_NAME,             MY_ACCOUNT_NAME);
        values.put(CalendarContract.Calendars.ACCOUNT_TYPE,             CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(CalendarContract.Calendars.NAME,                     CALENDAR_NAME);
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,    CALENDAR_NAME);
        values.put(CalendarContract.Calendars.CALENDAR_COLOR,           0xFFFF0000);
        values.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,    CalendarContract.Calendars.CAL_ACCESS_OWNER);
        values.put(CalendarContract.Calendars.OWNER_ACCOUNT,            "some.account@googlemail.com");
        values.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE,       "Europe/Berlin");
        values.put(CalendarContract.Calendars.SYNC_EVENTS,              1);

        Uri.Builder builder = CalendarContract.Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME,   MY_ACCOUNT_NAME);
        builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE,   CalendarContract.ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER,    "true");

        Uri uri = getContentResolver().insert(builder.build(), values);
    }

    private long getCalendarId()
    {
        String[] projection = new String[]{CalendarContract.Calendars._ID};
        String selection = CalendarContract.Calendars.ACCOUNT_NAME + " = ? " +
                        CalendarContract.Calendars.ACCOUNT_TYPE + " = ? ";

        String[] selArgs =
                new String[]{MY_ACCOUNT_NAME, CalendarContract.ACCOUNT_TYPE_LOCAL};
        Cursor cursor = getContentResolver().
                query(
                        CalendarContract.Calendars.CONTENT_URI,
                        projection,
                        selection,
                        selArgs,
                        null);
        if (cursor.moveToFirst()) return cursor.getLong(0);
        return -1;
    }

}