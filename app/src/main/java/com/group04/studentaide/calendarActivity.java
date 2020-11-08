package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class calendarActivity extends AppCompatActivity {

    EditText event;
    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        event = findViewById(R.id.event);
        addEvent = findViewById(R.id.addEvent);

        addEvent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!event.getText().toString().isEmpty()){
                    Intent calendar = new Intent(Intent.ACTION_INSERT);
                    calendar.setData(CalendarContract.Events.CONTENT_URI);
                    calendar.putExtra(CalendarContract.Events.TITLE, event.getText().toString());
                    calendar.putExtra(CalendarContract.Events.ALL_DAY, true);

                    if(calendar.resolveActivity(getPackageManager()) != null){
                        startActivity(calendar);
                    }
                    else{
                        Toast.makeText(calendarActivity.this, "There is no app that can support this action.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(calendarActivity.this, "Please specify an event.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
