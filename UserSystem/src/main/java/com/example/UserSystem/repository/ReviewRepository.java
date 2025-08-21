package com.example.UserSystem.repository;
import com.example.UserSystem.model.Review;
import com.example.UserSystem.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId);
}
