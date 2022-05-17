package com.example.studdybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.studdybuddy.R;
import com.example.studdybuddy.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {

    private ActivityLogInBinding activityLogInBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogInBinding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(activityLogInBinding.getRoot());
        registerListener();
    }

    private void registerListener(){
        activityLogInBinding.createNewAccount.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });
    }

}