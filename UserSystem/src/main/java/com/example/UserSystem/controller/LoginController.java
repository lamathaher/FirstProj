package com.example.UserSystem.controller;

import com.example.UserSystem.model.User;
import com.example.UserSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    // ✅ تسجيل المستخدم (اختياري إذا بتعملي تسجيل بنفس الكنترولر)
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // ✅ معالجة تسجيل الدخول وتوجيه حسب الدور
    @GetMapping("/login-success")
    public String loginSuccess(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName());

        if (user == null) {
            return "redirect:/login?error";
        }

        String role = "ROLE_" + user.getRole();

        if ("ROLE_staff".equals(role)) {
            return "redirect:/staff/dashboard";
        } else if ("ROLE_gym_member".equals(role)) {
            return "redirect:/dashboard"; // 🟢 
        } else {
        	// بدل redirect:/success
        	return "redirect:/login?error=role";


        }
    }

}
