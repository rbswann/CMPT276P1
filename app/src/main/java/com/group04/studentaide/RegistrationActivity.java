package com.group04.studentaide;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.id.register_activity); // CHANGE TO NAME OF PROPER XML FILE


        //Check if user is already logged in, if so go straight to app homepage
        if (SharedPreferencesUtility.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        //NEED BUTTONS AND BUTTON IDS FROM XML

    }

    //User TextUtils class to check if EditText field is empty
    //When user is entering email address -- ensure that there is an '@' for email

    private void registerUser(){
        String firstName;
        String lastName;
        String email;
        //onclick listener for boolean to check whether user selects student or educator
        Boolean educator;

        //POST method here to upload user information to server/database
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                serverURL.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{

                        }catch(Exception e){

                        } //End Catch
                    } //End onResponse
                }
        };
}
