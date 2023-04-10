package com.root34.aurora.mail.dao;

import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.mail.dto.MailDTO;
import com.root34.aurora.mail.dto.TagDTO;
import com.root34.aurora.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 @ClassName : MailMapper
 @Date : 23.03.20.
 @Writer : 김수용
 @Description : 메일 SQL을 호출하기 위한 인터페이스
 */
@Mapper
public interface MailMapper {

    MemberDTO selectMemberDetail(int memberCode); // 멤버 상세 정보 조회

    int saveMail(MailDTO mailDTO); // 메일 저장(발송)

    int getMailCount(HashMap<String, Object> searchConditions); // 조건별 메일 갯수 조회

    List<MailDTO> selectMailListByConditions(SelectCriteria selectCriteria); // 조건별 메일 목록 조회

    int updateImportantStatus(MailDTO mailDTO); // 메일 중요 상태 수정 

    int updateDeleteStatus(MailDTO mailDTO); // 메일 삭제 상태 수정

    int registerTag(TagDTO tagDTO); // 태그 생성

    List<TagDTO> selectTagListByMemberCode(int memberCode); // 태그 목록 조회

    int updateTag(TagDTO tagDTO); // 태그 수정

    int deleteTag(long tagCode); // 태그 삭제

    int registerBlackList(HashMap<String, Object> parameters); // 블랙리스트 등록

    List<String> selectBlackListByMemberCode(int memberCode); // 블랙리스트 조회

    int deleteBlockedSenderEmail(HashMap<String, Object> parameters); // 블랙리스트에서 삭제
}