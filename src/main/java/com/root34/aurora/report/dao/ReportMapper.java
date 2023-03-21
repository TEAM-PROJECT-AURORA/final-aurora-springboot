package com.root34.aurora.report.dao;

import com.root34.aurora.report.dto.ReportDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 @ClassName : ReportMapper
 @Date : 23.03.21.
 @Writer : 김수용
 @Description : 보고 SQL을 호출하기 위한 인터페이스
 */
@Mapper
public interface ReportMapper {

    int registerReportMap(ReportDTO reportDTO);

    ArrayList<ReportDTO> getRoutineReportList(Long memberCode);

    ArrayList<ReportDTO> getCasualReportList(Long memberCode);
}