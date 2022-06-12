package com.example.practicespace.vo;

import com.google.gson.annotations.SerializedName;

public class BookLog {
    @SerializedName("seq")
    private int seq;

    @SerializedName("bookLogStatus")
    private String bookLogStatus;

    @SerializedName("createdTime")
    private String createdTime;

    @SerializedName("lastModifiedTime")
    private String lastModifiedTime;

    @SerializedName("user")
    private User user;

    @SerializedName("book")
    private Book book;

    @SerializedName("group")
    private Group group;

    public int getSeq(){return seq;}
    public String getBookLogStatus(){return bookLogStatus;}
    public String getCreatedTime(){return createdTime;}
    public String getLastModifiedTime(){return lastModifiedTime;}
    public User getUser(){return user;}
    public Book getBook(){return book;}
    public Group getGroup(){return group;}

}
