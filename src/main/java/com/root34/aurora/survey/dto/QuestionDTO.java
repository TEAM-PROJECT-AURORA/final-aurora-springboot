package com.root34.aurora.survey.dto;

import lombok.Data;

import java.util.List;

/**
	@ClassName : QuestionDTO
	@Date : 2023-04-07
	@Writer : 오승재
	@Description : 질문 DTO
*/
@Data
public class QuestionDTO {
    
    /**
    	* @variable  질문 번호
    */
    String questionNo;
    /**
         * @variable 설문 코드
     */
    String surveyCode;
    /**
        * @variable 질문 타입
     */
    String questionType;
    /**
        * @variable 질문 내용
     */
    String questionBody;

    List<ChoiceDTO> choices;
}
