package com.example.UserSystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // صاحب الريفيو
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    private String title;

    @Column(length = 2000)
    private String content;

    private Integer rating; // اختياري: مثلاً من 1 لـ 5

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters / setters
}
