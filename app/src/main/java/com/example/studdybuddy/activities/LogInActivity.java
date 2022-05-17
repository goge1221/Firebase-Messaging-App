package com.example.studdybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.savedstate.SavedStateRegistryOwner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.studdybuddy.R;
import com.example.studdybuddy.databinding.ActivityLogInBinding;
import com.example.studdybuddy.utilities.Constants;
import com.example.studdybuddy.utilities.SavePreferences;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LogInActivity extends AppCompatActivity {

    private ActivityLogInBinding activityLogInBinding;
    private SavePreferences savePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogInBinding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(activityLogInBinding.getRoot());
        savePreferences = new SavePreferences(getApplicationContext());
        if(savePreferences.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("USER_NAME", savePreferences.getString(Constants.KEY_NAME));
            startActivity(intent);
            finish();
        }
        registerListener();
        logIn();
    }

    private void registerListener(){
        activityLogInBinding.createNewAccount.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void logIn(){
        activityLogInBinding.signInButton.setOnClickListener(view -> {
            if(isLogInInputCorrect()){
                displayLoadingIcon(true);
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                database.collection(Constants.KEY_COLLECTION_USERS)
                        .whereEqualTo(Constants.KEY_EMAIL, activityLogInBinding.userEmail.getText().toString())
                        .whereEqualTo(Constants.KEY_PASSWORD, activityLogInBinding.userPassword.getText().toString())
                        .get()
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful() && task.getResult() != null && task.getResult()
                                    .getDocuments().size() > 0){
                                DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                savePreferences.putBoolean(Constants.KEY_IS_SIGNED_IN, true);
                                savePreferences.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                                savePreferences.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else{
                                displayLoadingIcon(false);
                                Toast.makeText(this, "Error while logging in.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void displayLoadingIcon(boolean isProcessing){
        if(isProcessing){
            activityLogInBinding.signInButton.setVisibility(View.INVISIBLE);
            activityLogInBinding.progressBar.setVisibility(View.VISIBLE);
        }
        else {
            activityLogInBinding.progressBar.setVisibility(View.INVISIBLE);
            activityLogInBinding.signInButton.setVisibility(View.VISIBLE);
        }
    }

    private boolean isLogInInputCorrect(){
        if(activityLogInBinding.userEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter your E-Mail adress.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(activityLogInBinding.userEmail.getText().toString()).matches()){
            Toast.makeText(this, "Invalid E-Mail adress.", Toast.LENGTH_SHORT).show();
        }
        else if(activityLogInBinding.userPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();
            return false;
        } return true;
    }


}