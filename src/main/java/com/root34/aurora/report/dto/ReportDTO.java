package com.root34.aurora.report.dto;

import lombok.Data;

/**
 @ClassName : ReportDTO
 @Date : 23.03.21.
 @Writer : 김수용
 @Description : 보고 정보를 담을 DTO
 */
@Data
public class ReportDTO {

    /**
     * @param id insert후 생성된 PK값을 담을 변수
     * @param reportCode 보고 코드
     * @param memberCode 멤버 코드
     * @param reportType 보고 유형
     * @param reportTitle 보고 제목
     * @param reportInfo 보고 정보
     * @param reportCycle 보고 주기
     * @param readStatus 읽음 상태
     * @param completionStatus 완료 상태
     */
    private int id;
    private long reportCode;
    private long memberCode;
    private String reportType;
    private String reportTitle;
    private String reportInfo;
    private String reportCycle;
    private char readStatus;
    private char completionStatus;
}