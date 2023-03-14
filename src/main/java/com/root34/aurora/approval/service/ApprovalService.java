package com.root34.aurora.approval.service;

import com.root34.aurora.approval.dao.ApprovalMapper;
import com.root34.aurora.approval.dto.ApprovalDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public ApprovalDTO approve(ApprovalDTO approvalDTO){

        int result = approvalMapper.insertApprove(approvalDTO);

        return approvalDTO;
    }
}
