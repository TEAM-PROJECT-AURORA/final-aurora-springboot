package com.root34.aurora.approval.controller;

import com.root34.aurora.approval.dto.ApprovalDTO;
import com.root34.aurora.approval.dto.DocumentDTO;
import com.root34.aurora.approval.service.ApprovalService;
import com.root34.aurora.common.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 @FileName : ApprovalController
 @Date : 2:03 PM
 @작성자 : heojaehong
 @프로그램 설명 : 결재 컨트롤러 클래스
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    /**
        @MethodName : lastList
    	@Date : 4:20 PM
    	@Writer : heojaehong
    	@Method Description : 최근 리스트 조회
    */
    @GetMapping("/approvals")
    public ResponseEntity<ResponseDto> lastList(@RequestBody ApprovalDTO approvalDTO){
        log.info("[ApprovalController] GetMapping lastList: " + approvalDTO);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"등록 성공",approvalService.lastList()));
    }
    /**
        @MethodName : approve
    	@Date : 3:07 PM
    	@Writer : heojaehong
    	@Method Description : 결재서류 생성을 위한 메소드
    */
    @PostMapping("/approvals/draft/form/{docCode}")
    public ResponseEntity<ResponseDto> approve(@RequestBody DocumentDTO documentDTO){
        log.info("[ApprovalController] postMapping docCode: " + documentDTO.getDocCode());
        ApprovalDTO approvalDTO = new ApprovalDTO();
        approvalDTO.setDocumentDto(documentDTO);


        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"등록 성공",approvalService.approve(approvalDTO)));
    }
}
