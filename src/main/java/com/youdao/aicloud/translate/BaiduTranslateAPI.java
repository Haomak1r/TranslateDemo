package com.youdao.aicloud.translate;

import com.baidu.translate.demo.TransApi;

public class BaiduTranslateAPI {
    private static final String APP_ID = "20231206001903138";
    private static final String SECURITY_KEY = "tMkks62qUtW50jDEiUYw";
    public static String translate(String text, String from, String to) {
        TransApi api=new TransApi(APP_ID,SECURITY_KEY);
        String result=api.getTransResult(text,from,to);
        int index=result.indexOf("dst");
        int lastindex=result.lastIndexOf("}");
        result=result.substring(index+5,lastindex-2);
        result=result.replace("\"","");
        return result;
    }
}