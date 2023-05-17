package com.root34.aurora.addressBook.dto;

import lombok.Data;

/**
	@ClassName : AddressGroupDTO
	@Date : 2023-03-22
	@Writer : 오승재
	@Description : 주소록 그룹 DTO
*/
@Data
public class AddressGroupDTO {
    
    /**
    	* @variable 그룹 코드 
    */
    private String groupCode;
    /**
    	* @variable 그룹명
    */
    private String groupName;
	/**
		* @variable 사원 코드 
	*/
	private int memberCode;
	/**
		* @variable 소속팀 코드
	*/
	private String teamCode;
}
