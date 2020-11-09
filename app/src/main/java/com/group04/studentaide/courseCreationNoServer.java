package com.group04.studentaide;

/*
Written By Yufeng Luo
Tested functionality locally using activity_course_creation.xml, and coursesActivity.xml

Currently no implementation for institution field or Quizzes swithc --> will be done in v2/v3
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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class courseCreationNoServer extends AppCompatActivity {


    EditText mInputCourseName;
    EditText mInputInstitution;
    Button mCreateCourse;
    CourseSingleton courseList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_creation); // Set to study session XML
        mInputCourseName = findViewById(R.id.inputCourseName);
        mInputInstitution = findViewById(R.id.institutionInput);
        mCreateCourse = findViewById(R.id.createButton);

        //Create our LinkedHashMap object from singleton
        courseList = CourseSingleton.getInstance();

        //After user enters details and clicks create course
        //They will be taken back to the main course activity page
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

    @Override
    protected void onStop() {
        super.onStop();
        readWriteStorage storageHelper = new readWriteStorage();
        LinkedHashMap<String, ArrayList<Double>> courseListLinkedHM= courseList.getLinkedHM();

        try {
            storageHelper.SaveHashMapToStorage("courses.bin", courseListLinkedHM);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
