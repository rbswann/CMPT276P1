/*
* UNTESTED
*
* Registration Class POSTS user entered information using Volley to Server URL in JSONObject format
* Using Volleys built-in functionality getParams() that utilizes hashmaps to store key-pair values
*
* Makes use of StudentAideSingleton class to create RequestQueues and add Volley requests to be sent to server
*
* */

package com.group04.studentaide;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.group04.studentaide.serverURL.*;
import static com.group04.studentaide.SharedPreferencesUtility.*;

public class RegistrationActivity extends AppCompatActivity {

    //Initialize buttons globally
    Button registerButton;
    EditText inputFirstName;
    EditText inputLastName;
    EditText inputEmail; // This will be the username
    EditText inputPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration); // CHANGE TO NAME OF PROPER XML FILE


        //Check if user is already logged in, if so go straight to app homepage
        if (SharedPreferencesUtility.getInstance(this).isLoggedIn()){
            finish();
            //
            startActivity(new Intent(this, MainActivity.class));
        }

        registerButton = (Button) findViewById(R.id.login2);
        inputFirstName = (EditText) findViewById(R.id.editTextTextPersonName);
        inputLastName = (EditText) findViewById(R.id.editTextTextPersonName2);
        inputEmail = (EditText) findViewById(R.id.editTextTextPersonName3);

        //Maybe make it so that password needs to be entered the same twice -- ensure user is typing in correct password
        inputPassword = (EditText) findViewById(R.id.editTextTextPersonName4);

        //Want to error check for valid email
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    //User TextUtils class to check if EditText field is empty
    //When user is entering email address -- ensure that there is an '@' for email

    //When user clicks register button -> use registerUser() to send POST request
    private void registerUser(){


        String firstName = inputFirstName.getText().toString().trim();
        String lastName = inputLastName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        //onclick listener for boolean to check whether user selects student or educator
        //Boolean educator;


        //Ensure that all text fields are entered by user
        if (TextUtils.isEmpty(email)){
            inputEmail.setError("Please enter your email");
            inputEmail.requestFocus(); // requestFocus will make the focus go to this box that is empty
        }

        if (TextUtils.isEmpty(firstName)){
            inputFirstName.setError("Please enter your first name");
            inputFirstName.requestFocus(); // requestFocus will make the focus go to this box that is empty
        }

        if (TextUtils.isEmpty(lastName)){
            inputLastName.setError("Please enter your last name");
            inputLastName.requestFocus(); // requestFocus will make the focus go to this box that is empty
        }

        if (TextUtils.isEmpty(password)){
            inputPassword.setError("Please enter a password");
            inputPassword.requestFocus(); // requestFocus will make the focus go to this box that is empty
        }

        //TODO: Check if email is already been used in database

        //POST method here to upload user information to server/database
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
            }){

            //Data to be sent to database
            @Override
            public Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", email);
                params.put("password", password);
                params.put("firstname", firstName);
                params.put("lastname", lastName);
                //Boolean educator later
                return params;
            }

            //For shared preferences -- key-pair values in hashmap
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String,String> headers = new HashMap<String, String>();
                headers.put(KEY_firstName, firstName);
                headers.put(KEY_lastName, lastName);
                headers.put(KEY_email, email);
                return headers;
            }
        };

        //Singleton class used here to create new RequestQueue and send off our JsonObject
        StudentAideSingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
}
