package com.server.core.pojo;

import java.io.Serializable;

public class Student implements Serializable {
    private int sid;
    private String pwd;
    private int numOfAttempts;

    private int scores[];

    private Question[] questions;

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public Student(int sid, String pwd) {
        this.sid = sid;
        this.pwd = pwd;
        this.numOfAttempts = 0;
        this.scores = new int[3];
        this.questions = new Question[10];
    }


    public int getSid() {
        return sid;
    }


    public String getPwd() {
        return pwd;
    }

    public int getNumOfAttempts() {
        return numOfAttempts;
    }

    public void setNumOfAttempts(int numOfAttempts) {
        this.numOfAttempts = numOfAttempts;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }
}
