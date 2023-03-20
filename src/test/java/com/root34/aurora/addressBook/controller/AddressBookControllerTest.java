package com.root34.aurora.addressBook.controller;

import com.root34.aurora.FinalAuroraSpringbootApplication;
import com.root34.aurora.common.ResponseDTO;
import net.minidev.json.JSONObject;
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
        String groupName = "주소록 그룹 테스트2";
        JSONObject object = new JSONObject();
        object.put("groupName", groupName);

        // when
        ResponseEntity<ResponseDTO> response = addressBookController.insertGroup(object);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("그룹 추가 성공", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void 개인_주소록_전체_조회_컨트롤러_테스트() {

        // given
        String offset = "1";
        int memberCode = 1;

        // when
        ResponseEntity<ResponseDTO> response = addressBookController.selectAllPersonalAddressesWithPaging(offset, memberCode);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("조회 성공", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }

    @Test
    void 팀_주소록_전체_조회_컨트롤러_테스트() {

        // given
        String offset = "1";
        String team = "개발1팀";

        // when
        ResponseEntity<ResponseDTO> response = addressBookController.selectAllTeamAddressesWithPaging(offset, team);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("조회 성공", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
    }
}