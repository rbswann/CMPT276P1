package com.group04.studentaide;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/*
-- Option to choose course if student wants to study for certain course, otherwise adds to general study time
1. User input time in milliseconds
2. Update textview to display entered time
3. Student clicks start timer


 */

public class studySession extends AppCompatActivity {

    EditText userInputTime;
    Button setTime;
    Button startPauseTime;
    Button resetTime;
    TextView textCountdownTimer;

    private CountDownTimer mCountDownTimer;
    private Boolean mTimerRunning;
    private long mStartTimeMilli;
    private long mTimeLeftMilli;
    private long mEndTimeMilli;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(); // Set to study session XML

        userInputTime = (EditText) findViewById(R.id.userTime); // Name to be set
        setTime = (Button) findViewById(R.id.setTimeButton);
        textCountdownTimer = (TextView) findViewById(R.id.countDown);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void startTimer(){
        mEndTime = System.currentTimeMillis() + mTimeLeftMilli;

        mCountDownTimer = new CountDownTimer(mTimeLeftMilli, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftMilli = millisUntilFinished;
                updateCountDown();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
            }

        }.start();
        mTimerRunning = true;
    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning  = false;
    }

    private void resetTimer(){
        mTimeLeftMilli = mStartTimeMilli;
        updateCountDown();    }

    private void setTimer(long milliseconds){
        mStartTimeMilli = milliseconds;
        resetTimer();
    }


    //Updates remaining time on timer
    private void updateCountDown(){

        int hours = (int) (mTimeLeftMilli / 1000) / 3600;
        int minutes = (int) ((mTimeLeftMilli / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftMilli / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        textCountdownTimer.setText(timeLeftFormatted);
    }

}
