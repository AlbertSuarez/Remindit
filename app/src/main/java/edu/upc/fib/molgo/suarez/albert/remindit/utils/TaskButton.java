package edu.upc.fib.molgo.suarez.albert.remindit.utils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout.*;

import edu.upc.fib.molgo.suarez.albert.remindit.R;

public class TaskButton extends Button {

    public TaskButton(Context context, String text) {
        super(context);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        setBackgroundColor(getResources().getColor(R.color.darkorange));
        setText(text);
    }
}
