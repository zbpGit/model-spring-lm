package com.model.controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/10/14.
 */
public class DetailControllor extends Controller {

    public void TypeAdd(){
        String mold = Db.getSql("workType.mold");
        List<Record> mold1 = Db.find(mold,0);
        List<Record> mold2 = Db.find(mold,1);
        List<Record> mold3 = Db.find(mold,2);
        Map<String,Object> map = new TreeMap<String, Object>();
        map.put("0",mold1);
        map.put("1",mold2);
        map.put("2",mold3);
        renderJson(map);
    }

}
