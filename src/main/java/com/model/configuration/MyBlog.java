package com.model.configuration;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log4jLogFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.SqlReporter;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import com.model.interceptors.GlobalInterceptor;
import com.model.routes.FontRoutes;

/**
 * Created by Administrator on 2017/10/10.
 */
public class MyBlog extends JFinalConfig{

    public void configConstant(Constants constants) {
        constants.setDevMode(true);
         /*导入数据库连接*/
        PropKit.use("jdbc.properties");
        //开启日志
        constants.setLogFactory(new Log4jLogFactory());
        SqlReporter.setLog(true);



    }

    public void configRoute(Routes routes) {
        routes.add(new FontRoutes());
    }

    public void configEngine(Engine engine) {
        /*导入函数路径*/
        engine.setBaseTemplatePath(PathKit.getWebRootPath());
    }

    public void configPlugin(Plugins plugins) {
        DruidPlugin druidPlugin =new DruidPlugin(PropKit.get("jdbcUrl"),
                PropKit.get("jdbcUsername"),PropKit.get("jdbcPassword").trim());
        plugins.add(druidPlugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        /*sql的起始文件夹*/
        arp.setBaseSqlTemplatePath(PathKit.getWebRootPath());
        arp.addSqlTemplate("/WEB-INF/sql/model.sql");
        arp.setShowSql(true);
        arp.setDevMode(true);
        plugins.add(arp);
        /*缓存*/
        plugins.add(new EhCachePlugin());
        /*使用任务调度函数*/
        Cron4jPlugin jPlugin = new Cron4jPlugin(PropKit.use("cron4j"),"cron4j");
        plugins.add(jPlugin);
    }

    @Override
    public void afterJFinalStart() {
        System.out.println("model");
    }

    public void configInterceptor(Interceptors interceptors) {
        interceptors.addGlobalActionInterceptor(new GlobalInterceptor());
    }

    public void configHandler(Handlers handlers) {

    }

    public static void main(String[] args) {
        JFinal.start("model-spring-lm/src/main/webapp",80,"/");
    }

}
