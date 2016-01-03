package edu.upc.fib.molgo.suarez.albert.remindit.activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

import edu.upc.fib.molgo.suarez.albert.remindit.R;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Event;

public class DayViewActivity extends Activity {

    ArrayList<Event> eventsOfDay;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_view);
        initializeView();
    }

    private void initializeView() {

    }

}
