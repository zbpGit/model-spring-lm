package com.model.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
import com.model.interceptors.ErrorInterceptor;
import com.model.util.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/10/10.
 */
@Before(value = {ErrorInterceptor.class,Tx.class})
public class AnnunciateControllor extends Controller {



    /**
     * Banner标题
     */
    @Before(CacheInterceptor.class)
    @CacheName("Banner")
    public void Banner() {
        String banner = Db.getSql("banner.banner");
        List<Record> Banner = Db.find(banner);
        System.out.println(Banner);
        renderJson(Banner);
    }

    /**
     * 服务类型
     */
    public void serve() {
        String type = getPara("type");
        String serve = Db.getSql("serve.serve");
        List<Record> Serve = Db.find(serve, type);
        renderJson(Serve);
    }

    /**
     * 收藏通告
     */
    public void Collect() {
        Integer vid = getParaToInt("vid");
        String uid = getPara("uid");
        String openId = getSessionAttr("openid");
        if (!uid.equals(openId)) {
            String all = Db.getSql("model_an.pdsc");
            Record record = Db.findFirst(all, openId, vid);
            System.out.println(record);
            try {
                if (record == null) {
                    Record model_an = new Record();
                    model_an.set("mid", openId).set("aid", vid);
                    Db.save("model_an", model_an);
                    renderJson("{\"result\":\"已收藏\"}");
                } else {
                    Record model_an = new Record();
                    model_an.set("id", record.get("id"));
                    Db.delete("model_an", model_an);
                    renderJson("{\"result\":\"未收藏\"}");
                }
            } catch (Exception e) {
                throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
            }
        } else {
            renderJson("{\"result\":\"请勿收藏自己的通告\"}");
        }
    }

    /**
     * 通告首页
     */
    public void annunciate() {
        Integer page = getParaToInt("page");//起始页数
        String address = getPara("address");//地区
        String type = getPara("type");//类型
        String sort = getPara("sort");//排序
        Map map = new TreeMap();
        if (address.equals("")){
            Kv kv = Kv.by("type",type).set("sort",sort);
            SqlPara sqlPara = Db.getSqlPara("annunciate.SelectAll",kv);
            Page<Record> recordPage = Db.paginate(page,10,sqlPara);
            renderJson(recordPage);
        }else {
            if (page == 1){
                Kv officialN = Kv.by("official","非官方").set("top",1).set("address",JSONUtil.judge(address)).set("type",type).set("sort",sort);
                SqlPara Sqlpage = Db.getSqlPara("annunciate.SelectPage",officialN);
                List<Record> officialNList = Db.find(Sqlpage);
                map.put("stickAnnunciate",officialNList);
                Kv officialY = Kv.by("official","官方").set("top",0).set("address",JSONUtil.judge(address)).set("type",type).set("sort",sort);
                SqlPara para = Db.getSqlPara("annunciate.SelectPage",officialY);
                List<Record> officialYList = Db.find(para);
                map.put("official",officialYList);
            }
            Kv kv = Kv.by("official","非官方").set("top",0).set("address","%"+address+"%").set("type",type).set("sort",sort);
            SqlPara Sqlpage = Db.getSqlPara("annunciate.SelectPage",kv);
            Page<Record> recordPage = Db.paginate(page,10,Sqlpage);
            map.put("page",recordPage);
            renderJson(map);
        }
    }

    /**
     * 判断是不是官方
     */
    public void judge() {
        String openId = getSessionAttr("openid");
        Integer vid = getParaToInt("vid");
        String official = getPara("official");
        try {
            if (official.equals("官方")) {
                String SelectOfficial = Db.getSql("Stick.SelectOfficial");
                Record stick = Db.findFirst(SelectOfficial, openId, vid);
                if (stick != null && !stick.equals("")) {
                    renderJson("{\"result\":\"success\"}");
                } else {
                    String SelectTime = Db.getSql("serve.SelectTime");
                    Record serve = Db.findFirst(SelectTime, 1);
                    renderJson(serve);
                }
            } else {
                renderJson("{\"result\":\"请充值\"}");
            }
        } catch (Exception e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 通告详情表
     */
    public void Particulars() {
        Integer vid = getParaToInt("vid");
        String openId = getSessionAttr("openid");
        Map map = new TreeMap();
        try {
            String UpdateHit = Db.getSql("annunciate.UpdateHit");
            Db.update(UpdateHit,vid);
            String SelectAM = Db.getSql("annunciate.SelectAM");
            Record SelectAM02 = Db.findFirst(SelectAM,vid);
            SelectAM02.set("name",EmojiUtil.emojiRecovery2(String.valueOf(SelectAM02.get("name"))));
            map.put("annunciate", SelectAM02);
            String  PictureUrl = Db.getSql("view.PictureUrl");
            List<Record> view = Db.find(PictureUrl,SelectAM02.get("vid"));
            if(view != null) {
                map.put("view", view);
            } else if(view != null) {
                map.put("view", null);
            }
            String pdsc = Db.getSql("model_an.pdsc");
            Record model_a = Db.findFirst(pdsc, openId, vid);
            String SelectInform = Db.getSql("Reports.SelectInform");
            List<Record> reports = Db.find(SelectInform,openId, vid);
            if(model_a == null) {
                map.put("model_an", "未收藏");
            } else {
                map.put("model_an", "已收藏");
            }
            if(reports.size() != 0 && reports != null) {
                map.put("reports", "已举报");
            } else {
                map.put("reports", "未举报");
            }
            renderJson(map);
        } catch (Exception e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 编辑发送通告详情
     *
     */
    public void AddParticulars(){
        Record reception = new Record();
        reception.set("worktype",getPara("worktype")).set("deadtime",getPara("deadtime"))
                .set("worktheme",getPara("worktheme")).set("starttime",getPara("starttime"))
                .set("endtime",getPara("endtime")).set("arealist",getPara("arealist"))
                .set("detailaddr",getPara("detailaddr")).set("inputcount",getPara("inputcount"))
                .set("ifinterview",getPara("ifinterview")).set("gender",getPara("gender"))
                .set("price",getPara("price")).set("inputspecify",getPara("inputspecify"))
                .set("addrmark",getPara("addrmark")).set("contactinfo",getPara("contactinfo"));
        String serverId = getPara("serverId");
        String path = getRequest().getServletContext().getRealPath("Files/User");
        String openId = getSessionAttr("openid");
        Record annunciate = JsonMessageUtil.annunciatemessage(reception, openId);
        try {
            if(serverId.equals("")) {
                System.out.println(annunciate);
                Db.save("annunciate",annunciate);
            } else if(!serverId.equals("")) {
                Db.save("annunciate",annunciate);
                String role = Db.getSql("overall.access_token");
                Record overa = Db.findFirst(role,"access_token");
                String[] chrstr = serverId.split(",");
                for(int j = 0; j < chrstr.length; ++j) {
                    String file = DloadImgUtil.downloadMedia((String) overa.get("price"), chrstr[j], path, "/model-spring-lm/Files/User/");
                    Record view = new Record();
                    view.set("v_id",annunciate.get("Vid")).set("p_url",file);
                    Db.save("view",view);
                }
            }
            renderJson("{\"result\":\"success\"}");
        } catch (Exception e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 添加联系信息
     */
    public void Firstnext(){
        try {
            String id = getSessionAttr("id");
            Record model = Db.findById("model",id);
            if(model != null && model.get("wx_id") != null && model.get("phone") != null) {
                renderJson("{\"result\":\"存在联系方式\"}");
            } else {
                renderJson("{\"result\":\"不存在联系方式\"}");
            }
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 添加联系信息
     */
    public void Relation(){
        String id = getSessionAttr("id");
            try {
                String Wx = getPara("Wx");
                String Wxphone = getPara("Wxphone");
                if(!Wx.equals("") && !Wxphone.equals("")) {
                    Record model = new Record();
                    model.set("wx_id",Wx).set("phone",Wxphone).set("id",id);
                    Db.update("model",model);
                    renderJson("{\"result\":\"保存成功\"}");
                } else {
                    renderJson("{\"result\":\"存在信息\"}");
                }
            } catch (Exception e) {
                throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
            }
    }

    /**
     * 举报
     */
    public void Reports(){
        Integer vid = getParaToInt("vid");
        String Message = getPara("message");
        String uid = getPara("uid");
        String time = DateUtil.Time();
        String serverId = getPara("serverId");
        String openId = getSessionAttr("openid");
        String path = getRequest().getServletContext().getRealPath("Files/User");
        try {
            String role = Db.getSql("overall.access_token");
            Record overa = Db.findFirst(role,"access_token");
            if(!openId.equals(uid)) {
                String SelectInform = Db.getSql("Reports.SelectInform");
                List<Record> reports = Db.find(SelectInform,openId, vid);
                if(reports.size() != 0 && reports != null) {
                    renderJson("{\"result\":\"已经举报过\"}");
                } else {
                    Record report = new Record();
                    report.set("vid",vid).set("Message",Message).set("nickname",openId).set("uid",uid)
                            .set("time",time);
                    Db.save("reports",report);
                    if(!serverId.equals("")) {
                        String[] chrstr = serverId.split(",");
                        for(int j = 0; j < chrstr.length; ++j) {
                            String file = DloadImgUtil.downloadMedia((String) overa.get("price"), chrstr[j].toString(), path, "/model-spring-lm/Files/User/");
                            Record record = new Record();
                            record.set("reid",report.get("id")).set("url",file);
                        }
                    }
                    renderJson("{\"result\":\"举报成功\"}");
                }
            } else {
                renderJson("{\"result\":\"不能举报自己\"}");
            }
        } catch (Exception e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 类型
     */
    public void typeList(){
        String annunciatetype = Db.getSql("annunciatetype.SelectAdd");
        List<Record> records = Db.find(annunciatetype);
        renderJson(records);
    }




}
