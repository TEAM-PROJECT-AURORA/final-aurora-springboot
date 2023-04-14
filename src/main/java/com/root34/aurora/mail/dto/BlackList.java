package com.root34.aurora.mail.dto;

import lombok.Data;

import java.util.List;

/**
 @ClassName : BlockedSenders
 @Date : 2023-04-10
 @Writer : 김수용
 @Description : 스팸 목록에 등록할 email 리스트
 */
@Data
public class BlackList {

    /**
     * @variable emails 이메일 목록
     */
    private List<String> emails;
}
