package com.root34.aurora.member.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 @ClassName : MemberDTO
 @Date : 23.03.19.
 @Writer : 정근호
 @Description : 사원 변수 선언
 */
@Data
public class MemberDTO implements UserDetails {

    /**
     * @param memberCode     사원코드
     * @param deptCode       부서코드
     * @param jobCode        직윝코드
     * @param taskCode       직무코드
     * @param memberName     이름
     * @param memberId       아이디
     * @param memberPWD      비밀번호
     * @param memberEmail    이메일
     * @param address        주소
     * @param gender         성별
     * @param memberRole     권한
     * @param memberHireDate 입사일
     * @param memberEndDate  퇴사일
     * @param introduction   자기소개
     * @param significant    특이사항
     * @param status         재직상태
     * @param birthDay       생년월일
     * @param team           소속팀
     * @param fileCode       사진파일코드
     * @param phone          핸드폰
     */
    private int memberCode;
    private String deptCode;
    private String jobCode;
    private String taskCode;
    private String memberName;
    private String memberId;
    private String memberPWD;
    private String memberEmail;
    private String address;
    private String gender;
    private String memberRole;
    private String memberHireDate;
    private String memberEndDate;
    private String introduction;
    private String significant;
    private String status;
    private Date birthDay;
    private String team;
    private String fileCode;
    private String phone;
    private String deptName;
    private String jobName;

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
