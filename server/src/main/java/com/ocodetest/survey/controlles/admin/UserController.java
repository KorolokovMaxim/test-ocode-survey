package com.ocodetest.survey.controlles.admin;


import com.ocodetest.survey.entity.Answer;
import com.ocodetest.survey.entity.Survey;
import com.ocodetest.survey.entity.User;
import com.ocodetest.survey.payload.response.MessageResponse;
import com.ocodetest.survey.payload.response.QuestionAnswerResponse;
import com.ocodetest.survey.payload.response.ViewUserAnswer;
import com.ocodetest.survey.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user/")
@PreAuthorize("hasRole('USER')")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/get-all-survey")
    public ResponseEntity<List<Survey>> getAllSurveys(){
        List<Survey> surveyList = userService.getAllSurveys();
        return new ResponseEntity<>(surveyList, HttpStatus.OK);
    }

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<List<QuestionAnswerResponse>> getSurveyForCompleted(@PathVariable Long surveyId){
        List<QuestionAnswerResponse> questionAnswerResponseList = userService.getSurveyForCompleted(surveyId);
        return new ResponseEntity<>(questionAnswerResponseList,HttpStatus.OK);
    }

    @PostMapping("/survey/{surveyId}/saveCompletedSurvey")
    public ResponseEntity<?> saveCompletedSurvey(@RequestParam(name = "userQueryAnswers") Set<Answer> answers ,
                                                 @PathVariable Survey surveyId,
                                                 @AuthenticationPrincipal User user){
        userService.saveCompletedSurvey(answers,user,surveyId);
        return new ResponseEntity<>(new MessageResponse("Save user answers is successfully"), HttpStatus.OK);
    }

    @GetMapping("/show-completed-survey/{surveyId}")
    public ResponseEntity<List<ViewUserAnswer>> showCompletedUserSurvey(@PathVariable Survey surveyId,
                                                                        @AuthenticationPrincipal User user){

        List<ViewUserAnswer> viewUserAnswerList = userService.showCompletedUserSurvey(surveyId,user);
        return new ResponseEntity<>(viewUserAnswerList, HttpStatus.OK);
    }
}
