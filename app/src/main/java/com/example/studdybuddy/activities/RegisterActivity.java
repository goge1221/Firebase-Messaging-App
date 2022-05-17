package com.example.studdybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.studdybuddy.R;
import com.example.studdybuddy.databinding.ActivityRegisterUserBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterUserBinding registerUserBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerUserBinding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(registerUserBinding.getRoot());
        getBackToLogInListener();
    }

    private void getBackToLogInListener(){
        registerUserBinding.goBackToLogIn.setOnClickListener(view -> onBackPressed());
    }
}