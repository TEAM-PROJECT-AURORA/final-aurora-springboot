package com.root34.aurora.survey.service;

import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.common.paging.SurveyPaging;
import com.root34.aurora.survey.dao.SurveyMapper;
import com.root34.aurora.survey.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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

        int surveyCode = 0;
        log.info("[SurveyService] insertSurvey Start ===================================");
        if(surveyDTO.getSurveyCode() == null || "".equals(surveyDTO.getSurveyCode())) {
            surveyCode = surveyMapper.insertSurvey(surveyDTO);
        }

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

    public int selectTotalSurveys(String searchValue) {

        log.info("[SurveyService] selectTotalSurveys Start ===================================");
        int totalCount = surveyMapper.selectTotalSurveys(searchValue);

        log.info("[SurveyService] selectTotalSurveys End ===================================");
        return totalCount;
    }

    public List<SurveyDTO> selectAllSurveysWithPaging(SelectCriteria selectCriteria) {

        log.info("[SurveyService] selectAllSurveysWithPaging Start ===================================");
        List<SurveyDTO> surveyDTOs = surveyMapper.selectAllSurveysWithPaging(selectCriteria);
        log.info("[SurveyService] selectAllSurveysWithPaging Paging Start ===================================");
        List<SurveyDTO> surveyDTOsWithPaging = SurveyPaging.applyPaging(surveyDTOs, selectCriteria);
        log.info("[SurveyService] selectAllSurveysWithPaging End ===================================");
        return surveyDTOsWithPaging;
    }

    public String selectReplyStatus(Map map) {

        log.info("[SurveyService] selectReplyStatus Start ===================================");
        String replyStatus = surveyMapper.selectReplyStatus(map);

        log.info("[SurveyService] selectReplyStatus End ===================================");
        return replyStatus == null? "미답변":replyStatus;
    }

    public List<SurveyDTO> selectAllSurveysForManagementWithPaging(SelectCriteria selectCriteria) {

        log.info("[SurveyService] selectAllSurveysForManagementWithPaging Start ===================================");
        List<SurveyDTO> surveyDTOs = surveyMapper.selectAllSurveysForManagementWithPaging(selectCriteria);
        log.info("[SurveyService] selectAllSurveysWithPaging Paging Start ===================================");
        List<SurveyDTO> surveyDTOsWithPaging = SurveyPaging.applyPaging(surveyDTOs, selectCriteria);
        log.info("[SurveyService] selectAllSurveysForManagementWithPaging End ===================================");
        return surveyDTOsWithPaging;
    }

    @Transactional
    public int insertSurveyReply(List<AnswerDTO> answerDTOList) {

        log.info("[SurveyService] insertSurveyReply Start ===================================");

        int result = surveyMapper.insertSurveyReply(answerDTOList);

        log.info("[SurveyService] insertSurveyReply Start ===================================");
        return result;
    }

    @Transactional
    public String insertSurveyReplyStatus(ReplyStatusDTO replyStatusDTO) {

        log.info("[SurveyService] insertSurveyReplyStatus Start ===================================");

        int result = surveyMapper.insertSurveyReplyStatus(replyStatusDTO);

        log.info("[SurveyService] insertSurveyReplyStatus Start ===================================");

        return result > 0? "답변 추가 성공":"답변 추가 실패";
    }

    @Transactional
    public String deleteSurveys(String[] surveyCodes) {

        log.info("[SurveyService] deleteSurveys Start ===================================");

        int result = surveyMapper.deleteSurveys(surveyCodes);

        log.info("[SurveyService] deleteSurveys Start ===================================");

        return result > 0? "설문 삭제 성공":"설문 삭제 실패";
    }

    public SurveyDTO selectSurveyForUpdate(String surveyCode) {

        log.info("[SurveyService] selectSurveyForUpdate Start ===================================");

        SurveyDTO surveyDTO = surveyMapper.selectSurveyForUpdate(surveyCode);

        log.info("[SurveyService] selectSurveyForUpdate Start ===================================");

        return surveyDTO;
    }

    @Transactional
    public String updateSurvey(SurveyDTO surveyDTO) {

        log.info("[SurveyService] updateSurvey Start ===================================");
        int surveyCode = surveyMapper.updateSurvey(surveyDTO);

        List<QuestionDTO> questionDTOS = surveyDTO.getQuestions();
        log.info("[SurveyService] surveyCode" + surveyDTO.getSurveyCode());
        for(int i = 0; i < questionDTOS.size(); i++) {

                log.info("[SurveyService] surveyCode 있던질문" + surveyDTO.getSurveyCode());
                int questionNo = surveyMapper.updateQuestions(questionDTOS.get(i));
            List<ChoiceDTO> choiceDTOS = questionDTOS.get(i).getChoices();
            for(int j = 0; j < choiceDTOS.size(); j++) {
                log.info("[SurveyService] questionNo" + choiceDTOS.get(j).getQuestionNo());

                if(choiceDTOS.get(j).getQuestionNo() == null || "".equals(choiceDTOS.get(j).getQuestionNo())) {
                    log.info("[SurveyService] questionNo 새 선택지" + questionDTOS.get(i).getQuestionNo());
                    choiceDTOS.get(j).setQuestionNo(questionDTOS.get(i).getQuestionNo());
                    int result = surveyMapper.insertChoices(choiceDTOS.get(j));
                } else {
                    log.info("[SurveyService] questionNo 있던선택지" + questionDTOS.get(i).getQuestionNo());
                    int result = surveyMapper.updateChoices(choiceDTOS.get(j));
                }

            }
        }

        log.info("[SurveyService] updateSurvey End ===================================");

        return (surveyCode > 0)? "설문 수정 성공" : "설문 수정 실패";
    }
}
