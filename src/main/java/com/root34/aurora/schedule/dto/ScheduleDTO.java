package com.root34.aurora.schedule.dto;

import lombok.Data;
import java.sql.Date;

/**
 @ClassName : ScheduleDTO
 @Date : 23.03.20 -> 23.04.05
 @Writer : 서지수
 @Description : 일정관리 정보 DTO
 */
@Data
public class ScheduleDTO {

    /**
     * @variable 일정 코드
     */
    private int scheduleCode;
    /**
     * @variable 사원 코드
     */
    private int memberCode;
    /**
     * @variable 일정 제목
     */
    private String scheduleName;
    /**
     * @variable 시작 날짜
     */
    private Date scheduleStartDay;
    /**
     * @variable 끝 날짜
     */
    private Date scheduleEndDay;
    /**
     * @variable 장소
     */
    private String schedulePlace;
    /**
     * @variable 내용
     */
    private String scheduleContent;
}
