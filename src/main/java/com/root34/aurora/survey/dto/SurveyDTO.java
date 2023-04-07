package com.root34.aurora.survey.dto;

import lombok.Data;

import java.util.List;

/**
	@ClassName : SurveyDTO
	@Date : 2023-04-06
	@Writer : 오승재
	@Description : 설문에 대한 정보
*/
@Data
public class SurveyDTO {

    /**
    	* @variable 설문 코드 
    */
    String surveyCode;
    /**
    	* @variable 설문 주제 
    */
    String surveySubject;
    /**
    	* @variable 시작일 
    */
    String startDate;
    /**
    	* @variable 종료일 
    */
    String endDate;
    /**
    	* @variable 답변 상태 
    */
    String replyStatus;
    /**
    	* @variable 문항 리스트
    */
    List<QuestionDTO> questions;
}
