package com.root34.aurora.survey.dao;

import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.survey.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SurveyMapper {

    int insertSurvey(SurveyDTO surveyDTO);

    int insertQuestions(QuestionDTO questionDTO);

    int insertChoices(ChoiceDTO choiceDTO);

    int selectTotalSurveys(String searchValue);

    List<SurveyDTO> selectAllSurveysWithPaging(SelectCriteria selectCriteria);

    String selectReplyStatus(Map map);

    List<SurveyDTO> selectAllSurveysForManagementWithPaging(SelectCriteria selectCriteria);

    int insertSurveyReply(List<AnswerDTO> answerDTOList);

    int insertSurveyReplyStatus(ReplyStatusDTO replyStatusDTO);

    int deleteSurveys(String[] surveyCodes);

    SurveyDTO selectSurveyForUpdate(String surveyCode);

    int updateSurvey(SurveyDTO surveyDTO);

    int updateQuestions(QuestionDTO questionDTO);

    int updateChoices(ChoiceDTO choiceDTO);
}
