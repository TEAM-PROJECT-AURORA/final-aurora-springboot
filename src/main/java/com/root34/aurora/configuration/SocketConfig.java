package com.root34.aurora.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 @FileName : SocketConfig
 @Date : 4:05 PM
 @작성자 : heojaehong
 @설명 : 소캣설정, Stomp 엔드포인트 설정
 */
@Configuration
/* websocket 메시지 처리를 활성화 시켜주는 어노테이션*/
@EnableWebSocketMessageBroker
/* 웹소켓 구성을 사용자 정의 가능하도록 WebSocketMessageBrokerConfigurer 구현*/
public class SocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /* stomp 접속 주소 url => /ws-stomp
        *  클라이언트가 /ws-stomp 경로를 사용해서 웹소켓 서버에 연결할 수 있고, 웹소켓을 지원하지 않는 브라우저에 대한 대체 옵션을 제공*/
        registry.addEndpoint("/ws-stomp") // 연결될 엔드포인트
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS(); // SockJS 지원을 활성화
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지를 구독 url => 메시지를 받을 때
        /* 접두사 /sub를 사용하여 간단한 브로커를 활성화하여 메시지 브로커를 설정
        *  즉, 클라이언트는 /sub 접두사가 있는 토픽을 구독하여 메시지를 수신할 수있음*/
        registry.enableSimpleBroker("/sub");

        // 메시지를 발행 url => 메시지를 보낼 때
        /* 애플리케이션 대상 접두사를 /pub으로 설정
        *  클라이언트가 서버 측 처리를 위해 /pub접두사가 있는 대상에 메시지를 보내야 한다.*/
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
