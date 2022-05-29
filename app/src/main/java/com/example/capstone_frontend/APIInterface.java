package com.example.capstone_frontend;

import com.example.capstone_frontend.book_controller.*;
import com.example.capstone_frontend.group_controller.*;

import java.security.acl.Group;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface APIInterface {
    @FormUrlEncoded
    @POST("book")
    Call<setBook> saveBook(
            @Field("title") String title,
            @Field("author") String author,
            @Field("publisher") String publisher,
            @Field("isbn") String isbn,
            @Field("thumbnail") String thumbnail,
            @Field("publishDate") String publishDate,
            @Field("description") String description,
            @Field("category") String category,
            @Field("groupSeq") int groupSeq);

    @GET("book/{seq}")
    Call<bookGet> getBookSeq( @Field("seq") int seq );

    @DELETE("book/{seq}")
    Call<bookDelete> deleteBookSeq( @Field("seq") int seq);

    @FormUrlEncoded
    @PATCH("book/borrow")
    Call<borrowBook> borrow( @Field("bookSeq") int bookSeq);

    @GET("book/isbn/{seq}")
    Call<searchByIsbn> getIsbnSeq( @Field("isbn") String isbn);

    @GET("book/list")
    Call<bookList> getBookList(
            @Field("groupSeq") int groupSeq,
            @Field("start") int start,
            @Field("display") int display);

    @GET("book/mybook")
    Call<myBookList> getMyBookList(
            @Field("start") int start,
            @Field("display") int display);

    @FormUrlEncoded
    @PATCH("book/return")
    Call<returnBook> bookReturn( @Field("bookSeq") int bookSeq);

    @FormUrlEncoded
    @PUT("book/update")
    Call<bookUpdate> bookUpdate(
            @Field("seq") int seq,
            @Field("title") String title,
            @Field("author") String author,
            @Field("publisher") String publisher,
            @Field("isbn") String isbn,
            @Field("thumbnail") String thumbnail,
            @Field("publishDate") String publishDate,
            @Field("description") String description,
            @Field("category") String category);

    @FormUrlEncoded
    @POST("group")
    Call<setGroup> saveGroup(
            @Field("name") String name,
            @Field("isOpen") boolean isOpen,
            @Field("thumbnail") String thumbnail,
            @Field("longtitude") double longtitude,
            @Field("latitude") double latitude);

    @GET("group/{seq}")
    Call<groupGet> getGroupSeq( @Field("seq") int seq );


    @DELETE("group/{seq}")
    Call<groupDelete> deleteGroupSeq( @Field("seq") int seq);

    @FormUrlEncoded
    @PATCH("group/authorize")
    Call<authorizeAdmin> groupAuthorize(
            @Field("groupSeq") int groupSeq,
            @Field("userSeq") int userSeq);

    @FormUrlEncoded
    @POST("group/join")
    Call<groupJoin> join(
            @Field("groupSeq") int groupSeq,
            @Field("authenticationCode") String authenticationCode);

    @GET("group/list")
    Call<openGroupList> getGroupList();

    @DELETE("group/resign")
    Call<groupResign> Resign( @Field("groupSeq") int groupSeq);
}
