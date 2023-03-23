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
     * @param mailCode 메일 코드
     * @param tagCode 태그 코드
     * @param memberCode 멤버 코드
     * @param mailTitle 메일 제목
     * @param mailBody 메일 내용
     * @param sender 발신인
     * @param recipient 수신인
     * @param shipDate 발송일
     * @param readStatus 읽음 상태
     * @param importantStatus 중요 상태
     * @param deleteStatus 삭제 상태
     */
    private long mailCode;
    private long tagCode;
    private long memberCode;
    private String mailTitle;
    private String mailBody;
    private String sender;
    private String recipient;
    private Date shipDate;
    private char readStatus;
    private char importantStatus;
    private char deleteStatus;
}