package com.youdao.aicloud.translate;

import com.youdao.aicloud.translate.utils.AuthV3Util;
import com.youdao.aicloud.translate.utils.HttpUtil;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 网易有道智云翻译服务api调用demo
 * api接口: https://openapi.youdao.com/api
 */
public class YoudaoTranslateAPI {

    private static final String APP_KEY = "69217fdb3f514ec6";     // 您的应用ID
    private static final String APP_SECRET = "N8VdfJafuinKXnHUFL6p3pXFRRCamh5M";  // 您的应用密钥

    public static String translate(String text, String from, String to) throws NoSuchAlgorithmException {
        // 添加请求参数
        Map<String, String[]> params = createRequestParams(text,from,to);
        // 添加鉴权相关参数
        AuthV3Util.addAuthParams(APP_KEY, APP_SECRET, params);
        // 请求api服务
        byte[] result = HttpUtil.doPost("https://openapi.youdao.com/api", null, params, "application/json");
        // 打印返回结果
        if (result != null) {
            String M=new String(result, StandardCharsets.UTF_8);
            int translateIndex=M.indexOf("translation");
            if(translateIndex!=-1){
                int start=M.indexOf("[",translateIndex);
                int end=M.indexOf("]",start);
                String result1=M.substring(start+1,end);
                result1=result1.replace("\"","");
                int point=result1.indexOf(".");
                if (point!=-1 && point<result1.length())
                    result1=result1.substring(0,point);
                return result1;
            }
        }
        return null;
    }

    private static Map<String, String[]> createRequestParams(String text,String f,String t) {
        /*
         * note: 将下列变量替换为需要请求的参数
         * 取值参考文档: https://ai.youdao.com/DOCSIRMA/html/%E8%87%AA%E7%84%B6%E8%AF%AD%E8%A8%80%E7%BF%BB%E8%AF%91/API%E6%96%87%E6%A1%A3/%E6%96%87%E6%9C%AC%E7%BF%BB%E8%AF%91%E6%9C%8D%E5%8A%A1/%E6%96%87%E6%9C%AC%E7%BF%BB%E8%AF%91%E6%9C%8D%E5%8A%A1-API%E6%96%87%E6%A1%A3.html
         */
        String q = text;
        String from = f;
        String to = t;

        return new HashMap<String, String[]>() {{
            put("q", new String[]{q});
            put("from", new String[]{from});
            put("to", new String[]{to});
        }};
    }
}
