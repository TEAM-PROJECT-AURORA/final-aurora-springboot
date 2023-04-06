package com.root34.aurora.attendance.dao;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
	@ClassName : AttendanceMapper
	@Date : 2023-03-23
	@Writer : 정근호
	@Description :
*/
@Mapper
public interface AttendanceMapper {

	Map getAttendance(int memberCode);

	void insertWorkTime(LocalDateTime workTime, int memberCode);

	void insertOffTime(LocalDateTime offTime, int memberCode);

	Map selectWorkStatus(int memberCode);

	Map selectTime(int memberCode);

	Map selectTimeByDay(int memberCode , LocalDate attRegDate);

	Map selectMonthTime(int MemberCode);

}
