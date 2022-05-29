package com.example.capstone_frontend.book_controller;

import com.example.capstone_frontend.vo.Book;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class bookList{
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
        @SerializedName("book")
        @Expose
        public List<Book> books = new ArrayList<Book>();
    }
}
