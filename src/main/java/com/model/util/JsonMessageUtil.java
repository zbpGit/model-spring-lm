package com.model.util;

import com.jfinal.plugin.activerecord.Record;

import java.util.Map;

public class JsonMessageUtil {

    /**
     * 编辑发送通告详情
     * @param map
     * @param openid
     * @return
     */
    public static Record annunciatemessage(Record map, String openid) {
        System.out.println(map);
        String worktype = map.get("worktype");
        String date = DateUtil.servicer();
        String deadtime = map.get("deadtime");
        String worktheme = map.get("worktheme");
        String starttime = map.get("starttime");
        String cndtime = map.get("endtime");
        String arealist = map.get("arealist");
        String site = Cutout(arealist);
        String detailaddr = map.get("detailaddr");
        Integer inputcount = Integer.valueOf(String.valueOf(map.get("inputcount")));
        String ifinterview = map.get("ifinterview");
        String gender = map.get("gender");
        String price = map.get("price");
        String inputspecify = map.get("inputspecify");
        String contact = map.get("contactinfo");
        System.out.println(contact);
        Record annunciate = new Record();
        annunciate.set("work_type",worktype);
        annunciate.set("deadline",date);
        annunciate.set("abortTime",deadtime);
        annunciate.set("theme",worktheme);
        annunciate.set("work_time",starttime);
        annunciate.set("work_finish",cndtime);
        annunciate.set("workplace",arealist);
        annunciate.set("site",site);
        annunciate.set("information",detailaddr);
        annunciate.set("asex",gender);
        annunciate.set("number",inputcount);
        annunciate.set("interview",ifinterview);
        annunciate.set("price",price);
        annunciate.set("details",inputspecify);
        annunciate.set("uid",openid);
        annunciate.set("audit",Integer.valueOf(1));
        annunciate.set("contact",contact);
        return annunciate;
    }

   public static Record updateAnnunciate(Integer vid, Record map) {
        String worktype = map.get("worktype");
        String date = DateUtil.Time();
        String deadtime = map.get("deadtime");
        String worktheme = map.get("worktheme");
        String cndtime = map.get("endtime");
        String arealist = map.get("arealist");
        String site = Cutout(arealist);
        String detailaddr = map.get("detailaddr");
        Integer inputcount = Integer.valueOf(String.valueOf(map.get("inputcount")));
        String ifinterview = map.get("ifinterview");
        String gender = map.get("gender");
        String price = map.get("price");
        String inputspecify = map.get("inputspecify");
        String Work_time = map.get("starttime");
        String contact = map.get("contactinfo");
        Record annunciate = new Record();
        annunciate.set("vid",vid);
        annunciate.set("work_type",worktype);
        annunciate.set("deadline",date);
        annunciate.set("abortTime",deadtime);
        annunciate.set("theme",worktheme);
        annunciate.set("work_time",Work_time);
        annunciate.set("work_finish",cndtime);
        annunciate.set("workplace",arealist);
        annunciate.set("site",site);
        annunciate.set("information",detailaddr);
        annunciate.set("asex",gender);
        annunciate.set("number",inputcount);
        annunciate.set("interview",ifinterview);
        annunciate.set("price",price);
        annunciate.set("details",inputspecify);
        annunciate.set("contact",contact);
        return annunciate;
    }


    public static String Cutout(String arealist) {
        String[] arr = arealist.split(" ");
        return arr[1];
    }
}
