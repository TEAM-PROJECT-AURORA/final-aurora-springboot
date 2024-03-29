package com.root34.aurora.worklog.dao;

import com.root34.aurora.worklog.dto.DayWorklogDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

/**
 @ClassName : DayWorklogMapper
 @Date : 23.03.23
 @Writer : 서지수
 @Description : 일일 업무일지 매퍼
 */
@Mapper
public interface DayWorklogMapper {

    int selectDayWorklogTotal(int memberCode);

    List<DayWorklogDTO> selectDayWorklogListWithPaging(Map map);

    DayWorklogDTO selectDayWorklog(int dayWorklogCode);

    int insertDayWorklog(DayWorklogDTO dayWorklogDTO);

    int updateDayWorklog(DayWorklogDTO dayWorklogDTO);

    int deleteDayWorklog(int dayWorklogCode);

    DayWorklogDTO selectMemberInfo(int memberCode);
}
