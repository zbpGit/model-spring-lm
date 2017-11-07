package com.model.util;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/5.
 */
public class UnifyThrowEcxp {
    /**
     * 统一抛异常- maps里面可自定义异常参数
     */
    public static String throwExcp(Exception e) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("result","error");
        map.put("Date", String.valueOf(new Date()));
        map.put("type", e.toString());
        map.put("message", e.getMessage());
        return gson.toJson(map);
    }
}
