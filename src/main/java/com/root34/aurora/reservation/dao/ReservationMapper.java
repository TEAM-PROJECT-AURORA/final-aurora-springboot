package com.root34.aurora.reservation.dao;

import com.root34.aurora.reservation.dto.AssetDTO;
import com.root34.aurora.reservation.dto.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
	@ClassName : ReservationMapper
	@Date : 2023-03-28
	@Writer : 오승재
	@Description : 자산 매퍼
*/
@Mapper
public interface ReservationMapper {

	List<AssetDTO> selectAllAssetCategory();

    List<AssetDTO> selectAllAssets();

	int selectTotalMyReservation(Map map);

	List<ReservationDTO> selectAllMyReservation(Map map);

	ReservationDTO selectReservationForUpdate(String reservationNo);

	int updateReservation(ReservationDTO reservationDTO);

	int deleteReservation(String[] reservationNos);

	List<ReservationDTO> selectAllReservationsByAsset(Map map);
}
