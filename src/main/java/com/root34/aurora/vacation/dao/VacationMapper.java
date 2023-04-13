package com.root34.aurora.vacation.dao;


import com.root34.aurora.vacation.dto.VacationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
	@ClassName : VacationMapper
	@Date : 2023-03-25
	@Writer : 정근호
	@Description : 휴가 매퍼
*/
@Mapper
public interface VacationMapper {

    Map selectVacation(int memberCode);

	Map selectUsedVacation(int memberCode);

	void updateRemainVacation(int memberCode , int vacationNo);
	//vacation insert
    int insertVacation(int memberCode);

	//use_vacation insert
	int insertVacationUse(VacationDTO vacationDTO);

	int calculateRemainVacation(VacationDTO vacationDTO);

	List<VacationDTO> selectVacationDetail(int memberCode);
}
