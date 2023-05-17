package com.root34.aurora.messenger.dto;

import com.root34.aurora.member.dto.MemberDTO;
import lombok.Data;

import java.util.List;

/**
 @FileName : MessengerDTO
 @Date : 2:01 PM
 @작성자 : heojaehong
 @프로그램 설명 : 메신저방 DTO
 */

@Data
public class MessengerDTO {

    /**@Variable : 메신저 코드*/
    private int mesCode;
    /**@Variable :  방번호*/
    private int roomNum;
    /**@Variable :  방이름*/
    private String mesName;
    /**@Variable :  맴버코드*/
    private Integer memberCode;
    /**@Variable :  멤버 DTO*/
    private MemberDTO memberDTO;

}
