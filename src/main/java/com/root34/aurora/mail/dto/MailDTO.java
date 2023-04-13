package com.root34.aurora.mail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.root34.aurora.common.FileDTO;
import com.root34.aurora.member.dto.MemberDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
     * @variable recipient 수신인
     */
    private String recipient;
    /**
     * @variable cc 참조
     */
    private String cc;
    /**
     * @variable shipDate 발송일
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime shipDate;
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
    /**
     * @variable memberCode 멤버 코드
     */
    private int memberCode;
    /**
     * @variable memberDTO 멤버 DTO
     */
    private MemberDTO memberDTO;
    /**
     * @variable tagDTO 태그 DTO
     */
    private TagDTO tagDTO;
    /**
     * @variable hasAttachments 첨부파일 존재 여부
     */
    private boolean hasAttachments;
    /**
     * @variable fileList 파일리스트
     */
    private List<FileDTO> fileList;
}