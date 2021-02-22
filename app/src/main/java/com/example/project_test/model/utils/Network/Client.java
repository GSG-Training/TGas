package com.example.project_test.model.utils.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Retrofit retrofit=null;
    public static Retrofit getInstance(String Url){
        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

       return retrofit;
    }
}
