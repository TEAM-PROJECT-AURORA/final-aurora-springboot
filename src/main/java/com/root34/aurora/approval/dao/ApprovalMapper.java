package com.root34.aurora.approval.dao;

import com.root34.aurora.approval.dto.ApprovalDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 @FileName : ApprovalMapper
 @Date : 3:08 PM
 @작성자 : heojaehong
 @프로그램 설명 : 결재 매퍼 클래스
 */
@Mapper

public interface ApprovalMapper {
    /** 결재 생성
     * @param approvalDTO 결재 DTO*/
    int insertApprove(ApprovalDTO approvalDTO);
    /** 최근리스트, 첫화면에 보여지는 데이터 */
    List<ApprovalDTO> lastList();
    /** 미결재 리스트 */
    List<ApprovalDTO> pendingList();
    /** 상세 정보 조회
     * @param appCode 결재코드*/
    ApprovalDTO selectApprove(int appCode);
    /** 결재완료 리스트 */
    List<ApprovalDTO> completedList();
}
