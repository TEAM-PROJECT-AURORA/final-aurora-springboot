package com.root34.aurora.approval.service;

import com.root34.aurora.approval.dao.ApprovalMapper;
import com.root34.aurora.approval.dto.ApprovalDTO;
import com.root34.aurora.approval.dto.ApprovalLineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Map;

/**
 @FileName : ApprovalService
 @Date : 2:05 PM
 @작성자 : heojaehong
 @프로그램 설명 : 결재 서비스 클래스
 */
@Service
@Slf4j
public class ApprovalService {

    private final ApprovalMapper approvalMapper;

    public ApprovalService (ApprovalMapper approvalMapper) {
        this.approvalMapper = approvalMapper;
    }

    /**
     @MethodName : lastList
     @Date : 4:21 PM
     @Writer : heojaehong
     @Description : 최근 결재 리스트 조회
     */
    public List<ApprovalDTO> lastList(int memberCode) {

        try {
            List<ApprovalDTO> approvalDtoList = approvalMapper.lastList(memberCode);
            log.info("[ApprovalService] lastList :" + approvalDtoList);
            if (approvalDtoList == null) {
                throw new Exception("목록이 없습니다.");
            }

            return approvalDtoList;
        } catch (Exception e) {
            log.error("[ApprovalService] lastList method error : " + e.getMessage());
            // 에러에 대한 응답 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당페이지가 존재하지 않습니다.", e);
        }
    }
    /**
        @MethodName : pendingList
    	@Date : 1:42 PM
    	@Writer : heojaehong
    	@Description : 미결재 리스트 메소드
    */
    public List<ApprovalDTO> pendingList(int memberCode) {

        try {
            List<ApprovalDTO> pendingList = approvalMapper.pendingList(memberCode);
            if(pendingList == null){
                throw new Exception("조회할 목록이 없습니다.");
            }

            return pendingList;
        } catch (Exception e) {
            log.error("[ApprovalService] pendingList method error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당페이지가 존재하지 않습니다.", e);
        }
    }

    /**
        @MethodName : completedList
    	@Date : 1:43 PM
    	@Writer : heojaehong
    	@Description : 결재완료 리스트 메소드
    */
    public List<ApprovalDTO> completedList(int memberCode) {

        try {
            List<ApprovalDTO> pendingList = approvalMapper.completedList(memberCode);
            if(pendingList == null){
                throw new Exception("조회할 목록이 없습니다.");
            }

            return pendingList;
        } catch (Exception e) {
            log.error("[ApprovalService] completedList method error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당페이지가 존재하지 않습니다.", e);
        }
    }

/**
    @MethodName : detailApprove
	@Date : 2:48 PM
	@Writer : heojaehong
	@Description : 상세정보 조회 메서드
*/
    public ApprovalDTO detailApprove(@PathVariable int appCode) {

        log.info("[ApprovalService] detailApprove 실행 " );
        try {
            ApprovalDTO approvalDTO = approvalMapper.detailApprove(appCode);
            log.info("approvalDTO : " + approvalDTO);
            if(appCode < 0){
                throw new Exception("해당 문서가 존재하지 않습니다.");
            }

            return approvalDTO;
        }catch (Exception e){
            log.error("[ApprovalService] detailApprove method error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당페이지가 존재하지 않습니다.", e);
        }
    }

    /**
    @MethodName : updateApproval
	@Date : 11:45 AM
	@Writer : heojaehong
	@Description : 결재수정 메서드
*/
    public ApprovalDTO updateApproval(ApprovalDTO approvalDTO) {

        log.info("[ApprovalService] updateApproval 실행 " );
        try {
            approvalDTO = approvalMapper.updateApproval(approvalDTO);
            int appCode = approvalDTO.getAppCode();
            if(appCode < 0) {
                throw new Exception("해당 문서가 존재하지 않습니다.");
            }

            return approvalDTO;
        }
        catch (Exception e) {
            log.error("[ApprovalService] updateApproval method error" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 페이지가 존재하지 않습니다.", e);
        }
    }

    /**
    @MethodName : updateApproval
	@Date : 11:45 AM
	@Writer : heojaehong
	@Description : 결재삭제 메서드
    */
    @Transactional
    public int deleteApproval(Map<String, Object> deleteApprove) {

        log.info("[ApprovalService] deleteApproval 실행");
        
        try {
             int result = approvalMapper.deleteApproval(deleteApprove);
             return result;
        }catch(Exception e) {
            log.error("[ApprovalService] deleteApproval 실행 오류" + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 서류가 없습니다.");

        }
    }

    /**
     @MethodName : approve
     @Date : 4:26 PM
     @Writer : heojaehong
     @Description : 전자결재 서류 등록
     */
    public ApprovalDTO approve(ApprovalDTO approvalDTO) { // 호출한 곳에서 예외처리

        try {
            log.info("[ApprovalService] approve 실행");
            int result = approvalMapper.insertApprove(approvalDTO);
            if(result == 0){
                throw new Exception("제출 실패!");
            }

            return approvalDTO;
        } catch (Exception e) {
            log.error("error 발생! 등록 실패 : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일시적인 오류로 등록에 실패했습니다.",e);
        }
    }
    /**
     @FileName : ApprovalService
     @Date : 8:45 PM
     @작성자 : heojaehong
     @Description  : 결재선 추가를 위한 서비스 메소드
     */
    public int setLineApproval(List<ApprovalLineDTO> approvalLineDTOList) {

        try {
            log.info("[ApprovalService] setLineApproval 실행");
            int result = approvalMapper.setLineApproval(approvalLineDTOList);

            if(result == 0) {
                throw new Exception("결재선 작성에 실패 했습니다.");
            }
            return result;
        } catch (Exception e) {
            log.error("error 발생! 결재선 작성에 실패 했습니다. : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 접근!", e);
        }
    }
    /**
     @FileName : ApprovalService
     @Date : 11:03 AM
     @작성자 : heojaehong
     @설명 : 결재선 상태 보여주는 메서드
     */
    public List<ApprovalLineDTO> approvalLine(int appCode) {

        try {
            log.info("[ApprovalService] approvalLine 실행");
            List<ApprovalLineDTO> list = approvalMapper.approvalLine(appCode);

            if(appCode < 0){
                throw new Exception("해당 결재문서가 없습니다");
            }
            return list;
        } catch (Exception e) {
            log.error("error 발생! 결재선 확인에 실패 했습니다. : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "확인할 수 없습니다.", e);
        }

    }
    /**
     @FileName : ApprovalService
     @Date : 11:03 AM
     @작성자 : heojaehong
     @설명 : 결재요청 보여주는 메서드
     */
    public List<ApprovalLineDTO> approvalLineList(int memberCode) {

        try {
            log.info("[ApprovalService] approvalLineList 실행");
            List<ApprovalLineDTO> list = approvalMapper.approvalLineList(memberCode);

            if(memberCode < 0){
                throw new Exception("해당 요청문서가 없습니다");
            }
            return list;
        } catch (Exception e) {
            log.error("error 발생! 결재선 확인에 실패 했습니다. : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "확인할 수 없습니다.", e);
        }
    }
    /**
     @FileName : ApprovalService
     @Date : 11:03 AM
     @작성자 : heojaehong
     @설명 : 결재선 수정하는 메서드
     */
    public int patchLineApproval(ApprovalLineDTO approvalLineDTO) {

        try{
            log.info("[ApprovalService] patchLineApproval 실행");
            int result = approvalMapper.patchLineApproval(approvalLineDTO);

            if( result == 0) {
                throw new Exception("수정 할 수 없습니다.");
            }
            return result;
        } catch (Exception e) {
            log.error("error 발생! 결재선 수정에 실패 했습니다. : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정할 수 없습니다.", e);
        }
    }
}
