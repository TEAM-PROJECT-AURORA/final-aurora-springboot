package com.root34.aurora.vacation.dao;


import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
	@ClassName : VacationMapper
	@Date : 2023-03-25
	@Writer : 정근호
	@Description :
*/
@Mapper
public interface VacationMapper {

    Map selectVacation(int memberCode , int vacationNo);

	Map selectUsedVacation(int memberCode);

	void updateRemainVacation(int memberCode , int vacationNo);
}