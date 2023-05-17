package com.root34.aurora.messenger.service;

import com.root34.aurora.exception.CreationFailedException;
import com.root34.aurora.messenger.dao.MessengerMapper;
import com.root34.aurora.messenger.dto.MessengerDTO;
import com.root34.aurora.messenger.dto.MessengerRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     @MethodName : messengerRegister
     @Date : 4:59 PM
     @Writer : heojaehong
     @Description : 채팅방 생성 하는 메서드
     */
    public int messengerRegister(MessengerRequestDTO messengerRequestDTO) {

        int result = 0;
        try{
            //배열을 처리
            for(Integer memberCode : messengerRequestDTO.getMemberCode()) {
                MessengerDTO addMessengerDTO = new MessengerDTO();
                addMessengerDTO.setRoomNum(messengerRequestDTO.getRoomNum());
                addMessengerDTO.setMemberCode(memberCode);
                addMessengerDTO.setMesName(messengerRequestDTO.getMesName());

                int arrayResult = messengerMapper.messengerRegister(addMessengerDTO);

                if(arrayResult == 0){
                    throw new Exception("생성 실패!");
                }

                result += arrayResult;
            }

            return result;
        } catch (Exception e) {
            log.error("[MessengerService] 메신저 방 생성에 실패 했습니다. : ", e);
            throw new CreationFailedException("생성 실패", e);
        }
    }
}
