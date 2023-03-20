package com.root34.aurora.file;

import lombok.Data;

/**
 @FileName : FileDTO
 @Date : 3:05 PM
 @작성자 : heojaehong
 @프로그램 설명 : 파일 dto 클래스
 */
@Data
public class FileDTO {

    private int fileCode;
    private String fileName;
    private String fileOriginName;
    private String fileSize;
    private String filePath;
    private ApprovalDTO approval;
    // 수용이 형 메일, 보고 
    
}
