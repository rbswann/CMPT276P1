/*
*
* UNTESTED
*
* User login will check information entered serverside and use StringRequest response to determine whether or not credentials are known in database
* On successful login, new Intent will be created taking users to MainActivity
*
*
* */

package com.group04.studentaide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.group04.studentaide.serverURL.LOGIN_URL;

public class loginActivity extends AppCompatActivity {

    Button logInButton;
    EditText inputUserName;
    EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logInButton = (Button) findViewById(R.id.register);
        inputUserName = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);

        //If the response from server is success, allow login

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

    }

    //Register button clicked, open register activity
    public void register(View view){
        Intent register = new Intent(this, RegistrationActivity.class);
        startActivity(register);
    }

    //Log in button clicked
    private void logIn(){
        final String userName = inputUserName.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();


        //Ensure users fill in respective fields and prevents from sending POST request if empty
        if (TextUtils.isEmpty(userName)){
            inputUserName.setError("Please enter your email");
            inputUserName.requestFocus();
        }

        if (TextUtils.isEmpty(password)){
            inputPassword.setError("Please enter your email");
            inputPassword.requestFocus();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("success")){
                            //Open main activity
                            Intent homePage = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(homePage);
                        }else{
                            //Display login failure
                            Toast.makeText(loginActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userName);
                params.put("password", password);

                return params;
            }
        };
        StudentAideSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    /*
    //Test case: Checking user data from is valid in database
    private void checkUser(){
        JsonObjectRequest userObj = new JsonObjectRequest(Request.Method.GET, LOGIN_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        })
    }

    */

}
