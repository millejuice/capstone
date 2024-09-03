package com.example.CAPSTONE1.comment.service;

import com.example.CAPSTONE1.comment.repo.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepo commentRepo;


}
