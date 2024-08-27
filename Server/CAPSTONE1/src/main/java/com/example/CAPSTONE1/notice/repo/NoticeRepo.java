package com.example.CAPSTONE1.notice.repo;

import com.example.CAPSTONE1.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepo extends JpaRepository<Notice,Long> {
}
