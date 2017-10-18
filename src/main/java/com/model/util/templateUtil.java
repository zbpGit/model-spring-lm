package com.model.util;



import net.sf.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by V on 2017/6/7.
 */
public class templateUtil {
    /**
     *
     * @param  first  头部
     * @return JSONObject
     */
    public static JSONObject packJsonmsg(String first, String one, String two, String three){
        JSONObject json=new JSONObject();
        JSONObject jsonFirst=new JSONObject();
        jsonFirst.put("value",first);
        jsonFirst.put("color","#173177");
        json.put("first",jsonFirst);
        JSONObject jsonOrderMoneySum=new JSONObject();
        jsonOrderMoneySum.put("value",one);
        jsonOrderMoneySum.put("color","#173177");
        json.put("keyword1",jsonOrderMoneySum);
        JSONObject json2=new JSONObject();
        json2.put("value",two);
        json2.put("color","#173177");
        json.put("keyword2",json2);
        JSONObject jsonOrderProductName=new JSONObject();
        jsonOrderProductName.put("value",three);
        jsonOrderProductName.put("color","#173177");
        json.put("keyword3",jsonOrderProductName);
        return json;
    }

    public static void sendME(String openid,String theme,String nickname){
        String d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String two="您好，平台收到并核实了您的举报信息，已对违规者及内容进行相关处理。感谢您对平台的支持！";
        JSONObject j=packJsonmsg(" ","",d,"");
        String sss=sendwechatmstToUser(openid,"jSBRFx5-FbGHYJ7xsdTSBIqt-Ma0yjAZBPU_SQKZcNw","http://card.kx51.wang/model-spring-lm/app/releasenotify.html","#444444",j);
    }

    public  static String sendwechatmstToUser(String touser,String template_id,String clickurl,String topcolor,JSONObject data){
        String token=HttpRequester.sendGet("http://card.kx51.wang/model-spring-lm/Picture/JsSdk");
        System.out.println(token.toString());
        JSONObject jasonObject= JSONObject.fromObject(token);
        Map map=(Map)jasonObject;
        String jsapi_ticket= (String) map.get("jsapi_ticket");
        String tmpurl="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        String url=tmpurl.replace("ACCESS_TOKEN",jsapi_ticket);
        JSONObject json=new JSONObject();
        json.put("touser",touser);
        json.put("template_id",template_id);
        json.put("url",clickurl);
        json.put("topcolor",topcolor);
        json.put("data",data);
        System.out.println(json.toString());
        String result=httpsRequest(url,"POST",json.toString());
        System.out.println(result);
        JSONObject resultJson= JSONObject.fromObject(result);
        System.out.println(resultJson);
                String errmsg=(String)resultJson.get("errmsg");
        if(!"ok".equals(errmsg)){
            return errmsg;
        }
        return "success";
    }

    public static String httpsRequest(String requestUrl,String requestMethod,String outputStr){
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            //释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        }catch (ConnectException ce){
            System.out.println("连接超时：{}");
        } catch (Exception e) {
            System.out.println("https请求异常：{}");
            e.printStackTrace();
        }
        return null;
    }

}
