package com.root34.aurora.messenger.service;

import com.root34.aurora.messenger.dao.MessengerMapper;
import com.root34.aurora.messenger.dto.MessengerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
/**
 @FileName : MessengerService
 @Date : 4:59 PM
 @작성자 : heojaehong
 @설명 : 메신저 서비스
 */
@Slf4j
@Service
public class MessengerService {
    
    /**@Variable :  Mapper 선언*/
    private MessengerMapper messengerMapper;

    public MessengerService(MessengerMapper messengerMapper) { this.messengerMapper = messengerMapper; }

    /**
        @MethodName : messengerList
    	@Date : 4:59 PM
    	@Writer : heojaehong
    	@Description : 채팅방 리스트 조회 하는 메서드
    */
    public List<MessengerDTO> messengerList(int memberCode) {

        try{
            List<MessengerDTO> list = messengerMapper.messengerList(memberCode);

            if(list == null){
                new Exception("채팅방이 없습니다.");
            }
            return list;

        } catch (Exception e){
            log.error("[MessengerService] 메신저 방 조회 실패 했습니다. : ", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "확인할 수 없습니다.", e);
        }
    }


}
