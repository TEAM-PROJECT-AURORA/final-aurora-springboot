package com.root34.aurora.approval.service;

import com.root34.aurora.approval.dao.ApprovalMapper;
import com.root34.aurora.approval.dto.ApprovalDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


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
     @Method Description : 최근 결재 리스트 조회
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

            return null;
        }
    }

    public List<ApprovalDTO> pendingList() {

        try {
            List<ApprovalDTO> pendingList = approvalMapper.pendingList();
            if(pendingList == null){
                throw new Exception("조회할 목록이 없습니다.");
            }

            return pendingList;
        } catch (Exception e) {
            log.error("[ApprovalService] pendingList method error : " + e.getMessage());

            return null;
        }
    }

    public List<ApprovalDTO> completedList() {

        return null;
    }

    /**
        @MethodName : approve
    	@Date : 4:26 PM
    	@Writer : heojaehong
    	@Method Description : 전자결재 서류 등록
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


}
