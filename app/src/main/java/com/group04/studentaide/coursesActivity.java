/*

Written by: Yufeng Luo

 */
package com.group04.studentaide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


public class coursesActivity extends AppCompatActivity {

    Button createCourseClicked;
    //Button to open an activity where spinner can choose which course to look at?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        //Instantiate courses object that will be used throughout our course activities
        //With getInstance, if there is no instance of courseList it will be created
        CourseSingleton courseList = CourseSingleton.getInstance();

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
    //This is code for spinner --> need arrayadapter and hashmap key values in the spinner
    //Get a reference to the new update Hashmap and arraylist when reopening activity

    CourseSingleton courseList = CourseSingleton.getInstance();
    ArrayList<String> hashKeys = courseList.courseKeys;
    mSpinner = findViewById(R.id.spinner);
    //Set spinner adapter
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, hashKeys);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String choice = parent.getItemAtPosition(position).toString();
            //random data inserted to see what appears
            courseList.setStudyTime("General", 16.3);
            courseList.setStudyTime(choice, 12.3);
            Double choiceTime = courseList.getStudyTime(choice);
            Double time = courseList.getStudyTime("General");
            Toast.makeText(getApplicationContext(), "General: " + time + choice + ":" + choiceTime, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

     */


    /*
    To be completed in later versions

    public void courseSelect(){

    }

    public void courseJoin(){

    }
     */

}