package com.example.UserSystem.controller;

import com.example.UserSystem.model.User;
import com.example.UserSystem.service.MealService;
import com.example.UserSystem.service.UserServicee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class staffController {

    private final UserServicee userService;

    @Autowired
    public staffController(UserServicee userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public String coachDashboard(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "coach-dashboard";
    }

    @GetMapping("/review")
    public String review() {
        return "review"; // يطابق templates/review.html
    }
    
  

}


