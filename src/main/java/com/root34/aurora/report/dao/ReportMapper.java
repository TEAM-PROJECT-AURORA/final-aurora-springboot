package com.root34.aurora.report.dao;

import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.report.dto.ReportDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 @ClassName : ReportMapper
 @Date : 23.03.21.
 @Writer : 김수용
 @Description : 보고 SQL을 호출하기 위한 인터페이스
 */
@Mapper
public interface ReportMapper {

    int registerReport(ReportDTO reportDTO); // 보고서 작성

    int registerReporter(HashMap<String, Object> parameter);

    int getReportCount(HashMap<String, Object> searchConditions); // 조건별 보고 갯수 조회

    List<ReportDTO> selectReportListWithPaging(SelectCriteria selectCriteria); // 조건별 보고 목록 조회
}