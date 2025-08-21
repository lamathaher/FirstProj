package com.example.UserSystem.controller;

import com.example.UserSystem.model.User;
import com.example.UserSystem.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class GeneralPagesController {

	@Autowired
	private CustomUserDetailsService userService;


    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

  

    @GetMapping("/contactus")
    public String contactPage() {
        return "contactus";
    }

    @GetMapping("/body")
    public String showBodyPage(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            User user = userService.getUserByEmail(email); // ✅ get user from service
            model.addAttribute("user", user);              // ✅ pass to Thymeleaf
        }
        return "body"; // ✅ maps to body.html
    }
    @GetMapping("/plan")
    public String plan() {
        return "plan";
    }
    @GetMapping("/workouts")
    public String workouts() {
        return "workouts";
    }
    @GetMapping("/classes")
    public String classes() {
        return "classes";
    }
   
    
}
