package com.root34.aurora.schedule.service;

import com.root34.aurora.schedule.dao.ScheduleMapper;
import com.root34.aurora.schedule.dto.ScheduleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;

    public ScheduleService(ScheduleMapper scheduleMapper) { this.scheduleMapper = scheduleMapper; }

    public List<ScheduleDTO> selectScheduleCalendarAboutMe(int memberCode) {

        log.info("[ScheduleService] selectScheduleCalendarAboutMonth Start ====================");
        List<ScheduleDTO> selectScheduleCalendarAboutMonth = scheduleMapper.selectScheduleCalendarAboutMe(memberCode);
        log.info("[ScheduleService] selectScheduleCalendarAboutMonth End ====================");

        return selectScheduleCalendarAboutMonth;
    }

    public List<ScheduleDTO> selectScheduleCalendarAboutTeam(String teamCode) {

        log.info("[ScheduleService] selectScheduleCalendarAboutWeek Start ====================");
        List<ScheduleDTO> selectScheduleCalendarAboutWeek = scheduleMapper.selectScheduleCalendarAboutTeam(teamCode);
        log.info("[ScheduleService] selectScheduleCalendarAboutWeek End ====================");

        return selectScheduleCalendarAboutWeek;
    }

    public List<ScheduleDTO> selectScheduleCalendarAboutDay() {

        log.info("[ScheduleService] selectScheduleCalendarAboutDay Start ====================");
        List<ScheduleDTO> selectScheduleCalendarAboutDay = scheduleMapper.selectScheduleCalendarAboutDay();
        log.info("[ScheduleService] selectScheduleCalendarAboutDay End ====================");

        return selectScheduleCalendarAboutDay;
    }

    public List<ScheduleDTO> selectSchedule(Map map) {

        log.info("[ScheduleService] selectSchedule Start ====================");
        List<ScheduleDTO> schedules = scheduleMapper.selectSchedule(map);
        log.info("[ScheduleService] selectSchedule End ====================");

        return schedules;
    }

    @Transactional
    public Object insertSchedule(ScheduleDTO scheduleDTO) {

        log.info("[ScheduleService] insertSchedule Start ====================");
        log.info("[ScheduleService] scheduleDTO : " + scheduleDTO);
        int result = 0;
        result = scheduleMapper.insertSchedule(scheduleDTO);
        log.info("[ScheduleService] insertSchedule End ====================");
        log.info("[ScheduleService] result > 0 성공 : " + result);

        return (result > 0) ? "일정 입력 성공" : "일정 입력 실패";
    }

    @Transactional
    public Object updateSchedule(ScheduleDTO scheduleDTO) {

        log.info("[ScheduleService] updateSchedule Start ====================");
        log.info("[ScheduleService] scheduleDTO : " + scheduleDTO);
        int result = 0;
        result = scheduleMapper.updateSchedule(scheduleDTO);
        log.info("[ScheduleService] updateSchedule End ====================");
        log.info("[ScheduleService] result > 0 성공 : " + result);

        return (result > 0) ? "일정 업데이트 성공" : "일정 업데이트 실패";
    }

    @Transactional
    public Object deleteSchedule(int scheduleCode) {

        log.info("[ScheduleService] deleteSchedule Start ====================");
        log.info("[ScheduleService] scheduleCode : " + scheduleCode);
        int result = 0;
        result = scheduleMapper.deleteSchedule(scheduleCode);
        log.info("[ScheduleService] deleteSchedule End ====================");
        log.info("[ScheduleService] result > 0 성공 : " + result);

        return (result > 0) ? "일정 삭제 성공" : "일정 삭제 실패";
    }
}
