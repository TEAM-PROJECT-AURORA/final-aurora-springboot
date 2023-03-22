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

    @Transactional
    public MemberDTO signup(MemberDTO memberDto) {
        log.info("[AuthService] Signup Start ===================================");
        log.info("[AuthService] MemberRequestDto {}", memberDto);

        if(memberMapper.selectByEmail(memberDto.getMemberEmail()) != null) {
            log.info("[AuthService] 이메일이 중복됩니다.");
            throw new DuplicatedUsernameException("이메일이 중복됩니다.");
        }

        log.info("[AuthService] Member Signup Start ==============================");
        memberDto.setMemberPWD(passwordEncoder.encode(memberDto.getMemberPWD()));
        log.info("[AuthService] Member {}", memberDto);
        int result = memberMapper.insertMember(memberDto);
        log.info("[AuthService] Member Insert Result {}", result > 0 ? "회원 가입 성공" : "회원 가입 실패");

        log.info("[AuthService] Signup End ==============================");

        return memberDto;
    }

    @Transactional
    public TokenDTO login(MemberDTO memberDto) {
        log.info("[AuthService] Login Start ===================================");
        log.info("[AuthService] {}", memberDto);

        // 1. 아이디 조회
        MemberDTO member = memberMapper.findByMemberId(memberDto.getMemberId())
                .orElseThrow(() -> new LoginFailedException("잘못된 아이디 또는 비밀번호입니다"));

        // 2. 비밀번호 매칭
        if (!passwordEncoder.matches(memberDto.getMemberPWD(), member.getMemberPWD())) {
            log.info("[AuthService] Password Match Fail!!!!!!!!!!!!");
            throw new LoginFailedException("잘못된 아이디 또는 비밀번호입니다");
        }

        // 3. 토큰 발급
        TokenDTO tokenDto = tokenProvider.generateTokenDto(member);
        log.info("[AuthService] tokenDto {}", tokenDto);

        log.info("[AuthService] Login End ===================================");

        return tokenDto;
    }

}
