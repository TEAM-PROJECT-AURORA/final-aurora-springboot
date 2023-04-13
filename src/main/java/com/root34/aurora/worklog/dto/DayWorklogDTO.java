package com.root34.aurora.worklog.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class DayWorklogDTO {

    private int dayWorklogCode;
    private int memberCode;
    private Date dayReportingDate;
    private String morningDayContent;
    private String afternoonDayContent;
    private String morningDayNote;
    private String afternoonDayNote;
    private String daySpecialNote;

    private String memberName;
    private String deptCode;
    private String deptName;
    private String jobCode;
    private String jobName;
    private String taskCode;
    private String memberEmail;
    private String introduction;
    private String birthday;
    private String team;
    private String phone;

}
