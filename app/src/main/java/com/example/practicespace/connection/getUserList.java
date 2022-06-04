package com.example.practicespace.connection;

import com.example.practicespace.vo.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class getUserList extends setGroup{

    @SerializedName("data")
    @Expose
    public getUser.Data data;

    public class Data {
        @SerializedName("userList")
        @Expose
        public List<User> userList = new ArrayList<User>();
    }
}
