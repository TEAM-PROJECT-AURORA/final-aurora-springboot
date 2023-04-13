package com.root34.aurora.vacation.service;

import com.root34.aurora.vacation.dao.VacationMapper;
import com.root34.aurora.vacation.dto.VacationDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
