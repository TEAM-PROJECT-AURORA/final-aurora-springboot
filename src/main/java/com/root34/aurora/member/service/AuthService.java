package com.root34.aurora.member.service;

import com.root34.aurora.exception.DuplicatedUsernameException;
import com.root34.aurora.exception.LoginFailedException;
import com.root34.aurora.jwt.TokenProvider;
import com.root34.aurora.member.dao.MemberMapper;
import com.root34.aurora.member.dto.MemberDTO;
import com.root34.aurora.member.dto.TokenDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AuthService(MemberMapper memberMapper, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    /**
     * @MethodName : signup
     * @Date : 23.03.20.
     * @Writer : 정근호
     * @Description :사원 등록을 위한 매소드
     */
    @Transactional
    public MemberDTO signup(MemberDTO memberDTO) {
        log.info("[AuthService] Signup Start ===================================");
        log.info("[AuthService] MemberRequestDto {}", memberDTO);

        if(memberMapper.selectByEmail(memberDTO.getMemberEmail()) != null) {
            log.info("[AuthService] 이메일이 중복됩니다.");
            throw new DuplicatedUsernameException("이메일이 중복됩니다.");
        }

        log.info("[AuthService] Member Signup Start ==============================");
        memberDTO.setMemberPWD(passwordEncoder.encode(memberDTO.getMemberPWD()));
        log.info("[AuthService] Member {}", memberDTO);
        int result = memberMapper.insertMember(memberDTO);
        log.info("[AuthService] Member Insert Result {}", result > 0 ? "회원 가입 성공" : "회원 가입 실패");

        log.info("[AuthService] Signup End ==============================");

        return memberDTO;
    }

    @Transactional
    public TokenDTO login(MemberDTO memberDTO) {
        log.info("[AuthService] Login Start ===================================");
        log.info("[AuthService] {}", memberDTO);

        // 1. 아이디 조회
        MemberDTO member = memberMapper.findByMemberId(memberDTO.getMemberId())
                .orElseThrow(() -> new LoginFailedException("잘못된 아이디 또는 비밀번호입니다"));

        // 2. 비밀번호 매칭
        if (!passwordEncoder.matches(memberDTO.getMemberPWD(), member.getMemberPWD())) {
            log.info("[AuthService] Password Match Fail!!!!!!!!!!!!");
            throw new LoginFailedException("잘못된 아이디 또는 비밀번호입니다");
        }

        // 3. 토큰 발급
        TokenDTO tokenDTO = tokenProvider.generateTokenDto(member);
        log.info("[AuthService] tokenDto {}", tokenDTO);

        log.info("[AuthService] Login End ===================================");

        return tokenDTO;
    }

}