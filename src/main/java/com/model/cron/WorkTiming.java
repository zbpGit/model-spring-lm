package com.model.cron;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.model.util.DateUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/22.
 */
public class WorkTiming implements Runnable{

    /**
     * 名片定时
     */
    @Override
    public void run() {
        String SelectAll = Db.getSql("wStick.SelectAll");
        List<Record> wStick = Db.find(SelectAll);
        if (wStick != null){
            for (Record record : wStick){
                if (record.get("stopTime").equals("0"))break;
                Long CurrentTime = DateUtil.Time((String) record.get("stopTime"));
                if (CurrentTime < 0L){
                    Record wstick = new Record().set("id",record.get("id")).set("identifying","0");
                    Db.update("wStick","id",wstick);
                    Record work = new Record().set("id",record.get("vid")).set("Stick",0);
                    Db.update("work","id",work);

                    String SelectNext = Db.getSql("wStick.SelectNext");
                    List<Record> Next = Db.find(SelectNext,record.get("vid"));
                    if (Next == null){
                        Record works = new Record().set("id",record.get("vid")).set("wStick",0);
                        Db.update("work","id",works);
                    }else {
                        Record works = new Record().set("id",record.get("vid")).set("wStick",1);
                        Db.update("work","id",works);
                    }
                    String SelectStick = Db.getSql("work.SelectStick");
                    List<Record> workStick = Db.find(SelectStick);
                    if (workStick.size() < 10){
                        String SelectFollowUp = Db.getSql("wStick.SelectFollowUp");
                        List<Record> FollowUp = Db.find(SelectFollowUp);
                        for (Record followup : FollowUp){
                            Record WORK = Db.findById("work","id",followup.get("vid"));
                            if ((Integer)WORK.get("Stick") == 1 && (Integer)WORK.get("exist") == 1)break;
                            String moneyType = Db.getSql("serve.moneyType");
                            Record serve = Db.findFirst(moneyType,followup.get("money"),"2");
                            String expire = DateUtil.Delay((Integer) serve.get("time"));
                            Record ContinueToZhiding = new Record().set("id",followup.get("id")).set("stopTime",expire);
                            Db.update("wStick","id",ContinueToZhiding);
                            Record WorkStick = new Record().set("id",followup.get("vid")).set("Stick",1);
                            Db.update("work","id",WorkStick);
                        }
                    }
                }
            }
        }
    }
}
