package com.root34.aurora.report.service;

import com.root34.aurora.report.dao.ReportMapper;
import com.root34.aurora.report.dto.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
	@ClassName : ReportService
	@Date : 2023-03-22
	@Writer : 김수용
	@Description : 보고관련 요청을 처리할 Service
*/
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
    	* @Description : 보고를 작성하는 메서드
    */
    public boolean registerReport(ReportDTO reportDTO, List<Integer> memberList) {

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
        return result > 0 && count == memberList.size();
    }

    public ArrayList<Object> getAllReportList(long memberCode) {

        ArrayList<Object> allReportList = new ArrayList<>();
//        allReportList.add(reportMapper.selectRoutineReportList(memberCode));
//        allReportList.add(reportMapper.getCasualReportList(memberCode));

        return allReportList;
    }
}
