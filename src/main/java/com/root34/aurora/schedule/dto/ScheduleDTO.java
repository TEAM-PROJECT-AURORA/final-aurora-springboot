package com.root34.aurora.schedule.dto;

import lombok.Data;

import java.sql.Time;
import java.sql.Date;

//@NoArgsConstructor
@Data
public class ScheduleDTO {

    private int scheduleCode;
    private int memberCode;
    private int scheduleCategoryCode;
    private String scheduleName;
    private Date scheduleStartDay;
    private Date scheduleEndDay;
    private Time scheduleStartTime;
    private Time scheduleEndTime;
    private String schedulePlace;
    private String scheduleContent;
}
