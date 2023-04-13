package com.root34.aurora.messenger.controller;

import com.root34.aurora.FinalAuroraSpringbootApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ContextConfiguration(classes = FinalAuroraSpringbootApplication.class)
class MessengerControllerTest {

    @Autowired
    private MessengerController messengerController;

    private MockMvc mockMvc;

    @Test
    public void testInit() {
        assertNotNull(messengerController);
        assertNotNull(mockMvc);
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(messengerController).build();
    }
    @Test
    void 메신저_채팅방_조회_컨트롤러_테스트() throws Exception {

        // given

        // when

        // then

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messenger-lists/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("api/v1/messenger-lists/1"))
                .andDo(MockMvcResultHandlers.print());
    }
}