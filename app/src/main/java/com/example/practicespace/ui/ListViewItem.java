package com.example.practicespace.ui;

import com.example.practicespace.vo.Admin;

public class ListViewItem {

    private int group_icon;
    private String group_name;
    private String description;
    private int groupSeq;
    private boolean isOpen;
    private String createdDate;
    private Admin admin;


    public ListViewItem(int icon, String name, String description, int seq, boolean isOepn, String createdDate, Admin admin){
        this.group_icon = icon;
        this.group_name = name;
        this.description = description;
        this.groupSeq = seq;
        this.isOpen = isOepn;
        this.createdDate = createdDate;
        this.admin = admin;
    }




    public int getIcon(){ return this.group_icon; }
    public String getGroupName(){ return this.group_name; }
    public int getGroupSeq(){ return this.groupSeq; }
    public boolean getIsOpen(){return this.isOpen;}
    public String getGroupDes(){return this.description;}
    public String getCreatedDate(){return this.createdDate;}
    public Admin getAdmin(){return  this.admin;}
}
