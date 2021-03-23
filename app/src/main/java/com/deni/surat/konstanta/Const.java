package com.deni.surat.konstanta;

/**
 * Created by Deni Supriyatna (deni ace) on 13 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public class Const {
    private static String base = "http://192.168.43.190/surat/";

    public static String baseUrl() {
        return base + "api/";
    }

    public static String url() {
        return base + "downloadsurat/";
    }

    public static String urlFoto(){
        return base + "uploads/image/foto/";
    }
}
