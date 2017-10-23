package com.jiangxin.peerevaluation2.model;



public class GroupCode {

    public static String getCode(int i){
        String temp = String.valueOf(i);

        char cha[]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char ch[]=new char[2];
        for(int a=0;a<ch.length;a++)
        {
            int index;
            index=(int)(Math.random()*(cha.length));
            ch[a]=cha[index];
        }
        temp = ch[0]+temp+ ch[1];

        return  temp;
    }


}
