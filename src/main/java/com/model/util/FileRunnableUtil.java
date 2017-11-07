package com.model.util;

import com.jfinal.plugin.activerecord.Record;

import java.util.List;

import static com.model.util.MergeUtil.montage;

/**
 * Created by Administrator on 2017/8/10.
 */
public class FileRunnableUtil implements Runnable {


    private List<Record> mookes;

    private Integer type;

    private String path;

    private String[] price;

    public List<Record> getMookes() {
        return mookes;
    }

    public void setMookes(List<Record> mookes) {
        this.mookes = mookes;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] getPrice() {
        return price;
    }

    public void setPrice(String[] price) {
        this.price = price;
    }

    public FileRunnableUtil(List<Record> mookes, Integer type, String path) {
        this.mookes = mookes;
        this.type = type;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            price = montage(mookes,type,path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
