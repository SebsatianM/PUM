package com.example.pomodoroapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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


public class AppActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    String userName = "You are not logged in", userEmail = "You are not logged in";
    TextView userNameTextView, userEmailTextView;
    Button signOutButton;
    AlertDialog.Builder builder;
    private DrawerLayout drawer;

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
            setUpNavigationBar(userName, userEmail);

        } else {
            setUpNavigationBar(userName, userEmail);
        }

        setUpToolbar();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    NavigationView getNavigationView(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        return navigationView;
    }

    void setUpNavigationBar(String userName, String userEmail) {
        View headreView = getNavigationView().getHeaderView(0);;
        userNameTextView = headreView.findViewById(R.id.userName);
        userEmailTextView = headreView.findViewById(R.id.userEmail);
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
        switch (item.getItemId()){
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
                                Toast.makeText(getApplicationContext(),"Now take advantage of it.",
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
}
