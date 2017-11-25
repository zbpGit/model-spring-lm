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
import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2017/10/31.
 */
@Before(value = {ErrorInterceptor.class,Tx.class})
public class WorkControllor extends Controller {

    /**
     * 名片首页
     */
    public void workAll(){
        try {
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
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 名片详情
     * @throws UnsupportedEncodingException
     */
    public void WorkXX() throws UnsupportedEncodingException {
        try {
            Map map = new TreeMap();
            Integer id = getParaToInt("id");
            String UpdateHit = Db.getSql("work.UpdateHit");
            Random random = new Random();
            int s = random.nextInt(10)%(10-1+1) + 1;
            Db.update(UpdateHit,s,id);
            Record work = Db.findById("work","id",id);
            String user = Db.getSql("user.user");
            Record model = Db.findFirst(user,work.get("wid"));
            model.set("name",EmojiUtil.emojiRecovery2(String.valueOf(model.get("name"))));
            String SelectP = Db.getSql("workpicture.SelectP");
            List<Record> workpicture = Db.find(SelectP,id);
            String SelectLike = Db.getSql("enjoy.SelectLike");
            Record enjoy = Db.findFirst(SelectLike,work.get("id"),getSessionAttr("openid"));
            String SelectMo = Db.getSql("model_mo.SelectMo");
            Record modelMo = Db.findFirst(SelectMo,getSessionAttr("openid"),id);
            String SelectNick = Db.getSql("wReports.SelectNick");
            Record wReports = Db.findFirst(SelectNick,getSessionAttr("openid"),id);
            map.put("work", work);
            map.put("model", model);
            map.put("workpicture", workpicture);
            if (modelMo==null){
                map.put("modelMo","未收藏");
            }else {
                map.put("modelMo","已收藏");
            }
            if (wReports == null){
                map.put("Reports","未举报");
            }else {
                map.put("Reports","已收藏");
            }
            if (enjoy == null){
                map.put("like","未点赞");
            }else {
                map.put("like","已点赞");
            }
            renderJson(map);
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }


    /**
     * 自己名片
     */
    public void Wgrouping(){
        try {
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
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 名片删除
     */
    public void WDelete(){
        try {
            Integer id = getParaToInt("id");
            String Identifying= Db.getSql("wStick.Identifying");
            Record wStick = Db.findFirst(Identifying,id);
            if (wStick != null){
                String EndDusinessCard = Db.getSql("wStick.EndDusinessCard");
                Db.update(EndDusinessCard,DateUtil.date(),"0",id);
            }
            Record work = new Record().set("id",id).set("exist",2);
            Db.update("work","id",work);
            renderJson("{\"result\":\"success\"}");
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }

    }

    /**
     * 判断是否添加过名片
     */
    public void workJudge(){
        try {
            String openid = getSessionAttr("openid");
            String work = Db.getSql("work.Judge");
            Record record = Db.findFirst(work,openid);
            if (record != null){
                renderJson(record);
            }else {
                renderJson("{\"result\":\"无名片\"}");
            }
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 我的收藏名片
     */
    public void WorkMo(){
        try {
            String openid = getSessionAttr("openid");
            String Selectmid = Db.getSql("model_mo.Selectmid");
            List<Record> records = Db.find(Selectmid,openid);
            List<Record> recordList = new ArrayList<>();
            for (Record record : records){
                String wp =Db.getSql("work.SelectWP");
                Record recordss = Db.findFirst(wp,record.get("moid"));
                recordList.add(recordss);
            }
            renderJson(recordList);
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }
    /**
     * 名片收藏
     */
    public void workCollect(){
        try {
            Integer id = getParaToInt("id");
            String wid = getPara("wid");
            String openid = getSessionAttr("openid");
            if (!openid.equals(wid)){
                String SelectMo = Db.getSql("model_mo.SelectMo");
                Record record = Db.findFirst(SelectMo,openid,id);
                if (record == null){
                    Record mo = new Record().set("mid",openid).set("moid",id);
                    Db.save("model_mo","mo_id",mo);
                    renderJson("{\"result\":\"收藏成功\"}");
                }else {
                    String DMM = Db.getSql("model_mo.DMM");
                    Db.update(DMM,openid,id);
                    renderJson("{\"result\":\"取消收藏\"}");
                }
            }else {
                renderJson("{\"result\":\"不能收藏自己的名片\"}");
            }
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 删除收藏名片
     */
    public void MultipleChoiceDelete(){
        try {
            String id = getPara("id");
            String openid = getSessionAttr("openid");
            String mid[] = id.split(",");
            String DeleteMoid = Db.getSql("model_mo.DeleteMoid");
            for (String i : mid){
                Db.update(DeleteMoid,openid,Integer.valueOf(i));
            }
            renderJson("{\"result\":\"success\"}");
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
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
            Record work =new Record().set("wid",openid).set("name",getPara("name")).set("sex",getPara("sex")).set("age",getPara("age")).set("region",getPara("region")).set("city", JSONUtil.Cutout(getPara("region")))
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
     * 名片修改
     */
    public void WorkUpdate(){
        try {
            Integer id = getParaToInt("id");
            String wid = getPara("wid");
            String headlineID = getPara("headlineID");
            String headline = getPara("headline");
            String workpictureid = getPara("workpictureid");
            String picture = getPara("picture");
            String openId = getSessionAttr("openid");
            String path = getRequest().getServletContext().getRealPath("Files/work");
            String types = getPara("types");
            if (wid.equals(openId) || wid == null){
                renderJson("{\"result\":\"没有权限修改被人的通告！\"}");
            }
            Record work = new Record().set("id",id).set("wid",openId).set("name",getPara("name")).set("sex",getPara("sex")).set("age",getPara("age")).set("region",getPara("region")).set("city",JsonMessageUtil.Cutout(getPara("region"))).set("stature",getPara("stature"))
                    .set("weight",getPara("weight")).set("surround",getPara("surround")).set("shoe",getPara("shoe")).set("workJob",getPara("workJob")).set("workType",getPara("workType")).set("work",getPara("work"))
                    .set("offer",getPara("offer")).set("describ",getPara("describ")).set("relation",getPara("phone")).set("QQ",getPara("QQ")).set("times",DateUtil.servicer());
            Db.update("work","id",work);

            String SelectP = Db.getSql("workpicture.SelectP");
            List<Record> workpicture = Db.find(SelectP,id);
            String role = Db.getSql("overall.access_token");
            Record overa = Db.findFirst(role,"access_token");

            String DeleteWid = Db.getSql("workpicture.DeleteWid");
            if(workpictureid.equals("") && headlineID.equals("")) {
                if(workpictureid.equals("")) {
                    Db.update(DeleteWid,id,2);
                    if(!picture.equals("")) {
                        String[] url = picture.split(",");
                        String[] typess = types.split(",");
                        for(int i = 0; i < url.length; ++i) {
                            Record workpictures = new Record().set("wid",id).set("picture",url[i]).set("type",2).set("variety",Integer.valueOf(typess[i]));
                            Db.save("workpicture",workpictures);
                        }
                    }
                }
                if(headlineID.equals("")) {
                    Db.update(DeleteWid,id,1);
                    if(!headline.equals("")) {
                        String[] url = headline.split(",");
                        for(int i = 0; i < url.length; ++i) {
                            String file = DloadImgUtil.downloadMedia((String) overa.get("price"), url[i], path, "/model-spring-lm/Files/work/");
                            Record workpictures = new Record().set("wid",id).set("picture",file).set("type",1).set("variety",1);
                            Db.save("workpicture",workpictures);
                        }
                    }
                }
            }else {
                if(!workpictureid.equals("")) {
                    String[] pictureid = workpictureid.split(",");
                    for(int i = 0; i < pictureid.length; ++i) {
                        for(int k = 0; k < workpicture.size(); ++k) {
                            if(Integer.parseInt((String) workpicture.get(k).get("id")) == Integer.parseInt(pictureid[i])) {
                                workpicture.remove(k);
                                --k;
                            }
                        }
                    }
                }

                if(!headlineID.equals("")) {
                    String[] HeadlineID = headlineID.split(",");
                    for(int i = 0; i < HeadlineID.length; ++i) {
                        for(int k = 0; k < workpicture.size(); ++k) {
                            if(Integer.parseInt((String) workpicture.get(k).get("id")) == Integer.parseInt(HeadlineID[i])) {
                                workpicture.remove(k);
                                --k;
                            }
                        }
                    }
                }

                if(workpicture != null) {
                    Iterator workSize = workpicture.iterator();
                    while(workSize.hasNext()) {
                        Record works = (Record) workSize.next();
                        Db.delete("workpicture","id",works);
                    }
                }

                if(!picture.equals("")) {
                    String[] url = picture.split(",");
                    String[] typess = types.split(",");
                    for(int j = 0; j < url.length; ++j) {
                        Record workpictures = new Record().set("wid",id).set("picture", url[j]).set("type",2).set("variety",Integer.parseInt(typess[j]));
                        Db.save("workpicture","id",workpictures);
                    }
                }

                if(!headline.equals("")) {
                    String[] url = headline.split(",");
                    for(int j = 0; j < url.length; ++j) {
                        String file = DloadImgUtil.downloadMedia((String) overa.get("price"), url[j], path, "/model-spring-lm/Files/work/");
                        Record workpictures = new Record().set("wid",id).set("picture",file).set("type",1).set("variety",1);
                        Db.save("workpicture",workpictures);
                    }
                }
            }
            renderJson("{\"result\":\"success\"}");
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
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
            String Dase = picture.substring(23);
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
            System.out.println(type+modelName+modelHeight+modelWeight+modelBust+modelWaist+modelHips+modelShoes);
            String SelectMooke = Db.getSql("mooke.SelectMooke");
            List<Record> mookes = Db.find(SelectMooke,openid,type);
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

    /**
     * 点赞
     */
    public void WorkLike(){
        try {
            String openid = getSessionAttr("openid");
            Integer id = getParaToInt("id");
            String wid = getPara("wid");
            if (!wid.equals(openid)){
                String SelectLike = Db.getSql("enjoy.SelectLike");
                Record enjoys = Db.findFirst(SelectLike,id,openid);
                System.out.println(enjoys);
                if (enjoys != null){
                    renderJson("{\"result\":\"不能重复点赞！\"}");
                }else {
                    Record enjoy = new Record().set("wid",id).set("oneself",wid).set("nickname",openid).set("headline","点赞").set("message","xxxxxx")
                            .set("type",1).set("timestamp",DateUtil.servicer());
                    Db.save("enjoy","id",enjoy);
                    String workAttention = Db.getSql("work.workAttention");
                    Db.update(workAttention,id);
                    renderJson("{\"result\":\"success\"}");
                }
            }else {
                renderJson("{\"result\":\"不能给自己点赞！\"}");
            }
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }


    /**
     * 名片留言
     */
    public void WorkLeave(){
        try {
            String openid = getSessionAttr("openid");
            Integer id = getParaToInt("id");
            String wid = getPara("wid");
            String message = getPara("message");
            if (!wid.equals(openid)){
                Record enjoy = new Record().set("wid",id).set("oneself",wid).set("nickname",openid).set("headline","留言").set("message",message)
                        .set("type",2).set("timestamp",DateUtil.servicer());
                Db.save("enjoy","id",enjoy);
                renderJson("{\"result\":\"success\"}");
            }else {
                renderJson("{\"result\":\"不能给自己留言\"}");
            }
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 举报名片
     */
    public void ReportABusinessCard(){
        try {
            Integer id =getParaToInt("id");
            String massage = getPara("massage");
            String serviceId = getPara("serviceId");
            String wid = getSessionAttr("wid");
            String openid = getSessionAttr("openid");
            if (wid.equals(openid)){
                renderJson("{\"result\":\"不能举报自己！\"}");
            }
            String SelectNick = Db.getSql("wReports.SelectNick");
            Record record = Db.findFirst(SelectNick,openid,id);
            if (record !=null){
                renderJson("{\"result\":\"不能重复举报！\"}");
            }
            Record wreports = new Record().set("vid",id).set("Message",massage).set("nickname",openid).set("uid",DateUtil.date());
            Db.save("wreports","id",wreports);
            if (serviceId != null){
                String role = Db.getSql("overall.access_token");
                Record overa = Db.findFirst(role,"access_token");
                String path = getRequest().getServletContext().getRealPath("Files/work");
                String[] chrstr = serviceId.split(",");
                for (String i : chrstr){
                    String file = DloadImgUtil.downloadMedia((String) overa.get("price"), i, path, "/model-spring-lm/Files/work/");
                    Record wview = new Record().set("reid",Integer.valueOf((String) wreports.get("id"))).set("url",file);
                    Db.save("wview","id",wview);
                }
            }
            renderJson("{\"result\":\"success\"}");
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 获取设置信息
     */
    public void WorkSystem(){
        try {
            String id = getSessionAttr("id");
            Record model = Db.findById("model","id",Integer.valueOf(id));
            model.set("name",EmojiUtil.emojiRecovery2(String.valueOf(model.get("name"))));
            renderJson(model);
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     *设置
     */
    public void WorkSetting(){
        try {
            Integer Guestbook = getParaToInt("Guestbook");
            Integer praise = getParaToInt("praise");
            Record model = new Record().set("id",Integer.valueOf((String) getSessionAttr("id")))
                    .set("Guestbook",Guestbook).set("praise",praise);
            Db.update("model","id",model);
            renderJson("{\"result\":\"success\"}");
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 下载模卡
     */
    public void Download() {
        String Paht = "/home/java/apache-tomcat-8.0.43/webapps" + getPara("path");
        try {
            File file = new File(Paht);
            String filename = file.getName();
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            InputStream fis = new BufferedInputStream(new FileInputStream(Paht));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            getResponse().reset();
            getResponse().addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            getResponse().addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(getResponse().getOutputStream());
            getResponse().setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            renderNull();
        } catch (IOException e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }

    }

    /**
     *留言
     */
    public void LeaveAMessage(){
        try {
            String openid = getSessionAttr("openid");
            String wid = getPara("wid");
            String message = getPara("message");
            if (!wid.equals(openid)){
                String exist = Db.getSql("conversation.exist");
                Record conversation = Db.findFirst(exist,openid,wid);
                Record ct = Db.findFirst(exist,wid,openid);
                if (conversation != null || ct != null){
                    if (conversation != null){
                        Record DialogueSave = new Record().set("cid",Integer.valueOf((Integer) conversation.get("id"))).set("user",openid).set("content",message)
                                .set("SendTime",DateUtil.date());
                        Db.save("dialogue",DialogueSave);
                    }else {
                        Record DialogueSave = new Record().set("cid",(Long) ct.get("id")).set("user",openid).set("content",message)
                                .set("SendTime",DateUtil.date());
                        Db.save("dialogue",DialogueSave);
                    }
                }else {
                    Record ConversationSave =new Record().set("sender",openid).set("recipient",wid).set("StartTime",DateUtil.date());
                    Db.save("conversation",ConversationSave);
                    Record DialogueSave = new Record().set("cid",(Long) ConversationSave.get("id")).set("user",openid).set("content",message)
                            .set("SendTime",DateUtil.date());
                    Db.save("dialogue",DialogueSave);
                }
                renderJson("{\"result\":\"success\"}");
            }else {
                renderJson("{\"result\":\"不能给自己留言\"}");
            }
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }

    }

}
