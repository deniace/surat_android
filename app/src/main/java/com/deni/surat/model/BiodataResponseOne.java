package com.deni.surat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deni Supriyatna (deni ace) on 07 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public class BiodataResponseOne {
    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private BiodataSingle biodataSingle;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public BiodataSingle getBiodataSingle() {
        return biodataSingle;
    }

    public void setBiodataSingle(BiodataSingle biodataSingle) {
        this.biodataSingle = biodataSingle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
