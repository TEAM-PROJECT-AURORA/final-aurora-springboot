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

	@PutMapping("/attendance/{memberCode}")
	public ResponseEntity<ResponseDTO> insertOffTime(@PathVariable int memberCode) {

		attendanceService.insertOffTime(memberCode);

		return ResponseEntity.ok(new ResponseDTO(HttpStatus.CREATED, "입력완료", attendanceService.getAttendance(memberCode)));

	}

	@GetMapping("/attendance/time/{memberCode}")
	public ResponseEntity<ResponseDTO> selectTime(@PathVariable int memberCode) {

		attendanceService.selectTime(memberCode);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료" , attendanceService.selectTime(memberCode)));

	}






}
