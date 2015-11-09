package com.example.michael.bae;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class LogingIn extends ActionBarActivity {
    EditText emailET, passET;
    Button loginBT;
    TextView forgotPass;
    final static private String FIRE_BASE_URL = "https://b-a-e.firebaseio.com/";
    Firebase myBaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging_in);
        myBaseRef = new Firebase(FIRE_BASE_URL);

        emailET = (EditText)findViewById(R.id.text_login_email);
        passET = (EditText)findViewById(R.id.text_login_pass);
        loginBT = (Button)findViewById(R.id.second_login_button);
        forgotPass = (TextView)findViewById(R.id.forgot_password);

        setButtonOnClickListener();
    }

    private void setButtonOnClickListener() {
        final String email = (emailET).getText().toString();
       final String pass = (passET).getText().toString();

        if (!email.isEmpty() && !pass.isEmpty()){
            loginBT.setEnabled(true);
        }

            loginBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (email.trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please Enter an Email address", Toast.LENGTH_SHORT).show();
                    }else if (pass.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please Enter a Password", Toast.LENGTH_SHORT).show();
                    }else {
                        myBaseRef.authWithPassword(email,pass, new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                startActivity(new Intent(LogingIn.this, MainActivity.class));
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loging_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

