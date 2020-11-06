/*
*
* UNTESTED
*
* User login will check information entered serverside and use StringRequest response to determine whether or not credentials are known in database
* On successful login, new Intent will be created taking users to MainActivity
*
* TODO: check server response is success to notify that user login is successful, otherwise allow user to try again
*
* */

package com.group04.studentaide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.group04.studentaide.RegistrationActivity;

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
        final String userName = inputUserName.getText().toString();
        final String passWord = inputPassword.getText().toString();
    }
}