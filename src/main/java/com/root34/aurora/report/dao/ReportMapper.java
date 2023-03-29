package com.root34.aurora.report.dao;

import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.common.FileDTO;
import com.root34.aurora.report.dto.ReportDTO;
import com.root34.aurora.report.dto.ReportDetailDTO;
import com.root34.aurora.report.dto.ReportRoundDTO;
import com.root34.aurora.report.dto.ReportRoundReplyDTO;
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

    int registerFileWithReportCode(FileDTO fileDTO); // 보고_첨부파일 등록

    int registerReporter(HashMap<String, Object> parameter); // 보고자 등록

    int getReportCount(HashMap<String, Object> searchConditions); // 조건별 보고 갯수 조회

    List<ReportDTO> selectReportListWithPaging(SelectCriteria selectCriteria); // 조건별 보고 목록 조회

    List<Long> selectThreeReportCodesByMemberCode(int memberCode); // 최근 정기보고 3개

    List<ReportRoundDTO> selectReportRoundSummaryListByReportCode(Long reportCode); // 정기보고 회차 요약 목록 조회 - 메인 페이지 요약 출력

    List<ReportDTO> selectCasualReportListByMemberCode(int memberCode); // 비정기 보고 10개 조회

    int getReportRoundCapacity(Long reportCode); // 보고 회차 정원 조회

    int registerReportRound(ReportRoundDTO reportRoundDTO); // 보고 회차 등록

    int updateReport(ReportDTO reportDTO); // 보고 수정

    int deleteReporter(Long reportCode); // 보고자 삭제

    int getReportRoundCount(Long reportCode); // 보고 회차 갯수 조회

    List<ReportRoundDTO> selectReportRoundListByReportCode(SelectCriteria selectCriteria); // 보고 회차 목록 조회

    List<Integer> selectMemberListInvolvedInReport(Long reportCode); // 보고 관련자 목록 조회

    ReportRoundDTO selectReportRoundDetailByRoundCode(Long reportCode); // 보고 회차 상세 조회

    ReportDTO selectCasualReportDetailByReportCode(Long reportCode); // 비정기보고 상세 조회

    List<FileDTO> selectReportAttachmentListByReportCode(Long reportCode); // 비정기보고 첨부파일 목록 조회

    int updateReportCompletionStatus(HashMap<String, Object> parameter); // 보고 완료상태 수정

    int updateReportReadStatus(HashMap<String, Object> parameter); // 보고 읽음상태 수정

    int countInChargeMember(HashMap<String, Object> parameter); // 보고 책임자 확인

    String selectReportType(Long reportCode); // 보고 유형 확인

    int registerReportDetail(ReportDetailDTO reportDetailDTO); // 회차별 상세 보고 작성

    char selectReportCompletionStatus(long reportCode); // 보고 완료 상태 확인

    int updateReportDetail(ReportDetailDTO reportDetailDTO); // 회차별 상세 보고 수정

    Integer selectMemberCodeByDetailCode(long detailCode); // 상세 보고 작성자 본인 확인

    List<ReportDetailDTO> selectReportDetailListByRoundCode(long roundCode); // 회차별 상세 보고 목록 조회

    int deleteReportDetail(long detailCode); // 회차별 상세 보고 삭제

    int registerReportRoundReply(ReportRoundReplyDTO reportRoundReplyDTO); // 보고 댓글 작성

    List<ReportRoundReplyDTO> selectReportRoundReply(long roundCode); // 보고 댓글 목록 조회

    int updateReportRoundReply(ReportRoundReplyDTO reportRoundReplyDTO); // 보고 댓글 수정

    int deleteReportRoundReply(long replyCode); // 보고 댓글 삭제

    Integer selectMemberCodeByReplyCode(long replyCode); // 보고 댓글 작성자 확인
}
