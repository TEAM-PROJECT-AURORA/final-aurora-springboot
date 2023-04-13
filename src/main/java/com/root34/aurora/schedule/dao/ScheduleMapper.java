package com.root34.aurora.schedule.dao;

import com.root34.aurora.schedule.dto.ScheduleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleMapper {

    List<ScheduleDTO> selectScheduleCalendarAboutMe(int memberCode);

    List<ScheduleDTO> selectScheduleCalendarAboutTeam(String teamCode);

    List<ScheduleDTO> selectScheduleCalendarAboutDay();

    List<ScheduleDTO> selectSchedule(Map map);

    int insertSchedule(ScheduleDTO scheduleDTO);

    int updateSchedule(ScheduleDTO scheduleDTO);

    int deleteSchedule(int scheduleCode);
}
