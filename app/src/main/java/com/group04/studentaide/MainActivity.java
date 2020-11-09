package com.group04.studentaide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    
    CourseSingleton courseList;
    ReadWriteStorage storageHelper;
    private boolean fileRead = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Load the LinkedHashMap from internal storage and load it into courseList inside CourseSingleton
        if (!fileRead) {
         
            storageHelper = new ReadWriteStorage();
            courseList = CourseSingleton.getInstance();
            LinkedHashMap<String, ArrayList<Double>> lhmStorage = storageHelper.LoadHashMapFromStorage("courses.bin");

            courseList.loadHashMapStorage(lhmStorage);
            
        }
        
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

    public void statsScreen(View view){
        Intent stats = new Intent(this, studyStatistics.class);
        startActivity(stats);
    }

    public void coursesScreen(View view){
        Intent courses = new Intent(this, coursesActivity.class);
        startActivity(courses);
    }
}
