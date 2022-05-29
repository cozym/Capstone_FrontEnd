package com.example.capstone_frontend.vo;

import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("seq")
    private int seq;

    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;

    @SerializedName("publisher")
    private String publisher;

    @SerializedName("isbn")
    private String isbn;

    @SerializedName("code")
    private String code;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("publishDate")
    private String publishDate;

    @SerializedName("description")
    private String description;

    @SerializedName("category")
    private String category;

    @SerializedName("rental")
    private boolean rental;

    public Book() {}

    public String getTitle(){return title;}

    public String getAuthor() {return author;}

    public String getPublisher() {return publisher;}

    public String getIsbn() {return isbn;}

    public String getThumbnail() {return thumbnail;}

    public String getPublishDate() {return publishDate;}

    public String getDescription() {return description;}

    public String getCategory() {return category;}

    public int getGroupSeq() {return seq;}

    public String getCode() {return code;}

    public boolean getRental() {return rental;}
}
