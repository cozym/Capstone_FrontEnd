package com.example.practicespace.ui;

import java.util.List;

public class ListViewItem2 {

    private List<String> email;
    private String nickname;
    private boolean admin;
    private int userSeq;



    public ListViewItem2(List<String> email, String nickname, boolean admin, int userSeq){
        this.email = email;
        this.nickname = nickname;
        this.admin = admin;
        this.userSeq = userSeq;
    }




    public List<String> getEmail(){return this.email;}
    public String getNickname(){return this.nickname;}
    public boolean getAdmin(){return this.admin;}
    public int getUserSeq(){return this.userSeq;}
}
