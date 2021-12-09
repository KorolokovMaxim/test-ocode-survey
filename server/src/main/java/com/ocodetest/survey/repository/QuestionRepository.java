package com.ocodetest.survey.repository;

import com.ocodetest.survey.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {

//
//    @Query("SELECT Question FROM Question where Question.survey = :surveyId")
//    List<Question> findByQuestionBySurveyId(@Param("surveyId") Long surveyId);

    List<Question> findQuestionBySurveyId(Long id);
}
