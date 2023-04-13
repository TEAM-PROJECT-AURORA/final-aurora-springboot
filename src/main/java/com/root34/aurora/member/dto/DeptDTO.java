package com.root34.aurora.member.dto;

import lombok.Data;

/**
	@ClassName : DeptDTO
	@Date : 2023-04-01
	@Writer : 정근호
	@Description : 부서 변수
*/
@Data
public class DeptDTO {

	/**
	 * @variable deptCode 부서코드
	 **/
    private String deptCode;
	/**
	 * @variable deptName 부서이름
	 **/
    private String deptName;
}
