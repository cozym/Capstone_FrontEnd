package com.example.practicespace.connection;

import com.example.practicespace.vo.BookLog;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class getMyBookLogList extends setGroup{
    @SerializedName("data")
    @Expose
    public Data data;
    public class Data{
        @SerializedName("bookLogList")
        @Expose
        public List<BookLog> bookLogList = new ArrayList<BookLog>();
    }
}
