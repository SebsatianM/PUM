package com.example.pomodoroapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;

import java.util.Date;


public class AppActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    String userName = "You are not logged in", userEmail = "You are not logged in", userID = "default", timeString;
    TextView userNameTextView, userEmailTextView,stateInfo;
    Integer sessionTime = 25, breakTime = 5, pStatus = 0, sessionTotalSeconds=0, breakTotalSeconds=0, minutes=0, seconds=0, elapsedSeconds = 0, remainingSeconds=0;
    double sessionPercentage;
    AlertDialog.Builder builder;
    private DrawerLayout drawer;
    DBHelper db;
    private TextView progressBarText;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private long sessionTimeLeft=0, breakTimeLeft=0;
    private Button startSessionButton, resetSessionButton;
    private boolean sessionTimerRunning=false, breakTimerRunning=false, sessionRunning = true, breakRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            userName = acct.getDisplayName();
            userEmail = acct.getEmail();
            userID = acct.getId();
            setUpNavigationBar(userName, userEmail);

        } else {
            setUpNavigationBar(userName, userEmail);
        }
        db = new DBHelper(this);
        db.insertSettings(userID, sessionTime, breakTime);
//        initDatabase();
        setUpToolbar();
        initTimer();
        getSessionSettings();

        if(savedInstanceState != null) {
            sessionTime = savedInstanceState.getInt("sessionTime");
            breakTime = savedInstanceState.getInt("breakTime");
            pStatus = savedInstanceState.getInt("pStatus");
            sessionTotalSeconds = savedInstanceState.getInt("sessionTotalSeconds");
            breakTotalSeconds = savedInstanceState.getInt("breakTotalSeconds");
            minutes = savedInstanceState.getInt("minutes");
            seconds = savedInstanceState.getInt("seconds");
            elapsedSeconds = savedInstanceState.getInt("elapsedSeconds");
            remainingSeconds = savedInstanceState.getInt("remainingSeconds");
            progressBar.setProgress(savedInstanceState.getInt("progressBarVal"));

            sessionTimeLeft = savedInstanceState.getLong("sessionTimeLeft");
            breakTimeLeft = savedInstanceState.getLong("breakTimeLeft");

            stateInfo.setText(savedInstanceState.getString("stateInfoText"));
            startSessionButton.setText(savedInstanceState.getString("startSessionButtonText"));
            resetSessionButton.setText(savedInstanceState.getString("resetSessionButtonText"));
            progressBarText.setText(savedInstanceState.getString("progressBarTextVal"));

            sessionTimerRunning = savedInstanceState.getBoolean("sessionTimerRunning");
            breakTimerRunning = savedInstanceState.getBoolean("breakTimerRunning");
            sessionRunning = savedInstanceState.getBoolean("sessionRunning");
            breakRunning = savedInstanceState.getBoolean("breakRunning");

            if(sessionRunning){
                startSessionTimer();
            }else {
                startBreakTimer();
            }
        }

    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private NavigationView getNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        return navigationView;
    }

    void setUpNavigationBar(String userName, String userEmail) {
        View headerView = getNavigationView().getHeaderView(0);
        userNameTextView = headerView.findViewById(R.id.userName);
        userEmailTextView = headerView.findViewById(R.id.userEmail);
        userNameTextView.setText(userName);
        userEmailTextView.setText(userEmail);

    }

    void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = getNavigationView();
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_bar_open, R.string.nav_bar_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    void signOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(AppActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_sessions:
                intent = new Intent(AppActivity.this, SessionsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                intent = new Intent(AppActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_info:
                builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.pomodoro_description)
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(), "Now take advantage of it.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                alert.setTitle(R.string.pomodoro_information);
                alert.show();
                break;
            case R.id.nav_logout:
                signOut();
                break;
        }
        return true;
    }

    public void showSettings(View view) {
        insertSession();

    }

    private void initDatabase() {


    }

    public void getSessionSettings() {
        Cursor resultSettings = db.getSettings(userID);
        if (resultSettings.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No settings avilable", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();

        while (resultSettings.moveToNext()) {
            buffer.append(resultSettings.getString(0) + " ");
            buffer.append(resultSettings.getString(1));
        }
        sessionTime = Integer.parseInt(buffer.substring(0, buffer.indexOf(" ")));
        breakTime = Integer.parseInt(buffer.substring(buffer.indexOf(" ") + 1, buffer.length()));

        sessionTotalSeconds = sessionTime * 60;
        breakTotalSeconds = breakTime * 60;
        sessionTimeLeft = sessionTime * 60000;
        breakTimeLeft = breakTime * 60000;

    }

    public void initTimer() {
        startSessionButton = findViewById(R.id.startSessionButton);
        resetSessionButton = findViewById(R.id.resetSessionButton);
        stateInfo = findViewById(R.id.stateInfo);
        progressBarText = findViewById(R.id.txtProgress);
        progressBar = findViewById(R.id.progressBar);
        startSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopSession();
            }
        });
        resetSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSession();
            }
        });
    }

    public void startStopSession() {
        if (sessionRunning == true && breakRunning == false) {
            stateInfo.setText("You are during session");
            if (sessionTimerRunning) {
                stopSessionTimer();
                Toast.makeText(getApplicationContext(), "Session", Toast.LENGTH_SHORT).show();
            } else {
                startSessionTimer();
                Toast.makeText(getApplicationContext(), "session", Toast.LENGTH_SHORT).show();
            }
        } else{
            stateInfo.setText("You are during break");
            if (breakTimerRunning) {
                stopBreakTimer();
                Toast.makeText(getApplicationContext(), "Break", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Break", Toast.LENGTH_SHORT).show();
                startBreakTimer();
            }
        }


    }

    public void startSessionTimer() {
        countDownTimer = new CountDownTimer(sessionTimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sessionTimeLeft = millisUntilFinished;
                updateTimer(sessionTimeLeft);
            }

            @Override
            public void onFinish() {
                sessionRunning = false;
                breakRunning = true;
                progressBar.setProgress(0);
                Toast.makeText(getApplicationContext(), "Session ended now rest", Toast.LENGTH_SHORT).show();
                startStopSession();
            }
        }.start();
        startSessionButton.setText("PAUSE");
        sessionTimerRunning = true;
    }

    public void stopSessionTimer() {
        countDownTimer.cancel();
        startSessionButton.setText("RESUME");
        sessionTimerRunning = false;
    }

    public void updateTimer(long timeLeft) {
        remainingSeconds = (int) timeLeft / 1000;
        seconds = remainingSeconds % 60;
        minutes = (remainingSeconds / 60) % 60;
        if (sessionRunning){
            elapsedSeconds = sessionTotalSeconds - remainingSeconds;
            sessionPercentage = (100 * elapsedSeconds / sessionTotalSeconds);
        }else if(breakRunning){
            elapsedSeconds = breakTotalSeconds - remainingSeconds;
            sessionPercentage = (100 * elapsedSeconds / breakTotalSeconds);
        }

        pStatus = (int) sessionPercentage;
        timeString = String.format("%02d:%02d", minutes, seconds);
        progressBarText.setText(timeString);
        progressBar.setProgress(pStatus);
    }

    public void resetSession() {
        countDownTimer.cancel();
        if (sessionRunning && !breakRunning) {
            sessionTimerRunning = false;
            sessionTimeLeft = sessionTime * 60000;
            sessionTotalSeconds = sessionTime * 60;
        }else if(!sessionRunning  && breakRunning) {
            breakTimerRunning = false;
            breakTimeLeft = breakTime * 60000;
            breakTotalSeconds = breakTime *60;
        }
        sessionTimerRunning = false;
        startSessionButton.setText("START");
        progressBarText.setText("");
        progressBar.setProgress(0);

    }

    public void startBreakTimer() {
        countDownTimer = new CountDownTimer(breakTimeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                breakTimeLeft = millisUntilFinished;
                updateTimer(breakTimeLeft);
            }

            @Override
            public void onFinish() {
                sessionRunning = true;
                breakRunning = false;
                progressBar.setProgress(0);
                startSessionButton.setText("START");
                stateInfo.setText("Start session");
                Toast.makeText(getApplicationContext(), "Break ended now You can start new session", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Session has been saved", Toast.LENGTH_SHORT).show();
                sessionTimerRunning = false;
                sessionTimeLeft = sessionTime * 60000;
                sessionTotalSeconds = sessionTime * 60;
            }
        }.start();
        startSessionButton.setText("PAUSE");
        breakTimerRunning = true;
    }

    public void stopBreakTimer() {
        countDownTimer.cancel();
        startSessionButton.setText("RESUME");
        breakTimerRunning = false;
    }
    public void insertSession(){
        String date = getCurrentTimeStamp();

        db.insertSession(userID, date ,Integer.toString(sessionTime), Integer.toString(breakTime));
    }

    public static String getCurrentTimeStamp(){
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date());
            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("sessionTime",sessionTime);
        savedInstanceState.putInt("breakTime",breakTime);
        savedInstanceState.putInt("pStatus",pStatus);
        savedInstanceState.putInt("sessionTotalSeconds",sessionTotalSeconds);
        savedInstanceState.putInt("breakTotalSeconds",breakTotalSeconds);
        savedInstanceState.putInt("minutes",minutes);
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putInt("elapsedSeconds",elapsedSeconds);
        savedInstanceState.putInt("remainingSeconds",remainingSeconds);
        savedInstanceState.putInt("progressBarVal",progressBar.getProgress());

        savedInstanceState.putLong("sessionTimeLeft",sessionTimeLeft);
        savedInstanceState.putLong("breakTimeLeft",breakTimeLeft);

        savedInstanceState.putString("stateInfoText",String.valueOf(stateInfo.getText()));
        savedInstanceState.putString("startSessionButtonText", String.valueOf(startSessionButton.getText()));
        savedInstanceState.putString("resetSessionButtonText", String.valueOf(resetSessionButton.getText()));
        savedInstanceState.putString("progressBarTextVal", String.valueOf(progressBarText.getText()));

        savedInstanceState.putBoolean("sessionTimerRunning",sessionTimerRunning);
        savedInstanceState.putBoolean("breakTimerRunning",breakTimerRunning);
        savedInstanceState.putBoolean("sessionRunning",sessionRunning);
        savedInstanceState.putBoolean("breakRunning",breakRunning);
    }
}

