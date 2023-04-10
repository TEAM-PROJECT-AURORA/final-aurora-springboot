package com.root34.aurora.schedule.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class ScheduleDTO {

    private int scheduleCode;
    private int memberCode;

    private String scheduleName;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date scheduleStartDay;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date scheduleEndDay;

    private String schedulePlace;
    private String scheduleContent;
}
