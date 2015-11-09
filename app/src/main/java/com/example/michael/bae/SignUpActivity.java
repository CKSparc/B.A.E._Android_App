package com.example.michael.bae;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;


public class SignUpActivity extends ActionBarActivity {
    final static private String FIRE_BASE_URL = "https://b-a-e.firebaseio.com/";
    Firebase myBaseRef;
    EditText nameET, emailET, passET;
    Button submitBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameET = (EditText) findViewById(R.id.text_name);
        emailET = (EditText) findViewById(R.id.text_email);
        passET = (EditText) findViewById(R.id.text_pass);
        submitBT = (Button) findViewById(R.id.su_finishBT);

        myBaseRef = new Firebase(FIRE_BASE_URL);

        setButtonOnClickListener();

        TextWatcher txtWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = (nameET).getText().toString();
                String emailT = (emailET).getText().toString().trim();
                String password = (passET).getText().toString().trim();


                if (!name.isEmpty() && !emailT.isEmpty() && !password.isEmpty()) {
                    submitBT.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        nameET.addTextChangedListener(txtWatcher);
        emailET.addTextChangedListener(txtWatcher);
        passET.addTextChangedListener(txtWatcher);

    }

    private void setButtonOnClickListener() {

        final String email = (emailET).getText().toString().trim();
        final String pass = (passET).getText().toString().trim();
       final String name = (nameET).getText().toString().trim();

        submitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pass.length() < 4) {
                    Toast.makeText(getApplicationContext(), "Password Too Short", Toast.LENGTH_SHORT).show();
                }else if (pass.length() > 16) {
                    Toast.makeText(getApplicationContext(), "Password Too Long", Toast.LENGTH_SHORT).show();
                }else {

                    myBaseRef.createUser(email, pass, new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {

                            HashMap<String, Object> user = new HashMap<>();
                            user.put("Name", name);
                            user.put("Email", email);
                            user.put("Password", pass);

                            String key = (email.substring(0, email.indexOf("@")) +
                                    email.substring(email.indexOf("@") + 1, email.indexOf(".")));

                            Firebase userRef = myBaseRef.child("Users/" + key);
                            userRef.setValue(user);

                            Toast.makeText(getApplicationContext(), "" + "Welcome" + name + "", Toast.LENGTH_SHORT).show();
                            Intent signUp = new Intent(SignUpActivity.this,
                                    LoginActivity.class);
                            startActivityForResult(signUp, 1);
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(getApplicationContext(), "Error, Email not valid", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
