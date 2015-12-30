package edu.upc.fib.molgo.suarez.albert.remindit.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import edu.upc.fib.molgo.suarez.albert.remindit.R;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Event;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Meeting;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Task;
import edu.upc.fib.molgo.suarez.albert.remindit.utils.Utils;

public class MainActivity extends ActionBarActivity
{
    public static final String MY_ACCOUNT_NAME = "albert";
    public static final String CALENDAR_NAME = "Remind it Calendar";
    public static final String EVENT_TO_ADD = "EventToAdd";
    public static final String TOAST_ERROR_FIND_ID = "Error to find Calendar";
    public static final String CALENDAR_TIME_ZONE = "Europe/Berlin";
    public static final String OWNER_ACCOUNT = "some.account@googlemail.com";
    public static final String UTC_TIME_ZONE = "UTC";
    public static final int UNDONE_TASK = 0;
    public static final int DONE_TASK = 1;

    Event eventAdded;
    String descriptionAssociatedMeeting;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null) return;
        eventAdded = (Event) data.getSerializableExtra(AddActivity.EVENT_TO_SEND);
        if (!eventAdded.isMeeting()) {
            descriptionAssociatedMeeting = data.getStringExtra(AddActivity.ASSOCIATED_MEETING);
        }

        if (eventAdded.isMeeting())
        {
            Meeting meeting = (Meeting) eventAdded;
            addMeeting(meeting.getDescription(), meeting.getDay(), meeting.getMonth(),meeting.getYear(),
                    meeting.getStartHour(), meeting.getStartMinute(), meeting.getEndHour(), meeting.getEndMinute());
        }
        else
        {
            Task task = (Task) eventAdded;
            task.setMeetingAssociated(descriptionAssociatedMeeting);
            addTask(task.getTitle(), task.getStartDay(), task.getStartMonth(), task.getStartYear(),
                    task.getEndDay(), task.getEndMonth(), task.getEndYear(), task.getMeetingAssociated());
        }

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
        // Object that represents the values we want to add
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Calendars.ACCOUNT_NAME,             MY_ACCOUNT_NAME);
        values.put(CalendarContract.Calendars.ACCOUNT_TYPE,             CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(CalendarContract.Calendars.NAME,                     CALENDAR_NAME);
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,    CALENDAR_NAME);
        values.put(CalendarContract.Calendars.CALENDAR_COLOR, 0xFFFF0000);
        values.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        values.put(CalendarContract.Calendars.OWNER_ACCOUNT, OWNER_ACCOUNT);
        values.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, CALENDAR_TIME_ZONE);
        values.put(CalendarContract.Calendars.SYNC_EVENTS, 1);

        // Additional query parameters
        Uri.Builder builder = CalendarContract.Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, MY_ACCOUNT_NAME);
        builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true");

        Uri uri = getContentResolver().insert(builder.build(), values);
    }


    private long getCalendarId()
    {
        String[] projection = new String[]{CalendarContract.Calendars._ID};
        String selection =
                CalendarContract.Calendars.ACCOUNT_NAME +
                        " = ? " ;
        String[] selArgs =
                new String[]{
                        MY_ACCOUNT_NAME};
        Cursor cursor =
                getContentResolver().
                        query(
                                CalendarContract.Calendars.CONTENT_URI,
                                projection,
                                selection,
                                selArgs,
                                null);
        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        }
        return -1;
    }

    private long addTask(String title, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, String associatedMeeting)
    {
        final long calendarId = getCalendarId();
        if (calendarId == -1) {
            Toast.makeText(MainActivity.this, TOAST_ERROR_FIND_ID, Toast.LENGTH_LONG).show();
            return -1;
        }
        // SET start date
        Calendar startCalendar = new GregorianCalendar(startDay, startMonth, startYear);
        startCalendar.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        long startTime = startCalendar.getTimeInMillis();
        // SET end date
        Calendar endCalendar = new GregorianCalendar(endDay, endMonth, endYear);
        endCalendar.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 999);
        long endTime = endCalendar.getTimeInMillis();

        // Initialize the object that represents the values we want to add
        ContentValues values = new ContentValues();
        values.put(Events.DTSTART,                  startTime);
        values.put(Events.DTEND,                    endTime);
        // How to indicate undone tasks -> In the title
        values.put(Events.TITLE,                    title);
        values.put(Events.CALENDAR_ID,              calendarId);
        values.put(Events.EVENT_TIMEZONE,           CALENDAR_TIME_ZONE);
        // How to indicate the optional associated meeting -> In the description
        values.put(Events.DESCRIPTION,              associatedMeeting);
        values.put(Events.ACCESS_LEVEL,             Events.ACCESS_PRIVATE);
        values.put(Events.SELF_ATTENDEE_STATUS,     Events.STATUS_CONFIRMED);
        values.put(Events.ALL_DAY,                  UNDONE_TASK);
        values.put(Events.ORGANIZER,                OWNER_ACCOUNT);
        values.put(Events.GUESTS_CAN_INVITE_OTHERS, 1);
        values.put(Events.GUESTS_CAN_MODIFY,        1);
        values.put(Events.AVAILABILITY,             Events.AVAILABILITY_BUSY);

        Uri uri = getContentResolver().insert(Events.CONTENT_URI, values);
        return new Long(uri.getLastPathSegment());
    }

    private long addMeeting(String description, int day, int month, int year, int startHour, int startMinute, int endHour, int endMinute)
    {
        final long calendarId = getCalendarId();
        if (calendarId == -1) {
            Toast.makeText(MainActivity.this, TOAST_ERROR_FIND_ID, Toast.LENGTH_LONG).show();
            return -1;
        }
        // SET start date
        Calendar startCalendar = new GregorianCalendar(day, month, year, startHour, startMinute, 0);
        startCalendar.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));
        startCalendar.set(Calendar.MILLISECOND, 0);
        long startTime = startCalendar.getTimeInMillis();
        // SET end date
        Calendar endCalendar = new GregorianCalendar(day, month, year, endHour, endMinute, 0);
        endCalendar.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE));
        endCalendar.set(Calendar.MILLISECOND, 0);
        long endTime = endCalendar.getTimeInMillis();

        // Initialize the object that represents the values we want to add
        ContentValues values = new ContentValues();
        values.put(Events.DTSTART,                  startTime);
        values.put(Events.DTEND,                    endTime);
        values.put(Events.TITLE,                    description);
        values.put(Events.CALENDAR_ID,              calendarId);
        values.put(Events.EVENT_TIMEZONE,           CALENDAR_TIME_ZONE);
        values.put(Events.ACCESS_LEVEL,             Events.ACCESS_PRIVATE);
        values.put(Events.SELF_ATTENDEE_STATUS,     Events.STATUS_CONFIRMED);
        values.put(Events.ALL_DAY,                  0);
        values.put(Events.ORGANIZER,                OWNER_ACCOUNT);
        values.put(Events.GUESTS_CAN_INVITE_OTHERS, 1);
        values.put(Events.GUESTS_CAN_MODIFY,        1);
        values.put(Events.AVAILABILITY,             Events.AVAILABILITY_BUSY);

        Uri uri = getContentResolver().insert(Events.CONTENT_URI, values);
        return new Long(uri.getLastPathSegment());
    }

}