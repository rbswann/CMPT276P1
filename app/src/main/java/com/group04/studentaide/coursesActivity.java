/*

Written by: Yufeng Luo

 */
package com.group04.studentaide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


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
    To be completed in later versions

    public void courseSelect(){

    }

    public void courseJoin(){

    }
     */

}