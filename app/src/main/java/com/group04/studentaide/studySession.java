package com.group04.studentaide;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    Button startTime;
    Button pauseTime;
    Button resetTime;
    TextView textCountdownTimer;

    private CountDownTimer mCountDownTimer;
    private Boolean mTimerRunning;
    private Boolean goodToGo = true;
    private long mStartTimeMilli;
    private long mTimeLeftMilli;
    private long mEndTimeMilli;
    private long mEndTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*INCLUDE XML FILE AND ATTRIBUTE IDS

        setContentView(R.layout.activity_main);

        userInputTime = (EditText) findViewById(R.id.edit_text_input);
        pauseTime = (Button) findViewById(R.id.button_pause);
        setTime = (Button) findViewById(R.id.button_set);
        textCountdownTimer = (TextView) findViewById(R.id.text_view_countdown);
        startTime = (Button) findViewById(R.id.button_start_pause);

         */
        pauseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = userInputTime.getText().toString().trim();

                if (input.length() == 0){
                    goodToGo = false;
                    Toast.makeText(getApplicationContext(), "Please enter a time.", Toast.LENGTH_SHORT).show();
                }else{
                    long millisLeftToTime = Long.parseLong(input) * 60000;
                    if (millisLeftToTime == 0){
                        goodToGo = false;
                        Toast.makeText(getApplicationContext(), "Please enter a time.", Toast.LENGTH_SHORT).show();
                    }

                    setTimer(millisLeftToTime);
                    userInputTime.setText("");
                }
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
        hideKeyboard();
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(view.getWindowToken(), 0);
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