package com.root34.aurora.vacation.dto;

import lombok.Data;

import java.time.LocalDate;

/**
	@ClassName : VacationDTO
	@Date : 2023-03-25
	@Writer : 정근호
	@Description :
*/
@Data
public class VacationDTO {
    //tbl_used_vacation
    /**
     * @variable vacationNo 휴가코드
     **/
    private int vacationNo;
    /**
     * @variable vacationStartDate 휴가시작일
     **/
    private LocalDate vacationStartDate;
    /**
     * @variable vacationEndDate 휴가마지막날
     **/
    private LocalDate vacationEndDate;
    /**
     * @variable isHalfDay 반차 사용여부
     **/
    private boolean isHalfDay;
    //tbl_vacation
    /**
     * @variable memberCode 사원코드
     **/
    private int memberCode;
    /**
     * @variable remainVacation 잔여휴가
     **/
    private double remainVacation;

    private String appStatus;




}
