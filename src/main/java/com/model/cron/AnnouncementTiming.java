package com.model.cron;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.model.interceptors.ErrorInterceptor;
import com.model.util.DateUtil;
import com.model.util.UnifyThrowEcxp;

import java.util.List;

/**
 * Created by Administrator on 2017/11/20.
 */
@Before(value = {ErrorInterceptor.class,Tx.class})
public class AnnouncementTiming implements Runnable {

    /**
     * 通告定时
     */
    @Override
    public void run() {
        try {
            String SelectAll = Db.getSql("Stick.SelectAll");
            List<Record> all = Db.find(SelectAll);
            if (all!=null){
                for (Record record : all){
                    if (record.get("stopTime").equals("0") || record.get("stopTime").equals("官方")) break;
                    Long CurrentTime = DateUtil.Time((String) record.get("stopTime"));
                    if (CurrentTime < 0L){
                        Record stick = new Record().set("id",record.get("id")).set("identifying","0");
                        Db.update("Stick","id",stick);
                        Record annunciate = new Record().set("vid",record.get("vid")).set("top","0");
                        Db.update("annunciate","vid",annunciate);
                        String SelectCheck = Db.getSql("Stick.SelectCheck");
                        Record stickCheck = Db.findFirst(SelectCheck,record.get("vid"));
                        if (stickCheck == null){
                            Record annunciateAwait = new Record().set("vid",record.get("vid")).set("await","0");
                            Db.update("annunciate","vid",annunciateAwait);
                        }
                        String QuantityArea = Db.getSql("annunciate.QuantityArea");
                        Record count = Db.findFirst(QuantityArea,record.get("sitz"));

                        String SelectLately = Db.getSql("annunciate.SelectLately");
                        List<Record> stickLately = Db.find(SelectLately,record.get("sitz"));
                        if ((Integer)count.get("quantity")<5){
                            for (Record lately :stickLately){
                                Record Annunciate = Db.findById("annunciate","vid",lately.get("vid"));
                                if (Annunciate.get("top").equals("1") && Annunciate.get("remove").equals("0") && Annunciate.get("audit").equals("1"))break;
                                String moneyType = Db.getSql("serve.moneyType");
                                Record serve = Db.findFirst(moneyType,lately.get("money"),"1");
                                String expire = DateUtil.Delay((Integer) serve.get("time"));
                                Record ContinueToZhiding = new Record().set("id",lately.get("id")).set("stopTime",expire);
                                Db.update("stick","id",ContinueToZhiding);
                                Record annunciates = new Record().set("vid",record.get("vid")).set("top","0");
                                Db.update("annunciate","vid",annunciates);
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException(UnifyThrowEcxp.throwExcp(e));
        }
    }
}
