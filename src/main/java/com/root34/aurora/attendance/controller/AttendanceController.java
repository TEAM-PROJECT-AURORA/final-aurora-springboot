package com.root34.aurora.attendance.controller;

import com.root34.aurora.attendance.dto.AttendanceDTO;
import com.root34.aurora.attendance.service.AttendanceService;
import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
	@ClassName : AttendanceController
	@Date : 2023-03-23
	@Writer : 정근호
	@Description : 근태 컨트롤러
*/
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class AttendanceController {

	private final AttendanceService attendanceService;
	private final MemberService memberService;


	/**
		@MethodName : getAttendance
		@Date : 2023-03-23
		@Writer : 정근호
		@Description : 근태 조회
	*/
	@GetMapping("/attendance/{memberCode}")
	public ResponseEntity<ResponseDTO> selectAttendance(@PathVariable int memberCode, @RequestParam(name = "selectedDate", required = false) String selectedDate) {

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료" , attendanceService.getAttendance(memberCode, selectedDate)));

	}

	/**
	 @MethodName : insertWorkTime
	 @Date : 2023-03-23
	 @Writer : 정근호
	 @Description : 출근시간 등록
	 */
	@PostMapping("/attendance/{memberCode}")
	public ResponseEntity<ResponseDTO> insertWorkTime(@PathVariable int memberCode) {

		attendanceService.insertWorkTime(memberCode);

		return ResponseEntity.ok(new ResponseDTO(HttpStatus.CREATED, "입력완료", attendanceService.selectWorkStatus(memberCode)));
	}

	/**
		@MethodName : insertOffTime
		@Date : 2023-03-26
		@Writer : 정근호
		@Description : 퇴근시간 등록
	*/
	@PutMapping("/attendance/{memberCode}")
	public ResponseEntity<ResponseDTO> insertOffTime(@PathVariable int memberCode) {

		attendanceService.insertOffTime(memberCode);

		return ResponseEntity.ok(new ResponseDTO(HttpStatus.CREATED, "입력완료",  attendanceService.selectWorkStatus(memberCode)));

	}

	/**
	 @MethodName : insertOrUpdateAttendance
	 @Date : 2023-04-09
	 @Writer : 정근호
	 @Description : 근태 수정 및 등록
	 */
	@PutMapping("/attendance/modify/{memberCode}")
	public ResponseEntity<ResponseDTO> insertOrUpdateAttendance(@PathVariable int memberCode, @RequestBody AttendanceDTO attendanceDTO,
																@RequestParam(name = "selectedDate", required = false) String selectedDate) {

		attendanceService.insertOrUpdateAttendance(memberCode, attendanceDTO,selectedDate );

		return ResponseEntity.ok(new ResponseDTO(HttpStatus.CREATED, "입력완료", attendanceService.getAttendance(memberCode, selectedDate)));

	}

	/**
		@MethodName : selectTime
		@Date : 2023-03-26
		@Writer : 정근호
		@Description : 이번주 누적,초과,잔여 이번달 누적 시간 조회
	*/
	@GetMapping("/attendance/time/{memberCode}")
	public ResponseEntity<ResponseDTO> selectTime(@PathVariable int memberCode, @RequestParam(name = "selectTime", required = false) String selectTime) {

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료" , attendanceService.selectTime(memberCode, selectTime)));

	}

	/**
		@MethodName : selectMonthTime
		@Date : 2023-03-26
		@Writer : 정근호
		@Description : 월별 평균근무 시간 근무일수 총 근무시간
	*/
	@GetMapping("/attendance/month-time/{memberCode}")
	public ResponseEntity<ResponseDTO> selectMonthTime(@PathVariable int memberCode ) {

		attendanceService.selectMonthTime(memberCode);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료", attendanceService.selectMonthTime(memberCode)));
	}

	/**
	 @MethodName : selectWorkStatus
	 @Date : 2023-04-05
	 @Writer : 정근호
	 @Description : 근무 상태 조회
	 */
	@GetMapping("/attendance/work-status/{memberCode}")
	public ResponseEntity<ResponseDTO> selectWorkStatus(@PathVariable int memberCode) {

		attendanceService.selectWorkStatus(memberCode);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 완료", attendanceService.selectWorkStatus(memberCode)));
	}

	/**
	 @MethodName : selectTimeByDay
	 @Date : 2023-04-06
	 @Writer : 정근호
	 @Description : 근무 시간 조회
	 */
	@GetMapping("/attendance/time-day/{memberCode}")
	public ResponseEntity<ResponseDTO> selectTimeByDay(@PathVariable int memberCode ,
													   @RequestParam("attRegDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate attRegDate) {

		attendanceService.selectTimeByDay(memberCode ,attRegDate);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 완료", attendanceService.selectTimeByDay(memberCode, attRegDate)));
	}

	/**
	 @MethodName : attendanceList
	 @Date : 2023-04-10
	 @Writer : 정근호
	 @Description : 근태 조회 리스트
	 */
	@GetMapping("/attendance/list")
	public ResponseEntity<ResponseDTO> attendanceList(@RequestParam(name="offset", defaultValue = "1") String offset,
													  @RequestParam(name = "selectedDate", required = false)  String selectedDate) {

		int totalCount = memberService.selectMemberTotal();
		int limit = 20;
		int buttonAmount = 5;
		SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount );

		log.info("[AttendanceController] selectCriteria : " + selectCriteria);
		ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
		responseDTOWithPaging.setPageInfo(selectCriteria);
		Object attendanceList = attendanceService.attendanceList(selectCriteria,selectedDate);
		List<AttendanceDTO> getAttendanceList = attendanceService.getAttendanceList(selectedDate);

		Map map = new HashMap();
		map.put("getAttendanceList", getAttendanceList);
		map.put("attendanceList", attendanceList);

		responseDTOWithPaging.setData(map);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 완료", responseDTOWithPaging));
	}




}
