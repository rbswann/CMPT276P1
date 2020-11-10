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
Written by: Yufeng Luo

-- Option to choose course if student wants to study for certain course, otherwise adds to general study time
Assume user inputs time in whole minutes because who sets a timer to study for 55 mins and 25 seconds
1. User input time in milliseconds
2. Update textview to display entered time in minutes
3. Student clicks start timer
4. When timer finishes -- update studyStatistics time // want to implement ability to choose certain course, may need a course class to store information first?
5. Send request and update data in server

//TODO: Study statistics added to total or certain course

 */

public class studySession extends AppCompatActivity {


    EditText userInputTime;
    Button setTime;
    Button startTime;
    Button pauseTime;
    Button resetTime;
    TextView textCountdownTimer;
    CourseSingleton courseList;

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
                // Create instance of CourseSingleton in order to store duration into LinkedHashMap.
                // Implemented here temporarily for the demo so the class would not have to sit through entire duration of timer to
                // show that the data gets stored properly into HashMap.
                CourseSingleton courseList = CourseSingleton.getInstance();
                String input = userInputTime.getText().toString().trim();
                double duration = Double.valueOf(input);

                if (input.length() == 0){
                    goodToGo = false;
                    Toast.makeText(getApplicationContext(), "Please enter a time.", Toast.LENGTH_SHORT).show();
                }else{
                    long millisLeftToTime = Long.parseLong(input) * 60000;
                    if (millisLeftToTime == 0){
                        goodToGo = false;
                        Toast.makeText(getApplicationContext(), "Please enter a time.", Toast.LENGTH_SHORT).show();
                    }

                    // No ability to select course yet, therefore insert dummy data of 1 in order to retrieve time.
                    courseList.setStudyTime("Test", duration);
                    setTimer(millisLeftToTime);
                    userInputTime.setText("");
                }

            }
        });

    }

    // Load the LinkedHashMap into internal storage after leaving studySession activity (may have added values)
    @Override
    protected void onStop() {
        super.onStop();
        ReadWriteStorage storageHelper = new ReadWriteStorage();
        LinkedHashMap<String, ArrayList<Double>> courseListLinkedHM= courseList.getLinkedHM();

        try {
            storageHelper.SaveHashMapToStorage("courses.bin", courseListLinkedHM);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Timer Functions
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
                //Create an object
                //totalTimeStudying = totalTimeStudying + (startTimeMilli /60000) in minutes
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

    //Updates timer to reflect the time inputed by user in minutes
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
