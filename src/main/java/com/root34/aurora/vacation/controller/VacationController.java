package com.root34.aurora.vacation.controller;

import com.root34.aurora.approval.dto.ApprovalDTO;
import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.vacation.dto.VacationDTO;
import com.root34.aurora.vacation.service.VacationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

//	@GetMapping("/vacation/{memberCode}")
//	public ResponseEntity<ResponseDTO> selectRemainVacation(@PathVariable int memberCode , @RequestParam( name= "vacationNo") int vacationNo ) {
//
//		vacationService.selectVacation( memberCode ,vacationNo);
//
//		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료" , vacationService.selectVacation(memberCode , vacationNo)));
//	}
	@GetMapping("/vacation/{memberCode}")
	public ResponseEntity<ResponseDTO> selectRemainVacation(@PathVariable int memberCode) {

		vacationService.selectVacation(memberCode);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료" , vacationService.selectVacation(memberCode)));
	}


	@GetMapping("/vacation/used/{memberCode}")
	public ResponseEntity<ResponseDTO> selectUsedVacation(@PathVariable int memberCode) {

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료" ,vacationService.selectUsedVacation(memberCode)));
	}

	@PutMapping("/vacation/remain/{memberCode}")
	public ResponseEntity<ResponseDTO> updateRemainVacation(@PathVariable int memberCode , @RequestBody VacationDTO vacationDTO) {

		vacationService.calculateRemainVacation(vacationDTO);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "수정 완료", vacationService.selectVacation(memberCode)));
	}

	@PostMapping("/vacation/remain/{memberCode}")
	public ResponseEntity<ResponseDTO> PostRemainVacation(@PathVariable int memberCode , @RequestBody @Valid VacationDTO vacationDTO) {
		vacationDTO.setMemberCode(memberCode);
		vacationService.PostRemainVacation(vacationDTO);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "등록 성공", vacationService.selectVacation(memberCode)));
	}

	@PostMapping("/vacation/{memberCode}")
	public ResponseEntity<ResponseDTO> insertRemainVacation(@PathVariable int memberCode) {

		vacationService.insertVacation(memberCode);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "등록 완료" , vacationService.selectVacation(memberCode)));
	}
}
