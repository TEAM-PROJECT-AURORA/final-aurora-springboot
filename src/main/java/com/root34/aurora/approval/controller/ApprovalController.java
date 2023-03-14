package com.root34.aurora.approval.controller;

import com.root34.aurora.approval.dto.ApprovalDTO;
import com.root34.aurora.approval.dto.DocumentDTO;
import com.root34.aurora.approval.service.ApprovalService;
import com.root34.aurora.common.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 @FileName : ApprovalController
 @Date : 2:03 PM
 @작성자 : heojaehong
 @프로그램 설명 : 결재 컨트롤러 클래스
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/approvals")
public class ApprovalController {

    private final ApprovalService approvalService;
    /**
        @MethodName : ApprovalController
    	@Date : 3:01 PM
    	@Writer : heojaehong
    	@Method Description : 컨트롤러 생성자 메소드
    */
    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    /**
        @MethodName : approve
    	@Date : 3:07 PM
    	@Writer : heojaehong
    	@Method Description : 결재서류 생성을 위한 메소드
    */
    @PostMapping("/draft/form/{docCode}")
    public ResponseEntity<ResponseDto> approve(@RequestBody DocumentDTO documentDTO){

        ApprovalDTO approvalDTO = new ApprovalDTO();
        approvalDTO.setDocumentDto(documentDTO);


        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"등록 성공",approvalService.approve(approvalDTO)));
    }
}
