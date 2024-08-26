package com.example.CAPSTONE1.domain.chat;

import com.example.CAPSTONE1.chat.controller.ChatController;
import com.example.CAPSTONE1.chat.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ChatController.class)
@AutoConfigureMockMvc
@SpringBootTest
class Chat {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @WithMockUser(roles = "NORMAL")
    @Test
    void chatAuthTest() throws Exception{
        List<com.example.CAPSTONE1.chat.entity.Chat> chats = this.chatService.findAllChatByRoomId(Long.valueOf(1));

    }
}
