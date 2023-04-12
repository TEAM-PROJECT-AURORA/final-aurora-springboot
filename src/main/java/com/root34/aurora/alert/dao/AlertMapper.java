package com.root34.aurora.alert.dao;

import com.root34.aurora.alert.dto.AlertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 @ClassName : AlertMapper
 @Date : 23.04.13.
 @Writer : 김수용
 @Description : 알림 SQL을 호출하기 위한 인터페이스
 */
@Mapper
public interface AlertMapper {

    void registerAlert(AlertDTO alertDTO); // 알림 등록

    List<AlertDTO> selectAlertListByMemberCode(int memberCode); // 알림 목록 조회

    void updateAlert(AlertDTO alertDTO); // 알림 수정

    void readAlert(long alertCode); // 알림 읽기

    void readAllAlert(int memberCode); // 모든 알림 읽기

    Integer getMemberCodeByEmail(String email); // 이메일로 멤버코드 조회
}