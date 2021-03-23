package com.deni.surat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deni Supriyatna (deni ace) on 11 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public class SuratSingle {
    @SerializedName("id_surat")
    @Expose
    private String idSurat;
    @SerializedName("no_surat")
    @Expose
    private String noSurat;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("keperluan")
    @Expose
    private String keperluan;
    @SerializedName("id_rt")
    @Expose
    private String idRt;
    @SerializedName("id_rw")
    @Expose
    private String idRw;
    @SerializedName("id_status_rt")
    @Expose
    private String idStatusRt;
    @SerializedName("id_status_rw")
    @Expose
    private String idStatusRw;
    @SerializedName("tgl_rt")
    @Expose
    private String tglRt;
    @SerializedName("nama_file")
    @Expose
    private String namaFile;
    @SerializedName("id_jabatan")
    @Expose
    private String idJabatan;
    @SerializedName("nama_user")
    @Expose
    private String namaUser;
    @SerializedName("no_hp")
    @Expose
    private String noHp;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("rt")
    @Expose
    private String rt;
    @SerializedName("rw")
    @Expose
    private String rw;
    @SerializedName("jenis_kelamin")
    @Expose
    private String jenisKelamin;
    @SerializedName("agama")
    @Expose
    private String agama;
    @SerializedName("tempat_lahir")
    @Expose
    private String tempatLahir;
    @SerializedName("tanggal_lahir")
    @Expose
    private String tanggalLahir;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("status_perkawinan")
    @Expose
    private String statusPerkawinan;

    @SerializedName("pekerjaan")
    @Expose
    private String pekerjaan;




    public String getIdSurat() {
        return idSurat;
    }

    public void setIdSurat(String idSurat) {
        this.idSurat = idSurat;
    }

    public String getNoSurat() {
        return noSurat;
    }

    public void setNoSurat(String noSurat) {
        this.noSurat = noSurat;
    }

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

    public String getTglRt() {
        return tglRt;
    }

    public void setTglRt(String tglRt) {
        this.tglRt = tglRt;
    }

    public String getNamaFile() {
        return namaFile;
    }

    public void setNamaFile(String namaFile) {
        this.namaFile = namaFile;
    }

    public String getIdJabatan() {
        return idJabatan;
    }

    public void setIdJabatan(String idJabatan) {
        this.idJabatan = idJabatan;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getStatusPerkawinan() {
        return statusPerkawinan;
    }

    public void setStatusPerkawinan(String statusPerkawinan) {
        this.statusPerkawinan = statusPerkawinan;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }


}
