package com.root34.aurora.survey.service;

import com.root34.aurora.survey.dao.SurveyMapper;
import com.root34.aurora.survey.dto.SurveyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SurveyService {

    private SurveyMapper surveyMapper;

    public SurveyService(SurveyMapper surveyMapper) {
        this.surveyMapper = surveyMapper;
    }

    @Transactional
    public String insertSurvey(SurveyDTO surveyDTO) {

        log.info("[SurveyService] insertSurvey Start ===================================");
        int result = surveyMapper.insertSurvey(surveyDTO);

        log.info("[SurveyService] insertSurvey End ===================================");
        return (result > 0)? "설문 추가 성공" : "설문 추가 실패";
    }
}
