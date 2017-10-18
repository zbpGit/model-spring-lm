package com.model.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.model.interceptors.ErrorInterceptor;

/**
 * Created by Administrator on 2017/10/10.
 */
@Before(value = {ErrorInterceptor.class,Tx.class})
public class AsynchronousControllor extends Controller {

    @Clear
    public void index(){
        setSessionAttr("id","128");
        setSessionAttr("openid","o2u1CxORtqS6tQ7uWy27VvpKFMTQ");
        String url = getPara("Url");
        redirect(url);
    }

}
