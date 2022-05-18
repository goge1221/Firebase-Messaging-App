package com.example.studdybuddy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.studdybuddy.CustomAdapter.UserAdapter;
import com.example.studdybuddy.R;
import com.example.studdybuddy.databinding.ActivityDisplayUsersBinding;
import com.example.studdybuddy.entity.User;
import com.example.studdybuddy.utilities.Constants;
import com.example.studdybuddy.utilities.SavePreferences;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DisplayUsersActivity extends AppCompatActivity {

    private ActivityDisplayUsersBinding binding;
    private SavePreferences savedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        savedPreferences = new SavePreferences(getApplicationContext());
        fetchUsers();
        getSupportActionBar().setTitle("Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void fetchUsers(){
        showLoadingIcon(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    showLoadingIcon(false);
                    String myUserId = savedPreferences.getString(Constants.KEY_USER_ID);
                    if(task.isSuccessful() && task.getResult() != null){
                        List<User> users = new ArrayList<>();
                        for(QueryDocumentSnapshot snapshot : task.getResult()){
                            if(myUserId.equals(snapshot.getId()))
                                continue;
                            User user = new User();
                            user.userName = snapshot.getString(Constants.KEY_NAME);
                            user.email = snapshot.getString(Constants.KEY_EMAIL);
                            user.token = snapshot.getString(Constants.KEY_FCM_TOKEN);
                            user.mentor = false;
                            users.add(user);
                        }
                        if(users.size() > 0){
                            UserAdapter userAdapter = new UserAdapter(users);
                            binding.recyclerViewDisplayUsers.setAdapter(userAdapter);
                            binding.recyclerViewDisplayUsers.setVisibility(View.VISIBLE);
                        } else Toast.makeText(this, "No users registered yet.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showLoadingIcon(boolean loading){
        if(loading)
            binding.progressBarDisplayUsers.setVisibility(View.VISIBLE);
        else binding.progressBarDisplayUsers.setVisibility(View.INVISIBLE);
    }

}