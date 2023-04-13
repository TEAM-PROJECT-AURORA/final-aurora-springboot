package com.root34.aurora.worklog.dao;

import com.root34.aurora.worklog.dto.WeekWorklogDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 @ClassName : WeekWorklogMapper
 @Date : 23.03.25
 @Writer : 서지수
 @Description : 주간 업무일지 매퍼
 */
@Mapper
public interface WeekWorklogMapper {

    int selectWeekWorklogTotal(int memberCode);

    List<WeekWorklogDTO> selectWeekWorklogListWithPaging(Map map);

    WeekWorklogDTO selectWeekWorklog(int weekWorklogCode);

    int insertWeekWorklog(WeekWorklogDTO weekWorklogDTO);

    int updateWeekWorklog(WeekWorklogDTO weekWorklogDTO);

    int deleteWeekWorklog(int weekWorklogCode);
}
