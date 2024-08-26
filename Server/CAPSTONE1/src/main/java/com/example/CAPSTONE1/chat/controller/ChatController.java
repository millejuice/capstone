package com.example.CAPSTONE1.chat.controller;

import com.example.CAPSTONE1.chat.dto.req.ChatReq;
import com.example.CAPSTONE1.chat.entity.Chat;
import com.example.CAPSTONE1.chat.service.ChatService;
import com.example.CAPSTONE1.chatRoom.repo.ChatRoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/{chatRoomId}") //여기로 전송되면 메서드 호출 클라이언트에서는 /pub/{chatRoomId}로 요청
   // @SendTo("/sub/{chatRoomId}") // /sub/{chatRoomId}를 구독한 모든곳에 메세지 전달. broker에서 적용한거 앞에 붙여줘야 함
    public void chat(@DestinationVariable("chatRoomId") Long chatRoomId, ChatReq.ChattingReq req){
        chatService.createChat(chatRoomId, req);
        simpMessagingTemplate.convertAndSend("/sub/" + chatRoomId, req);
    }


    @GetMapping("/chat/{roomId}")
    public ResponseEntity<List<Chat>> getMessages(@PathVariable Long roomId){
        List<Chat> res = chatService.findAllChatByRoomId(roomId);
        return ResponseEntity.ok().body(res);
    }


}
