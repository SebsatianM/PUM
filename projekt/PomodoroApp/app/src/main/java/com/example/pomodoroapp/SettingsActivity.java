package com.example.pomodoroapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputLayout;


public class SettingsActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    String userID;
    String sessionTimeNewString, breakTimeNewString;
    EditText sessionLengthEditText, breakLengthEditText;
    public Integer sessionTimeNew, breakTimeNew;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sessionLengthEditText = findViewById(R.id.sesstionLength);
        breakLengthEditText = findViewById(R.id.breakLength);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            userID = acct.getId();
        } else {
            userID = "default";
        }
    }

    public void saveSettings(View view) {
        sessionTimeNewString = sessionLengthEditText.getText().toString();
        breakTimeNewString = breakLengthEditText.getText().toString();
        db = new DBHelper(this);
        if (sessionTimeNewString != null && breakTimeNewString != null) {
            sessionTimeNew = Integer.parseInt(sessionTimeNewString);
            breakTimeNew = Integer.parseInt(breakTimeNewString);
            Boolean checkinserdata = db.updateSettings(userID, sessionTimeNew, breakTimeNew);
            if (checkinserdata == true) {
                Toast.makeText(getApplicationContext(), "Entry Updated", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Entry Not Updated. Inputs must be provided", Toast.LENGTH_SHORT).show();
        }

    }

}

