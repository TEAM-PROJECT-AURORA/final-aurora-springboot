package com.root34.aurora.survey.dao;

import com.root34.aurora.survey.dto.ChoiceDTO;
import com.root34.aurora.survey.dto.QuestionDTO;
import com.root34.aurora.survey.dto.SurveyDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SurveyMapper {

    int insertSurvey(SurveyDTO surveyDTO);

    int insertQuestions(QuestionDTO questionDTO);

    int insertChoices(ChoiceDTO choiceDTO);
}
