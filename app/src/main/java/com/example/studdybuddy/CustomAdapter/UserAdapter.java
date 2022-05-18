package com.example.studdybuddy.CustomAdapter;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studdybuddy.databinding.DisplayOneUserBinding;
import com.example.studdybuddy.CustomAdapter.UserAdapter;
import com.example.studdybuddy.entity.User;

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
       //     binding.getRoot().setOnClickListener(view -> getMeToUserChat());
            //Set Immage muss noch her
        }

        void getMeToUserChat(){

        }
    }
}
