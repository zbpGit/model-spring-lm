package com.model.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * Created by Administrator on 2017/8/5.
 * 异常拦截器
 */
public class ErrorInterceptor implements Interceptor {
    public void intercept(Invocation invocation) {
        try {
            invocation.invoke();
        } catch (Exception e) {
            JSONObject jo = JSONObject.parseObject(e.getMessage());
            invocation.getController().renderJson(jo.toJSONString());
        }
    }
}
