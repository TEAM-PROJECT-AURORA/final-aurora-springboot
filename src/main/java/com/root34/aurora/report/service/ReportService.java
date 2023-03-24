package com.root34.aurora.report.service;

import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.report.dao.ReportMapper;
import com.root34.aurora.report.dto.ReportDTO;
import com.root34.aurora.report.dto.ReportRoundDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
	@ClassName : ReportService
	@Date : 2023-03-22
	@Writer : 김수용
	@Description : 보고관련 요청을 처리할 Service
*/
@Slf4j
@Service
public class ReportService {

    private final ReportMapper reportMapper;

    @Autowired
    public ReportService(ReportMapper reportMapper) {

        this.reportMapper = reportMapper;
    }

    /**
    	* @MethodName : registerReport
    	* @Date : 2023-03-22
    	* @Writer : 김수용
    	* @Description : 보고 작성
    */
    public boolean registerReport(ReportDTO reportDTO, List<Integer> memberList) {

        log.info("[ReportService] registerReport");
        int result = reportMapper.registerReport(reportDTO);
        log.info("[ReportService] registerReport result : " + result);

        int generatedPk = reportDTO.getId();
        log.info("[ReportService] generatedPk : " + generatedPk);

        int count = 0;

        for (Integer listItem : memberList) {
            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("reportCode", generatedPk);
            parameter.put("listItem", listItem);

            reportMapper.registerReporter(parameter);

            count++;
        }
        log.info("[ReportService] count : " + count);
        return result > 0 && count == memberList.size();
    }

    /**
    	* @MethodName : getReportSummary
    	* @Date : 2023-03-23
    	* @Writer : 김수용
    	* @Description : 전체 보고 조회 - 보고 메인 페이지용
    */
    public HashMap<String, Object> getReportSummary(int memberCode) {

        log.info("[ReportService] getReportSummary");
        List<Long> recentRoutineReportCodeList = reportMapper.selectThreeReportCodesByMemberCode(memberCode);
        log.info("[ReportService] recentRoutineReportCodeList : " + recentRoutineReportCodeList);

        HashMap<String, Object> searchConditions = new HashMap<>();
        searchConditions.put("memberCode", memberCode);
        searchConditions.put("reportType", "Routine");
        searchConditions.put("completionStatus", "N");
        log.info("[ReportService] searchConditions : " + searchConditions);

        HashMap<String, Object> response = new HashMap<>();

        for(int i = 0; i < recentRoutineReportCodeList.size(); i++) {

            List<ReportRoundDTO> reportRoundList = reportMapper.selectReportRoundListByReportCode(recentRoutineReportCodeList.get(i));
            String resultName = "routineList" + (i + 1);
            response.put(resultName, reportRoundList);
            log.info("[ReportService] " + resultName + " : " + reportRoundList);
        }
        List<ReportDTO> casualList = reportMapper.selectCasualReportListByMemberCode(memberCode);
        response.put("casualList", casualList);
        log.info("[ReportService] casualList : " + casualList);

        return response;
    }

    /**
    	* @MethodName : registerReportRound
    	* @Date : 2023-03-23
    	* @Writer : 김수용
    	* @Description : 보고 회차 등록
    */
    public boolean registerReportRound(ReportRoundDTO reportRoundDTO) {

        log.info("[ReportService] registerReportRound");
        int capacity = reportMapper.getReportRoundCapacity(reportRoundDTO.getReportCode());
        reportRoundDTO.setCapacity(capacity);
        log.info("[ReportService] capacity : " + capacity);

        LocalDate currentDate = LocalDate.now();
        String roundTitle = currentDate + " 정기 보고";
        reportRoundDTO.setRoundTitle(roundTitle);
        log.info("[ReportService] RoundTitle : " + roundTitle);

        int result = reportMapper.registerReportRound(reportRoundDTO);
        log.info("[ReportService] result : " + (result > 0));

        return result > 0;
    }

    /**
    	* @MethodName : updateReport
    	* @Date : 2023-03-23
    	* @Writer : 김수용
    	* @Description : 보고 수정
    */
    public boolean updateReport(ReportDTO reportDTO, List<Integer> memberList) {

        log.info("[ReportService] updateReport");
        log.info("[ReportService] memberList : " + memberList);

        log.info("[ReportService] ReportDTO : " + reportDTO);
        int updateResult = reportMapper.updateReport(reportDTO);
        log.info("[ReportService] updateResult : " + updateResult);

        Long reportCode = reportDTO.getReportCode();

        int deleteResult = reportMapper.deleteReporter(reportCode);

        int count = 0;

        for (Integer listItem : memberList) {
            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("reportCode", reportCode);
            parameter.put("listItem", listItem);

            reportMapper.registerReporter(parameter);

            count++;
        }

        log.info("[ReportService] count : " + count);

        return updateResult > 0 && deleteResult > 0 && count > 0;
    }

    /**
    	* @MethodName : selectReportListByConditions
    	* @Date : 2023-03-24
    	* @Writer : 김수용
    	* @Description : 조건별 보고 목록 조회
    */
    public ResponseDTOWithPaging selectReportListByConditions(int offset, HashMap<String, Object> searchConditions) {

        log.info("[ReportService] selectReportListByConditions");
        int totalCount = reportMapper.getReportCount(searchConditions);
        log.info("[ReportService] totalCount : " + totalCount);
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria searchCriteria = Pagenation.getSelectCriteria(offset, totalCount, limit, buttonAmount);
        log.info("[ReportService] searchCriteria : " + searchCriteria);
        searchCriteria.setSearchConditions(searchConditions);

        List<ReportDTO> reportList = reportMapper.selectReportListWithPaging(searchCriteria);
        log.info("[ReportService] reportList : " + reportList);

        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setPageInfo(searchCriteria);
        responseDTOWithPaging.setData(reportList);
        log.info("[ReportService] responseDTOWithPaging : " + responseDTOWithPaging);

//        if(reportList.isEmpty())) {
//            return new Exception
//        }

        return responseDTOWithPaging;
    }
}