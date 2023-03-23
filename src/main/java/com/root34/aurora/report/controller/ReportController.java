package com.root34.aurora.report.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.report.dto.ReportDTO;
import com.root34.aurora.report.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
	* @Description : 보고 작성 요청을 처리해줄 메소드
*/
//    @ApiOperation(value = "보고서 작성")
    @Transactional
    @PostMapping(value = "/reports")
//    public ResponseEntity<ResponseDTO> registerReport(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Map<String, Object> requestData) {
    public ResponseEntity<ResponseDTO> registerReport(HttpServletRequest request, @RequestBody Map<String, Object> requestData) {

//        String jwtToken = authorizationHeader.substring(7); // "Bearer" 제거
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        int memberCode = authentication.get
        int memberCode = (int)request.getAttribute("memberCode");

        log.info("memberCode : " + memberCode);

        ReportDTO reportDTO = new ObjectMapper().convertValue(requestData.get("reportDTO"), ReportDTO.class);
        List<Integer> memberList = (List<Integer>) requestData.get("memberList");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "보고서 작성 성공", reportService.registerReport(reportDTO, memberList)));
    }

    /**
     * @MethodName :
     * @Date :
     * @Writer :
     * @Method Description :
     */
//    @ApiOperation(value = "보고 조회") // Swagger
    @Transactional
    @GetMapping(value ="/reports")
    public ResponseEntity<ResponseDTO> getAllReportList() {

//        log.info("[BoardService] selectBoardListWithPaging Start ===================================");
        long memberCode = 1;

        if(reportService.getReportSummary(memberCode).size() == 0) {
//            log.info("[BoardService] selectBoardListWithPaging End ===================================");
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회된 보고서가 없습니다.", true));
        } else {
//            log.info("[BoardService] selectBoardListWithPaging End ===================================");
//            return ResponseEntity.internalServerError().body(ResponseDTO.status(HttpStatus.InternalServerError));
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전체 보고서 목록 조회 성공", false));
        }
    }

//    보고 등록시 보고자 0명이면 돌려보내기
}
