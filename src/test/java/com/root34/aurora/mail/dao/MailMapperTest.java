package com.root34.aurora.mail.dao;

import com.root34.aurora.mail.dto.MailDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MailMapperTest {

    @Autowired
    private MailMapper mailMapper;

    @Test
    public void 메일_맵퍼_의존성_주입_테스트() {

        assertNotNull(mailMapper);
    }

    @Test
    @Transactional
    @Rollback(false)
    void 메일_보내기_테스트() {

        // given
        MailDTO mailDTO = new MailDTO();
//        mailDTO.setMemberCode(1);
        mailDTO.setMailTitle("Test Title");
        mailDTO.setMailBody("Test Body");
        mailDTO.setSender("TEST-01@project-aurora.co.kr");
        mailDTO.setRecipient("ssssong125@gmail.com");
//        ( DEFAULT, NULL, 1, 'Test 제목', 'Test 내용', 'TEST-01@project-aurora.co.kr', 'ssssong125@gmail.com' )

        // when
        int result = mailMapper.sendMail(mailDTO);

        // then
        assertEquals(1, result);
    }

//    @Test
//    @Transactional
//    @Rollback(false)
//    void 메일_삭제_테스트() {
//
//    }
}
