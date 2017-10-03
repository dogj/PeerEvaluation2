package com.jiangxin.peerevaluation2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */


public class QuestionData {

    private static  List<String> question_name = new ArrayList<>();
    private static  List<String> question_id = new ArrayList<>();
    private static List<String> question_type = new ArrayList<>();


    private static List<QuestionItem> data;




    public static void adddata(String name){
        question_name.add(name);
    }


    public static void remove(int position){
        question_name.remove(position);
        question_type.remove(position);
        question_id.remove(position);

    }

    public static int size(){
        return question_name.size();
    }

    public static void clear(){
        question_name.clear();
        question_type.clear();
        question_id.clear();
    }


    public static List<QuestionItem> getListData() {

        if (data==null){

            data = new ArrayList<>();}
        else {
            data.clear();
        }

        for ( int i = 0; i <question_name.size(); i++){
            QuestionItem item = new QuestionItem();
            item.setName(question_name.get(i));
            data.add(item);
        }
        return data;
    }




}

