package com.root34.aurora.member.controller;


import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.member.dto.*;
import com.root34.aurora.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * @MethodName : memberList
     * @Date : 23.03.20.
     * @Writer : 정근호
     * @Description : 사원 리스트 출력 및 페이징 처리
     */
    @GetMapping("/hrm")
    public ResponseEntity<ResponseDTO> memberList(@RequestParam(name="offset", defaultValue = "1") String offset) {

        log.info("[MemberController] selectMemberListWithPaging :" + offset);

        int totalCount = memberService.selectMemberTotal();
        int limit = 20;
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
    @GetMapping("/hrm/{memberCode}")
    public ResponseEntity<ResponseDTO> memberDetail(@PathVariable Integer memberCode) {



        List<DeptDTO> selectDept = memberService.selectDept();
        List<JobDTO> selectJob = memberService.selectJob();
        Map map = new HashMap();
        if(memberCode != null) {
            MemberDTO memberDTO = memberService.selectMemberDetail(memberCode);
            map.put("memberDTO", memberDTO);
        }

        map.put("selectJob", selectJob);
        map.put("selectDept", selectDept);
        log.info("[memberService(map))]" + map);


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK ,"조회 성공", map));
    }

    /**
     * @MethodName : memberModify
     * @Date : 23.03.20.
     * @Writer : 정근호
     * @Description : 사원 정보 수정
     */
    @PutMapping("/hrm/{memberCode}")
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
    @GetMapping("/hrm/search")
    public ResponseEntity<ResponseDTO> selectSearchListAboutName(@RequestParam(name="name", defaultValue = "all") String search,
                                                                 @RequestParam(name="offset", defaultValue = "1") String offset) {

        log.info("[MemberController] selectSearchListAboutName :" + offset);

        int totalCount = memberService.selectMemberTotal();
        int limit = 20;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount );

        log.info("[MemberController] selectCriteria : " + selectCriteria);
        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setPageInfo(selectCriteria);
        responseDTOWithPaging.setData(memberService.selectMemberListAboutName(search, selectCriteria));

        log.info("[memberService.selectSearchMemberListAboutName(search))]" + search);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공" ,responseDTOWithPaging ));


    }

    /**
     * @MethodName : selectMemberListAboutEmail
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 이메일로 사원 검색
     */
    @GetMapping("/hrm/email")
    public ResponseEntity<ResponseDTO> selectMemberListAboutEmail(@RequestParam(name="email", defaultValue = "all") String search,
                                                                  @RequestParam(name="offset", defaultValue = "1") String offset) {

        log.info("[MemberController] selectMemberListAboutEmail :" + offset);

        int totalCount = memberService.selectMemberTotal();
        int limit = 20;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount );

        log.info("[MemberController] selectCriteria : " + selectCriteria);
        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setPageInfo(selectCriteria);
        responseDTOWithPaging.setData(memberService.selectMemberListAboutEmail( search, selectCriteria));

        log.info("[memberService.selectMemberListAboutEmail(search))]" + search);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공",responseDTOWithPaging));
    }

    /**
     * @MethodName : selectMemberListAboutDept
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 부서별 사원 검색
     */
    @GetMapping("/hrm/dept")
    public ResponseEntity<ResponseDTO> selectMemberListAboutDept(@RequestParam(name="dept", defaultValue = "all") String search,
                                                                 @RequestParam(name="offset", defaultValue = "1") String offset) {

        log.info("[MemberController] selectMemberListAboutDept :" + offset);

        int totalCount = memberService.selectMemberTotal();
        int limit = 20;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount );

        log.info("[MemberController] selectCriteria : " + selectCriteria);
        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setPageInfo(selectCriteria);
        responseDTOWithPaging.setData(memberService.selectMemberListAboutDept( search, selectCriteria));

        log.info("[memberService.selectMemberListAboutDept(search))]" + search);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공",responseDTOWithPaging));

    }

    /**
     * @MethodName : selectMemberListAboutJob
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 직위별 사원 검색
     */
    @GetMapping("/hrm/job")
    public ResponseEntity<ResponseDTO> selectMemberListAboutJob(@RequestParam(name="job", defaultValue = "all") String search,
                                                                @RequestParam(name="offset", defaultValue = "1") String offset) {

        log.info("[MemberController] selectMemberListAboutJob :" + offset);

        int totalCount = memberService.selectMemberTotal();
        int limit = 20;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount );

        log.info("[MemberController] selectCriteria : " + selectCriteria);
        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setPageInfo(selectCriteria);
        responseDTOWithPaging.setData(memberService.selectMemberListAboutJob( search, selectCriteria));

        log.info("[memberService.selectMemberListAboutJob(search))]" + search);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDTOWithPaging));

    }

    /**
     * @MethodName :selectMemberListAboutTask
     * @Date : 23.03.22.
     * @Writer : 정근호
     * @Description : 직무별 사원 검색
     */
    @GetMapping("/hrm/task")
    public ResponseEntity<ResponseDTO> selectMemberListAboutTask(@RequestParam(name="task", defaultValue = "all") String search,
                                                                 @RequestParam(name="offset", defaultValue = "1") String offset) {

        log.info("[MemberController] selectMemberListAboutTask :" + offset);

        int totalCount = memberService.selectMemberTotal();
        int limit = 20;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount );

        log.info("[MemberController] selectCriteria : " + selectCriteria);
        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setPageInfo(selectCriteria);
        responseDTOWithPaging.setData(memberService.selectMemberListAboutTask( search, selectCriteria));

        log.info("[memberService.selectMemberListAboutTask(search))]" + search);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공",responseDTOWithPaging));
    }

    /**
    	@MethodName : selectCode
    	@Date : 2023-04-03
    	@Writer : 정근호
    	@Description : 회원등록시 보이는 코드와 이름 조회
    */
    @GetMapping("/hrm/code")
    public ResponseEntity<ResponseDTO> selectCode() {

        List<DeptDTO> selectDept = memberService.selectDept();
        List<JobDTO> selectJob = memberService.selectJob();
        List<TaskDTO> selectTask = memberService.selectTask();
        List<TeamDTO> selectTeam = memberService.selectTeam();

        Map map = new HashMap();
        map.put("selectJob", selectJob);
        map.put("selectDept", selectDept);
        map.put("selectTask", selectTask);
        map.put("selectTeam", selectTeam);
        log.info("[memberService(map))]" + map);


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK ,"조회 성공", map));
    }


}

