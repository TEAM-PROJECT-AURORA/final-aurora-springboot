package com.root34.aurora.approval.dto;

import com.root34.aurora.member.dto.MemberDTO;
import lombok.Data;
/**
 @FileName : ApprovalLineDTO
 @Date : 1:46 PM
 @작성자 : heojaehong
 @프로그램 설명 : 결재선 DTO
 */
@Data
public class ApprovalLineDTO {

    /**@Variable : 결재선 코드*/
    private int appLineCode;

    /**@Variable : 결재 상태
     * @주의 : 결재대기 n, 결재완료 y*/
    private String appStatus;
    /**@Variable : 결재코드*/
    private int appCode;
    /**@Variable : 사원코드*/
    private MemberDTO memberDTO;
    /**@Variable : 결재 DTO*/
    private ApprovalDTO approvalDTO;
    /**@Variable : 문서 DTO*/
    private DocumentDTO documentDTO;

}
