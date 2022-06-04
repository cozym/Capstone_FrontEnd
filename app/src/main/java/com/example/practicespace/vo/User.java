package com.example.practicespace.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("emails")
    private List<String> emails;

    @SerializedName("nickname")
    private String nickname;


    public List<String> getEmails(){return emails;}
    public String getNickname() {return nickname;}

}

