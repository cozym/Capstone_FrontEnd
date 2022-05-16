package com.example.user;

public class ListViewItem {

    private int group_icon;
    private String group_name;
    private String hash_tag;

    public ListViewItem(int icon, String name, String hashtag){
        this.group_icon = icon;
        this.group_name = name;
        this.hash_tag = hashtag;

    }




    public int getIcon(){ return this.group_icon; }
    public String getGroupName(){ return this.group_name; }
    public String getHashTag(){ return this.hash_tag; }

}
