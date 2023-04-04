package com.root34.aurora.report.dto;

import com.root34.aurora.member.dto.MemberDTO;
import lombok.Data;

import java.sql.Date;

/**
 @ClassName : ReportDTO
 @Date : 23.03.21.
 @Writer : 김수용
 @Description : 보고 정보를 담을 DTO
 */
@Data
public class ReportDTO {

    /**
     * @variable reportCode 보고 코드
     */
    private long reportCode;
    /**
     * @variable memberCode 멤버 코드
     */
    private int memberCode;
    /**
     * @variable reportType 보고 유형
     */
    private String reportType;
    /**
     * @variable reportTitle 보고 제목
     */
    private String reportTitle;
    /**
     * @variable reportInfo 보고 정보
     */
    private String reportInfo;
    /**
     * @variable reportCycle 보고 주기
     */
    private String reportCycle;
    /**
     * @variable regDate 보고 등록일
     */
    private Date regDate;
    /**
     * @variable readStatus 읽음 상태
     */
    private char readStatus;
    /**
     * @variable completionStatus 완료 상태
     */
    private char completionStatus;
    /**
     * @variable memberDTO 멤버 DTO
     */
    private MemberDTO memberDTO;
}