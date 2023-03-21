package com.root34.aurora.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 @ClassName : MailConfig
 @Date : 23.03.20.
 @Writer : 김수용
 @Description : 메일 관련 설정 configuration
 */
@Slf4j
@Component
//@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class MailConfig {

//    @Value("${spring.mail.host}")
    private String host;
//    @Value("${spring.mail.port}")
    private int port;
//    @Value("${spring.mail.username}")
    private String username;
//    @Value("${spring.mail.password}")
    private String password;
//    @Value("${spring.mail.protocol}")
    private String protocol;
//    @Value("${spring.mail.properties.**}")
    private Properties properties;

    // getter, setter 메서드 생략

    @Bean
    public JavaMailSender javaMailSender() {

        log.info("host", host);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setProtocol(protocol);
        mailSender.setJavaMailProperties(properties);

        return mailSender;
    }
}
