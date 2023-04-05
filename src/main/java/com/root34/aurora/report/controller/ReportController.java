package com.root34.aurora.report.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.report.dto.ReportDTO;
import com.root34.aurora.report.dto.ReportDetailDTO;
import com.root34.aurora.report.dto.ReportRoundDTO;
import com.root34.aurora.report.dto.ReportRoundReplyDTO;
import com.root34.aurora.report.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
	@ClassName : ReportController
	@Date : 2023-03-21
	@Writer : 김수용
	@Description : 보고 관련 요청을 처리하는 컨트롤러
*/
@Slf4j
@RestController
@RequestMapping("api/v1")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * @MethodName : registerReport
     * @Date : 2023-03-22
     * @Writer : 김수용
     * @Description : 보고 작성
     */
    @Transactional
//    @PostMapping(value = "/reports", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PostMapping(value = "/reports", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDTO> registerReport(HttpServletRequest request,
                                                      @RequestPart("reportDTO") ReportDTO reportDTO,
                                                      @RequestPart("memberList") List<Integer> memberList,
                                                      @RequestPart(name = "fileList", required = false)List<MultipartFile> fileList) {

        try {
            log.info("[ReportController] registerReport Start");
            log.info("[ReportController] request : " + request.getHeader("Authorization"));
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[ReportController] memberCode : " + memberCode);
            reportDTO.setMemberCode(memberCode);

            log.info("[ReportController] memberList : " + memberList);
            log.info("[ReportController] reportDTO : " + reportDTO);
            log.info("[ReportController] fileList : " + fileList);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "보고서 작성 성공",
                    reportService.registerReport(reportDTO, memberList, fileList)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : getAllReportList
     * @Date : 2023-03-23
     * @Writer : 김수용
     * @Description : 전체 보고 조회 - 보고 메인 페이지용
     */
    @GetMapping(value = "/reports/summary")
    public ResponseEntity<ResponseDTO> getReportSummary(HttpServletRequest request) {

        try {
            log.info("[ReportController] getAllReportList Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[ReportController] memberCode : " + memberCode);

            HashMap<String, Object> response = reportService.getReportSummary(memberCode);
            log.info("[ReportController] response : " + response);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전체 보고서 목록 조회 성공", response));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : selectRoutineReportList
     * @Date : 2023-03-24
     * @Writer : 김수용
     * @Description : 조건별 정기보고 목록 조회
     */
    @GetMapping(value = "/reports/routines")
    public ResponseEntity<ResponseDTO> selectRoutineReportListByConditions(HttpServletRequest request,
//                                                               @RequestParam String reportType,
                                                               @RequestParam char completionStatus,
                                                               @RequestParam int offset) {

        try {
            log.info("[ReportController] selectRoutineReportList Start");
            log.info("[ReportController] offset : " + offset);

            HashMap<String, Object> searchConditions = new HashMap<>();
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            searchConditions.put("memberCode", memberCode);
            searchConditions.put("reportType", "Routine");
            searchConditions.put("completionStatus", completionStatus);
            log.info("[ReportController] searchConditions : " + searchConditions);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보고 목록 조회 성공",
                    reportService.selectReportListByConditions(offset, searchConditions)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : selectRoutineReportList
     * @Date : 2023-03-24
     * @Writer : 김수용
     * @Description : 조건별 비정기보고 목록 조회
     */
    @GetMapping(value = "/reports/casuals")
    public ResponseEntity<ResponseDTO> selectCasualReportListByConditions(HttpServletRequest request,
//                                                                    @RequestParam String reportType,
                                                                    @RequestParam char completionStatus,
                                                                    @RequestParam int offset) {

        try {
            log.info("[ReportController] selectRoutineReportList Start");
            log.info("[ReportController] offset : " + offset);

            HashMap<String, Object> searchConditions = new HashMap<>();
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            searchConditions.put("memberCode", memberCode);
//            searchConditions.put("reportType", reportType);
            searchConditions.put("reportType", "Casual");
            searchConditions.put("completionStatus", completionStatus);
            log.info("[ReportController] searchConditions : " + searchConditions);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보고 목록 조회 성공",
                    reportService.selectReportListByConditions(offset, searchConditions)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : selectCasualReportDetailByReportCode
     * @Date : 2023-03-27
     * @Writer : 김수용
     * @Description : 비정기보고 상세 조회
     */
    @GetMapping("/reports/casual/{reportCode}")
    public ResponseEntity<ResponseDTO> selectCasualReportDetailByReportCode(HttpServletRequest request,
                                                                            @PathVariable Long reportCode) {

        try {
            log.info("[ReportController] selectCasualReportDetailByReportCode Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[ReportController] memberCode : " + memberCode);
            log.info("[ReportController] reportCode : " + reportCode);

            HashMap<String, Object> casualReportDetail = reportService.selectCasualReportDetailByReportCode(memberCode, reportCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "비정기보고 상세 조회 성공",
                    casualReportDetail));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

//            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "보고서 작성 성공",
//                    reportService.registerReport(reportDTO, memberList, fileList)));
//        } catch (Exception e) {
//            log.info("[ReportController] Exception : " + e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
//        }
//    }
    /**
     * @MethodName : updateReport
     * @Date : 2023-03-23
     * @Writer : 김수용
     * @Description : 보고 수정
     */
    @Transactional
    @PutMapping(value = "/reports", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDTO> updateReport(HttpServletRequest request,
                                                      @RequestPart("reportDTO") ReportDTO reportDTO,
                                                      @RequestPart("memberList") List<Integer> memberList,
                                                      @RequestPart(name = "fileList", required = false)List<MultipartFile> fileList) {

        try {
            log.info("[ReportController] updateReport Start");

            if(memberList.size() == 0) {
                return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST, "보고서 수정 실패! - 보고자를 등록해주세요", null));
            }
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            reportDTO.setMemberCode(memberCode);

            log.info("[ReportController] reportDTO : " + reportDTO);
            log.info("[ReportController] memberList : " + memberList);
            log.info("[ReportController] fileList : " + fileList);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보고 수정 성공",
                    reportService.updateReport(reportDTO, memberList, fileList)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }
//    /**
//     * @MethodName : updateReport
//     * @Date : 2023-03-23
//     * @Writer : 김수용
//     * @Description : 보고 수정
//     */
//    @Transactional
//    @PutMapping(value = "/reports")
//    public ResponseEntity<ResponseDTO> updateReport(HttpServletRequest request,
//                                                    @RequestBody Map<String, Object> requestData) {
//
//        try {
//            log.info("[ReportController] updateReport Start");
//            List<Integer> memberList = (List<Integer>) requestData.get("memberList");
//
//            if(memberList.size() == 0) {
//                return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST, "보고서 수정 실패! - 보고자를 등록해주세요", null));
//            }
//            log.info("[ReportController] memberList : " + memberList);
//
//            Integer memberCode = (Integer) request.getAttribute("memberCode");
//            log.info("[ReportController] memberCode : " + memberCode);
//
//            ReportDTO reportDTO = new ObjectMapper().convertValue(requestData.get("reportDTO"), ReportDTO.class);
//            reportDTO.setMemberCode(memberCode);
//            log.info("[ReportController] reportDTO : " + reportDTO);
//
//            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보고 수정 성공",
//                    reportService.updateReport(memberCode, reportDTO, memberList)));
//        } catch (Exception e) {
//            log.info("[ReportController] Exception : " + e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
//        }
//    }

    /**
     * @MethodName : updateReportCompletionStatusToComplete
     * @Date : 2023-03-27
     * @Writer : 김수용
     * @Description : 보고 완료상태 수정 - 완료
     */
    @DeleteMapping("/reports/{reportCode}")
    public ResponseEntity<ResponseDTO> updateReportCompletionStatusToComplete(HttpServletRequest request,
                                                                              @PathVariable Long reportCode) {

        try {
            log.info("[ReportController] updateReportCompletionStatusToComplete Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[ReportController] memberCode : " + memberCode);
            log.info("[ReportController] reportCode : " + reportCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보고 완료상태 수정 성공",
                    reportService.updateReportCompletionStatusToComplete(memberCode, reportCode)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : registerReportRound
     * @Date : 2023-03-23
     * @Writer : 김수용
     * @Description : 보고 회차 등록
     */
    @Transactional
    @PostMapping(value = "/reports/rounds")
    public ResponseEntity<ResponseDTO> registerReportRound(@RequestBody ReportRoundDTO reportRoundDTO) {

        try {
            log.info("[ReportController] registerReportRound Start");
            log.info("[ReportController] ReportRoundDTO : " + reportRoundDTO);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "보고 회차 등록 성공",
                    reportService.registerReportRound(reportRoundDTO)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : selectReportRoundListByReportCode
    	* @Date : 2023-03-26
    	* @Writer : 김수용
    	* @Description : 보고 회차 목록 조회
    */
    @GetMapping(value = "/reports/{reportCode}/rounds")
    public ResponseEntity<ResponseDTO> selectReportRoundListByReportCode(HttpServletRequest request,
                                                                         @PathVariable Long reportCode,
                                                                         @RequestParam int offset) {

        try {
            log.info("[ReportController] selectReportRoundListByReportCode Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[ReportController] memberCode : " + memberCode);
            log.info("[ReportController] reportCode : " + reportCode);
            log.info("[ReportController] offset : " + offset);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "정기보고 회차 목록 조회 성공",
                    reportService.selectReportRoundListByReportCode(memberCode, reportCode, offset)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : selectReportRoundDetailByRoundCode
     * @Date : 2023-03-27
     * @Writer : 김수용
     * @Description : 보고 회차 상세 조회
     */
    @GetMapping(value = "/reports/routine/{reportCode}/rounds/{roundCode}")
    public ResponseEntity<ResponseDTO> selectReportRoundDetailByRoundCode(HttpServletRequest request,
                                                                          @PathVariable Long reportCode,
                                                                          @PathVariable Long roundCode) {

        try {
            log.info("[ReportController] selectReportRoundDetailByRoundCode Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[ReportController] memberCode : " + memberCode);
            log.info("[ReportController] reportCode : " + reportCode);
            log.info("[ReportController] roundCode : " + roundCode);

            HashMap<String, Object> response = reportService.selectReportRoundDetailByRoundCode(memberCode, reportCode, roundCode);
            log.info("[ReportController] response : " + response);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "정기보고 회차 상세 조회 성공",
                    response));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : updateReportRound
     * @Date : 2023-04-05
     * @Writer : 김수용
     * @Description : 보고 회차 수정
     */
    @Transactional
    @PutMapping(value = "/reports/rounds")
    public ResponseEntity<ResponseDTO> updateReportRound(@RequestBody ReportRoundDTO reportRoundDTO) {

        try {
            log.info("[ReportController] updateReportRound Start");
            log.info("[ReportController] ReportRoundDTO : " + reportRoundDTO);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보고 회차 수정 성공",
                    reportService.updateReportRound(reportRoundDTO)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : deleteReportRound
     * @Date : 2023-04-05
     * @Writer : 김수용
     * @Description : 보고 회차 삭제
     */
    @Transactional
    @DeleteMapping(value = "/reports/rounds")
    public ResponseEntity<ResponseDTO> deleteReportRound(@RequestBody long roundCode) {

        try {
            log.info("[ReportController] deleteReportRound Start");
            log.info("[ReportController] roundCode : " + roundCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보고 회차 삭제 성공",
                    reportService.deleteReportRound(roundCode)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : registerReportDetail
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 상세 보고 작성
    */
    @PostMapping("/reports/{reportCode}/rounds/{roundCode}/detail-reports")
    public ResponseEntity<ResponseDTO> registerReportDetail(HttpServletRequest request,
                                                            @PathVariable long reportCode,
                                                            @PathVariable int roundCode,
                                                            @RequestBody String detailBody) {
//                                                            @RequestBody ReportDetailDTO reportDetailDTO) {

        try {
            log.info("[ReportController] registerReportDetail Start");
            log.info("[ReportController] reportCode : " + reportCode);

            Integer memberCode = (Integer) request.getAttribute("memberCode");

            ReportDetailDTO reportDetailDTO = new ReportDetailDTO();
            reportDetailDTO.setDetailBody(detailBody);
            reportDetailDTO.setMemberCode(memberCode);
            reportDetailDTO.setRoundCode(roundCode);
            log.info("[ReportController] reportDetailDTO : " + reportDetailDTO);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상세 보고 작성 성공",
                    reportService.registerReportDetail(reportCode, reportDetailDTO)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : selectReportDetailListByRoundCode
     * @Date : 2023-03-28
     * @Writer : 김수용
     * @Description : 상세 보고 목록 조회
     */
    @GetMapping("/reports/{reportCode}/rounds/{roundCode}/detail-reports")
    public ResponseEntity<ResponseDTO> selectReportDetailListByRoundCode(HttpServletRequest request,
                                                                         @PathVariable long reportCode,
                                                                         @PathVariable long roundCode) {

        try {
            log.info("[ReportController] selectReportDetailListByRoundCode Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[ReportController] memberCode : " + memberCode);
            log.info("[ReportController] reportCode : " + reportCode);
            log.info("[ReportController] roundCode : " + roundCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상세 보고 목록 조회 성공",
                    reportService.selectReportDetailListByRoundCode(memberCode, reportCode, roundCode)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : updateReportDetail
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 상세 보고 수정
    */
    @PutMapping("/reports/{reportCode}/rounds/{roundCode}/detail-reports")
    public ResponseEntity<ResponseDTO> updateReportDetail(HttpServletRequest request,
                                                          @PathVariable long reportCode,
                                                          @PathVariable long roundCode,
                                                          @RequestBody ReportDetailDTO reportDetailDTO) {

        try {
            log.info("[ReportController] updateReportDetail Start");
            log.info("[ReportController] reportCode : " + reportCode);

            Integer memberCode = (Integer) request.getAttribute("memberCode");

            reportDetailDTO.setMemberCode(memberCode);
            reportDetailDTO.setRoundCode(roundCode);
            log.info("[ReportController] reportDetailDTO : " + reportDetailDTO);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상세 보고 수정 성공",
                    reportService.updateReportDetail(reportCode, reportDetailDTO)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : deleteReportDetail
    	* @Date : 2023-03-28
    	* @Writer : 김수용
    	* @Description : 상세 보고 삭제
    */
    // [] Optional Path Parameters : 해당 값을 선택적으로 입력 할 수 있음, 대부분 웹 프레임워크에서 지원 x
    @DeleteMapping("/reports/rounds/detail-reports/{detailCode}")
    public ResponseEntity<ResponseDTO> deleteReportDetail(HttpServletRequest request,
                                                          @PathVariable long detailCode) {

        try {
            log.info("[ReportController] selectReportDetailListByRoundCode Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[ReportController] memberCode : " + memberCode);
            log.info("[ReportController] detailCode : " + detailCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상세 보고 삭제 성공",
                    reportService.deleteReportDetail(memberCode, detailCode)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : registerReportRoundReply
    	* @Date : 2023-03-28
    	* @Writer : 김수용
    	* @Description : 보고 댓글 작성
    */
    @PostMapping("/reports/rounds/{roundCode}/comments")
    public ResponseEntity<ResponseDTO> registerReportRoundReply(HttpServletRequest request,
                                                                @PathVariable long roundCode,
                                                                @RequestBody String replyBody) {

        try {
            log.info("[ReportController] registerReportRoundReply Start");

            ReportRoundReplyDTO reportRoundReplyDTO = new ReportRoundReplyDTO();
            reportRoundReplyDTO.setReplyBody(replyBody);
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            reportRoundReplyDTO.setMemberCode(memberCode);
            reportRoundReplyDTO.setRoundCode(roundCode);
            log.info("[ReportController] reportRoundReplyDTO : " + reportRoundReplyDTO);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보고 댓글 작성 성공",
                    reportService.registerReportRoundReply(reportRoundReplyDTO)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : selectReportRoundReply
    	* @Date : 2023-03-28
    	* @Writer : 김수용
    	* @Description : 보고 댓글 목록 조회
    */
    @GetMapping("/reports/{reportCode}/rounds/{roundCode}/comments")
    public ResponseEntity<ResponseDTO> selectReportRoundReply(HttpServletRequest request,
                                                              @PathVariable long reportCode,
                                                              @PathVariable long roundCode) {

        try {
            log.info("[ReportController] registerReportRoundReply Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[ReportController] memberCode : " + memberCode);
            log.info("[ReportController] reportCode : " + reportCode);
            log.info("[ReportController] roundCode : " + roundCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보고 댓글 목록 조회 성공",
                    reportService.selectReportRoundReply(memberCode, reportCode, roundCode)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : updateReportRoundReply
    	* @Date : 2023-03-28
    	* @Writer : 김수용
    	* @Description : 보고 댓글 수정
    */
    @PutMapping("/reports/rounds/{roundCode}/comments/{replyCode}")
    public ResponseEntity<ResponseDTO> updateReportRoundReply(HttpServletRequest request,
                                                              @PathVariable long roundCode,
                                                              @PathVariable long replyCode,
                                                              @RequestBody String replyBody) {

        try {
            log.info("[ReportController] updateReportRoundReply Start");

            ReportRoundReplyDTO reportRoundReplyDTO = new ReportRoundReplyDTO();
            reportRoundReplyDTO.setReplyBody(replyBody);
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            reportRoundReplyDTO.setMemberCode(memberCode);
            reportRoundReplyDTO.setReplyCode(replyCode);
            reportRoundReplyDTO.setRoundCode(roundCode);
            log.info("[ReportController] reportRoundReplyDTO : " + reportRoundReplyDTO);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보고 댓글 수정 성공",
                    reportService.updateReportRoundReply(reportRoundReplyDTO)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : deleteReportRoundReply
    	* @Date : 2023-03-28
    	* @Writer : 김수용
    	* @Description : 보고 댓글 삭제
    */
    @DeleteMapping("/reports/rounds/comments/{replyCode}")
    public ResponseEntity<ResponseDTO> deleteReportRoundReply(HttpServletRequest request,
                                                              @PathVariable long replyCode) {

        try{
            log.info("[ReportController] updateReportRoundReply Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[ReportController] memberCode : " + memberCode);
            log.info("[ReportController] replyCode : " + replyCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보고 댓글 삭제 성공",
                    reportService.deleteReportRoundReply(memberCode, replyCode)));
        } catch (Exception e) {
            log.info("[ReportController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }
}
