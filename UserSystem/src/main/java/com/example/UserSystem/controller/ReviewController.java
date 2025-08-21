package com.example.UserSystem.controller;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserSystem.model.Review;
import com.example.UserSystem.repository.ReviewRepository;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepository repo;

    public ReviewController(ReviewRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Review> submitReview(@RequestBody Review review, Principal principal) {
        // لازم تربط الريفيو باليوزر الحالي: تجيب اليوزر من الـ principal
        // مثال: User user = userService.findByEmail(principal.getName());
        // review.setUser(user);
        Review saved = repo.save(review);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/mine")
    public List<Review> getMyReviews(Principal principal) {
        // تجيب الـ userId من اليوزر الحالي
        // User user = userService.findByEmail(principal.getName());
        // return repo.findByUserId(user.getId());
        return List.of(); // placeholder
    }
}

