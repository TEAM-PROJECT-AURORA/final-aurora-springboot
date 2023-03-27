package com.root34.aurora.addressBook.service;

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
class AddressBookServiceTest {

    @Autowired
    private AddressBookService addressBookService;

    @Test
    void 주소록_서비스_의존성_주입_테스트() {

        assertNotNull(addressBookService);
    }


    @Test
    void 주소록_총개수_조회_서비스_테스트() {

        // given

        // when
        int result = addressBookService.selectTotalMemberAddresses();

        // then
        assertTrue(result > 0);
    }

    @Test
    void 주소록_전체_조회_서비스_테스트() {

        // given
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(1, 10, 10, 5);

        // when
        List<AddressBookDTO> list = addressBookService.selectAllMemberAddresses(selectCriteria);

        // then
        assertNotNull(list);
    }

    @Test
    void 부서별_주소록_조회_서비스_테스트() {

        // given
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(1, 10, 10, 5);
        Map map = new HashMap();
        map.put("selectCriteria", selectCriteria);
        map.put("deptCode", "DEV");

        // when
        List<AddressBookDTO> list = addressBookService.selectAllMemberAddressesByDept(map);

        // then
        assertNotNull(list);
    }

    @Test
    void 사내_주소록_상세_조회_서비스_테스트() {

        // given
        int memberCode = 1;

        // when
        MemberDTO memberDTO = addressBookService.selectOneMemberAddress(memberCode);

        // then
        assertNotNull(memberDTO);
    }

    @Test
    void 주소록_그룹_추가_서비스_테스트() {

        // given
        AddressGroupDTO addressGroupDTO = new AddressGroupDTO();
        addressGroupDTO.setGroupName("주소록 그룹 테스트3");
        addressGroupDTO.setMemberCode(1);

        // when
        String result = addressBookService.insertGroup(addressGroupDTO);

        // then
        assertEquals("그룹 추가 성공", result);
    }

    @Test
    void 그룹_주소록_총_개수_조회_서비스_테스트() {

        // given
        String groupCode = "1";

        // when
        int result = addressBookService.selectTotalGroupAddresses(groupCode);

        // then
        assertTrue(result > 0);
    }

    @Test
    void 그룹_주소록_전체_조회_서비스_테스트() {

        // given
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(1, 10, 10, 5);
        Map map = new HashMap();
        map.put("selectCriteria", selectCriteria);
        map.put("groupCode", "1");

        // when
        List<AddressBookDTO> list = addressBookService.selectAllGroupAddresses(map);

        // then
        System.out.println("list = " + list);
        assertNotNull(list);
    }

    @Test
    void 그룹_주소록_추가_서비스_테스트() {

        // given
        AddressBookDTO addressBookDTO = new AddressBookDTO();
        addressBookDTO.setGroupCode("1");
        addressBookDTO.setName("허재홍");
        addressBookDTO.setPhone("010-2222-3333");
        addressBookDTO.setEmail("lupy@test.com");
        addressBookDTO.setCompany("뽀로로");
        addressBookDTO.setDepartment("루피담당");
        addressBookDTO.setJobName("02-1111-2222");

        // when
        String  result = addressBookService.insertGroupAddress(addressBookDTO);

        // then
        assertEquals("그룹 주소록 추가 성공", result);
    }

    @Test
    void 주소록_수정_서비스_테스트() {

        // given
        AddressBookDTO address = new AddressBookDTO();
        address.setName("허카다시안");
        address.setPhone("010-1111-1111");
        address.setEmail("heoCasadian@test.com");
        address.setCompany("커다시안패밀리");
        address.setDepartment("허씨");
        address.setJobName("02-1111-1111");
        Map map = new HashMap();
        map.put("address", address);
        map.put("addBookNo", "3");

        // when
        String result = addressBookService.updateAddress(map);

        // then
        assertEquals("주소록 수정 성공", result);
    }

//    @Test
//    void 주소록_삭제_서비스_테스트() {
//
//        // given
//        String addBookNo = "2";
//
//        // when
//        String result = addressBookService.deleteAddress(addBookNo);
//
//        // then
//        assertEquals("주소록 삭제 성공", result);
//    }

    @Test
    void 개인_주소록_그룹_조회_서비스_테스트() {

        // given
        int memberCode = 1;

        // when
        List<AddressGroupDTO> list = addressBookService.selectPersonalGroups(memberCode);

        // then
        assertNotNull(list);
    }

    @Test
    void 팀_주소록_그룹_조회_서비스_테스트() {

        // given
        int memberCode = 1;

        // when
        List<AddressGroupDTO> list = addressBookService.selectTeamGroups(memberCode);

        // then
        assertNotNull(list);
    }
}