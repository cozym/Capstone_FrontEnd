package com.example.practicespace.connection;

import com.example.practicespace.vo.Group;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class resignGroup {
    @SerializedName("httpStatus")
    @Expose
    public String httpStatus;

    @SerializedName("message")
    @Expose
    public String message;

}
