package com.root34.aurora.report.dto;

import lombok.Data;

import java.sql.Date;

/**
 @ClassName : ReportRoundDTO
 @Date : 23.03.22.
 @Writer : 김수용
 @Description : 보고 회차 정보를 담을 DTO
 */
@Data
public class ReportRoundDTO {

    /**
     * @param roundCode 보고 회차 코드
     * @param reportCode 보고 코드
     * @param roundTitle 보고 회차 제목
     * @param roundBody 보고 회차 내용
     * @param regDate 보고 회차 등록일
     * @param capacity 보고 정원
     */
    private long roundCode;
    private long reportCode;
    private String roundTitle;
    private String roundBody;
    private Date regDate;
    private int capacity;
}