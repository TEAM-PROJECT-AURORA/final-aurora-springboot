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
	 * @variable attCode 근태코드
	 **/
     private int attCode;
	/**
	 * @variable memberCode 사원코드
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
	 private String tardy; //지각
	/**
	 * @variable earlyOff 조퇴
	 **/
	 private String earlyOff; //조퇴
	/**
	 * @variable truancy 무단결근
	 **/
	 private String truancy; //무단결근
	/**
	 * @variable absence 결근
	 **/
	 private String absence; //결근
	/**
	 * @variable workStatus 근무상태
	 **/
	 private String workStatus; //디폴트값 퇴근
	/**
	 * @variable attRegDate 근무날짜
	 **/
	 private LocalDate attRegDate; //근무 날짜
	/**
	 * @variable phone 핸드폰
	 **/
	private String phone;
	/**
	 * @variable deptName 부서명
	 **/
	private String deptName;
	/**
	 * @variable jobName 직급명
	 **/
	private String jobName;
	/**
	 * @variable taskName 직무명
	 **/
	private String taskName;
	/**
	 * @variable teamName 팀명
	 **/
	private String teamName;
	/**
	 * @variable memberEmail 이메일
	 **/
	private String memberEmail;

	private String deptCode;
	/**
	 * @variable jobCode 직윝코드
	 **/
	private String jobCode;
	/**
	 * @variable taskCode 직무코드
	 **/
	private String taskCode;
	/**
	 * @variable memberName 이름
	 **/
	private String memberName;

	/**
	 * @variable teamCode 소속팀코드
	 **/
	private String teamCode;
//	/**
//	 * @variable tardySum 지각합
//	 **/
//	private int tardySum;
//	/**
//	 * @variable earlyOffSum 조퇴합
//	 **/
//	private int earlyOffSum;
//	/**
//	 * @variable truancySum 무단결급합
//	 **/
//	private int truancySum;
//	/**
//	 * @variable absenceSum 결근합
//	 **/
//	private int absenceSum;



}
