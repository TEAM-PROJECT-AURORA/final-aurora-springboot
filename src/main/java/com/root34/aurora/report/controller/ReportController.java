package com.root34.aurora.report.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.report.dto.ReportDTO;
import com.root34.aurora.report.dto.ReportRoundDTO;
import com.root34.aurora.report.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
	@ClassName : ReportController
	@Date : 2023-03-21
	@Writer : 김수용
	@Description : 보고 관련 요청을 처리하는 컨트롤러
*/
//@Api(tags = {"reports"}) // Swagger
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
//    @ApiOperation(value = "보고 작성")
    @Transactional
    @PostMapping(value = "/reports")
    public ResponseEntity<ResponseDTO> registerReport(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {

        log.info("[ReportController] registerReport");
        List<Integer> memberList = (List<Integer>) requestData.get("memberList");
        if(memberList.size() == 0) {
            return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST, "보고서 작성 실패! - 보고자를 등록해주세요", null));
        }
        log.info("[ReportController] memberList : " + memberList);

        Integer memberCode = (Integer) request.getAttribute("memberCode");
        log.info("[ReportController] memberCode : " + memberCode);

        ReportDTO reportDTO = new ObjectMapper().convertValue(requestData.get("reportDTO"), ReportDTO.class);
        reportDTO.setMemberCode(memberCode);
        log.info("[ReportController] reportDTO : " + reportDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "보고서 작성 성공", reportService.registerReport(reportDTO, memberList)));
    }

    /**
     * @MethodName : getAllReportList
     * @Date : 2023-03-23
     * @Writer : 김수용
     * @Description : 전체 보고 조회 - 보고 메인 페이지용
     */
//    @ApiOperation(value = "전체 보고 조회") // Swagger
    @GetMapping(value = "/reports")
    public ResponseEntity<ResponseDTO> getAllReportList(HttpServletRequest request) {

        log.info("[ReportController] getAllReportList");
        Integer memberCode = (Integer) request.getAttribute("memberCode");
        log.info("[ReportController] memberCode : " + memberCode);

        HashMap<String, Object> response = reportService.getReportSummary(memberCode);
        log.info("[ReportController] response : " + response);

        if (((List) response.get("routineList1")).size() == 0 && ((List) response.get("casualList")).size() == 0) {
            log.info("[ReportController] getAllReportList : 조회된 보고서가 없습니다.");
//            log.info("[BoardService] selectBoardListWithPaging End ===================================");
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회된 보고서가 없습니다.", null));
        } else {
//            log.info("[BoardService] selectBoardListWithPaging End ===================================");
            log.info("[ReportController] getAllReportList : 전체 보고서 목록 조회 성공");
//            return ResponseEntity.internalServerError().body(ResponseDTO.status(HttpStatus.InternalServerError));
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전체 보고서 목록 조회 성공", response));
        }
    }

    /**
     * @MethodName : registerReportRound
     * @Date : 2023-03-23
     * @Writer : 김수용
     * @Description : 보고 회차 등록
     */
//    @ApiOperation(value = "보고 회차 등록") // Swagger
    @Transactional
    @PostMapping(value = "/reports/routines")
    public ResponseEntity<ResponseDTO> registerReportRound(@RequestBody ReportRoundDTO reportRoundDTO) {

        log.info("[ReportController] registerReportRound");
        log.info("[ReportController] ReportRoundDTO : " + reportRoundDTO);

        return reportService.registerReportRound(reportRoundDTO)?
                ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "보고 회차 등록 성공", true))
                : ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST, "보고 회차 등록 실패!", false));
    }

    /**
     * @MethodName : updateReport
     * @Date : 2023-03-23
     * @Writer : 김수용
     * @Description : 보고 수정
     */
    //    @ApiOperation(value = "보고 수정") // Swagger
    @Transactional
    @PutMapping(value = "/reports")
    public ResponseEntity<ResponseDTO> updateReport(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {

        log.info("[ReportController] updateReport");
        List<Integer> memberList = (List<Integer>) requestData.get("memberList");

        if(memberList.size() == 0) {
            return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST, "보고서 수정 실패! - 보고자를 등록해주세요", null));
        }
        log.info("[ReportController] memberList : " + memberList);

        Integer memberCode = (Integer) request.getAttribute("memberCode");
        log.info("[ReportController] memberCode : " + memberCode);

        ReportDTO reportDTO = new ObjectMapper().convertValue(requestData.get("reportDTO"), ReportDTO.class);
        reportDTO.setMemberCode(memberCode);
        log.info("[ReportController] reportDTO : " + reportDTO);

        return reportService.updateReport(reportDTO, memberList)?
                ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "보고 수정 성공", true))
                : ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.BAD_REQUEST, "보고 수정 실패!", false));
    }

    /**
     * @MethodName : selectRoutineReportList
     * @Date : 2023-03-24
     * @Writer : 김수용
     * @Description : 정기보고 목록 조회
     */
//    @ApiOperation(value = "정기보고 목록 조회") // Swagger
    @GetMapping(value = "/reports/routine/active")
    public ResponseEntity<ResponseDTO> selectRoutineReportList(HttpServletRequest request, @RequestParam int offset) {

        log.info("[ReportController] selectRoutineReportList");
        log.info("[ReportController] offset : " + offset);

        HashMap<String, Object> searchConditions = new HashMap<>();
        Integer memberCode = (Integer) request.getAttribute("memberCode");
        searchConditions.put("memberCode", memberCode);
        searchConditions.put("reportType", "Routine");
        searchConditions.put("completionStatus", 'N');
        log.info("[ReportController] searchConditions : " + searchConditions);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "정기보고 목록 조회 성공", reportService.selectReportListByConditions(offset, searchConditions)));
    }

    /**
     * @MethodName : selectCompletedRoutineReportList
     * @Date : 2023-03-24
     * @Writer : 김수용
     * @Description : 완료된 정기보고 목록 조회
     */
//    @ApiOperation(value = "완료된 정기보고 목록 조회") // Swagger
    @GetMapping(value = "/reports/routine/completed")
    public ResponseEntity<ResponseDTO> selectCompletedRoutineReportList(HttpServletRequest request, @RequestParam int offset) {

        log.info("[ReportController] selectCompletedRoutineReportList");
        log.info("[ReportController] offset : " + offset);

        HashMap<String, Object> searchConditions = new HashMap<>();
        Integer memberCode = (Integer) request.getAttribute("memberCode");
        searchConditions.put("memberCode", memberCode);
        searchConditions.put("reportType", "Routine");
        searchConditions.put("completionStatus", 'Y');
        log.info("[ReportController] searchConditions : " + searchConditions);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "완료된 정기보고 목록 조회 성공", reportService.selectReportListByConditions(offset, searchConditions)));
    }

    /**
     * @MethodName : selectCasualReportList
     * @Date : 2023-03-24
     * @Writer : 김수용
     * @Description : 비정기보고 목록 조회
     */
//    @ApiOperation(value = "비정기보고 목록 조회") // Swagger
    @GetMapping(value = "/reports/casual/active")
    public ResponseEntity<ResponseDTO> selectCasualReportList(HttpServletRequest request, @RequestParam int offset) {

        log.info("[ReportController] selectCasualReportList");
        log.info("[ReportController] offset : " + offset);

        HashMap<String, Object> searchConditions = new HashMap<>();
        Integer memberCode = (Integer) request.getAttribute("memberCode");
        searchConditions.put("memberCode", memberCode);
        searchConditions.put("reportType", "Casual");
        searchConditions.put("completionStatus", 'N');
        log.info("[ReportController] searchConditions : " + searchConditions);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "비정기보고 목록 조회 성공", reportService.selectReportListByConditions(offset, searchConditions)));
    }

    /**
     * @MethodName : selectCompletedCasualReportList
     * @Date : 2023-03-24
     * @Writer : 김수용
     * @Description : 완료된 비정기보고 목록 조회
     */
//    @ApiOperation(value = "완료된 비정기보고 목록 조회") // Swagger
    @GetMapping(value = "/reports/casual/completed")
    public ResponseEntity<ResponseDTO> selectCompletedCasualReportList(HttpServletRequest request, @RequestParam int offset) {

        log.info("[ReportController] selectCompletedCasualReportList");
        log.info("[ReportController] offset : " + offset);

        HashMap<String, Object> searchConditions = new HashMap<>();
        Integer memberCode = (Integer) request.getAttribute("memberCode");
        searchConditions.put("memberCode", memberCode);
        searchConditions.put("reportType", "Casual");
        searchConditions.put("completionStatus", 'Y');
        log.info("[ReportController] searchConditions : " + searchConditions);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "완료된 비정기보고 목록 조회 성공", reportService.selectReportListByConditions(offset, searchConditions)));
    }

    /**
    	* @MethodName : selectReportRoundListByReportCode
    	* @Date : 2023-03-26
    	* @Writer : 김수용
    	* @Description : 정기보고 회차 목록 조회
    */
    //    @ApiOperation(value = "정기보고 회차 목록 조회") // Swagger
    @GetMapping(value = "/reports/routine/{reportCode}/rounds")
    public ResponseEntity<ResponseDTO> selectReportRoundListByReportCode(HttpServletRequest request, @PathVariable Long reportCode, @RequestParam int offset) {

        log.info("[ReportController] selectReportRoundListByReportCode");
        log.info("[ReportController] reportCode : " + reportCode);
        log.info("[ReportController] offset : " + offset);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "정기보고 회차 목록 조회 성공", reportService.selectReportRoundListByReportCode(reportCode, offset)));
    }

    /**
    	* @MethodName : selectReportRoundDetailByRoundCode
    	* @Date : 2023-03-27
    	* @Writer : 김수용
    	* @Description : 정기보고 회차 상세 조회
    */
    @GetMapping(value = "/reports/routine/{reportCode}/rounds/{roundCode}")
    public ResponseEntity<ResponseDTO> selectReportRoundDetailByRoundCode(HttpServletRequest request, @PathVariable Long reportCode, @PathVariable Long roundCode) {

        log.info("[ReportController] selectReportRoundDetailByRoundCode");
        Integer memberCode = (Integer) request.getAttribute("memberCode");
        log.info("[ReportController] memberCode : " + memberCode);

        log.info("[ReportController] reportCode : " + reportCode);
        log.info("[ReportController] roundCode : " + roundCode);

        ReportRoundDTO reportRoundDetail = reportService.selectReportRoundDetailByRoundCode(roundCode);
        log.info("[ReportController] reportRoundDetail : " + reportRoundDetail);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "정기보고 회차 상세 조회 성공", reportRoundDetail));
    }
}
