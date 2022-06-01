package com.example.practicespace.connection;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {
    @FormUrlEncoded
    @POST("book")
    Call<setBook> saveBook(
            @Header("Authorization") String token,
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
    Call<getBook> getBookSeq(
            @Header("Authorization") String token,
            @Path("seq") int seq );

    @DELETE("book/{seq}")
    Call<deleteBook> deleteBookSeq(
            @Header("Authorization") String token,
            @Path("seq") int seq);

    @FormUrlEncoded
    @PATCH("book/borrow")
    Call<borrowBook> borrow(
            @Header("Authorization") String token,
            @Field("bookSeq") int bookSeq);

    @GET("book/isbn/{seq}")
    Call<searchByIsbn> getIsbnSeq(
            @Header("Authorization") String token,
            @Path("isbn") String isbn);

    @GET("book/list")
    Call<bookList> getBookList(
            @Header("Authorization") String token,
            @Field("groupSeq") int groupSeq,
//             @Field("start") int start,
//             @Field("display") int display
    );

    @GET("book/mybook")
    Call<myBookList> getMyBookList(
            @Header("Authorization") String token,
            @Field("start") int start,
            @Field("display") int display);

    @FormUrlEncoded
    @PATCH("book/return")
    Call<returnBook> bookReturn(
            @Header("Authorization") String token,
            @Field("bookSeq") int bookSeq);

    @FormUrlEncoded
    @PUT("book/update")
    Call<updateBook> bookUpdate(
            @Header("Authorization") String token,
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
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("isOpen") boolean isOpen,
            @Field("thumbnail") String thumbnail,
            @Field("description") String description,
            @Field("longtitude") double longtitude,
            @Field("latitude") double latitude);

    @GET("group/{seq}")
    Call<getGroup> getGroupSeq(
            @Header("Authorization") String token,
            @Path("seq") int seq );


    @DELETE("group/{seq}")
    Call<deleteGroup> deleteGroupSeq(
            @Header("Authorization") String token,
            @Path("seq") int seq);

    @FormUrlEncoded
    @PATCH("group/authorize")
    Call<authorizeAdmin> groupAuthorize(
            @Header("Authorization") String token,
            @Field("groupSeq") int groupSeq,
            @Field("userSeq") int userSeq);

    @FormUrlEncoded
    @POST("group/join")
    Call<joinGroup> join(
            @Header("Authorization") String token,
            @Field("groupSeq") int groupSeq,
            @Field("authenticationCode") String authenticationCode);

    @GET("group/list")
    Call<openGroupList> getGroupList();

    @DELETE("group/resign")
    Call<resignGroup> Resign(
            @Header("Authorization") String token,
            @Field("groupSeq") int groupSeq);

    @GET("user/info")
    Call<getUser> getUserInfo(
            @Header("Authorization") String token);

    @FormUrlEncoded
    @PATCH("user/nickname")
    Call<modNickname> modifyNickname(
            @Header("Authorization") String token,
            @Field("nickname") String nickname);

}
