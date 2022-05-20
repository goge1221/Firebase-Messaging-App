package com.example.studdybuddy.CustomAdapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studdybuddy.activities.ChatActivity;
import com.example.studdybuddy.databinding.DisplayUserRecentConversationBinding;
import com.example.studdybuddy.entity.Message;
import com.example.studdybuddy.entity.User;
import com.example.studdybuddy.firebase.MessagingService;
import com.example.studdybuddy.utilities.Constants;
import com.example.studdybuddy.utilities.SavePreferences;

import java.util.List;

public class RecentMessageAdapter extends RecyclerView.Adapter<RecentMessageAdapter.ConversionViewHolder> {

    private final List<Message> chatMessages;


    public RecentMessageAdapter(List<Message> messages){
        chatMessages = messages;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(
                DisplayUserRecentConversationBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }


    class ConversionViewHolder extends RecyclerView.ViewHolder {
        DisplayUserRecentConversationBinding binding;

        ConversionViewHolder(DisplayUserRecentConversationBinding displayUserRecentConversationBinding) {
            super(displayUserRecentConversationBinding.getRoot());
            binding = displayUserRecentConversationBinding;
        }

        void setData(Message chatMessage) {

            binding.userNameRecent.setText(chatMessage.conversionName);
            binding.recentMessage.setText(chatMessage.message);

            for(User user: Constants.usersGlobal){
                if(user.userName.equals(chatMessage.conversionName) && user.mentor)
                    binding.userImage.setVisibility(View.INVISIBLE);
            }

            if(chatMessage.message.isEmpty()) binding.recentMessage.setText("An image was sent.");
            binding.getRoot().setOnClickListener(view -> {
                User user = new User();
                user.userName = chatMessage.conversionName;
                user.id = chatMessage.conversionId;
                Intent intent = new Intent(binding.getRoot().getContext(), ChatActivity.class);
                intent.putExtra(Constants.KEY_USER, user);
                binding.getRoot().getContext().startActivity(intent);
            });
        }
    }
}
