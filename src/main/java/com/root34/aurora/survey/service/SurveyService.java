package com.root34.aurora.survey.service;

import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.survey.dao.SurveyMapper;
import com.root34.aurora.survey.dto.ChoiceDTO;
import com.root34.aurora.survey.dto.QuestionDTO;
import com.root34.aurora.survey.dto.SurveyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

        List<QuestionDTO> questionDTOS = surveyDTO.getQuestions();
        for(int i = 0; i < questionDTOS.size(); i++) {

            questionDTOS.get(i).setSurveyCode(surveyDTO.getSurveyCode());
            int questionNo = surveyMapper.insertQuestions(questionDTOS.get(i));

            List<ChoiceDTO> choiceDTOS = questionDTOS.get(i).getChoices();
            for(int j = 0; j < choiceDTOS.size(); j++) {
                choiceDTOS.get(j).setQuestionNo(questionDTOS.get(i).getQuestionNo());
                int result = surveyMapper.insertChoices(choiceDTOS.get(j));
            }
        }

        log.info("[SurveyService] insertSurvey End ===================================");
        return (surveyCode > 0)? "설문 추가 성공" : "설문 추가 실패";
    }

    public int selectTotalSurveys() {

        log.info("[SurveyService] selectTotalSurveys Start ===================================");
        int totalCount = surveyMapper.selectTotalSurveys();

        log.info("[SurveyService] selectTotalSurveys End ===================================");
        return totalCount;
    }

    public List<SurveyDTO> selectAllSurveysWithPaging(SelectCriteria selectCriteria) {

        log.info("[SurveyService] selectAllSurveysWithPaging Start ===================================");
        List<SurveyDTO> surveyDTOS = surveyMapper.selectAllSurveysWithPaging(selectCriteria);

        log.info("[SurveyService] selectAllSurveysWithPaging End ===================================");
        return surveyDTOS;
    }

    public String selectReplyStatus(Map map) {

        log.info("[SurveyService] selectReplyStatus Start ===================================");
        String replyStatus = surveyMapper.selectReplyStatus(map);

        log.info("[SurveyService] selectReplyStatus End ===================================");
        return replyStatus == null? "미답변":replyStatus;
    }

    public List<SurveyDTO> selectAllSurveysForManagementWithPaging(SelectCriteria selectCriteria) {

        log.info("[SurveyService] selectAllSurveysForManagementWithPaging Start ===================================");
        List<SurveyDTO> surveyDTOS = surveyMapper.selectAllSurveysForManagementWithPaging(selectCriteria);

        log.info("[SurveyService] selectAllSurveysForManagementWithPaging End ===================================");
        return surveyDTOS;
    }
}
