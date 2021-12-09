package com.ocodetest.survey.repository;

import com.ocodetest.survey.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {

    List<Answer> findAnswerByQuestionId(Long id);
}
