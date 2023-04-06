package com.root34.aurora.survey.dao;

import com.root34.aurora.survey.dto.SurveyDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SurveyMapper {

    int insertSurvey(SurveyDTO surveyDTO);
}
