package com.root34.aurora.reservation.service;

import com.root34.aurora.member.dto.MemberDTO;
import com.root34.aurora.reservation.dao.ReservationMapper;
import com.root34.aurora.reservation.dto.AssetDTO;
import com.root34.aurora.reservation.dto.ReservationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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

    @Transactional
    public String updateReservation(ReservationDTO reservationDTO) {

        log.info("[ReservationService] updateReservation Start ===================================");

        // 프론트에서 받은 시간 -> mysql datetime 형식으로 변환
        // 우리가 넣을 date 형식을 지정 - 뒤에 패턴을 잘맞춰야됨..
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy. M. d. a h:mm:ss", Locale.KOREA);
        // 넣은 date 를 어떤 형식으로 받을지 지정
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        // 형식 변환할 날짜 문자열과 지정한 inputFormatter 같이 넣어주고 날짜 형식으로 받은 다음
        // outputFormatter 로 string 으로 전환하는 듯
        // LocalDate 는 날짜만, 시간까지 받고 싶으면 LocalDateTime 사용
        LocalDateTime startDate = LocalDateTime.parse(reservationDTO.getStartTime(), inputFormatter);
        LocalDateTime endDate = LocalDateTime.parse(reservationDTO.getEndTime(), inputFormatter);
        reservationDTO.setStartTime(outputFormatter.format(startDate));
        reservationDTO.setEndTime(outputFormatter.format(endDate));
        log.info("[ReservationService] updateReservation 날짜 변환 = {}", reservationDTO);
        int result = reservationMapper.updateReservation(reservationDTO);

        log.info("[ReservationService] updateReservation End ===================================");
        return result > 0? "예약 수정 성공":"예약 수정 실패";
    }

    @Transactional
    public String deleteReservation(String[] reservationNos) {

        log.info("[ReservationService] deleteReservation Start ===================================");
        int result  = reservationMapper.deleteReservation(reservationNos);

        log.info("[ReservationService] deleteReservation End ===================================");
        return result > 0? "예약 삭제 성공" : "예약 삭제 실패";
    }

    public List<ReservationDTO> selectAllReservationsByAsset(Map map) {

        log.info("[ReservationService] selectAllReservationsByAsset Start ===================================");

        List<ReservationDTO> reservations = reservationMapper.selectAllReservationsByAsset(map);

        log.info("[ReservationService] selectAllReservationsByAsset End ===================================");
        return reservations;
    }

    public List<ReservationDTO> selectAllReservationsByDate(Map map) {

        log.info("[ReservationService] selectAllReservationsByDate Start ===================================");

        List<ReservationDTO> reservations = reservationMapper.selectAllReservationsByDate(map);

        log.info("[ReservationService] selectAllReservationsByDate End ===================================");
        return reservations;
    }

    public MemberDTO selectMemberInfoForRegister(int memberCode) {

        log.info("[ReservationService] selectMemberInfoForRegister Start ===================================");

        MemberDTO memberDTO = reservationMapper.selectMemberInfoForRegister(memberCode);

        log.info("[ReservationService] selectMemberInfoForRegister End ===================================");
        return memberDTO;
    }
    
    public String insertReservation(ReservationDTO reservationDTO) {

        log.info("[ReservationService] insertReservation Start ===================================");
        int result  = reservationMapper.insertReservation(reservationDTO);

        log.info("[ReservationService] insertReservation End ===================================");
        return result > 0? "예약 등록 성공" : "예약 등록 실패";
    }
}
