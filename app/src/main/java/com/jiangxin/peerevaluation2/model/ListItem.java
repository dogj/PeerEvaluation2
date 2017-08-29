package com.jiangxin.peerevaluation2.model;

/**
 * Created by Administrator on 2017/1/9.
 */


public class ListItem {
    private String titles;
    private int imageResId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public int getImageResId() {

        return imageResId;
    }

    public String getTitles() {
        return titles;
    }
}
