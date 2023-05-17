package com.root34.aurora.messenger.dto;

import com.root34.aurora.member.dto.MemberDTO;
import lombok.Data;

/**
 @FileName : MessengerMemberDTO
 @Date : 3:11 PM
 @작성자 : heojaehong
 @설명 : 다 대 다 관계 클래스
 */
@Data
public class MessengerMemberDTO {
    /**@Variable : 메신저 참가원 코드*/
    private int mesMemCode;
    /**@Variable :  메신저 DTO*/
    private MessengerDTO messengerDTO;
    /**@Variable :  맴버 DTO*/
    private MemberDTO memberDTO;
}
