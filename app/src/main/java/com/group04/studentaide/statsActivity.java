package com.group04.studentaide;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import static com.group04.studentaide.serverURL.*;
import static com.group04.studentaide.SharedPreferencesUtility.*;

public class statsActivity extends AppCompatActivity {

    TextView totalTimeStudied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        double totalTime = getTimeTotal();
        String displayTotal = Double.toString(totalTime);

        totalTimeStudied = (TextView) findViewById(R.id.statslabel);
        totalTimeStudied.setText(displayTotal);

    }

    /* GET request to retrieve all study session data and returns sum of all duration (totalTimeStudied).
       Currently requires database to function, will change once we get internal storage working.
     */
    private double getTimeTotal(String GUID) throws Exception {

        String getUrl = ROOT_URL + "study_schedule/" + GUID;
        ArrayList<Double> durationList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("study_schedule");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONArray jsonArr2 = jsonArray.getJSONArray(i);

                        for (int j = 0; j < jsonArr2.length(); j++) {

                            JSONObject jsonObject = jsonArr2.getJSONObject(j);
                            double duration = jsonObject.getDouble("duration");

                            String start = jsonObject.getString("start");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
                            LocalDateTime currentDateTime = LocalDateTime.now();

                            if (currentDateTime.isAfter(startDateTime)) {

                                durationList.add(duration);

                            }

                        }

                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                }

            }

        });

        double totalTime = 0;

        for (int i = 0; i < durationList.size(); i++) {

            totalTime += durationList.get(i);

        }

        StudentAideSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        return totalTime;

    }

    /* GET request to retrieve all study session data and only returns sum of duration from specific courses
       (courseTime).
       Currently requires database to function, I don't think we'll be using this in v1 so I won't be
       editing it for now.
     */
    private double getTimeCourse(String GUID, int courseCode) throws Exception {

        String getUrl = ROOT_URL + "study_schedule/" + GUID;
        ArrayList<Double> courseDurationList = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("study_schedule");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONArray jsonArr2 = jsonArray.getJSONArray(i);

                        for (int j = 0; j < jsonArr2.length(); j++) {

                            JSONObject jsonObject = jsonArr2.getJSONObject(j);
                            double duration = jsonObject.getDouble("duration");
                            int courseID = jsonObject.getInt("course_id")

                            String start = jsonObject.getString("start");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            LocalDateTime startDateTime = LocalDateTime.parse(start, formatter);
                            LocalDateTime currentDateTime = LocalDateTime.now();

                            if (currentDateTime.isAfter(startDateTime) && courseID == courseCode) {

                                courseDurationList.add(duration);

                            }

                        }

                    }

                } catch (JSONException e) {

                    e.printStackTrace();

                }

            }

        });

        double courseTime = 0;

        for (int i = 0; i < courseDurationList.size(); i++) {

            courseTime += courseDurationList.get(i);

        }

        StudentAideSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        return courseTime;

    }

}