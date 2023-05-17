package com.root34.aurora.survey.dto;

import lombok.Data;

/**
	@ClassName : ReplyStatusDTO
	@Date : 2023-04-08
	@Writer : 오승재
	@Description : 설문 답변 상태 DTO
*/
@Data
public class ReplyStatusDTO {

    /**
    	* @variable 설문 답변 상태 번호
    */
    private String surveyReplyStateNo;
    /**
    	* @variable 설문 코드
    */
    private String surveyCode;
    /**
    	* @variable 사원 코드
    */
    private int memberCode;
    /**
    	* @variable 답변 상태
    */
    private String replyStatus;
}
