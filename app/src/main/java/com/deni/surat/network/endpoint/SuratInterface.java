package com.deni.surat.network.endpoint;

import com.deni.surat.model.RegisterResponse;
import com.deni.surat.model.SuratRequest;
import com.deni.surat.model.SuratRequestPaging;
import com.deni.surat.model.SuratResponseOne;
import com.deni.surat.model.SuratResponsePaging;
import com.deni.surat.model.SuratValidasiRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Deni Supriyatna (deni ace) on 10 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public interface SuratInterface {
    @Headers("Content-Type: application/json")
    @POST("surat")
    Call<RegisterResponse> postSurat (@Header("Authorization") String token, @Body SuratRequest suratRequest);

    @Headers("Content-Type: application/json")
    @POST("surat/paging")
    Call<SuratResponsePaging> getSuratPaging (@Header("Authorization") String token, @Body SuratRequestPaging suratRequestPaging);

    @Headers("Content-Type: application/json")
    @GET("surat/{idSurat}")
    Call<SuratResponseOne> getSuratById (@Header("Authorization") String token, @Path("idSurat") String idSurat);

    @Headers("Content-Type: application/json")
    @PUT("surat/{idSurat}")
    Call<RegisterResponse> validasiSuratById (@Header("Authorization") String token, @Path("idSurat") String idSurat, @Body SuratValidasiRequest suratValidasiRequest);

    @Headers("Content-Type: application/json")
    @GET("surat/user/{idUser}")
    Call<SuratResponsePaging> getSuratByUser (@Header("Authorization") String token, @Path("idUser") String idUser);
}
