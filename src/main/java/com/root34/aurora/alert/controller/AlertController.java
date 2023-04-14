package com.root34.aurora.alert.controller;

import com.root34.aurora.alert.service.AlertService;
import com.root34.aurora.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("api/v1")
public class AlertController {

//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
    private final AlertService alertService;

    public AlertController(AlertService alertService) {

        this.alertService = alertService;
    }

    /**
    	* @MethodName : sendAlertToUser
    	* @Date : 2023-04-14
    	* @Writer : 김수용
    	* @Description : 알림 전송
    */
//    public void sendAlertToUser(AlertDTO alertDTO, int receiverMemberCode) {
//
//        log.info("[AlertController] sendAlertToUser Start");
//        messagingTemplate.convertAndSendToUser(
//                String.valueOf(receiverMemberCode), "/topic/alert", alertDTO);
//    }

    /**
     * @MethodName : selectAlertListByMemberCode
     * @Date : 2023-04-13
     * @Writer : 김수용
     * @Description : 알림 목록 조회
     */
    @GetMapping("/alerts")
    public ResponseEntity<ResponseDTO> selectAlertListByMemberCode(HttpServletRequest request) {

        try {
            log.info("[AlertController] selectAlertListByMemberCode Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[AlertController] memberCode : " + memberCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "알림 목록 조회 성공",
                    alertService.selectAlertListByMemberCode(memberCode)));
        } catch (Exception e) {
            log.info("[AlertController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : readAlert
     * @Date : 2023-04-13
     * @Writer : 김수용
     * @Description : 알림 읽기
     */
    @Transactional
    @PutMapping("/alerts/{alertCode}")
    public ResponseEntity<ResponseDTO> readAlert(@PathVariable long alertCode) {

        try {
            log.info("[AlertController] updateAlert Start");

            alertService.readAlert(alertCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "알림 읽기 성공",
                    true));
        } catch (Exception e) {
            log.info("[AlertController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }

    /**
     * @MethodName : readAllAlert
     * @Date : 2023-04-13
     * @Writer : 김수용
     * @Description : 모든 알림 읽기
     */
    @Transactional
    @DeleteMapping("/alerts")
    public ResponseEntity<ResponseDTO> readAllAlert(HttpServletRequest request) {

        try {
            log.info("[AlertController] readAllAlert Start");
            Integer memberCode = (Integer) request.getAttribute("memberCode");
            log.info("[AlertController] memberCode : " + memberCode);

            alertService.readAllAlert(memberCode);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "모든 알림 읽기 성공",
                    true));
        } catch (Exception e) {
            log.info("[AlertController] Exception : " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null));
        }
    }
}
