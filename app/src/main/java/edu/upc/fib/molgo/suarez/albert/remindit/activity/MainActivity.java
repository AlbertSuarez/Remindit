package edu.upc.fib.molgo.suarez.albert.remindit.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.upc.fib.molgo.suarez.albert.remindit.R;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Event;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Meeting;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Task;
import edu.upc.fib.molgo.suarez.albert.remindit.utils.MeetingButton;
import edu.upc.fib.molgo.suarez.albert.remindit.utils.OnSwipeTouchListener;
import edu.upc.fib.molgo.suarez.albert.remindit.utils.Utils;

public class MainActivity extends ActionBarActivity
{
    public static final String MY_ACCOUNT_NAME =            "albert.suarez.molgo";
    public static final String CALENDAR_NAME =              "Remind it Calendar";
    public static final String EVENT_TO_ADD =               "EventToAdd";
    public static final String TOAST_ERROR_FIND_ID =        "Error to find Calendar";
    public static final String TOAST_ERROR_FORMAT_DATE =    "Error to format Date";
    public static final String CALENDAR_TIME_ZONE =         "Europe/Berlin";
    public static final String OWNER_ACCOUNT =              "some.account@googlemail.com";
    public static final String UTC_TIME_ZONE =              "UTC";
    public static final String SETTINGS_VARIABLE_KEY =      "settings";
    public static final String SETTINGS_VARIABLE_2_KEY =    "settings2";
    public static final String CALENDAR_ID_KEY =            "calendarId";
    public static final int UNDONE_TASK =                   0;
    public static final int DONE_TASK =                     1;
    public static final int MEETING =                       1;
    public static final int TASK =                          0;

    public static final String MONDAY =                     "monday";
    public static final String TUESDAY =                    "tuesday";
    public static final String WEDNESDAY =                  "wednesday";
    public static final String THURSDAY =                   "thursday";
    public static final String FRIDAY =                     "friday";
    public static final String SATURDAY =                   "saturday";
    public static final String SUNDAY =                     "sunday";

    Event eventAdded;
    String descriptionAssociatedMeeting;
    long calendarId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        initializeProvider();
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
                ArrayList<Event> e = findByWeek("28/12/2015", "03/01/2016");
                Log.d("#### Find by week:", e.toString());
                break;
            case R.id.action_settings:

                break;
            case R.id.help:

                break;
            case R.id.about:
                deleteAll();
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
            addMeeting(meeting.getDescription(), meeting.getDay(), meeting.getMonth(), meeting.getYear(),
                    meeting.getStartHour(), meeting.getStartMinute(), meeting.getEndHour(), meeting.getEndMinute());
        }
        else
        {
            Task task = (Task) eventAdded;
            task.setMeetingAssociated(descriptionAssociatedMeeting);
            addTask(task.getTitle(), task.getStartDay(), task.getStartMonth(), task.getStartYear(),
                    task.getEndDay(), task.getEndMonth(), task.getEndYear(), task.getMeetingAssociated());
        }
        updateView();
    }

    private void deleteEventsView()
    {
        RelativeLayout parentView;
        // Meeting Buttons
        parentView = (RelativeLayout) findViewById(R.id.mondayEvents);
        for (int i = 0; i < parentView.getChildCount(); i++) {
            View childView = parentView.getChildAt(i);
            if (childView instanceof MeetingButton) childView.setVisibility(View.INVISIBLE);
        }
        parentView = (RelativeLayout) findViewById(R.id.tuesdayEvents);
        for (int i = 0; i < parentView.getChildCount(); i++) {
            View childView = parentView.getChildAt(i);
            if (childView instanceof MeetingButton) childView.setVisibility(View.INVISIBLE);
        }
        parentView = (RelativeLayout) findViewById(R.id.wednesdayEvents);
        for (int i = 0; i < parentView.getChildCount(); i++) {
            View childView = parentView.getChildAt(i);
            if (childView instanceof MeetingButton) childView.setVisibility(View.INVISIBLE);
        }
        parentView = (RelativeLayout) findViewById(R.id.thursdayEvents);
        for (int i = 0; i < parentView.getChildCount(); i++) {
            View childView = parentView.getChildAt(i);
            if (childView instanceof MeetingButton) childView.setVisibility(View.INVISIBLE);
        }
        parentView = (RelativeLayout) findViewById(R.id.fridayEvents);
        for (int i = 0; i < parentView.getChildCount(); i++) {
            View childView = parentView.getChildAt(i);
            if (childView instanceof MeetingButton) childView.setVisibility(View.INVISIBLE);
        }
        parentView = (RelativeLayout) findViewById(R.id.saturdayEvents);
        for (int i = 0; i < parentView.getChildCount(); i++) {
            View childView = parentView.getChildAt(i);
            if (childView instanceof MeetingButton) childView.setVisibility(View.INVISIBLE);
        }
        parentView = (RelativeLayout) findViewById(R.id.sundayEvents);
        for (int i = 0; i < parentView.getChildCount(); i++) {
            View childView = parentView.getChildAt(i);
            if (childView instanceof MeetingButton) childView.setVisibility(View.INVISIBLE);
        }

        //Tasks Buttons
        LinearLayout tasksLayout;
        tasksLayout = (LinearLayout) findViewById(R.id.mondayTasks);
        for (int i = 0; i < tasksLayout.getChildCount(); i++) parentView.getChildAt(i).setVisibility(View.INVISIBLE);
        tasksLayout = (LinearLayout) findViewById(R.id.tuesdayTasks);
        for (int i = 0; i < tasksLayout.getChildCount(); i++) parentView.getChildAt(i).setVisibility(View.INVISIBLE);
        tasksLayout = (LinearLayout) findViewById(R.id.wednesdayTasks);
        for (int i = 0; i < tasksLayout.getChildCount(); i++) parentView.getChildAt(i).setVisibility(View.INVISIBLE);
        tasksLayout = (LinearLayout) findViewById(R.id.thursdayTasks);
        for (int i = 0; i < tasksLayout.getChildCount(); i++) parentView.getChildAt(i).setVisibility(View.INVISIBLE);
        tasksLayout = (LinearLayout) findViewById(R.id.fridayTasks);
        for (int i = 0; i < tasksLayout.getChildCount(); i++) parentView.getChildAt(i).setVisibility(View.INVISIBLE);
        tasksLayout = (LinearLayout) findViewById(R.id.saturdayTasks);
        for (int i = 0; i < tasksLayout.getChildCount(); i++) parentView.getChildAt(i).setVisibility(View.INVISIBLE);
        tasksLayout = (LinearLayout) findViewById(R.id.sundayTasks);
        for (int i = 0; i < tasksLayout.getChildCount(); i++) parentView.getChildAt(i).setVisibility(View.INVISIBLE);
    }

    private void updateView()
    {
        deleteEventsView();

        String startDay = Utils.getFirstDayOfTheCurrentWeek();
        String endDay = Utils.getLastDayOfTheCurrentWeek();
        ArrayList<Event> events = findByWeek(startDay, endDay);
        for (Event e : events) {
            if (e.isMeeting()) {
                Meeting m = (Meeting) e;
                String day = Utils.getDayOfWeekInString(m.getDate());
                String text = m.getDescription();
                int startMinute = m.getTotalStartMinute();
                int endMinute = m.getTotalEndMinute();
                createMeetingButton(day, text, startMinute, endMinute);
            }
            else {
                Task t = (Task) e;

            }
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

        // Initialize swipes
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                modifyDays(false);
                updateView();
            }

            public void onSwipeLeft() {
                modifyDays(true);
                updateView();
            }

        });
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                modifyDays(false);
                updateView();
            }

            public void onSwipeLeft() {
                modifyDays(true);
                updateView();
            }
        });
    }

    private void modifyDays(boolean increase)
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

        String[] days;
        if (increase) days = Utils.nextWeek();
        else days = Utils.previousWeek();

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
        SharedPreferences settings = getSharedPreferences(SETTINGS_VARIABLE_KEY, 0);
        boolean silent = settings.getBoolean(SETTINGS_VARIABLE_2_KEY, false);
        if (!silent) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(SETTINGS_VARIABLE_2_KEY, true);
            editor.commit();
            createNewCalendar();
            calendarId = getCalendarId();
            if (calendarId == -1) Toast.makeText(MainActivity.this, TOAST_ERROR_FIND_ID, Toast.LENGTH_LONG).show();
            editor.putLong(CALENDAR_ID_KEY, calendarId);
            editor.commit();
        }
        else {
            calendarId = settings.getLong(CALENDAR_ID_KEY, -1);
            updateView();
        }
    }

    private void createNewCalendar()
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
        String selection = CalendarContract.Calendars.ACCOUNT_NAME + " = ? " ;
        String[] selArgs = new String[]{MY_ACCOUNT_NAME};
        Cursor cursor =
                getContentResolver().
                        query(
                                CalendarContract.Calendars.CONTENT_URI,
                                projection,
                                selection,
                                selArgs,
                                null);
        if (cursor.moveToFirst()) return cursor.getLong(0);
        return -1;
    }

    private long addTask(String title, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear, String associatedMeeting)
    {
        // SET start date
        long startTime = Utils.createDate(startDay, startMonth, startYear, 0, 0);
        // SET end date
        long endTime = Utils.createDate(endDay, endMonth, endYear, 0, 0);

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
        values.put(Events.GUESTS_CAN_INVITE_OTHERS, TASK);
        values.put(Events.GUESTS_CAN_MODIFY,        1);
        values.put(Events.AVAILABILITY,             Events.AVAILABILITY_BUSY);

        Uri uri = getContentResolver().insert(Events.CONTENT_URI, values);
        return new Long(uri.getLastPathSegment());
    }

    private long addMeeting(String description, int day, int month, int year, int startHour, int startMinute, int endHour, int endMinute)
    {
        // SET start date
        long startTime = Utils.createDate(day, month, year, startHour, startMinute);
        // SET end date
        long endTime = Utils.createDate(day, month, year, endHour, endMinute);

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
        values.put(Events.GUESTS_CAN_INVITE_OTHERS, MEETING);
        values.put(Events.GUESTS_CAN_MODIFY,        1);
        values.put(Events.AVAILABILITY,             Events.AVAILABILITY_BUSY);

        Uri uri = getContentResolver().insert(Events.CONTENT_URI, values);
        return new Long(uri.getLastPathSegment());
    }

    private Meeting findMeetingByDescription(String description)
    {
        String[] projection = new String[]{
                Events._ID,
                Events.TITLE,
                Events.DTSTART,
                Events.DTEND
        };
        String selection = Events.TITLE + " = ? " ;
        String[] selArgs = new String[]{description};
        Cursor cursor =
                getContentResolver().
                        query(
                                Events.CONTENT_URI,
                                projection,
                                selection,
                                selArgs,
                                null);
        if (cursor.moveToFirst()) {
            return new Meeting( cursor.getLong(0),
                                new Date(cursor.getLong(2)),
                                new Date(cursor.getLong(3)),
                                cursor.getString(1));
        }
        cursor.close();
        return new Meeting();
    }

    private Meeting findMeetingById(long id)
    {
        String[] projection = new String[]{
                Events._ID,
                Events.TITLE,
                Events.DTSTART,
                Events.DTEND
        };
        String selection = Events._ID + " = ? " ;
        String[] selArgs = new String[]{Long.toString(id)};
        Cursor cursor =
                getContentResolver().
                        query(
                                Events.CONTENT_URI,
                                projection,
                                selection,
                                selArgs,
                                null);
        if (cursor.moveToFirst()) {
            return new Meeting( cursor.getLong(0),
                                new Date(cursor.getLong(2)),
                                new Date(cursor.getLong(3)),
                                cursor.getString(1));
        }
        cursor.close();
        return new Meeting();
    }

    private Task findTaskByTitle(String title)
    {
        String[] projection = new String[]{
                Events._ID,
                Events.TITLE,
                Events.DTSTART,
                Events.DTEND,
                Events.ALL_DAY,
                Events.DESCRIPTION
        };
        String selection = Events.TITLE + " = ? " ;
        String[] selArgs = new String[]{title};
        Cursor cursor =
                getContentResolver().
                        query(
                                Events.CONTENT_URI,
                                projection,
                                selection,
                                selArgs,
                                null);
        if (cursor.moveToFirst()) {
            boolean done;
            if (cursor.getInt(4) == DONE_TASK) done = true;
            else done = false;
            return new Task(    cursor.getLong(0),
                                cursor.getString(1),
                                new Date(cursor.getLong(2)),
                                new Date(cursor.getLong(3)),
                                done,
                                cursor.getString(5));
        }
        cursor.close();
        return new Task();
    }

    private ArrayList<Event> findByWeek(String firstDayString, String lastDayString)
    {
        ArrayList<Event> result = new ArrayList<>();
        ArrayList<Event> events = list();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date firstDay = dateFormat.parse(firstDayString);
            Date lastDay = dateFormat.parse(lastDayString);
            for (Event e : events) {
                if (e.isMeeting()) {
                    Meeting m = (Meeting) e;
                    if (Utils.compareTo(m.getDate(), firstDay) == 1 && Utils.compareTo(m.getDate(), lastDay) == -1) {
                        result.add(m);
                    }
                } else {
                    Task t = (Task) e;
                    if (Utils.compareTo(t.getDateStart(), firstDay) == 1 && Utils.compareTo(t.getDateStart(), lastDay) == -1) {
                        result.add(t);
                    }
                }
            }
        }
        catch (ParseException pe) {
            Toast.makeText(MainActivity.this, TOAST_ERROR_FORMAT_DATE, Toast.LENGTH_LONG).show();
        }
        return result;
    }

    private ArrayList<Event> list()
    {
        ArrayList<Event> events = new ArrayList<>();
        String[] projection = new String[]{
                Events.GUESTS_CAN_INVITE_OTHERS,
                Events._ID,
                Events.TITLE,
                Events.DTSTART,
                Events.DTEND,
                Events.ALL_DAY,
                Events.DESCRIPTION
        };
        String selection = Events.CALENDAR_ID + " = ? " ;
        String[] selArgs = new String[]{Long.toString(calendarId)};
        Cursor cursor =
                getContentResolver().
                        query(
                                Events.CONTENT_URI,
                                projection,
                                selection,
                                selArgs,
                                null);
        while (cursor.moveToNext()) {
            int type = cursor.getInt(0);
            if (type == MEETING)
                events.add(new Meeting(cursor.getLong(1), new Date(cursor.getLong(3)), new Date(cursor.getLong(4)), cursor.getString(2)));
            else if (type == TASK) {
                boolean done;
                if (cursor.getInt(5) == DONE_TASK) done = true;
                else done = false;
                events.add(new Task(cursor.getLong(1), cursor.getString(2), new Date(cursor.getLong(3)), new Date(cursor.getLong(4)), done, cursor.getString(6)));
            }
        }
        cursor.close();
        return events;
    }

    private void deleteAll()
    {
        String[] selArgs =
                new String[]{Long.toString(calendarId)};
        String selection = Events.CALENDAR_ID + " = ? " ;
        int deleted =
                getContentResolver().
                        delete(
                                Events.CONTENT_URI,
                                selection,
                                selArgs);
    }

    private void createMeetingButton(String day, String text, int startTime, int endTime)
    {
        RelativeLayout dayLayout;
        if (day.equals(MONDAY)) dayLayout = (RelativeLayout) findViewById(R.id.mondayEvents);
        else if (day.equals(TUESDAY)) dayLayout = (RelativeLayout) findViewById(R.id.tuesdayEvents);
        else if (day.equals(WEDNESDAY)) dayLayout = (RelativeLayout) findViewById(R.id.wednesdayEvents);
        else if (day.equals(THURSDAY)) dayLayout = (RelativeLayout) findViewById(R.id.thursdayEvents);
        else if (day.equals(FRIDAY)) dayLayout = (RelativeLayout) findViewById(R.id.fridayEvents);
        else if (day.equals(SATURDAY)) dayLayout = (RelativeLayout) findViewById(R.id.saturdayEvents);
        else if (day.equals(SUNDAY)) dayLayout = (RelativeLayout) findViewById(R.id.sundayEvents);
        else return;

        int duration = endTime - startTime;
        int durationInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, duration, getResources().getDisplayMetrics());
        int marginInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, startTime, getResources().getDisplayMetrics());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, durationInPx);
        params.setMargins(0, marginInPx, 0, 0);
        dayLayout.addView(new MeetingButton(this, text), params);
    }

    // TODO Falta per crear
    private void createTaskButton(String day, String text, int startTime, int endTime)
    {
        RelativeLayout dayLayout;
        if (day.equals(MONDAY)) dayLayout = (RelativeLayout) findViewById(R.id.mondayEvents);
        else if (day.equals(TUESDAY)) dayLayout = (RelativeLayout) findViewById(R.id.tuesdayEvents);
        else if (day.equals(WEDNESDAY)) dayLayout = (RelativeLayout) findViewById(R.id.wednesdayEvents);
        else if (day.equals(THURSDAY)) dayLayout = (RelativeLayout) findViewById(R.id.thursdayEvents);
        else if (day.equals(FRIDAY)) dayLayout = (RelativeLayout) findViewById(R.id.fridayEvents);
        else if (day.equals(SATURDAY)) dayLayout = (RelativeLayout) findViewById(R.id.saturdayEvents);
        else if (day.equals(SUNDAY)) dayLayout = (RelativeLayout) findViewById(R.id.sundayEvents);
        else return;

        int duration = endTime - startTime;
        int durationInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, duration, getResources().getDisplayMetrics());
        int marginInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, startTime, getResources().getDisplayMetrics());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, durationInPx);
        params.setMargins(0, marginInPx, 0, 0);
        dayLayout.addView(new MeetingButton(this, text), params);
    }
}
