package com.jiangxin.peerevaluation2.model;

/**
 * Created by Administrator on 2017/1/9.
 */


public class QuestionItem {
    private String name;
    private String  id;
    private float type;

    public float getType() {
        return type;
    }

    public void setType(float type) {this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
