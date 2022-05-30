package com.example.practicespace;

public class LoginInfo {

    public static LoginInfo instance;

    public static LoginInfo getInstance() {
        return instance;
    }

    String httpStatus;
    String message;
    LoginToken data;
}
