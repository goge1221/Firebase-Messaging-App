package com.example.studdybuddy.CustomAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studdybuddy.databinding.MessageReceivedLayoutBinding;
import com.example.studdybuddy.databinding.MessageSentLayoutBinding;
import com.example.studdybuddy.entity.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<Message> chatMessages;
    private final String senderId;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public MessageAdapter(List<Message> chatMessages, String senderId) {
        this.chatMessages = chatMessages;
        this.senderId = senderId;
    }

    @Override
    public int getItemViewType(int position) {
        if(chatMessages.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT;
        } else return VIEW_TYPE_RECEIVED;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT){
            return new SentMessageView(
                    MessageSentLayoutBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }
        else return new ReceiveMessageView(
                MessageReceivedLayoutBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_SENT){
            ((SentMessageView) holder).setData(chatMessages.get(position));
        }
        else ((ReceiveMessageView) holder).setData(chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    static class SentMessageView extends RecyclerView.ViewHolder{

        private final MessageSentLayoutBinding binding;

        public SentMessageView(@NonNull MessageSentLayoutBinding messageSentLayoutBinding) {
            super(messageSentLayoutBinding.getRoot());
            binding = messageSentLayoutBinding;
        }

        void setData(Message message){
            binding.myMessage.setText(message.message.trim());
            binding.textDateTime.setText(message.date.trim());
        }
    }

    static class ReceiveMessageView extends  RecyclerView.ViewHolder{

        private final MessageReceivedLayoutBinding binding;

        public ReceiveMessageView(@NonNull MessageReceivedLayoutBinding messageReceivedLayoutBinding){
            super(messageReceivedLayoutBinding.getRoot());
            binding = messageReceivedLayoutBinding;
        }

        void setData(Message mesasge){
            binding.receivedMessage.setText(mesasge.message.trim());
            binding.textDateTime.setText(mesasge.date.trim());
        }

    }


}
