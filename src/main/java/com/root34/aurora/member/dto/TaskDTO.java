package com.root34.aurora.member.dto;

import lombok.Data;
/**
	@ClassName : TaskDTO
	@Date : 2023-04-01
	@Writer : 정근호
	@Description :직무 변수
*/
@Data
public class TaskDTO {
	/**
	 * @variable taskCode 직무코드
	 **/
    private String taskCode;
	/**
	 * @variable taskName 직무이름
	 **/
    private String taskName;
}
