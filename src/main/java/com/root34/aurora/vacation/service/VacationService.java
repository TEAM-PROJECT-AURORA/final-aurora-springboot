package com.root34.aurora.vacation.service;

import com.root34.aurora.exception.CreationFailedException;
import com.root34.aurora.vacation.dao.VacationMapper;
import com.root34.aurora.vacation.dto.VacationDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
	@ClassName : VacationService
	@Date : 2023-03-25
	@Writer : 정근호
	@Description :
*/
@Slf4j
@AllArgsConstructor
@Service
public class VacationService {

    private final VacationMapper vacationMapper;

    /**
     @MethodName : selectVacation
     @Date : 2023-04-05
     @Writer : 정근호
     @Description : 남은 휴가 조회
     */
    public Map selectVacation(int memberCode) {

        log.info("[VacationService] selectVacation Start ===================");
        log.info("[memberCode]   :" + memberCode );
        Map count = vacationMapper.selectVacation(memberCode);
        log.info("[count]   :"   + count);
        log.info("[VacationService] selectVacation End ==============================" );

        return count;
    }
    /**
     @MethodName : selectUsedVacation
     @Date : 2023-04-05
     @Writer : 정근호
     @Description : 사용한 휴가 조회
     */
    public Map selectUsedVacation(int memberCode) {

        log.info("[VacationService] selectUsedVacation Start ===================");
        log.info("[memberCode]   :" + memberCode);
        Map count = vacationMapper.selectUsedVacation(memberCode);
        log.info("[count]   :" + count);
        log.info("[VacationService] selectUsedVacation End ==============================");

        return count;
    }
    /**
     @MethodName : updateRemainVacation
     @Date : 2023-04-05
     @Writer : 정근호
     @Description : 사용한 휴가 계산해서 새로 휴가 수정
     */
    public void updateRemainVacation(int memberCode, int vacationNo ) {

        log.info("[VacationService] updateRemainVacation Start ===================");
        log.info("[memberCode]   :" + memberCode);
        log.info("[vacationCode]   :" + vacationNo);
        vacationMapper.updateRemainVacation(memberCode, vacationNo);
        log.info("[VacationService] updateRemainVacation End ==============================");
    }
    /**
     @MethodName : selectVacationDetail
     @Date : 2023-04-10
     @Writer : 정근호
     @Description : 휴가 상세 정보 조회
     */

    public List<VacationDTO> selectVacationDetail(int memberCode) {

        log.info("[VacationService] selectVacationDetail Start ===================");
        log.info("[memberCode]   :" + memberCode);
        List<VacationDTO> selectVacationDetail = vacationMapper.selectVacationDetail(memberCode);
        log.info("[VacationService] updateRemainVacation End ==============================");

        return selectVacationDetail;
    }

    public int insertVacation(int memberCode) {

        try{
            int result = vacationMapper.insertVacation(memberCode);

            if(result == 0) {
                throw new Exception("휴가를 신청할 수 없습니다.");
            }
            return result;
        } catch (Exception e) {
            log.error("[VacationService] insertVacation 에러 발생 ! ", e.getMessage());
            throw new CreationFailedException("웹 사이트 서버에 문제가 있습니다.",e);
        }
    }

    public int PostRemainVacation(VacationDTO vacationDTO) {
        log.info("[VacationService] : {}", vacationDTO);
        try{
            int result = vacationMapper.insertVacationUse(vacationDTO);
            log.info("[VacationService] : {}", vacationDTO);
            if(result == 0) {
                throw new Exception("휴가를 신청할 수 없습니다.");
            }
            return result;
        } catch (Exception e) {
            log.error("[VacationService] PostRemainVacation 에러 발생 ! ", e.getMessage());
            throw new CreationFailedException("웹 사이트 서버에 문제가 있습니다.");
        }
    }

    public int calculateRemainVacation(VacationDTO vacationDTO) {

        log.info("[calculateRemainVacation] : {}", vacationDTO);
        try{
            int result = vacationMapper.calculateRemainVacation(vacationDTO);
            if(result == 0) {
                throw new Exception("휴가를 신청할 수 없습니다.");
            }
            return result;
        } catch (Exception e) {
            log.error("[VacationService] calculateRemainVacation 에러 발생 ! ", e.getMessage());
            throw new CreationFailedException("웹 사이트 서버에 문제가 있습니다.",e);
        }
    }
}
