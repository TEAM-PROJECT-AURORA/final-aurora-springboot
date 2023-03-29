package com.root34.aurora.reservation.service;

import com.root34.aurora.reservation.dao.ReservationMapper;
import com.root34.aurora.reservation.dto.AssetDTO;
import com.root34.aurora.reservation.dto.ReservationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
	@ClassName : ReservationService
	@Date : 2023-03-28
	@Writer : 오승재
	@Description : 예약 서비스
*/
@Service
@Slf4j
public class ReservationService {

    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationMapper reservationMapper) {
        this.reservationMapper = reservationMapper;
    }

    public List<AssetDTO> selectAllAssetCategory() {

        log.info("[ReservationService] selectAllAssetCategory Start ===================================");
        List<AssetDTO> assets = reservationMapper.selectAllAssetCategory();

        log.info("[ReservationService] selectAllAssetCategory End ===================================");
        return assets;
    }

    public List<AssetDTO> selectAllAssets() {

        log.info("[ReservationService] selectAllAssets Start ===================================");
        List<AssetDTO> assets = reservationMapper.selectAllAssets();

        log.info("[ReservationService] selectAllAssets End ===================================");
        return assets;
    }

    public int selectTotalMyReservation(Map map) {

        log.info("[ReservationService] selectTotalMyReservation Start ===================================");
        int totalCount = reservationMapper.selectTotalMyReservation(map);

        log.info("[ReservationService] selectTotalMyReservation End ===================================");
        return totalCount;
    }

    public List<ReservationDTO> selectAllMyReservation(Map map) {

        log.info("[ReservationService] selectAllMyReservation Start ===================================");
        List<ReservationDTO> reservations = reservationMapper.selectAllMyReservation(map);

        log.info("[ReservationService] selectAllMyReservation End ===================================");
        return reservations;
    }

    public ReservationDTO selectReservationForUpdate(String reservationNo) {

        log.info("[ReservationService] selectReservationForUpdate Start ===================================");
        ReservationDTO reservation = reservationMapper.selectReservationForUpdate(reservationNo);

        log.info("[ReservationService] selectReservationForUpdate End ===================================");
        return reservation;
    }
}
