package com.root34.aurora.mail.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
	@ClassName : ImapProperties
	@Date : 2023-04-10
	@Writer : 김수용
	@Description : Imap 설정
*/
@Configuration
@ConfigurationProperties(prefix = "imap")
@Data
public class ImapProperties {

    /**
     * @variable host imap 호스트 
     */
    private String host;
    /**
     * @variable port iamp 포트 
     */
    private int port;
    /**
     * @variable username 유저 아이디
     */
    private String username;
    /**
     * @variable password 유저 비밀번호
     */
    private String password;
    /**
     * @variable sslEnable ssl 사용 여부
     */
    private boolean sslEnable;
}