package com.root34.aurora.addressBook.dto;

import lombok.Data;

/**
	@ClassName : AddressbookDTO
	@Date : 2023-03-14
	@Writer : 오승재
	@Description : 주소록 정보를 담은 DTO
*/
@Data
public class AddressBookDTO {

    /**
     * @param addbookNo 주소록번호
     * @param groupCode 그룹 코드
     * @param groupName 그룹명
     * @param memberCode 사원 코드
     * @param team 소속팀
     * @param name 이름
     * @param phone 전화번호
     * @param email 이메일
     * @param company 회사
     * @param department 부서
     * @param comPhone 회사전화
     */
    private String addbookNo;
    private String groupCode;
    private String groupName;
    private int memberCode;
    private String team;
    private String name;
    private String phone;
    private String email;
    private String company;
    private String department;
    private String comPhone;

}
