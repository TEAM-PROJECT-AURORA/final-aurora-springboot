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
		@ClassName : AttendanceService
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


	public void insertWorkTime(int memberCode) {

		LocalDateTime workTime = LocalDateTime.now();
		attendanceMapper.insertWorkTime(workTime ,memberCode);
	}



}
