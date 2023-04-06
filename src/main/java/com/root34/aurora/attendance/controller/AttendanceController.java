package com.root34.aurora.attendance.controller;

import com.root34.aurora.attendance.service.AttendanceService;
import com.root34.aurora.common.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
	@ClassName : AttendanceController
	@Date : 2023-03-23
	@Writer : 정근호
	@Description :
*/
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class AttendanceController {

	private final AttendanceService attendanceService;


	/**
		@MethodName : getAttendance
		@Date : 2023-03-23
		@Writer : 정근호
		@Description :
	*/
	@GetMapping("/attendance/{memberCode}")
	public ResponseEntity<ResponseDTO> selectAttendance(@PathVariable int memberCode) {

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료" , attendanceService.getAttendance(memberCode)));

	}

	/**
	 @MethodName : insertWorkTime
	 @Date : 2023-03-23
	 @Writer : 정근호
	 @Description :
	 */
	@PostMapping("/attendance/{memberCode}")
	public ResponseEntity<ResponseDTO> insertWorkTime(@PathVariable int memberCode) {

		attendanceService.insertWorkTime(memberCode);

		return ResponseEntity.ok(new ResponseDTO(HttpStatus.CREATED, "입력완료", attendanceService.getAttendance(memberCode)));
	}

	/**
		@MethodName : insertOffTime
		@Date : 2023-03-26
		@Writer : 정근호
		@Description :
	*/
	@PutMapping("/attendance/{memberCode}")
	public ResponseEntity<ResponseDTO> insertOffTime(@PathVariable int memberCode) {

		attendanceService.insertOffTime(memberCode);

		return ResponseEntity.ok(new ResponseDTO(HttpStatus.CREATED, "입력완료", attendanceService.getAttendance(memberCode)));

	}

	/**
		@MethodName : selectTime
		@Date : 2023-03-26
		@Writer : 정근호
		@Description :
	*/
	@GetMapping("/attendance/time/{memberCode}")
	public ResponseEntity<ResponseDTO> selectTime(@PathVariable int memberCode) {

		attendanceService.selectTime(memberCode);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료" , attendanceService.selectTime(memberCode)));

	}

	/**
		@MethodName : selectMonthTime
		@Date : 2023-03-26
		@Writer : 정근호
		@Description : 월별 평균근무 시간 근무일수 총 근무시간
	*/
	@GetMapping("/attendance/month-time/{memberCode}")
	public ResponseEntity<ResponseDTO> selectMonthTime(@PathVariable int memberCode) {

		attendanceService.selectMonthTime(memberCode);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료", attendanceService.selectMonthTime(memberCode)));
	}

	/**
	 @MethodName : selectWorkStatus
	 @Date : 2023-04-05
	 @Writer : 정근호
	 @Description :
	 */
	@GetMapping("/attendance/work-status/{memberCode}")
	public ResponseEntity<ResponseDTO> selectWorkStatus(@PathVariable int memberCode) {

		attendanceService.selectWorkStatus(memberCode);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 완료", attendanceService.selectWorkStatus(memberCode)));
	}

	/**
	 @MethodName : selectWorkStatus
	 @Date : 2023-04-05
	 @Writer : 정근호
	 @Description :
	 */
	@GetMapping("/attendance/time-day/{memberCode}")
	public ResponseEntity<ResponseDTO> selectTimeByDay(@PathVariable int memberCode) {

		attendanceService.selectTimeByDay(memberCode);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 완료", attendanceService.selectTimeByDay(memberCode)));
	}





}
