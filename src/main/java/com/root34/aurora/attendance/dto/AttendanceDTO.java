package com.root34.aurora.attendance.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
	@ClassName : AttendanceDTO
	@Date : 2023-03-22
	@Writer : 정근호
	@Description : 근태 관리 변수 선언
*/
@Data
public class AttendanceDTO {
	/**
	 * @variable attCode       근태코드
	 * @variable memberCode    사원코드
	 * @variable workTime      출근시간
	 * @variable offTime       퇴근시간
	 * @variable tardy         지각
	 * @variable earlyOff      조퇴
	 * @variable truancy       무단결근
	 * @variable absence       결근
	 * @variable workStatus    근무상태
	 * @variable attRegDate    근무날짜
	 */
	/**
	 * @variable attCode 근태코드
	 **/
     private int attCode;
	/**
	 * @variable attCode 근태코드
	 **/
	 private int memberCode;
	/**
	 * @variable workTime 출근시간
	 **/
	 private LocalDateTime workTime;
	/**
	 * @variable offTime 퇴근시간
	 **/
	 private LocalDateTime offTime;
	/**
	 * @variable tardy 지각
	 **/
	 private char tardy; //지각
	/**
	 * @variable earlyOff 조퇴
	 **/
	 private char earlyOff; //조퇴
	/**
	 * @variable truancy 무단결근
	 **/
	 private char truancy; //무단결근
	/**
	 * @variable absence 결근
	 **/
	 private char absence; //결근
	/**
	 * @variable workStatus 근무상태
	 **/
	 private String workStatus; //디폴트값 퇴근
	/**
	 * @variable attRegDate 근무날짜
	 **/
	 private LocalDate attRegDate; //근무 날짜


}
