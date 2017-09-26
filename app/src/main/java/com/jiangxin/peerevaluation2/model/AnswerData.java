package com.jiangxin.peerevaluation2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */


public class AnswerData {
//    private  static  String[] titles = {"第一", "第二", "第三"};
//    private static   int[] icons = {R.drawable.beiqu, R.drawable.beiyule, R.drawable.bugaoxing};
//    private static   int[] types ={0,2,2};
    private static  List<String> question_from = new ArrayList<>();
    private static  List<Float> question_score = new ArrayList<Float>();
    private static List<String> question_to = new ArrayList<>();



    public void initial(){
        question_from.clear();
        question_score.clear();
        question_to.clear();
        question_from.add("xin");
        question_from.add("xin");
        question_from.add("xin");
        question_to.add("xin");
        question_to.add("jon");
        question_to.add("snow");
        question_score.add((float) 0);
        question_score.add((float) 0);
        question_score.add((float) 0);
    }


    public int size(){
        return question_from.size();
    }

    public static void setAnswer(String from, String to , float score, int position){

        question_from.set(position,from);
        question_to.set(position,to);
        question_score.set(position,score);

    }

    public static List<AnswerItem> getListData() {
        List<AnswerItem> data = new ArrayList<>();

        for ( int i = 0; i <question_from.size()&& i<question_score.size(); i++){
            AnswerItem item = new AnswerItem();
            item.setQuestion_from(question_from.get(i));
            item.setScore(question_score.get(i));
            item.setQuestion_to(question_to.get(i));
            data.add(item);
        }
        return data;
    }




}

