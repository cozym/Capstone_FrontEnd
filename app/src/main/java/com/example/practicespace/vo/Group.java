package com.example.practicespace.vo;

import com.google.gson.annotations.SerializedName;

public class Group {

    @SerializedName("seq")
    private int seq;

    @SerializedName("name")
    private String name;

    //@SerializedName("thumbnail")
    //private String thumbnail;

    @SerializedName("authenticationCode")
    private String authenticationCode;

    @SerializedName("description")
    private String description;

    @SerializedName("admin")
    private Admin admin;

    @SerializedName("open")
    private boolean open;

    @SerializedName("createdDate")
    private String createdDate;

    public Group() {}

    public int getSeq() {return seq;}
    public String getName(){return name;}

    //public String getThumbnail() {return thumbnail;}

    public String getAuthenticationCode(){return authenticationCode;}

    public Admin getAdmin() {return admin;}

    public boolean getOpen() {return open;}

    public String getDescription(){return description;}

    public String getCreatedDate(){return createdDate;}
}
