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
    private static  List<String> group_reason = new ArrayList<>();
    private static List<String> group_code = new ArrayList<>();
    private static  String current_user = null;
    private static  String current_group = null;
    private static List<GroupItem> data = null;
    public static void set_current_user (String pid){
        GroupData.current_user=pid;
    }
    public static String get_current_user (){
        return current_user;
    }


    public static void clear(){
        groupname.clear();
        group_reason.clear();
        group_code.clear();
    }



    public static void adddata(String name, String reason, String code){
        groupname.add(name);
        group_reason.add(reason);
        group_code.add(code);
    }



    public static String getCurrent_group(int position) {
        if(group_code.size()>=position) {

            return group_code.get(position).substring(1, group_code.get(position).length());
        }else{
            return "opps bug for get current_group";
        }
    }

    public static void setCurrent_group(String current_group) {
        GroupData.current_group = current_group;
    }

    public static String getCurrent_group() {
        return current_group;
    }

    public int size(){
        return groupname.size();
    }

    public static GroupItem getListData(int position){
            GroupItem item2 = new GroupItem();
            item2.setName(groupname.get(position));
            item2.setId(group_reason.get(position));
            item2.setCode(group_code.get(position));
        return item2;
    }


    public static List<GroupItem> getListData() {

        if (data==null){

        data = new ArrayList<>();}
        else {
            data.clear();
        }

        for ( int i = 0; i <groupname.size()&& i<group_reason.size(); i++){
            GroupItem item = new GroupItem();
            item.setName(groupname.get(i));
            item.setId(group_reason.get(i));
            item.setCode(group_code.get(i));
            data.add(item);
        }
        return data;
    }

}

