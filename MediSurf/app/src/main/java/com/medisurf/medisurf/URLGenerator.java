package com.medisurf.medisurf;


public class URLGenerator {
//    public static String ip = "128.199.132.222";
    public static final String ip = "http://192.168.42.10:8000/";
    public static final String index = "ms/index/";
    public static final String genericsalt = "ms/genericsalt/";
    public static final String showbrands = "ms/showbrands/";
    public static final String getbrands = "ms/getbrands/";
    public static final String optimisebill = "ms/optimisebill/";
    public static final String savestat = "ms/savestat/";
    static String getUrl(final String URLFor){
        return ip + URLFor;
    }
}
