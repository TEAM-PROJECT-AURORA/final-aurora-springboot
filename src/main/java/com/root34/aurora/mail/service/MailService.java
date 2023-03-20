package com.root34.aurora.mail.service;

import com.root34.aurora.mail.dto.MailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 @ClassName : MailService
 @Date : 23.03.20.
 @Writer : 김수용
 @Description : 메일관련 요청을 처리할 Service
 */
@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // 이메일 보내는 메서드
//    public void sendEmail(String to, String subject, String text) {
    public boolean sendEmail(MailDTO mailDTO) {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(mailDTO.getRecipient());
            helper.setSubject(mailDTO.getMailTitle());
            helper.setText(mailDTO.getMailBody(), true);
//            helper.addAttachment("my_photo.png", new ClassPathResource("my_photo.png"));
            javaMailSender.send(message);

            System.out.println("체크");
        } catch (MessagingException e) {
            // 예외 처리
            return false;
        }

        return true;
    }
}
