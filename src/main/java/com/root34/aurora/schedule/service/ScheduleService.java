package com.root34.aurora.schedule.service;

import com.root34.aurora.schedule.dao.ScheduleMapper;
import com.root34.aurora.schedule.dto.ScheduleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;

    public ScheduleService(ScheduleMapper scheduleMapper) { this.scheduleMapper = scheduleMapper; }

    public List<ScheduleDTO> selectScheduleCalendarAboutMonth() {

        log.info("[ScheduleService] selectScheduleCalendarAboutMonth Start ====================");
        List<ScheduleDTO> selectScheduleCalendarAboutMonth = scheduleMapper.selectScheduleCalendarAboutMonth();
        log.info("[ScheduleService] selectScheduleCalendarAboutMonth End ====================");

        return selectScheduleCalendarAboutMonth;
    }

    public List<ScheduleDTO> selectScheduleCalendarAboutWeek() {

        log.info("[ScheduleService] selectScheduleCalendarAboutWeek Start ====================");
        List<ScheduleDTO> selectScheduleCalendarAboutWeek = scheduleMapper.selectScheduleCalendarAboutWeek();
        log.info("[ScheduleService] selectScheduleCalendarAboutWeek End ====================");

        return selectScheduleCalendarAboutWeek;
    }

    public List<ScheduleDTO> selectScheduleCalendarAboutDay() {

        log.info("[ScheduleService] selectScheduleCalendarAboutDay Start ====================");
        List<ScheduleDTO> selectScheduleCalendarAboutDay = scheduleMapper.selectScheduleCalendarAboutDay();
        log.info("[ScheduleService] selectScheduleCalendarAboutDay End ====================");

        return selectScheduleCalendarAboutDay;
    }

    @Transactional
    public Object insertSchedule(ScheduleDTO scheduleDTO) {

        log.info("[ScheduleService] insertSchedule Start ====================");
        log.info("[ScheduleService] scheduleDTO : " + scheduleDTO);
        int result = 0;
        result = scheduleMapper.insertSchedule(scheduleDTO);
        log.info("[ScheduleService] insertSchedule End ====================");

        return (result > 0) ? "일정 입력 성공" : "일정 입력 실패";
    }

    @Transactional
    public Object updateSchedule(ScheduleDTO scheduleDTO) {

        log.info("[ScheduleService] updateSchedule Start ====================");
        log.info("[ScheduleService] scheduleDTO : " + scheduleDTO);
        int result = 0;
        result = scheduleMapper.updateSchedule(scheduleDTO);
        log.info("[ScheduleService] updateSchedule End ====================");

        return (result > 0) ? "일정 업데이트 성공" : "일정 업데이트 실패";
    }
}
