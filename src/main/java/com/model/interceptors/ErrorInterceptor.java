package com.model.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.JFinal;
import com.model.controller.ModelNameCardControllor;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/8/5.
 * 异常拦截器
 */
public class ErrorInterceptor implements Interceptor {

    private static Logger logger = Logger.getLogger(ErrorInterceptor.class);

    public void intercept(Invocation invocation) {
        try {
            invocation.invoke();
        } catch (Exception e) {
            logger(invocation,e);
            JSONObject jo = JSONObject.parseObject(e.getMessage());
            invocation.getController().renderJson(jo.toJSONString());
        }
    }

    private void logger(Invocation inv, Exception e) {
        //开发模式
        if (JFinal.me().getConstants().getDevMode()){
            e.printStackTrace();
        }
        StringBuilder sb =new StringBuilder("\n---Exception Log Begin---\n");
        sb.append("Controller:").append(inv.getController().getClass().getName()).append("\n");
        sb.append("Date:").append(String.valueOf(new Date())).append("\n");
        sb.append("Method:").append(inv.getMethodName()).append("\n");
        sb.append("Exception Type:").append(e.getClass().getName()).append("\n");
        sb.append("Exception Details:");
        logger.error(sb.toString(),e);
    }
}
