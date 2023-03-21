package com.root34.aurora.member.controller;


import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.member.dto.MemberDTO;
import com.root34.aurora.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 @ClassName : MemberMapper
 @Date : 23.03.20.
 @Writer : 정근호
 @Description : 회원 SQL을 호출하기 위한 인터페이스
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

//    @GetMapping("/members/{memberId}")
//    public ResponseEntity<ResponseDTO> selectMyMemberInfo(@PathVariable String memberId) {
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", memberService.selectMyInfo(memberId)));
//    }


    /**
     * @MethodName :
     * @Date :
     * @Writer :
     * @Method Description :
     */
    @GetMapping("/members")
    public ResponseEntity<ResponseDTO> memberList(@RequestParam(name="offset", defaultValue = "1") String offset) {

        log.info("[MemberController] selectMemberListWithPaging :" + offset);

        int totalCount = memberService.selectMemberTotal();
        int limit = 10;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount );

        log.info("[MemberController] selectCriteria : " + selectCriteria);

        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setPageInfo(selectCriteria);
        responseDTOWithPaging.setData(memberService.memberList(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDTOWithPaging));

    }
    /**
     * @MethodName :
     * @Date :
     * @Writer :
     * @Method Description :
     */
    @GetMapping("/members/{memberCode}")
    public ResponseEntity<ResponseDTO> memberDetail(@PathVariable Integer memberCode) {


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK ,"조회 성공", memberService.memberDetail(memberCode)));




    }
    /**
     * @MethodName :
     * @Date :
     * @Writer :
     * @Method Description :
     */
    @PutMapping("/members/{memberCode}")
    public ResponseEntity<ResponseDTO> memberModify(@RequestBody MemberDTO memberDTO, @PathVariable int memberCode) {

        memberDTO.setMemberCode(memberCode);

        log.info("[memberService.memberDetail(memberCode))]" + memberDTO);
       return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "업데이트 성공", memberService.memberModify(memberDTO)));
    }

}
