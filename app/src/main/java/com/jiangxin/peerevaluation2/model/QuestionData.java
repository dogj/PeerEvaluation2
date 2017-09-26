package com.jiangxin.peerevaluation2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */


public class QuestionData {
//    private  static  String[] titles = {"第一", "第二", "第三"};
//    private static   int[] icons = {R.drawable.beiqu, R.drawable.beiyule, R.drawable.bugaoxing};
//    private static   int[] types ={0,2,2};
    private static  List<String> question_name = new ArrayList<>();
    private static  List<String> question_id = new ArrayList<>();
    private static List<String> question_type = new ArrayList<>();
    private static List<Float> question_score = new ArrayList<Float>();



    public void initial(){
        question_name.clear();
        question_id.clear();
        question_type.clear();
        question_name.add("xin");
        question_name.add("jon");
        question_name.add("snow");
        question_id.add("IT7374");
        question_id.add("IT7374");
        question_id.add("IT7374");
        question_type.add("0");
        question_type.add("0");
        question_type.add("0");
        question_score.add((float) 0);
        question_score.add((float) 0);
        question_score.add((float) 0);
    }

//    public static void adddata(){
//
//        question_name.add("test");
//        question_id.add("IT7377");
//
//    }

//    public static void adddata(String name, String id){
//        question_name.add(name);
//        question_id.add(id);
//
//    }

    public static void setAnswer(String from, String to , float score, int position){

        question_name.set(position,from);
        question_id.set(position,to);
        question_type.set(position, String.valueOf(score));

    }


    public static void remove(int position){
       question_name.remove(position);
        question_type.remove(position);
    }

    public int size(){
        return question_name.size();
    }

    public static GroupItem getListData(int position){
            GroupItem item2 = new GroupItem();
            item2.setName(question_name.get(position));
            item2.setId(question_id.get(position));
        return item2;
    }


    public static List<QuestionItem> getListData() {
        List<QuestionItem> data = new ArrayList<>();

        for ( int i = 0; i <question_name.size()&& i<question_id.size(); i++){
            QuestionItem item = new QuestionItem();
            item.setName(question_name.get(i));
            item.setType(Float.parseFloat(question_type.get(i)));
            data.add(item);
        }
        return data;
    }




}

