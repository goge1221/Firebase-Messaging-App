package com.example.studdybuddy.CustomAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studdybuddy.databinding.DisplayUserRecentConversationBinding;
import com.example.studdybuddy.entity.Message;

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
            binding.recentMessage.setText(chatMessage.message);
            binding.userNameRecent.setText(chatMessage.conversionName);
        }
    }
}
