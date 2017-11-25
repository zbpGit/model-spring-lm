package com.model.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

public class AuthUtil {

    public static JSONObject doGetJson(String url) throws IOException {
        JSONObject jsonObject = null;
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null){
            String result = EntityUtils.toString(entity,"UTF-8");
            jsonObject = JSONObject.fromObject(result);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }

    /**
     * 给微信返回参数
     * @param return_code
     * @param return_msg
     * @return
     */
    public static String setXml(String return_code, String return_msg) {
        SortedMap<String, String> parameters = new TreeMap();
        parameters.put("return_code", return_code);
        parameters.put("return_msg", return_msg);
        return "<xml><return_code><![CDATA[" + return_code + "]]>" + "</return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }
}
