package com.example.practicespace.ui;

import java.util.List;

public class ListViewItem2 {

    private List<String> email;
    private String nickname;
    private boolean admin;
    private int userSeq;
    private int groupSeq;



    public ListViewItem2(List<String> email, String nickname, boolean admin, int userSeq, int groupSeq){
        this.email = email;
        this.nickname = nickname;
        this.admin = admin;
        this.userSeq = userSeq;
        this.groupSeq = groupSeq;
    }




    public List<String> getEmail(){return this.email;}
    public String getNickname(){return this.nickname;}
    public boolean getAdmin(){return this.admin;}
    public int getUserSeq(){return this.userSeq;}
    public int getGroupSeq(){return this.groupSeq;}
}
