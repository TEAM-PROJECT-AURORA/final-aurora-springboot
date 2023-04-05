package com.root34.aurora.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
    @ClassName : ReportRoundDTO
    @Date : 23.03.22.
    @Writer : 김수용
    @Description : 보고 회차 정보를 담을 DTO
 */
@Data
public class ReportRoundDTO {

    /**
     * @variable roundCode 보고 회차 코드
     */
    private long roundCode;
    /**
     * @variable reportCode 보고 코드
     */
    private long reportCode;
    /**
     * @variable roundTitle 보고 회차 제목
     */
    private String roundTitle;
    /**
     * @variable roundBody 보고 회차 내용
     */
    private String roundBody;
    /**
     * @variable regDate 보고 회차 등록일
     */
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime regDate;
    /**
     * @variable 상세 보고 갯수
     */
    private int reportedMemberCount;
    /**
     * @variable capacity 보고 정원
     */
    private int capacity;
}