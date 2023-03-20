package com.root34.aurora.member.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Data
public class MemberDTO implements UserDetails {

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
    private String Phone;




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
