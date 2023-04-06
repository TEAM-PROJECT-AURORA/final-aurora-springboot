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
        int surveyCode = surveyMapper.insertSurvey(surveyDTO);

        for(int i = 0; i < surveyDTO.getQuestions().size(); i++) {

            surveyDTO.getQuestions().get(i).setSurveyCode(surveyDTO.getSurveyCode());
            int questionNo = surveyMapper.insertQuestions(surveyDTO.getQuestions().get(i));

            for(int j = 0; j < surveyDTO.getQuestions().get(i).getChoices().size(); j++) {
                surveyDTO.getQuestions().get(i).getChoices().get(j).setQuestionNo(surveyDTO.getQuestions().get(i).getQuestionNo());
                int result = surveyMapper.insertChoices(surveyDTO.getQuestions().get(i).getChoices().get(j));
            }
        }

        log.info("[SurveyService] insertSurvey End ===================================");
        return (surveyCode > 0)? "설문 추가 성공" : "설문 추가 실패";
    }
}
