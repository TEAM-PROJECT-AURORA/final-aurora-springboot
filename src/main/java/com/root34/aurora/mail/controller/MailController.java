package com.root34.aurora.mail.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.mail.dto.BlackList;
import com.root34.aurora.mail.dto.MailDTO;
import com.root34.aurora.mail.dto.TagDTO;
import com.root34.aurora.mail.service.MailService;
import com.root34.aurora.mail.dto.MailSearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

//@Api(tags = {"mails"}) // Swagger
@Slf4j
@RestController
@RequestMapping("api/v1")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailsService) {
        this.mailService = mailsService;
    }

    /**
    	* @MethodName : sendMail
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 메일 전송
    */
//    @Transactional
//    @PostMapping(value ="/mails")
//    public ResponseEntity<ResponseDTO> sendMail(HttpServletRequest request, @RequestBody MailDTO mailDTO) {
//
//        try {
//            log.info("[MailController] sendMail Start");
//            Integer memberCode = (Integer) request.getAttribute("memberCode");
//            log.info("[MailController] memberCode : " + memberCode);
//            mailDTO.setMemberCode(memberCode);
//            log.info("[MailController] mailDTO : " + mailDTO);
//
//            boolean result = mailService.sendMail(mailDTO);
//
//            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메일 전송 성공!", result));
//        } catch (Exception e) {
//            log.info("[MailController] Exception : " + e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
//        }
//    }
    @Transactional
    @PostMapping(value = "/mails", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDTO> sendMail(HttpServletRequest request,
                                                @RequestPart("mailDTO") MailDTO mailDTO,
                                                @RequestPart(name = "fileList", required = false) List<MultipartFile> fileList) {

        try {
            log.info("[MailController] sendMail Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[MailController] memberCode : " + memberCode);
            mailDTO.setMemberCode(memberCode);
            log.info("[MailController] mailDTO : " + mailDTO);

            boolean result = mailService.sendMail(mailDTO, fileList);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메일 전송 성공!", result));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : readUnseenMails
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 메일 목록 갱신
    */
    @Transactional
    @GetMapping(value = "/mails/unseen")
    public ResponseEntity<ResponseDTO> readUnseenMails() {

        try {
            log.info("[MailController] readUnseenMails Start");

            mailService.readUnseenMails();

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메일 목록 갱신 성공!", true));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : selectMailListByConditions
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 조건별 메일 목록 조회
    */
    @GetMapping(value = "mails")
    public ResponseEntity<ResponseDTO> selectMailListByConditions(HttpServletRequest request,
                                                                  @ModelAttribute MailSearchCriteria searchCriteria,
                                                                  @RequestParam int offset) {

        try {
            log.info("[MailController] selectMailListByConditions Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[MailController] memberCode : " + memberCode);
            log.info("[MailController] searchCriteria : " + searchCriteria);
            log.info("[MailController] offset : " + offset);

            HashMap<String, Object> searchConditions = mailService.convertSearchCriteriaToMap(searchCriteria);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메일 목록 조회 성공",
                    mailService.selectMailListByConditions(memberCode, offset, searchConditions)));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : selectMailDetailByMailCode
    	* @Date : 2023-04-12
    	* @Writer : 김수용
    	* @Description : 메일 상세 조회
    */
    @GetMapping(value = "/mails/{mailCode}")
    public ResponseEntity<ResponseDTO> selectMailDetailByMailCode(HttpServletRequest request, @PathVariable long mailCode) {

        try {
            log.info("[MailController] selectMailDetailByMailCode Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[MailController] memberCode : " + memberCode);
            log.info("[MailController] mailCode : " + mailCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메일 상세 조회 성공",
                    mailService.selectMailDetailByMailCode(memberCode, mailCode)));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : updateImportantStatus
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 메일 중요 상태 수정
    */
    @Transactional
    @PutMapping("/mails/{mailCode}/important-status/{importantStatus}")
    public ResponseEntity<ResponseDTO> updateImportantStatus(@PathVariable long mailCode,
                                                             @PathVariable char importantStatus) {

        try {
            log.info("[MailController] updateImportantStatus Start");
            log.info("[MailController] mailCode : " + mailCode);
            log.info("[MailController] importantStatus : " + importantStatus);

            MailDTO mailDTO = new MailDTO();
            mailDTO.setMailCode(mailCode);
            mailDTO.setImportantStatus(importantStatus);
            log.info("[MailController] mailDTO : " + mailDTO);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메일 중요 상태 수정 성공",
                    mailService.updateImportantStatus(mailDTO)));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : updateDeleteStatus
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 메일 삭제 상태 수정 
    */
    @Transactional
    @DeleteMapping("/mails/delete-status/{deleteStatus}")
    public ResponseEntity<ResponseDTO> updateDeleteStatus(@RequestBody List<Long> mailCodeList,
                                                             @PathVariable char deleteStatus) {

        try {
            log.info("[MailController] updateDeleteStatus Start");
            log.info("[MailController] mailCodeList : " + mailCodeList);
            log.info("[MailController] deleteStatus : " + deleteStatus);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메일 삭제 상태 수정 성공",
                    mailService.updateDeleteStatus(mailCodeList, deleteStatus)));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : updateMailTag
    	* @Date : 2023-04-12
    	* @Writer : 김수용
    	* @Description : 메일 태그 변경
    */
    @Transactional
    @PutMapping(value = "/mails/{mailCode}/tags/{tagCode}")
    public ResponseEntity<ResponseDTO> updateMailTag(@PathVariable long mailCode, @PathVariable long tagCode) {

        try {
            log.info("[MailController] updateMailTag Start");
            log.info("[MailController] mailCode : " + mailCode);
            log.info("[MailController] tagCode : " + tagCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메일 태그 변경 성공",
                    mailService.updateMailTag(mailCode, tagCode)));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : deleteMail
     * @Date : 2023-04-12
     * @Writer : 김수용
     * @Description : 메일 완전 삭제
     */
    @Transactional
    @DeleteMapping(value = "/mails")
    public ResponseEntity<ResponseDTO> deleteMail(@RequestBody List<Long> mailCodeList) {

        try {
            log.info("[MailController] deleteMail Start");
            log.info("[MailController] mailCodeList : " + mailCodeList);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "메일 완전 삭제 성공",
                    mailService.deleteMail(mailCodeList)));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : registerTag
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 태그 생성
    */
    @Transactional
    @PostMapping(value = "/mails/tags")
    public ResponseEntity<ResponseDTO> registerTag(HttpServletRequest request, @RequestBody TagDTO tagDTO) {

        try {
            log.info("[MailController] registerTag Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[MailController] memberCode : " + memberCode);
            tagDTO.setMemberCode(memberCode);
            log.info("[MailController] tagDTO : " + tagDTO);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "태그 생성 성공",
                    mailService.registerTag(tagDTO)));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : selectTagListByMemberCode
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 태그 목록 조회
    */
    @GetMapping(value = "/mails/tags")
    public ResponseEntity<ResponseDTO> selectTagListByMemberCode(HttpServletRequest request) {

        try {
            log.info("[MailController] selectTagListByMemberCode Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[MailController] memberCode : " + memberCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "태그 목록 조회 성공",
                    mailService.selectTagListByMemberCode(memberCode)));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : updateTag
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 태그 수정
    */
    @Transactional
    @PutMapping(value = "/mails/tags")
    public ResponseEntity<ResponseDTO> updateTag(HttpServletRequest request, @RequestBody TagDTO tagDTO) {

        try {
            log.info("[MailController] updateTag Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[MailController] memberCode : " + memberCode);
            tagDTO.setMemberCode(memberCode);
            log.info("[MailController] tagDTO : " + tagDTO);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "태그 수정 성공",
                    mailService.updateTag(tagDTO)));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
    	* @MethodName : deleteTag
    	* @Date : 2023-04-10
    	* @Writer : 김수용
    	* @Description : 태그 삭제
    */
    @Transactional
    @DeleteMapping(value = "/mails/tags/{tagCode}")
    public ResponseEntity<ResponseDTO> deleteTag(@PathVariable long tagCode) {

        try {
            log.info("[MailController] deleteTag Start");
            log.info("[MailController] tagCode : " + tagCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "태그 삭제 성공",
                    mailService.deleteTag(tagCode)));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : registerBlockList
     * @Date : 2023-04-10
     * @Writer : 김수용
     * @Description : 블랙리스트 등록
     */
    @Transactional
    @PostMapping(value = "/mails/black-list")
    public ResponseEntity<ResponseDTO> registerBlackList(HttpServletRequest request, @RequestBody BlackList blackList) {

        try {
            log.info("[MailController] registerBlockList Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[MailController] memberCode : " + memberCode);
            log.info("[MailController] blackList : " + blackList);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "블랙리스트 등록 성공",
                    mailService.registerBlackList(memberCode, blackList.getEmails())));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : selectBlackListByMemberCode
     * @Date : 2023-04-10
     * @Writer : 김수용
     * @Description : 블랙리스트 조회
     */
    @GetMapping(value = "/mails/black-list")
    public ResponseEntity<ResponseDTO> selectBlackListByMemberCode(HttpServletRequest request) {

        try {
            log.info("[MailController] selectBlackListByMemberCode Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[MailController] memberCode : " + memberCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "블랙리스트 조회 성공",
                    mailService.selectBlackListByMemberCode(memberCode)));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : deleteBlockedSenderEmail
     * @Date : 2023-04-10
     * @Writer : 김수용
     * @Description : 블랙리스트에서 삭제
     */
    @Transactional
    @DeleteMapping(value = "/mails/black-list")
    public ResponseEntity<ResponseDTO> deleteBlockedSenderEmail(HttpServletRequest request, @RequestBody BlackList blackList) {

        try {
            log.info("[MailController] deleteBlockedSenderEmail Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[MailController] memberCode : " + memberCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "블랙리스트에서 삭제 성공",
                    mailService.deleteBlockedSenderEmail(memberCode, blackList.getEmails())));
        } catch (Exception e) {
            log.info("[MailController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }
}
