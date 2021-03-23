package com.deni.surat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deni Supriyatna (deni ace) on 10 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public class SuratRequest {
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("keperluan")
    @Expose
    private String keperluan;

    @SerializedName("foto")
    @Expose
    private String foto;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
