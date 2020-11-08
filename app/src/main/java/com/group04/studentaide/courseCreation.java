package com.group04.studentaide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.group04.studentaide.serverURL.COURSE_URL;

/*
Written by: Yufeng Luo
Enter course name -- POST to server
Set end date -- No end date in JSON template
Course creation generates a course ID also posted to server -- 5 digit(?)
 */

public class courseCreation extends AppCompatActivity {

    Button courseCreation;
    EditText inputCourseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_creation);

        //Link buttons and edittext

        courseCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCourse();
            }
        });

    }


    private void createCourse(){

        String courseName = inputCourseName.getText().toString().trim();
        String courseID = Integer.toString(generateCourseID());
        //Generate courseID when create course clicked

        JsonObjectRequest jsonObject = new JSONObject(Request.Method.GET, COURSE_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Course created", Toast.LENGTH_SHORT).show();
                        Intent openCourse = new Intent(getApplicationContext(), coursesActivity.class);
                        startActivity(openCourse);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
             }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("course_name", courseName);
                params.put("course_id", courseID);
                return params;
            }
        };
        StudentAideSingleton.getInstance(this).addToRequestQueue(jsonObject);
    }

    public static int generateCourseID(){
        int n = 1000 + new Random().nextInt(9999);
        return n;
    }


}