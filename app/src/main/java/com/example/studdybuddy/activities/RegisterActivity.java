package com.example.studdybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.studdybuddy.databinding.ActivityRegisterUserBinding;
import com.example.studdybuddy.utilities.Constants;
import com.example.studdybuddy.utilities.SavePreferences;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterUserBinding registerUserBinding;
    private SavePreferences savePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerUserBinding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(registerUserBinding.getRoot());
        savePreferences = new SavePreferences(getApplicationContext());
        if(savePreferences.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            intent.putExtra("USER_NAME", savePreferences.getString(Constants.KEY_NAME));
            finish();
        }
        getBackToLogInListener();
        getUserData();
    }

    private void getBackToLogInListener(){
        registerUserBinding.goBackToLogIn.setOnClickListener(view -> onBackPressed());
    }

    private void getUserData(){
        registerUserBinding.registerUserButton.setOnClickListener(view -> {
            if(checkSignInData()){
                createAccount();
            }
        });
    }

    private void createAccount(){
        displayProcessIcon(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME, registerUserBinding.registerUserName.getText().toString());
        user.put(Constants.KEY_EMAIL, registerUserBinding.registerUserEmail.getText().toString());
        user.put(Constants.KEY_PASSWORD, registerUserBinding.registerUserPassword.getText().toString());
        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    displayProcessIcon(false);
                    savePreferences.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                    savePreferences.putBoolean(Constants.KEY_IS_MENTOR, false);
                    savePreferences.putString(Constants.KEY_USER_ID, documentReference.getId());
                    savePreferences.putString(Constants.KEY_NAME, registerUserBinding.registerUserName.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    displayProcessIcon(false);
                    Log.e("Error during registration: ", e.getMessage());
                });
    }

    private void displayProcessIcon(boolean isProcessing){
        if(isProcessing){
            registerUserBinding.registerUserButton.setVisibility(View.INVISIBLE);
            registerUserBinding.progressBar.setVisibility(View.VISIBLE);
        }
        else {
            registerUserBinding.progressBar.setVisibility(View.INVISIBLE);
            registerUserBinding.registerUserButton.setVisibility(View.VISIBLE);
        }
    }


    private boolean checkSignInData(){
        if(registerUserBinding.registerUserName.getText().toString().isEmpty()){
            Toast.makeText(this, "Empty user name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(registerUserBinding.registerUserEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Wrong E-Mail or no E-Mail adress.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(registerUserBinding.registerUserEmail.getText().toString()).matches()){
            Toast.makeText(this, "Enter a valid E-Mail adress.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(registerUserBinding.confirmUserPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter your password again.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(registerUserBinding.registerUserPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter a password.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!registerUserBinding.registerUserPassword.getText().toString().equals(
                registerUserBinding.confirmUserPassword.getText().toString())){
            Toast.makeText(this, "The two passwords don't match.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }

}