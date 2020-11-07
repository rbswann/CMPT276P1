package com.group04.studentaide;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
-- Option to choose course if student wants to study for certain course, otherwise adds to general study time
1. User input time in milliseconds
2. Update textview to display entered time
3. Student clicks start timer


 */

public class studySession extends AppCompatActivity {

    EditText userInputTime;
    Button setTime;
    TextView countdownTimer;
    private long mStartTimeMilli;
    private long mEndTimeMilli;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(); // Set to study session XML

        userInputTime = (EditText) findViewById(R.id.userTime); // Name to be set
        setTime = (Button) findViewById(R.id.setTimeButton);
        countdownTimer = (TextView) findViewById(R.id.countDown);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void setTimer(long milliseconds){
        mStartTimeMilli = milliseconds;
    }

}
