package com.root34.aurora.addressBook.service;

import com.root34.aurora.FinalAuroraSpringbootApplication;
import com.root34.aurora.addressBook.dto.AddressBookDTO;
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
        List<MemberDTO> list = addressBookService.selectAllMemberAddresses(selectCriteria);

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
        List<MemberDTO> list = addressBookService.selectAllMemberAddressesByDept(map);

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
        System.out.println("memberDTO = " + memberDTO);
        assertNotNull(memberDTO);
    }

    @Test
    void 주소록_그룹_추가_서비스_테스트() {

        // given
        String team = "개발1팀";

        // when
        int result = addressBookService.insertGroup(team);

        // then
        assertEquals(1, result);
    }

    @Test
    void 개인_주소록_총_개수_조회_서비스_테스트() {

        // given
        int memberCode = 1;

        // when
        int result = addressBookService.selectTotalPersonalAddresses(memberCode);

        // then
        assertTrue(result > 0);
    }

    @Test
    void 개인_주소록_전체_조회_서비스_테스트() {

        // given
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(1, 10, 10, 5);
        Map map = new HashMap();
        map.put("selectCriteria", selectCriteria);
        map.put("memberCode", 1);

        // when
        List<AddressBookDTO> list = addressBookService.selectAllPersonalAddresses(map);

        // then
        System.out.println("list = " + list);
        assertNotNull(list);
    }

    @Test
    void 개인_주소록_추가_서비스_테스트() {

        // given
        AddressBookDTO addressBookDTO = new AddressBookDTO();
        addressBookDTO.setGroupCode("1");
        addressBookDTO.setMemberCode(1);
        addressBookDTO.setName("허재홍");
        addressBookDTO.setPhone("010-2222-3333");
        addressBookDTO.setEmail("lupy@test.com");
        addressBookDTO.setCompany("뽀로로");
        addressBookDTO.setDepartment("루피담당");
        addressBookDTO.setComPhone("02-1111-2222");

        // when
        int result = addressBookService.insertPersonalAddress(addressBookDTO);

        // then
        assertEquals(1, result);
    }

    @Test
    void 팀_주소록_총_개수_조회_서비스_테스트() {

        // given
        String team = "개발1팀";

        // when
        int result = addressBookService.selectTotalTeamAddresses(team);

        // then
        assertTrue(result > 0);
    }

    @Test
    void 팀_주소록_전체_조회_서비스_테스트() {

        // given
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(1, 10, 10, 5);
        Map map = new HashMap();
        map.put("selectCriteria", selectCriteria);
        map.put("team", "개발1팀");

        // when
        List<AddressBookDTO> list = addressBookService.selectAllTeamAddresses(map);

        // then
        System.out.println("list = " + list);
        assertNotNull(list);
    }

    @Test
    void 팀공용_주소록_추가_서비스_테스트() {

        // given
        AddressBookDTO addressBookDTO = new AddressBookDTO();
        addressBookDTO.setGroupCode("1");
        addressBookDTO.setTeam("개발1팀");
        addressBookDTO.setName("허재홍");
        addressBookDTO.setPhone("010-2222-3333");
        addressBookDTO.setEmail("lupy@test.com");
        addressBookDTO.setCompany("뽀로로");
        addressBookDTO.setDepartment("루피담당");
        addressBookDTO.setComPhone("02-1111-2222");

        // when
        int result = addressBookService.insertTeamAddress(addressBookDTO);

        // then
        assertEquals(1, result);
    }

    @Test
    void 주소록_수정_서비스_테스트() {

        // given
        AddressBookDTO addressBookDTO = new AddressBookDTO();
        addressBookDTO.setName("허카다시안");
        addressBookDTO.setPhone("010-1111-1111");
        addressBookDTO.setEmail("heoCasadian@test.com");
        addressBookDTO.setCompany("커다시안패밀리");
        addressBookDTO.setDepartment("허씨");
        addressBookDTO.setComPhone("02-1111-1111");
        addressBookDTO.setAddbookNo("2");

        // when
        int result = addressBookService.updateAddress(addressBookDTO);

        // then
        assertEquals(1, result);
    }

    @Test
    void 주소록_삭제_서비스_테스트() {

        // given
        String addbookNo = "2";

        // when
        int result = addressBookService.deleteAddress(addbookNo);

        // then
        assertEquals(1, result);
    }
}