package com.root34.aurora.addressBook.service;

import com.root34.aurora.addressBook.dao.AddressBookMapper;
import com.root34.aurora.addressBook.dto.AddressBookDTO;
import com.root34.aurora.addressBook.dto.AddressGroupDTO;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 @ClassName : AddressBookService
 @Date : 2023-03-20
 @Writer : 오승재
 @Description : 주소록 서비스 클래스
 */
@Service
@Slf4j
public class AddressBookService {

    private final AddressBookMapper addressBookMapper;

    public AddressBookService(AddressBookMapper addressBookMapper) {
        this.addressBookMapper = addressBookMapper;
    }

    public int selectTotalMemberAddresses() {

        log.info("[addressBookService] selectTotalMemberAddresses Start ===================================");
        int result = addressBookMapper.selectTotalMemberAddresses();

        log.info("[addressBookService] selectTotalMemberAddresses End ===================================");
        return result;
    }

    public List<AddressBookDTO> selectAllMemberAddresses(SelectCriteria selectCriteria) {

        log.info("[addressBookService] selectAllMemberAddresses Start ===================================");
        List<AddressBookDTO> addresses = addressBookMapper.selectAllMemberAddresses(selectCriteria);

        log.info("[addressBookService] selectAllMemberAddresses End ===================================");
        return addresses;
    }

    public int selectTotalMemberAddressesByDept(String deptCode) {

        log.info("[addressBookService] selectTotalMemberAddressesByDept Start ===================================");
        int result = addressBookMapper.selectTotalMemberAddressesByDept(deptCode);

        log.info("[addressBookService] selectTotalMemberAddressesByDept End ===================================");
        return result;
    }

    public List<AddressBookDTO> selectAllMemberAddressesByDept(Map map) {

        log.info("[addressBookService] selectAllMemberAddressesByDept Start ===================================");
        List<AddressBookDTO> addresses = addressBookMapper.selectAllMemberAddressesByDept(map);

        log.info("[addressBookService] selectAllMemberAddressesByDept End ===================================");
        return addresses;
    }

    public MemberDTO selectOneMemberAddress(int memberCode) {

        log.info("[addressBookService] selectOneMemberAddress Start ===================================");
        MemberDTO memberDTO = addressBookMapper.selectOneMemberAddress(memberCode);

        log.info("[addressBookService] selectOneMemberAddress End ===================================");
        return memberDTO;
    }

    @Transactional
    public String insertGroup(AddressGroupDTO addressGroupDTO) {

        log.info("[addressBookService] insertGroup Start ===================================");
        int result = addressBookMapper.insertGroup(addressGroupDTO);

        log.info("[addressBookService] insertGroup End ===================================");
        return (result > 0)? "그룹 추가 성공" : "그룹 추가 실패";
    }

    public int selectTotalGroupAddresses(String groupCode) {

        log.info("[addressBookService] selectTotalGroupAddresses Start ===================================");
        int result = addressBookMapper.selectTotalGroupAddresses(groupCode);

        log.info("[addressBookService] selectTotalGroupAddresses End ===================================");
        return result;
    }

    public List<AddressBookDTO> selectAllGroupAddresses(Map map) {

        log.info("[addressBookService] selectAllGroupAddresses Start ===================================");
        List<AddressBookDTO> list = addressBookMapper.selectAllGroupAddresses(map);

        log.info("[addressBookService] selectAllGroupAddresses End ===================================");
        return list;
    }

    @Transactional
    public String insertGroupAddress(AddressBookDTO addressBookDTO) {

        log.info("[addressBookService] insertGroupAddress Start ===================================");
        int result = addressBookMapper.insertGroupAddress(addressBookDTO);

        log.info("[addressBookService] insertGroupAddress End ===================================");
        return (result > 0)? "그룹 주소록 추가 성공" : "그룹 주소록 추가 실패";
    }

    @Transactional
    public String updateAddress(Map map) {

        log.info("[addressBookService] updateAddress Start ===================================");
        int result = addressBookMapper.updateAddress(map);

        log.info("[addressBookService] updateAddress End ===================================");
        return (result > 0)? "주소록 수정 성공" : "주소록 수정 실패";
    }

    @Transactional
    public String deleteAddress(String[] addBookNos) {

        log.info("[addressBookService] deleteAddress Start ===================================");
        int result = addressBookMapper.deleteAddress(addBookNos);

        log.info("[addressBookService] deleteAddress End ===================================");
        return (result > 0)? "주소록 삭제 성공" : "주소록 삭제 실패";
    }

    public List<AddressGroupDTO> selectPersonalGroups(int memberCode) {

        log.info("[addressBookService] selectPersonalGroups Start ===================================");
        List<AddressGroupDTO> list = addressBookMapper.selectPersonalGroups(memberCode);

        log.info("[addressBookService] selectPersonalGroups End ===================================");

        return list;
    }

    public List<AddressGroupDTO> selectTeamGroups(int memberCode) {

        log.info("[addressBookService] selectTeamGroups Start ===================================");
        List<AddressGroupDTO> list = addressBookMapper.selectTeamGroups(memberCode);

        log.info("[addressBookService] selectTeamGroups End ===================================");

        return list;
    }

    public int selectTotalMembersSearch(Map map) {

        log.info("[addressBookService] selectTotalMembersSearch Start ===================================");
        int totalNum = addressBookMapper.selectTotalMembersSearch(map);

        log.info("[addressBookService] selectTotalMembersSearch End ===================================");

        return totalNum;
    }

    public int selectTotalGroupsWithSearch(Map map) {

        log.info("[addressBookService] selectTotalGroupsWithSearch Start ===================================");
        int totalNum = addressBookMapper.selectTotalGroupsWithSearch(map);

        log.info("[addressBookService] selectTotalGroupsWithSearch End ===================================");

        return totalNum;
    }

    public List<AddressBookDTO> selectGroupsWithSearch(Map map) {

        log.info("[addressBookService] selectGroupsWithSearch Start ===================================");
        List<AddressBookDTO> list = addressBookMapper.selectGroupsWithSearch(map);

        log.info("[addressBookService] selectGroupsWithSearch End ===================================");

        return list;
    }

    public List<AddressBookDTO> selectMembersWithSearch(SelectCriteria selectCriteria) {

        log.info("[addressBookService] selectMembersWithSearch Start ===================================");
        List<AddressBookDTO> list = addressBookMapper.selectMembersWithSearch(selectCriteria);

        log.info("[addressBookService] selectMembersWithSearch End ===================================");

        return list;
    }

    @Transactional
    public String insertMemberToGroup(Map map) {

        log.info("[addressBookService] insertMemberToGroup Start ===================================");
        int result = addressBookMapper.insertMemberToGroup(map);

        log.info("[addressBookService] insertMemberToGroup End ===================================");

        return result > 0? "추가 성공" : "추가 실패";
    }

    @Transactional
    public String deleteGroup(String groupCode) {

        log.info("[addressBookService] deleteGroup Start ===================================");
        int result1 = addressBookMapper.deleteAddressInGroup(groupCode);

        log.info("[addressBookService] deleteAddressInGroup result1 : " + result1);

        int result2 = addressBookMapper.deleteGroup(groupCode);

        log.info("[addressBookService] deleteGroup End ===================================");

        return result2 > 0? "삭제 성공" : "삭제 실패";
    }

    @Transactional
    public String updateGroup(AddressGroupDTO addressGroupDTO) {

        log.info("[addressBookService] updateGroup Start ===================================");

        int result = addressBookMapper.updateGroup(addressGroupDTO);

        log.info("[addressBookService] updateGroup End ===================================");

        return result > 0? "수정 성공" : "수정 실패";
    }

    public AddressBookDTO selectAddressForUpdate(String addBookNo) {

        log.info("[addressBookService] selectAddressForUpdate Start ===================================");
        AddressBookDTO addressBookDTO = addressBookMapper.selectAddressForUpdate(addBookNo);

        log.info("[addressBookService] selectAddressForUpdate End ===================================");
        return addressBookDTO;
    }
}