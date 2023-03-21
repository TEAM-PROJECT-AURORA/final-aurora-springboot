package com.root34.aurora.report.service;

import com.root34.aurora.report.dao.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 @ClassName : ReportService
 @Date : 23.03.21.
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

    // 이메일 보내는 메서드
//    public void sendEmail(String to, String subject, String text) {
    public ArrayList<Object> getAllReportList(long memberCode) {

        ArrayList<Object> allReportList = new ArrayList<Object>();
        allReportList.add(reportMapper.getRoutineReportList(memberCode));
        allReportList.add(reportMapper.getCasualReportList(memberCode));

        return allReportList;
    }
}
