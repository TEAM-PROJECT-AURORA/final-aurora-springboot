package com.root34.aurora.report.service;

import com.root34.aurora.common.FileDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.report.dao.ReportMapper;
import com.root34.aurora.report.dto.ReportDTO;
import com.root34.aurora.report.dto.ReportDetailDTO;
import com.root34.aurora.report.dto.ReportRoundDTO;
import com.root34.aurora.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
	@ClassName : ReportService
	@Date : 2023-03-22
	@Writer : 김수용
	@Description : 보고관련 요청을 처리할 Service
*/
@Slf4j
@Service
public class ReportService {

    @Value("${file.file-dir}")
    private String FILE_DIR;
    @Value("${file.file-url}")
    private String FILE_URL;

    private final ReportMapper reportMapper;

    @Autowired
    public ReportService(ReportMapper reportMapper) {

        this.reportMapper = reportMapper;
    }

    /**
    	* @MethodName : verifyMemberReportAccess
    	* @Date : 2023-03-24
    	* @Writer : 김수용
    	* @Description : 보고 관련자 체크
    */
    public void verifyMemberReportAccess(int memberCode, Long reportCode) {

        try {
            log.info("[ReportService] verifyMemberReportAccess Start");
            log.info("[ReportService] memberCode : " + memberCode);
            log.info("[ReportService] reportCode : " + reportCode);

            List<Integer> involvedMemberCodeList = reportMapper.selectMemberListInvolvedInReport(reportCode);
            log.info("[ReportService] involvedMemberCodeList : " + involvedMemberCodeList);

            if(!involvedMemberCodeList.contains(memberCode)) {
                throw new Exception("보고 관련자가 아닙니다. 조회할 권한이 없습니다.");
            }
        } catch (Exception e) {
            log.error("[ReportService] verifyMemberReportAccess Error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
    	* @MethodName : countInChargeMember
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 보고 책임자 확인
    */
    public boolean countInChargeMember(int memberCode, Long reportCode) {

        log.info("[ReportService] countInChargeMember Start");
        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put("memberCode", memberCode);
        parameter.put("reportCode", reportCode);

        int result = reportMapper.countInChargeMember(parameter);
        log.info("[ReportService] countInChargeMember result : " + result);

        return result > 0;
    }

    /**
     * @MethodName : updateReportReadStatusToRead
     * @Date : 2023-03-27
     * @Writer : 김수용
     * @Description : 보고 읽음상태 수정 - 읽음
     */
    public void updateReportReadStatusToRead(int memberCode, Long reportCode) {

        if(countInChargeMember(memberCode, reportCode)) {
            try {
                log.info("[ReportService] updateReportReadStatusToRead Start");
                HashMap<String, Object> parameter = new HashMap<>();
                parameter.put("reportCode", reportCode);
                parameter.put("readStatus", 'Y');
                log.info("[ReportService] parameter : " + parameter);

                reportMapper.updateReportReadStatus(parameter);
            } catch (Exception e) {
                log.error("[ReportService] updateReportReadStatusToRead Error : " + e.getMessage());
                // 에러에 대한 응답 처리
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            }
        }
    }

    /**
    	* @MethodName : updateReportReadStatusToUnread
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 보고 읽음상태 수정 - 읽지않음
    */
    public void updateReportReadStatusToUnread(Long reportCode) {

        try {
            log.info("[ReportService] updateReportReadStatusToUnread Start");
            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("reportCode", reportCode);
            parameter.put("readStatus", 'N');
            log.info("[ReportService] parameter : " + parameter);

            reportMapper.updateReportReadStatus(parameter);
        } catch (Exception e) {
            log.error("[ReportService] updateReportReadStatusToUnread Error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
    	* @MethodName : updateReportCompletionStatusToComplete
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 보고 완료상태 수정 - 완료
    */
    public boolean updateReportCompletionStatusToComplete(int memberCode, Long reportCode) {

        log.info("[ReportService] updateReportCompletionStatus Start");
        try {
            if(!countInChargeMember(memberCode, reportCode)) {
                throw new Exception("보고 책임자가 아닙니다. 수정할 권한이 없습니다.");
            }
            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("memberCode", memberCode);
            parameter.put("reportCode", reportCode);
            parameter.put("completionStatus", 'Y');
            log.info("[ReportService] parameter : " + parameter);

            int result = reportMapper.updateReportCompletionStatus(parameter);

            return result > 0;
        } catch (Exception e) {
            log.error("[ReportService] updateReportCompletionStatus Error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
    	* @MethodName : registerReport
    	* @Date : 2023-03-22
    	* @Writer : 김수용
    	* @Description : 보고 작성
    */
    public boolean registerReport(ReportDTO reportDTO, List<Integer> memberList, List<MultipartFile> fileList) {

        try {
            log.info("[ReportService] registerReport Start");
            log.info("[ReportService] reportDTO : " + reportDTO);
            int result = reportMapper.registerReport(reportDTO);
            log.info("[ReportService] registerReport result : " + result);
            if(result == 0) {
                throw new Exception("보고서 등록 실패!");
            }

            Long generatedPk = reportDTO.getReportCode();
            log.info("[ReportService] generatedPk : " + generatedPk);

            int memberCount = 0;

            for (Integer listItem : memberList) {

                HashMap<String, Object> parameter = new HashMap<>();
                parameter.put("reportCode", generatedPk);
                parameter.put("listItem", listItem);

                memberCount += reportMapper.registerReporter(parameter);
            }
            log.info("[ReportService] memberCount : " + memberCount);

            if(memberCount == 0) {
                throw new Exception("에러 : 보고자가 등록되지않았습니다!");
            }
            if(fileList != null) {

                int fileCount = 0;

                    for (MultipartFile file : fileList) {

                        String fileName = UUID.randomUUID().toString().replace("-", "");
                        String replaceFileName = null;
                        log.info("[ReportService] FILE_DIR : " + FILE_DIR);
                        log.info("[ReportService] fileName : " + fileName);
                        replaceFileName = FileUploadUtils.saveFile(FILE_DIR, fileName, file);
                        log.info("[ReportService] replaceFileName : " + replaceFileName);

                        FileDTO fileDTO = new FileDTO();
                        fileDTO.setFileOriginName(file.getOriginalFilename());
        //                fileDTO.setFileOriginName(replaceFileName);
                        fileDTO.setFileName(replaceFileName);
                        fileDTO.setFilePath(FILE_DIR + replaceFileName);
                        fileDTO.setReportCode(generatedPk);
                        double fileSizeInMB = (double) file.getSize() / (1024 * 1024);
                        log.info("[ReportService] fileSizeInMB : " + fileSizeInMB);
                        String fileSizeString = String.format("%.2f MB", fileSizeInMB);
                        log.info("[ReportService] fileSizeString : " + fileSizeString);
                        fileDTO.setFileSize(fileSizeString);

        //                fileDTO.setBoardImageUrl(replaceFileName);

                        fileCount += reportMapper.registerFileWithReportCode(fileDTO);
        //                log.info("[ReportService] insert Image Name : "+ replaceFileName);
                    }
            }
            return result > 0 && memberCount == memberList.size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("[ReportService] registerReport Error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
    	* @MethodName : getReportSummary
    	* @Date : 2023-03-23
    	* @Writer : 김수용
    	* @Description : 전체 보고 조회 - 보고 메인 페이지용
    */
    public HashMap<String, Object> getReportSummary(int memberCode) {

        log.info("[ReportService] getReportSummary Start");
        List<Long> recentRoutineReportCodeList = reportMapper.selectThreeReportCodesByMemberCode(memberCode);
        log.info("[ReportService] recentRoutineReportCodeList : " + recentRoutineReportCodeList);

        HashMap<String, Object> searchConditions = new HashMap<>();
        searchConditions.put("memberCode", memberCode);
        searchConditions.put("reportType", "Routine");
        searchConditions.put("completionStatus", "N");
        log.info("[ReportService] searchConditions : " + searchConditions);

        HashMap<String, Object> response = new HashMap<>();

        for(int i = 0; i < recentRoutineReportCodeList.size(); i++) {

            List<ReportRoundDTO> reportRoundList = reportMapper.selectReportRoundSummaryListByReportCode(recentRoutineReportCodeList.get(i));
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

        try {
            log.info("[ReportService] registerReportRound Start");

            if(reportMapper.selectReportType(reportRoundDTO.getReportCode()).equals("Casual")) {
                throw new Exception("해당 보고서는 정기보고가 아닙니다!");
            }

            int capacity = reportMapper.getReportRoundCapacity(reportRoundDTO.getReportCode());
            reportRoundDTO.setCapacity(capacity);
            log.info("[ReportService] capacity : " + capacity);

            LocalDate currentDate = LocalDate.now();
            String roundTitle = currentDate + " 정기 보고";
            reportRoundDTO.setRoundTitle(roundTitle);
            log.info("[ReportService] RoundTitle : " + roundTitle);

            int result = reportMapper.registerReportRound(reportRoundDTO);
            log.info("[ReportService] result : " + (result > 0));

            if(result == 0) {
                throw new Exception("보고 회차 등록 실패!");
            }
            updateReportReadStatusToUnread(reportRoundDTO.getReportCode());

            return result > 0;
        } catch (Exception e) {
            log.error("[ReportService] registerReportRound Error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
    	* @MethodName : updateReport
    	* @Date : 2023-03-23
    	* @Writer : 김수용
    	* @Description : 보고 수정
    */
    public boolean updateReport(int memberCode, ReportDTO reportDTO, List<Integer> memberList) {

        try {
            if(!countInChargeMember(memberCode, reportDTO.getReportCode())) {
                throw new Exception("보고 책임자가 아닙니다. 수정할 권한이 없습니다!");            }
            log.info("[ReportService] updateReport Start");
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

            if(deleteResult == 0 || count == 0) {
                throw new Exception("보고 수정 실패!");
            }
            return updateResult > 0 && deleteResult > 0 && count > 0;
        } catch (Exception e) {
            log.error("[ReportService] updateReport Error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
    	* @MethodName : selectReportListByConditions
    	* @Date : 2023-03-24
    	* @Writer : 김수용
    	* @Description : 조건별 보고 목록 조회
    */
    public ResponseDTOWithPaging selectReportListByConditions(int offset, HashMap<String, Object> searchConditions) {

        log.info("[ReportService] selectReportListByConditions Start");
        int totalCount = reportMapper.getReportCount(searchConditions);
        log.info("[ReportService] totalCount : " + totalCount);
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(offset, totalCount, limit, buttonAmount);
        log.info("[ReportService] selectCriteria : " + selectCriteria);
        selectCriteria.setSearchConditions(searchConditions);

        List<ReportDTO> reportList = reportMapper.selectReportListWithPaging(selectCriteria);
        log.info("[ReportService] reportList : " + reportList);

        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setPageInfo(selectCriteria);
        responseDTOWithPaging.setData(reportList);
        log.info("[ReportService] responseDTOWithPaging : " + responseDTOWithPaging);

        return responseDTOWithPaging;
    }

    /**
    	* @MethodName : selectReportRoundListByReportCode
    	* @Date : 2023-03-24
    	* @Writer : 김수용
    	* @Description : 보고 회차 목록 조회
    */
    public ResponseDTOWithPaging selectReportRoundListByReportCode(int memberCode, Long reportCode, int offset) {

        try {
            verifyMemberReportAccess(memberCode, reportCode);

            if(reportMapper.selectReportType(reportCode) == "Casual") {
                throw new Exception("해당 보고서는 정기보고가 아닙니다!");
            }

            log.info("[ReportService] selectReportRoundListByReportCode Start");
            updateReportReadStatusToRead(memberCode,reportCode);

            int totalCount = reportMapper.getReportRoundCount(reportCode);
            int limit = 10;
            int buttonAmount = 5;

            SelectCriteria selectCriteria = Pagenation.getSelectCriteria(offset, totalCount, limit, buttonAmount);
            selectCriteria.setSearchCondition(String.valueOf(reportCode));
            log.info("[ReportService] selectCriteria : " + selectCriteria);

            List<ReportRoundDTO> reportRoundList = reportMapper.selectReportRoundListByReportCode(selectCriteria);
            log.info("[ReportService] reportRoundList : " + reportRoundList);

            ResponseDTOWithPaging responseDTOWithPaging =new ResponseDTOWithPaging();
            responseDTOWithPaging.setPageInfo(selectCriteria);
            responseDTOWithPaging.setData(reportRoundList);
            log.info("[ReportService] responseDTOWithPaging : " + responseDTOWithPaging);

            return responseDTOWithPaging;
        } catch (Exception e) {
            log.error("[ReportService] selectReportRoundListByReportCode Error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
    	* @MethodName : selectReportRoundDetailByRoundCode
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 정기보고 회차 상세 조회
    */
    public ReportRoundDTO selectReportRoundDetailByRoundCode(int memberCode, Long reportCode, Long roundCode) {

        try {
            verifyMemberReportAccess(memberCode, reportCode);

            log.info("[ReportService] selectReportRoundDetailByRoundCode Start");
            updateReportReadStatusToRead(memberCode,reportCode);

            ReportRoundDTO reportRoundDetail = reportMapper.selectReportRoundDetailByRoundCode(roundCode);
            log.info("[ReportService] reportRoundDetail : " + reportRoundDetail);

            return reportRoundDetail;
        } catch (Exception e) {
            log.error("[ReportService] selectReportRoundDetailByRoundCode Error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
    	* @MethodName : selectCasualReportDetailByReportCode
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 비정기보고 상세 조회
    */
    public HashMap<String, Object> selectCasualReportDetailByReportCode(int memberCode, Long reportCode) {

        try {
            verifyMemberReportAccess(memberCode, reportCode);

            if(reportMapper.selectReportType(reportCode) == "Routine") {
                throw new Exception("해당 보고서는 비정기보고가 아닙니다!");
            }

            log.info("[ReportService] selectCasualReportDetailByReportCode Start");
            updateReportReadStatusToRead(memberCode,reportCode);

            log.info("[ReportService] reportCode : " + reportCode);

            HashMap<String, Object> response = new HashMap<>();
            response.put("ReportDTO", reportMapper.selectCasualReportDetailByReportCode(reportCode));
            response.put("attachmentList", reportMapper.selectReportAttachmentListByReportCode(reportCode));
            log.info("[ReportService] response : " + response);

            return response;
        } catch (Exception e) {
            log.error("[ReportService] selectCasualReportDetailByReportCode Error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
    	* @MethodName : isReportNotCompleted
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 완료된 보고인지 체크
    */
    public void isReportNotCompleted(long reportCode) {

        try {
            log.info("[ReportService] isReportNotCompleted Start");
            log.info("[ReportService] reportCode : " + reportCode);

            char result = reportMapper.selectReportCompletionStatus(reportCode);
            log.info("[ReportService] result : " + result);

            if(result == 'Y') {
                throw new Exception("해당 보고는 이미 완료된 보고입니다!");
            }
        } catch (Exception e) {
            log.error("[ReportService] isReportNotCompleted Error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
    	* @MethodName : registerReportDetail
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 회차별 상세 보고 작성
    */
    public boolean registerReportDetail(long reportCode, ReportDetailDTO reportDetailDTO) {

        try {
            isReportNotCompleted(reportCode);

            verifyMemberReportAccess(reportDetailDTO.getMemberCode(), reportCode);

            log.info("[ReportService] registerReportDetail Start");
            log.info("[ReportService] reportDetailDTO : " + reportDetailDTO);

            int result = reportMapper.registerReportDetail(reportDetailDTO);
            log.info("[ReportService] result : " + result);

            if(result == 0) {
                throw new Exception("상세 보고 작성 실패!");
            }

            return result > 0;
        } catch (Exception e) {
            log.error("[ReportService] registerReportDetail Error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}