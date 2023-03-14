package com.root34.aurora.approval.dao;

import com.root34.aurora.approval.dto.ApprovalDTO;
import org.apache.ibatis.annotations.Mapper;
/**
 @FileName : ApprovalMapper
 @Date : 3:08 PM
 @작성자 : heojaehong
 @프로그램 설명 : 결재 매퍼 클래스
 */
@Mapper

public interface ApprovalMapper {

    int insertApprove(ApprovalDTO approvalDTO);
}
