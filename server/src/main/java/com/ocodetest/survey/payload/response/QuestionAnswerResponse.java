package com.ocodetest.survey.payload.response;

import com.ocodetest.survey.entity.Answer;
import com.ocodetest.survey.entity.Question;


import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerResponse {

    Question question;
    List<Answer> answerList = new ArrayList<>();

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
}
