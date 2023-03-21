package com.root34.aurora.addressBook.controller;

import com.root34.aurora.addressBook.dto.AddressBookDTO;
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
    	* @Method Description : 전체 사원 조소록 조회
    */
    @GetMapping("/address-book/addresses")
    public ResponseEntity<ResponseDTO> selectAllMemberAddressesWithPaging(@RequestParam(name="offset", defaultValue="1") String offset) {

        log.info("[AddressBookController] selectAllMemberAddressesWithPaging : " + offset);
        int totalCount = addressBookService.selectTotalMemberAddresses();
        int limit = 10;
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
    	* @Method Description : 부서별 전체 사원 주소록 조회
    */
    @GetMapping("/address-book/department/{deptCode}")
    public ResponseEntity<ResponseDTO> selectAllMemberAddressesByDeptWithPaging(@RequestParam(name="offset", defaultValue="1") String offset, @PathVariable String deptCode) {

        log.info("[AddressBookController] selectAllMemberAddressesByDeptWithPaging : " + offset);
        int totalCount = addressBookService.selectTotalMemberAddressesByDept(deptCode);
        int limit = 10;
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
    	* @Method Description : 사원 상세 조회 
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
    	* @Method Description : 그룹 추가
    */
    @PostMapping("/address-book/group")
    public ResponseEntity<ResponseDTO> insertGroup(@RequestBody JSONObject object) {

        String groupName = (String) object.get("groupName");
        log.info("[AddressBookController] insertGroup : " + groupName);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "그룹 추가 성공", addressBookService.insertGroup(groupName)));
    }

    /**
    	* @MethodName : selectAllPersonalAddressesWithPaging
    	* @Date : 2023-03-21
    	* @Writer : 오승재
    	* @Method Description : 개인 주소록 전체 조회
    */
    @GetMapping("/address-book/personal/{memberCode}")
    public ResponseEntity<ResponseDTO> selectAllPersonalAddressesWithPaging(@RequestParam String offset, @PathVariable int memberCode) {

        log.info("[AddressBookController] selectAllPersonalAddressesWithPaging : " + offset);
        int totalCount = addressBookService.selectTotalPersonalAddresses(memberCode);
        int limit = 10;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
        Map map = new HashMap();
        map.put("selectCriteria", selectCriteria);
        map.put("memberCode", memberCode);

        log.info("[AddressBookController] selectCriteria : " + selectCriteria);

        ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(addressBookService.selectAllPersonalAddresses(map));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }

    /**
    	* @MethodName : insertPersonalAddress
    	* @Date : 2023-03-21
    	* @Writer : 오승재
    	* @Method Description : 개인 주소록 추가
    */
    @PostMapping("/address-book/personal")
    public ResponseEntity<ResponseDTO> insertPersonalAddress(@RequestBody AddressBookDTO addressBookDTO) {

        log.info("[AddressBookController] insertPersonalAddress : " + addressBookDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "개인 주소록 추가 성공", addressBookService.insertPersonalAddress(addressBookDTO)));
    }

    /**
    	* @MethodName : selectAllTeamAddressesWithPaging
    	* @Date : 2023-03-21
    	* @Writer : 오승재
    	* @Method Description : 팀 주소록 전체 조회
    */
    @GetMapping("/address-book/team/{team}")
    public ResponseEntity<ResponseDTO> selectAllTeamAddressesWithPaging(@RequestParam String offset, @PathVariable String team) {

        log.info("[AddressBookController] selectAllTeamAddressesWithPaging : " + offset);
        int totalCount = addressBookService.selectTotalTeamAddresses(team);
        int limit = 10;
        int buttonAmount = 5;
        SelectCriteria selectCriteria = Pagenation.getSelectCriteria(Integer.parseInt(offset), totalCount, limit, buttonAmount);
        Map map = new HashMap();
        map.put("selectCriteria", selectCriteria);
        map.put("team", team);

        log.info("[AddressBookController] selectCriteria : " + selectCriteria);

        ResponseDTOWithPaging responseDtoWithPaging = new ResponseDTOWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(addressBookService.selectAllTeamAddresses(map));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }

    /**
    	* @MethodName : insertTeamAddress
    	* @Date : 2023-03-21
    	* @Writer : 오승재
    	* @Method Description : 팀 주소록 추가
    */
    @PostMapping("/address-book/team")
    public ResponseEntity<ResponseDTO> insertTeamAddress(@RequestBody AddressBookDTO addressBookDTO) {

        log.info("[AddressBookController] insertTeamAddress : " + addressBookDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "팀 주소록 추가 성공", addressBookService.insertTeamAddress(addressBookDTO)));
    }

    /**
    	* @MethodName : updateAddress
    	* @Date : 2023-03-21
    	* @Writer : 오승재
    	* @Method Description : 주소록 수정
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
    	* @Method Description : 주소록 삭제
    */
    @DeleteMapping("/address-book/{addBookNo}")
    public ResponseEntity<ResponseDTO> deleteAddress(@PathVariable String addBookNo) {

        log.info("[AddressBookController] deleteAddress : " + addBookNo);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "주소록 삭제 성공", addressBookService.deleteAddress(addBookNo)));
    }
}
