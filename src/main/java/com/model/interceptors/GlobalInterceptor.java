package com.model.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/7/6.
 */
public class GlobalInterceptor implements Interceptor{

    public void intercept(Invocation invocation) {
        HttpSession session = invocation.getController().getSession();
        String url = invocation.getActionKey();
        if (session == null){
            System.out.println("没有session");
            invocation.getController().redirect("/asynchronous/index?Url="+url);
        }else {
            String nickname = (String) session.getAttribute("openid");
            if (nickname != null){
                invocation.invoke();
            }else {
                System.out.println("没有session");
                invocation.getController().redirect("/asynchronous/index?Url="+url);
            }
        }
    }
}
