package com.example.practicespace.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Nickname {

    @SerializedName("nickname")
    private String nickname;

    public String getNickname() {
        return nickname;
    }
}
