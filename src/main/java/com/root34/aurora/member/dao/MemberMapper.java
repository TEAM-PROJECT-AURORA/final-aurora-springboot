package com.root34.aurora.member.dao;

import com.root34.aurora.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;


@Mapper
public interface MemberMapper {

    MemberDTO selectByEmail(String email);

    int insertMember(MemberDTO member);

    Optional<MemberDTO> findByMemberId(String memberId);

    MemberDTO selectByMemberId(String memberId);
}
