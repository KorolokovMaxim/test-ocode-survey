package com.ocodetest.survey.controlles.admin;

import com.ocodetest.survey.entity.Answer;
import com.ocodetest.survey.entity.Question;
import com.ocodetest.survey.entity.Survey;
import com.ocodetest.survey.entity.User;
import com.ocodetest.survey.payload.request.AnswerRequest;
import com.ocodetest.survey.payload.request.QuestionRequest;
import com.ocodetest.survey.payload.request.SurveyRequest;
import com.ocodetest.survey.payload.response.MessageResponse;
import com.ocodetest.survey.payload.response.QuestionAnswerResponse;
import com.ocodetest.survey.payload.response.ViewUserAnswer;
import com.ocodetest.survey.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {


    private final AdminService adminService;

    public AdminController(AdminService adminService) {

        this.adminService = adminService;
    }


    @PostMapping("/create-survey")
    public ResponseEntity<Survey> createSurvey(@Valid @RequestBody SurveyRequest request) {
        Survey survey = adminService.createSurvey(request);
        return new ResponseEntity<>(survey, HttpStatus.OK);
    }

    @GetMapping("/get-all-surveys")
    public ResponseEntity<List<Survey>> getAllSurvey() {
        return new ResponseEntity<>(adminService.getAllSurveys(), HttpStatus.OK);
    }

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<Survey> getOneSurvey(@PathVariable("surveyId") Long id) {
        Survey survey = adminService.getOneSurvey(id);
        return new ResponseEntity<>(survey, HttpStatus.OK);
    }

    @DeleteMapping("/survey/delete/{surveyId}")
    public ResponseEntity<?> deleteSurvey(@PathVariable("surveyId") Long id) {
        adminService.deleteSurvey(id);
        return new ResponseEntity<>(new MessageResponse("Survey was deleted"), HttpStatus.OK);
    }

    @PostMapping("/survey/{surveyId}/create-question/")
    public ResponseEntity<Question> createQuestion(@PathVariable(name = "surveyId") Long surveyId, @RequestBody QuestionRequest request) {
        Question question = adminService.createQuestion(surveyId, request);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @DeleteMapping("question/delete/{questionId}/")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        adminService.deleteQuestion(questionId);
        return new ResponseEntity<>(new MessageResponse("Question was deleted"), HttpStatus.OK);
    }

    @GetMapping("/question/{questionId}/")
    public ResponseEntity<Question> getOneQuestion(@PathVariable Long questionId) {
        Question question = adminService.getOneQuestion(questionId);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @GetMapping("survey/{surveyId}/allQuestion")
    public ResponseEntity<List<Question>> getAllQuestionBySurveyId(@PathVariable Long surveyId) {
        List<Question> questionList = adminService.getAllQuestions(surveyId);
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }


    @PostMapping("/question/{questionId}/create-answer")
    public ResponseEntity<Answer> createAnswer(@PathVariable Long questionId,
                                               @RequestBody AnswerRequest request) {

        Answer answer = adminService.createAnswer(questionId, request);
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @DeleteMapping("/answer/delete/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long answerId) {
        adminService.deleteAnswer(answerId);
        return new ResponseEntity<>(new MessageResponse("Answer was deleted"), HttpStatus.OK);
    }

    @GetMapping("/answer/{answerId}")
    public ResponseEntity<Answer> getOneAnswer(@PathVariable Long answerId) {
        Answer answer = adminService.getOneAnswer(answerId);
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @GetMapping("/question/{questionId}/allAnswers")
    public ResponseEntity<List<Answer>> getAllAnswers(@PathVariable Long questionId) {
        List<Answer> answerList = adminService.getAllAnswers(questionId);
        return new ResponseEntity<>(answerList, HttpStatus.OK);
    }

    @GetMapping("completed-survey/{surveyId}")
    public ResponseEntity<List<QuestionAnswerResponse>> getCompletedSurvey(@PathVariable Long surveyId) {
        List<QuestionAnswerResponse> questionAnswerResponsesList = adminService.getCompletedSurvey(surveyId);
        return new ResponseEntity<>(questionAnswerResponsesList, HttpStatus.OK);
    }

    @GetMapping("/get-completed-user-survey/{user}/{survey}")
    public ResponseEntity<List<ViewUserAnswer>> showSurveysUser(@PathVariable User user,
                                                                @PathVariable Survey survey) {
        List<ViewUserAnswer> viewUserAnswerList = adminService.showCompletedUserSurvey(user, survey);
        return new ResponseEntity<>(viewUserAnswerList, HttpStatus.OK);
    }


}
