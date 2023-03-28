package com.root34.aurora.reservation.controller;

import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/reservation/asset-category")
	public ResponseEntity<ResponseDTO> selectAllAssetCategory() {

		log.info("[ReservationController] selectAllAssetCategory start");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", reservationService.selectAllAssetCategory()));
	}

	@GetMapping("/reservation/assets")
	public ResponseEntity<ResponseDTO> selectAllAssetsAvailable() {

		log.info("[ReservationController] selectAllAssetsAvailable start");

		return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", reservationService.selectAllAssetsAvailable()));
	}
}
