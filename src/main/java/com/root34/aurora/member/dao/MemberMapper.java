package com.root34.aurora.member.dao;

import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;


/**
 @ClassName : MemberMapper
 @Date : 23.03.20.
 @Writer : 정근호
 @Description : 회원 SQL을 호출하기 위한 인터페이스
 */
@Mapper
public interface MemberMapper {

    MemberDTO selectByEmail(String memberEmail);

    int insertMember(MemberDTO member);

    Optional<MemberDTO> findByMemberId(String memberId);

    MemberDTO selectByMemberId(String memberId);

    List<MemberDTO> memberList(SelectCriteria selectCriteria);

    MemberDTO memberDetail(MemberDTO memberCode);

    int selectMemberTotal();

    int memberModify(MemberDTO memberDTO);

    List<MemberDTO> memberListWithSearchValue(String search);

    List<MemberDTO> selectMemberListAboutEmail(String search);

    List<MemberDTO> selectMemberListAboutDept(String search);

    List<MemberDTO> selectMemberListAboutJob(String search);
//
//    List<MemberDTO> selectMemberListAboutEmail();
//
//    List<MemberDTO> selectMemberListAboutEmail();


}
