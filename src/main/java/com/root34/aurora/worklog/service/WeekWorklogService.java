package com.root34.aurora.worklog.service;

import com.root34.aurora.worklog.dao.WeekWorklogMapper;
import com.root34.aurora.worklog.dto.WeekWorklogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WeekWorklogService {

    private final WeekWorklogMapper weekWorklogMapper;

    public WeekWorklogService(WeekWorklogMapper weekWorklogMapper) { this.weekWorklogMapper = weekWorklogMapper; }

    public int selectWeekWorklogTotal(int memberCode) {

        log.info("[WeekWorklogService] selectWeekWorklogTotal Start ====================");
        int result = weekWorklogMapper.selectWeekWorklogTotal(memberCode);
        log.info("[WeekWorklogService] selectWeekWorklogTotal End ====================");

        return result;
    }

    public Object selectWeekWorklogListWithPaging(Map map) {

        log.info("[WeekWorklogService] selectWeekWorklogListWithPaging Start ====================");
        List<WeekWorklogDTO> weekWorklogList = weekWorklogMapper.selectWeekWorklogListWithPaging(map);
        log.info("[WeekWorklogService] selectWeekWorklogListWithPaging End ====================");

        return weekWorklogList;
    }

    public WeekWorklogDTO selectWeekWorklog(int weekWorklogCode) {

        log.info("[WeekWorklogService] selectWeekWorklog Start ====================");
        WeekWorklogDTO weekWorklogDTO = weekWorklogMapper.selectWeekWorklog(weekWorklogCode);
        log.info("[WeekWorklogService] selectWeekWorklog End ====================");

        return weekWorklogDTO;
    }

    @Transactional
    public Object insertWeekWorklog(WeekWorklogDTO weekWorklogDTO) {

        log.info("[WeekWorklogService] insertWeekWorklog Start ====================");
        log.info("[WeekWorklogService] weekWorklogDTO : " + weekWorklogDTO);
        int result = 0;
        result = weekWorklogMapper.insertWeekWorklog(weekWorklogDTO);
        log.info("[WeekWorklogService] insertWeekWorklog End ====================");
        log.info("[WeekWorklogService] result > 0 성공 : " + result);

        return (result > 0) ? "주간 업무일지 등록 성공" : "주간 업무일지 등록 실패";
    }

    @Transactional
    public Object updateWeekWorklog(WeekWorklogDTO weekWorklogDTO) {

        log.info("[WeekWorklogService] updateWeekWorklog Start ====================");
        log.info("[WeekWorklogService] weekWorklogDTO : " + weekWorklogDTO);
        int result = 0;
        result = weekWorklogMapper.updateWeekWorklog(weekWorklogDTO);
        log.info("[WeekWorklogService] updateWeekWorklog End ====================");
        log.info("[WeekWorklogService] result > 0 성공 : " + result);

        return (result > 0) ? "주간 업무일지 수정 성공" : "주간 업무일지 수정 실패";
    }

    @Transactional
    public Object deleteWeekWorklog(int weekWorklogCode) {

        log.info("[WeekWorklogService] deleteWeekWorklog Start ====================");
        log.info("[ScheduleService] weekWorklogCode : " + weekWorklogCode);
        int result = 0;
        result = weekWorklogMapper.deleteWeekWorklog(weekWorklogCode);
        log.info("[WeekWorklogService] deleteWeekWorklog End ====================");
        log.info("[WeekWorklogService] result > 0 성공 : " + result);

        return (result > 0) ? "일일 업무일지 삭제 성공" : "일일 업무일지 삭제 실패";
    }
}
