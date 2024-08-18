package com.example.CAPSTONE1.chat.service;

import com.example.CAPSTONE1.chat.dto.req.ChatReq;
import com.example.CAPSTONE1.chat.entity.Chat;
import com.example.CAPSTONE1.chat.repo.ChatRepo;
import com.example.CAPSTONE1.chatRoom.entity.ChatRoom;
import com.example.CAPSTONE1.chatRoom.repo.ChatRoomRepo;
import com.example.CAPSTONE1.user.entity.User;
import com.example.CAPSTONE1.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {
    private final ChatRepo chatRepo;
    private final ChatRoomRepo chatRoomRepo;
    private final UserRepo userRepo;

    public Chat createChat(Long roomId, ChatReq.ChattingReq req) {

        ChatRoom chatRoom = chatRoomRepo.findById(roomId).orElseThrow(() -> {
            return new IllegalArgumentException("ChatRoom not found");
        });

        User u = userRepo.findByEmail(req.getSenderEmail());

        return chatRepo.save(Chat.createChat(chatRoom, u.getName(), req.getSenderEmail(), req.getMessage()));
    }


    public List<Chat> findAllChatByRoomId(Long roomId){
        return chatRepo.findAllByRoomId(roomId);
    }

}
