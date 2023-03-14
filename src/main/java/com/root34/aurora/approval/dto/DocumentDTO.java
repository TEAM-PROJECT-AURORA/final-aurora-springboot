package com.root34.aurora.approval.dto;

import lombok.Data;
/**
 @FileName : DocumentDto
 @Date : 12:17 PM
 @작성자 : heojaehong
 @프로그램 설명 : 문서 DTO
 */
@Data
public class DocumentDTO {

    /**@params : 문서코드*/
    private int docCode;
    /**@params : 문서이름*/
    private String docName;
}
