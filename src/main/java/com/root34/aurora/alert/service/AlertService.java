package com.root34.aurora.alert.service;

import com.root34.aurora.alert.dao.AlertMapper;
import com.root34.aurora.alert.dto.AlertDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @ClassName : AlertService
 @Date : 23.04.13.
 @Writer : 김수용
 @Description : 알림관련 요청을 처리할 Service
 */
@Slf4j
@Service
public class AlertService {

//    @Autowired
//    private AlertController alertController;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private final AlertMapper alertMapper;

    @Autowired
    public AlertService(AlertMapper alertMapper) {

        this.alertMapper = alertMapper;
    }

    /**
     * @MethodName : registerMailAlert
     * @Date : 2023-04-13
     * @Writer : 김수용
     * @Description : 알림 등록
     */
//    public void registerAlert(int senderMemberCode, int receiverMemberCode, String alertType, Object code) {
//
//        log.info("[AlertService] registerAlert Start");
//        AlertDTO alertDTO = new AlertDTO();
//        alertDTO.setSenderMemberCode(senderMemberCode);
//        alertDTO.setReceiverMemberCode(receiverMemberCode);
//        alertDTO.setAlertType(alertType);
//
//        switch (alertType) {
//
//            case "메일":
//                alertDTO.setMailCode((Long) code);
//                break;
//            case "보고":
//                alertDTO.setReportCode((Long) code);
//                break;
//            case "보고 회차":
//                alertDTO.setRoundCode((Long) code);
//                break;
//            case "상세보고":
//                alertDTO.setDetailCode((Long) code);
//                break;
//            case "보고 댓글":
//                alertDTO.setReplyCode((Long) code);
//                break;
//            case "결재":
//                alertDTO.setAppCode((Integer) code);
//                break;
//        }
//        log.info("[AlertService] alertDTO : " + alertDTO);
//
//        alertMapper.registerAlert(alertDTO);
//    }

    /**
     * @MethodName : registerMailAlert
     * @Date : 2023-04-13
     * @Writer : 김수용
     * @Description : 메일 알림 등록
     */
    public void registerMailAlert(String senderEmail, String recipient, String alertType, long mailCode) {

        log.info("[AlertService] registerAlert Start");

        Integer memberCode = alertMapper.getMemberCodeByEmail(recipient);
        log.info("[AlertService] memberCode : " + memberCode);

        if (memberCode == null) return;

        AlertDTO alertDTO = new AlertDTO();
        alertDTO.setSenderEmail(senderEmail);
        alertDTO.setReceiverMemberCode(memberCode);
        alertDTO.setAlertType("메일");
        alertDTO.setMailCode(mailCode);
        log.info("[AlertService] alertDTO : " + alertDTO);

        alertMapper.registerAlert(alertDTO);

        // 웹소켓을 통해 알림 전송
        sendAlertToUser(alertDTO, memberCode);
    }

    public void sendAlertToUser(AlertDTO alertDTO, int receiverMemberCode) {

        log.info("[AlertService] sendAlertToUser Start");
        messagingTemplate.convertAndSendToUser(
                String.valueOf(receiverMemberCode), "/topic/alert", alertDTO);
    }

    /**
    	* @MethodName : registerReportAlert
    	* @Date : 2023-04-14
    	* @Writer : 김수용
    	* @Description : 보고 알림 등록
    */
    public void registerReportAlert(AlertDTO alertDTO) {

        log.info("[AlertService] registerAlert Start");
        alertDTO.setAlertType("보고");

        alertMapper.registerAlert(alertDTO);
        log.info("[AlertService] alertDTO : " + alertDTO);

        // 웹소켓을 통해 알림 전송
        sendAlertToUser(alertDTO, alertDTO.getReceiverMemberCode());
    }

    /**
    	* @MethodName : registerReportRoundAlert
    	* @Date : 2023-04-14
    	* @Writer : 김수용
    	* @Description : 보고 회차 알림 등록
    */
    public void registerReportRoundAlert(AlertDTO alertDTO) {

        log.info("[AlertService] registerReportRoundAlert Start");
        alertDTO.setAlertType("보고 회차");

        alertMapper.registerAlert(alertDTO);
        log.info("[AlertService] alertDTO : " + alertDTO);

        // 웹소켓을 통해 알림 전송
        sendAlertToUser(alertDTO, alertDTO.getReceiverMemberCode());
    }

    /**
    	* @MethodName : registerDetailReportAlert
    	* @Date : 2023-04-14
    	* @Writer : 김수용
    	* @Description : 상세보고 알림 등록
    */
    public void registerDetailReportAlert(AlertDTO alertDTO) {

        log.info("[AlertService] registerDetailReportAlert Start");
        alertDTO.setAlertType("상세 보고");

        alertMapper.registerAlert(alertDTO);
        log.info("[AlertService] alertDTO : " + alertDTO);

        // 웹소켓을 통해 알림 전송
        sendAlertToUser(alertDTO, alertDTO.getReceiverMemberCode());
    }

    /**
    	* @MethodName : registerReportRoundReplyAlert
    	* @Date : 2023-04-14
    	* @Writer : 김수용
    	* @Description : 보고 댓글
    */
    public void registerReportRoundReplyAlert(AlertDTO alertDTO) {

        log.info("[AlertService] registerReportRoundReplyAlert Start");
        alertDTO.setAlertType("보고 댓글");

        alertMapper.registerAlert(alertDTO);
        log.info("[AlertService] alertDTO : " + alertDTO);

        // 웹소켓을 통해 알림 전송
        sendAlertToUser(alertDTO, alertDTO.getReceiverMemberCode());
    }

    /**
     * @MethodName : selectAlertListByMemberCode
     * @Date : 2023-04-13
     * @Writer : 김수용
     * @Description : 알림 목록 조회
     */
    public List<AlertDTO> selectAlertListByMemberCode(int memberCode) {

        log.info("[AlertService] registerApprovalAlert Start");
        log.info("[AlertService] memberCode : " + memberCode);

        List<AlertDTO> alertList = alertMapper.selectAlertListByMemberCode(memberCode);
        log.info("[AlertService] alertList : " + alertList);

        return alertList;
    }

    /**
     * @MethodName : updateAlert
     * @Date : 2023-04-13
     * @Writer : 김수용
     * @Description : 알림 읽음으로 수정
     */
    public void updateAlert(int receiverMemberCode, String alertType, Object code) {

        log.info("[AlertService] updateAlert Start");
        AlertDTO alertDTO = new AlertDTO();
        alertDTO.setReceiverMemberCode(receiverMemberCode);
        alertDTO.setAlertType(alertType);

        switch (alertType) {

            case "메일":
                alertDTO.setMailCode((Long) code);
                break;
            case "보고":
                alertDTO.setReportCode((Long) code);
                break;
            case "보고 회차":
                alertDTO.setRoundCode((Long) code);
                break;
            case "상세보고":
                alertDTO.setDetailCode((Long) code);
                break;
            case "보고 댓글":
                alertDTO.setReplyCode((Long) code);
                break;
            case "결재":
                alertDTO.setAppCode((Integer) code);
                break;
        }
        log.info("[AlertService] alertDTO : " + alertDTO);

        alertMapper.updateAlert(alertDTO);
    }

    /**
     * @MethodName : readAlert
     * @Date : 2023-04-13
     * @Writer : 김수용
     * @Description : 알림 읽기 - 알림 x 버튼
     */
    public void readAlert(long alertCode) {

        log.info("[AlertService] readAlert Start");
        log.info("[AlertService] alertCode : " + alertCode);

        alertMapper.readAlert(alertCode);
    }

    /**
     * @MethodName : readAllAlert
     * @Date : 2023-04-13
     * @Writer : 김수용
     * @Description : 모든 알림 읽기 - 모든 알림 닫기
     */
    public void readAllAlert(int memberCode) {

        log.info("[AlertService] readAlert Start");
        log.info("[AlertService] memberCode : " + memberCode);

        alertMapper.readAllAlert(memberCode);
    }
}
