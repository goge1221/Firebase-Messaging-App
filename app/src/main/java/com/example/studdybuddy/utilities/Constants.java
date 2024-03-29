package com.example.studdybuddy.utilities;

import android.net.Uri;

import com.example.studdybuddy.entity.User;

import java.util.HashSet;

public class Constants {
    public static final String KEY_COLLECTION_USERS = "users";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PREFERENCE_NAME = "chatAppPreference";
    public static final String KEY_IS_SIGNED_IN = "isSignedIn";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String KEY_USER = "user";
    public static final String KEY_COLLECTION_CHAT = "chat";
    public static final String KEY_SENDER_ID = "senderId";
    public static final String KEY_RECEIVER_ID = "receiverId";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_COLLECTION_CONVERSATIONS = "conversations";
    public static final String KEY_SENDER_NAME = "senderName";
    public static final String KEY_RECEIVER_NAME = "receiverName";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_IS_MENTOR = "mentor";
    public static HashSet<User>usersGlobal = new HashSet<>();


    public static void addUser(User user){
        for(User toCompare : usersGlobal){
            if(toCompare.email.equals(user.email))
                return;
        }
        usersGlobal.add(user);
    }



}
