package com.root34.aurora.report.dto;

import lombok.Data;

/**
	@ClassName : ReportDetailDTO
	@Date : 2023-03-27
	@Writer : 김수용
	@Description : 상세 보고 정보를 담을 DTO
*/
@Data
public class ReportDetailDTO {

    /**
     * @variable detailCode 상세 보고 코드
     */
    private long detailCode;
    /**
     * @variable roundCode 회차 코드
     */
    private long roundCode;
    /**
     * @variable memberCode 사원 코드
     */
    private int memberCode;
    /**
     * @variable detailBody 상세 보고 내용
     */
    private String detailBody;
}
