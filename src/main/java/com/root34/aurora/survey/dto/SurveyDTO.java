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

    String surveyCode;

    String surveySubject;

    String startDate;

    String endDate;

    List<QuestionDTO> questions;
}
