package com.root34.aurora.member.service;

import com.root34.aurora.member.dao.MemberMapper;
import com.root34.aurora.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

//    public int selectBoardTotal() {
//
//        log.info("[MemberService] selectMemberTotal Start ====================");
//
//    }

}