package com.root34.aurora.addressBook.controller;

import com.root34.aurora.addressBook.dto.AddressBookDTO;
import com.root34.aurora.addressBook.dto.AddressGroupDTO;
import com.root34.aurora.addressBook.service.AddressBookService;
import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class AddressBookController {

    private final AddressBookService addressBookService;

    public AddressBookController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    /**
    	* @MethodName : selectAllMemberAddressesWithPaging
    	* @Date : 2023-03-20
    	* @Writer : 오승재
    	* @Description : 전체 사원 주소록 조회
    */
    @GetMapping("/address-book/addresses")
    public ResponseEntity<ResponseDTO> selectAllMemberAddressesWithPaging(@RequestParam(name="offset", defaultValue="1") String offset) {

        log.info("[AddressBookController] selectAllMemberAddressesWithPaging : " + offset);
        int totalCount = addressBookService.selectTotalMemberAddresses();
        int limit = 20;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);

        log.info("[AddressBookController] selectCriteria : " + selectCriteria);

        ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(addressBookService.selectAllMemberAddresses(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }

    /**
    	* @MethodName : selectAllMemberAddressesByDeptWithPaging
    	* @Date : 2023-03-20
    	* @Writer : 오승재
    	* @Description : 부서별 전체 사원 주소록 조회
    */
    @GetMapping("/address-book/department/{deptCode}")
    public ResponseEntity<ResponseDTO> selectAllMemberAddressesByDeptWithPaging(@RequestParam(name="offset", defaultValue="1") String offset, @PathVariable String deptCode) {

        log.info("[AddressBookController] selectAllMemberAddressesByDeptWithPaging : " + offset);
        int totalCount = addressBookService.selectTotalMemberAddressesByDept(deptCode);
        int limit = 20;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
        Map map = new HashMap();
        map.put("selectCriteria", selectCriteria);
        map.put("deptCode", deptCode);

        log.info("[AddressBookController] selectCriteria : " + selectCriteria);

        ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(addressBookService.selectAllMemberAddressesByDept(map));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }

    /**
    	* @MethodName : selectOneMemberAddress
    	* @Date : 2023-03-20
    	* @Writer : 오승재
    	* @Description : 사원 상세 조회
    */
    @GetMapping("/address-book/addresses/{memberCode}")
    public ResponseEntity<ResponseDTO> selectOneMemberAddress(@PathVariable int memberCode) {

        log.info("[AddressBookController] selectOneMemberAddress : " + memberCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", addressBookService.selectOneMemberAddress(memberCode)));
    }

    /**
    	* @MethodName : insertGroup
    	* @Date : 2023-03-20
    	* @Writer : 오승재
    	* @Description : 그룹 추가
    */
    @PostMapping("/address-book/group")
    public ResponseEntity<ResponseDTO> insertGroup(@RequestBody AddressGroupDTO addressGroupDTO) {

        log.info("[AddressBookController] insertGroup : " + addressGroupDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "그룹 추가 성공", addressBookService.insertGroup(addressGroupDTO)));
    }

    /**
    	* @MethodName : selectAllPersonalAddressesWithPaging
    	* @Date : 2023-03-21
    	* @Writer : 오승재
    	* @Description : 그룹 주소록 전체 조회
    */
    @GetMapping("/address-book/groups/{groupCode}")
    public ResponseEntity<ResponseDTO> selectAllGroupAddressesWithPaging(@RequestParam String offset, @PathVariable String groupCode) {

        log.info("[AddressBookController] selectAllGroupAddressesWithPaging : " + offset);
        int totalCount = addressBookService.selectTotalGroupAddresses(groupCode);
        int limit = 20;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
        Map map = new HashMap();
        map.put("selectCriteria", selectCriteria);
        map.put("groupCode", groupCode);

        log.info("[AddressBookController] selectCriteria : " + selectCriteria);
        log.info("[AddressBookController] totalCount : " + totalCount);

        ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(addressBookService.selectAllGroupAddresses(map));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }

    /**
    	* @MethodName : insertPersonalAddress
    	* @Date : 2023-03-21
    	* @Writer : 오승재
    	* @Description : 그룹 주소록 추가
    */
    @PostMapping("/address-book/groups")
    public ResponseEntity<ResponseDTO> insertGroupAddress(@RequestBody AddressBookDTO addressBookDTO) {

        log.info("[AddressBookController] insertGroupAddress : " + addressBookDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "그룹 주소록 추가 성공", addressBookService.insertGroupAddress(addressBookDTO)));
    }

    /**
    	* @MethodName : updateAddress
    	* @Date : 2023-03-21
    	* @Writer : 오승재
    	* @Description : 주소록 수정
    */
    @PutMapping("/address-book/{addBookNo}")
    public ResponseEntity<ResponseDTO> updateAddress(@RequestBody AddressBookDTO address, @PathVariable String addBookNo) {

        log.info("[AddressBookController] updateAddress : " + address);
        Map map = new HashMap();
        map.put("address", address);
        map.put("addBookNo", addBookNo);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "주소록 수정 성공", addressBookService.updateAddress(map)));
    }

    /**
    	* @MethodName : deleteAddress
    	* @Date : 2023-03-21
    	* @Writer : 오승재
    	* @Description : 주소록 삭제
    */
    @DeleteMapping("/address-book/groups")
    public ResponseEntity<ResponseDTO> deleteAddress(@RequestBody JSONObject object) {

        String objectAsString = object.getAsString("addBookNos");
        String[] addBookNos = objectAsString.substring(1, objectAsString.length() - 1).split(", ");

        log.info("[AddressBookController] deleteAddress : " + addBookNos);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "주소록 삭제 성공", addressBookService.deleteAddress(addBookNos)));
    }

    /**
    	* @MethodName : selectPersonalGroups
    	* @Date : 2023-03-22
    	* @Writer : 오승재
    	* @Description : 개인 주소록 그룹 조회
    */
    @GetMapping("/address-book/personal-groups/{memberCode}")
    public ResponseEntity<ResponseDTO> selectPersonalGroups(@PathVariable int memberCode) {

        log.info("[AddressBookController] selectPersonalGroups : " + memberCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "개인 그룹 조회 성공", addressBookService.selectPersonalGroups(memberCode)));
    }

    /**
    	* @MethodName : selectTeamGroups
    	* @Date : 2023-03-23
    	* @Writer : 오승재
    	* @Description : 팀 주소록 그룹 조회
    */
    @GetMapping("/address-book/team-groups/{memberCode}")
    public ResponseEntity<ResponseDTO> selectTeamGroups(@PathVariable int memberCode) {

        log.info("[AddressBookController] selectTeamGroups : " + memberCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "팀 그룹 조회 성공", addressBookService.selectTeamGroups(memberCode)));
    }

    /**
    	* @MethodName : selectMembersWithSearch
    	* @Date : 2023-03-25
    	* @Writer : 오승재
    	* @Description : 검색으로 사원 주소록 조회
    */
    @GetMapping("address-book/search")
    public ResponseEntity<ResponseDTO> selectMembersWithSearch(@RequestParam(name="offset", defaultValue="1") String offset, String searchCondition, String searchValue) {

        log.info("[AddressBookController] selectMembersWithSearch : " + searchCondition + searchValue);

        Map map = new HashMap();
        map.put("searchCondition", searchCondition);
        map.put("searchValue", searchValue);

        int totalCount = addressBookService.selectTotalMembersSearch(map);
        int limit = 20;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
        selectCriteria.setSearchCondition(searchCondition);
        selectCriteria.setSearchValue(searchValue);

        log.info("[AddressBookController] selectCriteria : " + selectCriteria);

        ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(addressBookService.selectMembersWithSearch(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "검색 조회 성공", responseDtoWithPaging));
    }

    /**
    	* @MethodName : selectGroupsWithSearch
    	* @Date : 2023-03-26
    	* @Writer : 오승재
    	* @Description : 검색으로 그룹 주소록 조회
    */
    @GetMapping("address-book/groups/{groupCode}/search")
    public ResponseEntity<ResponseDTO> selectGroupsWithSearch(@RequestParam(name="offset", defaultValue="1") String offset, String searchCondition, String searchValue, @PathVariable String groupCode) {

        log.info("[AddressBookController] selectGroupsWithSearch : " + searchCondition + searchValue + groupCode);

        Map map = new HashMap();
        map.put("searchCondition", searchCondition);
        map.put("searchValue", searchValue);
        map.put("groupCode", groupCode);

        int totalCount = addressBookService.selectTotalGroupsWithSearch(map);
        int limit = 20;
        int buttonAmount = 5;

        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
        selectCriteria.setSearchCondition(searchCondition);
        selectCriteria.setSearchValue(searchValue);
        map.put("selectCriteria", selectCriteria);

        log.info("[AddressBookController] selectCriteria : " + selectCriteria);

        ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(addressBookService.selectGroupsWithSearch(map));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "검색 조회 성공", responseDtoWithPaging));
    }

    /**
    	* @MethodName : insertMemberToGroup
    	* @Date : 2023-03-26
    	* @Writer : 오승재
    	* @Description : 사원 주소록 주소록 그룹에 추가
    */
    @PostMapping("address-book/member-to-group")
    public ResponseEntity<ResponseDTO> insertMemberToGroup(@RequestBody JSONObject object) {

        log.info("[AddressBookController] insertMemberToGroup : " + object);
        String groupCode = (String) object.get("groupCode");
        String objectAsString = object.getAsString("memberCodes");
        String[] memberCodes = objectAsString.substring(1, objectAsString.length() - 1).split(", ");
        Map map = new HashMap();
        map.put("groupCode", groupCode);
        map.put("memberCodes", memberCodes);


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "주소록 추가 성공", addressBookService.insertMemberToGroup(map)));
    }
}
