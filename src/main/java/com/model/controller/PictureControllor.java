package com.model.controller;

import com.google.gson.Gson;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.model.interceptors.ErrorInterceptor;
import com.model.util.DateUtil;
import com.model.util.HttpRequester;
import com.model.util.UnifyThrowEcxp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/10/14.
 */
@Before(value = {ErrorInterceptor.class,Tx.class})
public class PictureControllor extends Controller {

    /**
     * 获取系统时间
     */
    public void Date(){
        String ddate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(Calendar.getInstance().getTime());
        renderJson(ddate);
    }

    /**
     * JsSDK
     */
    public void JsSdk(){
        TreeMap map = new TreeMap();
        try {
            String role = Db.getSql("overall.access_token");
            Record overa = Db.findFirst(role,"access_token");
            long i = DateUtil.Time((String) overa.get("expire"));
            if(i < 0L) {
                String jsapi_ticket = HttpRequester.jsapi_ticket();
                String date = DateUtil.Time();
                String StopTime = DateUtil.DateDouble(date, Double.valueOf(1.8D));
                Record overalJ = new Record();
                overalJ.set("id",overa.get("id")).set("role","access_token").set("price",jsapi_ticket).set("expire",StopTime);
                Db.update("overall",overalJ);
                map.put("jsapi_ticket",jsapi_ticket);
                renderJson(map);
            } else {
                String jsapi_ticket = overa.get("price");
                map.put("jsapi_ticket",jsapi_ticket);
                renderJson(map);
            }
        } catch (Exception e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));

        }
    }

    /**
     * 获取模卡图片
     */
    public void mookeType(){
        Integer type = Integer.valueOf(getPara("type"));
        String openid = getSessionAttr("openid");
        String mookeType = Db.getSql("mooke.SelectMooke");
        List<Record> mooke = Db.find(mookeType,openid,type);
        renderJson(mooke);
    }
}
