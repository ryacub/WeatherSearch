package com.wip.rayyacub.weathersearch.Networking;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String SEARCH_URL = "http://api.openweathermap.org/";
    public static final String API_KEY = "8e1af422c78d7f280383d6144f2de0d7";

    public static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(SEARCH_URL)
                    .client(getRetrofitOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static ApiService getApiService(){
        retrofit = null;
        return ApiClient.getClient().create(ApiService.class);
    }
    //http client interceptor to help with debugging
    private static OkHttpClient getRetrofitOkHttpClient() {
        HttpLoggingInterceptor localHttpLoggingInterceptor = new HttpLoggingInterceptor();
        localHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(localHttpLoggingInterceptor).build();
    }

}
