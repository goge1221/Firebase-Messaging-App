package com.example.studdybuddy.firebase;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.studdybuddy.CustomAdapter.UserAdapter;
import com.example.studdybuddy.entity.User;
import com.example.studdybuddy.utilities.Constants;
import com.google.firebase.FirebaseError;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MessagingService extends FirebaseMessagingService {

    private FirebaseFirestore database;
    private HashSet<User>users;

    public MessagingService(){
        database = FirebaseFirestore.getInstance();
    }



    @Override
    public void onNewToken(@NonNull String token){
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
    }

    public void getUsers(){
      //  Constants.usersGlobal.clear();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null){
                        for(QueryDocumentSnapshot snapshot : task.getResult()){
                            User user = new User();
                            user.userName = snapshot.getString(Constants.KEY_NAME);
                            user.email = snapshot.getString(Constants.KEY_EMAIL);
                            user.mentor = snapshot.getBoolean(Constants.KEY_IS_MENTOR);
                            Constants.addUser(user);
                        }
                        if(Constants.usersGlobal.size() < 1){
                            Toast.makeText(this, "No users registered yet.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }); Log.i("---", String.valueOf(Constants.usersGlobal.size()));

    }

}
