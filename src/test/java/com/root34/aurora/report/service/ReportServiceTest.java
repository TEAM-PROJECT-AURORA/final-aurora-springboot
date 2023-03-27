package com.root34.aurora.report.service;

import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.report.dao.ReportMapper;
import com.root34.aurora.report.dto.ReportDTO;
import com.root34.aurora.report.dto.ReportRoundDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
//    @MockBean
    private ReportMapper reportMapper;

    @Autowired
    private ReportService reportService;

    @Test
    public void 보고_맵퍼_의존성_주입_테스트() {

        assertNotNull(reportMapper);
        assertNotNull(reportService);
    }

    @Test
    void 보고_관련자_체크_서비스_테스트() {

        // given
        int memberCode = 1;
        Long reportCode1 = 5L;
        Long reportCode2 = 999999L;

        // when
        reportService.verifyMemberReportAccess(memberCode, reportCode1);
        reportService.verifyMemberReportAccess(memberCode, reportCode2);

        // then
    }

    @Transactional
    @Rollback(false)
    @Test
//    @Rollback
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

        log.info("[보고_작성_서비스_테스트] reportDTO : " + reportDTO);
        log.info("[보고_작성_서비스_테스트] memberList : " + memberList);

        // when
//        boolean result = reportService.registerReport(reportDTO, memberList, fileList);

        // then
//        assertEquals(true, result);
    }

    @Test
    void 정기_보고_갯수_조회_맵퍼_테스트() {

        // given
        HashMap<String, Object> conditions = new HashMap<>();
        conditions.put("memberCode", MEMBER_CODE);
        conditions.put("reportType", REPORT_TYPE);
        conditions.put("completionStatus", COMPLETION_STATUS);

        //when
        int result = reportMapper.getReportCount(conditions);

        // then
        assertNotEquals(0, result);
    }

    @Test
    void 완료되지_않은_정기_보고_목록_조회_맵퍼_테스트() {

        // given
        HashMap<String, Object> searchConditions = new HashMap<>();
        searchConditions.put("memberCode", MEMBER_CODE);
        searchConditions.put("reportType", REPORT_TYPE);
        searchConditions.put("completionStatus", COMPLETION_STATUS);

        String offset = "1";

        int totalCount = reportMapper.getReportCount(searchConditions);
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        selectCriteria.setSearchConditions(searchConditions);

        log.info("selectCriteria : " + selectCriteria);

        // when
        List<ReportDTO> routineReportList = reportMapper.selectReportListWithPaging(selectCriteria);

        // then

        for (ReportDTO reportDTO : routineReportList) {
            log.info("reportDTO: {}", reportDTO);
        }

        assertNotEquals(0, routineReportList.size());
        log.info("routineReportList.size = " + routineReportList.size());
    }

    @Test
    void 정기보고_최근_3개_회차_목록_조회_서비스_테스트() {

        // given
        int memberCode = MEMBER_CODE;

        HashMap<String, Object> searchConditions = new HashMap<>();
        searchConditions.put("memberCode", MEMBER_CODE);
        searchConditions.put("reportType", REPORT_TYPE);
        searchConditions.put("completionStatus", COMPLETION_STATUS);

        HashMap<String, List<ReportRoundDTO>> resultMap = new HashMap<>();

        // when
        List<Long> recentRoutineReportCodeList = reportMapper.selectThreeReportCodesByMemberCode(memberCode);

        for(int i = 0; i < recentRoutineReportCodeList.size(); i++) {

            List<ReportRoundDTO> result = reportMapper.selectReportRoundSummaryListByReportCode(recentRoutineReportCodeList.get(i));
            String resultName = "result" + (i + 1);
            resultMap.put(resultName, result);
        }
        List<ReportRoundDTO> routineList1 = resultMap.get("result1");
        List<ReportRoundDTO> routineList2 = resultMap.get("result2");
        List<ReportRoundDTO> routineList3 = resultMap.get("result3");

        List<ReportDTO> casualList = reportMapper.selectCasualReportListByMemberCode(memberCode);


        // then
        assertNotNull(routineList1);
        assertNotNull(routineList2);
        assertNotNull(routineList3);
        assertNotNull(casualList);

    }

    @Test
    @Transactional
    @Rollback(false)
    void 보고_회차_등록_서비스_테스트() {

        // given
        Long reportCode = 1L;

        ReportRoundDTO reportRoundDTO = new ReportRoundDTO();
        reportRoundDTO.setReportCode(1);
        reportRoundDTO.setRoundBody("보고 회차 등록 Test");

        LocalDate currentDate = LocalDate.now();
        String today = currentDate + " 정기 보고";
        reportRoundDTO.setRoundTitle(today);

        // when
        int capacity = reportMapper.getReportRoundCapacity(reportCode);
        reportRoundDTO.setCapacity(capacity);
        int result = reportMapper.registerReportRound(reportRoundDTO);

        // then
        assertEquals(1, result);
    }


    @Test
    @Transactional
    @Rollback(false)
    void 보고_수정_서비스_테스트() {

        // given
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReportCode(2L);
        reportDTO.setReportTitle("Modified Title");
        reportDTO.setReportInfo("Modified Information");
        reportDTO.setMemberCode(2);
        reportDTO.setReportCycle("Tue");
        reportDTO.setCompletionStatus('N');

        List<Integer> memberList = new ArrayList<>();
        memberList.add(1);

        Long reportCode = reportDTO.getReportCode();

        // when
        int result1 = reportMapper.updateReport(reportDTO);
        int result2 = reportMapper.deleteReporter(reportCode);

        int count = 0;

        for (Integer listItem : memberList) {
            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("reportCode", reportCode);
            parameter.put("listItem", listItem);

            reportMapper.registerReporter(parameter);

            count++;
        }

        // then
        assertEquals(1, result1);
        assertNotEquals(0, result2);
        assertNotEquals(0, count);
    }

    @Test
    void 조건별_보고_목록_조회_서비스_테스트() {

        // given
        HashMap<String, Object> searchConditions = new HashMap<>();
        searchConditions.put("memberCode", MEMBER_CODE);
        searchConditions.put("reportType", "Routine");
        searchConditions.put("completionStatus", COMPLETION_STATUS);
//        searchConditions.put("completionStatus", null);

        String offset = "1";

        int totalCount = reportMapper.getReportCount(searchConditions);
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        selectCriteria.setSearchConditions(searchConditions);

        // when
        List<ReportDTO> reportList = reportMapper.selectReportListWithPaging(selectCriteria);

        // then
        assertNotNull(reportList);
    }

    @Test
    void 보고_회차_목록_조회() {

        // given
        int memberCode = 1;
        Long reportCode = 1L;

        int offset = 1;
        int totalCount = reportMapper.getReportRoundCount(reportCode);
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(offset, totalCount, limit, buttonAmount);
        selectCriteria.setSearchCondition(String.valueOf(reportCode));

        // when
        ResponseDTOWithPaging result = reportService.selectReportRoundListByReportCode(memberCode, reportCode, offset);

        // then
        assertNotNull(result);
    }
}
