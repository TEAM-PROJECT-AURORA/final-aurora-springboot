package com.root34.aurora.member.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.member.dto.DeptDTO;
import com.root34.aurora.member.dto.JobDTO;
import com.root34.aurora.member.dto.MemberDTO;
import com.root34.aurora.member.service.AuthService;
import com.root34.aurora.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;
    private final MemberService memberService;

    public AuthController(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }


    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signup(@RequestBody MemberDTO memberDTO) {

        List<DeptDTO> selectDept = memberService.selectDept();
        List<JobDTO> selectJob = memberService.selectJob();
        MemberDTO member = authService.signup(memberDTO);
        Map map = new HashMap();
        map.put("member", member);
        map.put("selectJob", selectJob);
        map.put("selectDept", selectDept);


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "회원가입 성공", map));
    }
    /**
     * @MethodName :
     * @Date :
     * @Writer :
     * @Description :
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody MemberDTO memberDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "로그인 성공", authService.login(memberDTO)));
    }
}