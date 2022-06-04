package com.example.practicespace.connection;

import com.example.practicespace.vo.Group;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class setImage extends setGroup{
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {
        @SerializedName("")
        @Expose
        public String ImageURL;
    }
}
