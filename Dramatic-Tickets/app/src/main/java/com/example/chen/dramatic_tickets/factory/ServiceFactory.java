package com.example.chen.dramatic_tickets.factory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chennan on 2018/6/29.
 */

public class ServiceFactory {
    public static final String baseUrl = "http://193.112.112.48";

    private static OkHttpClient createOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createOkHttp())
                .build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = createRetrofit();
        return retrofit.create(serviceClass);
    }
}
