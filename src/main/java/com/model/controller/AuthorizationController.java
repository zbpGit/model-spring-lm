package com.model.controller;

import com.google.gson.Gson;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.model.interceptors.ErrorInterceptor;
import com.model.util.*;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/13.
 */
@Before(value = {ErrorInterceptor.class,Tx.class})
public class AuthorizationController extends Controller {

    /**
     * 授权
     * @throws UnsupportedEncodingException
     */
    public void wxLogin() throws UnsupportedEncodingException {
        String Url = getPara("url");
        String url = AuthorizationUtil.wxLogin();
        redirect(url);
    }

    /**
     * 授权回调
     */
    public void callBack(){
        try {
            String code = getPara("code");
            JSONObject infouser = AuthorizationUtil.callBack(code);
            String openid = infouser.getString("openid");
            String nickname = Db.getSql("modelMessage.nickname");
            Record model = Db.findFirst(nickname,openid);
            if(model == null) {
                Record modelMessage = AuthorizationUtil.message(infouser);
                Db.save("model",modelMessage);
                setSessionAttr("id",modelMessage.get("id"));
                setSessionAttr("openid",modelMessage.get("nickname"));
                render("index.html");
            } else {
                setSessionAttr("id",model.get("id"));
                setSessionAttr("openid",model.get("nickname"));
                render("index.html");
            }
        } catch (Exception e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 通告支付
     */
    public void Pay(){
        Integer vid = Integer.valueOf(getPara("vid"));
        Integer money = Integer.valueOf(getPara("money"));
        String openId = getSessionAttr("openid");
        String out_trade_no = SignUtil.getRandomStringByLength();
        try {

            String sqlMoeny = Db.getSql("serve.moneyType");
            Record serve = Db.findFirst(sqlMoeny,money,1);
            String sqlSitz = Db.getSql("annunciate.SelectStick");
            Record sitz = Db.findFirst(sqlSitz,vid);

            if(serve == null) {
                renderJson("{\"result\":\"不存在该充值类型\"}");
            }
            String nowTime = DateUtil.Time();
            String message = "亲爱的用户，你充值的" + serve.get("details") + "已经取消充值！";

            Record official = new Record();
            official.set("vid",vid).set("message",message).set("out_trade_no",out_trade_no)
                    .set("nickname",openId).set("timestamp",nowTime);
            Db.save("official",official);

            if(serve.get("time").equals("官方")) {
                Record stick = new Record();
                stick.set("vid",vid).set("out_trade_no",out_trade_no).set("nowTime",nowTime).set("stopTime",serve.get("time"))
                        .set("nickname",openId).set("money",money).set("sitz",sitz);
                Db.save("stick",stick);
            } else {
                String SelectCount = Db.getSql("Stick.SelectCount");
                Record count = Db.findFirst(SelectCount,sitz.get("site"));
                Long ct = count.get("ct");
                if(ct < 5) {
                    String SelectCheck = Db.getSql("Stick.SelectCheck");
                    Record Check = Db.findFirst(SelectCheck,vid);
                    if(Check != null && !Check.equals("")) {
                        Record stickSave = new Record();
                        stickSave.set("vid",vid).set("out_trade_no",out_trade_no).set("nowTime",Check.get("nowTime"))
                                .set("stopTime","0").set("nickname",openId).set("money",money).set("sitz",sitz.get("site"));
                        Db.save("stick",stickSave);
                    } else {
                        Integer time = Integer.valueOf((String)serve.get("time"));
                        String stopTime = DateUtil.Delay(time);
                        Record stickSave = new Record();
                        stickSave.set("vid",vid).set("out_trade_no",out_trade_no).set("nowTime",nowTime)
                                .set("stopTime",stopTime).set("nickname",openId).set("money",money).set("sitz",sitz.get("site"));
                        System.out.println(stickSave);
                        Db.save("stick",stickSave);
                    }
                } else {
                    Record stickSave = new Record();
                    stickSave.set("vid",vid).set("out_trade_no",out_trade_no).set("nowTime",nowTime)
                            .set("stopTime","0").set("nickname",openId).set("money",money).set("sitz",sitz.get("site"));
                    Db.save("stick",stickSave);
                }
                String pay = AuthorizationUtil.Pay(openId, money, out_trade_no, "http://www.qingmeng168.com/model-spring-lm/asynchronous/syntony");
                redirect(pay);
            }
        } catch (Exception e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 名片支付
     */
    public void Work() {
        /*Integer id = Integer.valueOf(getPara("id"));
        Integer money = Integer.valueOf(getPara("money"));*/
        Integer id = 144;
        Integer money = 10;
        String openId = getSessionAttr("openid");
        String out_trade_no = SignUtil.getRandomStringByLength();
        String sqlMoeny = Db.getSql("serve.moneyType");
        Record serve = Db.findFirst(sqlMoeny, money, 2);
        if (serve == null) {
            renderJson("{\"result\":\"不存在该充值类型\"}");
        } else {
            try {
            String nowTime = DateUtil.Time();
            String message = "亲爱的用户，你充值的" + serve.get("details") + "已经取消充值！";
            Record official = new Record();
            official.set("vid", id).set("message", message).set("out_trade_no", out_trade_no)
                    .set("nickname", openId).set("timestamp", nowTime);
            Db.save("official", official);
            String SelectStickC = Db.getSql("work.SelectStickC");
            Record work = Db.findFirst(SelectStickC);
            if ((Long)work.get("ct") < 5) {
                String Identifying = Db.getSql("wStick.Identifying");
                Record wStick = Db.findFirst(Identifying, id);
                if (wStick != null && !wStick.equals("")) {
                    Record stickSave = new Record();
                    stickSave.set("vid", id).set("out_trade_no", out_trade_no).set("nowTime",nowTime)
                            .set("stopTime", "0").set("nickname", openId).set("money", money);
                    Db.save("wstick", stickSave);
                } else {
                    Integer time = Integer.valueOf((String) serve.get("time"));
                    String stopTime = DateUtil.Delay(time);
                    Record stickSave = new Record();
                    stickSave.set("vid", id).set("out_trade_no", out_trade_no).set("nowTime", nowTime)
                            .set("stopTime", stopTime).set("nickname", openId).set("money", money);
                    System.out.println(stickSave);
                    Db.save("wstick", stickSave);
                }
            } else {
                Record stickSave = new Record();
                stickSave.set("vid", id).set("out_trade_no", out_trade_no).set("nowTime", nowTime)
                        .set("stopTime", "0").set("nickname", openId).set("money", money);
                Db.save("wstick", stickSave);
            }
                String pay = AuthorizationUtil.Pay(openId, money, out_trade_no, "http://www.qingmeng168.com/model-spring-lm/asynchronous/syntony");
                redirect(pay);
                renderText("123");
            } catch (Exception e) {
                throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
            }
        }
    }


    /**
     * 签名Sign
     */
    public void Sign(){
        String url = getPara("url");
        String jsapi_ticket = null;
        try {
            String role = Db.getSql("overall.access_token");
            Record overa = Db.findFirst(role,"access_token");
            if(overa == null) {
                    jsapi_ticket = HttpRequester.jsapi_ticket();
                    String ticketd = DateUtil.Time();
                    String StopTime = DateUtil.DateDouble(ticketd, Double.valueOf(1.8D));
                    Record overalJ = new Record();
                    overalJ.set("role","access_token").set("price",jsapi_ticket).set("expire",StopTime);
                    Db.save("overall",overalJ);
                } else if(overa != null) {
                    long i = DateUtil.Time((String) overa.get("expire"));
                    if(i < 0L) {
                        jsapi_ticket = HttpRequester.jsapi_ticket();
                        String date = DateUtil.Time();
                        String StopTime = DateUtil.DateDouble(date, Double.valueOf(1.8D));
                        Record overalJ = new Record();
                        overalJ.set("id",overa.get("id")).set("role","access_token").set("price",jsapi_ticket).set("expire",StopTime);
                        Db.update("overall",overalJ);
                    } else {
                        jsapi_ticket = overa.get("price");
                    }
            }
            String ticketd = HttpRequester.jsp_token(jsapi_ticket);
            Map map = SignUtil.sign(ticketd, url);
            map.put("APPID", "wx56731a06a3e947e8");
            renderJson(map);
        } catch (Exception e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

}
