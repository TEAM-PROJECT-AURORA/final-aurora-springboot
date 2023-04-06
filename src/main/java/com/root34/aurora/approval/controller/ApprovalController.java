package com.root34.aurora.approval.controller;

import com.root34.aurora.approval.dto.ApprovalDTO;
import com.root34.aurora.approval.dto.ApprovalLineDTO;
import com.root34.aurora.approval.dto.DocumentDTO;
import com.root34.aurora.approval.service.ApprovalService;
import com.root34.aurora.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    	@Description : 최근 리스트 조회
    */
    @GetMapping("/approvals/{memberCode}")
    public ResponseEntity<ResponseDTO> lastList(@PathVariable int memberCode){

        log.info("[ApprovalController] GetMapping lastList start ");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회 성공",approvalService.lastList(memberCode)));
    }

    /**
     @MethodName : lastList
     @Date : 4:20 PM
     @Writer : heojaehong
     @Description : 최근 요청대기 리스트 조회
     */
    @GetMapping("/approvals/approvalLine/{memberCode}")
    public ResponseEntity<ResponseDTO> approvalLineList(@PathVariable int memberCode){

        log.info("[ApprovalController] GetMapping lastList start ");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회 성공",approvalService.approvalLineList(memberCode)));
    }

    /**
     @MethodName : pendingList
     @Date : 12:09 AM
     @Writer : heojaehong
     @Description : 미결재 리스트 조회
     */
    @GetMapping("/approvals/pending/{memberCode}")
    public ResponseEntity<ResponseDTO> pendingList(@PathVariable int memberCode){

        ApprovalDTO approvalDTO = new ApprovalDTO();
        log.info("[ApprovalController] GetMapping lastList: " + approvalDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회 성공",approvalService.pendingList(memberCode)));
    }

    /**
     @MethodName : completedList
     @Date : 12:09 AM
     @Writer : heojaehong
     @Description : 결재완료 리스트 조회
     */
    @GetMapping("/approvals/completed/{memberCode}")
    public ResponseEntity<ResponseDTO> completedList(@PathVariable int memberCode){

        ApprovalDTO approvalDTO = new ApprovalDTO();
        log.info("[ApprovalController] GetMapping lastList: " + approvalDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"조회 성공",approvalService.completedList(memberCode)));
    }

    /**
        @MethodName : detailApprove
    	@Date : 2:01 PM
    	@Writer : heojaehong
    	@Description : 결재 상세정보 조회
    */
    @GetMapping("/approvals/detail/{appCode}")
    public ResponseEntity<ResponseDTO> detailApprove(@PathVariable int appCode){

        log.info("[ApprovalController] PathVariable detailApprove: " + appCode);

        Object detailApproval = approvalService.detailApprove(appCode);
        Object approvalLine = approvalService.approvalLine(appCode);

        Map<String, Object> approvalInfo = new HashMap<>();
        approvalInfo.put("detailApproval", detailApproval);
        approvalInfo.put("approvalLine", approvalLine);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "상세정보 조회 성공", approvalInfo));
    }

    /**
        @MethodName : updateApproval
    	@Date : 2:10 PM
    	@Writer : heojaehong
    	@Description : 결재 수정
    */
    @PutMapping("approvals/{appCode}")
    public ResponseEntity<ResponseDTO> updateApproval(@PathVariable int appCode, @RequestBody ApprovalDTO approvalDTO){

        approvalDTO.setAppCode(appCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "수정 성공", approvalService.updateApproval(approvalDTO)));
    }

    /**
        @MethodName : deleteApproval
    	@Date : 2:10 PM
    	@Writer : heojaehong
    	@Description : 결재 삭제
    */
    @DeleteMapping("approvals/{appCode}")
    public ResponseEntity<ResponseDTO> deleteApproval(@PathVariable int appCode) {
        Map<String, Object> deleteMap = new HashMap<>();
        deleteMap.put("appCode", appCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "삭제 성공", approvalService.deleteApproval(deleteMap)));
    }

    /**
     @MethodName : approve
     @Date : 3:07 PM
     @Writer : heojaehong
     @Description : 결재서류 생성을 위한 메소드
     */
    @PostMapping("/approvals/draft/form/{docCode}")
    public ResponseEntity<ResponseDTO> approve(@PathVariable int docCode, @RequestBody @Valid ApprovalDTO approvalDTO) {
        log.info("[ApprovalController] postMapping docCode: ");
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDocCode(docCode);
        approvalDTO.setDocumentDTO(documentDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"등록 성공",approvalService.approve(approvalDTO)));
    }

    /**
     @MethodName : approve
     @Date : 3:07 PM
     @Writer : heojaehong
     @Description : 결재선 생성을 위한 메소드
     */
    @PostMapping("/approvals/draft/form/line/{appCode}")
    public ResponseEntity<ResponseDTO> setLineApproval(@PathVariable int appCode, @RequestBody List<ApprovalLineDTO> approvalLineDTOList) {
        log.info("[ApprovalController] postMapping setLineApproval ");

        for(ApprovalLineDTO approvalLineDTO : approvalLineDTOList) {
            approvalLineDTO.setAppCode(appCode);
        }

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"등록 성공",approvalService.setLineApproval(approvalLineDTOList)));
    }

    /**
     @MethodName : detailApprove
     @Date : 2:01 PM
     @Writer : heojaehong
     @Description : 결재선 수정
     */
    @PatchMapping("/approvals/draft/form/line/{appCode}")
    public ResponseEntity<ResponseDTO> updateApprovalLine(@PathVariable int appCode, @RequestBody ApprovalLineDTO approvalLineDTO){
        log.info("[ApprovalController] PathVariable updateApprovalLine: " + appCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "결재선 수정 성공", approvalService.patchLineApproval(approvalLineDTO)));
    }
}
