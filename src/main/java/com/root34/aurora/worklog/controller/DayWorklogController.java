package com.root34.aurora.worklog.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.worklog.dto.DayWorklogDTO;
import com.root34.aurora.worklog.service.DayWorklogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 @ClassName : DayWorklogController
 @Date : 23.03.23
 @Writer : 서지수
 @Description : 일일 업무일지 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class DayWorklogController {

    private final DayWorklogService dayWorklogService;

    public DayWorklogController(DayWorklogService dayWorklogService) { this.dayWorklogService = dayWorklogService; }

    /**
     @MethodName  : selectDayWorklogListWithPaging
     @Date : 23.03.23
     @Writer : 서지수
     @Description : 일일 업무일지 전체조회
     */
    @GetMapping("/worklogs/days/{memberCode}")
    public ResponseEntity<ResponseDTO> selectDayWorklogListWithPaging(@RequestParam(name="offset", defaultValue="1") String offset,
                                                                      @PathVariable int memberCode) {

        log.info("[DayWorklogController] selectDayWorklogListWithPaging : " + offset);
        int totalCount = dayWorklogService.selectDayWorklogTotal(memberCode); // selectDayWorklogTotal() ()안에 멤버코드를 줘서 그 멤버만 볼수 있게 해준다?
        int limit = 20;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
        Map map = new HashMap();
        map.put("selectCriteria", selectCriteria);
        map.put("memberCode", memberCode);
        log.info("[DayWorklogController] selectCriteria : " + selectCriteria);
        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setPageInfo(selectCriteria);
        responseDTOWithPaging.setData(dayWorklogService.selectDayWorklogListWithPaging(map));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDTOWithPaging));
    }

    /**
     @MethodName  : selectDayWorklogDetail
     @Date : 23.03.23
     @Writer : 서지수
     @Description : 일일 업무일지 상세조회
     */
    @GetMapping("/worklogs/days/detail/{dayWorklogCode}")
    public ResponseEntity<ResponseDTO> selectDayWorklogDetail(@PathVariable int dayWorklogCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일일 업무일지 상세 조회 성공",
                dayWorklogService.selectDayWorklog(dayWorklogCode)));
    }

    /**
     @MethodName  : selectMemberInfo
     @Date : 23.04.10
     @Writer : 서지수
     @Description : 멤버 정보 조회
     */
    @GetMapping("/worklogs/days/member/{memberCode}")
    public ResponseEntity<ResponseDTO> selectMemberInfo(@PathVariable int memberCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "멤버조회 조회 성공",
                dayWorklogService.selectMemberInfo(memberCode)));
    }

    /**
     @MethodName  : insertDayWorklog
     @Date : 23.03.23
     @Writer : 서지수
     @Description : 일일 업무일지 생성
     */
    @PostMapping(value = "/worklogs/days")
    public ResponseEntity<ResponseDTO> insertDayWorklog(@RequestBody DayWorklogDTO dayWorklogDTO) {

        log.info("[DayWorklogController] PostMapping dayWorklogDTO : " + dayWorklogDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일일 업무일지 등록 성공",
                dayWorklogService.insertDayWorklog(dayWorklogDTO)));
    }

    /**
     @MethodName  : updateDayWorklog
     @Date : 23.03.23
     @Writer : 서지수
     @Description : 일일 업무일지 수정
     */
    @PutMapping(value = "/worklogs/days")
    public ResponseEntity<ResponseDTO> updateDayWorklog(@RequestBody DayWorklogDTO dayWorklogDTO) {

        log.info("[DayWorklogController] PutMapping dayWorklogDTO : " + dayWorklogDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일일 업무일지 업데이트 성공",
                dayWorklogService.updateDayWorklog(dayWorklogDTO)));
    }

    /**
     @MethodName  : deleteDayWorklog
     @Date : 23.03.23
     @Writer : 서지수
     @Description : 일일 업무일지 삭제
     */
    @DeleteMapping(value = "/worklogs/days/{dayWorklogCode}")
    public ResponseEntity<ResponseDTO> deleteDayWorklog(@PathVariable int dayWorklogCode) {

        log.info("[DayWorklogController] DeleteMapping dayWorklogCode : " + dayWorklogCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일일 업무일지 삭제 성공",
                dayWorklogService.deleteDayWorklog(dayWorklogCode)));
    }
}
