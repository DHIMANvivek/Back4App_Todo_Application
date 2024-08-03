package com.example.back4apptodoapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    EditText vdEmail, vdPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void login(View view) {
        // Check if email is empty
        if (TextUtils.isEmpty(vdEmail.getText())) {
            vdEmail.setError("Email is required!");
            return;
        }

        // Check if password is empty
        if (TextUtils.isEmpty(vdPassword.getText())) {
            vdPassword.setError("Password is required!");
            return;
        }

        // Show progress dialog
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.show();

        // Attempt login in background
        ParseUser.logInInBackground(vdEmail.getText().toString(), vdPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                progress.dismiss();
                if (user != null) {
                    // Login successful
                    Toast.makeText(LoginActivity.this, "Welcome :)", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Login failed
                    Toast.makeText(LoginActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void signup(View view) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

}