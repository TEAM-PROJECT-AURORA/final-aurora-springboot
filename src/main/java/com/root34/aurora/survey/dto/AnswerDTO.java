package com.root34.aurora.survey.dto;

import lombok.Data;

/**
	@ClassName : AnswerDTO
	@Date : 2023-04-07
	@Writer : 오승재
	@Description : 설문 답변 DTO
*/
@Data
public class AnswerDTO {

	/**
		* @variable 답변 번호
	*/
    private String answerNo;
	/**
		* @variable 설문 코드 
	*/
	private String surveyCode;
	/**
		* @variable 질문 번호
	*/
	private String questionNo;
	/**
		* @variable 선택지 번호
	*/
	private String choiceNo;
	/**
		* @variable 답변 내용
	*/
	private String answerBody;
	/**
		* @variable 사원 코드
	*/
	private int memberCode;
}
