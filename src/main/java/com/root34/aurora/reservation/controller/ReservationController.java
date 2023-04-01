package com.root34.aurora.reservation.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.reservation.dto.ReservationDTO;
import com.root34.aurora.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
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
		* @Description : 수정을 위한 내 예약 조회
	*/
	@GetMapping("/reservation/{reservationNo}")
	public ResponseEntity<ResponseDTO> selectReservationForUpdate(@PathVariable String reservationNo) {

		log.info("[ReservationController] selectReservationForUpdate start");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", reservationService.selectReservationForUpdate(reservationNo)));
	}
	
	/**
		* @MethodName : updateReservation
		* @Date : 2023-03-30
		* @Writer : 오승재
		* @Description : 내 예약 수정
	*/
	@PutMapping("/reservation/{reservationNo}")
	public ResponseEntity<ResponseDTO> updateReservation(@RequestBody ReservationDTO reservationDTO, @PathVariable String reservationNo) {

		reservationDTO.setReservationNo(reservationNo);
		log.info("[ReservationController] updateReservation start" + reservationDTO);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "예약 수정 성공", reservationService.updateReservation(reservationDTO)));
	}
	
	/**
		* @MethodName : deleteReservation
		* @Date : 2023-03-30
		* @Writer : 오승재
		* @Description : 내 예약 삭제
	*/
	@DeleteMapping("/reservation")
	public ResponseEntity<ResponseDTO> deleteReservation(@RequestBody JSONObject object) {

		String objectAsString = object.getAsString("reservationNos");
		String[] reservationNos = objectAsString.substring(1, objectAsString.length() - 1).split(", ");

		log.info("[ReservationController] deleteReservation : " + reservationNos);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "예약 취소 성공", reservationService.deleteReservation(reservationNos)));
	}
	
	/**
		* @MethodName : selectAllReservationsByAsset
		* @Date : 2023-03-31
		* @Writer : 오승재
		* @Description : 자산에 따른 예약 리스트 조회
	*/
	@GetMapping("/reservations/{assetCode}")
	public ResponseEntity<ResponseDTO> selectAllReservationsByAsset(@PathVariable String assetCode, @RequestParam String startTime, @RequestParam String endTime) {

		log.info("[ReservationController] selectAllReservationsByAsset start" + assetCode + startTime + endTime);

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-M-d", Locale.KOREA);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.KOREA);
		LocalDate startDate = LocalDate.parse(startTime, inputFormatter);
		LocalDate endDate = LocalDate.parse(endTime, inputFormatter);
		LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.of(0,0));
		LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23,59));
		Map map = new HashMap<>();
		map.put("assetCode", assetCode);
		map.put("startTime", outputFormatter.format(startDateTime));
		map.put("endTime", outputFormatter.format(endDateTime));

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "예약 조회 성공", reservationService.selectAllReservationsByAsset(map)));
	}

	/**
		* @MethodName : selectAllReservationsByDate
		* @Date : 2023-04-01
		* @Writer : 오승재
		* @Description : 날짜에 따른 예약 정보 조회
	*/
	@GetMapping("/reservations/{assetCode}/date")
	public ResponseEntity<ResponseDTO> selectAllReservationsByDate(@RequestParam String startDateTime, @RequestParam String endDateTime, @PathVariable String assetCode) {

		log.info("[ReservationController] selectAllReservationsByDate assetCode" + assetCode);

		Map map = new HashMap<>();
		map.put("assetCode", assetCode);
		map.put("startDateTime", startDateTime);
		map.put("endDateTime", endDateTime);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "예약 조회 성공", reservationService.selectAllReservationsByDate(map)));
	}

	/**
		* @MethodName : selectMemberInfoForRegister
		* @Date : 2023-04-01
		* @Writer : 오승재
		* @Description : 예약하기를 위한 사원 정보 조회
	*/
	@GetMapping("/reservation/member-info/{memberCode}")
	public ResponseEntity<ResponseDTO> selectMemberInfoForRegister(@PathVariable int memberCode) {

		log.info("[ReservationController] selectMemberInfoForRegister start" + memberCode);

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "정보 조회 성공", reservationService.selectMemberInfoForRegister(memberCode)));
	}

//	@PostMapping("/reservation")
//	public ResponseEntity<ResponseDTO> insertReservation(@RequestBody ReservationDTO reservationDTO) {
//
//		log.info("[ReservationController] insertReservation start" + reservationDTO);
//
//		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "예약 등록 성공", reservationService.insertReservation(reservationDTO)));
//	}
}
