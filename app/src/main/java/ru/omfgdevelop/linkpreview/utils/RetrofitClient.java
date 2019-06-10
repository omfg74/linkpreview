package ru.omfgdevelop.linkpreview.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.omfgdevelop.linkpreview.BuildConfig;
import ru.omfgdevelop.linkpreview.interfaces.RetrofitInterface;
import ru.omfgdevelop.linkpreview.repository.Constants;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private Retrofit retrofit;
    private OkHttpClient client;
    private String API_URL = Constants.BASE_URL;
    private RetrofitInterface retrofitInterface;
    String accessToken = null;

    private Retrofit retrofitRefresh = null;

    public RetrofitClient() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(addInterceptor());
        }
        client = okHttpBuilder.build();
        retrofit = new Retrofit.Builder().baseUrl(API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//for test!!!!
                .addConverterFactory(GsonConverterFactory.create(addConvertorFactory()))
                .client(client)
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
    }


    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public RetrofitInterface getRetrofitInterface() {
        return retrofitInterface;
    }

    private HttpLoggingInterceptor addInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    private Gson addConvertorFactory() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return gson;
    }
}
