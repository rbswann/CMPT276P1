package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginScreen(View view){
        Intent login = new Intent(this, loginActivity.class);
        startActivity(login);
    }

    public void calendarScreen(View view){
        Intent calendar = new Intent(this, calendarActivity.class);
        startActivity(calendar);
    }

    public void statsScreen(View view){
        Intent stats = new Intent(this, statsActivity.class);
        startActivity(stats);
    }

    public void coursesScreen(View view){
        Intent courses = new Intent(this, coursesActivity.class);
        startActivity(courses);
    }
}