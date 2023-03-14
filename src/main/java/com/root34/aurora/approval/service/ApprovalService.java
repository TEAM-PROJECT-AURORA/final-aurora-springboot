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
        // 조회할 목록이 없을 때

        // 조회할 목록이 있을 때
        List<ApprovalDTO> approval = approvalMapper.lastList();

        return approval;
    }

    /**
        @MethodName : approve
    	@Date : 4:26 PM
    	@Writer : heojaehong
    	@Method Description : 전자결재 서류 등록
    */
    public ApprovalDTO approve(ApprovalDTO approvalDTO){

        int result = approvalMapper.insertApprove(approvalDTO);

        return approvalDTO;
    }

}
