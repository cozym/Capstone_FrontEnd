package com.example.capstone_frontend.book_controller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book {
        @SerializedName("httpStatus")
        @Expose
        public String httpStatus;

        @SerializedName("message")
        @Expose
        public String message;
}
