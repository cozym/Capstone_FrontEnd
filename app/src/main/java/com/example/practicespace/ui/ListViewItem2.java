package com.example.practicespace.ui;

import java.util.List;

public class ListViewItem2 {

    private List<String> email;
    private String nickname;



    public ListViewItem2(List<String > email, String nickname){
        this.email = email;
        this.nickname = nickname;
    }




    public List<String> getEmail(){return this.email;}
    public String getNickname(){return this.nickname;}
}
