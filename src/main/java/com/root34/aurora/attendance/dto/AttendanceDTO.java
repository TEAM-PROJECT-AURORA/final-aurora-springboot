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
	 * @param attCode       근태코드
	 * @param memberCode    사원코드
	 * @param workTime      출근시간
	 * @param offTime       퇴근시간
	 * @param tardy         지각
	 * @param earlyOff      조퇴
	 * @param truancy       무단결근
	 * @param absence       결근
	 * @param workStatus    근무상태
	 * @param attRegDate    근무날짜
	 */
     private int attCode;
	 private int memberCode;
	 private LocalDateTime workTime;
	 private LocalDateTime offTime;
	 private char tardy; //지각
	 private char earlyOff; //조퇴
	 private char truancy; //무단결근
	 private char absence; //결근
	 private String workStatus; //디폴트값 퇴근
	 private LocalDate attRegDate; //근무 날짜

}
