package com.group04.studentaide;

/*
Written By Yufeng Luo
Tested functionality locally using activity_course_creation.xml, and coursesActivity.xml
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class courseCreationNoServer extends AppCompatActivity {


    EditText mInputCourseName;
    EditText mInputInstitution;
    Button mCreateCourse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set to study session XML
        mInputCourseName = findViewById(R.id.hint);
        mInputInstitution = findViewById(R.id.institution);
        mCreateCourse = findViewById(R.id.createButton);

        //Create our LinkedHashMap object from singleton
        CourseSingleton courseList = CourseSingleton.getInstance();

        mCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCourse(courseList);
                Toast.makeText(getApplicationContext(), "Course has been created.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),coursesActivity.class);
                startActivity(intent);

            }
        });

    }

    private void createCourse(CourseSingleton courseList){
        String inputCourseName = mInputCourseName.getText().toString().trim();

        if (TextUtils.isEmpty(inputCourseName)){
            mInputCourseName.setError("Please enter your email");
            mInputCourseName.requestFocus(); // requestFocus will make the focus go to this box that is empty
        }

        if(!inputCourseName.isEmpty()) {
            courseList.setCourseName(inputCourseName);
            courseList.setCourseKeys(inputCourseName);
        }
    }
}
