package com.deni.surat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deni Supriyatna (deni ace) on 11 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public class SuratResponseOne {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private SuratSingle suratSingle;

    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public SuratSingle getSuratSingle() {
        return suratSingle;
    }

    public void setSuratSingle(SuratSingle suratSingle) {
        this.suratSingle = suratSingle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
