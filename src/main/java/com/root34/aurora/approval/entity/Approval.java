package com.root34.aurora.approval.entity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "TBL_APPROVAL")
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APP_CODE")
    private int appCode;

    @Column(name = "DOC_CODE")
    private int docCode;

    @Column(name = "MEMBER_CODE")
    private int memberCode;

    @Column(name = "APP_TITLE")
    private String appTitle;

    @Column(name = "APP_DESCRIPT")
    private String appDescript;

    @Column(name = "APP_DATE")
    private Date appDate;

    @Column(name = "APP_START_DATE")
    private Date appStartDate;

    @Column(name = "APP_END_DATE")
    private Date appEndDate;

    @Column(name = "APP_STATUS")
    private String appStatus;

    @Column(name = "APP_OPEN")
    private String appOpen;

    // Getters, setters 및 필요한 메서드를 여기에 추가합니다.
}

