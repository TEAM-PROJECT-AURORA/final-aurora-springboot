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

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"조회 성공",approvalService.lastList()));
    }

    /**
     @MethodName : pendingList
     @Date : 12:09 AM
     @Writer : heojaehong
     @Method Description : 미결재 리스트 조회
     */
    @GetMapping("/approvals/pending")
    public ResponseEntity<ResponseDto> pendingList(@RequestBody ApprovalDTO approvalDTO){
        log.info("[ApprovalController] GetMapping lastList: " + approvalDTO);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"조회 성공",approvalService.pendingList()));
    }

    /**
     @MethodName : completedList
     @Date : 12:09 AM
     @Writer : heojaehong
     @Method Description : 결재완료 리스트 조회
     */
    @GetMapping("/approvals/completed")
    public ResponseEntity<ResponseDto> completedList(@RequestBody ApprovalDTO approvalDTO){

        log.info("[ApprovalController] GetMapping lastList: " + approvalDTO);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"조회 성공",approvalService.completedList()));
    }

    /**
        @MethodName : detailApprove
    	@Date : 2:01 PM
    	@Writer : heojaehong
    	@Method Description : 결재 상세정보 조회
    */
    @GetMapping("/approvals/{appCode}")
    public ResponseEntity<ResponseDto> detailApprove(@PathVariable int appCode){

        ApprovalDTO approvalDTO = new ApprovalDTO();
        log.info("[ApprovalController] GetMapping detailApprove: " + approvalDTO.toString());

        approvalDTO.setAppCode(appCode);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "상세정보 조회 성공", approvalService.detailApprove(appCode)));
    }

    /**
        @MethodName : approve
    	@Date : 3:07 PM
    	@Writer : heojaehong
    	@Description : 결재서류 생성을 위한 메소드
    */
    @PostMapping("/approvals/draft/form/{docCode}")
    public ResponseEntity<ResponseDto> approve(@PathVariable int docCode, @RequestBody ApprovalDTO approvalDTO) throws Exception {
        log.info("[ApprovalController] postMapping docCode: " + approvalDTO.getDocumentDto().getDocCode());

        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDocCode(docCode);
        approvalDTO.setDocumentDto(documentDTO);

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK,"등록 성공",approvalService.approve(approvalDTO)));
    }
    /**
        @MethodName : updateApproval
    	@Date : 2:10 PM
    	@Writer : heojaehong
    	@Description : 결재 수정
    */
    @PutMapping("approvals/{docCode}")
    public ResponseEntity<ResponseDto> updateApproval(@PathVariable int docCode, @RequestBody ApprovalDTO approvalDTO){

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "수정 성공", approvalService.updateApproval(docCode)));
    }
}
