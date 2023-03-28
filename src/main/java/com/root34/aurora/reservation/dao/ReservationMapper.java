package com.root34.aurora.reservation.dao;

import com.root34.aurora.reservation.dto.AssetDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
	@ClassName : ReservationMapper
	@Date : 2023-03-28
	@Writer : 오승재
	@Description : 자산 매퍼
*/
@Mapper
public interface ReservationMapper {

	List<AssetDTO> selectAllAssetCategory();

    List<AssetDTO> selectAllAssetsAvailable();
}
