package com.root34.aurora.report.dao;

import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.report.dto.ReportDTO;
import com.root34.aurora.report.dto.ReportRoundDTO;
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

    int registerReporter(HashMap<String, Object> parameter); // 보고자 등록

    int getReportCount(HashMap<String, Object> searchConditions); // 조건별 보고 갯수 조회

    List<ReportDTO> selectReportListWithPaging(SelectCriteria selectCriteria); // 조건별 보고 목록 조회

    List<Long> selectThreeReportCodesByMemberCode(int memberCode); // 최근 정기보고 3개

    List<ReportRoundDTO> selectReportRoundListByReportCode(Long reportCode); // 정기보고 회차 목록 조회 - 메인 페이지 요약 출력

    List<ReportDTO> selectCasualReportListByMemberCode(int memberCode); // 비정기 보고 10개 조회

    int getReportRoundCapacity(Long reportCode); // 보고 회차 정원 조회

    int registerReportRound(ReportRoundDTO reportRoundDTO); // 보고 회차 등록

    int updateReport(ReportDTO reportDTO); // 보고 수정

    int deleteReporter(Long reportCode); // 보고자 삭제
}