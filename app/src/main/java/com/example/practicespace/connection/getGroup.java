package com.example.practicespace.connection;

import com.example.practicespace.vo.Group;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getGroup {
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
