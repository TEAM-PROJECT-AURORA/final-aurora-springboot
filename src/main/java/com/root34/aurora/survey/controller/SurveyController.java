package com.root34.aurora.survey.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.survey.dto.SurveyDTO;
import com.root34.aurora.survey.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping("survey")
    public ResponseEntity<ResponseDTO> insertSurvey(@RequestBody SurveyDTO surveyDTO) {

        log.info("[SurveyController] insertSurvey : " + surveyDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "설문 추가 성공", surveyService.insertSurvey(surveyDTO)));
    }
}
