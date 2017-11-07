package com.model.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.model.interceptors.ErrorInterceptor;
import com.model.util.*;
import org.apache.commons.lang.text.StrTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.StreamHandler;

/**
 * Created by Administrator on 2017/10/31.
 */
@Before(value = {ErrorInterceptor.class,Tx.class})
public class WorkControllor extends Controller {

    /**
     * 名片首页
     */
    public void workAll(){
        Integer page = getParaToInt("page");//当前页数
        String region = getPara("region");//地区
        String workJob = getPara("workJob");//风格标签
        String workType = getPara("workType");//工作标签
        String rank = getPara("rank");//排序
        Map<String,Object> map = new TreeMap<>();
        if (page == 1){
            List<Record> records = new ArrayList<>();
            String SelectStick = Db.getSql("work.SelectStick");
            List<Record> record = Db.find(SelectStick);
            for (Record record1 :record){
                String SelectWP = Db.getSql("work.SelectWP");
                Record record2 = Db.findFirst(SelectWP,record1.get("id"));
                records.add(record2);
            }
            map.put("workStick",records);
        }
        List<Record> works = new ArrayList<>();
        Kv kv = Kv.by("region",JSONUtil.judge(region)).set("workJob",JSONUtil.judge(workJob)).set("workType",JSONUtil.judge(workType)).set("rank",rank);
        SqlPara para = Db.getSqlPara("work.SelectMo",kv);
        Page<Record> pages = Db.paginate(page,10,para);
        Record pageO = new Record();
        pageO.set("PageNumber",pages.getPageNumber()).set("TotalPage",pages.getTotalPage());
        map.put("page",pageO);
        for (Record record :pages.getList()){
            String s = Db.getSql("work.SelectWP");
            Record record1 = Db.findFirst(s,record.get("id"));
            works.add(record1);
        }
        map.put("work",works);
        renderJson(map);
    }

    /**
     * 自己名片
     */
    public void Wgrouping(){
        String openid = getSessionAttr("openid");
        String nickName = Db.getSql("work.workNickName");
        List<Record> records = Db.find(nickName,openid);
        List<Record> recordList = new ArrayList<>();
        for (Record record : records){
            String wp =Db.getSql("work.SelectWP");
            Record recordss = Db.findFirst(wp,record.get("id"));
            recordList.add(recordss);
        }
        renderJson(recordList);
    }

    /**
     * 判断是否添加过名片
     */
    public void workJudge(){
        String openid = getSessionAttr("openid");
        String work = Db.getSql("work.Judge");
        Record record = Db.findFirst(work,openid);
        if (record != null){
            renderJson(record);
        }else {
            renderJson("{\"result\":\"无名片\"}");
        }
    }

    /**
     * 我的收藏名片
     */
    public void WorkMo(){
        String openid = getSessionAttr("openid");
        String Selectmid = Db.getSql("work.Selectmid");
        List<Record> records = Db.find(Selectmid,openid);
        List<Record> recordList = new ArrayList<>();
        for (Record record : records){
            String wp =Db.getSql("work.SelectWP");
            Record recordss = Db.findFirst(wp,record.get("id"));
            recordList.add(recordss);
        }
        renderJson(recordList);
    }

    /**
     * 名片收藏
     */
    public void workCollect(){
        Integer id = getParaToInt("id");
        String wid = getPara("wid");
        String openid = getSessionAttr("openid");
        if (!openid.equals(wid)){
            String SelectMo = Db.getSql("model_mo.SelectMo");
            Record record = Db.findFirst(SelectMo,openid,id);
            if (record == null){
                Record mo = new Record().set("mid",openid).set("moid",id);
                Db.save("model_mo",mo);
                renderJson("{\"result\":\"收藏成功\"}");
            }else {
                String DMM = Db.getSql("model_mo.DMM");
                Db.update(DMM,openid,id);
                renderJson("{\"result\":\"取消收藏\"}");
            }
        }else {
            renderJson("{\"result\":\"不能收藏自己的名片\"}");
        }
    }

    /**
     * 名片添加
     */
    public void WorkSave(){
        try {
            String path = getRequest().getServletContext().getRealPath("Files/work");
            String picture = getPara("picture");
            String types = getPara("types");
            String headline = getPara("headline");
            String openid = getSessionAttr("openid");
            Record work =new Record().set("wid",openid).set("name",getPara("name")).set("age",getPara("age")).set("region",getPara("region")).set("city", JSONUtil.Cutout(getPara("region")))
                    .set("stature",getPara("stature")).set("weight",getPara("weight")).set("surround",getPara("surround")).set("shoe",getPara("shoe")).set("workJob",getPara("workJob"))
                    .set("workType",getPara("workType")).set("work",getPara("work")).set("offer",getPara("offer")).set("describ",getPara("describ")).set("relation",getPara("phone"))
                    .set("QQ",getPara("QQ")).set("times", DateUtil.servicer());
            Db.save("work",work);
            if(!headline.equals("")){
                String role = Db.getSql("overall.access_token");
                Record overa = Db.findFirst(role,"access_token");
                String chrstr[] = headline.split(",");
                for (String i :chrstr){
                    String file = DloadImgUtil.downloadMedia((String) overa.get("price"), i, path, "/model-spring-lm/Files/work/");
                    Record workpicture = new Record();
                    workpicture.set("wid",work.get("id")).set("picture",file).set("type",1).set("variety",1);
                    Db.save("workpicture",workpicture);
                }
            }
            if (!picture.equals("")){
                String chrstr[] = picture.split(",");
                String[] typess = types.split(",");
                for (int j = 0;j<chrstr.length;j++){
                    Record workpicture = new Record();
                    workpicture.set("wid",work.get("id")).set("picture",chrstr[j]).set("type",2).set("variety",Integer.valueOf(typess[j]));
                    Db.save("workpicture",workpicture);
                }
            }
            renderJson("{\"result\":\"success\"}");
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 名片删除
     */
    public void WDelete(){
        Integer id = getParaToInt("id");

    }

    /**
     * 模卡保存
     */
    public void workpicture(){
        try {
            String openid = getSessionAttr("openid");
            Integer id = getParaToInt("id");
            Integer type = getParaToInt("type");
            String picture = getPara("picture");
            String Dase = picture.substring(22);
            String path = getRequest().getServletContext().getRealPath("Files/mooke");

            String SelectIndex = Db.getSql("mooke.SelectIndex");
            Record mooke = Db.findFirst(SelectIndex,type,id,openid);
            if (mooke == null){
                String url = DaseUtil.generateImage(Dase, path + "/" + SignUtil.random(5) + ".jpg");
                Record mookes = new Record().set("nickname",openid).set("type",type).set("subscript",id).set("path",url);
                Db.save("mooke",mookes);
            }else {
                String url = DaseUtil.generateImage(Dase, path + "/" + SignUtil.random(5) + ".jpg");
                Record mookes = new Record().set("id",mooke.get("id")).set("path",url);
                Db.update("mooke",mookes);
            }
            renderJson("{\"result\":\"success\"}");
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 拼接模卡
     */
    public void joint(){
        try {
            String path = getRequest().getServletContext().getRealPath("Files/mooke");
            String openid = getSessionAttr("openid");
            Integer type = getParaToInt("type");
            String modelName = getPara("modelName");
            String modelHeight = getPara("modelHeight");
            String modelWeight = getPara("modelWeight");
            String modelBust = getPara("modelBust");
            String modelWaist = getPara("modelWaist");
            String modelHips = getPara("modelHips");
            String modelShoes = getPara("modelShoes");

            String SelectMooke = Db.getSql("mooke.SelectMooke");
            List<Record> mookes = Db.find(SelectMooke,openid,2);
            String render = null;
            if(type.intValue() == 5) {
                render = MergeUtil.ThreadMK(mookes, type, path, modelName, modelHeight, modelWeight, modelBust, modelWaist, modelHips, modelShoes);
            } else if(type.intValue() == 7) {
                render = MergeUtil.ThreadMK(mookes, type, path, modelName, modelHeight, modelWeight, modelBust, modelWaist, modelHips, modelShoes);
            } else if(type.intValue() == 9) {
                render = MergeUtil.ThreadMK(mookes, type, path, modelName, modelHeight, modelWeight, modelBust, modelWaist, modelHips, modelShoes);
            } else if(type.intValue() == 11) {
                render = MergeUtil.ThreadMK(mookes, type, path, modelName, modelHeight, modelWeight, modelBust, modelWaist, modelHips, modelShoes);
            }
            renderJson("{\"result\":\""+render+"\"}");
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    public void WorkUpdate(){
        try {
            Integer id = getParaToInt("id");
            String headlineID = getPara("headlineID");
            String headline = getPara("headline");
            String workpictureid = getPara("workpictureid");
            String picture = getPara("picture");
            String openId = getSessionAttr("openid");
            String path = getRequest().getServletContext().getRealPath("Files/work");
            String types = getPara("types");

            Record work = new Record().set("id",id).set("wid",openId).set("name",getPara("name")).set("age",getPara("age")).set("region",getPara("region")).set("city",JsonMessageUtil.Cutout(getPara("region"))).set("stature",getPara("stature"))
                    .set("weight",getPara("weight")).set("surround",getPara("surround")).set("shoe",getPara("shoe")).set("workJob",getPara("workJob")).set("workType",getPara("workType")).set("work",getPara("work"))
                    .set("offer",getPara("offer")).set("describ",getPara("describ")).set("relation",getPara("phone")).set("QQ",getPara("QQ")).set("times",DateUtil.servicer());
            Db.update("work","id",work);

        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }





}
