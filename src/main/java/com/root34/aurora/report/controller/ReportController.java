package com.root34.aurora.report.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.report.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @MethodName :
     * @Date :
     * @Writer :
     * @Method Description :
     */
//    @ApiOperation(value = "보고 조회") // Swagger
    @Transactional
    @PostMapping(value ="/reports")
    public ResponseEntity<ResponseDTO> getAllReportList() {

//        log.info("[BoardService] selectBoardListWithPaging Start ===================================");
        long memberCode = 1;

        if(reportService.getAllReportList(memberCode).size() == 0) {
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
