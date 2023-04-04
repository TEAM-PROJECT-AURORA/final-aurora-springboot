package com.root34.aurora.report.service;

import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.exception.DataNotFoundException;
import com.root34.aurora.exception.NotAuthorException;
import com.root34.aurora.exception.report.AlreadyCompletedReportException;
import com.root34.aurora.exception.report.NotInvolvedInReportException;
import com.root34.aurora.exception.report.NotReportSupervisorException;
import com.root34.aurora.report.dto.ReportDTO;
import com.root34.aurora.report.dto.ReportDetailDTO;
import com.root34.aurora.report.dto.ReportRoundDTO;
import com.root34.aurora.report.dto.ReportRoundReplyDTO;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
//@AutoConfigureTestDatabase
public class ReportServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceTest.class);
    private final int MEMBER_CODE = 1;
    private final String REPORT_TYPE = "Casual";
    private final char COMPLETION_STATUS = 'N';

    @Autowired
    private ReportService reportService;

    @Test
    public void 보고_맵퍼_의존성_주입_테스트() {

        assertNotNull(reportService);
    }

    @Test
    void 보고_관련자_체크_서비스_테스트() {

        // given
        int memberCode = 1;
        Long reportCode1 = 1L;
        Long reportCode2 = 0L;

        // when

        // then
        reportService.verifyMemberReportAccess(memberCode, reportCode1);

        assertThrows(NotInvolvedInReportException.class, () -> {
            reportService.verifyMemberReportAccess(memberCode, reportCode2);
        });
    }

    @Test
    void 보고_책임자_확인_서비스_테스트() {

        // given
        int memberCode = 1;
        long reportCode = 1L;

        // when
        boolean result = reportService.countInChargeMember(memberCode, reportCode);

        // then
        assertEquals(true, result);
    }

    @Test
    void 완료된_보고인지_체크_서비스_테스트() {

        // given
        long reportCode = 1L;

        // when

        // then
        assertThrows(AlreadyCompletedReportException.class, () -> {
            reportService.isReportNotCompleted(reportCode);
        });
    }

    @Test
    void 상세보고_작성자_확인_서비스_테스트() {

        // given
        int memberCode = 0;
        long detailCode1 = 0L;
        long detailCode2 = 2L;

        // when

        // then
        assertThrows(DataNotFoundException.class, () -> {
            reportService.isDetailReportAuthor(memberCode, detailCode1);
        });
        assertThrows(NotAuthorException.class, () -> {
            reportService.isDetailReportAuthor(memberCode, detailCode2);
        });
    }

    @Test
    void 보고_댓글_작성자_확인_서비스_테스트() {

        // given
        int memberCode = 2;
        long replyCode1 = 0L;
        long replyCode2 = 2L;

        // when

        // then
        assertThrows(NotFoundException.class, () -> {
            reportService.isReplyAuthor(memberCode, replyCode1);
        });
        assertThrows(NotAuthorException.class, () -> {
            reportService.isReplyAuthor(memberCode, replyCode2);
        });
    }

    @Test
    @Transactional
    @Rollback
    void 보고_읽음상태_수정_읽음_서비스_테스트() {

        // given
        int memberCode = 1;
        long reportCode = 1L;

        // when

        // then
        reportService.updateReportReadStatusToRead(memberCode, reportCode);
    }
    
    @Test
    @Transactional
    @Rollback
    void 보고_읽음상태_수정_읽지않음_서비스_테스트() {

        // given
        long reportCode = 1L;

        // when

        // then
        reportService.updateReportReadStatusToUnread(reportCode);
    }

    @Test
    @Transactional
    @Rollback
    void 보고_완료상태_수정_완료_서비스_테스트() {

        // given
        int memberCode1 = 0;
        int memberCode2 = 1;
        long reportCode = 1L;

        // when

        // then
        assertThrows(NotReportSupervisorException.class, () -> {
            reportService.updateReportCompletionStatusToComplete(memberCode1, reportCode);
        });
        reportService.updateReportCompletionStatusToComplete(memberCode2, reportCode);
    }

    @Test
    @Transactional
    @Rollback
    void 보고_작성_서비스_테스트() {

        // given
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setMemberCode(MEMBER_CODE);
        reportDTO.setReportType(REPORT_TYPE);
        reportDTO.setReportTitle("TestReportTitle");
        reportDTO.setReportInfo("TestReportInfo");
        reportDTO.setReportCycle("Mon");

        List<Integer> memberList = new ArrayList<>();
        memberList.add(2);
        memberList.add(3);

        // 가상의 파일 생성
        byte[] content1 = "Hello, world!".getBytes();
        String fileName1 = "test.txt";
        MultipartFile file1 = new MockMultipartFile("file", fileName1, "text/plain", content1);

        byte[] content2 = "Hello, world!".getBytes();
        String fileName2 = "test.jpg";
        MultipartFile file2 = new MockMultipartFile("file", fileName2, "jpg/plain", content2);

        List<MultipartFile> fileList = new ArrayList<MultipartFile>();
        fileList.add(file1);
        fileList.add(file2);

        // when
        boolean result = reportService.registerReport(reportDTO, memberList, fileList);

        // then
        assertEquals(true, result);
    }

    @Test
    void 전체_보고_조회_서비스_테스트() {

        // given
        int memberCode1 = 0;
        int memberCode2 = 1;

        // when
        HashMap<String, Object> result = reportService.getReportSummary(memberCode2);


        // then
        assertThrows(DataNotFoundException.class, () -> {
            reportService.getReportSummary(memberCode1);
        });
        assertNotNull(result);
    }

    @Test
    @Transactional
    @Rollback
    void 보고_회차_등록_서비스_테스트() {

        // given
        ReportRoundDTO reportRoundDTO1 = new ReportRoundDTO();
        reportRoundDTO1.setReportCode(0);
        reportRoundDTO1.setRoundBody("Test");
        ReportRoundDTO reportRoundDTO2 = new ReportRoundDTO();
        reportRoundDTO2.setReportCode(1);
        reportRoundDTO2.setRoundBody("Test");

        // when
        boolean result = reportService.registerReportRound(reportRoundDTO2);

        // then
        assertThrows(NullPointerException.class, () -> {
            reportService.registerReportRound(reportRoundDTO1);
        });
        assertEquals(true, result);
    }

    @Test
    @Transactional
    @Rollback
    void 보고_수정_서비스_테스트() {

        // given
        int memberCode = 1;

        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReportCode(5L);
        reportDTO.setReportTitle("Modified Title");
        reportDTO.setReportInfo("Modified Information");
        reportDTO.setMemberCode(1);
        reportDTO.setReportCycle("Tue");
        reportDTO.setCompletionStatus('N');

        List<Integer> memberList = new ArrayList<>();
        memberList.add(1);
        memberList.add(2);

        // when
        boolean result = reportService.updateReport(memberCode, reportDTO, memberList);

        // then
        assertEquals(true, result);
    }

    @Test
    void 조건별_보고_목록_조회_서비스_테스트() {

        // given
        int offset = 1;
        int memberCode = 1;
        String reportType = "Routine";
        char completionStatus = 'N';

        HashMap<String, Object> searchConditions = new HashMap<>();
        searchConditions.put("memberCode", memberCode);
        searchConditions.put("reportType", reportType);
        searchConditions.put("completionStatus", completionStatus);

        // when
        ResponseDTOWithPaging result = reportService.selectReportListByConditions(offset, searchConditions);

        // then
        assertNotNull(result);
    }

    @Test
    void 보고_회차_목록_조회_서비스_테스트() {

        // given
        int memberCode = 1;
        long reportCode = 1L;
        int offset = 1;

        // when
        ResponseDTOWithPaging result = reportService.selectReportRoundListByReportCode(memberCode, reportCode, offset);

        // then
        assertNotNull(result);
    }

    @Test
    void 정기보고_회차_상세_조회_서비스_테스트() {

        // given
        int memberCode = 1;
        long reportCode = 1L;
        long roundCode = 1L;

        // when
        HashMap<String, Object> result = reportService.selectReportRoundDetailByRoundCode(memberCode, reportCode, roundCode);

        // then
        assertNotNull(result);
    }

    @Test
    void 비정기보고_상세_조회_서비스_테스트() {

        // given
        int memberCode = 1;
        long reportCode = 1L;

        // when
        HashMap<String ,Object> result = reportService.selectCasualReportDetailByReportCode(memberCode, reportCode);

        // then
        assertNotNull(result);
    }

    @Test
    @Transactional
    @Rollback
    void 회차별_상세_보고_작성_서비스_테스트() {

        // given
        long reportCode = 11L;

        ReportDetailDTO reportDetailDTO = new ReportDetailDTO();
        reportDetailDTO.setRoundCode(20L);
        reportDetailDTO.setMemberCode(1);
        reportDetailDTO.setDetailBody("TestBody");

        // when
        boolean result = reportService.registerReportDetail(reportCode, reportDetailDTO);

        // then
        assertEquals(true, result);
    }

    @Test
    @Transactional
    @Rollback
    void 회차별_상세_보고_수정_서비스_테스트() {

        // given
        long reportCode = 11L;

        ReportDetailDTO reportDetailDTO = new ReportDetailDTO();
        reportDetailDTO.setDetailCode(8);
        reportDetailDTO.setRoundCode(20L);
        reportDetailDTO.setMemberCode(1);
        reportDetailDTO.setDetailBody("TestBody");

        // when
        boolean result = reportService.updateReportDetail(reportCode, reportDetailDTO);

        // then
        assertEquals(true, result);
    }

    @Test
    void 회차별_상세보고_목록_조회_서비스_테스트() {

        // given
        int memberCode = 1;
        long reportCode = 1L;
        long roundCode = 1;

        // when
        List<ReportDetailDTO> result = reportService.selectReportDetailListByRoundCode(memberCode, reportCode, roundCode);

        // then
        assertNotNull(result);
    }

    @Test
    @Transactional
    @Rollback
    void 상세_보고_삭제_서비스_테스트() {

        // given
        int memberCode = 1;
        long detailCode = 2L;

        // when
        boolean result = reportService.deleteReportDetail(memberCode, detailCode);

        // then
        assertEquals(true, result);
    }

    @Test
    @Transactional
    @Rollback
    void 보고_댓글_작성_서비스_테스트() {

        // given
        long reportCode = 11L;

        ReportRoundReplyDTO reportRoundReplyDTO = new ReportRoundReplyDTO();
        reportRoundReplyDTO.setRoundCode(21L);
        reportRoundReplyDTO.setMemberCode(1);
        reportRoundReplyDTO.setReplyBody("Test Report Reply Body");

        // when
        boolean result = reportService.registerReportRoundReply(reportCode, reportRoundReplyDTO);

        // then
        assertTrue(result);
    }

    @Test
    void 보고_댓글_목록_조회_서비스_테스트() {

        // given
        int memberCode = 1;
        long reportCode = 11L;
        long roundCode = 21L;

        // when
        List<ReportRoundReplyDTO> result = reportService.selectReportRoundReply(memberCode, reportCode, roundCode);

        // then
        assertNotNull(result);
    }

    @Test
    @Transactional
    @Rollback
    void 보고_댓글_수정_서비스_테스트() throws Exception {

        // given
        ReportRoundReplyDTO reportRoundReplyDTO = new ReportRoundReplyDTO();
        reportRoundReplyDTO.setMemberCode(1);
        reportRoundReplyDTO.setReplyCode(2L);
        reportRoundReplyDTO.setReplyBody("Modified Test Report Reply Body");

        // when
        boolean result = reportService.updateReportRoundReply(reportRoundReplyDTO);

        // then
        assertTrue(result);
    }

    @Test
    @Transactional
    @Rollback
    void 보고_댓글_삭제() throws Exception {

        // given
        int memberCode = 1;
        long replyCode = 2L;

        // when
        boolean result = reportService.deleteReportRoundReply(memberCode, replyCode);

        // then
        assertTrue(result);
    }
}
