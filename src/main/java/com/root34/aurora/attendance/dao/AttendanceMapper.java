package com.root34.aurora.attendance.dao;

import com.root34.aurora.attendance.dto.AttendanceDTO;
import com.root34.aurora.common.paging.SelectCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
	@ClassName : AttendanceMapper
	@Date : 2023-03-23
	@Writer : 정근호
	@Description :
*/
@Mapper
public interface AttendanceMapper {

	Map getAttendance(int memberCode , String selectedDate);

	void insertWorkTime(LocalDateTime workTime, int memberCode);

	void insertOffTime(LocalDateTime offTime, int memberCode);

	void insertOrUpdateAttendance(int memberCode, AttendanceDTO attendanceDTO, String selectedDate);

	Map selectWorkStatus(int memberCode);

	List<AttendanceDTO> getAttendanceList(String selectedDate);

	List<AttendanceDTO> attendanceList(SelectCriteria selectCriteria,  String selectedDate);

	Map selectTime(int memberCode, String selectTime);

	Map selectTimeByDay(int memberCode , LocalDate attRegDate);

	Map selectMonthTime(int MemberCode);

}
