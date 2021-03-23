package com.deni.surat.network.endpoint;

import com.deni.surat.model.LoginRequest;
import com.deni.surat.model.LoginResponse;
import com.deni.surat.model.RegisterRequest;
import com.deni.surat.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Deni Supriyatna (deni ace) on 04 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public interface User {

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);


}
