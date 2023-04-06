package com.root34.aurora.report.service;

import com.root34.aurora.common.FileDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.exception.CreationFailedException;
import com.root34.aurora.exception.DataNotFoundException;
import com.root34.aurora.exception.NotAuthorException;
import com.root34.aurora.exception.UpdateFailedException;
import com.root34.aurora.exception.report.AlreadyCompletedReportException;
import com.root34.aurora.exception.report.InvalidReportTypeException;
import com.root34.aurora.exception.report.NotInvolvedInReportException;
import com.root34.aurora.exception.report.NotReportSupervisorException;
import com.root34.aurora.member.dto.MemberDTO;
import com.root34.aurora.report.dao.ReportMapper;
import com.root34.aurora.report.dto.ReportDTO;
import com.root34.aurora.report.dto.ReportDetailDTO;
import com.root34.aurora.report.dto.ReportRoundDTO;
import com.root34.aurora.report.dto.ReportRoundReplyDTO;
import com.root34.aurora.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

        log.info("[ReportService] verifyMemberReportAccess Start");
        log.info("[ReportService] memberCode : " + memberCode);
        log.info("[ReportService] reportCode : " + reportCode);

        List<Integer> involvedMemberCodeList = reportMapper.selectMemberListInvolvedInReport(reportCode);
        log.info("[ReportService] involvedMemberCodeList : " + involvedMemberCodeList);

        if(!involvedMemberCodeList.contains(memberCode)) {

            throw new NotInvolvedInReportException("보고 관련자가 아닙니다. 조회할 권한이 없습니다.");
        }
        log.info("[ReportService] verifyMemberReportAccess Passed");
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
        log.info("[ReportService] parameter : " + parameter);

        int result = reportMapper.countInChargeMember(parameter);
        log.info("[ReportService] countInChargeMember result : " + result);

        return result > 0;
    }

    /**
     * @MethodName : isReportNotCompleted
     * @Date : 2023-03-27
     * @Writer : 김수용
     * @Description : 완료된 보고인지 체크
     */
    public void isReportNotCompleted(long reportCode) {

        log.info("[ReportService] isReportNotCompleted Start");
        log.info("[ReportService] reportCode : " + reportCode);

        char result = reportMapper.selectReportCompletionStatus(reportCode);
        log.info("[ReportService] isReportNotCompleted result : " + result);

        if(result == 'Y') {
            throw new AlreadyCompletedReportException("해당 보고는 완료된 보고입니다. 완료된 보고는 더 이상 작성이나 수정이 불가합니다!");
        }
        log.info("[ReportService] isReportNotCompleted Passed");
    }

    /**
     * @MethodName : isDetailReportAuthor
     * @Date : 2023-03-28
     * @Writer : 김수용
     * @Description : 상세보고 작성자 확인
     */
    public void isDetailReportAuthor(int memberCode, Long detailCode) {

        log.info("[ReportService] isDetailReportAuthor Start");
        log.info("[ReportService] memberCode : " + memberCode);
        log.info("[ReportService] detailCode : " + detailCode);

        Integer detailReportAuthor = reportMapper.selectMemberCodeByDetailCode(detailCode);
        log.info("[ReportService] detailReportAuthor : " + detailReportAuthor);

        if(detailReportAuthor == null) {
            throw new DataNotFoundException("해당 상세 보고의 작성자를 찾을 수 없습니다!");
        }

        boolean isNotAuthor = (memberCode != detailReportAuthor);
        log.info("[ReportService] isNotAuthor : " + isNotAuthor);

        if(isNotAuthor) {
            throw new NotAuthorException("해당 상세 보고의 작성자가 아닙니다!");
        }
        log.info("[ReportService] isDetailReportAuthor Passed");
    }

    /**
     * @MethodName : isReplyAuthor
     * @Date : 2023-03-28
     * @Writer : 김수용
     * @Description : 보고 댓글 작성자 확인
     */
    public void isReplyAuthor(int memberCode, Long replyCode) throws Exception {

        log.info("[ReportService] isReplyAuthor Start");
        log.info("[ReportService] memberCode : " + memberCode);
        log.info("[ReportService] replyCode : " + replyCode);

        Integer author = reportMapper.selectMemberCodeByReplyCode(replyCode);
        log.info("[ReportService] author : " + author);

        if(author == null) {
            throw new NotFoundException("해당 보고 댓글의 작성자를 찾을 수 없습니다!");
        }
        boolean isAuthor = memberCode == author;
        log.info("[ReportService] isAuthor : " + isAuthor);

        if(!isAuthor) {
            throw new NotAuthorException("해당 보고 댓글의 작성자가 아닙니다!");
        }
        log.info("[ReportService] isReplyAuthor Passed");
    }

    /**
     * @MethodName : updateReportReadStatusToRead
     * @Date : 2023-03-27
     * @Writer : 김수용
     * @Description : 보고 읽음상태 수정 - 읽음
     */
    public void updateReportReadStatusToRead(int memberCode, Long reportCode) {

        if(countInChargeMember(memberCode, reportCode)) {
            log.info("[ReportService] updateReportReadStatusToRead Start");
            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("reportCode", reportCode);
            parameter.put("readStatus", 'Y');
            log.info("[ReportService] parameter : " + parameter);

            int result = reportMapper.updateReportReadStatus(parameter);
            log.info("[ReportService] updateReportReadStatusToRead result : " + result);

            if(result == 0) {
                throw new UpdateFailedException("해당 보고를 읽음 상태로 수정하는데 실패했습니다!");
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

        log.info("[ReportService] updateReportReadStatusToUnread Start");
        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put("reportCode", reportCode);
        parameter.put("readStatus", 'N');
        log.info("[ReportService] parameter : " + parameter);

        int result = reportMapper.updateReportReadStatus(parameter);
        log.info("[ReportService] updateReportReadStatusToUnread result : " + result);

        if(result == 0) {
            throw new UpdateFailedException("해당 보고를 읽지않음 상태로 수정하는데 실패했습니다!");
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
        log.info("[ReportService] memberCode : " + memberCode);
        log.info("[ReportService] reportCode : " + reportCode);

        if(!countInChargeMember(memberCode, reportCode)) {
            throw new NotReportSupervisorException("보고 책임자가 아닙니다. 수정할 권한이 없습니다.");
        }
        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put("memberCode", memberCode);
        parameter.put("reportCode", reportCode);
        parameter.put("completionStatus", 'Y');
        log.info("[ReportService] parameter : " + parameter);

        int result = reportMapper.updateReportCompletionStatus(parameter);
        log.info("[ReportService] updateReportCompletionStatusToComplete result : " + result);

        return result > 0;
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
            log.info("[ReportService] memberList : " + memberList);
            log.info("[ReportService] fileList : " + fileList);

            int result = reportMapper.registerReport(reportDTO);
            log.info("[ReportService] registerReport result : " + result);

            if(result == 0) {
                throw new CreationFailedException("보고서 등록 실패!");
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

                throw new CreationFailedException("보고자가 등록되지않았습니다!");
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
                        fileDTO.setFileName(replaceFileName);
                        fileDTO.setFilePath(FILE_DIR + replaceFileName);
                        fileDTO.setReportCode(generatedPk);

                        double fileSizeInBytes = (double) file.getSize();

                        String fileSizeString = fileSizeInBytes < 1024?
                                String.format("%.2f KB", fileSizeInBytes / 1024) :
                                String.format("%.2f MB", fileSizeInBytes / (1024 * 1024));
                        log.info("[ReportService] fileSizeString : " + fileSizeString);
                        fileDTO.setFileSize(fileSizeString);
                        log.info("[ReportService] fileDTO : " + fileDTO);

                        fileCount += reportMapper.registerFileWithReportCode(fileDTO);
                        log.info("[ReportService] fileCount : " + fileCount);
                    }
            }
            return result > 0 && memberCount == memberList.size();
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드에 실패했습니다!");
        }
    }

    /**
     * @MethodName : selectReportDetailByReportCode
     * @Date : 2023-03-27
     * @Writer : 김수용
     * @Description : 보고 상세 조회
     */
    public HashMap<String, Object> selectReportDetailByReportCode(int memberCode, Long reportCode) {

        log.info("[ReportService] selectReportDetailByReportCode Start");
        log.info("[ReportService] memberCode : " + memberCode);
        log.info("[ReportService] reportCode : " + reportCode);

        verifyMemberReportAccess(memberCode, reportCode);

        HashMap<String, Object> response = new HashMap<>();

        ReportDTO reportDTO = reportMapper.selectReportDetailByReportCode(reportCode);
        response.put("reportDTO", reportDTO);

        // 보고자 목록(책임자 제외)
        List<Integer> memberList = reportMapper.selectMemberListInvolvedInReport(reportCode);
        memberList.removeIf(reporterMemberCode -> reporterMemberCode == memberCode);

        if(reportDTO.getReportType().equals("Routine")) {

            List<MemberDTO> memberDTOList = new ArrayList<>();

            for(int reporterMemberCode : memberList) {

                MemberDTO memberDTO = reportMapper.selectReporterDetail(reporterMemberCode);
                memberDTOList.add(memberDTO);
            }
            response.put("memberList", memberDTOList);
        } else {

            response.put("fileList", reportMapper.selectReportAttachmentListByReportCode(reportCode));
            response.put("reporterDetail", reportMapper.selectReporterDetail(memberList.get(0)));
        }

        log.info("[ReportService] selectReportDetailByReportCode response : " + response);

        return response;
    }

    /**
    	* @MethodName : getReportSummary
    	* @Date : 2023-03-23
    	* @Writer : 김수용
    	* @Description : 전체 보고 조회 - 보고 메인 페이지용
    */
    public HashMap<String, Object> getReportSummary(int memberCode) {

        log.info("[ReportService] getReportSummary Start");
        log.info("[ReportService] memberCode : " + memberCode);

        HashMap<String, Object> response = new HashMap<>();

        List<Long> recentRoutineReportCodeList = reportMapper.selectThreeReportCodesByMemberCode(memberCode);
        log.info("[ReportService] recentRoutineReportCodeList : " + recentRoutineReportCodeList);

        for(int i = 0; i < recentRoutineReportCodeList.size(); i++) {

            ReportDTO reportDTO = reportMapper.selectReportDetailByReportCode(recentRoutineReportCodeList.get(i));
            response.put("routineReportDTO" + (i + 1), reportDTO);
        }

        HashMap<String, Object> searchConditions = new HashMap<>();
        searchConditions.put("memberCode", memberCode);
        searchConditions.put("reportType", "Routine");
        searchConditions.put("completionStatus", "N");
        log.info("[ReportService] searchConditions : " + searchConditions);

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

        log.info("[ReportService] registerReportRound Start");
        log.info("[ReportService] reportRoundDTO : " + reportRoundDTO);

        String reportType = reportMapper.selectReportType(reportRoundDTO.getReportCode());
        log.info("[ReportService] reportType : " + reportType);

        if(reportType == null) {
            throw new NullPointerException("해당 보고서의 유형을 확인할 수 없습니다!");
        } else if(reportType.equals("Casual")) {
            throw new InvalidReportTypeException("해당 보고서는 정기보고가 아닙니다!");
        }

        int capacity = reportMapper.getReportRoundCapacity(reportRoundDTO.getReportCode());
        reportRoundDTO.setCapacity(capacity);
        log.info("[ReportService] capacity : " + capacity);

//        해당 날짜를 통해 제목을 자동 생성하려했는데 폐기
//        LocalDate currentDate = LocalDate.now();
//        String roundTitle = currentDate + " 정기 보고";
//        reportRoundDTO.setRoundTitle(roundTitle);
//        log.info("[ReportService] RoundTitle : " + roundTitle);

//        HashMap<String, Object> parameter = new HashMap<>();
//        parameter.put("roundTitle", roundTitle);
//        parameter.put("roundCode", reportRoundDTO.getRoundCode());
//
//        if(reportMapper.isRoundTitleExist(parameter) > 0) {
//
////            roundTitle
//        }
        int result = reportMapper.registerReportRound(reportRoundDTO);
        log.info("[ReportService] result : " + (result > 0));

        if(result == 0) {
            throw new CreationFailedException("보고 회차 등록 실패!");
        }
        updateReportReadStatusToUnread(reportRoundDTO.getReportCode());

        return result > 0;
    }

    /**
    	* @MethodName : updateReportRound
    	* @Date : 2023-04-05
    	* @Writer : 김수용
    	* @Description : 보고 회차 수정
    */
    public boolean updateReportRound(ReportRoundDTO reportRoundDTO) {

        log.info("[ReportService] updateReportRound Start");
        log.info("[ReportService] reportRoundDTO : " + reportRoundDTO);

        String reportType = reportMapper.selectReportType(reportRoundDTO.getReportCode());
        log.info("[ReportService] reportType : " + reportType);

        if(reportType == null) {
            throw new NullPointerException("해당 보고서의 유형을 확인할 수 없습니다!");
        } else if(reportType.equals("Casual")) {
            throw new InvalidReportTypeException("해당 보고서는 정기보고가 아닙니다!");
        }

        int result = reportMapper.updateReportRound(reportRoundDTO);
        log.info("[ReportService] updateReportRound result : " + (result > 0));

        if(result == 0) {
            throw new CreationFailedException("보고 회차 수정 실패!");
        }
        return result > 0;
    }

    /**
    	* @MethodName : deleteReportRound
    	* @Date : 2023-04-05
    	* @Writer : 김수용
    	* @Description : 보고 회차 삭제
    */
    public boolean deleteReportRound(long roundCode) {

        log.info("[ReportService] deleteReportRound Start");
        log.info("[ReportService] roundCode : " + roundCode);

        int result = reportMapper.deleteReportRound(roundCode);
        log.info("[ReportService] deleteReportRound result : " + (result > 0));

        if(result == 0) {
            throw new CreationFailedException("보고 회차 삭제 실패!");
        }
        return result > 0;
    }

    /**
     * @MethodName : updateReport
     * @Date : 2023-03-23
     * @Writer : 김수용
     * @Description : 보고 수정
     */
    public boolean updateReport(ReportDTO reportDTO, List<Integer> memberList, List<MultipartFile> fileList) throws IOException {

        log.info("[ReportService] updateReport Start");
        log.info("[ReportService] ReportDTO : " + reportDTO);
        log.info("[ReportService] memberList : " + memberList);
        log.info("[ReportService] fileList : " + fileList);

        if(!countInChargeMember(reportDTO.getMemberCode(), reportDTO.getReportCode())) {
            throw new NotReportSupervisorException("보고 책임자가 아닙니다. 수정할 권한이 없습니다!");
        }

        int updateResult = reportMapper.updateReport(reportDTO);
        log.info("[ReportService] updateResult : " + updateResult);

        if(updateResult == 0) {
            throw new UpdateFailedException("보고 수정 실패!");
        }
        int deleteResult = reportMapper.deleteReporter(reportDTO.getReportCode());
        log.info("[ReportService] deleteReporter Result : " + deleteResult);

        int memberCount = 0;

        for (Integer listItem : memberList) {
            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("reportCode", reportDTO.getReportCode());
            parameter.put("listItem", listItem);

            reportMapper.registerReporter(parameter);

            memberCount++;
        }
        log.info("[ReportService] memberList count : " + memberCount);

        if(deleteResult == 0 || memberCount == 0 ||  memberCount != memberList.size()) {
            throw new UpdateFailedException("보고자 수정 실패!");
        }

        log.info("[ReportService] reportDTO.getReportType() == \"Casual\" : " + reportDTO.getReportType().equals("Casual"));

        if(reportDTO.getReportType().equals("Casual")) {

            int fileDeleteResult = reportMapper.deleteFiles(reportDTO.getReportCode());
            log.info("[ReportService] fileDeleteResult : " + fileDeleteResult);

            if (fileList != null) {

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
                    fileDTO.setFileName(replaceFileName);
                    fileDTO.setFilePath(FILE_DIR + replaceFileName);
                    fileDTO.setReportCode(reportDTO.getReportCode());

                    double fileSizeInBytes = (double) file.getSize();

                    String fileSizeString = fileSizeInBytes < 1024 ?
                            String.format("%.2f KB", fileSizeInBytes / 1024) :
                            String.format("%.2f MB", fileSizeInBytes / (1024 * 1024));
                    log.info("[ReportService] fileSizeString : " + fileSizeString);
                    fileDTO.setFileSize(fileSizeString);
                    log.info("[ReportService] fileDTO : " + fileDTO);

                    fileCount += reportMapper.registerFileWithReportCode(fileDTO);
                    log.info("[ReportService] fileCount : " + fileCount);
                }
            }
        }
        return updateResult > 0 && deleteResult > 0 && memberCount > 0;
    }
//    /**
//    	* @MethodName : updateReport
//    	* @Date : 2023-03-23
//    	* @Writer : 김수용
//    	* @Description : 보고 수정
//    */
//    public boolean updateReport(int memberCode, ReportDTO reportDTO, List<Integer> memberList) {
//
//        log.info("[ReportService] updateReport Start");
//        log.info("[ReportService] ReportDTO : " + reportDTO);
//        log.info("[ReportService] memberList : " + memberList);
//
//        if(!countInChargeMember(memberCode, reportDTO.getReportCode())) {
//            throw new NotReportSupervisorException("보고 책임자가 아닙니다. 수정할 권한이 없습니다!");
//        }
//
//        int updateResult = reportMapper.updateReport(reportDTO);
//        log.info("[ReportService] updateResult : " + updateResult);
//
//        Long reportCode = reportDTO.getReportCode();
//
//        int deleteResult = reportMapper.deleteReporter(reportCode);
//        log.info("[ReportService] deleteResult : " + deleteResult);
//
//        int count = 0;
//
//        for (Integer listItem : memberList) {
//            HashMap<String, Object> parameter = new HashMap<>();
//            parameter.put("reportCode", reportCode);
//            parameter.put("listItem", listItem);
//
//            reportMapper.registerReporter(parameter);
//
//            count++;
//        }
//        log.info("[ReportService] memberList count : " + count);
//
//        if(deleteResult == 0 || count == 0) {
//            throw new UpdateFailedException("보고 수정 실패!");
//        }
//        return updateResult > 0 && deleteResult > 0 && count > 0;
//    }

    /**
    	* @MethodName : selectReportListByConditions
    	* @Date : 2023-03-24
    	* @Writer : 김수용
    	* @Description : 조건별 보고 목록 조회
    */
    public ResponseDTOWithPaging selectReportListByConditions(int offset, HashMap<String, Object> searchConditions) {

        log.info("[ReportService] selectReportListByConditions Start");
        log.info("[ReportService] searchConditions : " + searchConditions);
        int totalCount = reportMapper.getReportCount(searchConditions);
        log.info("[ReportService] totalCount : " + totalCount);
        int limit = 10;
        log.info("[ReportService] limit : " + limit);
        int buttonAmount = 5;
        log.info("[ReportService] buttonAmount : " + buttonAmount);

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(offset, totalCount, limit, buttonAmount);
        selectCriteria.setSearchConditions(searchConditions);
        log.info("[ReportService] selectCriteria : " + selectCriteria);

        List<ReportDTO> reportList = reportMapper.selectReportListWithPaging(selectCriteria);
        log.info("[ReportService] reportList : " + reportList);

        if(searchConditions.get("reportType").equals("Routine")) {

            for(ReportDTO routineReport : reportList) {

                routineReport.setMemberDTO(reportMapper.selectReporterDetail(routineReport.getMemberCode()));
            }
        } else {

            for(ReportDTO casualReport : reportList) {

                List<Integer> repoterMemberCodeList = reportMapper.selectMemberListInvolvedInReport(casualReport.getReportCode());
                repoterMemberCodeList.removeIf(reporterMemberCode -> reporterMemberCode == searchConditions.get("memberCode"));

                MemberDTO reporterDTO = reportMapper.selectReporterDetail(repoterMemberCodeList.get(0));

                casualReport.setMemberDTO(reporterDTO);
            }
        }
        log.info("[ReportService] reportList : " + reportList);

        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setData(reportList);
        responseDTOWithPaging.setPageInfo(selectCriteria);
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

        log.info("[ReportService] selectReportRoundListByReportCode Start");
        log.info("[ReportService] memberCode : " + memberCode);
        log.info("[ReportService] reportCode : " + reportCode);
        log.info("[ReportService] offset : " + offset);

        verifyMemberReportAccess(memberCode, reportCode);

        if(reportMapper.selectReportType(reportCode) == "Casual") {
            throw new InvalidReportTypeException("해당 보고서는 정기보고가 아닙니다!");
        }
        updateReportReadStatusToRead(memberCode,reportCode);

        int totalCount = reportMapper.getReportRoundCount(reportCode);
        int limit = 10;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(offset, totalCount, limit, buttonAmount);
        selectCriteria.setSearchCondition(String.valueOf(reportCode));
        log.info("[ReportService] selectCriteria : " + selectCriteria);

        ReportDTO reportDTO = reportMapper.selectReportDetailByReportCode(reportCode);
        reportDTO.setMemberDTO(reportMapper.selectReporterDetail(reportDTO.getMemberCode()));
        log.info("[ReportService] reportDTO : " + reportDTO);

        List<ReportRoundDTO> reportRoundList = reportMapper.selectReportRoundListByReportCode(selectCriteria);
        log.info("[ReportService] reportRoundList : " + reportRoundList);

        for(ReportRoundDTO reportRoundDTO : reportRoundList) {

            reportRoundDTO.setReportedMemberCount(reportMapper.getReportedMemberCountByRoundCode(reportRoundDTO.getRoundCode()));
            reportRoundDTO.setReplyCount(reportMapper.getReplyCountByRoundCode(reportRoundDTO.getRoundCode()));
        }
        log.info("[ReportService] reportRoundList : " + reportRoundList);

        HashMap<String, Object> result = new HashMap<>();
        result.put("reportDTO", reportDTO);
        result.put("reportRoundList", reportRoundList);

        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setPageInfo(selectCriteria);
        responseDTOWithPaging.setData(result);
        log.info("[ReportService] responseDTOWithPaging : " + responseDTOWithPaging);

        return responseDTOWithPaging;
    }

    /**
    	* @MethodName : selectReportRoundDetailByRoundCode
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 정기보고 회차 상세 조회
    */
    public HashMap<String, Object> selectReportRoundDetailByRoundCode(int memberCode, Long reportCode, Long roundCode) {

        log.info("[ReportService] selectReportRoundDetailByRoundCode Start");
        log.info("[ReportService] memberCode : " + memberCode);
        log.info("[ReportService] reportCode : " + reportCode);
        log.info("[ReportService] roundCode : " + roundCode);

        verifyMemberReportAccess(memberCode, reportCode);

        updateReportReadStatusToRead(memberCode, reportCode);

        ReportRoundDTO reportRoundDetail = reportMapper.selectReportRoundDetailByRoundCode(roundCode);
        log.info("[ReportService] reportRoundDetail : " + reportRoundDetail);
        ReportDTO reportDTO = reportMapper.selectReportDetailByReportCode(reportCode);
        log.info("[ReportService] reportDTO : " + reportDTO);
        MemberDTO memberDTO = reportMapper.selectReporterDetail(reportDTO.getMemberCode());
        log.info("[ReportService] memberDTO : " + memberDTO);

        HashMap<String, Object> result = new HashMap<>();
        result.put("reportRoundDetail", reportRoundDetail);
        result.put("reportDTO", reportDTO);
        result.put("memberDTO", memberDTO);

        return result;
    }

    /**
    	* @MethodName : registerReportDetail
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 회차별 상세 보고 작성
    */
    public boolean registerReportDetail(long reportCode, ReportDetailDTO reportDetailDTO) {

        log.info("[ReportService] registerReportDetail Start");
        log.info("[ReportService] reportDetailDTO : " + reportDetailDTO);

        isReportNotCompleted(reportCode);

        verifyMemberReportAccess(reportDetailDTO.getMemberCode(), reportCode);

        int result = reportMapper.registerReportDetail(reportDetailDTO);
        log.info("[ReportService] registerReportDetail result : " + result);

        if(result == 0) {
            throw new CreationFailedException("상세 보고 작성 실패!");
        }
        return result > 0;
    }

    /**
    	* @MethodName : updateReportDetail
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 회차별 상세 보고 수정
    */
    public boolean updateReportDetail(long reportCode, ReportDetailDTO reportDetailDTO) {

        log.info("[ReportService] updateReportDetail Start");
        log.info("[ReportService] reportDetailDTO : " + reportDetailDTO);

        isReportNotCompleted(reportCode);

        isDetailReportAuthor(reportDetailDTO.getMemberCode(), reportDetailDTO.getDetailCode());

        int result = reportMapper.updateReportDetail(reportDetailDTO);
        log.info("[ReportService] updateReportDetail result : " + result);

        return result > 0;
    }

    /**
    	* @MethodName : selectReportDetailListByRoundCode
    	* @Date : 2023-03-28
    	* @Writer : 김수용
    	* @Description : 회차별 상세 보고 목록 조회
    */
    public List<ReportDetailDTO> selectReportDetailListByRoundCode(int memberCode, long reportCode, long roundCode) {

        log.info("[ReportService] selectReportDetailListByRoundCode Start");
        log.info("[ReportService] memberCode : " + memberCode);
        log.info("[ReportService] reportCode : " + reportCode);
        log.info("[ReportService] roundCode : " + roundCode);

        verifyMemberReportAccess(memberCode, reportCode);

        List<ReportDetailDTO> reportDetailList = reportMapper.selectReportDetailListByRoundCode(roundCode);
        log.info("[ReportService] reportDetailList : " + reportDetailList);

        for(ReportDetailDTO reportDetail : reportDetailList) {

            MemberDTO memberDTO = reportMapper.selectReporterDetail(reportDetail.getMemberCode());
            reportDetail.setMemberName(memberDTO.getMemberName());
            reportDetail.setJobName(memberDTO.getJobName());
        }
//        if(reportDetailList.size() == 0) {
//            throw new DataNotFoundException("조회된 상세보고 목록이 없습니다!");
//        }
        return reportDetailList;
    }

    /**
    	* @MethodName : deleteReportDetail
    	* @Date : 2023-03-28
    	* @Writer : 김수용
    	* @Description : 상세 보고 삭제
    */
    public boolean deleteReportDetail(int memberCode, long detailCode) {

        log.info("[ReportService] deleteReportDetail Start");
        log.info("[ReportService] memberCode : " + memberCode);
        log.info("[ReportService] detailCode : " + detailCode);

        isDetailReportAuthor(memberCode, detailCode);

        return reportMapper.deleteReportDetail(detailCode) > 0;
    }

    /**
    	* @MethodName : registerReportRoundReply
    	* @Date : 2023-03-28
    	* @Writer : 김수용
    	* @Description : 보고 댓글 작성
    */
    public boolean registerReportRoundReply(ReportRoundReplyDTO reportRoundReplyDTO) {

        log.info("[ReportService] registerReportRoundReply Start");
        log.info("[ReportService] memberCode : " + reportRoundReplyDTO.getMemberCode());
        log.info("[ReportService] roundCode : " + reportRoundReplyDTO.getRoundCode());
        log.info("[ReportService] replyBody : " + reportRoundReplyDTO.getReplyBody());

//        verifyMemberReportAccess(reportRoundReplyDTO.getMemberCode(), reportCode);
        int result = reportMapper.registerReportRoundReply(reportRoundReplyDTO);
        log.info("[ReportService] registerReportRoundReply result : " + result);

        if(result == 0 ) {
            throw new CreationFailedException("보고 댓글 작성 실패!");
        }
        return result > 0;
    }

    /**
    	* @MethodName : selectReportRoundReply
    	* @Date : 2023-03-28
    	* @Writer : 김수용
    	* @Description : 보고 댓글 목록 조회
    */
    public List<ReportRoundReplyDTO> selectReportRoundReply(int memberCode, long reportCode, long roundCode) {

        log.info("[ReportService] selectReportRoundReply Start");
        log.info("[ReportService] memberCode : " + memberCode);
        log.info("[ReportService] roundCode : " + roundCode);

        verifyMemberReportAccess(memberCode, reportCode);

        List<ReportRoundReplyDTO> reportReplyList = reportMapper.selectReportRoundReply(roundCode);
        log.info("[ReportService] reportReplyList : " + reportReplyList);

        for(ReportRoundReplyDTO reportReplyDTO : reportReplyList) {

            reportReplyDTO.setMemberName(reportMapper.selectReporterDetail(memberCode).getMemberName());
        }

//        if(reportReplyList.isEmpty()) {
//            throw new DataNotFoundException("조회된 보고 댓글이 없습니다!");
//        }
        return reportReplyList;
    }

    /**
    	* @MethodName : updateReportRoundReply
    	* @Date : 2023-03-28
    	* @Writer : 김수용
    	* @Description : 보고 댓글 수정
    */
    public boolean updateReportRoundReply(ReportRoundReplyDTO reportRoundReplyDTO) throws Exception {

        log.info("[ReportService] updateReportRoundReply Start");
        log.info("[ReportService] reportRoundReplyDTO : " + reportRoundReplyDTO);

        isReplyAuthor(reportRoundReplyDTO.getMemberCode(), reportRoundReplyDTO.getReplyCode());

        int result = reportMapper.updateReportRoundReply(reportRoundReplyDTO);
        log.info("[ReportService] updateReportRoundReply result : " + result);

        return result > 0;
    }

    /**
    	* @MethodName : deleteReportRoundReply
    	* @Date : 2023-03-28
    	* @Writer : 김수용
    	* @Description : 보고 댓글 삭제
    */
    public boolean deleteReportRoundReply(int memberCode, long replyCode) throws Exception {

        log.info("[ReportService] deleteReportRoundReply Start");
        log.info("[ReportService] memberCode : " + memberCode);
        log.info("[ReportService] replyCode : " + replyCode);

        isReplyAuthor(memberCode,replyCode);

        int result = reportMapper.deleteReportRoundReply(replyCode);
        log.info("[ReportService] deleteReportRoundReply result : " + result);

        return result > 0;
    }

//    /**
//    	* @MethodName : selectReporterCount
//    	* @Date : 2023-04-03
//    	* @Writer : 김수용
//    	* @Description : 보고 현황 조회
//    */
//    public HashMap<String, Object> selectReporterCount(long reportCode, long roundCode) throws Exception {
//
//        log.info("[ReportService] selectReporterCount Start");
//        log.info("[ReportService] reportCode : " + reportCode);
//        log.info("[ReportService] roundCode : " + roundCode);
//
//        int totalReporter = reportMapper.getReportRoundCapacity(reportCode);
//        int reportDetailCount = reportMapper.getReportDetailCountByRoundCode(roundCode);
//        log.info("[ReportService] capacity : " + totalReporter);
//        log.info("[ReportService] reportDetailCount : " + reportDetailCount);
//
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("totalReporter", totalReporter);
//        result.put("reportDetailCount", reportDetailCount);
//
//        return result;
//    }
}
