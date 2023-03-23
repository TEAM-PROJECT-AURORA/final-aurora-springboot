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

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class DayWorklogController {

    private final DayWorklogService dayWorklogService;

    public DayWorklogController(DayWorklogService dayWorklogService) { this.dayWorklogService = dayWorklogService; }

    @GetMapping("/worklogs/days")
    public ResponseEntity<ResponseDTO> selectDayWorklogListWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset) {

        log.info("[DayWorklogController] selectDayWorklogListWithPaging : " + offset);
        int totalCount = dayWorklogService.selectDayWorklogTotal();
        int limit = 20;
        int buttonAmout = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmout);
        log.info("[DayWorklogController] selectCriteria : " + selectCriteria);
        ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
        responseDTOWithPaging.setPageInfo(selectCriteria);
        responseDTOWithPaging.setData(dayWorklogService.selectDayWorklogListWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDTOWithPaging));
    }

    @GetMapping("/worklogs/days/{dayWorklogCode}")
    public ResponseEntity<ResponseDTO> selectDayWorklogDetail(@PathVariable int dayWorklogCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일일 업무일지 상세 조회 성공", dayWorklogService.selectDayWorklog(dayWorklogCode)));
    }

    @PostMapping(value = "/worklogs/days")
    public ResponseEntity<ResponseDTO> insertDayWorklog(@RequestBody DayWorklogDTO dayWorklogDTO) {

        log.info("[DayWorklogController] PostMapping dayWorklogDTO : " + dayWorklogDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일일 업무일지 등록 성공", dayWorklogService.insertDayWorklog(dayWorklogDTO)));
    }

    @PutMapping(value = "/worklogs/days")
    public ResponseEntity<ResponseDTO> updateDayWorklog(@RequestBody DayWorklogDTO dayWorklogDTO) {

        log.info("[DayWorklogController] PutMapping dayWorklogDTO : " + dayWorklogDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일일 업무일지 업데이트 성공", dayWorklogService.updateDayWorklog(dayWorklogDTO)));
    }

    public ResponseEntity<ResponseDTO> deleteDayWorklog(@PathVariable int dayWorklogCode) {

        log.info("[DayWorklogController] DeleteMapping dayWorklogCode : " + dayWorklogCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일일 업무일지 삭제 성공", dayWorklogService.deleteDayWorklog(dayWorklogCode)));
    }
}
