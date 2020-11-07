package com.group04.studentaide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class coursesActivity extends AppCompatActivity {

    Button createCourseClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        createCourseClicked.findViewById(R.id.courseCreate);

        createCourseClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseCreate();
            }
        });

    }

    private void courseCreate(){
        Intent create = new Intent(this, courseCreation.class);
        startActivity(create);
    }

    /*
    To be completed in later versions

    public void courseSelect(){

    }

    public void courseJoin(){

    }
     */
}