package com.root34.aurora.report.dao;

import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.report.dto.ReportDTO;
import com.root34.aurora.report.dto.ReportRoundDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReportMapperTest {

    private static final Logger log = LoggerFactory.getLogger(ReportMapperTest.class);
    private final int MEMBER_CODE = 1;
    private final String REPORT_TYPE = "Routine";
    private final char COMPLETION_STATUS = 'N';

    @Autowired
    private ReportMapper reportMapper;

    @Test
    public void 보고_맵퍼_의존성_주입_테스트() {

        assertNotNull(reportMapper);
    }

    @Test
    @Transactional
    @Rollback(false)
    void 보고_작성_맵퍼_테스트() {

        // given
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setMemberCode(1);
        reportDTO.setReportType("Routine");
        reportDTO.setReportTitle("TestReportTitle");
        reportDTO.setReportInfo("TestReportInfo");
        reportDTO.setReportCycle("Mon");

        List<Integer> memberList = new ArrayList<>();
        memberList.add(2);
        memberList.add(3);

        // when
        int result = reportMapper.registerReport(reportDTO);

        int generatedPk = reportDTO.getId();

        int count = 0;

        for (Integer listItem : memberList) {
            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("reportCode", generatedPk);
            parameter.put("listItem", listItem);

            reportMapper.registerReporter(parameter);

            count++;
        }

        // then
        assertEquals(1, result);
        assertEquals(memberList.size(), count);
    }

    @Test
    void 보고_갯수_조회_맵퍼_테스트() {

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
    void 보고_목록_조회_맵퍼_테스트() {

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
    void 최근_정기보고_3개_조회_맵퍼_테스트() {

        // given
        int memberCode = MEMBER_CODE;

        // when
        List<Long> recentRoutineReportCodeList = reportMapper.selectThreeReportCodesByMemberCode(memberCode);

        // then
        log.info("recentRoutineReportCodeList.size() : " + recentRoutineReportCodeList.size());
        log.info("recentRoutineReportCodeList : " + recentRoutineReportCodeList);

        assertNotEquals(recentRoutineReportCodeList.size(), 0);
    }

    @Test
    void 요약_정기보고_회차_목록_조회_맵퍼_테스트() {

        // given
        List<Long> reportCodeList = Arrays.asList(3L, 2L, 1L);

        HashMap<String, List<ReportRoundDTO>> resultMap = new LinkedHashMap<>();

//        List<Long> reportCodeList = new ArrayList<>();
//        reportCodeList.add(12L);
//        reportCodeList.add(11L);
//        reportCodeList.add(10L);

        // when
        for(int i = 0; i < reportCodeList.size(); i++) {

            List<ReportRoundDTO> result = reportMapper.selectReportRoundListByReportCode(reportCodeList.get(i));
            String resultName = "result" + (i + 1);
            resultMap.put(resultName, result);
        }
        List<ReportRoundDTO> routineList1 = resultMap.get("result1");
        List<ReportRoundDTO> routineList2 = resultMap.get("result2");
        List<ReportRoundDTO> routineList3 = resultMap.get("result3");

        // then
        assertNotNull(routineList1);
        assertNotNull(routineList2);
        assertNotNull(routineList3);
    }

    @Test
    void 비정기보고_목록_조회_맵퍼_테스트() {

        // given
        int memberCode = MEMBER_CODE;

        // when
        List<ReportDTO> casualReportList = reportMapper.selectCasualReportListByMemberCode(memberCode);

        // then
        assertNotNull(casualReportList);
    }

    @Test
    void 보고_회차_정원_조회_맵퍼_테스트() {

        // given
        Long reportCode = 1L;

        // when
        int capacity = reportMapper.getReportRoundCapacity(reportCode);

        // then
        assertEquals(2, capacity);
    }

    @Test
    @Transactional
    @Rollback(false)
    void 보고_회차_등록_맵퍼_테스트() {

        // given
        ReportRoundDTO reportRoundDTO = new ReportRoundDTO();
        reportRoundDTO.setReportCode(1);
        reportRoundDTO.setRoundTitle("보고");
        reportRoundDTO.setRoundBody("보고 회차 등록 Test");
        reportRoundDTO.setCapacity(2);

        // when
        int result = reportMapper.registerReportRound(reportRoundDTO);

        // then
        assertEquals(1, result);
    }

    @Test
    @Transactional
    @Rollback(false)
    void 보고_수정_맵퍼_테스트() {

        // given
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReportCode(1L);
        reportDTO.setReportTitle("Modified Title");
        reportDTO.setReportInfo("Modified Information");
        reportDTO.setMemberCode(2);
        reportDTO.setReportCycle("Tue");
        reportDTO.setCompletionStatus('N');

        // when
        int result = reportMapper.updateReport(reportDTO);

        // then
        assertEquals(1, result);
    }


    @Test
    @Transactional
    @Rollback(false)
    void 보고자_삭제_맵퍼_테스트() {

        // given
        Long reportCode = 1L;

        // when
        int result = reportMapper.deleteReporter(reportCode);

        // then
        assertNotEquals(0, result);
    }

}