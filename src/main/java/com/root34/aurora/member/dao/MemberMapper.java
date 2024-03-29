package com.root34.aurora.member.dao;

import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.member.dto.*;
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

    MemberDTO selectMemberDetail(int memberCode);

    List<JobDTO> selectJob();

    List<DeptDTO> selectDept();
    List<TaskDTO> selectTask();

    List<TeamDTO> selectTeam();


    int selectMemberTotal();

    int memberModify(MemberDTO memberDTO);

    List<MemberDTO> selectMemberListAboutName(String search, SelectCriteria selectCriteria);

    List<MemberDTO> selectMemberListAboutEmail(String search, SelectCriteria selectCriteria);

    List<MemberDTO> selectMemberListAboutDept(String search, SelectCriteria selectCriteria);

    List<MemberDTO> selectMemberListAboutJob(String search, SelectCriteria selectCriteria);

    List<MemberDTO> selectMemberListAboutTask(String search, SelectCriteria selectCriteria);


}
