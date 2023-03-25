package com.root34.aurora.addressBook.dao;

import com.root34.aurora.FinalAuroraSpringbootApplication;
import com.root34.aurora.addressBook.dto.AddressBookDTO;
import com.root34.aurora.addressBook.dto.AddressGroupDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.member.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = FinalAuroraSpringbootApplication.class)
class AddressBookMapperTest {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Test
    void 매퍼_인터페이스_의존성_주입_테스트() {

        assertNotNull(addressBookMapper);
    }

    @Test
    void 주소록_총개수_조회_매퍼_테스트() {

        // given

        // when
        int total = addressBookMapper.selectTotalMemberAddresses();

        // then
        assertTrue(total > 0);
    }

    @Test
    void 주소록_전체_조회_매퍼_테스트() {

        // given
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(1, 10, 10, 5);

        // when
        List<AddressBookDTO> list = addressBookMapper.selectAllMemberAddresses(selectCriteria);

        // then
        assertNotNull(list);
    }

    @Test
    void 부서별_주소록_총개수_조회_매퍼_테스트() {

        // given
        String deptCode = "DEV";

        // when
        int total = addressBookMapper.selectTotalMemberAddressesByDept(deptCode);

        // then
        assertTrue(total > 0);
    }

    @Test
    void 부서별_주소록_조회_매퍼_테스트() {

        // given
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(1, 10, 10, 5);
        Map map = new HashMap();
        map.put("selectCriteria", selectCriteria);
        map.put("deptCode", "DEV");

        // when
        List<AddressBookDTO> list = addressBookMapper.selectAllMemberAddressesByDept(map);

        // then
        assertNotNull(list);
    }

    @Test
    void 사내_주소록_상세_조회_매퍼_테스트() {

        // given
        int memberCode = 1;

        // when
        MemberDTO memberDTO = addressBookMapper.selectOneMemberAddress(memberCode);

        // then
        System.out.println("memberDTO = " + memberDTO);
        assertNotNull(memberDTO);
    }

    @Test
    void 주소록_그룹_추가_매퍼_테스트() {

        // given
        AddressGroupDTO addressGroupDTO = new AddressGroupDTO();
        addressGroupDTO.setGroupName("주소록 그룹 테스트4");
        addressGroupDTO.setMemberCode(1);


        // when
        int result = addressBookMapper.insertGroup(addressGroupDTO);

        // then
        assertEquals(1, result);
    }

    @Test
    void 그룹_주소록_총_개수_조회_매퍼_테스트() {

        // given
        String groupCode = "1";

        // when
        int result = addressBookMapper.selectTotalGroupAddresses(groupCode);

        // then
        assertTrue(result > 0);
    }
    
    @Test
    void 그룹_주소록_전체_조회_매퍼_테스트() {

        // given
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(1, 10, 10, 5);
        Map map = new HashMap();
        map.put("selectCriteria", selectCriteria);
        map.put("groupCode", "1");

        // when
        List<AddressBookDTO> list = addressBookMapper.selectAllGroupAddresses(map);

        // then
        System.out.println("list = " + list);
        assertNotNull(list);
    }

    @Test
    void 개인_주소록_추가_매퍼_테스트() {

        // given
        AddressBookDTO addressBookDTO = new AddressBookDTO();
        addressBookDTO.setGroupCode("1");
        addressBookDTO.setName("허재홍");
        addressBookDTO.setPhone("010-2222-3333");
        addressBookDTO.setEmail("lupy@test.com");
        addressBookDTO.setCompany("뽀로로");
        addressBookDTO.setDepartment("루피담당");
        addressBookDTO.setComPhone("02-1111-2222");

        // when
        int result = addressBookMapper.insertGroupAddress(addressBookDTO);

        // then
        assertEquals(1, result);
    }

    @Test
    void 주소록_수정_매퍼_테스트() {

        // given
        AddressBookDTO address = new AddressBookDTO();
        address.setName("허킴");
        address.setPhone("010-1111-1111");
        address.setEmail("heoCadasian@test.com");
        address.setCompany("커다시안패밀리");
        address.setDepartment("허씨");
        address.setComPhone("02-1111-1111");
        Map map = new HashMap();
        map.put("address", address);
        map.put("addBookNo", "3");

        // when
        int result = addressBookMapper.updateAddress(map);

        // then
        assertEquals(1, result);
    }

//    @Test
//    void 주소록_삭제_매퍼_테스트() {
//
//        // given
//        String addBookNo = "1";
//
//        // when
//        int result = addressBookMapper.deleteAddress(addBookNo);
//
//        // then
//        assertEquals(1, result);
//    }

    @Test
    void 개인_주소록_그룹_조회_매퍼_테스트() {

        // given
        int memberCode = 1;

        // when
        List<AddressGroupDTO> list = addressBookMapper.selectPersonalGroups(memberCode);

        // then
        assertNotNull(list);
    }

    @Test
    void 팀_주소록_그룹_조회_매퍼_테스트() {

        // given
        int memberCode = 1;

        // when
        List<AddressGroupDTO> list = addressBookMapper.selectTeamGroups(memberCode);

        // then
        assertNotNull(list);
    }

    @Test
    void 사원_검색_총개수_조회_매퍼_테스트() {

        // given
//        Map map = new HashMap();
//        map.put("")

        // when

        // then
    }
}
