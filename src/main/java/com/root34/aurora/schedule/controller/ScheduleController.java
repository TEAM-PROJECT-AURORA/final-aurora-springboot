package com.root34.aurora.schedule.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.schedule.dto.ScheduleDTO;
import com.root34.aurora.schedule.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) { this.scheduleService = scheduleService; }

    @GetMapping("/schedules/calendar/month")
    public ResponseEntity<ResponseDTO> selectScheduleCalendarAboutMonth() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", scheduleService.selectScheduleCalendarAboutMonth()));
    }

    @GetMapping("/schedules/calendar/week")
    public ResponseEntity<ResponseDTO> selectScheduleCalendarAboutWeek() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", scheduleService.selectScheduleCalendarAboutWeek()));
    }

    @GetMapping("/schedules/calendar/day")
    public ResponseEntity<ResponseDTO> selectScheduleCalendarAboutDay() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", scheduleService.selectScheduleCalendarAboutDay()));
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



}
