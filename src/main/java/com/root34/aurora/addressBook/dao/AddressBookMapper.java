package com.root34.aurora.addressBook.dao;

import com.root34.aurora.addressBook.dto.AddressBookDTO;
import com.root34.aurora.addressBook.dto.AddressGroupDTO;
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

	List<AddressBookDTO> selectAllMemberAddresses(SelectCriteria selectCriteria);

	int selectTotalMemberAddressesByDept(String deptCode);

	List<AddressBookDTO> selectAllMemberAddressesByDept(Map map);

	MemberDTO selectOneMemberAddress(int memberCode);

	int insertGroup(AddressGroupDTO addressGroupDTO);

	int selectTotalGroupAddresses(String groupCode);

	List<AddressBookDTO> selectAllGroupAddresses(Map map);

	int insertGroupAddress(AddressBookDTO addressBookDTO);

	int updateAddress(Map map);

	int deleteAddress(String[] addBookNos);

	List<AddressGroupDTO> selectPersonalGroups(int memberCode);

	List<AddressGroupDTO> selectTeamGroups(int memberCode);

	int selectTotalMembersSearch(Map map);

	List<AddressBookDTO> selectMembersWithSearch(SelectCriteria selectCriteria);

	int selectTotalGroupsWithSearch(Map map);

	List<AddressBookDTO> selectGroupsWithSearch(Map map);

	int insertMemberToGroup(Map map);
}
