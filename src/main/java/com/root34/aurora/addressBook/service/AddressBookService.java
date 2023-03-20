package com.root34.aurora.addressBook.service;

import com.root34.aurora.addressBook.dao.AddressBookMapper;
import com.root34.aurora.addressBook.dto.AddressBookDTO;
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

    public List<MemberDTO> selectAllMemberAddresses(SelectCriteria selectCriteria) {

        log.info("[addressBookService] selectAllMemberAddresses Start ===================================");
        List<MemberDTO> addresses = addressBookMapper.selectAllMemberAddresses(selectCriteria);

        log.info("[addressBookService] selectAllMemberAddresses End ===================================");
        return addresses;
    }

    public int selectTotalMemberAddressesByDept(String deptCode) {

        log.info("[addressBookService] selectTotalMemberAddressesByDept Start ===================================");
        int result = addressBookMapper.selectTotalMemberAddressesByDept(deptCode);

        log.info("[addressBookService] selectTotalMemberAddressesByDept End ===================================");
        return result;
    }

    public List<MemberDTO> selectAllMemberAddressesByDept(Map map) {

        log.info("[addressBookService] selectAllMemberAddressesByDept Start ===================================");
        List<MemberDTO> addresses = addressBookMapper.selectAllMemberAddressesByDept(map);

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
    public int insertGroup(String groupName) {

        log.info("[addressBookService] insertGroup Start ===================================");
        int result = addressBookMapper.insertGroup(groupName);

        log.info("[addressBookService] insertGroup End ===================================");
        return result;
    }

    public int selectTotalPersonalAddresses(int MemberCode) {

        log.info("[addressBookService] selectTotalPersonalAddresses Start ===================================");
        int result = addressBookMapper.selectTotalPersonalAddresses(MemberCode);

        log.info("[addressBookService] selectTotalPersonalAddresses End ===================================");
        return result;
    }

    public List<AddressBookDTO> selectAllPersonalAddresses(Map map) {

        log.info("[addressBookService] selectAllPersonalAddresses Start ===================================");
        List<AddressBookDTO> list = addressBookMapper.selectAllPersonalAddresses(map);

        log.info("[addressBookService] selectAllPersonalAddresses End ===================================");
        return list;
    }

    @Transactional
    public int insertPersonalAddress(AddressBookDTO addressBookDTO) {

        log.info("[addressBookService] insertPersonalAddress Start ===================================");
        int result = addressBookMapper.insertPersonalAddress(addressBookDTO);

        log.info("[addressBookService] insertPersonalAddress End ===================================");
        return result;
    }

    public int selectTotalTeamAddresses(String team) {

        log.info("[addressBookService] selectTotalTeamAddresses Start ===================================");
        int result = addressBookMapper.selectTotalTeamAddresses(team);

        log.info("[addressBookService] selectTotalTeamAddresses End ===================================");
        return result;
    }

    public List<AddressBookDTO> selectAllTeamAddresses(Map map) {

        log.info("[addressBookService] selectAllTeamAddresses Start ===================================");
        List<AddressBookDTO> list = addressBookMapper.selectAllTeamAddresses(map);

        log.info("[addressBookService] selectAllTeamAddresses End ===================================");
        return list;
    }

    @Transactional
    public int insertTeamAddress(AddressBookDTO addressBookDTO) {

        log.info("[addressBookService] insertTeamAddress Start ===================================");
        int result = addressBookMapper.insertTeamAddress(addressBookDTO);

        log.info("[addressBookService] insertTeamAddress End ===================================");
        return result;
    }

    @Transactional
    public int updateAddress(AddressBookDTO addressBookDTO) {

        log.info("[addressBookService] updateAddress Start ===================================");
        int result = addressBookMapper.updateAddress(addressBookDTO);

        log.info("[addressBookService] updateAddress End ===================================");
        return result;
    }

    @Transactional
    public int deleteAddress(String addbookNo) {

        log.info("[addressBookService] deleteAddress Start ===================================");
        int result = addressBookMapper.deleteAddress(addbookNo);

        log.info("[addressBookService] deleteAddress End ===================================");
        return result;
    }
}
