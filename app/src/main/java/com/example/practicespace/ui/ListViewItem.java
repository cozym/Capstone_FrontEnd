package com.example.practicespace.ui;

public class ListViewItem {

    private int group_icon;
    private String group_name;
    private String hash_tag;
    private int groupSeq;

    public ListViewItem(int icon, String name, String hashtag, int seq){
        this.group_icon = icon;
        this.group_name = name;
        this.hash_tag = hashtag;
        this.groupSeq = seq;

    }




    public int getIcon(){ return this.group_icon; }
    public String getGroupName(){ return this.group_name; }
    public String getHashTag(){ return this.hash_tag; }
    public int getGroupSeq(){ return this.groupSeq; }

}
