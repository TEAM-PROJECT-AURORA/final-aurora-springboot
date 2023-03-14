package com.root34.aurora.addressBook.dao;

import com.root34.aurora.addressBook.dto.AddressBookDTO;
import com.root34.aurora.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
	@ClassName : AddressBookMapper
	@Date : 2023-03-14
	@Writer : 오승재
	@Description : 주소록 매퍼 + TODO-페이징 처리 넣을 것
*/
@Mapper
public interface AddressBookMapper {

	int selectTotalMemberAddresses();

	List<MemberDTO> selectAllMemberAddresses();

	List<MemberDTO> selectAllMemberAddressesByDept(String deptCode);

	MemberDTO selectOneMemberAddress(int memberCode);

	List<AddressBookDTO> selectAllPersonalAddresses(int MemberCode);

	int insertPersonalAddress(AddressBookDTO addressBookDTO);

	List<AddressBookDTO> selectAllTeamAddresses(String team);

	int insertTeamAddress(AddressBookDTO addressBookDTO);
}
