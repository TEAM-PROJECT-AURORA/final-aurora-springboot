package com.root34.aurora.vacation.controller;

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

	/**
	 @MethodName : selectRemainVacation
	 @Date : 2023-04-05
	 @Writer : 정근호
	 @Description : 남은 휴가 조회
	 */
	@GetMapping("/vacation/{memberCode}")
	public ResponseEntity<ResponseDTO> selectRemainVacation(@PathVariable int memberCode) {

		vacationService.selectVacation(memberCode);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료" , vacationService.selectVacation(memberCode)));
	}
	/**
	 @MethodName : selectUsedVacation
	 @Date : 2023-04-05
	 @Writer : 정근호
	 @Description : 사용한 휴가 조회
	 */

	@GetMapping("/vacation/used/{memberCode}")
	public ResponseEntity<ResponseDTO> selectUsedVacation(@PathVariable int memberCode) {

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료" ,vacationService.selectUsedVacation(memberCode)));
	}
	/**
	 @MethodName : updateRemainVacation
	 @Date : 2023-04-05
	 @Writer : 정근호
	 @Description : 사용한 휴가 계산해서 새로 휴가 수정
	 */
	@PutMapping("/vacation/remain/{memberCode}")
	public ResponseEntity<ResponseDTO> updateRemainVacation(@PathVariable int memberCode , @RequestBody VacationDTO vacationDTO) {

		vacationService.calculateRemainVacation(vacationDTO);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "수정 완료", vacationService.selectVacation(memberCode)));
	}

	@PostMapping("/vacation/remain/{memberCode}")
	public ResponseEntity<ResponseDTO> PostRemainVacation(@PathVariable int memberCode , @RequestBody @Valid VacationDTO vacationDTO) {
		vacationDTO.setMemberCode(memberCode);
		log.info("[VacationController] PostRemainVacation start" + vacationDTO);
		vacationService.PostRemainVacation(vacationDTO);
		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "등록 성공", vacationService.selectVacation(memberCode)));
	}

	@PostMapping("/vacation/{memberCode}")
	public ResponseEntity<ResponseDTO> insertRemainVacation(@PathVariable int memberCode) {

		vacationService.insertVacation(memberCode);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "등록 완료" , vacationService.selectVacation(memberCode)));
	}
	/**
	 @MethodName : selectVacationDetail
	 @Date : 2023-04-10
	 @Writer : 정근호
	 @Description : 휴가 상세 정보 조회
	 */
	@GetMapping("/vacation/detail/{memberCode}")
	public ResponseEntity<ResponseDTO> selectVacationDetail(@PathVariable int memberCode) {

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회완료", vacationService.selectVacationDetail(memberCode)));


	}

}
