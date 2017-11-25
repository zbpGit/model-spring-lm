//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.model.util;

import com.google.gson.Gson;
import com.jfinal.plugin.activerecord.Record;
import net.sf.json.JSONObject;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class AuthorizationUtil {

    public static String wxLogin() throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
        stringBuffer.append("appid=wx56731a06a3e947e8");
        stringBuffer.append("&redirect_uri=" + URLEncoder.encode("http://www.qingmeng168.com/model-spring-lm/payment/callBack", "UTF-8"));
        stringBuffer.append("&response_type=code");
        stringBuffer.append("&scope=snsapi_userinfo");
        stringBuffer.append("&state=STATE#wechat_redirect");
        String url = stringBuffer.toString();
        return url;
    }

    /**
     * 授权回调取得的参数
     * @return
     * @throws IOException
     */
    public static JSONObject callBack(String code) throws IOException {
        String url = null;
        String infoUrl = null;
        StringBuffer stringBuffer = new StringBuffer();
        //通过之前的Util来请求接口
        stringBuffer.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
        stringBuffer.append("appid=wx56731a06a3e947e8");
        stringBuffer.append("&secret=724f483876d791efdd680841e8c94c2f");
        stringBuffer.append("&code=" + code);
        stringBuffer.append("&grant_type=authorization_code");
        url= stringBuffer.toString();

        JSONObject jsonObject = AuthUtil.doGetJson(url);
        String openid = jsonObject.getString("openid");
        String token = jsonObject.getString("access_token");

        StringBuffer buffer = new StringBuffer();
        buffer.append("https://api.weixin.qq.com/sns/userinfo?");
        buffer.append("access_token="+token);
        buffer.append("&openid="+openid);
        buffer.append("&lang=zh_CN");
        infoUrl = buffer.toString();
        JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
        return userInfo;
    }

    public static Record message(JSONObject infouser) throws UnsupportedEncodingException {
        new Gson();
        String sex = null;
        String openid = infouser.getString("openid");
        String name = EmojiUtil.emojiConvert1(infouser.getString("nickname"));
        Integer gender = Integer.valueOf(infouser.getString("sex"));
        switch(gender.intValue()) {
            case 0:
                sex = "未知";
                break;
            case 1:
                sex = "男";
                break;
            case 2:
                sex = "女";
        }

        String province = infouser.getString("province");
        String city = infouser.getString("city");
        String country = infouser.getString("country");
        String url = infouser.getString("headimgurl");
        String privilege = infouser.getString("privilege");
        Record model = new Record();
        model.set("nickname",openid);
        model.set("hurl",url);
        model.set("name",name);
        model.set("sex",sex);
        model.set("area",city);
        return model;
    }

    public static String Pay(String openid, Integer money, String out_trade_no, String payTwo) throws IOException, SAXException, ParserConfigurationException {
        Gson gson = new Gson();
        String sum = moenyO(money);
        SortedMap<String, Object> map = new TreeMap();
        map.put("appid", "wx56731a06a3e947e8");
        map.put("attach", "美约通告");
        map.put("body", "充值");
        map.put("mch_id", "1480846252");
        map.put("device_info", "WEB");
        map.put("trade_type", "JSAPI");
        map.put("nonce_str", SignUtil.getRandomStringByLength());
        map.put("notify_url", payTwo);
        map.put("out_trade_no", out_trade_no);
        map.put("total_fee", sum);
        map.put("openid", openid);
        String mySign = SignUtil.createSign("UTF-8", map);
        map.put("sign", mySign);
        String xml = XMLUtil.ArrayToXml(map);
        String xx = HttpRequester.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xml);
        Map map1 = XMLUtil.getMapFromXML(xx);
        String prepay_id = (String)map1.get("prepay_id");
        String prepay = "prepay_id=" + prepay_id;
        String nonce_str = SignUtil.getRandomStringByLength();
        String timeStamp = SignUtil.create_timestamp();
        SortedMap<String, Object> sortedMap = new TreeMap();
        sortedMap.put("appId", "wx56731a06a3e947e8");
        sortedMap.put("timeStamp", timeStamp);
        sortedMap.put("nonceStr", nonce_str);
        sortedMap.put("package", prepay);
        sortedMap.put("signType", "MD5");
        String paySign = SignUtil.createSign("UTF-8", sortedMap);
        sortedMap.put("paySign", paySign);
        String pay = gson.toJson(sortedMap);
        return pay;
    }

    public static String moeny(Integer money) {
        String sum = String.valueOf(money.intValue() * 100);
        return sum;
    }

    public static String moenyO(Integer money) {
        String sum = String.valueOf(money.intValue() * 1);
        return sum;
    }

    public static String address(String address) {
        if(address.equals("全国")) {
            address = null;
        }

        return address;
    }

    public static String addressO(String address) {
        if(address.equals("")) {
            address = null;
        }

        return address;
    }

    public static Integer page(Integer page) {
        page = Integer.valueOf(page.intValue() * 10);
        return page;
    }
}
