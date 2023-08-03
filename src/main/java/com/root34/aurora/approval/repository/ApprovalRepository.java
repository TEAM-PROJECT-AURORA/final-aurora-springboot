package com.root34.aurora.approval.repository;

import com.root34.aurora.approval.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** 제네릭의 integer는 테이블의 primary key의 데이터 타입에 따라 변경된다.
 *  Approval의 app_code는 타입이 int이기 때문에 제네릭에 Integer로 한다*/
public interface ApprovalRepository extends JpaRepository<Approval, Integer> {

    Approval findByAppCode(int appCode);
}
