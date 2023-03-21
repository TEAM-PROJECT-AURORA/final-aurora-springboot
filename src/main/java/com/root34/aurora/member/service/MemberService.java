package com.root34.aurora.member.service;

import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.member.dao.MemberMapper;
import com.root34.aurora.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberDTO selectMyInfo(String memberId) {

        log.info("[MemberService] getMyInfo Start ==============================");
        MemberDTO member = memberMapper.selectByMemberId(memberId);
        log.info("[MemberService] {}", member);
        log.info("[MemberService] getMyInfo End ==============================");

        return member;
    }

    public int selectMemberTotal() {

        log.info("[MemberService] selectMemberTotal Start ====================");
        int result = memberMapper.selectMemberTotal();

        log.info("[MemberService] selectMemberTotal End ====================");
        return result;
    }

    public Object memberList(SelectCriteria selectCriteria){

        log.info("[MemberService] selectMemberListWithPaging Start =========================");
        List<MemberDTO> memberList = memberMapper.memberList(selectCriteria);

        log.info("MemberService selectMemberListWithPaging End =============================");
        return memberList;
    }

    public MemberDTO memberDetail(Integer memberCode) {

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberCode(memberCode);
        MemberDTO result = memberMapper.memberDetail(memberDTO);
        return result;
    }
    @Transactional
    public String memberModify(MemberDTO memberDTO) {

        // MemberDTO modifyDTO = new Member(멤버 맵퍼.조회(멤버DTO.사원번호))
        // modifymember.set memberDTO !null

        memberMapper.memberModify(memberDTO);
        int result = 0;

        result = memberMapper.memberModify(memberDTO);

        return  (result > 0 ) ? " 수정 성공" : "수정 실패 ";
    }

}