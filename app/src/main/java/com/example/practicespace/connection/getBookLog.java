package com.example.practicespace.connection;

import com.example.practicespace.vo.Book;
import com.example.practicespace.vo.BookLog;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class getBookLog extends setGroup{
    @SerializedName("data")
    @Expose
    public Data data;
    public class Data{
        @SerializedName("book")
        @Expose
        public BookLog booklog = new BookLog();
    }
}
