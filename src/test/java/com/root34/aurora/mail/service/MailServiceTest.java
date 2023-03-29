package com.root34.aurora.mail.service;

import com.root34.aurora.configuration.MailConfig;
import com.root34.aurora.mail.dto.MailDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
public class MailServiceTest {

    @Value("${file.file-dir}")
    private String FILE_DIR;
    @Value("${file.file-url}")
    private String FILE_URL;

//    private final MailConfig mailConfig;
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailServiceTest(/*JavaMailSender javaMailSender, */MailConfig mailConfig) {

//        this.mailConfig = mailConfig;
        this.javaMailSender = mailConfig.javaMailSender();
    }

    @Test
    public void 메일_서비스_의존성_주입_테스트() {

        assertNotNull(javaMailSender);
    }

    @Test
    void 메일_보내기_테스트() throws IOException {

        // given
        MailDTO mailDTO = new MailDTO();
        mailDTO.setRecipient("ssssong125@gmail.com");
//        mailDTO.setRecipient("jgh337337@gmail.com");
//        mailDTO.setRecipient("os@gmail.com");
        mailDTO.setMailTitle("Test Title");
        mailDTO.setMailBody("Test Body");
//        MockMultipartFile file = new MockMultipartFile("file", "testAuroraImage.jpg", "image/jpg",
//                                                        new FileInputStream("C:\\auroraFiles\\testAuroraImage.jpg"));

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setTo(mailDTO.getRecipient());
            messageHelper.setSubject(mailDTO.getMailTitle());
            messageHelper.setText(mailDTO.getMailBody());

//            messageHelper.addAttachment("testAuroraImage.jpg", new File("testAuroraImage.jpg"));
//            messageHelper.addAttachment("testAuroraImage.jpg", new File("C:\\auroraFiles\\testAuroraImage.jpg"));
            messageHelper.addAttachment("testAuroraImage.jpg", new File("C:\\auroraFiles\\testAuroraImage.jpg"));

            javaMailSender.send(message);

//            log.info(javaMailSender.toString());
            System.out.println(javaMailSender);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
