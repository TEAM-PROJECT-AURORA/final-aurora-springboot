package com.root34.aurora.schedule.service;

import com.root34.aurora.schedule.dao.ScheduleMapper;
import com.root34.aurora.schedule.dto.ScheduleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 @ClassName : ScheduleService
 @Date : 23.03.20
 @Writer : 서지수
 @Description : 일정관리 서비스
 */
@Slf4j
@Service
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;

    public ScheduleService(ScheduleMapper scheduleMapper) { this.scheduleMapper = scheduleMapper; }

    /**
     @MethodName  : selectScheduleCalendarAboutMe
     @Date : 23.03.20. -> 23.04.05
     @Writer : 서지수
     @Description : 나의 캘린더 조회
     */
    public List<ScheduleDTO> selectScheduleCalendarAboutMe(int memberCode) {

        log.info("[ScheduleService] selectScheduleCalendarAboutMonth Start ====================");
        List<ScheduleDTO> selectScheduleCalendarAboutMonth = scheduleMapper.selectScheduleCalendarAboutMe(memberCode);
        log.info("[ScheduleService] selectScheduleCalendarAboutMonth End ====================");

        return selectScheduleCalendarAboutMonth;
    }

    /**
     @MethodName  : selectScheduleCalendarAboutTeam
     @Date : 23.03.20.
     @Writer : 서지수
     @Description : 팀 캘린더 조회
     */
    public List<ScheduleDTO> selectScheduleCalendarAboutTeam(String teamCode) {

        log.info("[ScheduleService] selectScheduleCalendarAboutWeek Start ====================");
        List<ScheduleDTO> selectScheduleCalendarAboutWeek = scheduleMapper.selectScheduleCalendarAboutTeam(teamCode);
        log.info("[ScheduleService] selectScheduleCalendarAboutWeek End ====================");

        return selectScheduleCalendarAboutWeek;
    }

//    public List<ScheduleDTO> selectScheduleCalendarAboutDay() {
//
//        log.info("[ScheduleService] selectScheduleCalendarAboutDay Start ====================");
//        List<ScheduleDTO> selectScheduleCalendarAboutDay = scheduleMapper.selectScheduleCalendarAboutDay();
//        log.info("[ScheduleService] selectScheduleCalendarAboutDay End ====================");
//
//        return selectScheduleCalendarAboutDay;
//    }

    /**
     @MethodName  : selectScheduleDetail
     @Date : 23.03.20. -> 23.04.06
     @Writer : 서지수
     @Description : 일정관리 디테일 조회
     */
    public ScheduleDTO selectSchedule(int scheduelCode) {

        log.info("[ScheduleService] selectSchedule Start ====================");
//        List<ScheduleDTO> schedules = scheduleMapper.selectSchedule(scheduelCode);
        ScheduleDTO scheduleDTO = scheduleMapper.selectSchedule(scheduelCode);
        log.info("[ScheduleService] selectSchedule End ====================");

        return scheduleDTO;
    }

    /**
     @MethodName  : insertSchedule
     @Date : 23.03.21.
     @Writer : 서지수
     @Description : 일정관리 생성
     */
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

    /**
     @MethodName  : updateSchedule
     @Date : 23.03.21.
     @Writer : 서지수
     @Description : 일정관리 수정
     */
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

    /**
     @MethodName  : deleteSchedule
     @Date : 23.03.21.
     @Writer : 서지수
     @Description : 일정관리 삭제
     */
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
