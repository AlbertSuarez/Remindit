package edu.upc.fib.molgo.suarez.albert.remindit.activity;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.upc.fib.molgo.suarez.albert.remindit.R;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Event;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Meeting;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Task;
import edu.upc.fib.molgo.suarez.albert.remindit.utils.TaskButton;

public class DayViewActivity extends Activity {

    private ArrayList<Event> eventsOfDay;
    private String dateInLongFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_view);
        eventsOfDay = (ArrayList<Event>) getIntent().getSerializableExtra(MainActivity.EVENTS_DAY_TO_SHOW);
        dateInLongFormat = getIntent().getStringExtra(MainActivity.DATE_IN_LONG_FORMAT);
        initializeView();
    }

    private void initializeView() {
        TextView title = (TextView) findViewById(R.id.dateTitle);
        title.setText(dateInLongFormat);

        LinearLayout tasksToAdd = (LinearLayout) findViewById(R.id.tasksLinearLayout);
        RelativeLayout meetingsToAdd = (RelativeLayout) findViewById(R.id.meetings);
        int numberOfTasks = 0;

        for (Event e : eventsOfDay) {
            if (e.isMeeting()) {
                Meeting m = (Meeting) e;

            }
            else {
                Task t = (Task) e;
                tasksToAdd.addView(new TaskButton(this, t.getTitle()));
                ++numberOfTasks;
            }
        }

        if (numberOfTasks == 0) findViewById(R.id.secondView).setVisibility(View.GONE);
    }

}
