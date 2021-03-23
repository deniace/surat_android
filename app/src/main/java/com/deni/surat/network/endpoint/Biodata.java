package com.deni.surat.network.endpoint;

import com.deni.surat.model.BiodataRequestPaging;
import com.deni.surat.model.BiodataResponseAll;
import com.deni.surat.model.BiodataResponseOne;
import com.deni.surat.model.BiodataResponsePaging;
import com.deni.surat.model.BiodataSingle;
import com.deni.surat.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Deni Supriyatna (deni ace) on 07 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public interface Biodata {
    @Headers("Content-Type: application/json")
    @GET("profile")
    Call<BiodataResponseAll> getProfileAll (@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("profile/paging")
    Call<BiodataResponsePaging> getProfilePaging (@Header("Authorization") String token, @Body BiodataRequestPaging biodataRequestPaging);

    @Headers("Content-Type: application/json")
    @GET("profile/{idUser}")
    Call<BiodataResponseOne> getProfileById (@Header("Authorization") String token, @Path("idUser") String idUser);

    @Headers("Content-Type: application/json")
    @PUT("profile/{idUser}")
    Call<RegisterResponse> updateProfileById (@Header("Authorization") String token, @Body BiodataSingle biodataSingle, @Path("idUser") String idUser);

    @Headers("Content-Type: application/json")
    @DELETE("profile/{idUser}")
    Call<RegisterResponse> deleteProfileById (@Header("Authorization") String token, @Path("idUser") String idUser);
}
