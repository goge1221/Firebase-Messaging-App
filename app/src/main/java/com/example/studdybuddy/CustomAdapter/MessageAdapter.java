package com.example.studdybuddy.CustomAdapter;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studdybuddy.R;
import com.example.studdybuddy.databinding.MessageReceivedLayoutBinding;
import com.example.studdybuddy.databinding.MessageSentLayoutBinding;
import com.example.studdybuddy.entity.Message;
import com.example.studdybuddy.utilities.Constants;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<Message> chatMessages;
    private final String senderId;
    private boolean needToSendPhoto = false;
    private Uri photoId;

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
            try {
                ((SentMessageView) holder).setData(chatMessages.get(position));
            } catch (IOException e) {
                e.printStackTrace();
            }
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

        public void sendImage() throws IOException {
            binding.messageSendImage.setVisibility(View.VISIBLE);
            binding.myMessage.setVisibility(View.INVISIBLE);
//            binding.messageSendImage.setImageURI(Constants.FOTO_URI);
        }
        //Wird jedes mal aufgerunfen checken if text show image ist
        void setData(Message message) throws IOException {
            if(!message.message.trim().equals("SHOW_IMAGE")) {
                binding.myMessage.setText(message.message.trim());
                binding.textDateTime.setText(message.date.trim());
            }
            else{
                binding.myMessage.setVisibility(View.GONE);
                binding.textDateTime.setVisibility(View.GONE);
                binding.messageSendImage.setVisibility(View.VISIBLE);
                binding.textDateTimeForImage.setVisibility(View.VISIBLE);
                binding.textDateTimeForImage.setText(message.date.trim());
            }

        }
    }

    static class ReceiveMessageView extends  RecyclerView.ViewHolder{

        private final MessageReceivedLayoutBinding binding;

        public ReceiveMessageView(@NonNull MessageReceivedLayoutBinding messageReceivedLayoutBinding){
            super(messageReceivedLayoutBinding.getRoot());
            binding = messageReceivedLayoutBinding;
        }

        void setData(Message mesasge){

            if(mesasge.message.equals("SHOW_IMAGE")) {
                binding.receivedMessage.setVisibility(View.GONE);
                binding.textDateTime.setVisibility(View.GONE);
                binding.messageReceivedImage.setVisibility(View.VISIBLE);
                binding.textDateTimeForImage.setVisibility(View.VISIBLE);
                binding.textDateTimeForImage.setText(mesasge.date.trim());
            }
            else{
                binding.textDateTime.setText(mesasge.date.trim());
                binding.receivedMessage.setText(mesasge.message);
            }
        }

    }


}
