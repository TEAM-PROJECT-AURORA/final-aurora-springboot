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

    public Map selectRemainVacation(int memberCode) {

        log.info("[AttendanceService] getAttendance Start ===================");
        log.info("[memberCode]   :" + memberCode );
        Map count = vacationMapper.selectRemainVacation(memberCode);
        log.info("[count]   :"   + count);
        log.info("[VacationService] selectRemainVacation End ==============================" );

        return count;
    }



}
