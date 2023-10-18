package com.example.quiz;

public class Question {
    private int questionId;
    private boolean trueAnswer;

    public Question(int questionId, boolean trueAnswer) {
        this.questionId = questionId;
        this.trueAnswer = trueAnswer;
    }

    int getQuestionId(){
        return questionId;
    }
    boolean isTrueAnswer(){
        return trueAnswer;
    }



}
