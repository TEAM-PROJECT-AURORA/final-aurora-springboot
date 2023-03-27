package com.root34.aurora.common;

import com.root34.aurora.approval.dto.ApprovalDTO;
import lombok.Data;

/**
 @FileName : FileDTO
 @Date : 3:05 PM
 @작성자 : heojaehong
 @프로그램 설명 : 파일 dto 클래스
 */
@Data
public class FileDTO {

    /**@Variable : 파일 코드*/
    private int fileCode;
    /**@Variable :  파일 이름*/
    private String fileName;
    /**@Variable :  파일 원본 이름*/
    private String fileOriginName;
    /**@Variable :  파일 사이즈*/
    private String fileSize;
    /**@Variable :  파일 경로*/
    private String filePath;
    /**@Variable :  결재 DTO, 결재 코드 용*/
    private ApprovalDTO approval;
    /**@Variable :  메일 코드*/
    private Long mailCode;
    /**@Variable :  보고 코드*/
    private Long reportCode;
}
