package com.example.CAPSTONE1.community.repo;

import com.example.CAPSTONE1.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepo extends JpaRepository<Community,Long> {
}