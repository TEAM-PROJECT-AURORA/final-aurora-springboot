package com.root34.aurora.addressBook.dto;

import lombok.Data;

/**
	@ClassName : AddressBookDTO
	@Date : 2023-03-14
	@Writer : 오승재
	@Description : 주소록 정보를 담은 DTO
*/
@Data
public class AddressBookDTO {

    /**
    	* @variable 주소록번호
    */
    private String addBookNo;
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
    	* @variable 소속팀 
    */
    private String team;
    /**
    	* @variable 이름 
    */
    private String name;
    /**
    	* @variable 전화번호 
    */
    private String phone;
    /**
    	* @variable 이메일 
    */
    private String email;
    /**
    	* @variable 회사
    */
    private String company;
    /**
    	* @variable 부서 
    */
    private String department;
    /**
    	* @variable 직급
    */
    private String jobName;
}
