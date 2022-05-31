package com.example.practicespace.vo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Emails {

    @SerializedName("emails")
    private List<String> emails;

    public List<String> getemails() {
        return emails;
    }
}
