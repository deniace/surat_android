package com.deni.surat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Deni Supriyatna (deni ace) on 11 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public class SuratResponsePaging {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("total_data")
    @Expose
    private Integer totalData;
    @SerializedName("data")
    @Expose
    private List<SuratSingle> suratSingle = null;

    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getTotalData() {
        return totalData;
    }

    public void setTotalData(Integer totalData) {
        this.totalData = totalData;
    }

    public List<SuratSingle> getSuratSingle() {
        return suratSingle;
    }

    public void setSuratSingle(List<SuratSingle> suratSingle) {
        this.suratSingle = suratSingle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
