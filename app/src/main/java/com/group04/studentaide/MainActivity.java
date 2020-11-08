package com.group04.studentaide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button openCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openCourses = (Button) findViewById(R.id.coursesOpen);

        openCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursesScreen(); //
            }
        });

    }

    //Yufeng: I'm not sure if View view needs to be passed into these functions because when the functions are called
    //and the new Intent is created, startActivity will open the class that is associated with the Intent

    public void loginScreen(View view){
        Intent login = new Intent(this, loginActivity.class);
        startActivity(login);
    }

    public void calendarScreen(View view){
        Intent calendar = new Intent(this, calendarActivity.class);
        startActivity(calendar);
    }

    public void coursesScreen(){
        Intent courses = new Intent(this, coursesActivity.class);
        startActivity(courses);
    }
}