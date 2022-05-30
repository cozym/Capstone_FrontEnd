package com.example.practicespace.ui;

import android.webkit.JavascriptInterface;

import com.google.gson.Gson;

public class MyJavascriptInterface {

    @JavascriptInterface
    public void getHtml(String html) {
        GetUserInfo(html);
    }

    public void GetUserInfo(String response) {
        Gson gson = new Gson();
        LoginInfo info = gson.fromJson(response, LoginInfo.class);

        LoginInfo.instance = info;

    }

}