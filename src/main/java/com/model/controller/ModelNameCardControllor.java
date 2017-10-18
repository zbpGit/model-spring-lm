package com.model.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.model.interceptors.ErrorInterceptor;
import com.model.util.*;
import com.sun.org.apache.regexp.internal.RE;

import java.awt.dnd.DnDConstants;
import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2017/10/14.
 */
@Before(value = {ErrorInterceptor.class,Tx.class})
public class ModelNameCardControllor extends Controller {

    public void index(){
        renderText("123");
    }

    /**
     * 个人信息
     * @throws UnsupportedEncodingException
     */
    public void ModelMessage() throws UnsupportedEncodingException {
        String  id = getSessionAttr("id");
        String openid = getSessionAttr("openid");
        Record model = Db.findById("model",id);
        model.set("name", EmojiUtil.emojiRecovery2((String) model.get("name")));
        String count = Db.getSql("official.SelectCount");
        Record official = Db.findFirst(count,openid);
        Map<String,Object> map = new TreeMap<String, Object>();
        map.put("model",model);
        map.put("count",official.get("ct"));
        renderJson(map);
    }

    /**
     *信息类型未读数量
     */
    public void ModelCount(){
        String openid = getSessionAttr("openid");
        String count = Db.getSql("official.SelectCount");
        Record official = Db.findFirst(count,openid);
        String like =Db.getSql("enjoy.SelectCount");
        Record like02 = Db.findFirst(like,1,openid);
        String news =Db.getSql("enjoy.SelectCount");
        Record news02 = Db.findFirst(news,2,openid);
        Map map = new TreeMap();
        map.put("system",official.get("ct"));
        map.put("like",like02.get("ct"));
        map.put("news02",news02.get("ct"));
        renderJson(map);
    }

    /**
     * 我的收藏夹
     * 查看收藏的通告
     */
    public void AnnunciateCollect(){
        try {
            String openid = getSessionAttr("openid");
            String SelectAnnunciate = Db.getSql("model_an.SelectAnnunciate");
            List<Record> model_an = Db.find(SelectAnnunciate,openid);
            List oneself = new ArrayList();
            if(model_an != null){
                for (Record record : model_an){
                    Integer vid = Integer.valueOf(record.get("id").toString());
                    String all = Db.getSql("annunciate.SelectAnnunciate");
                    Record annunciate = Db.findFirst(all,vid);
                    if (Integer.valueOf(annunciate.get("audit").toString()) == 1){
                        oneself.add(annunciate);
                    }
                }
            }
            renderJson(oneself);
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 我收藏的通告信息详情
     */
    public void AnnunciateParticulars(){
        Integer vid = getParaToInt("vid");
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
            renderJson(map);
        } catch (Exception e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 查看自己的通告发布
     */
    public void Annunciateexamine(){
        String openid = getSessionAttr("openid");
        String annunciateAll = Db.getSql("annunciate.Annunciateexamine");
        List<Record> annunciateList = Db.find(annunciateAll,openid);
        renderJson(annunciateList);
    }

    /**
     * 查看自己的通告性情
     */
    public void AnnunciateMessage(){
        Integer vid = getParaToInt("vid");
        Map map = new TreeMap();
        try {
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
            renderJson(map);
        } catch (Exception e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 编辑修改自己的通告
     */
    public void Annunciatecompile(){
        String id =getPara("id");
        String serverId = getPara("serverId");
        Integer vid = Integer.valueOf(getPara("vid"));
        String path = getRequest().getServletContext().getRealPath("Files/User");
        try {
            Record annunciate = new Record();
            annunciate.set("worktype",getPara("worktype")).set("deadtime",getPara("deadtime")).set("worktheme",getPara("worktheme"))
                    .set("endtime",getPara("endtime")).set("arealist",getPara("arealist")).set("detailaddr",getPara("detailaddr"))
                    .set("inputcount",getPara("inputcount")).set("ifinterview",getPara("ifinterview")).set("gender",getPara("gender"))
                    .set("price",getPara("price")).set("inputspecify",getPara("inputspecify")).set("starttime",getPara("starttime"))
                    .set("addrmark",getPara("addrmark")).set("contactinfo",getPara("contactinfo"));
            Db.update("annunciate","vid",JsonMessageUtil.updateAnnunciate(vid,annunciate));

            String role = Db.getSql("overall.access_token");
            Record overa = Db.findFirst(role,"access_token");

            String[] viewId = id.split(",");
            String view = Db.getSql("view.PictureUrl");
            List<Record> viewList = Db.find(view,vid);

            if(!id.equals("")) {
                for (int i = 0; i < viewId.length; i++) {
                    for (int k = 0; k < viewList.size(); k++) {
                        if (viewList.get(i).get("id").equals(viewId[i])){
                            viewList.remove(k);
                            --k;
                        }
                    }
                }
                if(viewList != null) {
                    for (Record record :viewList){
                        Db.deleteById("view",record.get("id"));
                    }
                }
                String[] url = serverId.split(",");
                if(!serverId.equals("")) {
                    for (String Url : url){
                       String file = DloadImgUtil.downloadMedia((String) overa.get("price"),Url, path, "/model-spring-lm/Files/User/");
                       Record viewAdd = new Record();
                       viewAdd.set("v_id",vid).set("p_url",file);
                       Db.save("view",viewAdd);
                    }
                }
            } else if(id.equals("")) {
                String DeleteAdd = Db.getSql("view.DeleteAdd");
                Db.update(DeleteAdd,vid);
                String[] url = serverId.split(",");
                if(!serverId.equals("")) {
                    for (String Url : url){
                        String file = DloadImgUtil.downloadMedia((String) overa.get("price"),Url, path, "/model-spring-lm/Files/User/");
                        Record viewAdd = new Record();
                        viewAdd.set("v_id",vid).set("p_url",file);
                        Db.save("view",viewAdd);
                    }
                }
            }
            renderJson("{\"result\":\"success\"}");
        } catch (Exception e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    public void UpdateEnough(){
        try {
            Integer vid = getParaToInt("vid");
            Integer enough = Integer.valueOf(getPara("enough"));
            String UpdateEnough = Db.getSql("annunciate.UpdateEnough");
            Db.update(UpdateEnough,enough,vid);
            renderJson("{\"result\":\"success\"}");
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 删除自己通告信息
     */
    public void ADelete(){
        try {
            Integer vid = getParaToInt("vid");
            String StickDelete = Db.getSql("Stick.SelectCheck");
            Record Stick = Db.findFirst(StickDelete,vid);
            if (Stick != null){
                String StickUpdate = Db.getSql("Stick.UpdateRemove");
                Db.update(StickUpdate,vid);
            }
            String Update = Db.getSql("annunciate.UpdateRemove");
            Db.update(Update,vid);
            renderJson("{\"result\":\"success\"}");
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }

    /**
     * 系统信息
     */
    public void Reports(){
        String openid = getSessionAttr("openid");

    }

    public void File(){
        try {
            File file = new File("/home/tomcat/apache-tomcat-8.0.43/webapps/model-spring-lm/img/index/banner1.jpg");
            String filename = file.getName();
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            InputStream fis = new BufferedInputStream(new FileInputStream("/home/tomcat/apache-tomcat-8.0.43/webapps/model-spring-lm/img/index/banner1.jpg"));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            getResponse().reset();
            getResponse().addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            getResponse().addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(getResponse().getOutputStream());
            getResponse().setContentType("application/octet-stream");
            //getResponse().setContentType("image/jpeg");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            renderNull();
        } catch (IOException e) {
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }


}
