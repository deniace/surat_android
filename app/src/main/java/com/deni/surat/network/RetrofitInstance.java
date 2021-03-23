package com.deni.surat.network;

import android.content.Context;

import com.deni.surat.konstanta.Const;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Deni Supriyatna (deni ace) on 04 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public class RetrofitInstance {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = Const.baseUrl();


    public static Retrofit getRetrofit(Context context){
        if(retrofit == null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
