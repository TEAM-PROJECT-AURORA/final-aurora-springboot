package com.root34.aurora.worklog.dao;

import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.worklog.dto.DayWorklogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DayWorklogMapper {
    
    int selectDayWorklogTotal();

    List<DayWorklogDTO> selectDayWorklogListWithPaging(SelectCriteria selectCriteria);

    DayWorklogDTO selectDayWorklog(int dayWorklogCode);

    int insertDayWorklog(DayWorklogDTO dayWorklogDTO);

    int updateDayWorklog(DayWorklogDTO dayWorklogDTO);

    int deleteDayWorklog(int dayWorklogCode);
}
