package edu.upc.fib.molgo.suarez.albert.remindit.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.*;

import edu.upc.fib.molgo.suarez.albert.remindit.R;

public class MainActivity extends ActionBarActivity {

    CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //TODO Fer que entri a una altre activity on es vegi la feina feta durant el dia
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + (month+1) + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }

}
