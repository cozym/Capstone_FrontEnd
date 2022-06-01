package com.example.practicespace.connection;

import com.example.practicespace.vo.Emails;
import com.example.practicespace.vo.Group;
import com.example.practicespace.vo.Nickname;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getUser {
    @SerializedName("httpStatus")
    @Expose
    public String httpStatus;

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {
        @SerializedName("emails")
        @Expose
        public Emails emails = new Emails();

        @SerializedName("nickname")
        @Expose
        public Nickname nickname = new Nickname();
    }
}
