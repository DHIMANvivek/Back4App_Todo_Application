package com.example.back4apptodoapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    EditText vdEmail, vdPassword, vdName, vdConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        vdName = findViewById(R.id.vdName);
        vdEmail = findViewById(R.id.vdEmail);
        vdPassword = findViewById(R.id.vdPassword);
        vdConfirmPassword = findViewById(R.id.vdConfirmPassword);
    }

    public void signup(View view) {
        if (TextUtils.isEmpty(vdName.getText())) {
            vdName.setError("Name is required!");
        } else if (TextUtils.isEmpty(vdEmail.getText())) {
            vdEmail.setError("Email is required!");
        } else if (TextUtils.isEmpty(vdPassword.getText())) {
            vdPassword.setError("Password is required!");
        } else if (TextUtils.isEmpty(vdConfirmPassword.getText())) {
            vdConfirmPassword.setError("Confirm password is required!");
        } else if (!vdPassword.getText().toString().equals(vdConfirmPassword.getText().toString())) {
            Toast.makeText(SignupActivity.this, "Passwords are not the same!", Toast.LENGTH_LONG).show();
        } else {
            final ProgressDialog progress = new ProgressDialog(this);
            progress.setMessage("Loading...");
            progress.show();

            ParseUser user = new ParseUser();
            user.setUsername(vdEmail.getText().toString().trim());
            user.setEmail(vdEmail.getText().toString().trim());
            user.setPassword(vdPassword.getText().toString());
            user.put("name", vdName.getText().toString().trim());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    progress.dismiss();
                    if (e == null) {
                        Toast.makeText(SignupActivity.this, "Welcome!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ParseUser.logOut();
                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
