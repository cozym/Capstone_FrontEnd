package com.example.practicespace.connection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getUser extends setGroup{

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {
        @SerializedName("emails")
        @Expose
        public List<String> emails;

        @SerializedName("nickname")
        @Expose
        public String nickname;
    }
}
