package com.root34.aurora.schedule.dao;

import com.root34.aurora.schedule.dto.ScheduleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    List<ScheduleDTO> selectScheduleCalendarAboutMonth();

    List<ScheduleDTO> selectScheduleCalendarAboutWeek();

    List<ScheduleDTO> selectScheduleCalendarAboutDay();

    ScheduleDTO selectSchedule(int scheduleCode);

    int insertSchedule(ScheduleDTO scheduleDTO);

    int updateSchedule(ScheduleDTO scheduleDTO);

    int deleteSchedule(int scheduleCode);
}
