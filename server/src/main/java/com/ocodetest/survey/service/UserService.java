package com.ocodetest.survey.service;

import com.ocodetest.survey.entity.*;
import com.ocodetest.survey.payload.response.QuestionAnswerResponse;
import com.ocodetest.survey.payload.response.ViewUserAnswer;
import com.ocodetest.survey.repository.AnswerRepository;
import com.ocodetest.survey.repository.QuestionRepository;
import com.ocodetest.survey.repository.SurveyRepository;
import com.ocodetest.survey.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    public UserService(SurveyRepository surveyRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, UserRepository userRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }

    public List<Survey> getAllSurveys(){
        return surveyRepository.findAll();
    }

    public List<QuestionAnswerResponse> getSurveyForCompleted(Long id){
        List<QuestionAnswerResponse> questionAndAnswersMap = new ArrayList<>();
        List<Question> questionList = questionRepository.findQuestionBySurveyId(id);
        for (Question question : questionList){
            QuestionAnswerResponse questionAnswerResponse = new QuestionAnswerResponse();
            questionAnswerResponse.setQuestion(question);
            questionAnswerResponse.setAnswerList(answerRepository.findAnswerByQuestionId(question.getId()));
            questionAndAnswersMap.add(questionAnswerResponse);
        }
        return questionAndAnswersMap;
    }

    public void saveCompletedSurvey(Set<Answer> answers, User user, Survey survey){
        Set<Answer> userAnswer = user.getAnswers();
        userAnswer.addAll(answers);
        user.setAnswers(userAnswer);
        user.setSurveyUser(Collections.singletonList(survey));
        userRepository.save(user);
    }

    public List<ViewUserAnswer> showCompletedUserSurvey(Survey survey, User user){
        Set<Answer> userAnswer = user.getAnswers();
        List<ViewUserAnswer> viewUserAnswers = new ArrayList<>();
        survey.getQuestions().forEach(question -> {
            ViewUserAnswer viewUserAnswer = new ViewUserAnswer();
            viewUserAnswer.setQuestion(question);
            question.getAnswers().forEach(answer -> {
                if(userAnswer.contains(answer)){
                    viewUserAnswer.addAnswer(answer);
                }
            });
            viewUserAnswers.add(viewUserAnswer);
                }
        );
        return viewUserAnswers;
    }

}
