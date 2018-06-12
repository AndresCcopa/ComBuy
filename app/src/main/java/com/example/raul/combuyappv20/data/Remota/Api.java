package com.example.raul.combuyappv20.data.Remota;

import android.os.StrictMode;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api{

    public static CombuyClient getRetrofit(){

        final String URL_APICOMBUY="https://combuyapi.herokuapp.com/";
        CombuyClient api;


        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(URL_APICOMBUY)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(CombuyClient.class);
        return  api;
    }
}
