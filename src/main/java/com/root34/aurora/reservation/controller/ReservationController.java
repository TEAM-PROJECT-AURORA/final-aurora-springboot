package com.root34.aurora.reservation.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.reservation.dto.ReservationDTO;
import com.root34.aurora.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
	@ClassName : ReservationController
	@Date : 2023-03-28
	@Writer : 오승재
	@Description : 예약 컨트롤러
*/
@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ReservationController {

    private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	/**
		* @MethodName : selectAllAssetCategory
		* @Date : 2023-03-30
		* @Writer : 오승재
		* @Description : 자산 카테고리 조회
	*/
	@GetMapping("/reservation/asset-category")
	public ResponseEntity<ResponseDTO> selectAllAssetCategory() {

		log.info("[ReservationController] selectAllAssetCategory start");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", reservationService.selectAllAssetCategory()));
	}

	/**
		* @MethodName : selectAllAssets
		* @Date : 2023-03-30
		* @Writer : 오승재
		* @Description : 모든 자산 리스트 조회
	*/
	@GetMapping("/reservation/assets")
	public ResponseEntity<ResponseDTO> selectAllAssets() {

		log.info("[ReservationController] selectAllAssets start");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", reservationService.selectAllAssets()));
	}

	/**
		* @MethodName : selectMyReservationWithPaging
		* @Date : 2023-03-30
		* @Writer : 오승재
		* @Description : 내 예약 조회
	*/
	@GetMapping("/reservation/my-reservation/{memberCode}")
	public ResponseEntity<ResponseDTO> selectMyReservationWithPaging(@RequestParam(name="offset", defaultValue="1") String offset, @PathVariable int memberCode) {

		log.info("[ReservationController] selectMyReservationWithPaging start");
		Map map = new HashMap();
		map.put("offset", offset);
		map.put("memberCode", memberCode);
		int totalCount = reservationService.selectTotalMyReservation(map);
		int limit = 20;
		int buttonAmount = 5;
		SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
		map.put("selectCriteria", selectCriteria);

		log.info("[AddressBookController] selectCriteria : " + selectCriteria);

		ResponseDTOWithPaging responseDTOWithPaging = new ResponseDTOWithPaging();
		responseDTOWithPaging.setPageInfo(selectCriteria);
		responseDTOWithPaging.setData(reservationService.selectAllMyReservation(map));

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDTOWithPaging));
	}

	/**
		* @MethodName : selectReservationForUpdate
		* @Date : 2023-03-30
		* @Writer : 오승재
		* @Description : 수정을 위한 예약 조회
	*/
	@GetMapping("/reservation/{reservationNo}")
	public ResponseEntity<ResponseDTO> selectReservationForUpdate(@PathVariable String reservationNo) {

		log.info("[ReservationController] selectReservationForUpdate start");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", reservationService.selectReservationForUpdate(reservationNo)));
	}

	@PutMapping("/reservation/{reservationNo}")
	public ResponseEntity<ResponseDTO> updateReservation(@RequestBody ReservationDTO reservationDTO, @PathVariable String reservationNo) {

		reservationDTO.setReservationNo(reservationNo);
		log.info("[ReservationController] updateReservation start" + reservationDTO);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "예약 수정 성공", reservationService.updateReservation(reservationDTO)));
	}
}
