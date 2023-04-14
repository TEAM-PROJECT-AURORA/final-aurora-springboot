package com.root34.aurora.mail.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
	@ClassName : MailSearchCriteria
	@Date : 2023-04-10
	@Writer : 김수용
	@Description : 메일 검색 조건
*/
@Data
public class MailSearchCriteria {

    /**
     * @variable tagCode 태그 코드
     */
    private long tagCode;
    /**
     * @variable mailTitle 메일 제목
     */
    private String mailTitle;
    /**
     * @variable mailBody 메일 내용
     */
    private String mailBody;
    /**
     * @variable senderName 발신자 이름
     */
    private String senderName;
    /**
     * @variable senderEmail 발신자 이메일
     */
    private String senderEmail;
    /**
     * @variable recipients 수신자
     */
    private String recipients;
    /**
     * @variable startDate 검색 시작일
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    /**
     * @variable endDate 검색 종료일
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    /**
     * @variable importantStatus 중요 상태
     */
    private char importantStatus;
    /**
     * @variable deleteStatus 삭제 상태
     */
    private char deleteStatus;
    /**
     * @variable blacklist 블랙리스트
     */
    private List<Integer> blacklist;
}
