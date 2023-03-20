package com.root34.aurora.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 @ClassName : MailConfig
 @Date : 23.03.20.
 @Writer : 김수용
 @Description : 메일 관련 설정 configuration
 */
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailConfig {

    private String host;
    private int port;
    private String username;
    private String password;
    private String protocol;
    private Properties properties;

    // getter, setter 메서드 생략

    @Bean
    public JavaMailSender javaMailSender() {

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
