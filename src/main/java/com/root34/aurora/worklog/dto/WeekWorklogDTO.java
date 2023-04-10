package com.root34.aurora.worklog.dto;

import lombok.Data;

import java.sql.Date;

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
