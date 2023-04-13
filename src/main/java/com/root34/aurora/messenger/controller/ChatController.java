package com.root34.aurora.messenger.controller;

import com.root34.aurora.messenger.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ChatController {

    // 클라이언트에서 `/pub/chat 주소로 메시지를 보내면 , 서버가 받은 메시지를 그대로 `/topic/messages`주소로 전달한다.
    // 클라이언트는 이주소(/topic/messages)를 구독하여 메시지를 받을 수 있다.
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageDTO sendMessage(@Payload MessageDTO messageDTO) {
        log.info("Received message: {}", messageDTO);

        return messageDTO;
    }


}
