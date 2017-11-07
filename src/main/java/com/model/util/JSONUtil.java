package com.model.util;


import net.sf.json.JSONArray;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/5/13.
 */
public class JSONUtil {
    /*
    * 集合转JSON集合
    * */
    public static List<JSONArray> JSONList(List objectList){
        List<JSONArray> jsonArrays = new ArrayList<JSONArray>();
        for (Object banners1:objectList){
            jsonArrays.add(net.sf.json.JSONArray.fromObject(banners1));
        }
        return jsonArrays;
    }
    /**
     * 对象转为JSON
     */
    public static JSONArray JSON(Object jsonList){
        JSONArray jsonArray = JSONArray.fromObject(jsonList);
        return jsonArray;
    }
    public static String distinguish(String sort) {
        if (sort.equals("")) {
            sort = null;
        }
        return sort;
    }

    public static String address(String address) {
        if(address.equals("全国")) {
            address = null;
        }

        return address;
    }

    public static String Cutout(String arealist) {
        String[] arr = arealist.split(" ");
        return arr[1];
    }

    public static String judge(String judge){
        if (judge.equals("")){
            return null;
        }else {
            return "%"+judge+"%";
        }
    }

}
