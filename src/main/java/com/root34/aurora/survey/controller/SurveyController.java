package com.root34.aurora.survey.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.survey.dto.SurveyDTO;
import com.root34.aurora.survey.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /**
    	* @MethodName : insertSurvey
    	* @Date : 2023-04-06
    	* @Writer : 오승재
    	* @Description : 설문 등록
    */
    @PostMapping("survey")
    public ResponseEntity<ResponseDTO> insertSurvey(@RequestBody SurveyDTO surveyDTO) {

        log.info("[SurveyController] insertSurvey : " + surveyDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "설문 추가 성공", surveyService.insertSurvey(surveyDTO)));
    }

    /**
    	* @MethodName : selectAllSurveysWithPaging
    	* @Date : 2023-04-07
    	* @Writer : 오승재
    	* @Description : 페이징 처리한 모든 설문 조회
    */
    @GetMapping("survey/surveys/{memberCode}")
    public ResponseEntity<ResponseDTO> selectAllSurveysWithPaging(@RequestParam(name="offset", defaultValue="1") String offset, @PathVariable Integer memberCode) {

        log.info("[SurveyController] selectAllSurveysWithPaging : " + offset);

        int totalCount = surveyService.selectTotalSurveys();
        int limit = 15;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
        selectCriteria.setSearchValue(memberCode.toString());
        log.info("[SurveyController] selectCriteria : " + selectCriteria);

        ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);

        responseDtoWithPaging.setData(surveyService.selectAllSurveysWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "검색 조회 성공", responseDtoWithPaging));
    }

    /**
    	* @MethodName : selectReplyStatus
    	* @Date : 2023-04-07
    	* @Writer : 오승재
    	* @Description : 설문 답변 상태 조회 TODO - 이거 위에거랑 합쳐야될 듯
    */
    @GetMapping("survey/{surveyCode}/member/{memberCode}")
    public ResponseEntity<ResponseDTO> selectReplyStatus(@PathVariable String surveyCode, @PathVariable Integer memberCode) {

        log.info("[SurveyController] selectReplyStatus : " + surveyCode + memberCode);
        Map map = new HashMap();
        map.put("surveyCode", surveyCode);
        map.put("memberCode", memberCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "답변 상태 조회 성공", surveyService.selectReplyStatus(map)));
    }

    @GetMapping("survey/surveys-management")
    public ResponseEntity<ResponseDTO> selectAllSurveysForManagementWithPaging(@RequestParam(name="offset", defaultValue="1") String offset, @RequestParam(required = false) String searchCondition, @RequestParam(required = false) String searchValue) {

        log.info("[SurveyController] selectAllSurveysForManagementWithPaging : " + offset);

        int totalCount = surveyService.selectTotalSurveys();
        int limit = 15;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
        if(searchCondition != null) {
            selectCriteria.setSearchCondition(searchCondition);
        }
        if(searchValue != null) {
            selectCriteria.setSearchValue(searchValue);
        }
        log.info("[SurveyController] selectCriteria : " + selectCriteria);

        ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(surveyService.selectAllSurveysForManagementWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }
}
