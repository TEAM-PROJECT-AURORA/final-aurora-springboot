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
 @ClassName : MemberController
 @Date : 23.03.20.
 @Writer : 정근호
 @Description : 인사관리 컨트롤러
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
     * @MethodName : memberList
     * @Date : 23.03.20.
     * @Writer : 정근호
     * @Description : 사원 리스트 출력 및 페이징 처리
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
     * @MethodName : memberDetail
     * @Date :23.03.20.
     * @Writer : 정근호
     * @Description : 사원 상세정보 조회
     */
    @GetMapping("/members/{memberCode}")
    public ResponseEntity<ResponseDTO> memberDetail(@PathVariable Integer memberCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK ,"조회 성공", memberService.memberDetail(memberCode)));
    }

    /**
     * @MethodName : memberModify
     * @Date : 23.03.20.
     * @Writer : 정근호
     * @Description : 사원 정보 수정
     */
    @PutMapping("/members/{memberCode}")
    public ResponseEntity<ResponseDTO> memberModify(@RequestBody MemberDTO memberDTO, @PathVariable int memberCode) {

        memberDTO.setMemberCode(memberCode);

        log.info("[memberService.memberDetail(memberCode))]" + memberDTO);
       return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.CREATED, "업데이트 성공", memberService.memberModify(memberDTO)));
    }

    /**
     * @MethodName : selectSearchListAboutName
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 이름으로 사원 검색
     */
    @GetMapping("/members/search")
    public ResponseEntity<ResponseDTO> selectSearchListAboutName(@RequestParam(name="name", defaultValue = "all") String search) {

        log.info("[memberService.selectSearchMemberListAboutName(search))]" + search);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공" , memberService.selectMemberListAboutName(search)));

    }

    /**
     * @MethodName : selectMemberListAboutEmail
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 이메일로 사원 검색
     */
    @GetMapping("/members/email")
    public ResponseEntity<ResponseDTO> selectMemberListAboutEmail(@RequestParam(name="email", defaultValue = "all") String search) {

        log.info("[memberService.selectMemberListAboutEmail(search))]" + search);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", memberService.selectMemberListAboutEmail(search)));
    }

    /**
     * @MethodName : selectMemberListAboutDept
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 부서별 사원 검색
     */
    @GetMapping("/members/dept")
    public ResponseEntity<ResponseDTO> selectMemberListAboutDept(@RequestParam(name="dept", defaultValue = "all") String search) {

        log.info("[memberService.selectMemberListAboutDept(search))]" + search);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", memberService.selectMemberListAboutDept(search)));

    }

    /**
     * @MethodName : selectMemberListAboutJob
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 직위별 사원 검색
     */
    @GetMapping("/members/job")
    public ResponseEntity<ResponseDTO> selectMemberListAboutJob(@RequestParam(name="job", defaultValue = "all") String search) {

        log.info("[memberService.selectMemberListAboutJob(search))]" + search);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", memberService.selectMemberListAboutJob(search)));

    }

    /**
     * @MethodName :selectMemberListAboutTask
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 직무별 사원 검색
     */
    @GetMapping("/members/task")
    public ResponseEntity<ResponseDTO> selectMemberListAboutTask(@RequestParam(name="task", defaultValue = "all") String search) {

        log.info("[memberService.selectMemberListAboutTask(search))]" + search);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", memberService.selectMemberListAboutTask(search)));
    }
}
