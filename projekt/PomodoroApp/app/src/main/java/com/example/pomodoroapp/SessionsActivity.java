package com.example.pomodoroapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.ArrayList;


public class SessionsActivity extends AppCompatActivity {
    DBHelper db;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {

            user_id = acct.getId();

        } else {
            user_id = "default";
        }

        ListView listView = findViewById(R.id.sessionListView);
        db = new DBHelper(this);

        ArrayList<SingleSession> sessionList = new ArrayList<>();
        SingleSession singleSession;

        Cursor sessionsData = db.getSessions(user_id);

        if (sessionsData.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "You don't have any stored sessions.", Toast.LENGTH_SHORT).show();
        } else {
            while (sessionsData.moveToNext()) {
                singleSession = new SingleSession(sessionsData.getString(2), sessionsData.getString(3), sessionsData.getString(4));
                sessionList.add(singleSession);
            }
            ThreeColumn_ListAdapter adapter = new ThreeColumn_ListAdapter(this, R.layout.list_adapter_view, sessionList);
            listView.setAdapter(adapter);
        }

    }
}
