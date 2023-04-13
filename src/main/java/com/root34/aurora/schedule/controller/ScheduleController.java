package com.root34.aurora.schedule.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.schedule.dto.ScheduleDTO;
import com.root34.aurora.schedule.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) { this.scheduleService = scheduleService; }

    @GetMapping("/schedules/calendar/my/{memberCode}")
    public ResponseEntity<ResponseDTO> selectScheduleCalendarAboutMe(@PathVariable int memberCode ) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", scheduleService.selectScheduleCalendarAboutMe(memberCode)));
    }

    @GetMapping("/schedules/calendar/team/{teamCode}")
    public ResponseEntity<ResponseDTO> selectScheduleCalendarAboutTeam(@PathVariable String teamCode) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", scheduleService.selectScheduleCalendarAboutTeam(teamCode)));
    }

    @GetMapping("/schedules/calendar/day")
    public ResponseEntity<ResponseDTO> selectScheduleCalendarAboutDay() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", scheduleService.selectScheduleCalendarAboutDay()));
    }

    @GetMapping("/schedules/calendar/{scheduleCode}/date")
    public ResponseEntity<ResponseDTO> selectScheduleDetail(@RequestParam Date scheduleStartDay, @RequestParam Date scheduleEndDay, @PathVariable int scheduleCode) {

        log.info("[ScheduleController] selectScheduleDetail scheduleCode" + scheduleCode);

        Map map = new HashMap<>();
        map.put("scheduleCode", scheduleCode);
        map.put("scheduleStartDay", scheduleStartDay);
        map.put("scheduleEndDay", scheduleEndDay);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"일정 상세정보 조회 성공", scheduleService.selectSchedule(map)));
    }

    @PostMapping(value = "/schedules/calendar")
    public ResponseEntity<ResponseDTO> insertSchedule(@RequestBody ScheduleDTO scheduleDTO) {
//    public ResponseEntity<ResponseDTO> insertSchedule(@ModelAttribute ScheduleDTO scheduleDTO) {
        log.info("[ScheduleController] PostMapping scheduleDTO : " + scheduleDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일정 입력 성공", scheduleService.insertSchedule(scheduleDTO)));
    }

    @PutMapping(value = "/schedules/calendar")
    public ResponseEntity<ResponseDTO> updateSchedule(@RequestBody ScheduleDTO scheduleDTO) {
//    public ResponseEntity<ResponseDTO> updateSchedule(@ModelAttribute ScheduleDTO scheduleDTO) {

        log.info("[ScheduleController] PostMapping scheduleDTO : " + scheduleDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일정 업데이트 성공", scheduleService.updateSchedule(scheduleDTO)));
    }

    @DeleteMapping(value = "/schedules/calendar/{scheduleCode}")
    public ResponseEntity<ResponseDTO> deleteSchedule(@PathVariable int scheduleCode) {

        log.info("[ScheduleController] DeleteMapping scheduleCode : " + scheduleCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일정 삭제 성공", scheduleService.deleteSchedule(scheduleCode)));
    }
}
