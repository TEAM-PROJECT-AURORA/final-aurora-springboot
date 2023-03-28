package com.root34.aurora.reservation.dto;

import lombok.Data;

/**
	@ClassName : AssetDTO
	@Date : 2023-03-28
	@Writer : 오승재
	@Description : 자산 정보
*/
@Data
public class AssetDTO {

    /**
    	* @variable 자산 코드 
    */
    private String assetCode;
    /**
    	* @variable 자산명 
    */
    private String assetName;
    /**
    	* @variable 자산 설명 
    */
    private String assetCategory;
    /**
    	* @variable 예약가능여부
    */
    private String status;
}
