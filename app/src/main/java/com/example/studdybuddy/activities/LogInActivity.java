package com.example.studdybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.studdybuddy.R;
import com.example.studdybuddy.databinding.ActivityLogInBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

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