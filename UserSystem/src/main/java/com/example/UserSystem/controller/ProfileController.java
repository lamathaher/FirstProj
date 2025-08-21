package com.example.UserSystem.controller;

import com.example.UserSystem.model.User;
import com.example.UserSystem.repository.UserRepository;
import com.example.UserSystem.security.CustomUserDetails;
import com.example.UserSystem.service.UserServicee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {

    private final UserServicee userService;
    private final UserRepository userRepository;

    @Autowired
    public ProfileController(UserServicee userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String showMemberDashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userRepository.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "success";
    }

    @GetMapping("/staff/dashboard")
    public String showCoachDashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);

        List<User> users = userService.getAllUsers();  // جلب كل المستخدمين
        model.addAttribute("users", users);             // إضافة المستخدمين للموديل

        return "coach-dashboard";
    }
}
