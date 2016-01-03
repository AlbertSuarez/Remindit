package edu.upc.fib.molgo.suarez.albert.remindit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import edu.upc.fib.molgo.suarez.albert.remindit.R;
import edu.upc.fib.molgo.suarez.albert.remindit.domain.Task;
import edu.upc.fib.molgo.suarez.albert.remindit.utils.TaskLayout;
import edu.upc.fib.molgo.suarez.albert.remindit.utils.Utils;

public class UndoneTasksActivity extends Activity {

    public static final String TASKS_TO_MODIFY = "TasksToModify";
    private ArrayList<Task> undoneTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.undone_tasks);
        undoneTasks = (ArrayList<Task>) getIntent().getSerializableExtra(MainActivity.UNDONE_TASKS_TO_SHOW);
        initializeView();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(UndoneTasksActivity.this, MainActivity.class);
        i.putExtra(TASKS_TO_MODIFY, undoneTasks);
        setResult(RESULT_OK, i);
        finish();
    }

    private void initializeView() {
        LinearLayout layoutToAdd = (LinearLayout) findViewById(R.id.undoneTaskLayout);
        int marginTenInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        int marginOneInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams layoutParams;
        for (Task t : undoneTasks) {
            TaskLayout layout = new TaskLayout(this, t.getTitle(), Utils.dateToString(t.getDateStart()), Utils.dateToString(t.getDateEnd()), t.getMeetingAssociated());
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(marginTenInPx, 0, 0, marginTenInPx);
            layoutToAdd.addView(layout, layoutParams);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Go to task view
                }
            });

            View view = new View(this);
            view.setBackgroundColor(getResources().getColor(R.color.gray));
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, marginOneInPx);
            layoutParams.setMargins(0, 0, 0, marginTenInPx);
            layoutToAdd.addView(view, layoutParams);
        }
    }
}
