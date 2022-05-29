package com.example.capstone_frontend.group_controller;

import com.example.capstone_frontend.vo.Group;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class resignGroup {
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
        @SerializedName("group")
        @Expose
        public Group group = new Group();
    }
}
