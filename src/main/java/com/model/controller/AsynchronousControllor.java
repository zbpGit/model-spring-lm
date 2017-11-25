package com.model.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.model.interceptors.ErrorInterceptor;
import com.model.util.SignUtil;
import com.model.util.UnifyThrowEcxp;
import com.model.util.XMLUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

import static com.model.util.AuthUtil.setXml;

/**
 * Created by Administrator on 2017/10/10.
 */
@Before(value = {ErrorInterceptor.class,Tx.class})
public class AsynchronousControllor extends Controller {

    @Clear
    public void index(){
        setSessionAttr("id","128");
        setSessionAttr("openid","o2u1CxORtqS6tQ7uWy27VvpKFMTQ");
        System.out.println(getSessionAttr("id"));
        System.out.println(getSessionAttr("openid"));
        String url = getPara("Url");
        renderNull();
    }


    /**
     * 通告支付回调
     */
    public void syntony() throws IOException, ParserConfigurationException, SAXException {
        String resXml = null;
        //读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = getRequest().getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        //解析xml成map
        Map<String, Object> m = new HashMap<String, Object>();
        m = XMLUtil.getMapFromXML(sb.toString());

        //过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = (String) m.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 账号信息
        String key = "q224p9mz75249r7kerl9zz6ycawi17az"; // key
        //判断签名是否正确
        if (SignUtil.isTenpaySign("UTF-8", packageParams, key)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            try {
                if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
                    String  success = null;
                    // 这里是支付成功，返回参数
                    resXml = setXml("SUCCESS", "OK");
                    //////////执行自己的业务逻辑////////////////
                    String out_trade_no = (String) packageParams.get("out_trade_no");

                    String SelectExist = Db.getSql("Stick.SelectExist");
                    Record Stick = Db.findFirst(SelectExist,out_trade_no);
                    String moneyType = Db.getSql("serve.moneyType");
                    Record serve = Db.findFirst(moneyType,Stick.get("money"),"1");
                    if (Stick != null){
                        if (Stick.get("stopTime").equals("官方")){
                            Record Sticks = new Record().set("id",Integer.valueOf((Integer) Stick.get("id")))
                                    .set("payment","1").set("identifying","2");
                            Db.update("Stick","id",Sticks);
                            String UpdateOficial = Db.getSql("official.UpdateOficial");
                            Db.update(UpdateOficial,"亲爱的用户，你充值的" + serve.get("details") + ",充值成功！",out_trade_no);
                        }else if (!Stick.get("stopTime").equals("0")){
                            Record Sticks = new Record().set("id",Integer.valueOf((Integer)Stick.get("id")))
                                    .set("payment","1").set("identifying","1");
                            Db.update("Stick","id",Sticks);
                            String UpdateOficial = Db.getSql("official.UpdateOficial");
                            Db.update(UpdateOficial,"亲爱的用户，你充值的" + serve.get("details") + ",充值成功！正在置顶！",out_trade_no);
                        }else if (Stick.get("stopTime").equals("0")){
                            String SelectCheck = Db.getSql("Stick.SelectCheck");
                            Record SStick = Db.findFirst(SelectCheck,(Integer)Stick.get("vid"));
                            String SelectLater = Db.getSql("Stick.SelectLater");
                            Record SSStick = Db.findFirst(SelectLater,Stick.get("sitz"));
                            Integer count = (Integer) SSStick.get("quantity");
                            if (SStick == null){
                                if(count > 5) {
                                    success = serve.get("details")  + "充值成功,本地区您是下位置顶的用户！";
                                } else {
                                    success = serve.get("details") + "充值成功,本地区前面还有" + count + "个用户！";
                                }
                            }else if(count < 5) {
                                success = serve.get("details") + "续费充值成功,本地区您是下位置顶的用户！";
                            } else if(count > 5) {
                                success = serve.get("details") + "续费充值成功,本地区前面还有" + count + "个用户！";
                            }
                            Record Sticks = new Record().set("id",Integer.valueOf((Integer)Stick.get("id")))
                                    .set("payment","1").set("identifying","1");
                            Db.update("Stick","id",Sticks);
                            String UpdateOficial = Db.getSql("official.UpdateOficial");
                            Db.update(UpdateOficial,success,out_trade_no);
                            Record annunciate = new Record().set("vid",(Integer)Stick.get("vid")).set("await","1");
                            Db.update("annunciate","vid",annunciate);
                        }
                    }
                }else {
                    resXml =  setXml("fail", "微信返回的交易状态不正确（result_code=" + "SUCCESS" + "）");
                }
            }catch (Exception e){
                throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
            }
        } else {
            resXml =  setXml("fail", "签名失败");
        }
        //------------------------------
        //处理业务完毕
        //------------------------------
        BufferedOutputStream out = new BufferedOutputStream(getResponse().getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    /**
     * 名片支付回调
     * @throws Exception
     */
    public void workY() throws Exception {
        String resXml = null;
        //读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = getRequest().getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        System.out.println("回调参数="+sb.toString());
        //解析xml成map
        Map<String, Object> m = new HashMap<String, Object>();
        m = XMLUtil.getMapFromXML(sb.toString());

        //过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = (String) m.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 账号信息
        String key = "q224p9mz75249r7kerl9zz6ycawi17az"; // key
        //判断签名是否正确
        if (SignUtil.isTenpaySign("UTF-8", packageParams, key)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            try {
                if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
                    String  success = null;
                    // 这里是支付成功，返回参数
                    resXml = setXml("SUCCESS", "OK");
                    //////////执行自己的业务逻辑////////////////
                    String out_trade_no = (String) packageParams.get("out_trade_no");

                    String SelectOut = Db.getSql("wStick.SelectOut");
                    Record wStick = Db.findFirst(SelectOut,out_trade_no);
                    String moneyType = Db.getSql("serve.moneyType");
                    Record serve = Db.findFirst(moneyType,out_trade_no,"2");
                    if (wStick !=null){
                        if (!wStick.get("stopTime").equals("0")){
                            String UpdateOut = Db.getSql("wStick.UpdateOut");
                            Db.update(UpdateOut,"1","1",out_trade_no);
                            Record work = new Record().set("id",(Integer)wStick.get("vid")).set("Stick",1);
                            Db.update("work","id",work);
                            String UpdateOficial = Db.getSql("official.UpdateOficial");
                            Db.update(UpdateOficial,"亲爱的用户，你充值的" + serve.get("details") + ",充值成功！正在置顶！",out_trade_no);
                        }else if (wStick.get("stopTime").equals("0")){
                            String UpdateOut = Db.getSql("wStick.UpdateOut");
                            Db.update(UpdateOut,"1","1",out_trade_no);
                            Record work = new Record().set("id",(Integer)wStick.get("vid")).set("wStick",1);
                            Db.update("work","id",work);
                            String UpdateOficial = Db.getSql("official.UpdateOficial");
                            Db.update(UpdateOficial,"亲爱的用户，你充值的" + serve.get("details") + ",充值成功！正在置顶！",out_trade_no);
                        }
                    }
                }else {
                    resXml =  setXml("fail", "微信返回的交易状态不正确（result_code=" + "SUCCESS" + "）");
                }
            }catch (Exception e){
                throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
            }
        } else {
            resXml =  setXml("fail", "签名失败");
        }
        //------------------------------
        //处理业务完毕
        //------------------------------
        BufferedOutputStream out = new BufferedOutputStream(getResponse().getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

}
