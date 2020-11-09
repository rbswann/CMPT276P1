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
import java.util.LinkedHashMap;


public class coursesActivity extends AppCompatActivity {

    Button createCourseClicked;
    //Button to open an activity where spinner can choose which course to look at?

    private boolean fileRead = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        //Instantiate courses object that will be used throughout our course activities
        //With getInstance, if there is no instance of courseList it will be created
        CourseSingleton courseList = CourseSingleton.getInstance();

        //On first application opening, it will attempt to read in the data from storage into our courseList HashMap
        if(!fileRead){
            LinkedHashMap<String, ArrayList<Double>> courseListLinkedHM = courseList.getLinkedHM();
            ReadWriteStorage writeStorage = new ReadWriteStorage();
            courseListLinkedHM = writeStorage.LoadHashMapFromStorage("courses.bin");
            fileRead = true;
        }

        createCourseClicked.findViewById(R.id.courseCreate);

        createCourseClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseCreate();
            }
        });

    }

    //When create course button is clicked, user will be taken to new activity where they can create a course
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