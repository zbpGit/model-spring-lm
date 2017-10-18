package com.model.util;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/16.
 */
public class SQLUtils {
    //根据请求参数动态生成sql，过滤空值
    //适用于条件查询
    public static String DynamicSQL(Map<String,Object> map){

        String sql = " where ";

        //遍历map把其中value为空的删除
        Iterator it = map.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry mapentry = (Map.Entry) it.next();
            if(mapentry.getValue()!=""){
                sql = sql + mapentry.getKey() + " = " + "'"+mapentry.getValue()+"'"+" and ";
            }
        }

        if(sql.trim().endsWith("and")){
            sql = sql.substring(0, sql.lastIndexOf("and"));
        }

        if(sql.equals(" where ")){
            return "";
        }

        //因为用到表的别名，需要替换
        sql = sql.replaceAll("user", "u");

        return sql;
    }
}
