package com.root34.aurora.messenger.dao;

import com.root34.aurora.messenger.dto.MessengerDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessengerMapper {
    public int messengerRegister(MessengerDTO messengerDTO);

    /** 메신저 리스트 조회
     * @param memberCode 맴버코드*/
    public List<MessengerDTO> messengerList(int memberCode);

}
