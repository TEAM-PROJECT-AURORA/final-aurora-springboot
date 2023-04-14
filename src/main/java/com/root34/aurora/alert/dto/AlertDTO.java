package com.root34.aurora.alert.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 @ClassName : AlertDTO
 @Date : 23.04.13.
 @Writer : 김수용
 @Description : 알림 정보를 담을 DTO
 */
@Data
public class AlertDTO {

    /**
     * @variable alertCode 알림 코드
     */
    private Long alertCode;
    /**
     * @variable senderMemberCode 발신자 멤버 코드
     */
    private Integer senderMemberCode;
    /**
     * @variable senderEmail 발신자 이메일
     */
    private String senderEmail;
    /**
     * @variable receiverMemberCode 수신자 멤버 코드
     */
    private int receiverMemberCode;
    /**
     * @variable isRead 읽음 여부
     */
    private char readStatus;
    /**
     * @variable regDate 등록일시
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime regDate;
    /**
     * @variable alertType 알림 유형
     */
    private String alertType;
    /**
     * @variable mailCode 메일 코드
     */
    private Long mailCode;
    /**
     * @variable reportCode 보고 코드
     */
    private Long reportCode;
    /**
     * @variable roundCode 보고 회차 코드
     */
    private Long roundCode;
    /**
     * @variable detailCode 상세 보고 코드
     */
    private Long detailCode;
    /**
     * @variable replyCode 댓글 코드
     */
    private Long replyCode;
    /**
     * @variable appCode 결재 코드
     */
    private Integer appCode;
    /**
     * @variable alertMessage 알림 내용
     */
    private String alertMessage;
}