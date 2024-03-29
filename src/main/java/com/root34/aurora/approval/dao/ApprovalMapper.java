package com.root34.aurora.approval.dao;

import com.root34.aurora.approval.dto.ApprovalDTO;
import com.root34.aurora.approval.dto.ApprovalLineDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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
    List<ApprovalDTO> lastList(int memberCode);
    /** 미결재 리스트 */
    List<ApprovalDTO> pendingList(int memberCode);
    /** 상세 정보 조회
     * @param appCode 결재코드*/
    ApprovalDTO detailApprove(int appCode);
    /** 결재완료 리스트 */
    List<ApprovalDTO> completedList(int memberCode);
    /** 결재 수정 
    * @param approvalDTO 결재 DTO*/
    int updateApproval(ApprovalDTO approvalDTO);
    /** 결재 삭제 
    * @param deleteMap 다중삭제를 위한 deleteMap*/
    int deleteApproval(Map<String, Object> deleteMap);
    /** 결재선 추가
     * @param approvalLineDTOList 결재선 DTO 리스트*/
    int setLineApproval(List<ApprovalLineDTO> approvalLineDTOList);
    /** 결재선 확인
     * @param appCode 결재문서 코드 */
    List<ApprovalLineDTO> approvalLine(int appCode);
    /** 결재선 확인
     * @param memberCode 맴버 코드 */
    List<ApprovalLineDTO> approvalLineList(int memberCode);
    /** 결재선 수정 확인
     * @param approvalLineDTO 결재선DTO */
    int patchLineApproval(ApprovalLineDTO approvalLineDTO);
}
