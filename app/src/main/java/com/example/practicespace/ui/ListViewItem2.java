package com.example.practicespace.ui;

import java.util.List;

public class ListViewItem2 {

    private List<String> email;
    private String nickname;
    private boolean admin;



    public ListViewItem2(List<String> email, String nickname, boolean admin){
        this.email = email;
        this.nickname = nickname;
        this.admin = admin;
    }




    public List<String> getEmail(){return this.email;}
    public String getNickname(){return this.nickname;}
    public boolean getAdmin(){return this.admin;}
}
