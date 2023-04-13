package com.root34.aurora.survey.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.survey.dto.AnswerDTO;
import com.root34.aurora.survey.dto.ReplyStatusDTO;
import com.root34.aurora.survey.dto.SurveyDTO;
import com.root34.aurora.survey.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        int totalCount = surveyService.selectTotalSurveys(null);
        int limit = 15;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
        selectCriteria.setSearchValue(memberCode.toString());
        log.info("[SurveyController] selectCriteria : " + selectCriteria);

        ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);

        responseDtoWithPaging.setData(surveyService.selectAllSurveysWithPaging(selectCriteria));
        log.info("[SurveyController] surveyService.selectAllSurveysWithPaging(selectCriteria : " + surveyService.selectAllSurveysWithPaging(selectCriteria));
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "검색 조회 성공", responseDtoWithPaging));
    }

    /**
    	* @MethodName : selectReplyStatus
    	* @Date : 2023-04-07
    	* @Writer : 오승재
    	* @Description : 설문 답변 상태 조회
    */
    @GetMapping("survey/{surveyCode}/member/{memberCode}")
    public ResponseEntity<ResponseDTO> selectReplyStatus(@PathVariable String surveyCode, @PathVariable Integer memberCode) {

        log.info("[SurveyController] selectReplyStatus : " + surveyCode + memberCode);
        Map map = new HashMap();
        map.put("surveyCode", surveyCode);
        map.put("memberCode", memberCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "답변 상태 조회 성공", surveyService.selectReplyStatus(map)));
    }

    /**
    	* @MethodName : selectAllSurveysForManagementWithPaging
    	* @Date : 2023-04-08
    	* @Writer : 오승재
    	* @Description : 설문 관리를 위한 페이징 조회
    */
    @GetMapping("survey/surveys-management")
    public ResponseEntity<ResponseDTO> selectAllSurveysForManagementWithPaging(@RequestParam(name="offset", defaultValue="1") String offset, @RequestParam(required = false) String searchCondition, @RequestParam(required = false) String searchValue) {

        log.info("[SurveyController] selectAllSurveysForManagementWithPaging : " + offset);

        int totalCount = surveyService.selectTotalSurveys(searchValue);
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
    
    /**
    	* @MethodName : insertSurveyReply
    	* @Date : 2023-04-09
    	* @Writer : 오승재
    	* @Description : 설문 답변 등록
    */
    @PostMapping("survey/{surveyCode}")
    public ResponseEntity<ResponseDTO> insertSurveyReply(@RequestBody Map<String, Object> json, ReplyStatusDTO replyStatusDTO, @PathVariable String surveyCode) {


        replyStatusDTO.setSurveyCode(surveyCode);
        log.info("[SurveyController] insertSurveyReply json : " + json);
        log.info("[SurveyController] insertSurveyReply replyStatusDTO : " + replyStatusDTO);

        List<Map<String, Object>> answerList = (List<Map<String, Object>>) json.get("form");
        List<AnswerDTO> answerDTOList = new ArrayList<>();

        for (Map<String, Object> answer : answerList) {
            AnswerDTO answerDTO = new AnswerDTO();
            answerDTO.setQuestionNo((String) answer.get("questionNo"));
            answerDTO.setChoiceNo((String) answer.get("choiceNo"));
            answerDTO.setAnswerBody((String) answer.get("answerBody"));
            answerDTO.setAnswerNo((String) answer.get("answerNo"));
            answerDTO.setMemberCode(replyStatusDTO.getMemberCode());
            answerDTOList.add(answerDTO);
        }
        log.info("[SurveyController] insertSurveyReply answerDTOList : " + answerDTOList);

        int result = surveyService.insertSurveyReply(answerDTOList);

        String resultMessage = "답변 추가 실패";
        if(result > 0) {
            resultMessage = surveyService.insertSurveyReplyStatus(replyStatusDTO);
        }

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "설문 답변 추가 성공", resultMessage));
    }

    /**
    	* @MethodName : deleteSurveys
    	* @Date : 2023-04-09
    	* @Writer : 오승재
    	* @Description : 설문 삭제
    */
    @DeleteMapping("survey")
    public ResponseEntity<ResponseDTO> deleteSurveys(@RequestBody JSONObject object) {

        log.info("[SurveyController] deleteSurveys : " + object);
        String objectAsString = object.getAsString("surveyCodes");
        String[] surveyCodes = objectAsString.substring(1, objectAsString.length() - 1).split(", ");

        log.info("[SurveyController] deleteSurveys : " + surveyCodes);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "설문 삭제 성공", surveyService.deleteSurveys(surveyCodes)));
    }

    /**
    	* @MethodName : selectSurveyForUpdate
    	* @Date : 2023-04-09
    	* @Writer : 오승재
    	* @Description : 수정을 위한 설문 조회
    */
    @GetMapping("survey/{surveyCode}")
    public ResponseEntity<ResponseDTO> selectSurveyForUpdate(@PathVariable String surveyCode) {

        log.info("[SurveyController] selectSurveyForUpdate : " + surveyCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "설문 조회 성공", surveyService.selectSurveyForUpdate(surveyCode)));
    }

    /**
    	* @MethodName : updateSurvey
    	* @Date : 2023-04-09
    	* @Writer : 오승재
    	* @Description : 설문 수정
    */
    @PutMapping("survey")
    public ResponseEntity<ResponseDTO> updateSurvey(@RequestBody SurveyDTO surveyDTO) {

        log.info("[SurveyController] updateSurvey : " + surveyDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "설문 수정 성공", surveyService.updateSurvey(surveyDTO)));
    }

    /**
    	* @MethodName : deleteQuestions
    	* @Date : 2023-04-10
    	* @Writer : 오승재
    	* @Description : 질문 삭제
    */
    @DeleteMapping("survey/questions")
    public ResponseEntity<ResponseDTO> deleteQuestions(@RequestBody JSONObject object) {

        String objectAsString = object.getAsString("questionNos");
        String[] questionNos = objectAsString.substring(1, objectAsString.length() - 1).split(", ");

        log.info("[SurveyController] updateSurvey : " + questionNos);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "질문 삭제 성공", surveyService.deleteQuestions(questionNos)));
    }

    /**
    	* @MethodName : selectSurveyReplyDetail
    	* @Date : 2023-04-10
    	* @Writer : 오승재
    	* @Description : 자기 답변 조회
    */
    @GetMapping("survey/member/{memberCode}")
    public ResponseEntity<ResponseDTO> selectSurveyReplyDetail(@PathVariable Integer memberCode, @RequestParam String surveyCode) {

        log.info("[SurveyController] selectSurveyReplyDetail : " + memberCode + surveyCode);

        Map map = new HashMap();
        map.put("memberCode", memberCode);
        map.put("surveyCode", surveyCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "답변 조회 성공", surveyService.selectSurveyReplyDetail(map)));
    }
}
