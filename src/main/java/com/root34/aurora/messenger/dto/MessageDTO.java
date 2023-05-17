package com.root34.aurora.messenger.dto;

import com.root34.aurora.member.dto.MemberDTO;
import lombok.Data;

import java.sql.Date;

/**
 @FileName : MessageDTO
 @Date : 3:05 PM
 @작성자 : heojaehong
 @설명 : 대화내용에대한 클래스
 */
@Data
public class MessageDTO {

    /**@Variable :  메세지 코드*/
    private int messageCode;
    /**@Variable :  채팅방 번호*/
    private int mesCode;
    /**@Variable :  맴버 코드*/
    private int memberCode;
    /**@Variable :  메시지 내용*/
    private String messageDescript;
    /**@Variable :  메신저 보낸 시간*/
    private Date messageTime;

    /**@Variable :  멤버 DTO*/
    private MemberDTO memberDTO;
}
