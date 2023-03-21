package com.root34.aurora.addressBook.dao;

import com.root34.aurora.addressBook.dto.AddressBookDTO;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
	@ClassName : AddressBookMapper
	@Date : 2023-03-14
	@Writer : 오승재
	@Description : 주소록 매퍼
*/
@Mapper
public interface AddressBookMapper {

	int selectTotalMemberAddresses();

	List<MemberDTO> selectAllMemberAddresses(SelectCriteria selectCriteria);

	int selectTotalMemberAddressesByDept(String deptCode);

	List<MemberDTO> selectAllMemberAddressesByDept(Map map);

	MemberDTO selectOneMemberAddress(int memberCode);

	int insertGroup(String groupName);

	int selectTotalPersonalAddresses(int MemberCode);

	List<AddressBookDTO> selectAllPersonalAddresses(Map map);

	int insertPersonalAddress(AddressBookDTO addressBookDTO);

	int selectTotalTeamAddresses(String team);

	List<AddressBookDTO> selectAllTeamAddresses(Map map);

	int insertTeamAddress(AddressBookDTO addressBookDTO);

	int updateAddress(Map map);

	int deleteAddress(String addbookNo);
}
