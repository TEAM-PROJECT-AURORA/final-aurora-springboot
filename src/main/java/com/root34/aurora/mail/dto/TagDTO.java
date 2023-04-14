package com.root34.aurora.mail.dto;

import lombok.Data;

/**
	@ClassName : TagDTO
	@Date : 2023-04-10
	@Writer : 김수용
	@Description : 태그 정보를 담을 DTO
*/
@Data
public class TagDTO {

    /**
     * @variable tagCode 태그 코드
     */
    private long tagCode;
    /**
     * @variable memberCode 멤버 코드
     */
    private int memberCode;
    /**
     * @variable tagName 태그명
     */
    private String tagName;
    /**
     * @variable tagColor 태그 색상
     */
    private String tagColor;
}