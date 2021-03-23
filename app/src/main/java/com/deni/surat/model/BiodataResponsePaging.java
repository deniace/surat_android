package com.deni.surat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Deni Supriyatna (deni ace) on 07 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public class BiodataResponsePaging {
    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("total_data")
    @Expose
    private int totalData;

    @SerializedName("data")
    @Expose
    private List<BiodataSingle> biodataSingle;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<BiodataSingle> getBiodataSingle() {
        return biodataSingle;
    }

    public void setBiodataSingle(List<BiodataSingle> biodataSingle) {
        this.biodataSingle = biodataSingle;
    }

    public int getTotalData() {
        return totalData;
    }

    public void setTotalData(int totalData) {
        this.totalData = totalData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
