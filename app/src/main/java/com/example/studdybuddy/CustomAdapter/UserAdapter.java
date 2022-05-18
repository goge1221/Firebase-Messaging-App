package com.example.studdybuddy.CustomAdapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studdybuddy.activities.ChatActivity;
import com.example.studdybuddy.databinding.DisplayOneUserBinding;
import com.example.studdybuddy.entity.User;
import com.example.studdybuddy.utilities.Constants;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private final List<User> userList;
    public UserAdapter(List<User> users){
        userList = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DisplayOneUserBinding displayOneUserBinding = DisplayOneUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(displayOneUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        DisplayOneUserBinding binding;

        UserViewHolder(DisplayOneUserBinding displayOneUserBinding){
            super(displayOneUserBinding.getRoot());
            binding = displayOneUserBinding;
        }

        void setUserData(User user){
            binding.userName.setText(user.userName);
            binding.userEmail.setText(user.email);
            binding.getRoot().setOnClickListener(view -> {
               Intent intent= new Intent(binding.getRoot().getContext(), ChatActivity.class);
               intent.putExtra(Constants.KEY_USER, user);
               binding.getRoot().getContext().startActivity(intent);
            });
            //Set Immage muss noch her
        }

    }
}
