package com.root34.aurora.approval.dto;

import com.root34.aurora.member.dto.MemberDTO;
import lombok.Data;

import java.sql.Date;

/**
 @FileName : ApprovalDTO
 @Date : 1:38 PM
 @작성자 : heojaehong
 @프로그램 설명 : 결재 DTO
 */
@Data
public class ApprovalDTO {

    /**@params : 결재코드*/
    private int appCode;
    /**@params : 문서DTO*/
    private DocumentDTO documentDto;
    /**@params : 사원DTO*/
    private MemberDTO memberDto;
    /**@params : 결재제목*/
    private String appTitle;
    /**@params : 결재내용*/
    private String appDescript;
    /**@params : 결재일*/
    private Date appDate;
    /**@params : 종료일*/
    private Date appEndDate;
    /**@params : 확인여부*/
    private String appOpen;

}
