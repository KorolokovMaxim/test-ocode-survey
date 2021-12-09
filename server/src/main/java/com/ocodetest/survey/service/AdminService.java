package com.ocodetest.survey.service;

import com.ocodetest.survey.entity.Answer;
import com.ocodetest.survey.entity.Question;
import com.ocodetest.survey.entity.Survey;
import com.ocodetest.survey.entity.User;
import com.ocodetest.survey.payload.request.AnswerRequest;
import com.ocodetest.survey.payload.request.QuestionRequest;
import com.ocodetest.survey.payload.request.SurveyRequest;
import com.ocodetest.survey.payload.response.QuestionAnswerResponse;
import com.ocodetest.survey.payload.response.ViewUserAnswer;
import com.ocodetest.survey.repository.AnswerRepository;
import com.ocodetest.survey.repository.QuestionRepository;
import com.ocodetest.survey.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public AdminService(SurveyRepository surveyRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Survey createSurvey(SurveyRequest request){
        Survey survey = new Survey();
        survey.setName(request.getName());
        surveyRepository.save(survey);
        return survey;
    }

    public List<Survey> getAllSurveys(){
        return surveyRepository.findAll();
    }

    public Survey getOneSurvey(Long id){
        return surveyRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Error: survey is not found"));
    }

    public void deleteSurvey(Long id){
        Survey survey = surveyRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Error: survey is not found"));
        surveyRepository.delete(survey);
    }

    public Question createQuestion(Long surveyId, QuestionRequest request){
        Survey survey = surveyRepository.findById(surveyId).orElseThrow();
        Question saveQuestion = new Question();
        saveQuestion.setName(request.getName());
        saveQuestion.setSurvey(survey);
        questionRepository.save(saveQuestion);
        return saveQuestion;
    }

    public void deleteQuestion(Long questionId){
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new RuntimeException("Error: question is not found"));
        questionRepository.delete(question);
    }

    public Question getOneQuestion(Long questionId){
        return questionRepository.findById(questionId).orElseThrow(
                () -> new RuntimeException("Error: question is not found"));
    }

    public List<Question> getAllQuestions(Long surveyId){
        return questionRepository.findQuestionBySurveyId(surveyId);
    }

    public Answer createAnswer(Long questionId, AnswerRequest request){
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new RuntimeException("Error: question is not found"));

        Answer saveAnswer = new Answer();
        saveAnswer.setName(request.getName());
        saveAnswer.setQuestion(question);
        answerRepository.save(saveAnswer);
        return saveAnswer;
    }

    public void deleteAnswer(Long answerId){
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                ()-> new RuntimeException("Error: answer is not found")
        );
        answerRepository.delete(answer);
    }

    public Answer getOneAnswer(Long answerId){
        return answerRepository.findById(answerId).orElseThrow(
                ()-> new RuntimeException("Error: answer is not found")
        );
    }

    public List<Answer> getAllAnswers(Long questionId){
        return answerRepository.findAnswerByQuestionId(questionId);
    }

    public List<QuestionAnswerResponse> getCompletedSurvey(Long surveyID){
        List<QuestionAnswerResponse> questionAndAnswersMap = new ArrayList<>();
        List<Question> questionList = questionRepository.findQuestionBySurveyId(surveyID);
        for (Question question : questionList){
            QuestionAnswerResponse questionAnswerResponse = new QuestionAnswerResponse();
            questionAnswerResponse.setQuestion(question);
            questionAnswerResponse.setAnswerList(answerRepository.findAnswerByQuestionId(question.getId()));
            questionAndAnswersMap.add(questionAnswerResponse);
        }

        return questionAndAnswersMap;
    }

    public List<ViewUserAnswer> showCompletedUserSurvey(User user , Survey survey){
        Set<Answer> getAllUserAnswers = user.getAnswers();
        List<ViewUserAnswer> viewUserAnswers = new ArrayList<>();
        survey.getQuestions().forEach(question -> {
            ViewUserAnswer viewUserAnswer = new ViewUserAnswer();
            viewUserAnswer.setQuestion(question);
            question.getAnswers().forEach(answer -> {
                if(getAllUserAnswers.contains(answer)){
                    viewUserAnswer.addAnswer(answer);
                }
            });
            viewUserAnswers.add(viewUserAnswer);
        });
        return viewUserAnswers;
    }

}
