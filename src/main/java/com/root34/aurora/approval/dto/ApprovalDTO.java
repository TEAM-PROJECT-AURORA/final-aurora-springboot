package com.root34.aurora.approval.dto;

import com.root34.aurora.member.dto.MemberDTO;
import lombok.Data;

import java.sql.Date;
import java.util.List;

/**
 @FileName : ApprovalDTO
 @Date : 1:38 PM
 @작성자 : heojaehong
 @프로그램 설명 : 결재 DTO
 */
@Data
public class ApprovalDTO {

    /**@Variable : 결재코드*/
    private int appCode;
    /**@Variable : 결재제목*/
    private String appTitle;
    /**@Variable : 결재내용*/
    private String appDescript;
    /**@Variable : 결재일*/
    private Date appDate;
    /**@Variable : 시작일*/
    private Date appStartDate;
    /**@Variable : 종료일*/
    private Date appEndDate;
    private String appStatus;
    /**@Variable : 확인여부
     * @주의 : 읽지 않은 상태는 n, 읽은 상태는 y*/
    private String appOpen;
    /**@Variable : 문서DTO*/
    private DocumentDTO documentDTO;
    /**@Variable : 사원DTO*/
    private MemberDTO memberDTO;

}
