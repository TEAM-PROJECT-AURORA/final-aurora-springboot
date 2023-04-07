package com.root34.aurora.survey.dto;

import lombok.Data;

/**
	@ClassName : ChoiceDTO
	@Date : 2023-04-07
	@Writer : 오승재
	@Description : 선택지 DTO
*/
@Data
public class ChoiceDTO {

    /**
    	* @variable  선택지 번호
    */
    String choiceNo;
    /**
        * @variable  질문 번호
    */
    String questionNo;
    /**
        * @variable  선택지 내용
    */
    String choiceBody;
}
