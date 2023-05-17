package com.root34.aurora.messenger.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessengerRequestDTO {
    /**@Variable : 방번호*/
    private int roomNum;
    /**@Variable : 방이름*/
    private String mesName;
    /**@Variable : 맴버코드*/
    private List<Integer> memberCode;
}
