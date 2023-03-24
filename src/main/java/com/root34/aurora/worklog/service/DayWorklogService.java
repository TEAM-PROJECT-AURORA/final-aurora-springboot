package com.root34.aurora.worklog.service;

import com.root34.aurora.worklog.dao.DayWorklogMapper;
import com.root34.aurora.worklog.dto.DayWorklogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DayWorklogService {

    private final DayWorklogMapper dayWorklogMapper;

    public DayWorklogService(DayWorklogMapper dayWorklogMapper) { this.dayWorklogMapper = dayWorklogMapper; }

    public int selectDayWorklogTotal(int memberCode) {

        log.info("[DayWorklogService] selectDayWorklogTotal Start ====================");
        int result = dayWorklogMapper.selectDayWorklogTotal(memberCode);
        log.info("[DayWorklogService] selectDayWorklogTotal End ====================");

        return result;
    }

    public Object selectDayWorklogListWithPaging(Map map) {

        log.info("[DayWorklogService] selectDayWorklogListWithPaging Start ====================");
        List<DayWorklogDTO> dayWorklogList = dayWorklogMapper.selectDayWorklogListWithPaging(map);
        log.info("[DayWorklogService] selectDayWorklogListWithPaging End ====================");

        return dayWorklogList;
    }

    public Object selectDayWorklog(int dayWorklogCode) {

        log.info("[DayWorklogService] selectDayWorklog Start ====================");
        DayWorklogDTO dayWorklogDTO = dayWorklogMapper.selectDayWorklog(dayWorklogCode);
        log.info("[DayWorklogService] selectDayWorklog End ====================");

        return dayWorklogDTO;
    }

    @Transactional
    public Object insertDayWorklog(DayWorklogDTO dayWorklogDTO) {

        log.info("[DayWorklogService] insertDayWorklog Start ====================");
        log.info("[DayWorklogService] dayWorklogDTO : " + dayWorklogDTO);
        int result = 0;
        result = dayWorklogMapper.insertDayWorklog(dayWorklogDTO);
        log.info("[DayWorklogService] insertDayWorklog End ====================");
        log.info("[DayWorklogService] result > 0 성공 : " + result);

        return (result > 0) ? "일일 업무일지 등록 성공" : "일일 업무일지 등록 실패";
    }

    @Transactional
    public Object updateDayWorklog(DayWorklogDTO dayWorklogDTO) {

        log.info("[DayWorklogService] updateDayWorklog Start ====================");
        log.info("[DayWorklogService] dayWorklogDTO : " + dayWorklogDTO);
        int result = 0;
        result = dayWorklogMapper.updateDayWorklog(dayWorklogDTO);
        log.info("[DayWorklogService] updateDayWorklog End ====================");
        log.info("[DayWorklogService] result > 0 성공 : " + result);

        return (result > 0) ? "일일 업무일지 업데이트 성공" : "일일 업무일지 업데이트 실패";
    }

    @Transactional
    public Object deleteDayWorklog(int dayWorklogCode) {

        log.info("[DayWorklogService] deleteDayWorklog Start ====================");
        log.info("[ScheduleService] dayWorklogCode : " + dayWorklogCode);
        int result = 0;
        result = dayWorklogMapper.deleteDayWorklog(dayWorklogCode);
        log.info("[DayWorklogService] deleteDayWorklog End ====================");
        log.info("[DayWorklogService] result > 0 성공 : " + result);

        return (result > 0) ? "일일 업무일지 삭제 성공" : "일일 업무일지 삭제 실패";
    }
}
