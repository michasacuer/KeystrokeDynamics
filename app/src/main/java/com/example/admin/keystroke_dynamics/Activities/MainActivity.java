package com.example.admin.keystroke_dynamics.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.keystroke_dynamics.Login.LoggedUser;
import com.example.admin.keystroke_dynamics.R;
import com.example.admin.keystroke_dynamics.Utils.PreferenceEditor;
import com.github.clans.fab.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loggedUserSharedPreferenceEditor = new PreferenceEditor(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View header=navigationView.getHeaderView(0);
        emailText = header.findViewById(R.id.nav_text_email);
        usernameText = header.findViewById(R.id.nav_text_username);

        if(loggedUserSharedPreferenceEditor.isEmpty())
            startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_CODE);

        else{
            isLogged = true;
            loggedUser = loggedUser.getInstance();
            loggedUserSharedPreferenceEditor.load(loggedUser);
            emailText.setText(loggedUser.getEmail());
            usernameText.setText(loggedUser.getUsername());
        }

        addMeasureActivity = new Intent(this, AddMeasureActivity.class);

        addMeasureButton = findViewById(R.id.floating_button_addMeasure);
        addMeasureButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(addMeasureActivity);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE){
            if(resultCode != Activity.RESULT_OK) {
                finish();
            }
            isLogged = true;
            loggedUser = loggedUser.getInstance();
            loggedUserSharedPreferenceEditor.save(loggedUser);
            emailText.setText(loggedUser.getEmail());
            usernameText.setText(loggedUser.getUsername());

        }
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer((GravityCompat.START));
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.nav_logout:
                isLogged = false;
                loggedUser.resetInstance();
                startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_CODE);
                loggedUserSharedPreferenceEditor.clear();
                Toast.makeText(getApplicationContext(), getString(R.string.logout), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    static final private int REQUEST_CODE = 0;
    private FloatingActionButton addMeasureButton;
    private Intent addMeasureActivity;
    private DrawerLayout drawerLayout;
    private boolean isLogged = false;
    private LoggedUser loggedUser;
    private TextView emailText;
    private TextView usernameText;
    private PreferenceEditor loggedUserSharedPreferenceEditor;
}
