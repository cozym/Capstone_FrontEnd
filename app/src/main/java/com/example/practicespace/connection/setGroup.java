package com.example.practicespace.connection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class setGroup {
    @SerializedName("httpStatus")
    @Expose
    public String httpStatus;

    @SerializedName("message")
    @Expose
    public String message;
}

