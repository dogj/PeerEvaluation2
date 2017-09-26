package com.jiangxin.peerevaluation2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */


public class Data {
//    private  static  String[] titles = {"第一", "第二", "第三"};
//    private static   int[] icons = {R.drawable.beiqu, R.drawable.beiyule, R.drawable.bugaoxing};
//    private static   int[] types ={0,2,2};
    private static  List<String> title = new ArrayList<>();
    private static  List<Integer> icon = new ArrayList<>();
    private static List<Integer> type = new ArrayList<>();




//    public void initial(){
//        title.add("第一");
//        title.add("第二");
//        title.add("第三");
//        icon.add(R.drawable.beiqu);
//        icon.add(R.drawable.beiyule);
//        icon.add(R.drawable.bugaoxing);
//        type.add(0);
//        type.add(2);
//        type.add(2);
//    }
//
//    public void adddata(){
//
//        title.add("第四");
//        icon.add(R.drawable.beiqu);
//        type.add(0);
//
//    }



    public static List<ListItem> getListData() {
        List<ListItem> data = new ArrayList<>();

        for ( int i = 0; i <title.size()&& i<icon.size(); i++){
            ListItem item = new ListItem();
            item.setImageResId(icon.get(i));
            item.setTitles(title.get(i));
            item.setType(type.get(i));
            data.add(item);
        }
        return data;
    }
}

