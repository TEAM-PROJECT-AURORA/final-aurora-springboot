package com.root34.aurora.vacation.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.vacation.service.VacationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
	@ClassName : VacationController
	@Date : 2023-03-25
	@Writer : 정근호
	@Description :
*/
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class VacationController {

    private final VacationService vacationService;

	@GetMapping("/vacation/{memberCode}")
	public ResponseEntity<ResponseDTO> selectRemainVacation(@PathVariable int memberCode) {

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료" , vacationService.selectRemainVacation(memberCode)));
	}

}
