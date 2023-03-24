package com.root34.aurora.addressBook.controller;

import com.root34.aurora.FinalAuroraSpringbootApplication;
import com.root34.aurora.addressBook.dto.AddressBookDTO;
import com.root34.aurora.addressBook.dto.AddressGroupDTO;
import com.root34.aurora.common.ResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes = FinalAuroraSpringbootApplication.class)
class AddressBookControllerTest {

    @Autowired
    private AddressBookController addressBookController;

    @Test
    void 주소록_전체_조회_컨트롤러_테스트() {

        // given
        String offset = "1";

        // when
        ResponseEntity<ResponseDTO> response = addressBookController.selectAllMemberAddressesWithPaging(offset);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("조회 성공", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void 부서별_주소록_전체_조회_컨트롤러_테스트() {

        // given
        String offset = "1";
        String deptCode = "DEV";

        // when
        ResponseEntity<ResponseDTO> response = addressBookController.selectAllMemberAddressesByDeptWithPaging(offset, deptCode);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("조회 성공", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void 주소록_상세_조회_컨트롤러_테스트() {

        // given
        int memberCode = 1;

        // when
        ResponseEntity<ResponseDTO> response = addressBookController.selectOneMemberAddress(memberCode);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("조회 성공", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void 주소록_그룹_추가_컨트롤러_테스트() {

        // given
        AddressGroupDTO addressGroupDTO = new AddressGroupDTO();
        addressGroupDTO.setGroupName("주소록 그룹 테스트2");
        addressGroupDTO.setMemberCode(1);

        // when
        ResponseEntity<ResponseDTO> response = addressBookController.insertGroup(addressGroupDTO);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("그룹 추가 성공", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void 그룹_주소록_전체_조회_컨트롤러_테스트() {

        // given
        String offset = "1";
        String groupCode = "1";

        // when
        ResponseEntity<ResponseDTO> response = addressBookController.selectAllGroupAddressesWithPaging(offset, groupCode);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("조회 성공", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void 그룹_주소록_추가_컨트롤러_테스트() {

        // given
        AddressBookDTO addressBookDTO = new AddressBookDTO();
        addressBookDTO.setName("킹수용");
        addressBookDTO.setGroupCode("1");
        addressBookDTO.setPhone("010-2222-4444");
        addressBookDTO.setEmail("test3@test.com");
        addressBookDTO.setCompany("오컴퍼니");
        addressBookDTO.setDepartment("개발부");
        addressBookDTO.setComPhone("02-111-2223");

        // when
        ResponseEntity<ResponseDTO> response = addressBookController.insertGroupAddress(addressBookDTO);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("그룹 주소록 추가 성공", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void 주소록_수정_컨트롤러_테스트() {

        // given
        AddressBookDTO address = new AddressBookDTO();
        address.setName("허카다시안");
        address.setPhone("010-1111-1111");
        address.setEmail("heoCasadian@test.com");
        address.setCompany("커다시안패밀리");
        address.setDepartment("허씨");
        address.setComPhone("02-1111-1111");
        String addBookNo = "4";

        // when
        ResponseEntity<ResponseDTO> response = addressBookController.updateAddress(address, addBookNo);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("주소록 수정 성공", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

//    @Test
//    void 주소록_삭제_컨트롤러_테스트() {
//
//        // given
//        String addBookNo = "4";
//
//        // when
//        ResponseEntity<ResponseDTO> response = addressBookController.deleteAddress(addBookNo);
//
//        // then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("주소록 삭제 성공", response.getBody().getMessage());
//        assertNotNull(response.getBody().getData());
//    }

    @Test
    void 개인_주소록_그룹_조회_컨트롤러_테스트() {

        // given
        int memberCode = 1;

        // when
        ResponseEntity<ResponseDTO> response = addressBookController.selectPersonalGroups(memberCode);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("개인 그룹 조회 성공", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void 팀_주소록_그룹_조회_컨트롤러_테스트() {

        // given
        int memberCode = 1;

        // when
        ResponseEntity<ResponseDTO> response = addressBookController.selectTeamGroups(memberCode);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("팀 그룹 조회 성공", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }
}