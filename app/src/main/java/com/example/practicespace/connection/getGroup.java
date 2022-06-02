package com.example.practicespace.connection;

import com.example.practicespace.vo.Group;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getGroup extends setGroup{
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {
        @SerializedName("group")
        @Expose
        public Group group = new Group();
    }
}
