package com.root34.aurora.messenger.controller;

import com.root34.aurora.approval.service.ApprovalService;
import com.root34.aurora.common.ResponseDTO;
import com.root34.aurora.common.paging.Pagenation;
import com.root34.aurora.common.paging.ResponseDTOWithPaging;
import com.root34.aurora.common.paging.SelectCriteria;
import com.root34.aurora.messenger.dto.MessengerDTO;
import com.root34.aurora.messenger.dto.MessengerRequestDTO;
import com.root34.aurora.messenger.service.MessengerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 @FileName : MessengerController
 @Date : 4:40 PM
 @작성자 : heojaehong
 @설명 : 메신져 컨트롤러
 */
@RestController
@Slf4j
@RequestMapping("/api/v1")
public class MessengerController {

    private MessengerService messengerService;

    public MessengerController(MessengerService messengerService) { this.messengerService = messengerService;}

    @GetMapping("/messenger-lists/{memberCode}")
    public ResponseEntity<ResponseDTO> messengerList(@PathVariable int memberCode) {

        log.info("[MessengerController] messengerList 실행");

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공!", messengerService.messengerList(memberCode)));
    }

    @PostMapping("/messenger-lists")
    public ResponseEntity<ResponseDTO> messengerRegister(@RequestBody MessengerRequestDTO messengerRequestDTO) {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK,"메신저 등록 성공!", messengerService.messengerRegister(messengerRequestDTO)));
    }
}

