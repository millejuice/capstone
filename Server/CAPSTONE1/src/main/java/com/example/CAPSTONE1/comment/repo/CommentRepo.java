package com.example.CAPSTONE1.comment.repo;

import com.example.CAPSTONE1.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Long> {
}
