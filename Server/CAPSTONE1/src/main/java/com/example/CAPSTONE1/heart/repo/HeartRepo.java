package com.example.CAPSTONE1.heart.repo;

import com.example.CAPSTONE1.heart.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepo extends JpaRepository<Heart,Long> {
}
