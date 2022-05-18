package com.example.studdybuddy.ui.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.studdybuddy.CustomAdapter.RecentMessageAdapter;
import com.example.studdybuddy.activities.DisplayUsersActivity;
import com.example.studdybuddy.databinding.ActivityChatBinding;
import com.example.studdybuddy.databinding.FragmentChatBinding;
import com.example.studdybuddy.entity.Message;
import com.example.studdybuddy.utilities.Constants;
import com.example.studdybuddy.utilities.SavePreferences;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private SavePreferences savePreferences;
    private List<Message> recentConversations;
    private RecentMessageAdapter recentMessageAdapter;
    private FirebaseFirestore database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChatViewModel chatViewModel =
                new ViewModelProvider(this).get(ChatViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        savePreferences = new SavePreferences(getContext());
        recentConversations = new ArrayList<>();
        recentMessageAdapter = new RecentMessageAdapter(recentConversations);
        database = FirebaseFirestore.getInstance();
        setNewMessageListener();
        binding.recentChatView.setAdapter(recentMessageAdapter);
        listenConversations();
        return root;
    }


    private void listenConversations(){
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_SENDER_ID, savePreferences.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CONVERSATIONS)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, savePreferences.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }



    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if(error != null) return;
        if(value != null){
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if (documentChange.getType() == DocumentChange.Type.ADDED){
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    Message message = new Message();
                    message.senderId = senderId;
                    message.receiverId = receiverId;
                    if (savePreferences.getString(Constants.KEY_USER_ID).equals(senderId)){
                        message.conversionName = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                        message.conversionId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    }
                    else{
                        message.conversionName = documentChange.getDocument().getString(Constants.KEY_SENDER_NAME);
                        message.conversionId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    }
                    message.message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                    message.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    recentConversations.add(message);
                }
                else if(documentChange.getType() == DocumentChange.Type.MODIFIED){
                    for(int i = 0; i < recentConversations.size(); i++){
                        String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                        if(recentConversations.get(i).senderId.equals(senderId) && recentConversations.get(i).receiverId.equals(receiverId)){
                            recentConversations.get(i).message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                            recentConversations.get(i).dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                            break;
                        }
                    }
                }
            }
            Collections.sort(recentConversations, (message1, message2) -> message1.dateObject.compareTo(message2.dateObject));
            recentMessageAdapter.notifyDataSetChanged();
            binding.recentChatView.smoothScrollToPosition(0);
            binding.recentChatView.setVisibility(View.VISIBLE);
            binding.prograssBarConversations.setVisibility(View.GONE);
        }
    };


    public void setNewMessageListener(){
        binding.sendNewMessage.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), DisplayUsersActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}