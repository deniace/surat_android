package com.deni.surat.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Deni Supriyatna (deni ace) on 04 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public class PreferenceManager {

    private static final String TOKEN = "token";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String ID_USER = "id_user";
    private static final String NO_HP = "no_hp";
    private static final String NAMA_USER = "nama_user";
    private static final String ID_JABATAN = "id_jabatan";
    private static final String NAMA_JABATAN = "nama_jabatan";
    private static final String ALAMAT = "alamat";
    private static final String IS_LOGIN = "is_login";
    private static final String REMEMBER_ME = "remember_me";
    private static final String RT = "rt";
    private static final String RW = "rw";

    private String token;
    private String email;
    private String password;
    private String idUser;
    private String noHp;
    private String namaUser;
    private String idJabatan;
    private String namaJabatan;
    private String alamat;
    private boolean isLogin;
    private boolean rememberMe;
    private String rt;
    private String rw;

    private Context context;

    private SharedPreferences preferences;

    public PreferenceManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("RIZKY", Context.MODE_PRIVATE);
    }

    public String getToken() {
        token = preferences.getString(TOKEN, "");
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        preferences.edit().putString(TOKEN, token).apply();
    }

    public String getIdUser() {
        idUser = preferences.getString(ID_USER, "");
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
        preferences.edit().putString(ID_USER, idUser).apply();
    }

    public String getNoHp() {
        noHp = preferences.getString(NO_HP, "");
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
        preferences.edit().putString(NO_HP, noHp).apply();
    }

    public String getNamaUser() {
        namaUser = preferences.getString(NAMA_USER, "");
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
        preferences.edit().putString(NAMA_USER, namaUser).apply();
    }

    public String getIdJabatan() {
        idJabatan = preferences.getString(ID_JABATAN, "");
        return idJabatan;
    }

    public void setIdJabatan(String idJabatan) {
        this.idJabatan = idJabatan;
        preferences.edit().putString(ID_JABATAN, idJabatan).apply();
    }

    public String getNamaJabatan() {
        namaJabatan = preferences.getString(NAMA_JABATAN, "");
        return namaJabatan;
    }

    public void setNamaJabatan(String namaJabatan) {
        this.namaJabatan = namaJabatan;
        preferences.edit().putString(NAMA_JABATAN, namaJabatan).apply();
    }

    public String getAlamat() {
        alamat = preferences.getString(ALAMAT, "");
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
        preferences.edit().putString(ALAMAT, alamat).apply();
    }

    public boolean isLogin() {
        isLogin = preferences.getBoolean(IS_LOGIN, false);
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
        preferences.edit().putBoolean(IS_LOGIN, isLogin).apply();
    }

    public boolean isRememberMe() {
        rememberMe = preferences.getBoolean(REMEMBER_ME, false);
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
        preferences.edit().putBoolean(REMEMBER_ME, rememberMe).apply();
    }

    public String getEmail() {
        email = preferences.getString(EMAIL, "");
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        preferences.edit().putString(EMAIL, email).apply();
    }

    public String getPassword() {
        password = preferences.getString(PASSWORD, "");
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        preferences.edit().putString(PASSWORD, password).apply();
    }

    public String getRt() {
        rt = preferences.getString(RT, "");
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
        preferences.edit().putString(RT, rt).apply();
    }

    public String getRw() {
        rw = preferences.getString(RW, "");
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
        preferences.edit().putString(RW, rw).apply();
    }
}
