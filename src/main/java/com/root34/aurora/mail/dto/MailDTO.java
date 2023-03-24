package com.root34.aurora.mail.dto;

import lombok.Data;

import java.sql.Date;

/**
 @ClassName : MailDTO
 @Date : 23.03.20.
 @Writer : 김수용
 @Description : 메일 정보를 담을 DTO
 */
@Data
public class MailDTO {

    /**
     * @variable mailCode 메일 코드
     */
    private long mailCode;
    /**
     * @variable tagCode 태그 코드
     */
    private long tagCode;
    /**
     * @variable memberCode 멤버 코드
     */
    private long memberCode;
    /**
     * @variable mailTitle 메일 제목
     */
    private String mailTitle;
    /**
     * @variable mailBody 메일 내용
     */
    private String mailBody;
    /**
     * @variable sender 발신인
     */
    private String sender;
    /**
     * @variable recipient 수신인
     */
    private String recipient;
    /**
     * @variable shipDate 발송일
     */
    private Date shipDate;
    /**
     * @variable readStatus 읽음 상태
     */
    private char readStatus;
    /**
     * @variable importantStatus 중요 상태
     */
    private char importantStatus;
    /**
     * @variable deleteStatus 삭제 상태
     */
    private char deleteStatus;
}