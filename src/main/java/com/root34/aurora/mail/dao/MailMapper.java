package com.root34.aurora.mail.dao;

import com.root34.aurora.mail.dto.MailDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 @ClassName : MailMapper
 @Date : 23.03.20.
 @Writer : 김수용
 @Description : 메일 SQL을 호출하기 위한 인터페이스
 */
@Mapper
public interface MailMapper {

    int sendMail(MailDTO mailDTO);
}