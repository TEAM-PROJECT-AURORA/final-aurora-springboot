package com.root34.aurora.alert.service;

import com.root34.aurora.alert.dao.AlertMapper;
import com.root34.aurora.alert.dto.AlertDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void registerAlert(int senderMemberCode, int receiverMemberCode, String alertType, Object code) {

        log.info("[AlertService] registerAlert Start");
        AlertDTO alertDTO = new AlertDTO();
        alertDTO.setSenderMemberCode(senderMemberCode);
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

        alertMapper.registerAlert(alertDTO);
    }

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
