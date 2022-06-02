package com.example.practicespace.connection;

import com.example.practicespace.vo.Book;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class searchByIsbn extends setGroup{
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {
        @SerializedName("book")
        @Expose
        public Book book = new Book();
    }
}
