package com.root34.aurora.worklog.dao;

import com.root34.aurora.worklog.dto.WeekWorklogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WeekWorklogMapper {

    int selectWeekWorklogTotal(int memberCode);

    List<WeekWorklogDTO> selectWeekWorklogListWithPaging(Map map);

    WeekWorklogDTO selectWeekWorklog(int weekWorklogCode);

    int insertWeekWorklog(WeekWorklogDTO weekWorklogDTO);

    int updateWeekWorklog(WeekWorklogDTO weekWorklogDTO);

    int deleteWeekWorklog(int weekWorklogCode);
}
