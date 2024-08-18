package com.example.CAPSTONE1.chat.repo;

import com.example.CAPSTONE1.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepo extends JpaRepository<Chat, Long> {
    List<Chat> findAllById(Long id);

    @Query("SELECT c FROM Chat c WHERE c.room.id = :roomId")
    List<Chat> findAllByRoomId(@Param("roomId") Long roomId);
}
