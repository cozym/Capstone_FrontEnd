package com.example.practicespace.connection;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://5gradekgucapstone.xyz:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build();
        return retrofit;
    }
}