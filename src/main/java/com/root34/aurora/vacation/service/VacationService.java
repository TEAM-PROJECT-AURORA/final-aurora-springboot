package com.root34.aurora.vacation.service;

import com.root34.aurora.vacation.dao.VacationMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public Map selectVacation(int memberCode , int vacationNo) {

        log.info("[VacationService] selectVacation Start ===================");
        log.info("[memberCode]   :" + memberCode );
        Map count = vacationMapper.selectVacation(memberCode , vacationNo);
        log.info("[count]   :"   + count);
        log.info("[VacationService] selectVacation End ==============================" );

        return count;
    }

    public Map selectUsedVacation(int memberCode) {

        log.info("[VacationService] selectUsedVacation Start ===================");
        log.info("[memberCode]   :" + memberCode);
        Map count = vacationMapper.selectUsedVacation(memberCode);
        log.info("[count]   :" + count);
        log.info("[VacationService] selectUsedVacation End ==============================");

        return count;
    }

    public void updateRemainVacation(int memberCode, int vacationNo ) {

        log.info("[VacationService] updateRemainVacation Start ===================");
        log.info("[memberCode]   :" + memberCode);
        log.info("[vacationCode]   :" + vacationNo);
        vacationMapper.updateRemainVacation(memberCode, vacationNo);
        log.info("[VacationService] updateRemainVacation End ==============================");
    }


}