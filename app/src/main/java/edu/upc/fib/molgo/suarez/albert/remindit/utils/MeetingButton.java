package edu.upc.fib.molgo.suarez.albert.remindit.utils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.Button;

public class MeetingButton extends Button {

    public MeetingButton(Context context, String text) {
        super(context);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        setText(text);
    }
}
