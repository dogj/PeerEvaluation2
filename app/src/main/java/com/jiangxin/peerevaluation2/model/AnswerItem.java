package com.jiangxin.peerevaluation2.model;



public class AnswerItem {
    private   String question_from;
    private    float score;
    private  String question_to;



    public  String getQuestion_to() {
        return question_to;
    }

    public  void setQuestion_to(String question_to) {
        this.question_to = question_to;
    }

    public  String getQuestion_from() {
        return question_from;
    }

    public  void setQuestion_from(String question_from) {
        this.question_from = question_from;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
