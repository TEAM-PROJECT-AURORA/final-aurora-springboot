package com.root34.aurora.attendance.service;

import com.root34.aurora.attendance.dao.AttendanceMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Map;

/**
	@ClassName : AttendanceService
	@Date : 2023-03-23
	@Writer : 정근호
	@Description :
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
		@Description :
	*/
	public Map getAttendance(int memberCode) {



		log.info("[AttendanceService] getAttendance Start ===================");
		log.info("[memberCode]   :" + memberCode );
		Map count = attendanceMapper.getAttendance(memberCode);
		log.info("[count]   :" + count );
		log.info("[AttendanceService] getAttendance End ===================");

		return count;

	}

	/**
	 @MethodName : selectTime
	 @Date : 2023-03-24
	 @Writer : 정근호
	 @Description :
	 */

	public Map selectTime(int memberCode) {

		log.info("[AttendanceService] selectTime Start ===================");
		Map selectTime = attendanceMapper.selectTime(memberCode);
		log.info("[memberCode]   :" + memberCode );
		log.info("[selectTime]   :" + selectTime );
		log.info("[AttendanceService] selectTime End ===================");

		return selectTime;


	}

	/**
	    @MethodName : insertWorkTime
		@Date : 2023-03-23
		@Writer : 정근호
		@Description :
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
		@Description :
	*/
	public void insertOffTime(int memberCode) {

		log.info("[AttendanceService] insertOffTime Start ===================");
		LocalDateTime offTime = LocalDateTime.now();
		attendanceMapper.insertOffTime(offTime, memberCode);
		log.info("[AttendanceService] insertOffTime End ===================");
	}





}
