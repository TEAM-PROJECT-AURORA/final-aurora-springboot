package com.root34.aurora.addressBook.dao;

import com.root34.aurora.FinalAuroraSpringbootApplication;
import com.root34.aurora.addressBook.dto.AddressBookDTO;
import com.root34.aurora.member.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(total == 0);
    }

    @Test
    void 주소록_전체_조회_매퍼_테스트() {

        // given

        // when
        List<MemberDTO> list = addressBookMapper.selectAllMemberAddresses();

        // then
        assertNotNull(list);
    }

    @Test
    void 부서별_주소록_조회_매퍼_테스트() {

        // given
        String deptCode = "DEV";

        // when
        List<MemberDTO> list = addressBookMapper.selectAllMemberAddressesByDept(deptCode);

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
    void 개인_주소록_전체_조회_매퍼_테스트() {

        // given
        int memberCode = 1;

        // when
        List<AddressBookDTO> list = addressBookMapper.selectAllPersonalAddresses(memberCode);

        // then
        System.out.println("list = " + list);
        assertNotNull(list);
    }

    @Test
    void 개인_주소록_추가_매퍼_테스트() {

        // given
        AddressBookDTO addressBookDTO = new AddressBookDTO();
        addressBookDTO.setName("허재홍");
        addressBookDTO.setPhone("010-2222-3333");
        addressBookDTO.setEmail("lupy@test.com");
        addressBookDTO.setCompany("뽀로로");
        addressBookDTO.setDepartment("루피담당");
        addressBookDTO.setComPhone("02-1111-2222");

        // when
        int result = addressBookMapper.insertPersonalAddress(addressBookDTO);

        // then
        assertTrue(result > 0);
    }

    @Test
    void 팀_주소록_전체_조회_매퍼_테스트() {

        // given
        String team = "개발1팀";

        // when
        List<AddressBookDTO> list = addressBookMapper.selectAllTeamAddresses(team);

        // then
        System.out.println("list = " + list);
        assertNotNull(list);
    }
}
