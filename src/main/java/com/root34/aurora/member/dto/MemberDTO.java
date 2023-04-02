package com.root34.aurora.member.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 @ClassName : MemberDTO
 @Date : 23.03.19.
 @Writer : 정근호
 @Description : 사원 변수 선언
 */
@Data
public class MemberDTO implements UserDetails {

    /**
     * @variable memberCode     사원코드
     * /
     *
     * @variable deptCode       부서코드
     * @variable jobCode        직윝코드
     * @variable taskCode       직무코드
     * @variable memberName     이름
     * @variable memberId       아이디
     * @variable memberPWD      비밀번호
     * @variable memberEmail    이메일
     * @variable address        주소
     * @variable gender         성별
     * @variable memberRole     권한
     * @variable memberHireDate 입사일
     * @variable memberEndDate  퇴사일
     * @variable introduction   자기소개
     * @variable significant    특이사항
     * @variable status         재직상태
     * @variable birthDay       생년월일
     * @variable team           소속팀
     * @variable fileCode       사진파일코드
     * @variable phone          핸드폰
     */
    /**
     * @variable memberCode 사원코드
     **/
    private int memberCode;
    /**
     * @variable jobCode 직윝코드
     **/
    private String deptCode;
    /**
     * @variable jobCode 직윝코드
     **/
    private String jobCode;
    /**
     * @variable taskCode 직무코드
     **/
    private String taskCode;
    /**
     * @variable memberName 이름
     **/
    private String memberName;
    /**
     * @variable memberId 아이디
     **/
    private String memberId;
    /**
     * @variable memberPWD 비밀번호
     **/
    private String memberPWD;
    /**
     * @variable memberEmail 이메일
     **/
    private String memberEmail;
    /**
     * @variable address 주소
     **/
    private String address;
    /**
     * @variable gender 성별
     **/
    private String gender;
    /**
     * @variable memberRole 권한
     **/
    private String memberRole;
    /**
     * @variable memberHireDate 입사일
     **/
    private String memberHireDate;
    /**
     * @variable memberEndDate 퇴사일
     **/
    private LocalDateTime memberEndDate;
    /**
     * @variable introduction 자기소개
     **/
    private String introduction;
    /**
     * @variable significant 특이사항
     **/
    private String significant;
    /**
     * @variable status 재직상태
     **/
    private String status;
    /**
     * @variable birthDay 생년월일
     **/
    private String birthDay;
    /**
     * @variable team 소속팀
     **/
    private String team;
    /**
     * @variable fileCode 사진파일코드
     **/
    private String fileCode;
    /**
     * @variable phone 핸드폰
     **/
    private String phone;
    /**
     * @variable deptName 부서명
     **/
    private String deptName;
    /**
     * @variable jobName 직급명
     **/
    private String jobName;
    /**
     * @variable taskName 직무명
     **/
    private String taskName;


    // 이하 코드는 security 를 위한 용도
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.memberPWD;
    }

    @Override
    public String getUsername() {
        return this.memberId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
