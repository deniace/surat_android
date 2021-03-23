package com.deni.surat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deni Supriyatna (deni ace) on 12 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public class SuratValidasiRequest {
    @SerializedName("id_jabatan")
    @Expose
    private String idJabatan;

    @SerializedName("id_status_rt")
    @Expose
    private String idStatusRt;
    @SerializedName("id_status_rw")
    @Expose
    private String idStatusRw;

    @SerializedName("id_rt")
    @Expose
    private String idRt;

    @SerializedName("id_rw")
    @Expose
    private String idRw;

    public String getIdJabatan() {
        return idJabatan;
    }

    public void setIdJabatan(String idJabatan) {
        this.idJabatan = idJabatan;
    }

    public String getIdStatusRt() {
        return idStatusRt;
    }

    public void setIdStatusRt(String idStatusRt) {
        this.idStatusRt = idStatusRt;
    }

    public String getIdStatusRw() {
        return idStatusRw;
    }

    public void setIdStatusRw(String idStatusRw) {
        this.idStatusRw = idStatusRw;
    }

    public String getIdRt() {
        return idRt;
    }

    public void setIdRt(String idRt) {
        this.idRt = idRt;
    }

    public String getIdRw() {
        return idRw;
    }

    public void setIdRw(String idRw) {
        this.idRw = idRw;
    }
}
