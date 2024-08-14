package com.example.CAPSTONE1.user.repo;

import com.example.CAPSTONE1.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
