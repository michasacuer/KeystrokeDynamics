package com.example.admin.keystroke_dynamics.Activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.admin.keystroke_dynamics.DTO.ApplicationDatabase;

public class MainActivity extends AppCompatActivity {

    Intent activityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main_activity);
        
        activityIntent = new Intent(this, LoginActivity.class);
        startActivity(activityIntent);
        finish();
    }


}