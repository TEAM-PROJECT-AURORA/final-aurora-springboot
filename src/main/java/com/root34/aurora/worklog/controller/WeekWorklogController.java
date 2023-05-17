package com.root34.aurora.worklog.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.worklog.dto.WeekWorklogDTO;
import com.root34.aurora.worklog.service.WeekWorklogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 @ClassName : WeekWorklogController
 @Date : 23.03.25
 @Writer : 서지수
 @Description : 주간 업무일지 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class WeekWorklogController {

    private final WeekWorklogService weekWorklogService;

    public WeekWorklogController(WeekWorklogService weekWorklogService) { this.weekWorklogService = weekWorklogService; }

    /**
     @MethodName  : selectWeekWorklogListWithPaging
     @Date : 23.03.25
     @Writer : 서지수
     @Description : 나의 주간 업무일지 전체조회
     */
    @GetMapping("/worklogs/weeks/{memberCode}")
    public ResponseEntity<ResponseDTO> selectWeekWorklogListWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset,
                                                                       @PathVariable int memberCode) {

        log.info("[WeekWorklogController] selectWeekWorklogListWithPaging : " + offset);
        int totalCount = weekWorklogService.selectWeekWorklogTotal(memberCode);
        int limit = 20;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
        Map map = new HashMap();
        map.put("selectCriteria", selectCriteria);
        map.put("memberCode", memberCode);
        log.info("[WeekWorklogController] selectCriteria : " + selectCriteria);
        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setPageInfo(selectCriteria);
        responseDTOWithPaging.setData(weekWorklogService.selectWeekWorklogListWithPaging(map));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDTOWithPaging));
    }

    /**
     @MethodName  : selectWeekWorklogDetail
     @Date : 23.03.25
     @Writer : 서지수
     @Description : 주간 업무일지 상세조회
     */
    @GetMapping("/worklogs/weeks/detail/{weekWorklogCode}")
    public ResponseEntity<ResponseDTO> selectWeekWorklogDetail(@PathVariable int weekWorklogCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "주간 업무일지 상세 조회 성공",
                weekWorklogService.selectWeekWorklog(weekWorklogCode)));
    }

    /**
     @MethodName  : insertWeekWorklog
     @Date : 23.03.25
     @Writer : 서지수
     @Description : 주간 업무일지 생성
     */
    @PostMapping(value = "/worklogs/weeks")
    public ResponseEntity<ResponseDTO> insertWeekWorklog(@RequestBody WeekWorklogDTO weekWorklogDTO) {

        log.info("[WeekWorklogController] PostMapping weekWorklogDTO : " + weekWorklogDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "주간 업무일지 생성 성공",
                weekWorklogService.insertWeekWorklog(weekWorklogDTO)));
    }

    /**
     @MethodName  : updateWeekWorklog
     @Date : 23.03.25
     @Writer : 서지수
     @Description : 주간 업무일지 수정
     */
    @PutMapping(value = "/worklogs/weeks")
    public ResponseEntity<ResponseDTO> updateWeekWorklog(@RequestBody WeekWorklogDTO weekWorklogDTO) {

        log.info("[WeekWorklogController] PutMapping weekWorklogDTO : " + weekWorklogDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "주간 업무일지 수정 성공",
                weekWorklogService.updateWeekWorklog(weekWorklogDTO)));
    }

    /**
     @MethodName  : deleteWeekWorklog
     @Date : 23.03.25
     @Writer : 서지수
     @Description : 주간 업무일지 삭제
     */
    @DeleteMapping(value = "/worklogs/weeks/{weekWorklogCode}")
    public ResponseEntity<ResponseDTO> deleteWeekWorklog(@PathVariable int weekWorklogCode) {

        log.info("[WeekWorklogController] DeleteMapping  weekWorklogCode: " + weekWorklogCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "주간 업무일지 삭제 성공",
                weekWorklogService.deleteWeekWorklog(weekWorklogCode)));
    }
}
