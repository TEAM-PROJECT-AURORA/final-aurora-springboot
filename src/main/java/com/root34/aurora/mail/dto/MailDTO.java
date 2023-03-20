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
     * @param cartNo 장바구니 번호(고유 일련번호)
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
