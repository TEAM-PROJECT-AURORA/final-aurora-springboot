package com.root34.aurora.attendance.service;

import com.root34.aurora.attendance.dao.AttendanceMapper;
import com.root34.aurora.attendance.dto.AttendanceDTO;
import com.root34.aurora.common.paging.SelectCriteria;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
	@ClassName : AttendanceService
	@Date : 2023-03-23
	@Writer : 정근호
	@Description : 근태 서비스
*/
@Slf4j
@AllArgsConstructor
@Service
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;

	/**
	    @MethodName : getAttendance
		@Date : 2023-03-23
		@Writer : 정근호
		@Description :근태 조회
	*/
	public Map getAttendance(int memberCode, String selectedDate) {



		log.info("[AttendanceService] getAttendance Start ===================");
		log.info("[memberCode]   :" + memberCode );
		Map count = attendanceMapper.getAttendance(memberCode, selectedDate);
		log.info("[count]   :" + count );
		log.info("[AttendanceService] getAttendance End ===================");

		return count;

	}

	/**
	 @MethodName : selectTime
	 @Date : 2023-03-24
	 @Writer : 정근호
	 @Description : 이번주 누적,초과,잔여 이번달 누적 시간 조회
	 */

	public Map selectTime(int memberCode , String selectTime) {

		log.info("[AttendanceService] selectTime Start ===================");
		Map selectTimes = attendanceMapper.selectTime(memberCode, selectTime);
		log.info("[memberCode]   :" + memberCode );
		log.info("[selectTimes]   :" + selectTimes );
		log.info("[AttendanceService] selectTime End ===================");

		return selectTimes;


	}

	/**
	    @MethodName : insertWorkTime
		@Date : 2023-03-23
		@Writer : 정근호
	    @Description : 출근시간 등록
	*/
	public void insertWorkTime(int memberCode) {

		log.info("[AttendanceService] insertWorkTime Start ===================");
		LocalDateTime workTime = LocalDateTime.now();
		attendanceMapper.insertWorkTime(workTime ,memberCode);
		log.info("[AttendanceService] insertWorkTime End ===================");
	}

	/**
		@MethodName : insertOffTime
		@Date : 2023-03-24
		@Writer : 정근호
		@Description : 퇴근시간 등록
	*/
	public void insertOffTime(int memberCode) {

		log.info("[AttendanceService] insertOffTime Start ===================");
		LocalDateTime offTime = LocalDateTime.now();
		attendanceMapper.insertOffTime(offTime, memberCode);
		log.info("[AttendanceService] insertOffTime End ===================");
	}

	/**
	 @MethodName : insertOrUpdateAttendance
	 @Date : 2023-04-09
	 @Writer : 정근호
	 @Description : 근태 수정 및 등록
	 */
	public void insertOrUpdateAttendance(int memberCode, AttendanceDTO attendanceDTO, String selectedDate) {

		log.info("[AttendanceService] insertOrUpdateAttendance Start ===================");

		attendanceMapper.insertOrUpdateAttendance(memberCode,attendanceDTO, selectedDate);
		log.info("[AttendanceService] insertOrUpdateAttendance End ===================");
	}

	/**
	 @MethodName : selectMonthTime
	 @Date : 2023-03-24
	 @Writer : 정근호
	 @Description :월별 평균근무 시간 근무일수 총 근무시간
	 */
	public Map selectMonthTime(int memberCode) {

		log.info("[AttendanceService] selectMonthTime Start ===================");
		Map selectMonthTimeTime = attendanceMapper.selectMonthTime(memberCode);
		log.info("[memberCode]   :" + memberCode );
		log.info("[selectMonthTimeTime]   :" + selectMonthTimeTime );
		log.info("[AttendanceService] selectMonthTime End ===================");

		return selectMonthTimeTime;
	}

	/**
	 @MethodName : selectWorkStatus
	 @Date : 2023-04-05
	 @Writer : 정근호
	 @Description : 근무 상태 조회
	 */
	public Map selectWorkStatus(int memberCode) {

		log.info("[AttendanceService] selectWorkStatus Start ===================");
		Map selectWorkStatus = attendanceMapper.selectWorkStatus(memberCode);
		log.info("[memberCode]   :" + memberCode );
		log.info("[selectWorkStatus]   :" + selectWorkStatus );
		log.info("[AttendanceService] selectWorkStatus End ===================");

		return selectWorkStatus;

	}
	/**
	 @MethodName : selectTimeByDay
	 @Date : 2023-04-05
	 @Writer : 정근호
	 @Description :근무 시간 조회
	 */

	public Map selectTimeByDay(int memberCode, LocalDate attRegDate) {

		log.info("[AttendanceService] selectTimeByDay Start ===================");
		Map selectTimeByDay = attendanceMapper.selectTimeByDay(memberCode, attRegDate);
		log.info("[memberCode]   :" + memberCode );
		log.info("[selectTimeByDay]   :" + selectTimeByDay );
		log.info("[AttendanceService] selectTimeByDay End ===================");

		return selectTimeByDay;

	}

	/**
	 @MethodName : attendanceList
	 @Date : 2023-04-10
	 @Writer : 정근호
	 @Description :근태 조회 리스트
	 */

	public Object attendanceList(SelectCriteria selectCriteria,  String selectedDate) {

		log.info("[AttendanceService] attendanceList Start =========================");
		List<AttendanceDTO> attendanceList = attendanceMapper.attendanceList(selectCriteria, selectedDate);

		log.info("[MemberService] selectMemberListWithPaging End =============================");

		return attendanceList;
	}

	public List<AttendanceDTO> getAttendanceList(@RequestParam(name="selectedDate") String selectedDate) {

		log.info("[AttendanceService] getAttendanceList Start =========================");

		log.info("[MemberService] getAttendanceList End =============================");
		return attendanceMapper.getAttendanceList(selectedDate);

	}

}
