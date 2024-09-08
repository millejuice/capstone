package com.example.CAPSTONE1.faq.repo;

import com.example.CAPSTONE1.faq.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepo extends JpaRepository<FAQ,Long> {
}
