package com.root34.aurora.worklog.dto;

import lombok.Data;
import java.sql.Date;

/**
 @ClassName : WeekWorklogDTO
 @Date : 23.03.25
 @Writer : 서지수
 @Description : 주간 업무일지 DTO
 */
@Data
public class WeekWorklogDTO {

    private int weekWorklogCode;
    private int memberCode;
    private Date weekReportingDate;
    private String monContent;
    private String tuesContent;
    private String wedContent;
    private String thurContent;
    private String friContent;
    private String weekNote;
    private String weekSpecialNote;
}
