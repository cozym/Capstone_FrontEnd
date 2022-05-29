package com.example.capstone_frontend.vo;

import com.google.gson.annotations.SerializedName;

public class Admin {
    @SerializedName("seq")
    private int seq;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("role")
    private String role;

    public int getSeq(){ return seq;}
    public String getNickname() {return nickname;}
    public String getRole(){return role;}
}
