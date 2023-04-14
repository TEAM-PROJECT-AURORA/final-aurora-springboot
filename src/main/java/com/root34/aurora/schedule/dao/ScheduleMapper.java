package com.root34.aurora.schedule.dao;

import com.root34.aurora.schedule.dto.ScheduleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 @ClassName : ScheduleMapper
 @Date : 23.03.20 -> 23.04.05
 @Writer : 서지수
 @Description : 일정관리 매퍼
 */
@Mapper
public interface ScheduleMapper {

    List<ScheduleDTO> selectScheduleCalendarAboutMe(int memberCode);

    List<ScheduleDTO> selectScheduleCalendarAboutTeam(String teamCode);

//    List<ScheduleDTO> selectScheduleCalendarAboutDay();

    ScheduleDTO selectSchedule(int scheduleCode);

    int insertSchedule(ScheduleDTO scheduleDTO);

    int updateSchedule(ScheduleDTO scheduleDTO);

    int deleteSchedule(int scheduleCode);
}
