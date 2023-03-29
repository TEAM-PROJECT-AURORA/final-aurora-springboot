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
 @ClassName : MemberService
 @Date : 23.03.20.
 @Writer : 정근호
 @Description :
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
     * @Description :페이징 처리를 위한 매소드
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
     * @Description :사원 전체 조회를 위한 매소드
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
     * @Description : 사원의 상세 조회를 위한 매소드
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
     * @Description :사원 정보 수정을 위한 매소드
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

    /**
     * @MethodName : selectMemberListAboutName
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 이름으로 사원 검색
     */
    public List<MemberDTO> selectMemberListAboutName(String search, SelectCriteria selectCriteria) {

        log.info("[MemberService] selectSearchMemberList Start =========================");
        log.info("[MemberService] searchValue : " + search);
        List<MemberDTO> selectMemberListAboutName = memberMapper.selectMemberListAboutName(search, selectCriteria);
        log.info("[MemberService] selectSearchMemberList : " + selectMemberListAboutName);

        log.info("[MemberService] selectSearchMemberList End =============================");

        return selectMemberListAboutName;
    }

    /**
     * @MethodName : selectMemberListAboutEmail
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 이메일로 사원 검색
     */
    public List<MemberDTO> selectMemberListAboutEmail(String search, SelectCriteria selectCriteria) {

        log.info("[MemberService] selectMemberListAboutEmail Start =========================");
        log.info("[MemberService] searchValue : " + search);
        List<MemberDTO> memberListAboutEmail = memberMapper.selectMemberListAboutEmail(search, selectCriteria);
        log.info("[MemberService] selectMemberListAboutEmail : " + memberListAboutEmail);

        log.info("[MemberService] selectMemberListAboutEmail End =============================");

        return memberListAboutEmail;

    }

    /**
     * @MethodName : selectMemberListAboutDept
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 부서별 사원 검색
     */
    public List<MemberDTO> selectMemberListAboutDept(String search, SelectCriteria selectCriteria) {

        log.info("[MemberService] selectMemberListAboutDept Start =========================");
        log.info("[MemberService] searchValue : " + search);
        List<MemberDTO> memberListAboutDept = memberMapper.selectMemberListAboutDept(search, selectCriteria);
        log.info("[MemberService] selectMemberListAboutDept : " + memberListAboutDept);

        log.info("[MemberService] selectMemberListAboutDept End =============================");

        return memberListAboutDept;
    }

    /**
     * @MethodName : selectMemberListAboutJob
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 직위별 사원 검색
     */
    public List<MemberDTO> selectMemberListAboutJob(String search, SelectCriteria selectCriteria) {

        log.info("[MemberService] selectMemberListAboutJob Start =========================");
        log.info("[MemberService] searchValue : " + search);
        List<MemberDTO> memberListAboutJob = memberMapper.selectMemberListAboutJob(search, selectCriteria);
        log.info("[MemberService] selectMemberListAboutJob : " + memberListAboutJob);

        log.info("[MemberService] selectMemberListAboutJob End =============================");

        return memberListAboutJob;
    }

    /**
     * @MethodName : selectMemberListAboutTask
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 직무별 사원 검색
     */
    public List<MemberDTO> selectMemberListAboutTask(String search, SelectCriteria selectCriteria) {

        log.info("[MemberService] selectMemberListAboutTask Start =========================");
        log.info("[MemberService] searchValue : " + search);
        List<MemberDTO> memberListAboutTask = memberMapper.selectMemberListAboutTask(search, selectCriteria);
        log.info("[MemberService] selectMemberListAboutTask : " + memberListAboutTask);

        log.info("[MemberService] selectMemberListAboutTask End =============================");

        return memberListAboutTask;
    }
}