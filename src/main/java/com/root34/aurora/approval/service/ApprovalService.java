package com.root34.aurora.approval.service;

import com.root34.aurora.approval.dao.ApprovalMapper;
import com.root34.aurora.approval.dto.ApprovalDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

/**
 @FileName : ApprovalService
 @Date : 2:05 PM
 @작성자 : heojaehong
 @프로그램 설명 : 결재 서비스 클래스
 */
@Service
@Slf4j
public class ApprovalService {

    private final ApprovalMapper approvalMapper;

    public ApprovalService (ApprovalMapper approvalMapper) {
        this.approvalMapper = approvalMapper;
    }

    /**
     @MethodName : lastList
     @Date : 4:21 PM
     @Writer : heojaehong
     @Description : 최근 결재 리스트 조회
     */
    public List<ApprovalDTO> lastList() {

        try {
            List<ApprovalDTO> approvalDtoList = approvalMapper.lastList();
            if (approvalDtoList == null) {
                throw new Exception("목록이 없습니다.");
            }

            return approvalDtoList;
        } catch (Exception e) {
            log.error("[ApprovalService] lastList method error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당페이지가 존재하지 않습니다.", e);
        }
    }
    /**
        @MethodName : pendingList
    	@Date : 1:42 PM
    	@Writer : heojaehong
    	@Description : 미결재 리스트 메소드
    */
    public List<ApprovalDTO> pendingList() {

        try {
            List<ApprovalDTO> pendingList = approvalMapper.pendingList();
            if(pendingList == null){
                throw new Exception("조회할 목록이 없습니다.");
            }

            return pendingList;
        } catch (Exception e) {
            log.error("[ApprovalService] pendingList method error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당페이지가 존재하지 않습니다.", e);
        }
    }

    /**
        @MethodName : completedList
    	@Date : 1:43 PM
    	@Writer : heojaehong
    	@Description : 결재완료 리스트 메소드
    */
    public List<ApprovalDTO> completedList() {

        try {
            List<ApprovalDTO> pendingList = approvalMapper.completedList();
            if(pendingList == null){
                throw new Exception("조회할 목록이 없습니다.");
            }

            return pendingList;
        } catch (Exception e) {
            log.error("[ApprovalService] completedList method error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당페이지가 존재하지 않습니다.", e);
        }
    }

    /**
        @MethodName : approve
    	@Date : 4:26 PM
    	@Writer : heojaehong
    	@Description : 전자결재 서류 등록
    */
    public ApprovalDTO approve(ApprovalDTO approvalDTO) throws Exception { // 호출한 곳에서 예외처리

        try {
            int result = approvalMapper.insertApprove(approvalDTO);
            if(result == 0){
                throw new Exception("제출 실패!");
            }

            return approvalDTO;
        } catch (Exception e) {
            throw e;
        }
    }

/**
    @MethodName : detailApprove
	@Date : 2:48 PM
	@Writer : heojaehong
	@Description : 상세정보 조회 메서드
*/
    public ApprovalDTO detailApprove(@PathVariable int appCode) {

        log.info("[ApprovalService] detailApprove 실행 " );
        try {
            ApprovalDTO approvalDTO = approvalMapper.selectApprove(appCode);
            if(appCode < 0){
                throw new Exception("해당 문서가 존재하지 않습니다.");
            }

            return approvalDTO;
        }catch (Exception e){
            log.error("[ApprovalService] detailApprove method error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당페이지가 존재하지 않습니다.", e);
        }
    }

    /**
    @MethodName : updateApproval
	@Date : 11:45 AM
	@Writer : heojaehong
	@Description : 결재수정 메서드
*/
    public ApprovalDTO updateApproval(ApprovalDTO approvalDTO) {

        log.info("[ApprovalService] updateApproval 실행 " );
        try {
            approvalDTO = approvalMapper.updateApproval(approvalDTO);
            int appCode = approvalDTO.getAppCode();
            if(appCode < 0) {
                throw new Exception("해당 문서가 존재하지 않습니다.");
            }

            return approvalDTO;
        }
        catch (Exception e) {
            log.error("[ApprovalService] updateApproval method error" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 페이지가 존재하지 않습니다.", e);
        }
    }

    /**
    @MethodName : updateApproval
	@Date : 11:45 AM
	@Writer : heojaehong
	@Description : 결재삭제 메서드
    */
    public ApprovalDTO deleteApproval(ApprovalDTO approvalDTO) {

        log.info("[ApprovalService] deleteApproval 실행");
        
        try {
            approvalDTO = approvalMapper.deleteApproval(approvalDTO);
            int appCode = approvalDTO.getAppCode();

            if(appCode < 0) {
                throw new Exception("삭제 할 수 없습니다.");
            }
            return approvalDTO;
        }catch(Exception e) {
            log.error("[ApprovalService] deleteApproval method error" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 서류가 없습니다.");

        }
    }

}
