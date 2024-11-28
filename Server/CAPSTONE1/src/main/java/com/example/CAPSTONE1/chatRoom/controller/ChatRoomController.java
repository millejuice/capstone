package com.example.CAPSTONE1.chatRoom.controller;

import com.example.CAPSTONE1.chatRoom.repo.ChatRoomRepo;
import com.example.CAPSTONE1.chatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomRepo chatRoomRepo;
    private final ChatRoomService chatRoomService;

    @PostMapping("/chatRoom")
    public void createChatRoom(String name){
        chatRoomService.createChatRoom(name);
    }

}
