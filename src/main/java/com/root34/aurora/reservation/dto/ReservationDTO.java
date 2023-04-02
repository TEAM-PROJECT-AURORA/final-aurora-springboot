package com.root34.aurora.reservation.dto;

import lombok.Data;

import java.sql.Date;

/**
	@ClassName : ReservationDTO
	@Date : 2023-03-28
	@Writer : 오승재
	@Description : 예약 정보
*/
@Data
public class ReservationDTO {

    /**
    	* @variable 예약 번호 
    */
    private String reservationNo;
    /**
    	* @variable 자산 코드 
    */
    private String assetCode;
    /**
    	* @variable 자산명 
    */
    private String assetName;
    /**
    	* @variable 사원 코드 
    */
    private String memberCode;
    /**
    	* @variable 사원명 
    */
    private String memberName;
    /**
    	* @variable 소속팀 
    */
    private String team;
    /**
    	* @variable 자산 설명 
    */
    private String description;
    /**
    	* @variable 시간 시간 
    */
    private String startTime;
    /**
    	* @variable 끝 시간 
    */
    private String endTime;
    /**
    	* @variable 예약일 
    */
    private Date reservationDate;
}
