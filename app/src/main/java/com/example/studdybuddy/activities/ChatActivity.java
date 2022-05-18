package com.example.studdybuddy.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.studdybuddy.CustomAdapter.MessageAdapter;
import com.example.studdybuddy.R;
import com.example.studdybuddy.databinding.ActivityChatBinding;
import com.example.studdybuddy.entity.Message;
import com.example.studdybuddy.entity.User;
import com.example.studdybuddy.utilities.Constants;
import com.example.studdybuddy.utilities.SavePreferences;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class ChatActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    private User user;
    private ActivityChatBinding binding;
    private List<Message> chatMessages;
    private MessageAdapter messageAdapter;
    private SavePreferences savePreferences;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = (User) getIntent().getSerializableExtra(Constants.KEY_USER);
        actionBarHandler();
        savePreferences = new SavePreferences(getApplicationContext());
        chatMessages = new ArrayList<>();
        messageAdapter = new MessageAdapter(chatMessages, savePreferences.getString(Constants.KEY_USER_ID));
        binding.displayChatMessages.setAdapter(messageAdapter);
        database = FirebaseFirestore.getInstance();
        binding.sendLayout.setOnClickListener(view -> sendMessage());
        listenMessages();
    }


    private void listenMessages(){
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, savePreferences.getString(Constants.KEY_USER_ID))
                .whereEqualTo(Constants.KEY_RECEIVER_ID, user.id)
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, user.id)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, savePreferences.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }


    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if(error != null){
            return;
        }
        if(value != null){
            int count = chatMessages.size();
            for(DocumentChange documentChange : value.getDocumentChanges()){
                if(documentChange.getType() == DocumentChange.Type.ADDED) {
                    Message message = new Message();
                    message.senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    message.receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    message.message = documentChange.getDocument().getString(Constants.KEY_MESSAGE);
                    message.date = getDate(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    message.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    chatMessages.add(message);
                }
            }
            Collections.sort(chatMessages, (message1, message2) -> message1.dateObject.compareTo(message2.dateObject));
            if(count == 0){
                messageAdapter.notifyDataSetChanged();
            }
            else{
                messageAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                binding.displayChatMessages.smoothScrollToPosition(chatMessages.size()-1);
            }
            binding.displayChatMessages.setVisibility(View.VISIBLE);
        }
        binding.progressBar.setVisibility(View.GONE);
    };


    private void sendMessage(){
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, savePreferences.getString(Constants.KEY_USER_ID));
        message.put(Constants.KEY_RECEIVER_ID, user.id);
        message.put(Constants.KEY_MESSAGE, binding.message.getText().toString());
        message.put(Constants.KEY_TIMESTAMP, new Date());
        database.collection(Constants.KEY_COLLECTION_CHAT).add(message);
        binding.message.setText(null);
    }

    private String getDate(Date date){
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }

    private void actionBarHandler(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(user.userName);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}