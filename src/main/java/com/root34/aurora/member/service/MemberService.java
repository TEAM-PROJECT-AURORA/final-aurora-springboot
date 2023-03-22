package com.root34.aurora.member.service;

import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.member.dao.MemberMapper;
import com.root34.aurora.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 @ClassName : MemberMapper
 @Date : 23.03.20.
 @Writer : 정근호
 @Description : 회원 SQL을 호출하기 위한 인터페이스
 */
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

    /**
     * @MethodName : selectMemberTotal
     * @Date : 23.03.20.
     * @Writer : 정근호
     * @Method Description :페이징 처리를 위한 매소드
     */
    public int selectMemberTotal() {

        log.info("[MemberService] selectMemberTotal Start ====================");
        int result = memberMapper.selectMemberTotal();

        log.info("[MemberService] selectMemberTotal End ====================");
        return result;
    }

    /**
     * @MethodName : memberList
     * @Date : 23.03.20.
     * @Writer : 정근호
     * @Method Description :사원 전체 조회를 위한 매소드
     */
    public Object memberList(SelectCriteria selectCriteria){

        log.info("[MemberService] selectMemberListWithPaging Start =========================");
        List<MemberDTO> memberList = memberMapper.memberList(selectCriteria);

        log.info("[MemberService] selectMemberListWithPaging End =============================");
        return memberList;
    }

    /**
     * @MethodName : memberDetail
     * @Date : 23.03.20.
     * @Writer : 정근호
     * @Method Description : 사원의 상세 조회를 위한 매소드
     */
    public MemberDTO memberDetail(Integer memberCode) {

        log.info("[MemberService] memberDetail Start =========================");
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberCode(memberCode);

        MemberDTO result = memberMapper.memberDetail(memberDTO);

        log.info("[MemberService] memberDetail End =============================");
        return result;
    }

    /**
     * @MethodName : memberModify
     * @Date : 23.03.20.
     * @Writer : 정근호
     * @Method Description :사원 정보 수정을 위한 매소드
     */
    @Transactional
    public String memberModify(MemberDTO memberDTO) {

        // MemberDTO modifyDTO = new Member(멤버 맵퍼.조회(멤버DTO.사원번호))
        // modifymember.set memberDTO !null
        log.info("[MemberService] memberModify Start =========================");
        memberMapper.memberModify(memberDTO);
        int result = 0;

        result = memberMapper.memberModify(memberDTO);

        log.info("[MemberService] memberModify End =============================");
        return  (result > 0 ) ? " 수정 성공" : "수정 실패 ";
    }

    public List<MemberDTO> selectSearchMemberList(String search) {

        log.info("[MemberService] selectSearchMemberList Start =========================");
        log.info("[MemberService] searchValue : " + search);
        List<MemberDTO> memberListWithSearchValues = memberMapper.memberListWithSearchValue(search);
        log.info("[MemberService] selectSearchMemberList : " + memberListWithSearchValues);

        log.info("[MemberService] selectSearchMemberList End =============================");

        return memberListWithSearchValues;
    }

    public List<MemberDTO> selectMemberListAboutEmail(String search) {

        log.info("[MemberService] selectMemberListAboutEmail Start =========================");
        log.info("[MemberService] searchValue : " + search);
        List<MemberDTO> MemberListAboutEmail = memberMapper.selectMemberListAboutEmail(search);
        log.info("[MemberService] selectMemberListAboutEmail : " + MemberListAboutEmail);

        log.info("[MemberService] selectMemberListAboutEmail End =============================");

        return MemberListAboutEmail;

    }

    public List<MemberDTO> selectMemberListAboutDept(String search) {

        log.info("[MemberService] selectMemberListAboutDept Start =========================");
        log.info("[MemberService] searchValue : " + search);
        List<MemberDTO> MemberListAboutDept = memberMapper.selectMemberListAboutDept(search);
        log.info("[MemberService] selectMemberListAboutDept : " + MemberListAboutDept);

        log.info("[MemberService] selectMemberListAboutDept End =============================");

        return MemberListAboutDept;
    }
}