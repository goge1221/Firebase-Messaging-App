package com.example.studdybuddy.entity;

import java.io.Serializable;

public class User implements Serializable {
    public String userName;
    public String email;
    public String token;
    public boolean mentor = false;
    public String id;


    @Override
    public String toString(){
        String toReturn = "Email," + email;
        return toReturn;
    }
}
