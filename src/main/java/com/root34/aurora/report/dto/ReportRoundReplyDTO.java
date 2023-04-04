package com.root34.aurora.report.dto;

import lombok.Data;

import java.sql.Date;

/**
	@ClassName : ReportRoundReplyDTO
	@Date : 2023-03-28
	@Writer : 김수용
	@Description :
*/
@Data
public class ReportRoundReplyDTO {

    /**
     * @variable replyCode 보고 댓글 코드
     */
    private long replyCode;
    /**
     * @variable roundCode 보고 회차 코드
     */
    private long roundCode;
    /**
     * @variable memberCode 보고 댓글 작성자
     */
    private int memberCode;
    /**
     * @variable memberName 보고 댓글 작성자명
     */
    private String memberName;
    /**
     * @variable replyBody 보고 댓글 내용
     */
    private String replyBody;
    /**
     * @variable regDate 보고 댓글 등록일
     */
    private Date regDate;
    /**
     * @variable deleteStatus 삭제 상태
     */
    private char deleteStatus;
}
