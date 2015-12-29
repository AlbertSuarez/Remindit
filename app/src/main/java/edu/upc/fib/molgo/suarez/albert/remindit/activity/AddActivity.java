package edu.upc.fib.molgo.suarez.albert.remindit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.upc.fib.molgo.suarez.albert.remindit.R;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Event;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Meeting;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Task;
import edu.upc.fib.molgo.suarez.albert.remindit.utils.Utils;

public class AddActivity extends Activity {

    public static final String EMPTY = "";
    public static final String FIRST_TITLE_MEETING = "Date";
    public static final String SECOND_TITLE_MEETING = "Start time";
    public static final String SECOND_TITLE_TASK = "Start date";
    public static final String THIRD_TITLE_MEETING = "End time";
    public static final String THIRD_TITLE_TASK = "End date";
    public static final String FOURTH_TITLE_MEETING = "Description";
    public static final String FOURTH_TITLE_TASK = "Associated meeting";
    public static final String HELP_TASK = "All dates must be in dd/mm/yyyy\nand the associated meeting must exist (optional)";
    public static final String HELP_MEETING = "All dates must be in dd/mm/yyyy format\nand all hours must be in hh:mm format";
    public static final String TOAST_ERROR = "Some field is in a wrong format";

    public static final String EVENT_TO_SEND = "EventToSend";
    public static final String ASSOCIATED_MEETING = "AssociatedMeeting";

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_events);
        event = (Event) getIntent().getSerializableExtra(MainActivity.EVENT_TO_ADD);
        try {
            initializeView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    private void initializeView() throws ParseException {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Button okButton = (Button) findViewById(R.id.okButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final TextView firstOption = (TextView) findViewById(R.id.meetingDateTitle);
        final EditText firstField = (EditText) findViewById(R.id.meetingDateField);

        final TextView secondOption = (TextView) findViewById(R.id.meetingHourStartTitle);
        final EditText secondField = (EditText) findViewById(R.id.meetingHourStartField);

        final TextView thirdOption = (TextView) findViewById(R.id.meetingHourEndTitle);
        final EditText thirdField = (EditText) findViewById(R.id.meetingHourEndField);

        final TextView fourthOption = (TextView) findViewById(R.id.meetingDescriptionTitle);
        final EditText fourthField = (EditText) findViewById(R.id.meetingDescriptionField);

        final TextView help = (TextView) findViewById(R.id.helpAdd);

        final RadioButton meetingButton =(RadioButton) findViewById(R.id.meeting_button);
        final RadioButton taskButton =(RadioButton) findViewById(R.id.task_button);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup_button);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case -1:
                        // Nothing checked it
                        break;
                    case R.id.meeting_button:
                        firstOption.setText(FIRST_TITLE_MEETING);
                        firstField.setVisibility(View.VISIBLE);
                        secondOption.setText(SECOND_TITLE_MEETING);
                        secondField.setVisibility(View.VISIBLE);
                        secondField.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
                        thirdOption.setText(THIRD_TITLE_MEETING);
                        thirdField.setVisibility(View.VISIBLE);
                        thirdField.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
                        fourthOption.setText(FOURTH_TITLE_MEETING);
                        fourthField.setVisibility(View.VISIBLE);
                        help.setText(HELP_MEETING);
                        break;
                    case R.id.task_button:
                        firstOption.setText(EMPTY);
                        firstField.setVisibility(View.INVISIBLE);
                        secondOption.setText(SECOND_TITLE_TASK);
                        secondField.setVisibility(View.VISIBLE);
                        secondField.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
                        thirdOption.setText(THIRD_TITLE_TASK);
                        thirdField.setVisibility(View.VISIBLE);
                        thirdField.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
                        fourthOption.setText(FOURTH_TITLE_TASK);
                        fourthField.setVisibility(View.VISIBLE);
                        help.setText(HELP_TASK);
                        break;
                    default:
                        // Nothing to do
                        break;
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (meetingButton.isChecked()) {
                        int hourStart, minuteStart, hourEnd, minuteEnd;
                        Date startDate, endDate;
                        String description;

                        if (fourthField.getText().toString().isEmpty()) throw new ParseException("Empty Description", 138);
                        description = fourthField.getText().toString();

                        startDate = dateFormat.parse(firstField.getText().toString());
                        hourStart = Utils.getHour(secondField.getText().toString());
                        minuteStart = Utils.getMinute(secondField.getText().toString());
                        hourEnd = Utils.getHour(thirdField.getText().toString());
                        minuteEnd = Utils.getMinute(thirdField.getText().toString());

                        if (hourStart > hourEnd || (hourStart == hourEnd && minuteStart >= minuteEnd))
                            throw new ParseException("HourStart is bigger than HourEnd", 148);

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(startDate);
                        calendar.set(Calendar.HOUR_OF_DAY, hourStart);
                        calendar.set(Calendar.MINUTE, minuteStart);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        startDate = calendar.getTime();

                        calendar.set(Calendar.HOUR_OF_DAY, hourEnd);
                        calendar.set(Calendar.MINUTE, minuteEnd);
                        endDate = calendar.getTime();

                        event = new Meeting(startDate, endDate, description);
                        Log.d("Event created", event.toString());
                        Intent i = new Intent(AddActivity.this, MainActivity.class);
                        i.putExtra(EVENT_TO_SEND, event);
                        setResult(RESULT_OK, i);
                        finish();
                    }
                    else if (taskButton.isChecked()) {
                        Date startDate, endDate;
                        String meetingAssociated = "";

                        if (!fourthField.getText().toString().isEmpty()) meetingAssociated = fourthField.getText().toString();

                        startDate = dateFormat.parse(secondField.getText().toString());
                        endDate = dateFormat.parse(thirdField.getText().toString());

                        if ((Utils.getYear(startDate) > Utils.getYear(endDate)) ||
                                (Utils.getYear(startDate) == Utils.getYear(endDate) && Utils.getMonth(startDate) > Utils.getMonth(endDate)) ||
                                (Utils.getYear(startDate) == Utils.getYear(endDate) && Utils.getMonth(startDate) == Utils.getMonth(endDate) && Utils.getDay(startDate) > Utils.getDay(endDate)))
                            throw new ParseException("startDate bigger than endDate", 188);

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(startDate);
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        startDate = calendar.getTime();

                        calendar.setTime(endDate);
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        endDate = calendar.getTime();

                        event = new Task(startDate, endDate);
                        Intent i = new Intent(AddActivity.this, MainActivity.class);
                        i.putExtra(EVENT_TO_SEND, event);
                        i.putExtra(ASSOCIATED_MEETING, meetingAssociated);
                        setResult(RESULT_OK, i);
                        finish();
                    }
                }
                catch (ParseException pe) {
                    Toast.makeText(AddActivity.this, TOAST_ERROR, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}