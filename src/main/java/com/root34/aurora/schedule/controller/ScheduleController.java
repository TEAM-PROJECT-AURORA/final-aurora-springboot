package com.root34.aurora.schedule.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.schedule.dto.ScheduleDTO;
import com.root34.aurora.schedule.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 @ClassName : ScheduleController
 @Date : 23.03.20
 @Writer : 서지수
 @Description : 일정관리 컨트롤러
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) { this.scheduleService = scheduleService; }

    /**
     @MethodName  : selectScheduleCalendarAboutMe
     @Date : 23.03.20. -> 23.04.05
     @Writer : 서지수
     @Description : 나의 캘린더 조회
     */
    @GetMapping("/schedules/calendar/my/{memberCode}")
    public ResponseEntity<ResponseDTO> selectScheduleCalendarAboutMe(@PathVariable int memberCode ) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", scheduleService.selectScheduleCalendarAboutMe(memberCode)));
    }

    /**
     @MethodName  : selectScheduleCalendarAboutTeam
     @Date : 23.03.20.
     @Writer : 서지수
     @Description : 팀 캘린더 조회
     */
    @GetMapping("/schedules/calendar/team/{teamCode}")
    public ResponseEntity<ResponseDTO> selectScheduleCalendarAboutTeam(@PathVariable String teamCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", scheduleService.selectScheduleCalendarAboutTeam(teamCode)));
    }

//    @GetMapping("/schedules/calendar/day")
//    public ResponseEntity<ResponseDTO> selectScheduleCalendarAboutDay() {
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", scheduleService.selectScheduleCalendarAboutDay()));
//    }

    /**
     @MethodName  : selectScheduleDetail
     @Date : 23.03.20. -> 23.04.06
     @Writer : 서지수
     @Description : 일정관리 디테일 조회
     */
    @GetMapping("/schedules/calendar/{scheduleCode}")
    public ResponseEntity<ResponseDTO> selectScheduleDetail(@PathVariable int scheduleCode) {

        log.info("[ScheduleController] selectScheduleDetail scheduleCode" + scheduleCode);
//        Map map = new HashMap<>();
//        map.put("scheduleCode", scheduleCode);
//        map.put("scheduleStartDay", scheduleStartDay);
//        map.put("scheduleEndDay", scheduleEndDay);
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"일정 상세정보 조회 성공", scheduleService.selectSchedule(map)));
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"일정 상세정보 조회 성공", scheduleService.selectSchedule(scheduleCode)));
    }

    /**
     @MethodName  : insertSchedule
     @Date : 23.03.21. 
     @Writer : 서지수
     @Description : 일정관리 생성
     */
    @PostMapping(value = "/schedules/calendar")
    public ResponseEntity<ResponseDTO> insertSchedule(@RequestBody ScheduleDTO scheduleDTO) {
//    public ResponseEntity<ResponseDTO> insertSchedule(@ModelAttribute ScheduleDTO scheduleDTO) {
        log.info("[ScheduleController] PostMapping scheduleDTO : " + scheduleDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일정 입력 성공", scheduleService.insertSchedule(scheduleDTO)));
    }

    /**
     @MethodName  : updateSchedule
     @Date : 23.03.21. 
     @Writer : 서지수
     @Description : 일정관리 수정
     */
    @PutMapping(value = "/schedules/calendar")
    public ResponseEntity<ResponseDTO> updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {
//    public ResponseEntity<ResponseDTO> updateSchedule(@ModelAttribute ScheduleDTO scheduleDTO) {

        log.info("[ScheduleController] PostMapping scheduleDTO : " + scheduleDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일정 업데이트 성공", scheduleService.updateSchedule(scheduleDTO)));
    }

    /**
     @MethodName  : deleteSchedule
     @Date : 23.03.21. 
     @Writer : 서지수
     @Description : 일정관리 삭제
     */
    @DeleteMapping(value = "/schedules/{scheduleCode}")
    public ResponseEntity<ResponseDTO> deleteSchedule(@PathVariable int scheduleCode) {

        log.info("[ScheduleController] DeleteMapping scheduleCode : " + scheduleCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일정 삭제 성공", scheduleService.deleteSchedule(scheduleCode)));
    }
}
