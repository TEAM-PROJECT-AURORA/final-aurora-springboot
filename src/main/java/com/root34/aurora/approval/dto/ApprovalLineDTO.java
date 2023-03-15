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

    /**@params : 결재선 코드*/
    private int appLineCode;
    /**@params : 결재코드*/
    private ApprovalDTO appCode;
    /**@params : 사원코드*/
    private MemberDTO MemberDto;
    /**@params : 결재 상태*/
    private String appStatus;

}