package com.example.capturecurrentscreen;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CaptureCurrentScreenActivity extends AppCompatActivity {

    Bitmap bmScreen;
    Dialog screenDialog;
    static final int ID_SCREENDIALOG = 1;

    ImageView bmImage;
    Button btnScreenDialog_OK;
    TextView TextOut;

    View screen;
    EditText EditTextIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_current_screen);
        screen = (View) findViewById(R.id.screen);
        Button btnCaptureScreen = (Button) findViewById(R.id.capturescreen);
        EditTextIn = (EditText) findViewById(R.id.textin);
        btnCaptureScreen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                screen.setDrawingCacheEnabled(false);
                screen.setDrawingCacheEnabled(true);
                bmScreen = screen.getDrawingCache();
                showDialog(ID_SCREENDIALOG);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        screenDialog = null;
        switch (id) {
            case (ID_SCREENDIALOG):
                screenDialog = new Dialog(this);
                screenDialog.setContentView(R.layout.dialog);
                bmImage = (ImageView) screenDialog.findViewById(R.id.image);
                TextOut = (TextView) screenDialog.findViewById(R.id.textout);
                btnScreenDialog_OK = (Button) screenDialog.findViewById(R.id.okdialogbutton);
                btnScreenDialog_OK.setOnClickListener(btnScreenDialog_OKOnClickListener);
        }
        return screenDialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case (ID_SCREENDIALOG):
                dialog.setTitle("Captured Screen");
                TextOut.setText(EditTextIn.getText().toString());
                bmImage.setImageBitmap(bmScreen);
                break;
        }
    }

    private Button.OnClickListener btnScreenDialog_OKOnClickListener = new Button.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            screenDialog.dismiss();
        }
    };

}
