package com.jiangxin.peerevaluation2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */


public class GroupData {
//    private  static  String[] titles = {"第一", "第二", "第三"};
//    private static   int[] icons = {R.drawable.beiqu, R.drawable.beiyule, R.drawable.bugaoxing};
//    private static   int[] types ={0,2,2};
    private static  List<String> groupname = new ArrayList<>();
    private static  List<String> course_id = new ArrayList<>();
    private static List<String> course_code = new ArrayList<>();
    private static  String current_user = null;

    public static void set_current_user (String pid){
        GroupData.current_user=pid;
    }
    public static String get_current_user (){
        return current_user;
    }


    public void initial(){
        groupname.add("第一");
        groupname.add("第二");
        groupname.add("第三");
        course_id.add("IT7374");
        course_id.add("IT7375");
        course_id.add("IT7376");
        course_code.add("0");
        course_code.add("1");
        course_code.add("2");
    }

    public static void adddata(){

        groupname.add("test");
        course_id.add("IT7377");

    }

    public static void adddata(String name, String id){
        groupname.add(name);
        course_id.add(id);

    }


    public static void remove(int position){
       groupname.remove(position);
        course_code.remove(position);
    }

    public int size(){
        return groupname.size();
    }

    public static GroupItem getListData(int position){
            GroupItem item2 = new GroupItem();
            item2.setName(groupname.get(position));
            item2.setId(course_id.get(position));
        return item2;
    }


    public static List<GroupItem> getListData() {
        List<GroupItem> data = new ArrayList<>();

        for ( int i = 0; i <groupname.size()&& i<course_id.size(); i++){
            GroupItem item = new GroupItem();
            item.setName(groupname.get(i));
            item.setId(course_id.get(i));
            item.setCode(String.valueOf(i));
            data.add(item);
        }
        return data;
    }

}

